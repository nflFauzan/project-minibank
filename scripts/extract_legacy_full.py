import urllib.request
import base64
import json
import re
import ssl
import time
import os

ctx = ssl.create_default_context()
ctx.check_hostname = False
ctx.verify_mode = ssl.CERT_NONE

base = "https://minibank.tazkia.ac.id"
auth = base64.b64encode(b"admin:minibank123").decode("ascii")
headers = {
    "Authorization": f"Basic {auth}",
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"
}

def fetch_url(url, retries=5, delay=2):
    req = urllib.request.Request(url, headers=headers)
    for attempt in range(retries):
        try:
            with urllib.request.urlopen(req, context=ctx, timeout=20) as resp:
                return resp.status, resp.read().decode('utf-8', errors='replace')
        except urllib.error.HTTPError as e:
            if e.code == 404:
                return 404, ""
            time.sleep(delay)
        except Exception as e:
            print(f"Fetch retry {attempt+1}/{retries} for {url}: {e}")
            time.sleep(delay * (attempt + 1))
    return 500, ""

print("==== [1/4] EXTRACTING PRODUCTS ====")
status, prod_json = fetch_url(f"{base}/api/products")
products = []
if status == 200:
    try:
        products = json.loads(prod_json)
        print(f"Successfully fetched {len(products)} products from /api/products")
    except Exception as e:
        print(f"JSON parse error for products: {e}")

print("\n==== [2/4] EXTRACTING CUSTOMERS ====")
customers = []
page = 0
while True:
    status, html = fetch_url(f"{base}/customer/list?page={page}&size=50")
    if status != 200 or not html:
        break
    tables = re.findall(r'<table[^>]*>(.*?)</table>', html, re.DOTALL)
    if not tables:
        break
    rows = re.findall(r'<tr[^>]*>(.*?)</tr>', tables[0], re.DOTALL)
    page_custs = []
    for r in rows[1:]:
        cols = re.findall(r'<td[^>]*>(.*?)</td>', r, re.DOTALL)
        clean_td = [' '.join(re.sub(r'<[^>]+>', ' ', c).split()) for c in cols]
        if len(clean_td) >= 5 and clean_td[0] != 'No customers found.':
            links = re.findall(r'href="([^"]+)"', r)
            edit_links = [l for l in links if '/customer/edit/' in l]
            view_links = [l for l in links if '/customer/view/' in l]
            cust_id = edit_links[0].split('/')[-1] if edit_links else (view_links[0].split('/')[-1] if view_links else '')
            page_custs.append({
                "id": cust_id,
                "cif": clean_td[0],
                "name": clean_td[1],
                "email": clean_td[2],
                "type": clean_td[3],
                "status": clean_td[4]
            })
    if not page_custs:
        break
    customers.extend(page_custs)
    print(f"Customer Page {page}: fetched {len(page_custs)} (Total: {len(customers)})")
    page += 1

print(f"Total basic customers fetched: {len(customers)}")

# Now fetch full customer details from /customer/edit/{id} or /customer/view/{id}
print("Enriching customer details from edit/view forms...")
for idx, c in enumerate(customers):
    cust_id = c["id"]
    if not cust_id:
        continue
    st, edit_html = fetch_url(f"{base}/customer/edit/{cust_id}")
    if st == 200 and edit_html:
        # Extract inputs
        inputs = re.findall(r'<input[^>]+name="([^"]+)"[^>]*value="([^"]*)"', edit_html)
        input_dict = {n: v for n, v in inputs}
        # Also check textarea for address
        textareas = re.findall(r'<textarea[^>]+name="([^"]+)"[^>]*>(.*?)</textarea>', edit_html, re.DOTALL)
        for n, v in textareas:
            input_dict[n] = v.strip()
        
        c["nik"] = input_dict.get("idNumber", input_dict.get("nik", ""))
        c["firstName"] = input_dict.get("firstName", "")
        c["lastName"] = input_dict.get("lastName", "")
        c["fullName"] = f"{c['firstName']} {c['lastName']}".strip() if (c['firstName'] or c['lastName']) else c["name"]
        c["dateOfBirth"] = input_dict.get("dateOfBirth", input_dict.get("tanggalLahir", ""))
        c["motherMaidenName"] = input_dict.get("motherMaidenName", input_dict.get("namaIbuKandung", ""))
        c["phoneNumber"] = input_dict.get("phoneNumber", input_dict.get("phone", ""))
        c["email"] = input_dict.get("email", c["email"])
        c["address"] = input_dict.get("address", "")
        c["city"] = input_dict.get("city", "")
        c["postalCode"] = input_dict.get("postalCode", "")
        c["occupation"] = input_dict.get("occupation", "")
        c["createdDate"] = input_dict.get("createdDate", "")
    
    # Also fetch view for created date / updated date if missing
    if not c.get("createdDate"):
        st_v, view_html = fetch_url(f"{base}/customer/view/{cust_id}")
        if st_v == 200 and view_html:
            m_dt = re.search(r'Created Date\s*</[^>]+>\s*<[^>]+>\s*([^<]+)<', view_html, re.IGNORECASE)
            if m_dt:
                c["createdDate"] = m_dt.group(1).strip()
    
    if (idx + 1) % 25 == 0 or idx == len(customers) - 1:
        print(f"Enriched {idx + 1}/{len(customers)} customers")

