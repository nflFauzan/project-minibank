async function fetchJson(url) {
  const res = await fetch(url);
  if (!res.ok) throw new Error(`HTTP ${res.status} for ${url}`);
  return res.json();
}

async function loadOptions(selectEl, url, placeholder) {
  const items = await fetchJson(url);
  selectEl.innerHTML = `<option value="">${placeholder}</option>`;
  items.forEach(v => {
    const opt = document.createElement("option");
    opt.value = v;
    opt.textContent = v;
    selectEl.appendChild(opt);
  });
}

async function setCascadeByKodePos(prefix, kodePos) {
  const prov = document.getElementById(`provinsi${prefix}`);
  const kota = document.getElementById(`kota${prefix}`);
  const kec  = document.getElementById(`kecamatan${prefix}`);
  const kel  = document.getElementById(`kelurahan${prefix}`);
  const kp   = document.getElementById(`kodePos${prefix}`);

  if (!kp || !prov || !kota || !kec || !kel) return;
  if (!kodePos || kodePos.trim().length < 5) return;

  const data = await fetchJson(`/api/postal-code/${encodeURIComponent(kodePos.trim())}`);

  prov.value = data.provinsi || "";

  await loadOptions(kota, `/api/postal-code/cities?prov=${encodeURIComponent(prov.value)}`, "-- Pilih Kota --");
  kota.value = data.kota || "";

  await loadOptions(kec,
    `/api/postal-code/districts?prov=${encodeURIComponent(prov.value)}&kota=${encodeURIComponent(kota.value)}`,
    "-- Pilih Kecamatan --"
  );
  kec.value = data.kecamatan || "";

  await loadOptions(kel,
    `/api/postal-code/villages?prov=${encodeURIComponent(prov.value)}&kota=${encodeURIComponent(kota.value)}&kec=${encodeURIComponent(kec.value)}`,
    "-- Pilih Kelurahan --"
  );
  kel.value = data.kelurahan || "";

  kp.value = (data.kodePos || kodePos.trim());
}

// mirror domisili berdasarkan value identitas (tanpa lookup kodepos)
async function setCascadeByValues(prefix, values) {
  const prov = document.getElementById(`provinsi${prefix}`);
  const kota = document.getElementById(`kota${prefix}`);
  const kec  = document.getElementById(`kecamatan${prefix}`);
  const kel  = document.getElementById(`kelurahan${prefix}`);
  const kp   = document.getElementById(`kodePos${prefix}`);

  if (!prov || !kota || !kec || !kel || !kp) return;

  const { provinsi, kota: vKota, kecamatan, kelurahan, kodePos } = values;

  prov.value = provinsi || "";
  if (!prov.value) {
    kota.innerHTML = `<option value="">-- Pilih Kota --</option>`;
    kec.innerHTML  = `<option value="">-- Pilih Kecamatan --</option>`;
    kel.innerHTML  = `<option value="">-- Pilih Kelurahan --</option>`;
    kp.value = "";
    return;
  }

  await loadOptions(kota, `/api/postal-code/cities?prov=${encodeURIComponent(prov.value)}`, "-- Pilih Kota --");
  kota.value = vKota || "";

  if (!kota.value) {
    kec.innerHTML = `<option value="">-- Pilih Kecamatan --</option>`;
    kel.innerHTML = `<option value="">-- Pilih Kelurahan --</option>`;
    kp.value = "";
    return;
  }

  await loadOptions(kec,
    `/api/postal-code/districts?prov=${encodeURIComponent(prov.value)}&kota=${encodeURIComponent(kota.value)}`,
    "-- Pilih Kecamatan --"
  );
  kec.value = kecamatan || "";

  if (!kec.value) {
    kel.innerHTML = `<option value="">-- Pilih Kelurahan --</option>`;
    kp.value = "";
    return;
  }

  await loadOptions(kel,
    `/api/postal-code/villages?prov=${encodeURIComponent(prov.value)}&kota=${encodeURIComponent(kota.value)}&kec=${encodeURIComponent(kec.value)}`,
    "-- Pilih Kelurahan --"
  );
  kel.value = kelurahan || "";

  // kodepos: mirror identitas (A3)
  kp.value = kodePos || "";
}

