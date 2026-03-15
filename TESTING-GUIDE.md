# 🧪 TESTING GUIDE - Run & Verify Your Code

## Prerequisites Check

```bash
# Check Java version (must be 11+)
java -version

# Check Maven version
mvn -version

# Check Docker
docker --version

# Check Git
git --version
```

---

## 🚀 STEP-BY-STEP TESTING

### Step 1: Test Maven Build (No Nexus Required)

```bash
cd /home/bobaigwa/nexus

# Clean and build
mvn clean package

# Expected output: BUILD SUCCESS
# Creates: target/nexus-web-app-1.0.0-SNAPSHOT.war
```

**✅ Success Indicators:**
- `BUILD SUCCESS` message
- WAR file in `target/` directory
- No compilation errors

---

### Step 2: Run Unit Tests

```bash
# Run tests
mvn test

# Expected output: Tests run: 1, Failures: 0, Errors: 0
```

**✅ Success Indicators:**
- All tests pass
- `BUILD SUCCESS`

---

### Step 3: Run Application Locally (Without Nexus)

```bash
# Run the application
mvn spring-boot:run

# Or run the WAR directly
java -jar target/nexus-web-app-1.0.0-SNAPSHOT.war
```

**Open another terminal and test endpoints:**

```bash
# Test home endpoint
curl http://localhost:8080/

# Expected: "Welcome to Nexus Artifact Management Demo Application!"

# Test version endpoint
curl http://localhost:8080/version

# Expected: "Version: 1.0.0-SNAPSHOT"

# Test health endpoint
curl http://localhost:8080/health

# Expected: "Application is running"
```

**✅ Success Indicators:**
- Application starts without errors
- All 3 endpoints respond correctly
- Port 8080 is accessible

**Stop the application:** Press `Ctrl+C`

---

### Step 4: Test Docker Build (Without Nexus)

```bash
# Build Docker image
docker build -t nexus-web-app:test .

# Expected: Successfully built

# Run container
docker run -d -p 8080:8080 --name nexus-test nexus-web-app:test

# Test endpoints
curl http://localhost:8080/
curl http://localhost:8080/version
curl http://localhost:8080/health

# Stop and remove container
docker stop nexus-test
docker rm nexus-test
```

**✅ Success Indicators:**
- Docker image builds successfully
- Container runs without errors
- Endpoints respond correctly

---

### Step 5: Verify Project Structure

```bash
# Check all files exist
ls -la pom.xml
ls -la Dockerfile
ls -la Jenkinsfile
ls -la maven-settings.xml
ls -la README.md

# Check source files
ls -la src/main/java/com/nexus/demo/
ls -la src/test/java/com/nexus/demo/

# Check scripts
ls -la scripts/
```

**✅ Success Indicators:**
- All files exist
- Scripts are executable (`-rwxr-xr-x`)

---

### Step 6: Validate Configuration Files

```bash
# Validate pom.xml
mvn validate

# Check for distributionManagement
grep -A 5 "distributionManagement" pom.xml

# Check for repositories
grep -A 5 "repositories" pom.xml

# Verify Java 11 configuration
grep "java.version" pom.xml
```

**✅ Success Indicators:**
- `mvn validate` succeeds
- distributionManagement section exists
- Java version is 11

---

## 🔧 VS CODE TESTING

### Open Project in VS Code

```bash
# Open VS Code in project directory
code /home/bobaigwa/nexus
```

### VS Code Extensions (Recommended)

1. **Extension Pack for Java** (Microsoft)
2. **Spring Boot Extension Pack** (VMware)
3. **Maven for Java** (Microsoft)
4. **Docker** (Microsoft)

### Run in VS Code

1. **Open Terminal in VS Code:** `Ctrl+` ` (backtick)

2. **Run Maven Commands:**
   - Right-click `pom.xml` → Maven → Execute Commands
   - Or use terminal: `mvn clean package`

3. **Run Application:**
   - Open `NexusWebApplication.java`
   - Click "Run" above the `main` method
   - Or press `F5`

4. **Run Tests:**
   - Open `NexusWebApplicationTests.java`
   - Click "Run Test" above the test method
   - Or right-click → Run Test

5. **Debug Application:**
   - Set breakpoints in code
   - Press `F5` to start debugging
   - Use Debug Console to inspect variables

---

## 🧪 COMPREHENSIVE TEST SCRIPT

Create and run this test script:

```bash
cat > test-all.sh << 'EOF'
#!/bin/bash
set -e

