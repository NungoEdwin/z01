#!/bin/bash

echo "=== Running All Tests ==="
echo ""

# Backend tests
echo "Testing Product Service..."
cd backend/product-service
mvn test
PRODUCT_RESULT=$?
cd ../..

echo ""
echo "Testing Order Service..."
cd backend/order-service
mvn test
ORDER_RESULT=$?
cd ../..

echo ""
echo "Testing User Service..."
cd backend/user-service
mvn test
USER_RESULT=$?
cd ../..

# Frontend tests
echo ""
echo "Testing Frontend..."
cd frontend
npm test
FRONTEND_RESULT=$?
cd ..

echo ""
echo "=== Test Results ==="
echo "Product Service: $([ $PRODUCT_RESULT -eq 0 ] && echo '✓ PASSED' || echo '✗ FAILED')"
echo "Order Service: $([ $ORDER_RESULT -eq 0 ] && echo '✓ PASSED' || echo '✗ FAILED')"
echo "User Service: $([ $USER_RESULT -eq 0 ] && echo '✓ PASSED' || echo '✗ FAILED')"
echo "Frontend: $([ $FRONTEND_RESULT -eq 0 ] && echo '✓ PASSED' || echo '✗ FAILED')"

if [ $PRODUCT_RESULT -eq 0 ] && [ $ORDER_RESULT -eq 0 ] && [ $USER_RESULT -eq 0 ] && [ $FRONTEND_RESULT -eq 0 ]; then
    echo ""
    echo "All tests passed! ✓"
    exit 0
else
    echo ""
    echo "Some tests failed! ✗"
    exit 1
fi