function lockDomisili(lock) {
  const inputIds = ["alamatDomisili","rtDomisili","rwDomisili","kodePosDomisili"];
  inputIds.forEach(id => {
    const el = document.getElementById(id);
    if (!el) return;
    if (lock) el.setAttribute("readonly", "readonly");
    else el.removeAttribute("readonly");
  });

  const selectIds = ["provinsiDomisili","kotaDomisili","kecamatanDomisili","kelurahanDomisili"];
  selectIds.forEach(id => {
    const el = document.getElementById(id);
    if (!el) return;
    if (lock) {
      el.dataset.locked = "1";
      el.style.pointerEvents = "none";     // tidak bisa diubah tapi tetap submit
      el.tabIndex = -1;
      el.setAttribute("aria-disabled", "true");
    } else {
      delete el.dataset.locked;
      el.style.pointerEvents = "";
      el.tabIndex = 0;
      el.removeAttribute("aria-disabled");
    }
  });
}

async function syncDomisiliFromIdentitas() {
  // copy text inputs
  const pairs = [
    ["alamatIdentitas", "alamatDomisili"],
    ["rtIdentitas", "rtDomisili"],
    ["rwIdentitas", "rwDomisili"],
  ];
  pairs.forEach(([a,b]) => {
    const src = document.getElementById(a);
    const dst = document.getElementById(b);
    if (src && dst) dst.value = src.value || "";
  });

  // copy cascade + kodepos
  await setCascadeByValues("Domisili", {
    provinsi:  document.getElementById("provinsiIdentitas")?.value || "",
    kota:      document.getElementById("kotaIdentitas")?.value || "",
    kecamatan: document.getElementById("kecamatanIdentitas")?.value || "",
    kelurahan: document.getElementById("kelurahanIdentitas")?.value || "",
    kodePos:   document.getElementById("kodePosIdentitas")?.value || "",
  });
}

document.addEventListener("DOMContentLoaded", () => {
  const kpId = document.getElementById("kodePosIdentitas");
  const kpDom = document.getElementById("kodePosDomisili");
  const checkbox = document.getElementById("domisiliSesuaiIdentitas");

  let syncTimer = null;
  let syncing = false;

  const scheduleSync = () => {
    if (!checkbox?.checked) return;
    clearTimeout(syncTimer);
    syncTimer = setTimeout(async () => {
      if (syncing) return;
      syncing = true;
      try { await syncDomisiliFromIdentitas(); }
      catch (e) { console.error(e); }
      finally { syncing = false; }
    }, 120);
  };

  // Kode pos identitas → autofill identitas, lalu (kalau checkbox ON) mirror ke domisili
  if (kpId) {
    kpId.addEventListener("blur", async () => {
      try { await setCascadeByKodePos("Identitas", kpId.value); }
      catch (e) { console.error(e); }
      scheduleSync();
    });
  }

  // Kode pos domisili → hanya aktif kalau checkbox OFF
  if (kpDom) {
    kpDom.addEventListener("blur", async () => {
      if (checkbox?.checked) return;
      try { await setCascadeByKodePos("Domisili", kpDom.value); }
      catch (e) { console.error(e); }
    });
  }

  // Checkbox: lock + mirror. TANPA menyentuh Identitas.
  if (checkbox) {
    checkbox.addEventListener("change", async () => {
      if (checkbox.checked) {
        lockDomisili(true);
        await syncDomisiliFromIdentitas();
      } else {
        lockDomisili(false);
      }
    });
  }

  // Link terus (A2): semua perubahan Identitas memicu sync
  const watchIdsInput = [
    "alamatIdentitas","rtIdentitas","rwIdentitas","kodePosIdentitas",
  ];
  watchIdsInput.forEach(id => {
    const el = document.getElementById(id);
    if (!el) return;
    el.addEventListener("input", scheduleSync);
  });

  const watchIdsSelect = [
    "provinsiIdentitas","kotaIdentitas","kecamatanIdentitas","kelurahanIdentitas",
  ];
  watchIdsSelect.forEach(id => {
    const el = document.getElementById(id);
    if (!el) return;
    el.addEventListener("change", scheduleSync);
  });
});
