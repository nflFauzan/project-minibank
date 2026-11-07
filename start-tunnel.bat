@echo off
setlocal enabledelayedexpansion
title MiniBank - Public Demo Tunnel

cd /d "%~dp0"

echo ==============================================================================
echo                 PROJECT MINIBANK - PUBLIC DEMO TUNNEL
echo ==============================================================================
echo.

:: 1. Cek ketersediaan cloudflared
if not exist ".tools\cloudflared.exe" (
    echo [ERROR] .tools\cloudflared.exe tidak ditemukan!
    echo.
    pause
    exit /b 1
)

:: 2. Cek apakah server Spring Boot (port 8080) sudah jalan
netstat -a -n -o | findstr /C:":8080 " | findstr "LISTENING" >nul 2>&1
if !errorlevel! neq 0 (
    echo [PERINGATAN] Aplikasi Spring Boot (port 8080) belum aktif!
    echo              Jalankan 'start-dev.bat' terlebih dahulu agar aplikasi siap.
    echo.
    echo Tekan tombol apa saja untuk melanjutkan setelah server aktif...
    pause >nul
)

echo [OK] Server port 8080 terdeteksi aktif.
echo Menghubungkan tunnel ke Cloudflare...
echo Tunggu beberapa detik, URL publik HTTPS akan muncul di kotak di bawah ini:
echo.
echo ==============================================================================
echo  PENTING: JANGAN TUTUP JENDELA INI SELAMA TRAINING/DEMO BERLANGSUNG!
echo ==============================================================================
echo.

.tools\cloudflared.exe tunnel --url http://localhost:8080

