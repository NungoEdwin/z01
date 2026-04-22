# Nexus Artifact Management System

A comprehensive project demonstrating artifact management using Nexus Repository Manager with Spring Boot, Maven, Docker, and CI/CD integration.

## Table of Contents
- [Project Overview](#project-overview)
- [Prerequisites](#prerequisites)
- [Nexus Repository Manager Setup](#nexus-repository-manager-setup)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [Building and Deploying](#building-and-deploying)
- [Docker Integration](#docker-integration)
- [CI/CD Pipeline](#cicd-pipeline)
- [Versioning](#versioning)
- [Security and Access Control](#security-and-access-control)
- [Usage Examples](#usage-examples)

## Project Overview

This project demonstrates:
- Nexus Repository Manager installation and configuration
- Spring Boot web application with Maven
- Artifact publishing to Nexus (JARs/WARs)
- Dependency management through Nexus proxy
- Docker image management in Nexus
- Automated CI/CD pipeline
- Version management
- Security and access control

## Prerequisites

- Java 11 or higher
- Maven 3.6+ (compatible with Java 11)
- Docker
- Nexus Repository Manager OSS 3.x
- Git

## Nexus Repository Manager Setup

### 1. Download and Install Nexus

```bash
# Download Nexus Repository Manager
cd /opt
sudo wget https://download.sonatype.com/nexus/3/latest-unix.tar.gz
sudo tar -xvzf latest-unix.tar.gz
sudo mv nexus-3* nexus
```

### 2. Create Nexus User (Non-Root)

```bash
# Create nexus user
sudo adduser nexus
sudo chown -R nexus:nexus /opt/nexus
sudo chown -R nexus:nexus /opt/sonatype-work
```

### 3. Configure Nexus to Run as Nexus User

```bash
# Edit nexus.rc file
sudo nano /opt/nexus/bin/nexus.rc

# Add this line:
run_as_user="nexus"
```

### 4. Create Systemd Service

```bash
sudo nano /etc/systemd/system/nexus.service
```

Add the following content:

```ini
[Unit]
Description=Nexus Repository Manager
After=network.target

[Service]
Type=forking
LimitNOFILE=65536
User=nexus
Group=nexus
ExecStart=/opt/nexus/bin/nexus start
ExecStop=/opt/nexus/bin/nexus stop
Restart=on-abort

[Install]
WantedBy=multi-user.target
```

### 5. Start Nexus Service

```bash
sudo systemctl daemon-reload
sudo systemctl enable nexus
sudo systemctl start nexus
sudo systemctl status nexus
```

### 6. Access Nexus

- URL: `http://localhost:8081`
- Default credentials: admin / (check `/opt/sonatype-work/nexus3/admin.password`)

![Nexus Login Screen](docs/images/nexus-login.png)

### 7. Create Repositories

#### Maven Hosted Repositories

1. **maven-releases** (already exists by default)
   - Type: hosted
   - Version policy: Release
   - Deployment policy: Disable redeploy

2. **maven-snapshots** (already exists by default)
   - Type: hosted
   - Version policy: Snapshot
   - Deployment policy: Allow redeploy

#### Docker Hosted Repository

1. Navigate to: Settings → Repositories → Create repository
2. Select: docker (hosted)
3. Configure:
   - Name: `docker-hosted`
   - HTTP port: `8082`
   - Enable Docker V1 API: ✓
   - Deployment policy: Allow redeploy

![Repository Creation](docs/images/create-repository.png)

#### Maven Proxy Repository

1. Create repository → maven2 (proxy)
2. Configure:
   - Name: `maven-central-proxy`
   - Remote storage: `https://repo1.maven.org/maven2/`
   - Auto-blocking: Enabled

#### Maven Group Repository

The default `maven-public` group should include:
- maven-releases
- maven-snapshots
- maven-central-proxy

![Repository List](docs/images/repository-list.png)

## Project Structure

```
nexus/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/nexus/demo/
│   │   │       ├── NexusWebApplication.java
│   │   │       └── controller/
│   │   │           └── HomeController.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/nexus/demo/
│               └── NexusWebApplicationTests.java
├── pom.xml
├── Dockerfile
├── Jenkinsfile
├── maven-settings.xml
├── .github/
│   └── workflows/
│       └── ci-cd.yml
└── README.md
```

## Configuration

### Maven Settings (maven-settings.xml)

Configure Maven to use Nexus as a mirror and for authentication:

```bash
# Copy maven-settings.xml to your Maven config directory
cp maven-settings.xml ~/.m2/settings.xml

# Set environment variables
export NEXUS_USERNAME=admin
export NEXUS_PASSWORD=your-password
```

### POM Configuration

The `pom.xml` includes:
- **distributionManagement**: Defines where to publish artifacts
- **repositories**: Configures Nexus as dependency source
- **pluginRepositories**: Configures Nexus for Maven plugins

### Application Configuration

Update Nexus URLs in configuration files if using remote server:
- `pom.xml`: Update repository URLs
- `maven-settings.xml`: Update mirror and repository URLs
- `.github/workflows/ci-cd.yml`: Update NEXUS_URL
- `Jenkinsfile`: Update NEXUS_URL

## Building and Deploying

### Local Build

```bash
# Build the project
mvn clean package

# Run tests
mvn test

# Deploy to Nexus
mvn deploy
```

### Verify Artifact in Nexus

1. Navigate to: Browse → maven-snapshots (or maven-releases)
2. Find: `com/nexus/demo/nexus-web-app/1.0.0-SNAPSHOT/`

![Artifact in Nexus](docs/images/artifact-published.png)

### Run Application Locally

```bash
# Run the WAR file
java -jar target/nexus-web-app-1.0.0-SNAPSHOT.war

# Or use Maven
mvn spring-boot:run
```

Access the application:
- Home: http://localhost:8080/
- Version: http://localhost:8080/version
- Health: http://localhost:8080/health

## Docker Integration

### Build Docker Image

```bash
# Build the application first
mvn clean package

# Build Docker image
docker build -t nexus-web-app:1.0.0 .
```

### Push to Nexus Docker Registry

```bash
# Login to Nexus Docker registry
docker login localhost:8082 -u admin -p your-password

# Tag the image
docker tag nexus-web-app:1.0.0 localhost:8082/nexus-web-app:1.0.0
docker tag nexus-web-app:1.0.0 localhost:8082/nexus-web-app:latest

# Push to Nexus
docker push localhost:8082/nexus-web-app:1.0.0
docker push localhost:8082/nexus-web-app:latest
```

### Verify Docker Image in Nexus

Navigate to: Browse → docker-hosted → nexus-web-app

![Docker Image in Nexus](docs/images/docker-image.png)

### Pull and Run from Nexus

```bash
# Pull from Nexus
docker pull localhost:8082/nexus-web-app:latest

# Run container
docker run -d -p 8080:8080 localhost:8082/nexus-web-app:latest
```

## CI/CD Pipeline

### GitHub Actions

The project includes a GitHub Actions workflow (`.github/workflows/ci-cd.yml`) that:
1. Checks out code
2. Sets up JDK 11
3. Builds with Maven
4. Runs tests
5. Publishes artifacts to Nexus
6. Builds Docker image
7. Pushes Docker image to Nexus

#### Setup GitHub Secrets

Add these secrets to your GitHub repository:
- `NEXUS_USERNAME`: Your Nexus username
- `NEXUS_PASSWORD`: Your Nexus password

![GitHub Secrets](docs/images/github-secrets.png)

### Jenkins Pipeline

The `Jenkinsfile` provides a Jenkins pipeline with similar stages.

#### Setup Jenkins

1. Install required plugins:
   - Maven Integration
   - Docker Pipeline
   - Credentials Binding

2. Configure tools:
   - JDK 11 (name: `JDK-11`)
   - Maven 3.8+ (name: `Maven-3.8`)

3. Add Nexus credentials:
   - ID: `nexus-credentials`
   - Type: Username with password

![Jenkins Pipeline](docs/images/jenkins-pipeline.png)

## Versioning

### Snapshot Versions

Development versions use `-SNAPSHOT` suffix:
```xml
<version>1.0.0-SNAPSHOT</version>
```

Snapshots can be redeployed multiple times and are stored in `maven-snapshots` repository.

### Release Versions

For production releases:

```bash
# Update version in pom.xml
<version>1.0.0</version>

# Build and deploy
mvn clean deploy
```

Releases are immutable and stored in `maven-releases` repository.

### Version Management Example

```bash
# Deploy snapshot
mvn deploy  # Deploys 1.0.0-SNAPSHOT

# Create release
mvn versions:set -DnewVersion=1.0.0
mvn clean deploy

# Start next development cycle
mvn versions:set -DnewVersion=1.1.0-SNAPSHOT
```

### Retrieve Specific Versions

```xml
<!-- In another project's pom.xml -->
<dependency>
    <groupId>com.nexus.demo</groupId>
    <artifactId>nexus-web-app</artifactId>
    <version>1.0.0</version>
</dependency>
```

![Version History](docs/images/version-history.png)

## Security and Access Control

### User Management

1. Navigate to: Settings → Security → Users
2. Create users with appropriate roles

![User Management](docs/images/user-management.png)

### Role-Based Access Control

#### Create Custom Role

1. Navigate to: Settings → Security → Roles → Create role
2. Configure privileges:
   - `nx-repository-view-maven2-maven-releases-read`
   - `nx-repository-view-maven2-maven-releases-browse`
   - `nx-repository-view-maven2-maven-releases-add`

Example roles:
- **Developer**: Read/write to snapshots, read from releases
- **Release Manager**: Full access to releases
- **CI/CD**: Automated deployment access

![Role Configuration](docs/images/role-config.png)

### Repository-Level Permissions

#### Restrict Access to Specific Repositories

1. Create role with specific repository privileges
2. Assign role to users

Example: Restrict production releases
```
Role: production-deployer
Privileges:
- nx-repository-view-maven2-maven-releases-*
- nx-repository-admin-maven2-maven-releases-*
```

### Content Selectors

Create content selectors for fine-grained access:

1. Navigate to: Settings → Security → Content Selectors
2. Create selector:
   - Name: `com-nexus-demo-only`
   - Expression: `coordinate.groupId =~ "^com\\.nexus\\.demo.*"`

![Content Selector](docs/images/content-selector.png)

### Anonymous Access

Disable anonymous access for security:

1. Navigate to: Settings → Security → Anonymous Access
2. Uncheck "Allow anonymous users to access the server"

### Realms Configuration

Enable authentication realms:
- Local Authenticating Realm (default)
- LDAP (optional)
- Docker Bearer Token Realm (for Docker)

![Realms Configuration](docs/images/realms-config.png)

### SSL/TLS Configuration

For production, configure HTTPS:

```bash
# Generate keystore
keytool -genkeypair -keystore keystore.jks -storepass password -alias nexus \
  -keyalg RSA -keysize 2048 -validity 365

# Configure in nexus.properties
sudo nano /opt/nexus/etc/nexus.properties

# Add:
application-port-ssl=8443
nexus-args=${jetty.etc}/jetty.xml,${jetty.etc}/jetty-https.xml
```

## Usage Examples

### Example 1: Dependency Resolution

Create a new Maven project that uses artifacts from Nexus:

```xml
<project>
    <repositories>
        <repository>
            <id>nexus-proxy</id>
            <url>http://localhost:8081/repository/maven-public/</url>
        </repository>
    </repositories>
    
    <dependencies>
        <dependency>
            <groupId>com.nexus.demo</groupId>
            <artifactId>nexus-web-app</artifactId>
            <version>1.0.0-SNAPSHOT</version>
        </dependency>
    </dependencies>
</project>
```

### Example 2: Download Artifact via REST API

```bash
# Download specific version
curl -u admin:password -O \
  http://localhost:8081/repository/maven-releases/com/nexus/demo/nexus-web-app/1.0.0/nexus-web-app-1.0.0.war

# Download latest snapshot
curl -u admin:password -O \
  http://localhost:8081/repository/maven-snapshots/com/nexus/demo/nexus-web-app/1.0.0-SNAPSHOT/nexus-web-app-1.0.0-SNAPSHOT.war
```

### Example 3: Search Artifacts

```bash
# Search by groupId
curl -u admin:password \
  "http://localhost:8081/service/rest/v1/search?group=com.nexus.demo"

# Search by name
curl -u admin:password \
  "http://localhost:8081/service/rest/v1/search?name=nexus-web-app"
```

### Example 4: Docker Multi-Stage Build

```dockerfile
FROM maven:3.8-openjdk-11 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:11-jre-slim
COPY --from=build /app/target/*.war app.war
ENTRYPOINT ["java", "-jar", "app.war"]
```

## Troubleshooting

### Common Issues

**Issue**: Cannot connect to Nexus
```bash
# Check Nexus status
sudo systemctl status nexus

# Check logs
tail -f /opt/sonatype-work/nexus3/log/nexus.log
```

**Issue**: Authentication failed
- Verify credentials in `maven-settings.xml`
- Check environment variables are set
- Verify user has correct permissions in Nexus

**Issue**: Docker push fails
- Ensure Docker Bearer Token Realm is enabled
- Verify Docker repository HTTP port is accessible
- Check firewall rules

## Best Practices

1. **Use Snapshots for Development**: Keep `-SNAPSHOT` suffix during development
2. **Immutable Releases**: Never redeploy release versions
3. **Semantic Versioning**: Follow MAJOR.MINOR.PATCH format
4. **Cleanup Policies**: Configure automatic cleanup for old snapshots
5. **Backup**: Regularly backup `/opt/sonatype-work/nexus3`
6. **Security**: Use HTTPS in production, disable anonymous access
7. **Resource Limits**: Allocate sufficient memory (min 4GB for Nexus)

## Cleanup Policies

Configure automatic cleanup:

1. Navigate to: Settings → Repository → Cleanup Policies
2. Create policy:
   - Name: `cleanup-snapshots`
   - Criteria: Last downloaded > 30 days
   - Apply to: maven-snapshots

## Monitoring

Monitor Nexus health:

```bash
# Check system status
curl -u admin:password http://localhost:8081/service/rest/v1/status

# Check system health
curl -u admin:password http://localhost:8081/service/rest/v1/status/check
```

## License

This project is for educational purposes.

## Support

For issues and questions:
- Nexus Documentation: https://help.sonatype.com/repomanager3
- Spring Boot Documentation: https://spring.io/projects/spring-boot
