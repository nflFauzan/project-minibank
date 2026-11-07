@echo off
setlocal enabledelayedexpansion

:: ==============================================================================
:: PROJECT MINIBANK - DEVELOPMENT SERVER STOPPER
:: ==============================================================================
:: Script ini berfungsi untuk:
:: 1. Menghentikan proses aplikasi Spring Boot pada port 8080.
:: 2. Menutup jendela CMD khusus server MiniBank.
:: 3. Menghentikan container database PostgreSQL via Docker Compose.
:: 4. Memverifikasi status pelepasan port 8080 & 2345.
:: ==============================================================================

:: Pastikan selalu berada di direktori root project
cd /d "%~dp0"
set "PROJECT_DIR=%~dp0"
if "%PROJECT_DIR:~-1%"=="\" set "PROJECT_DIR=%PROJECT_DIR:~0,-1%"

title MiniBank - Stop Dev Server

echo ==============================================================================
echo                 PROJECT MINIBANK - MENGHENTIKAN LAYANAN DEV
echo ==============================================================================
echo Direktori: %PROJECT_DIR%
echo.

:: ------------------------------------------------------------------------------
:: 1. HENTIKAN APLIKASI SPRING BOOT (PORT 8080)
:: ------------------------------------------------------------------------------
echo [1/2] Menghentikan Aplikasi Spring Boot...

set "STOPPED_COUNT=0"
for /f "tokens=5" %%a in ('netstat -a -n -o ^| findstr /C:":8080 " ^| findstr "LISTENING"') do (
    if not "%%a"=="0" if not "%%a"=="4" (
        echo       Ditemukan proses aplikasi pada port 8080 [PID: %%a]
        echo       Menghentikan proses tree [PID: %%a]...
        taskkill /F /PID %%a /T >nul 2>&1
        set /a STOPPED_COUNT+=1
    )
)

:: Tutup juga jendela konsol log Spring Boot yang mungkin masih terbuka
taskkill /F /FI "WINDOWTITLE eq MiniBank Backend*" >nul 2>&1

:: Tutup juga proses tunnel publik jika sedang aktif
taskkill /F /IM cloudflared.exe >nul 2>&1
taskkill /F /FI "WINDOWTITLE eq MiniBank - Public Demo Tunnel*" >nul 2>&1

if !STOPPED_COUNT! gtr 0 (
    echo       [OK] Proses aplikasi Spring Boot pada port 8080 berhasil dihentikan.
) else (
    echo       [INFO] Tidak ditemukan proses aktif pada port 8080.
)
echo.

:: ------------------------------------------------------------------------------
:: 2. HENTIKAN CONTAINER DATABASE POSTGRESQL (DOCKER COMPOSE)
:: ------------------------------------------------------------------------------
echo [2/2] Menghentikan Database PostgreSQL: docker compose down ...
echo.

where docker >nul 2>&1
if !errorlevel! neq 0 (
    echo       [INFO] Docker CLI tidak ditemukan, melewati docker compose down.
    goto :docker_done
)

docker info >nul 2>&1
if !errorlevel! neq 0 (
    echo       [INFO] Docker daemon tidak aktif, melewati docker compose down.
    goto :docker_done
)

docker compose down
if !errorlevel! neq 0 (
    echo       Mencoba perintah fallback: docker-compose down ...
    docker-compose down
)
echo.
echo       [OK] Container database minibank-postgres berhasil dimatikan.

:docker_done
echo.

:: ------------------------------------------------------------------------------
:: 3. VERIFIKASI AKHIR
:: ------------------------------------------------------------------------------
ping 127.0.0.1 -n 2 >nul

set "PORT_8080_STATUS=Bebas [OK]"
netstat -a -n -o | findstr /C:":8080 " | findstr "LISTENING" >nul 2>&1
if !errorlevel! equ 0 set "PORT_8080_STATUS=Masih Terpakai [!]"

set "PORT_2345_STATUS=Bebas [OK]"
netstat -a -n -o | findstr /C:":2345 " | findstr "LISTENING" >nul 2>&1
if !errorlevel! equ 0 set "PORT_2345_STATUS=Masih Terpakai [!]"

echo ==============================================================================
echo                     RINGKASAN STATUS LAYANAN DEV
echo ==============================================================================
echo  - Port 8080 [Spring Boot] : !PORT_8080_STATUS!
echo  - Port 2345 [PostgreSQL]  : !PORT_2345_STATUS!
echo.
echo  Semua layanan dev MiniBank telah dihentikan.
echo ==============================================================================
echo.

if "%~1"=="--nopause" goto :skip_pause
echo Tekan tombol apa saja untuk menutup jendela ini...
pause >nul
:skip_pause
exit /b 0
