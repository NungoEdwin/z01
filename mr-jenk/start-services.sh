#!/bin/bash

echo "Starting E-Commerce Services..."

# Start backend services
echo "Starting Product Service on port 8081..."
cd backend/product-service
nohup mvn spring-boot:run > ../../logs/product-service.log 2>&1 &
cd ../..

sleep 5

echo "Starting Order Service on port 8082..."
cd backend/order-service
nohup mvn spring-boot:run > ../../logs/order-service.log 2>&1 &
cd ../..

sleep 5

echo "Starting User Service on port 8083..."
cd backend/user-service
nohup mvn spring-boot:run > ../../logs/user-service.log 2>&1 &
cd ../..

sleep 5

# Start frontend
echo "Starting Frontend on port 4200..."
cd frontend
nohup npm start > ../logs/frontend.log 2>&1 &
cd ..

echo ""
echo "All services started!"
echo ""
echo "Services:"
echo "  Product Service: http://localhost:8081"
echo "  Order Service: http://localhost:8082"
echo "  User Service: http://localhost:8083"
echo "  Frontend: http://localhost:4200"
echo ""
echo "Logs are available in ./logs/"
echo ""
echo "To stop services, run: ./stop-services.sh"
