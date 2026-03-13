# Development Guide

## Getting Started

### Prerequisites
- JDK 17 or higher
- Maven 3.8+
- Node.js 18+
- PostgreSQL 14+
- Git

### Initial Setup

1. **Clone Repository**
```bash
git clone <repository-url>
cd buy-02
```

2. **Database Setup**
```bash
createdb ecommerce
psql -d ecommerce -f database/schema.sql
```

3. **Backend Setup**
```bash
cd backend
mvn clean install
```

4. **Frontend Setup**
```bash
cd frontend
npm install
```

## Development Workflow

### Creating a New Feature

1. **Create Feature Branch**
```bash
git checkout -b feature/feature-name
```

2. **Implement Feature**
- Write code following project conventions
- Add unit tests
- Update documentation

3. **Run Tests**
```bash
# Backend
cd backend
mvn test

# Frontend
cd frontend
npm test
```

4. **Commit Changes**
```bash
git add .
git commit -m "feat: add feature description"
```

5. **Push Branch**
```bash
git push origin feature/feature-name
```

6. **Create Pull Request**
- Use PR template
- Request code review
- Wait for CI/CD checks

### Code Review Process

1. **Reviewer Checklist**
- [ ] Code follows style guidelines
- [ ] Tests are comprehensive
- [ ] Documentation is updated
- [ ] No security issues
- [ ] Performance is acceptable

2. **Approval**
- At least one approval required
- All CI/CD checks must pass
- SonarQube quality gate passed

3. **Merge**
- Squash and merge to main
- Delete feature branch

## Coding Standards

### Java (Backend)

**Naming Conventions**
- Classes: PascalCase
- Methods: camelCase
- Constants: UPPER_SNAKE_CASE
- Packages: lowercase

**Code Style**
```java
public class ProductService {
    private final ProductRepository repository;
    
    public Product createProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        return repository.save(product);
    }
}
```

### TypeScript (Frontend)

**Naming Conventions**
- Components: PascalCase
- Services: PascalCase with Service suffix
- Variables: camelCase
- Constants: UPPER_SNAKE_CASE

**Code Style**
```typescript
export class ProductService {
  private apiUrl = 'http://localhost:8082/api/products';
  
  constructor(private http: HttpClient) {}
  
  getAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.apiUrl);
  }
}
```

## Testing Guidelines

### Unit Tests

**Backend (JUnit)**
```java
@Test
void testCreateProduct() {
    Product product = new Product();
    product.setName("Test");
    
    when(repository.save(any())).thenReturn(product);
    
    Product result = service.createProduct(product);
    
    assertNotNull(result);
    assertEquals("Test", result.getName());
}
```

**Frontend (Jasmine)**
```typescript
it('should create product', () => {
  const product = { name: 'Test' };
  service.createProduct(product).subscribe(result => {
    expect(result).toBeTruthy();
  });
});
```

### Integration Tests

Test complete workflows:
- User registration → Login → Browse → Add to cart → Checkout
- Seller creates product → Buyer purchases → Order tracking

## Database Migrations

### Adding New Tables
1. Update `database/schema.sql`
2. Create migration script
3. Test locally
4. Include in PR

### Modifying Existing Tables
1. Create ALTER statements
2. Ensure backward compatibility
3. Update entity classes
4. Test thoroughly

## API Development

### Creating New Endpoints

1. **Define DTO**
```java
public class CreateProductRequest {
    private String name;
    private BigDecimal price;
    // getters and setters
}
```

2. **Implement Service**
```java
@Service
public class ProductService {
    public Product createProduct(CreateProductRequest request) {
        // implementation
    }
}
```

3. **Create Controller**
```java
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody CreateProductRequest request) {
        return ResponseEntity.ok(service.createProduct(request));
    }
}
```

4. **Add Tests**
5. **Update API Documentation**

## Debugging

### Backend Debugging
```bash
# Run with debug mode
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
```

### Frontend Debugging
- Use browser DevTools
- Angular DevTools extension
- Console logging

### Database Debugging
```bash
# Connect to database
psql -d ecommerce

# View tables
\dt

# Query data
SELECT * FROM products;
```

## Performance Optimization

### Backend
- Use pagination for large datasets
- Implement caching
- Optimize database queries
- Use connection pooling

### Frontend
- Lazy load components
- Optimize bundle size
- Use OnPush change detection
- Implement virtual scrolling

## Security Best Practices

### Backend
- Validate all inputs
- Use parameterized queries
- Implement rate limiting
- Secure sensitive data
- Use HTTPS

### Frontend
- Sanitize user inputs
- Implement CSRF protection
- Secure local storage
- Validate on client and server

## Troubleshooting

### Common Issues

**Port Already in Use**
```bash
lsof -ti:8081 | xargs kill -9
```

**Database Connection Failed**
- Check PostgreSQL is running
- Verify credentials in application.properties
- Check firewall settings

**Build Failures**
- Clean Maven cache: `mvn clean`
- Delete node_modules: `rm -rf node_modules && npm install`
- Check Java/Node versions

## Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Angular Documentation](https://angular.io/docs)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Jenkins Documentation](https://www.jenkins.io/doc/)
- [SonarQube Documentation](https://docs.sonarqube.org/)
