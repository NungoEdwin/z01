#!/bin/bash

# Rollback script for e-commerce application
# This script restores the previous version of the application

BACKUP_DIR="/opt/backups/previous"
DEPLOY_DIR="/opt/ecommerce"

echo "Starting rollback process..."

if [ ! -d "$BACKUP_DIR" ]; then
    echo "Error: No backup found at $BACKUP_DIR"
    exit 1
fi

echo "Stopping current services..."
pkill -f product-service.jar || true
pkill -f order-service.jar || true
pkill -f user-service.jar || true

echo "Restoring previous version..."
cp -r $BACKUP_DIR/* $DEPLOY_DIR/

echo "Starting services with previous version..."
cd $DEPLOY_DIR/backend

nohup java -jar product-service.jar --server.port=8081 > /dev/null 2>&1 &
nohup java -jar order-service.jar --server.port=8082 > /dev/null 2>&1 &
nohup java -jar user-service.jar --server.port=8083 > /dev/null 2>&1 &

sleep 10

echo "Verifying services..."
curl -f http://localhost:8081/actuator/health || echo "Product service health check failed"
curl -f http://localhost:8082/actuator/health || echo "Order service health check failed"
curl -f http://localhost:8083/actuator/health || echo "User service health check failed"

echo "Rollback completed successfully!"
