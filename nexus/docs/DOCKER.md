# Docker Integration Guide

## Setup Docker Repository in Nexus

1. Navigate to: Settings → Repositories → Create repository
2. Select: docker (hosted)
3. Configure:
   - Name: `docker-hosted`
   - HTTP port: `8082`
   - Enable Docker V1 API: ✓

## Configure Docker Client

```bash
# Add insecure registry (for testing)
sudo nano /etc/docker/daemon.json
```

Add:
```json
{
  "insecure-registries": ["localhost:8082"]
}
```

Restart Docker:
```bash
sudo systemctl restart docker
```

## Build and Push Docker Image

```bash
# Build application
mvn clean package

# Build Docker image
docker build -t nexus-web-app:1.0.0 .

# Login to Nexus
docker login localhost:8082 -u admin -p password

# Tag image
docker tag nexus-web-app:1.0.0 localhost:8082/nexus-web-app:1.0.0
docker tag nexus-web-app:1.0.0 localhost:8082/nexus-web-app:latest

# Push to Nexus
docker push localhost:8082/nexus-web-app:1.0.0
docker push localhost:8082/nexus-web-app:latest
```

## Pull and Run from Nexus

```bash
docker pull localhost:8082/nexus-web-app:latest
docker run -d -p 8080:8080 localhost:8082/nexus-web-app:latest
```
