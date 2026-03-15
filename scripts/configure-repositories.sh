#!/bin/bash
NEXUS_URL="http://localhost:8081"
NEXUS_USER="admin"
NEXUS_PASSWORD="${NEXUS_PASSWORD:-admin123}"

echo "=== Configuring Nexus Repositories ==="

echo "Waiting for Nexus..."
until curl -sf -u $NEXUS_USER:$NEXUS_PASSWORD $NEXUS_URL/service/rest/v1/status > /dev/null; do
    sleep 5
done

echo "Creating Docker hosted repository..."
curl -X POST "$NEXUS_URL/service/rest/v1/repositories/docker/hosted" \
  -u $NEXUS_USER:$NEXUS_PASSWORD \
  -H "Content-Type: application/json" \
  -d '{
    "name": "docker-hosted",
    "online": true,
    "storage": {
      "blobStoreName": "default",
      "strictContentTypeValidation": true,
      "writePolicy": "ALLOW"
    },
    "docker": {
      "v1Enabled": true,
      "forceBasicAuth": true,
      "httpPort": 8082
    }
  }'

echo ""
echo "=== Configuration Complete ==="
