#!/usr/bin/env bash
# ===================================================================
# Infrastructure as Code (IaC) - VPS Initial Setup Script
# Target: Ubuntu VPS (139.190.97.52)
# Usage: sudo bash setup-vps.sh
# ===================================================================

set -euo pipefail

echo "=========================================="
echo " Starting Minibank VPS Setup on $(hostname)"
echo "=========================================="

# 1. Update system packages
echo "[1/6] Updating APT repository..."
apt-get update -y

# 2. Install prerequisites
echo "[2/6] Installing base tools & Certbot..."
apt-get install -y \
    ca-certificates \
    curl \
    gnupg \
    lsb-release \
    ufw \
    nginx \
    certbot \
    python3-certbot-nginx

# 3. Install Docker Engine & Docker Compose Plugin (if not already installed)
if ! command -v docker &> /dev/null; then
    echo "[3/6] Installing Docker Engine..."
    install -m 0755 -d /etc/apt/keyrings
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | gpg --dearmor -o /etc/apt/keyrings/docker.gpg
    chmod a+r /etc/apt/keyrings/docker.gpg

    echo \
      "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
      $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
      tee /etc/apt/sources.list.d/docker.list > /dev/null

    apt-get update -y
    apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
    systemctl enable docker
    systemctl start docker
else
    echo "[3/6] Docker is already installed."
fi

# Add tazkia user to docker group
if id "tazkia" &>/dev/null; then
    usermod -aG docker tazkia
    echo "Added user 'tazkia' to docker group."
fi

# 4. Setup Firewall (UFW)
echo "[4/6] Configuring UFW Firewall..."
ufw allow 22/tcp comment 'SSH'
ufw allow 80/tcp comment 'HTTP'
ufw allow 443/tcp comment 'HTTPS'
ufw --force enable || true

# 5. Prepare Deployment Directory
echo "[5/6] Setting up deployment directory /opt/minibank..."
mkdir -p /opt/minibank
if id "tazkia" &>/dev/null; then
    chown -R tazkia:tazkia /opt/minibank
fi
chmod 755 /opt/minibank

# 6. Configure Nginx Virtual Host
echo "[6/6] Configuring Nginx..."
NGINX_CONF_DEST="/etc/nginx/sites-available/dev.minibank.tazkia.ac.id"
if [ -f "infra/nginx/dev.minibank.tazkia.ac.id.conf" ]; then
    cp infra/nginx/dev.minibank.tazkia.ac.id.conf "$NGINX_CONF_DEST"
elif [ -f "/opt/minibank/dev.minibank.tazkia.ac.id.conf" ]; then
    cp /opt/minibank/dev.minibank.tazkia.ac.id.conf "$NGINX_CONF_DEST"
fi

ln -sf "$NGINX_CONF_DEST" /etc/nginx/sites-enabled/dev.minibank.tazkia.ac.id
nginx -t && systemctl reload nginx

echo "=========================================="
echo " VPS Setup Completed Successfully!"
echo " Deployment directory: /opt/minibank"
echo " Note: Run 'certbot --nginx -d dev.minibank.tazkia.ac.id' once DNS is active."
echo "=========================================="

