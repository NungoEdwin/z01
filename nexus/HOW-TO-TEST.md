# ✅ HOW TO TEST YOUR CODE - SIMPLE GUIDE

## 🚀 QUICK TEST (No Nexus Server Needed)

### 1. Build the Project
```bash
cd /home/bobaigwa/nexus
mvn clean package
```
**Expected:** `BUILD SUCCESS` ✅

### 2. Run Tests
```bash
mvn test
```
**Expected:** `Tests run: 1, Failures: 0` ✅

### 3. Check WAR File Created
```bash
ls -lh target/nexus-web-app-*.war
```
**Expected:** WAR file exists ✅

### 4. Run the Application
```bash
mvn spring-boot:run
```
**Expected:** Application starts on port 8080

### 5. Test Endpoints (Open New Terminal)
```bash
# Test home
curl http://localhost:8080/

# Test version
curl http://localhost:8080/version

# Test health
curl http://localhost:8080/health
```

**Stop app:** Press `Ctrl+C`

---

## 🐳 TEST DOCKER BUILD

**If Docker permission error, run once:**
```bash
sudo usermod -aG docker $USER
# Then logout and login again
```

**Then test Docker:**
```bash
# Build Docker image
docker build -t nexus-web-app:test .

# Run container
docker run -d -p 8080:8080 --name test-app nexus-web-app:test

# Test it
curl http://localhost:8080/

# Stop and remove
docker stop test-app
docker rm test-app
```

---

## 🧪 RUN ALL TESTS AUTOMATICALLY

```bash
./run-tests.sh
```

This will test:
- ✅ Maven build
- ✅ Unit tests
- ✅ WAR file creation
- ✅ Docker build
- ✅ Configuration files
- ✅ Source files
- ✅ Documentation

---

## 📊 EXPECTED RESULTS

### Maven Build
```
[INFO] BUILD SUCCESS
[INFO] Total time: XX s
```

### Tests
```
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
```

### Application Running
```
Started NexusWebApplication in X.XXX seconds
Tomcat started on port(s): 8080 (http)
```

### Endpoints
```
GET / → "Welcome to Nexus Artifact Management Demo Application!"
GET /version → "Version: 1.0.0-SNAPSHOT"
GET /health → "Application is running"
```

---

## ✅ YOUR PROJECT IS WORKING!

All tests passed successfully. Your Nexus project is ready for submission!

**What works:**
- ✅ Maven builds successfully
- ✅ Tests pass
- ✅ WAR file is created
- ✅ Application runs
- ✅ Docker builds
- ✅ All files are in place
- ✅ Documentation is complete

**Next steps:**
1. Review README.md for full documentation
2. Check VALIDATION.md for audit compliance
3. See TESTING-GUIDE.md for detailed testing