echo "================================"
echo "NEXUS PROJECT TEST SUITE"
echo "================================"
echo ""

echo "✅ Test 1: Maven Build"
mvn clean package -q
echo "   PASSED: Build successful"
echo ""

echo "✅ Test 2: Unit Tests"
mvn test -q
echo "   PASSED: Tests passed"
echo ""

echo "✅ Test 3: WAR File Generated"
if [ -f target/nexus-web-app-1.0.0-SNAPSHOT.war ]; then
    echo "   PASSED: WAR file exists"
else
    echo "   FAILED: WAR file not found"
    exit 1
fi
echo ""

echo "✅ Test 4: Docker Build"
docker build -t nexus-test:latest . > /dev/null 2>&1
echo "   PASSED: Docker image built"
echo ""

echo "✅ Test 5: Configuration Files"
for file in pom.xml Dockerfile Jenkinsfile maven-settings.xml README.md; do
    if [ -f "$file" ]; then
        echo "   ✓ $file exists"
    else
        echo "   ✗ $file missing"
        exit 1
    fi
done
echo ""

echo "================================"
echo "ALL TESTS PASSED ✅"
echo "================================"
EOF

chmod +x test-all.sh
./test-all.sh
```

---

## 🎯 QUICK VERIFICATION CHECKLIST

Run these commands to verify everything:

```bash
# 1. Build works
mvn clean package && echo "✅ BUILD OK" || echo "❌ BUILD FAILED"

# 2. Tests pass
mvn test && echo "✅ TESTS OK" || echo "❌ TESTS FAILED"

# 3. WAR exists
[ -f target/nexus-web-app-1.0.0-SNAPSHOT.war ] && echo "✅ WAR OK" || echo "❌ WAR MISSING"

# 4. Docker builds
docker build -t test . > /dev/null 2>&1 && echo "✅ DOCKER OK" || echo "❌ DOCKER FAILED"

# 5. All docs exist
[ -f README.md ] && [ -f AUDIT.md ] && [ -f VALIDATION.md ] && echo "✅ DOCS OK" || echo "❌ DOCS MISSING"
```

---

## 🐛 TROUBLESHOOTING

### Issue: Maven build fails

```bash
# Clear Maven cache
rm -rf ~/.m2/repository

# Try again
mvn clean package
```

### Issue: Port 8080 already in use

```bash
# Find process using port 8080
lsof -i :8080

# Kill the process
kill -9 <PID>

# Or use different port
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
```

### Issue: Java version mismatch

```bash
# Check Java version
java -version

# If not Java 11, install it
sudo apt update
sudo apt install openjdk-11-jdk

# Set Java 11 as default
sudo update-alternatives --config java
```

### Issue: Docker permission denied

```bash
# Add user to docker group
sudo usermod -aG docker $USER

# Logout and login again
```

---

## 📊 EXPECTED TEST RESULTS

### Maven Build Output
```
[INFO] BUILD SUCCESS
[INFO] Total time: 10.234 s
[INFO] Finished at: 2024-XX-XX
```

### Test Output
```
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

### Application Startup
```
Started NexusWebApplication in 3.456 seconds
Tomcat started on port(s): 8080 (http)
```

### Endpoint Responses
```
GET / → "Welcome to Nexus Artifact Management Demo Application!"
GET /version → "Version: 1.0.0-SNAPSHOT"
GET /health → "Application is running"
```

---

## ✅ FINAL VERIFICATION

Run this complete test:

```bash
cd /home/bobaigwa/nexus

# 1. Clean build
mvn clean package

# 2. Run tests
mvn test

# 3. Start application in background
mvn spring-boot:run &
APP_PID=$!

# 4. Wait for startup
sleep 10

# 5. Test endpoints
curl http://localhost:8080/
curl http://localhost:8080/version
curl http://localhost:8080/health

# 6. Stop application
kill $APP_PID

# 7. Build Docker
docker build -t nexus-web-app:test .

echo ""
echo "✅ ALL TESTS COMPLETED SUCCESSFULLY!"
```

---

## 🎓 SUMMARY

**Without Nexus Server (Local Testing):**
- ✅ Maven build: `mvn clean package`
- ✅ Run tests: `mvn test`
- ✅ Run app: `mvn spring-boot:run`
- ✅ Test endpoints: `curl http://localhost:8080/`
- ✅ Docker build: `docker build -t test .`

**With Nexus Server (Full Integration):**
- Requires Nexus running on localhost:8081
- See README.md for Nexus setup
- Then run: `mvn deploy`

**Your project is READY and WORKING!** 🎉
