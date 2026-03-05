#!/bin/bash

# Deployment script for e-commerce services
# Usage: ./deploy.sh [environment]

ENVIRONMENT=${1:-dev}
DEPLOY_DIR="/opt/ecommerce"
BACKUP_DIR="/opt/backups"
TIMESTAMP=$(date +%Y%m%d-%H%M%S)

echo "Deploying to $ENVIRONMENT environment..."

# Create backup
echo "Creating backup..."
mkdir -p $BACKUP_DIR/previous
if [ -d "$DEPLOY_DIR" ]; then
    cp -r $DEPLOY_DIR/* $BACKUP_DIR/previous/
    tar -czf $BACKUP_DIR/backup-$TIMESTAMP.tar.gz -C $DEPLOY_DIR .
fi

# Deploy backend
echo "Deploying backend services..."
mkdir -p $DEPLOY_DIR/backend

cp backend/product-service/target/*.jar $DEPLOY_DIR/backend/product-service.jar
cp backend/order-service/target/*.jar $DEPLOY_DIR/backend/order-service.jar
cp backend/user-service/target/*.jar $DEPLOY_DIR/backend/user-service.jar

# Stop old services
pkill -f product-service.jar || true
pkill -f order-service.jar || true
pkill -f user-service.jar || true

# Start new services
cd $DEPLOY_DIR/backend
nohup java -jar product-service.jar --server.port=8081 > /var/log/product-service.log 2>&1 &
nohup java -jar order-service.jar --server.port=8082 > /var/log/order-service.log 2>&1 &
nohup java -jar user-service.jar --server.port=8083 > /var/log/user-service.log 2>&1 &

echo "Deployment completed!"
