#!/bin/bash

echo "========================================="
echo "E-Commerce Platform - Complete Startup"
echo "========================================="
echo ""

# Step 1: Check/Start Database
echo "Step 1: Checking Database..."
if ! sudo service postgresql status > /dev/null 2>&1; then
    echo "Starting PostgreSQL..."
    sudo service postgresql start
fi

# Check if database exists
if ! sudo -u postgres psql -lqt | cut -d \| -f 1 | grep -qw ecommerce; then
    echo "Creating database..."
    sudo -u postgres psql -c "CREATE DATABASE ecommerce;"
    sudo -u postgres psql -c "CREATE USER ecommerce_user WITH PASSWORD 'ecommerce123';"
    sudo -u postgres psql -c "GRANT ALL PRIVILEGES ON DATABASE ecommerce TO ecommerce_user;"
    sudo -u postgres psql -c "ALTER DATABASE ecommerce OWNER TO ecommerce_user;"
    sudo -u postgres psql -d ecommerce -f database/schema.sql
fi
echo "✓ Database ready"
echo ""

# Step 2: Start Backend Services
echo "Step 2: Starting Backend Services..."
mkdir -p logs

start_service() {
    local service=$1
    local port=$2
    echo "  Starting $service on port $port..."
    cd backend/$service
    nohup mvn spring-boot:run > ../../logs/$service.log 2>&1 &
    echo $! > ../../logs/$service.pid
    cd ../..
}

start_service "product-service" 8082
start_service "user-service" 8081
start_service "cart-service" 8084
start_service "order-service" 8083

echo "✓ Backend services starting (will take 1-2 minutes)..."
echo ""

# Step 3: Start Frontend
echo "Step 3: Starting Frontend..."
cd frontend
if [ ! -d "node_modules" ]; then
    echo "  Installing npm dependencies..."
    npm install > ../logs/npm-install.log 2>&1
fi
nohup npm start > ../logs/frontend.log 2>&1 &
echo $! > ../logs/frontend.pid
cd ..
echo "✓ Frontend starting..."
echo ""

echo "========================================="
echo "Startup Complete!"
echo "========================================="
echo ""
echo "Services are starting in the background..."
echo "This will take 1-2 minutes for first startup."
echo ""
echo "URLs (will be ready soon):"
echo "  Frontend:        http://localhost:4200"
echo "  Product Service: http://localhost:8082"
echo "  User Service:    http://localhost:8081"
echo "  Cart Service:    http://localhost:8084"
echo "  Order Service:   http://localhost:8083"
echo ""
echo "Monitor startup:"
echo "  tail -f logs/product-service.log"
echo ""
echo "Stop everything:"
echo "  ./stop-all.sh"
echo ""
