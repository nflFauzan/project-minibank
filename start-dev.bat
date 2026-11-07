@echo off
setlocal enabledelayedexpansion

:: ==============================================================================
:: PROJECT MINIBANK - DEVELOPMENT SERVER STARTER
:: ==============================================================================
:: Script ini berfungsi untuk:
:: 1. Memeriksa ketersediaan tool dasar (Docker, Java, Maven Wrapper).
:: 2. Memeriksa apakah port 8080 [Spring Boot] & 2345 [PostgreSQL] sedang dipakai.
:: 3. Menjalankan container database PostgreSQL via Docker Compose.
:: 4. Memverifikasi database sudah siap menerima koneksi (pg_isready).
:: 5. Menjalankan aplikasi Spring Boot di jendela baru (atau foreground jika --fg).
:: 6. Membuka browser otomatis ke http://localhost:8080 setelah server siap.
:: ==============================================================================

:: Pastikan selalu berada di direktori root project
cd /d "%~dp0"
set "PROJECT_DIR=%~dp0"
if "%PROJECT_DIR:~-1%"=="\" set "PROJECT_DIR=%PROJECT_DIR:~0,-1%"

title MiniBank - Start Dev Server

echo ==============================================================================
echo                 PROJECT MINIBANK - DEVELOPMENT ENVIRONMENT
echo ==============================================================================
echo Direktori: %PROJECT_DIR%
echo.

:: ------------------------------------------------------------------------------
:: 1. CEK PRASYARAT SISTEM (Docker CLI, Docker Daemon, Java, mvnw.cmd)
:: ------------------------------------------------------------------------------
echo [1/4] Memeriksa prasyarat sistem...

:: Cek Docker CLI
where docker >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Docker tidak terdeteksi di PATH sistem!
    echo         Pastikan Docker Desktop sudah terpasang dan ditambahkan ke PATH.
    echo.
    goto :handle_exit_error
)

:: Cek Docker Daemon
docker info >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Docker Desktop belum berjalan atau Engine Docker sedang offline!
    echo         Silakan buka aplikasi Docker Desktop terlebih dahulu.
    echo         Tunggu hingga status Docker menjadi "Engine running", lalu jalankan script ini lagi.
    echo.
    goto :handle_exit_error
)

:: Cek Java (JDK)
where java >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Java tidak ditemukan di PATH sistem!
    echo         Proyek ini membutuhkan JDK 17+ atau JDK 21+.
    echo.
    goto :handle_exit_error
)

:: Cek file mvnw.cmd
if not exist "%PROJECT_DIR%\mvnw.cmd" (
    echo [ERROR] File mvnw.cmd tidak ditemukan di %PROJECT_DIR%!
    echo.
    goto :handle_exit_error
)

echo       [OK] Docker Engine aktif, Java terdeteksi, dan Maven Wrapper siap.
echo.

:: ------------------------------------------------------------------------------
:: 2. CEK KETERSEDIAAN PORT (8080 & 2345)
:: ------------------------------------------------------------------------------
echo [2/4] Memeriksa port layanan...

:: Cek Port 8080 (Spring Boot)
set "PID_8080="
for /f "tokens=5" %%a in ('netstat -a -n -o ^| findstr /C:":8080 " ^| findstr "LISTENING"') do (
    if not "%%a"=="0" if not "%%a"=="4" (
        set "PID_8080=%%a"
    )
)

