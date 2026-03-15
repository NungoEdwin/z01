#!/bin/bash
set -e
echo "=== Nexus Repository Manager Setup ==="
NEXUS_VERSION="3.60.0-02"
NEXUS_HOME="/opt/nexus"
NEXUS_DATA="/opt/sonatype-work"
NEXUS_USER="nexus"

if [ "$EUID" -ne 0 ]; then 
    echo "Please run as root (use sudo)"
    exit 1
fi

echo "Creating nexus user..."
if ! id "$NEXUS_USER" &>/dev/null; then
    useradd -r -m -U -d /opt/nexus -s /bin/bash $NEXUS_USER
fi

echo "Downloading Nexus..."
cd /opt
if [ ! -f "nexus-${NEXUS_VERSION}-unix.tar.gz" ]; then
    wget https://download.sonatype.com/nexus/3/nexus-${NEXUS_VERSION}-unix.tar.gz
fi

echo "Extracting Nexus..."
tar -xzf nexus-${NEXUS_VERSION}-unix.tar.gz
ln -sf nexus-${NEXUS_VERSION} nexus

echo "Setting ownership..."
chown -R $NEXUS_USER:$NEXUS_USER nexus-${NEXUS_VERSION}
chown -R $NEXUS_USER:$NEXUS_USER $NEXUS_DATA

echo "run_as_user=\"$NEXUS_USER\"" > nexus-${NEXUS_VERSION}/bin/nexus.rc

cat > /etc/systemd/system/nexus.service <<EOF
[Unit]
Description=Nexus Repository Manager
After=network.target

[Service]
Type=forking
LimitNOFILE=65536
User=$NEXUS_USER
Group=$NEXUS_USER
ExecStart=/opt/nexus-${NEXUS_VERSION}/bin/nexus start
ExecStop=/opt/nexus-${NEXUS_VERSION}/bin/nexus stop
Restart=on-abort
TimeoutSec=600

[Install]
WantedBy=multi-user.target
EOF

systemctl daemon-reload
systemctl enable nexus
systemctl start nexus

echo "=== Installation Complete ==="
echo "Access Nexus at: http://localhost:8081"
