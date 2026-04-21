# QUICKSTART GUIDE

## Prerequisites

Ensure you have the following installed:
- **Java 17+**: `java -version`
- **Maven 3.8+**: `mvn -version`
- **Node.js 18+**: `node -version`
- **Docker & Docker Compose**: `docker -version`
- **Git**: `git -version`

## Quick Setup (5 minutes)

### 1. Run Setup Script
```bash
./setup.sh
```

This will:
- Build all backend services
- Install frontend dependencies
- Start Jenkins in Docker

### 2. Get Jenkins Password
```bash
docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

### 3. Configure Jenkins
1. Open http://localhost:8080
2. Enter the password from step 2
3. Click "Install suggested plugins"
4. Create admin user
5. Click "Save and Finish"

### 4. Configure Jenkins Tools

**Go to: Manage Jenkins → Global Tool Configuration**

**Add Maven:**
- Name: `Maven-3.8`
- Check "Install automatically"
- Version: 3.8.6

**Add JDK:**
- Name: `JDK-17`
- Check "Install automatically"
- Version: jdk-17

**Add NodeJS:**
- Name: `NodeJS-18`
- Check "Install automatically"
- Version: 18.x

Click "Save"

### 5. Create Jenkins Pipeline Job

1. Click "New Item"
2. Name: `ecommerce-pipeline`
3. Type: "Pipeline"
4. Click "OK"
5. Under "Pipeline":
   - Definition: "Pipeline script from SCM"
   - SCM: "Git"
   - Repository URL: Your Git repository URL (or use local path)
   - Branch: `*/main` or `*/master`
   - Script Path: `Jenkinsfile`
6. Under "Build Triggers":
   - Check "Poll SCM"
   - Schedule: `H/5 * * * *`
7. Click "Save"

### 6. Start Application Services
```bash
./start-services.sh
```

Services will be available at:
- Product Service: http://localhost:8081/api/products
- Order Service: http://localhost:8082/api/orders
- User Service: http://localhost:8083/api/users
- Frontend: http://localhost:4200

## Running Tests

### Run All Tests
```bash
./run-tests.sh
```

### Run Individual Service Tests
```bash
# Backend
cd backend/product-service && mvn test
cd backend/order-service && mvn test
cd backend/user-service && mvn test

# Frontend
cd frontend && npm test
```

## Trigger Jenkins Build

### Manual Trigger
1. Go to http://localhost:8080
2. Click on `ecommerce-pipeline`
3. Click "Build Now"

### Parameterized Build
1. Click "Build with Parameters"
2. Select options:
   - ENVIRONMENT: dev/staging/production
   - SKIP_TESTS: true/false
   - DEPLOY_BACKEND: true/false
   - DEPLOY_FRONTEND: true/false
3. Click "Build"

### Automatic Trigger
Make any code change and commit:
```bash
git add .
git commit -m "Test CI/CD"
git push
```

Jenkins will automatically detect and build within 5 minutes.

## Testing CI/CD Pipeline

### Test 1: Successful Build
```bash
# Make a minor change
echo "// Test change" >> backend/product-service/src/main/java/com/ecommerce/product/Product.java
git add .
git commit -m "Test successful build"
git push
```

Watch Jenkins build succeed.

### Test 2: Build Error
```bash
# Introduce syntax error
echo "invalid java code" >> backend/product-service/src/main/java/com/ecommerce/product/Product.java
git add .
git commit -m "Test build error"
git push
```

Watch Jenkins detect and report the error.

### Test 3: Test Failure
Modify a test to fail:
```bash
# Edit backend/product-service/src/test/java/com/ecommerce/product/ProductServiceTest.java
# Change an assertion to fail
git add .
git commit -m "Test failure detection"
git push
```

Watch Jenkins halt on test failure.

## Verify Audit Requirements

### ✓ Pipeline runs successfully
- Trigger build and observe all stages complete

### ✓ Jenkins responds to build errors
- Introduce syntax error and verify pipeline fails

### ✓ Automated testing
- Tests run automatically
- Pipeline halts on test failure

### ✓ Auto-trigger on commit
- Push code and verify automatic build

### ✓ Automatic deployment
- Successful builds deploy automatically
- Check services are running

### ✓ Rollback strategy
```bash
./rollback.sh
```

### ✓ Notifications
- Check Jenkins console for build status
- Configure email/Slack in Jenkinsfile

### ✓ Parameterized builds
- Use "Build with Parameters" option

### ✓ Distributed builds
- Pipeline uses parallel stages for multiple services

## Stopping Services

```bash
./stop-services.sh
```

## Stopping Jenkins

```bash
cd jenkins
docker-compose down
```

## Troubleshooting

### Port already in use
```bash
# Find and kill process
lsof -ti:8081 | xargs kill -9
lsof -ti:8082 | xargs kill -9
lsof -ti:8083 | xargs kill -9
lsof -ti:4200 | xargs kill -9
```

### Jenkins not starting
```bash
cd jenkins
docker-compose logs jenkins
docker-compose restart jenkins
```

### Tests failing
```bash
# Clean and rebuild
cd backend/product-service
mvn clean install
```

### Frontend build issues
```bash
cd frontend
rm -rf node_modules package-lock.json
npm install
```

## View Logs

```bash
# Application logs
tail -f logs/product-service.log
tail -f logs/order-service.log
tail -f logs/user-service.log
tail -f logs/frontend.log

# Jenkins logs
docker logs jenkins -f
```

## Health Checks

```bash
# Check backend services
curl http://localhost:8081/actuator/health
curl http://localhost:8082/actuator/health
curl http://localhost:8083/actuator/health

# Test API endpoints
curl http://localhost:8081/api/products
curl http://localhost:8082/api/orders
curl http://localhost:8083/api/users
```

## Next Steps

1. Configure email notifications in Jenkins
2. Set up GitHub webhooks for instant triggers
3. Configure Slack notifications
4. Set up deployment server credentials
5. Implement security scanning
6. Add code coverage reports

## Support

For issues:
- Check logs in `./logs/` directory
- Review Jenkins console output
- Verify all prerequisites are installed
- Ensure ports are not in use

---

**Project is ready to run and test!** 🚀
