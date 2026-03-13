# E-Commerce Platform

A complete microservices-based e-commerce platform with Angular frontend and Spring Boot backend.

## Features

### Core Functionality
- **User Management**: Registration, authentication, and role-based access (Buyer/Seller/Admin)
- **Product Management**: CRUD operations, search, and filtering
- **Shopping Cart**: Persistent cart with localStorage support
- **Order Management**: Complete order lifecycle with status tracking
- **User Profiles**: Statistics for buyers (spending, favorite products) and sellers (earnings, best-selling products)
- **Search & Filter**: Keyword search and multi-criteria filtering

### Technical Features
- Microservices architecture
- RESTful APIs
- JWT authentication
- PostgreSQL database
- Responsive Angular UI
- CI/CD with Jenkins
- Code quality with SonarQube

## Architecture

### Backend Services
- **User Service** (Port 8081): User management and authentication
- **Product Service** (Port 8082): Product catalog and search
- **Order Service** (Port 8083): Order processing and statistics
- **Cart Service** (Port 8084): Shopping cart management

### Frontend
- **Angular 17**: Modern, responsive UI
- **Reactive Forms**: Form validation
- **HttpClient**: API communication
- **LocalStorage**: Cart persistence

## Database Schema

### Tables
- `users`: User accounts with roles
- `products`: Product catalog
- `carts`: Shopping carts
- `cart_items`: Cart contents
- `orders`: Order records
- `order_items`: Order details
- `wishlists`: Saved products (bonus feature)

## Setup Instructions

### Prerequisites
- Java 17+
- Maven 3.8+
- Node.js 18+
- PostgreSQL 14+
- Jenkins (for CI/CD)
- SonarQube (for code quality)

### Database Setup
```bash
createdb ecommerce
psql -d ecommerce -f database/schema.sql
```

### Backend Setup
```bash
cd backend
mvn clean install
```

Start each service:
```bash
cd user-service && mvn spring-boot:run
cd product-service && mvn spring-boot:run
cd order-service && mvn spring-boot:run
cd cart-service && mvn spring-boot:run
```

### Frontend Setup
```bash
cd frontend
npm install
npm start
```

Access at: http://localhost:4200

## API Endpoints

### User Service (8081)
- POST `/api/users/register` - Register user
- GET `/api/users/profile/buyer/{userId}` - Get buyer profile
- GET `/api/users/profile/seller/{userId}` - Get seller profile

### Product Service (8082)
- GET `/api/products` - List all products
- GET `/api/products/{id}` - Get product details
- GET `/api/products/search?keyword={keyword}` - Search products
- GET `/api/products/filter?category={cat}&minPrice={min}&maxPrice={max}` - Filter products
- POST `/api/products` - Create product

### Cart Service (8084)
- GET `/api/cart/{userId}` - Get user cart
- POST `/api/cart/{userId}/items` - Add to cart
- PUT `/api/cart/{userId}/items/{productId}?quantity={qty}` - Update quantity
- DELETE `/api/cart/{userId}/items/{productId}` - Remove item
- DELETE `/api/cart/{userId}` - Clear cart

### Order Service (8083)
- POST `/api/orders` - Create order
- GET `/api/orders/user/{userId}` - Get user orders
- GET `/api/orders/seller/{sellerId}` - Get seller orders
- GET `/api/orders/search?userId={id}&status={status}` - Search orders
- PUT `/api/orders/{orderId}/status?status={status}` - Update status
- PUT `/api/orders/{orderId}/cancel` - Cancel order
- DELETE `/api/orders/{orderId}` - Delete order
- GET `/api/orders/buyer-stats/{userId}` - Buyer statistics
- GET `/api/orders/seller-stats/{sellerId}` - Seller statistics

## CI/CD Pipeline

### Jenkins Pipeline Stages
1. **Checkout**: Pull code from repository
2. **Build Backend**: Maven build
3. **Unit Tests**: Run tests
4. **SonarQube Analysis**: Code quality check
5. **Quality Gate**: Enforce quality standards
6. **Build Frontend**: Angular build
7. **Deploy**: Deploy services

### Running Pipeline
```bash
# Configure Jenkins job to use Jenkinsfile
# Push code to trigger pipeline
git add .
git commit -m "Feature implementation"
git push origin feature-branch
```

## Code Quality

### SonarQube Integration
```bash
mvn sonar:sonar \
  -Dsonar.projectKey=ecommerce-platform \
  -Dsonar.host.url=http://localhost:9000
```

### Quality Standards
- Code coverage > 80%
- No critical/blocker issues
- Maintainability rating A
- Security rating A

## Testing

### Backend Tests
```bash
cd backend
mvn test
```

### Frontend Tests
```bash
cd frontend
npm test
```

## Security Measures

- Password encryption with BCrypt
- JWT token authentication
- CORS configuration
- SQL injection prevention with JPA
- Input validation
- XSS protection

## Best Practices

### Development Workflow
1. Create feature branch
2. Implement feature
3. Write tests
4. Create Pull Request
5. Code review
6. SonarQube check
7. Merge to main

### Code Review Checklist
- [ ] Code follows style guidelines
- [ ] Tests are included
- [ ] Documentation is updated
- [ ] No security vulnerabilities
- [ ] Performance considerations
- [ ] Error handling implemented

## Bonus Features

### Wishlist
- Save products for later
- Manage wishlist items

### Multiple Payment Methods
- Pay on Delivery (default)
- Credit Card
- PayPal

## Troubleshooting

### Common Issues

**Database Connection Error**
```bash
# Check PostgreSQL is running
sudo systemctl status postgresql
# Update credentials in application.properties
```

**Port Already in Use**
```bash
# Find and kill process
lsof -ti:8081 | xargs kill -9
```

**Frontend Build Error**
```bash
# Clear cache and reinstall
rm -rf node_modules package-lock.json
npm install
```

## Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

## License

This project is licensed under the MIT License.

## Contact

Project Link: https://github.com/yourusername/ecommerce-platform
