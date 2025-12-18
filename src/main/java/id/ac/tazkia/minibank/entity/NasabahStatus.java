package id.ac.tazkia.minibank.entity;

public enum NasabahStatus {
    INACTIVE,   // sebelum di-approve supervisor
    ACTIVE,     // sudah di-approve supervisor
    REJECTED    // ditolak supervisor (biar kebedaan dari INACTIVE)
}
