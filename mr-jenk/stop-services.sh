#!/bin/bash

echo "Stopping E-Commerce Services..."

# Stop backend services
pkill -f "product-service" || true
pkill -f "order-service" || true
pkill -f "user-service" || true


pkill -f "ng serve" || true
pkill -f "angular" || true

echo "All services stopped!"
