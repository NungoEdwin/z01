#!/bin/bash

echo "=== E-Commerce Microservices Setup ==="
echo ""

# Check prerequisites
echo "Checking prerequisites..."
command -v java >/dev/null 2>&1 || { echo "Java is required but not installed. Aborting." >&2; exit 1; }
command -v mvn >/dev/null 2>&1 || { echo "Maven is required but not installed. Aborting." >&2; exit 1; }
command -v node >/dev/null 2>&1 || { echo "Node.js is required but not installed. Aborting." >&2; exit 1; }
command -v docker >/dev/null 2>&1 || { echo "Docker is required but not installed. Aborting." >&2; exit 1; }

echo "✓ All prerequisites found"
echo ""

# Build backend services
echo "Building backend services..."
cd backend/product-service && mvn clean install -DskipTests && cd ../..
cd backend/order-service && mvn clean install -DskipTests && cd ../..
cd backend/user-service && mvn clean install -DskipTests && cd ../..
echo "✓ Backend services built"
echo ""

# Install frontend dependencies
echo "Installing frontend dependencies..."
cd frontend && npm install && cd ..
echo "✓ Frontend dependencies installed"
echo ""

# Setup Jenkins
echo "Setting up Jenkins..."
cd jenkins && docker-compose up -d && cd ..
echo "✓ Jenkins started"
echo ""

echo "=== Setup Complete ==="
echo ""
echo "Services:"
echo "  Jenkins: http://localhost:8080"
echo ""
echo "To get Jenkins initial password, run:"
echo "  docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword"
echo ""
echo "To start the application services, run:"
echo "  ./start-services.sh"
