# API Documentation

## Base URLs

- User Service: `http://localhost:8081`
- Product Service: `http://localhost:8082`
- Order Service: `http://localhost:8083`
- Cart Service: `http://localhost:8084`

## User Service API

### Register User
```
POST /api/users/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe",
  "role": "BUYER"
}

Response: 200 OK
{
  "id": 1,
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "BUYER"
}
```

### Get Buyer Profile
```
GET /api/users/profile/buyer/{userId}

Response: 200 OK
{
  "userId": 1,
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "BUYER",
  "totalSpent": 1500.00,
  "bestProducts": [...],
  "mostBuyingProducts": [...]
}
```

### Get Seller Profile
```
GET /api/users/profile/seller/{userId}

Response: 200 OK
{
  "userId": 2,
  "email": "seller@example.com",
  "firstName": "Jane",
  "lastName": "Smith",
  "role": "SELLER",
  "totalEarned": 5000.00,
  "bestSellingProducts": [...]
}
```

## Product Service API

### Get All Products
```
GET /api/products

Response: 200 OK
[
  {
    "id": 1,
    "sellerId": 2,
    "name": "Laptop",
    "description": "High-performance laptop",
    "price": 999.99,
    "stockQuantity": 10,
    "category": "Electronics",
    "imageUrl": "http://example.com/laptop.jpg"
  }
]
```

### Search Products
```
GET /api/products/search?keyword=laptop

Response: 200 OK
[...]
```

### Filter Products
```
GET /api/products/filter?category=Electronics&minPrice=100&maxPrice=1000

Response: 200 OK
[...]
```

### Create Product
```
POST /api/products
Content-Type: application/json

{
  "sellerId": 2,
  "name": "Laptop",
  "description": "High-performance laptop",
  "price": 999.99,
  "stockQuantity": 10,
  "category": "Electronics",
  "imageUrl": "http://example.com/laptop.jpg"
}

Response: 200 OK
```

### Update Product
```
PUT /api/products/{id}
Content-Type: application/json

{
  "name": "Updated Laptop",
  "price": 899.99,
  ...
}

Response: 200 OK
```

### Delete Product
```
DELETE /api/products/{id}

Response: 204 No Content
```

## Cart Service API

### Get Cart
```
GET /api/cart/{userId}

Response: 200 OK
{
  "id": 1,
  "userId": 1,
  "items": [
    {
      "id": 1,
      "productId": 1,
      "quantity": 2
    }
  ]
}
```

### Add to Cart
```
POST /api/cart/{userId}/items
Content-Type: application/json

{
  "productId": 1,
  "quantity": 2
}

Response: 200 OK
```

### Update Quantity
```
PUT /api/cart/{userId}/items/{productId}?quantity=3

Response: 200 OK
```

### Remove Item
```
DELETE /api/cart/{userId}/items/{productId}

Response: 200 OK
```

### Clear Cart
```
DELETE /api/cart/{userId}

Response: 204 No Content
```

## Order Service API

### Create Order
```
POST /api/orders
Content-Type: application/json

{
  "userId": 1,
  "shippingAddress": "123 Main St, City, State 12345",
  "paymentMethod": "PAY_ON_DELIVERY",
  "items": [
    {
      "productId": 1,
      "sellerId": 2,
      "quantity": 2,
      "price": 999.99
    }
  ]
}

Response: 200 OK
{
  "id": 1,
  "userId": 1,
  "totalAmount": 1999.98,
  "status": "PENDING",
  "paymentMethod": "PAY_ON_DELIVERY",
  "shippingAddress": "123 Main St, City, State 12345",
  "items": [...],
  "createdAt": "2024-01-01T10:00:00"
}
```

### Get User Orders
```
GET /api/orders/user/{userId}

Response: 200 OK
[...]
```

### Get Seller Orders
```
GET /api/orders/seller/{sellerId}

Response: 200 OK
[...]
```

### Search Orders
```
GET /api/orders/search?userId=1&status=PENDING

Response: 200 OK
[...]
```

### Update Order Status
```
PUT /api/orders/{orderId}/status?status=CONFIRMED

Response: 200 OK
```

### Cancel Order
```
PUT /api/orders/{orderId}/cancel

Response: 204 No Content
```

### Delete Order
```
DELETE /api/orders/{orderId}

Response: 204 No Content
```

### Get Buyer Statistics
```
GET /api/orders/buyer-stats/{userId}

Response: 200 OK
{
  "totalSpent": 1500.00,
  "mostBuyingProducts": [
    {
      "productId": 1,
      "count": 5
    }
  ],
  "bestProducts": [...]
}
```

### Get Seller Statistics
```
GET /api/orders/seller-stats/{sellerId}

Response: 200 OK
{
  "totalEarned": 5000.00,
  "bestSellingProducts": [
    {
      "productId": 1,
      "count": 20
    }
  ]
}
```

## Error Responses

### 400 Bad Request
```json
{
  "error": "Invalid request",
  "message": "Validation failed"
}
```

### 404 Not Found
```json
{
  "error": "Not found",
  "message": "Resource not found"
}
```

### 500 Internal Server Error
```json
{
  "error": "Internal server error",
  "message": "An unexpected error occurred"
}
```

## Status Codes

- 200 OK: Request successful
- 201 Created: Resource created
- 204 No Content: Request successful, no content returned
- 400 Bad Request: Invalid request
- 401 Unauthorized: Authentication required
- 403 Forbidden: Access denied
- 404 Not Found: Resource not found
- 500 Internal Server Error: Server error
