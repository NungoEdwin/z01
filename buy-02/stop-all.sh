#!/bin/bash

echo "Stopping all services..."

# Stop backend services
for service in user-service product-service order-service cart-service; do
    if [ -f logs/$service.pid ]; then
        kill $(cat logs/$service.pid) 2>/dev/null
        rm logs/$service.pid
        echo "✓ Stopped $service"
    fi
done

# Stop frontend
if [ -f logs/frontend.pid ]; then
    kill $(cat logs/frontend.pid) 2>/dev/null
    rm logs/frontend.pid
    echo "✓ Stopped frontend"
fi

# Kill any remaining processes
pkill -f "spring-boot:run" 2>/dev/null
pkill -f "ng serve" 2>/dev/null

echo ""
echo "All services stopped"