if defined PID_8080 (
    echo [PERINGATAN] Port 8080 sudah digunakan oleh proses PID: !PID_8080!
    set /p "KILL_8080=Apakah Anda ingin mematikan proses lama tersebut? [Y/T]: "
    if /i "!KILL_8080!"=="Y" (
        echo       Menghentikan proses pada port 8080...
        for /f "tokens=5" %%a in ('netstat -a -n -o ^| findstr /C:":8080 " ^| findstr "LISTENING"') do (
            if not "%%a"=="0" if not "%%a"=="4" (
                taskkill /F /PID %%a /T >nul 2>&1
            )
        )
        taskkill /F /FI "WINDOWTITLE eq MiniBank Backend*" >nul 2>&1
        ping 127.0.0.1 -n 3 >nul

        set "PID_8080_CHECK="
        for /f "tokens=5" %%b in ('netstat -a -n -o ^| findstr /C:":8080 " ^| findstr "LISTENING"') do (
            if not "%%b"=="0" if not "%%b"=="4" set "PID_8080_CHECK=%%b"
        )
        if defined PID_8080_CHECK (
            echo [ERROR] Gagal membebaskan port 8080 [masih dipakai PID: !PID_8080_CHECK!].
            echo         Silakan matikan proses tersebut via Task Manager.
            echo.
            goto :handle_exit_error
        ) else (
            echo       [OK] Port 8080 berhasil dibebaskan.
        )
    ) else (
        echo [BATAL] Port 8080 masih terpakai. Harap matikan proses sebelum menjalankan server.
        echo.
        goto :handle_exit_error
    )
) else (
    echo       [OK] Port 8080 tersedia untuk Spring Boot.
)

:: Cek Port 2345 (PostgreSQL)
set "PID_2345="
for /f "tokens=5" %%a in ('netstat -a -n -o ^| findstr /C:":2345 " ^| findstr "LISTENING"') do (
    if not "%%a"=="0" if not "%%a"=="4" (
        set "PID_2345=%%a"
    )
)

set "CONTAINER_RUNNING=0"
for /f "tokens=*" %%c in ('docker ps --filter "name=minibank-postgres" --filter "status=running" --format "{{.Names}}" 2^>nul') do (
    if "%%c"=="minibank-postgres" set "CONTAINER_RUNNING=1"
)

if defined PID_2345 (
    if "!CONTAINER_RUNNING!"=="1" (
        echo       [OK] Port 2345 aktif [Container minibank-postgres sudah berjalan].
    ) else (
        echo [PERINGATAN] Port 2345 sedang digunakan oleh proses lain [PID: !PID_2345!].
        echo             Bila bukan PostgreSQL minibank, startup Docker mungkin akan konflik.
    )
) else (
    echo       [OK] Port 2345 siap untuk PostgreSQL.
)
echo.

:: ------------------------------------------------------------------------------
:: 3. NYALAKAN DATABASE POSTGRESQL (DOCKER COMPOSE)
:: ------------------------------------------------------------------------------
echo [3/4] Menjalankan Database PostgreSQL: docker compose up -d ...
echo.
docker compose up -d
if !errorlevel! neq 0 (
    echo       Mencoba perintah fallback: docker-compose up -d ...
    docker-compose up -d
    if !errorlevel! neq 0 (
        echo.
        echo [ERROR] Gagal menjalankan docker compose up -d!
        echo         Pastikan Docker Desktop aktif dan file docker-compose.yml valid.
        echo.
        goto :handle_exit_error
    )
)
echo.
echo       Menunggu koneksi database PostgreSQL siap [pg_isready]...
set "DB_READY=0"
for /L %%i in (1,1,20) do (
    if "!DB_READY!"=="0" (
        docker exec minibank-postgres pg_isready -U minibank -d pgminibank >nul 2>&1
        if !errorlevel! equ 0 (
            set "DB_READY=1"
        ) else (
            ping 127.0.0.1 -n 2 >nul
        )
    )
)

if "!DB_READY!"=="1" (
    echo       [OK] Database PostgreSQL [minibank-postgres] siap menerima koneksi pada localhost:2345.
) else (
    echo [PERINGATAN] Database membutuhkan waktu respons lebih lama, melanjutkan proses...
)
echo.

:: ------------------------------------------------------------------------------
:: 4. MENJALANKAN APLIKASI SPRING BOOT
:: ------------------------------------------------------------------------------
echo [4/4] Menjalankan Aplikasi Spring Boot...

:: Cek parameter --fg atau --foreground
set "RUN_FOREGROUND=0"
if /i "%~1"=="--fg" set "RUN_FOREGROUND=1"
if /i "%~1"=="--foreground" set "RUN_FOREGROUND=1"

