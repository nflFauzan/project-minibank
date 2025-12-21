(function () {
  function el(id) { return document.getElementById(id); }
  function wait(ms) { return new Promise(res => setTimeout(res, ms)); }

  function copyValue(srcId, dstId) {
    const s = el(srcId), d = el(dstId);
    if (!s || !d) return;
    d.value = s.value;
  }

  function setDisabled(ids, disabled) {
    ids.forEach(id => {
      const e = el(id);
      if (e) e.disabled = disabled;
    });
  }

  async function syncDomisiliFromIdentitas() {
    // copy RT/RW & KodePos
    copyValue("rtIdentitas", "rtDomisili");
    copyValue("rwIdentitas", "rwDomisili");
    copyValue("kodePosIdentitas", "kodePosDomisili");

    // copy dropdown hierarchy
    copyValue("provinsiIdentitas", "provinsiDomisili");
    el("provinsiDomisili")?.dispatchEvent(new Event("change"));
    await wait(200);

    copyValue("kotaIdentitas", "kotaDomisili");
    el("kotaDomisili")?.dispatchEvent(new Event("change"));
    await wait(200);

    copyValue("kecamatanIdentitas", "kecamatanDomisili");
    el("kecamatanDomisili")?.dispatchEvent(new Event("change"));
    await wait(200);

    copyValue("kelurahanIdentitas", "kelurahanDomisili");
  }

  document.addEventListener("DOMContentLoaded", () => {
    const cb = el("domisiliSesuaiIdentitas");
    if (!cb) return;

    const domisiliIds = [
      "provinsiDomisili", "kotaDomisili", "kecamatanDomisili", "kelurahanDomisili",
      "rtDomisili", "rwDomisili", "kodePosDomisili"
    ];

    async function apply() {
      if (cb.checked) {
        await syncDomisiliFromIdentitas();
        setDisabled(domisiliIds, true);
      } else {
        setDisabled(domisiliIds, false);
      }
    }

    cb.addEventListener("change", () => { apply(); });

    // kalau checkbox aktif dan identitas berubah -> sync ulang
    const watch = [
      "provinsiIdentitas", "kotaIdentitas", "kecamatanIdentitas", "kelurahanIdentitas",
      "rtIdentitas", "rwIdentitas", "kodePosIdentitas"
    ];

    watch.forEach(id => {
      const e = el(id);
      if (!e) return;
      e.addEventListener("change", () => { if (cb.checked) apply(); });
      e.addEventListener("keyup", () => { if (cb.checked) apply(); });
      e.addEventListener("blur", () => { if (cb.checked) apply(); });
    });

    apply();
  });
})();
