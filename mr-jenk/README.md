# E-Commerce Microservices CI/CD Pipeline with Jenkins

A complete e-commerce platform with microservices architecture and automated CI/CD pipeline using Jenkins.

## Table of Contents
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Setup Instructions](#setup-instructions)
- [Running the Application](#running-the-application)
- [Testing](#testing)
- [CI/CD Pipeline](#cicd-pipeline)
- [Deployment](#deployment)
- [Troubleshooting](#troubleshooting)

## Architecture

This project consists of:
- **Product Service** (Spring Boot - Java)
- **Order Service** (Spring Boot - Java)
- **User Service** (Spring Boot - Java)
- **Frontend** (Angular)
- **Jenkins CI/CD Pipeline**

## Prerequisites

- Java 17 or higher
- Maven 3.8+
- Node.js 18+ and npm
- Docker and Docker Compose
- Git
- Jenkins (will be set up via Docker)

## Setup Instructions

### 1. Clone the Repository

```bash
git clone <your-repo-url>
cd mr-jenk
```

### 2. Set Up Jenkins

#### Option A: Using Docker (Recommended)

```bash
cd jenkins
docker-compose up -d
```

Jenkins will be available at `http://localhost:8080`

#### Get Initial Admin Password

```bash
docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

#### Option B: Manual Installation

1. Download Jenkins from https://www.jenkins.io/download/
2. Install Jenkins following the platform-specific instructions
3. Start Jenkins service

### 3. Configure Jenkins

1. Open `http://localhost:8080` in your browser
2. Enter the initial admin password
3. Install suggested plugins
4. Create an admin user
5. Install additional plugins:
   - Pipeline
   - Git
   - Maven Integration
   - NodeJS
   - Email Extension
   - Slack Notification (optional)
   - JUnit
   - HTML Publisher

### 4. Configure Jenkins Tools

**Configure Maven:**
- Go to: Manage Jenkins → Global Tool Configuration
- Add Maven: Name: `Maven-3.8`, Install automatically

**Configure JDK:**
- Add JDK: Name: `JDK-17`, Install automatically

**Configure NodeJS:**
- Add NodeJS: Name: `NodeJS-18`, Version: 18.x

### 5. Create Jenkins Credentials

Go to: Manage Jenkins → Credentials → System → Global credentials

Add the following credentials:

1. **Git Credentials** (if private repo)
   - Kind: Username with password
   - ID: `git-credentials`
   - Username: Your Git username
   - Password: Your Git token/password

2. **Deployment Server Credentials**
   - Kind: SSH Username with private key
   - ID: `deployment-server`
   - Username: deployment user
   - Private Key: Your SSH private key

3. **Email Credentials**
   - Kind: Username with password
   - ID: `email-credentials`
   - Username: Your email
   - Password: Your email password/app password

4. **Slack Token** (optional)
   - Kind: Secret text
   - ID: `slack-token`
   - Secret: Your Slack token

### 6. Create Jenkins Pipeline Job

1. Click "New Item"
2. Enter name: `ecommerce-pipeline`
3. Select "Pipeline"
4. Under "Pipeline" section:
   - Definition: Pipeline script from SCM
   - SCM: Git
   - Repository URL: Your repository URL
   - Credentials: Select git-credentials
   - Branch: */main
   - Script Path: Jenkinsfile
5. Under "Build Triggers":
   - Check "Poll SCM"
   - Schedule: `H/5 * * * *` (polls every 5 minutes)
   - Or use GitHub webhook for instant triggers
6. Save

### 7. Set Up GitHub Webhook (Optional but Recommended)

1. Go to your GitHub repository → Settings → Webhooks
2. Add webhook:
   - Payload URL: `http://<your-jenkins-url>/github-webhook/`
   - Content type: application/json
   - Events: Just the push event
   - Active: checked

## Running the Application

### Run All Services with Docker Compose

```bash
docker-compose up -d
```

Services will be available at:
- Product Service: http://localhost:8081
- Order Service: http://localhost:8082
- User Service: http://localhost:8083
- Frontend: http://localhost:4200

### Run Services Individually

#### Backend Services

```bash
# Product Service
cd backend/product-service
mvn spring-boot:run

# Order Service
cd backend/order-service
mvn spring-boot:run

# User Service
cd backend/user-service
mvn spring-boot:run
```

#### Frontend

```bash
cd frontend
npm install
npm start
```

## Testing

### Run Backend Tests

```bash
# Test all services
cd backend/product-service && mvn test
cd backend/order-service && mvn test
cd backend/user-service && mvn test

# Or test all at once from root
mvn test
```

### Run Frontend Tests

```bash
cd frontend

# Unit tests
npm test

# E2E tests
npm run e2e

# Test with coverage
npm run test:coverage
```

### View Test Reports

After running tests, reports are available at:
- Backend: `target/surefire-reports/`
- Frontend: `coverage/` and `test-results/`

## CI/CD Pipeline

### Pipeline Stages

1. **Checkout**: Fetches code from Git repository
2. **Build Backend**: Compiles all Java microservices
3. **Test Backend**: Runs JUnit tests
4. **Build Frontend**: Builds Angular application
5. **Test Frontend**: Runs Karma/Jasmine tests
6. **Package**: Creates deployable artifacts
7. **Deploy**: Deploys to target environment
8. **Rollback**: Automatic rollback on failure
9. **Notify**: Sends notifications via email/Slack

### Trigger a Build

#### Manual Trigger
1. Go to Jenkins dashboard
2. Click on `ecommerce-pipeline`
3. Click "Build Now"

#### Automatic Trigger
- Push code to the repository
- Pipeline will trigger automatically via webhook or polling

### Parameterized Build

The pipeline supports parameters:
- `ENVIRONMENT`: dev, staging, production
- `SKIP_TESTS`: true/false
- `DEPLOY_BACKEND`: true/false
- `DEPLOY_FRONTEND`: true/false

To use:
1. Click "Build with Parameters"
2. Select desired options
3. Click "Build"

### Monitor Build

- View console output for real-time logs
- Check test reports in build artifacts
- Review deployment status

## Deployment

### Deployment Environments

- **Development**: Auto-deploy on every commit to `develop` branch
- **Staging**: Auto-deploy on every commit to `staging` branch
- **Production**: Manual approval required for `main` branch

### Rollback Strategy

If deployment fails:
1. Pipeline automatically stops
2. Previous version is restored from backup
3. Notifications are sent
4. Manual intervention may be required

### Manual Rollback

```bash
# SSH to deployment server
ssh user@deployment-server

# Navigate to deployment directory
cd /opt/ecommerce

# Run rollback script
./rollback.sh
```

## Environment Variables

Create `.env` file in project root:

```env
# Database
DB_HOST=localhost
DB_PORT=5432
DB_NAME=ecommerce
DB_USER=admin
DB_PASSWORD=password

# Services
PRODUCT_SERVICE_PORT=8081
ORDER_SERVICE_PORT=8082
USER_SERVICE_PORT=8083

# Frontend
FRONTEND_PORT=4200
API_BASE_URL=http://localhost:8080

# Jenkins
JENKINS_URL=http://localhost:8080
JENKINS_USER=admin
JENKINS_TOKEN=your-token

# Email
SMTP_HOST=smtp.gmail.com
SMTP_PORT=587
SMTP_USER=your-email@gmail.com
SMTP_PASSWORD=your-app-password

# Slack (optional)
SLACK_WEBHOOK_URL=your-webhook-url
```

## Troubleshooting

### Jenkins Issues

**Problem**: Jenkins container won't start
```bash
# Check logs
docker logs jenkins

# Restart Jenkins
docker-compose restart jenkins
```

**Problem**: Pipeline fails at checkout
- Verify Git credentials are configured
- Check repository URL is correct
- Ensure Jenkins has network access to Git server

**Problem**: Build fails with "Maven not found"
- Go to Manage Jenkins → Global Tool Configuration
- Verify Maven is configured correctly

### Application Issues

**Problem**: Service won't start
```bash
# Check if port is already in use
netstat -tulpn | grep <port>

# Kill process using port
kill -9 <pid>
```

**Problem**: Tests failing
```bash
# Clean and rebuild
mvn clean install

# Run tests with debug
mvn test -X
```

**Problem**: Frontend build fails
```bash
# Clear cache
npm cache clean --force

# Reinstall dependencies
rm -rf node_modules package-lock.json
npm install
```

### Deployment Issues

**Problem**: Deployment fails
- Check deployment server is accessible
- Verify SSH credentials are correct
- Ensure sufficient disk space on server
- Check application logs

**Problem**: Rollback needed
```bash
# Use Jenkins rollback stage
# Or manually SSH and run rollback script
ssh user@server './rollback.sh'
```

## Security Best Practices

1. **Never commit sensitive data** (passwords, tokens, keys)
2. **Use Jenkins credentials** for all sensitive information
3. **Enable CSRF protection** in Jenkins
4. **Use HTTPS** for Jenkins in production
5. **Regularly update** Jenkins and plugins
6. **Implement role-based access control** (RBAC)
7. **Scan for vulnerabilities** in dependencies

## Monitoring and Logs

### View Application Logs

```bash
# Docker logs
docker logs product-service
docker logs order-service
docker logs user-service
docker logs frontend

# Or follow logs
docker logs -f product-service
```

### Jenkins Logs

```bash
# Jenkins container logs
docker logs jenkins

# Pipeline console output
# Available in Jenkins UI under build → Console Output
```

## Performance Optimization

- Use distributed builds for parallel execution
- Cache Maven dependencies
- Use Docker layer caching
- Implement incremental builds
- Optimize test execution time

## Contributing

1. Create a feature branch
2. Make changes
3. Run tests locally
4. Commit and push
5. Create pull request
6. Wait for CI/CD pipeline to pass
7. Request review

## License

MIT License

