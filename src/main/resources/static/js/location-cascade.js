document.addEventListener('DOMContentLoaded', function () {

  // --------- helper umum ----------
  async function loadSelect(url, selectEl, placeholder) {
    if (!selectEl) return;

    selectEl.innerHTML = '';
    const opt0 = document.createElement('option');
    opt0.value = '';
    opt0.textContent = placeholder;
    selectEl.appendChild(opt0);

    const resp = await fetch(url);
    if (!resp.ok) {
      console.error('Gagal fetch: ' + url);
      return;
    }
    const data = await resp.json();
    data.forEach(item => {
      const o = document.createElement('option');
      o.value = item;
      o.textContent = item;
      selectEl.appendChild(o);
    });
  }

  function clearSelect(selectEl, placeholder) {
    if (!selectEl) return;
    selectEl.innerHTML = '';
    const opt0 = document.createElement('option');
    opt0.value = '';
    opt0.textContent = placeholder;
    selectEl.appendChild(opt0);
  }

  // =========================================================
  //  A. ALAMAT IDENTITAS
  // =========================================================
  const kodePosIdent = document.getElementById('kodePosIdentitas');
  const provIdent    = document.getElementById('provinsiIdentitas');
  const kotaIdent    = document.getElementById('kotaIdentitas');
  const kecIdent     = document.getElementById('kecamatanIdentitas');
  const kelIdent     = document.getElementById('kelurahanIdentitas');

  // load semua provinsi saat awal
  if (provIdent) {
    loadSelect('/api/locations/provinces', provIdent, '-- Pilih Provinsi --');
  }

  // cascading identitas
  if (provIdent && kotaIdent && kecIdent && kelIdent) {

    provIdent.addEventListener('change', async function () {
      const prov = provIdent.value;
      clearSelect(kotaIdent, '-- Pilih Kota --');
      clearSelect(kecIdent, '-- Pilih Kecamatan --');
      clearSelect(kelIdent, '-- Pilih Kelurahan --');
      if (!prov) return;
      await loadSelect('/api/locations/cities?provinsi=' + encodeURIComponent(prov),
                       kotaIdent, '-- Pilih Kota --');
    });

    kotaIdent.addEventListener('change', async function () {
      const prov = provIdent.value;
      const kota = kotaIdent.value;
      clearSelect(kecIdent, '-- Pilih Kecamatan --');
      clearSelect(kelIdent, '-- Pilih Kelurahan --');
      if (!prov || !kota) return;
      await loadSelect('/api/locations/districts?provinsi=' + encodeURIComponent(prov) +
                       '&kota=' + encodeURIComponent(kota),
                       kecIdent, '-- Pilih Kecamatan --');
    });

    kecIdent.addEventListener('change', async function () {
      const prov = provIdent.value;
      const kota = kotaIdent.value;
      const kec  = kecIdent.value;
      clearSelect(kelIdent, '-- Pilih Kelurahan --');
      if (!prov || !kota || !kec) return;
      await loadSelect('/api/locations/villages?provinsi=' + encodeURIComponent(prov) +
                       '&kota=' + encodeURIComponent(kota) +
                       '&kecamatan=' + encodeURIComponent(kec),
                       kelIdent, '-- Pilih Kelurahan --');
    });
  }

  // autofill identitas dari kode pos
  async function autofillFromKodePos_Identitas() {
    if (!kodePosIdent || !kodePosIdent.value) return;
    const kode = kodePosIdent.value.trim();
    if (!kode) return;

    try {
      const resp = await fetch('/api/postal-code/' + encodeURIComponent(kode));
      if (!resp.ok) return;
      const data = await resp.json();

      // set provinsi
      if (provIdent) {
        // kalau option belum ada, tambahkan
        let found = false;
        Array.from(provIdent.options).forEach(o => {
          if (o.value === data.provinsi) found = true;
        });
        if (!found) {
          const o = document.createElement('option');
          o.value = data.provinsi;
          o.textContent = data.provinsi;
          provIdent.appendChild(o);
        }
        provIdent.value = data.provinsi;
      }

      // load kota berdasarkan provinsi
      await loadSelect('/api/locations/cities?provinsi=' + encodeURIComponent(data.provinsi),
                       kotaIdent, '-- Pilih Kota --');
      kotaIdent.value = data.kota;

      // load kecamatan berdasarkan kota
      await loadSelect('/api/locations/districts?provinsi=' + encodeURIComponent(data.provinsi) +
                       '&kota=' + encodeURIComponent(data.kota),
                       kecIdent, '-- Pilih Kecamatan --');
      kecIdent.value = data.kecamatan;

      // load kelurahan berdasarkan kecamatan
      await loadSelect('/api/locations/villages?provinsi=' + encodeURIComponent(data.provinsi) +
                       '&kota=' + encodeURIComponent(data.kota) +
                       '&kecamatan=' + encodeURIComponent(data.kecamatan),
                       kelIdent, '-- Pilih Kelurahan --');
      kelIdent.value = data.kelurahan;

    } catch (e) {
      console.error('Gagal autofill identitas dari kodepos', e);
    }
  }

  if (kodePosIdent) {
    kodePosIdent.addEventListener('blur', autofillFromKodePos_Identitas);
  }

  // =========================================================
  //  B. ALAMAT DOMISILI
  // =========================================================
  const kodePosDom = document.getElementById('kodePosDomisili');
  const provDom    = document.getElementById('provinsiDomisili');
  const kotaDom    = document.getElementById('kotaDomisili');
  const kecDom     = document.getElementById('kecamatanDomisili');
  const kelDom     = document.getElementById('kelurahanDomisili');

  if (provDom) {
    loadSelect('/api/locations/provinces', provDom, '-- Pilih Provinsi --');
  }

  if (provDom && kotaDom && kecDom && kelDom) {

    provDom.addEventListener('change', async function () {
      const prov = provDom.value;
      clearSelect(kotaDom, '-- Pilih Kota --');
      clearSelect(kecDom, '-- Pilih Kecamatan --');
      clearSelect(kelDom, '-- Pilih Kelurahan --');
      if (!prov) return;
      await loadSelect('/api/locations/cities?provinsi=' + encodeURIComponent(prov),
                       kotaDom, '-- Pilih Kota --');
    });

    kotaDom.addEventListener('change', async function () {
      const prov = provDom.value;
      const kota = kotaDom.value;
      clearSelect(kecDom, '-- Pilih Kecamatan --');
      clearSelect(kelDom, '-- Pilih Kelurahan --');
      if (!prov || !kota) return;
      await loadSelect('/api/locations/districts?provinsi=' + encodeURIComponent(prov) +
                       '&kota=' + encodeURIComponent(kota),
                       kecDom, '-- Pilih Kecamatan --');
    });

    kecDom.addEventListener('change', async function () {
      const prov = provDom.value;
      const kota = kotaDom.value;
      const kec  = kecDom.value;
      clearSelect(kelDom, '-- Pilih Kelurahan --');
      if (!prov || !kota || !kec) return;
      await loadSelect('/api/locations/villages?provinsi=' + encodeURIComponent(prov) +
                       '&kota=' + encodeURIComponent(kota) +
                       '&kecamatan=' + encodeURIComponent(kec),
                       kelDom, '-- Pilih Kelurahan --');
    });
  }

  async function autofillFromKodePos_Domisili() {
    if (!kodePosDom || !kodePosDom.value) return;
    const kode = kodePosDom.value.trim();
    if (!kode) return;

    try {
      const resp = await fetch('/api/postal-code/' + encodeURIComponent(kode));
      if (!resp.ok) return;
      const data = await resp.json();

      if (provDom) {
        let found = false;
        Array.from(provDom.options).forEach(o => {
          if (o.value === data.provinsi) found = true;
        });
        if (!found) {
          const o = document.createElement('option');
          o.value = data.provinsi;
          o.textContent = data.provinsi;
          provDom.appendChild(o);
        }
        provDom.value = data.provinsi;
      }

      await loadSelect('/api/locations/cities?provinsi=' + encodeURIComponent(data.provinsi),
                       kotaDom, '-- Pilih Kota --');
      kotaDom.value = data.kota;

      await loadSelect('/api/locations/districts?provinsi=' + encodeURIComponent(data.provinsi) +
                       '&kota=' + encodeURIComponent(data.kota),
                       kecDom, '-- Pilih Kecamatan --');
      kecDom.value = data.kecamatan;

      await loadSelect('/api/locations/villages?provinsi=' + encodeURIComponent(data.provinsi) +
                       '&kota=' + encodeURIComponent(data.kota) +
                       '&kecamatan=' + encodeURIComponent(data.kecamatan),
                       kelDom, '-- Pilih Kelurahan --');
      kelDom.value = data.kelurahan;

    } catch (e) {
      console.error('Gagal autofill domisili dari kodepos', e);
    }
  }

  if (kodePosDom) {
    kodePosDom.addEventListener('blur', autofillFromKodePos_Domisili);
  }

  // =========================================================
  //  C. Checkbox "domisili sama dengan identitas"
  // =========================================================
  const cbSame  = document.getElementById('domisiliSama');
  const rtId    = document.getElementById('rtIdentitas');
  const rwId    = document.getElementById('rwIdentitas');
  const rtDom   = document.getElementById('rtDomisili');
  const rwDom   = document.getElementById('rwDomisili');

  if (cbSame) {
    cbSame.addEventListener('change', function () {
      if (!cbSame.checked) {
        // kalau user uncheck, biarkan dia isi sendiri (tidak di-clear)
        return;
      }

      // 1) copy kodepos apa adanya
      if (kodePosIdent && kodePosDom) {
        kodePosDom.value = kodePosIdent.value;
      }

      // 2) copy dropdown alamat PERSIS seperti identitas
      //    (option list + value yang terpilih)
      if (provIdent && provDom) {
        provDom.innerHTML = provIdent.innerHTML;
        provDom.value     = provIdent.value;
      }
      if (kotaIdent && kotaDom) {
        kotaDom.innerHTML = kotaIdent.innerHTML;
        kotaDom.value     = kotaIdent.value;
      }
      if (kecIdent && kecDom) {
        kecDom.innerHTML = kecIdent.innerHTML;
        kecDom.value     = kecIdent.value;
      }
      if (kelIdent && kelDom) {
        kelDom.innerHTML = kelIdent.innerHTML;
        kelDom.value     = kelIdent.value;
      }

      // 3) copy RT/RW
      if (rtId && rtDom) rtDom.value = rtId.value;
      if (rwId && rwDom) rwDom.value = rwId.value;
    });
  }


});
