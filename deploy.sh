#!/bin/bash

echo "Starting deployment..."

# Stop existing services
echo "Stopping existing services..."
pkill -f "user-service"
pkill -f "product-service"
pkill -f "order-service"
pkill -f "cart-service"

# Start backend services
echo "Starting backend services..."
cd backend/user-service && java -jar target/user-service-1.0.0.jar &
cd ../product-service && java -jar target/product-service-1.0.0.jar &
cd ../order-service && java -jar target/order-service-1.0.0.jar &
cd ../cart-service && java -jar target/cart-service-1.0.0.jar &

# Start frontend
echo "Starting frontend..."
cd ../../frontend && npm start &

echo "Deployment completed!"
