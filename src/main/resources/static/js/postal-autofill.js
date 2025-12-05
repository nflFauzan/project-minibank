document.addEventListener('DOMContentLoaded', function () {

    async function fetchAndFill(kodePos, prefix) {
      // prefix: '' for identitas fields, or 'domisili' for domisili fields (as suffix)
      if (!kodePos || kodePos.trim().length === 0) return;
      try {
        const resp = await fetch(`/api/postal-code/${encodeURIComponent(kodePos.trim())}`);
        if (!resp.ok) {
          // clear fields if not found
          document.getElementById(prefix + 'provinsi').value = '';
          document.getElementById(prefix + 'kota').value = '';
          document.getElementById(prefix + 'kecamatan').value = '';
          document.getElementById(prefix + 'kelurahan').value = '';
          return;
        }
        const data = await resp.json();
        document.getElementById(prefix + 'provinsi').value = data.provinsi || '';
        document.getElementById(prefix + 'kota').value = data.kota || '';
        document.getElementById(prefix + 'kecamatan').value = data.kecamatan || '';
        document.getElementById(prefix + 'kelurahan').value = data.kelurahan || '';
      } catch (err) {
        console.error('Error fetching postal code:', err);
      }
    }
  
    // identitas kode pos input
    const kodePosIdentitas = document.getElementById('kodePosIdentitas');
    if (kodePosIdentitas) {
      kodePosIdentitas.addEventListener('blur', function () {
        fetchAndFill(kodePosIdentitas.value, '');
      });
      kodePosIdentitas.addEventListener('keyup', function (e) {
        // optional: trigger when enter pressed
        if (e.key === 'Enter') fetchAndFill(kodePosIdentitas.value, '');
      });
    }
  
    // domisili kode pos input
    const kodePosDomisili = document.getElementById('kodePosDomisili');
    if (kodePosDomisili) {
      kodePosDomisili.addEventListener('blur', function () {
        fetchAndFill(kodePosDomisili.value, 'domisili');
      });
      kodePosDomisili.addEventListener('keyup', function (e) {
        if (e.key === 'Enter') fetchAndFill(kodePosDomisili.value, 'domisili');
      });
    }
  
    // checkbox "domisili sama dengan identitas"
    const checkboxSame = document.getElementById('domisiliSama');
    if (checkboxSame) {
      checkboxSame.addEventListener('change', function () {
        const checked = checkboxSame.checked;
        if (checked) {
          // copy identitas fields into domisili fields (except RT/RW)
          const fields = ['alamatIdentitas','provinsi','kota','kecamatan','kelurahan','rtIdentitas','rwIdentitas','kodePosIdentitas'];
          // copy specific ones:
          document.getElementById('alamatDomisili').value = document.getElementById('alamatIdentitas').value || '';
          document.getElementById('domisiliprovinsi').value = document.getElementById('provinsi').value || '';
          document.getElementById('domisilikota').value = document.getElementById('kota').value || '';
          document.getElementById('domisilikecamatan').value = document.getElementById('kecamatan').value || '';
          document.getElementById('domisilikelurahan').value = document.getElementById('kelurahan').value || '';
          document.getElementById('kodePosDomisili').value = document.getElementById('kodePosIdentitas').value || '';
        } else {
          // clear domisili fields if unchecked
          document.getElementById('alamatDomisili').value = '';
          document.getElementById('domisiliprovinsi').value = '';
          document.getElementById('domisilokota').value = '';
          document.getElementById('domisilikecamatan').value = '';
          document.getElementById('domisilikelurahan').value = '';
          // leave RT/RW alone
        }
      });
    }
  
  });
  