if "!RUN_FOREGROUND!"=="1" (
    echo       Menjalankan server di jendela ini [Tekan Ctrl+C untuk berhenti]...
    echo.
    call "%PROJECT_DIR%\mvnw.cmd" spring-boot:run
    exit /b %errorlevel%
)

:: Mode default: Menjalankan di jendela CMD terpisah
echo       Membuka jendela baru untuk log server...
start "MiniBank Backend - Spring Boot" /D "%PROJECT_DIR%" cmd /k "title MiniBank Backend [Spring Boot :8080] && .\mvnw.cmd spring-boot:run"

echo       Menunggu server Spring Boot aktif di http://localhost:8080...
set "APP_RUNNING=0"
for /L %%w in (1,1,45) do (
    if "!APP_RUNNING!"=="0" (
        netstat -a -n -o | findstr /C:":8080 " | findstr "LISTENING" >nul 2>&1
        if !errorlevel! equ 0 (
            set "APP_RUNNING=1"
        ) else (
            ping 127.0.0.1 -n 2 >nul
        )
    )
)

echo.
if "!APP_RUNNING!"=="1" (
    echo ==============================================================================
    echo                   APLIKASI MINIBANK BERHASIL DIAKTIFKAN!
    echo ==============================================================================
    echo  URL Aplikasi : http://localhost:8080
    echo  Database     : localhost:2345 [Database: pgminibank]
    echo  Console Log  : Lihat jendela "MiniBank Backend [Spring Boot :8080]"
    echo.
    echo  Akun Demo Siap Pakai:
    echo  +------------------+----------+-------------+-------------------------------+
    echo  ^| Role             ^| Username ^| Password    ^| Keterangan                    ^|
    echo  +------------------+----------+-------------+-------------------------------+
    echo  ^| Admin / BM       ^| admin    ^| minibank123 ^| Administrator Utama           ^|
    echo  ^| Supervisor / BM  ^| spv      ^| minibank123 ^| Supervisor Approval           ^|
    echo  ^| Customer Service ^| cs1      ^| minibank123 ^| Fatimah CS (Buka Rekening)    ^|
    echo  ^| Teller           ^| teller1  ^| minibank123 ^| Hasan Teller (Transaksi/Buku) ^|
    echo  ^| All-Role (Tim)   ^| ainun    ^| minibank123 ^| Mahasiswa Tim Tazkia          ^|
    echo  ^| All-Role (Tim)   ^| fauzan   ^| minibank123 ^| Mahasiswa Tim Tazkia          ^|
    echo  ^| All-Role (Tim)   ^| akmal    ^| minibank123 ^| Mahasiswa Tim Tazkia          ^|
    echo  ^| All-Role (Tim)   ^| zahra    ^| minibank123 ^| Mahasiswa Tim Tazkia          ^|
    echo  +------------------+----------+-------------+-------------------------------+
    echo.
    echo  Untuk mematikan seluruh layanan:
    echo    Jalankan file: stop-dev.bat
    echo ==============================================================================
    echo.
    echo Membuka browser ke http://localhost:8080 ...
    start http://localhost:8080
) else (
    echo [CATATAN] Server masih dalam proses inisialisasi / unduh dependensi.
    echo           Pantau jendela log "MiniBank Backend [Spring Boot :8080]".
    echo           Setelah teks "Started AplikasiMinibankApplication" muncul,
    echo           buka browser Anda ke http://localhost:8080
)

echo.
if "%~1"=="--nopause" goto :skip_start_pause
if "%~2"=="--nopause" goto :skip_start_pause
echo Tekan tombol apa saja untuk menutup jendela peluncur ini...
pause >nul
:skip_start_pause
exit /b 0

:handle_exit_error
echo.
if "%~1"=="--nopause" goto :skip_error_pause
if "%~2"=="--nopause" goto :skip_error_pause
echo Terjadi kendala saat inisialisasi. Tekan tombol apa saja untuk keluar...
pause >nul
:skip_error_pause
exit /b 1