print("\n==== [3/4] EXTRACTING ACCOUNTS ====")
accounts = []
for st_code in ['ACTIVE', 'CLOSED', 'INACTIVE', 'FROZEN']:
    page = 0
    while True:
        status, html = fetch_url(f"{base}/account/list?page={page}&size=50&status={st_code}")
        if status != 200 or not html:
            break
        tables = re.findall(r'<table[^>]*>(.*?)</table>', html, re.DOTALL)
        if not tables:
            break
        rows = re.findall(r'<tr[^>]*>(.*?)</tr>', tables[0], re.DOTALL)
        page_accs = []
        for r in rows[1:]:
            cols = re.findall(r'<td[^>]*>(.*?)</td>', r, re.DOTALL)
            clean_td = [' '.join(re.sub(r'<[^>]+>', ' ', c).split()) for c in cols]
            if len(clean_td) >= 7 and 'No accounts found' not in clean_td[0]:
                links = re.findall(r'href="([^"]+)"', r)
                view_links = [l for l in links if '/account/view/' in l]
                acc_id = view_links[0].split('/')[-1] if view_links else ''
                page_accs.append({
                    "id": acc_id,
                    "accountNumber": clean_td[0],
                    "accountName": clean_td[1],
                    "customerRaw": clean_td[2],
                    "productRaw": clean_td[3],
                    "balance": clean_td[4],
                    "status": clean_td[5],
                    "openedDate": clean_td[6]
                })
        if not page_accs:
            break
        accounts.extend(page_accs)
        print(f"Account ({st_code}) Page {page}: fetched {len(page_accs)} (Total: {len(accounts)})")
        page += 1

# Deduplicate accounts by accountNumber
unique_accounts = {}
for a in accounts:
    unique_accounts[a["accountNumber"]] = a
accounts = list(unique_accounts.values())
print(f"Total Unique Accounts: {len(accounts)}")

print("\n==== [4/4] EXTRACTING TRANSACTIONS ====")
transactions = []
page = 0
while True:
    status, html = fetch_url(f"{base}/transaction/list?page={page}&size=50")
    if status != 200 or not html:
        break
    tables = re.findall(r'<table[^>]*>(.*?)</table>', html, re.DOTALL)
    if not tables:
        break
    rows = re.findall(r'<tr[^>]*>(.*?)</tr>', tables[0], re.DOTALL)
    page_trxs = []
    for r in rows[1:]:
        cols = re.findall(r'<td[^>]*>(.*?)</td>', r, re.DOTALL)
        clean_td = [' '.join(re.sub(r'<[^>]+>', ' ', c).split()) for c in cols]
        if len(clean_td) >= 6 and 'No transactions found' not in clean_td[0]:
            links = re.findall(r'href="([^"]+)"', r)
            view_links = [l for l in links if '/transaction/view/' in l]
            trx_id = view_links[0].split('/')[-1] if view_links else ''
            page_trxs.append({
                "id": trx_id,
                "trxNumber": clean_td[0],
                "date": clean_td[1],
                "type": clean_td[2],
                "accountRaw": clean_td[3],
                "amountRaw": clean_td[4],
                "notes": clean_td[5]
            })
    if not page_trxs:
        break
    transactions.extend(page_trxs)
    print(f"Transaction Page {page}: fetched {len(page_trxs)} (Total: {len(transactions)})")
    page += 1

print(f"Total Transactions: {len(transactions)}")

# Save full dump
os.makedirs("c:/project-minibank/src/main/resources/data", exist_ok=True)
dump_path = "c:/project-minibank/src/main/resources/data/legacy_minibank_full_dump.json"
with open(dump_path, "w", encoding="utf-8") as f:
    json.dump({
        "metadata": {
            "source": "https://minibank.tazkia.ac.id",
            "extracted_at": time.strftime("%Y-%m-%dT%H:%M:%SZ", time.gmtime()),
            "total_customers": len(customers),
            "total_accounts": len(accounts),
            "total_transactions": len(transactions),
            "total_products": len(products)
        },
        "products": products,
        "customers": customers,
        "accounts": accounts,
        "transactions": transactions
    }, f, indent=2, ensure_ascii=False)

print(f"\n==========================================")
print(f"SUCCESS: Full dataset exported to {dump_path}")
print(f"Customers: {len(customers)}")
print(f"Accounts: {len(accounts)}")
print(f"Transactions: {len(transactions)}")
print(f"Products: {len(products)}")
print(f"==========================================")
