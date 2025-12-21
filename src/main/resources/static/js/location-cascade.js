(async function () {
  function qs(id) { return document.getElementById(id); }

  async function fetchJson(url) {
    const resp = await fetch(url);
    if (!resp.ok) throw new Error("HTTP " + resp.status);
    return await resp.json();
  }

  function setOptions(selectEl, items, placeholder) {
    if (!selectEl) return;
    selectEl.innerHTML = "";
    const opt0 = document.createElement("option");
    opt0.value = "";
    opt0.textContent = placeholder || "-- Pilih --";
    selectEl.appendChild(opt0);

    (items || []).forEach(v => {
      const opt = document.createElement("option");
      opt.value = v;
      opt.textContent = v;
      selectEl.appendChild(opt);
    });
  }

  async function initCascade(prefix) {
    const prov = qs(`provinsi${prefix}`);
    const kota = qs(`kota${prefix}`);
    const kec  = qs(`kecamatan${prefix}`);
    const kel  = qs(`kelurahan${prefix}`);
    const kode = qs(`kodePos${prefix}`);

    if (!prov || !kota || !kec || !kel || !kode) return;

    // initial provinces
    try {
      const provs = await fetchJson("/api/postal-code/provinces");
      setOptions(prov, provs, "-- Pilih Provinsi --");
    } catch (e) {
      // kalau API error, dropdown tetap ada placeholder
      setOptions(prov, [], "-- Pilih Provinsi --");
    }

    setOptions(kota, [], "-- Pilih Kota --");
    setOptions(kec, [], "-- Pilih Kecamatan --");
    setOptions(kel, [], "-- Pilih Kelurahan --");

    prov.addEventListener("change", async () => {
      const p = prov.value;
      setOptions(kota, [], "-- Pilih Kota --");
      setOptions(kec, [], "-- Pilih Kecamatan --");
      setOptions(kel, [], "-- Pilih Kelurahan --");
      kode.value = "";

      if (!p) return;
      const cities = await fetchJson(`/api/postal-code/cities?prov=${encodeURIComponent(p)}`);
      setOptions(kota, cities, "-- Pilih Kota --");
    });

    kota.addEventListener("change", async () => {
      const p = prov.value;
      const k = kota.value;
      setOptions(kec, [], "-- Pilih Kecamatan --");
      setOptions(kel, [], "-- Pilih Kelurahan --");
      kode.value = "";

      if (!p || !k) return;
      const districts = await fetchJson(`/api/postal-code/districts?prov=${encodeURIComponent(p)}&kota=${encodeURIComponent(k)}`);
      setOptions(kec, districts, "-- Pilih Kecamatan --");
    });

    kec.addEventListener("change", async () => {
      const p = prov.value;
      const k = kota.value;
      const c = kec.value;
      setOptions(kel, [], "-- Pilih Kelurahan --");
      kode.value = "";

      if (!p || !k || !c) return;
      const villages = await fetchJson(`/api/postal-code/villages?prov=${encodeURIComponent(p)}&kota=${encodeURIComponent(k)}&kec=${encodeURIComponent(c)}`);
      setOptions(kel, villages, "-- Pilih Kelurahan --");
    });

    kel.addEventListener("change", async () => {
      const p = prov.value;
      const k = kota.value;
      const c = kec.value;
      const v = kel.value;
      kode.value = "";

      if (!p || !k || !c || !v) return;
      const codes = await fetchJson(`/api/postal-code/codes?prov=${encodeURIComponent(p)}&kota=${encodeURIComponent(k)}&kec=${encodeURIComponent(c)}&kel=${encodeURIComponent(v)}`);
      if (codes && codes.length > 0) kode.value = codes[0];
    });
  }

  document.addEventListener("DOMContentLoaded", async () => {
    await initCascade("Identitas");
    await initCascade("Domisili");
  });
})();
