#!/bin/bash

GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

echo -e "${BLUE}================================${NC}"
echo -e "${BLUE}NEXUS PROJECT TEST SUITE${NC}"
echo -e "${BLUE}================================${NC}"
echo ""

FAILED=0

# Test 1: Maven Build
echo -e "${YELLOW}Test 1: Maven Build${NC}"
if mvn clean package -q -DskipTests; then
    echo -e "${GREEN}✅ PASSED: Build successful${NC}"
else
    echo -e "${RED}❌ FAILED: Build failed${NC}"
    FAILED=1
fi
echo ""

# Test 2: Unit Tests
echo -e "${YELLOW}Test 2: Unit Tests${NC}"
if mvn test -q; then
    echo -e "${GREEN}✅ PASSED: Tests passed${NC}"
else
    echo -e "${RED}❌ FAILED: Tests failed${NC}"
    FAILED=1
fi
echo ""

# Test 3: WAR File
echo -e "${YELLOW}Test 3: WAR File Generated${NC}"
if [ -f target/nexus-web-app-1.0.0-SNAPSHOT.war ]; then
    SIZE=$(ls -lh target/nexus-web-app-1.0.0-SNAPSHOT.war | awk '{print $5}')
    echo -e "${GREEN}✅ PASSED: WAR file exists ($SIZE)${NC}"
else
    echo -e "${RED}❌ FAILED: WAR file not found${NC}"
    FAILED=1
fi
echo ""

# Test 4: Docker Build
echo -e "${YELLOW}Test 4: Docker Build${NC}"
if docker ps > /dev/null 2>&1; then
    if docker build -t nexus-test:latest . > /dev/null 2>&1; then
        echo -e "${GREEN}✅ PASSED: Docker image built${NC}"
        docker rmi nexus-test:latest > /dev/null 2>&1
    else
        echo -e "${RED}❌ FAILED: Docker build failed${NC}"
        FAILED=1
    fi
else
    echo -e "${YELLOW}⚠️  SKIPPED: Docker not accessible (run: sudo usermod -aG docker $USER)${NC}"
fi
echo ""

# Test 5: Configuration Files
echo -e "${YELLOW}Test 5: Configuration Files${NC}"
FILES="pom.xml Dockerfile Jenkinsfile maven-settings.xml README.md"
ALL_EXIST=1
for file in $FILES; do
    if [ -f "$file" ]; then
        echo -e "   ${GREEN}✓${NC} $file"
    else
        echo -e "   ${RED}✗${NC} $file"
        ALL_EXIST=0
    fi
done
if [ $ALL_EXIST -eq 1 ]; then
    echo -e "${GREEN}✅ PASSED: All config files exist${NC}"
else
    echo -e "${RED}❌ FAILED: Some files missing${NC}"
    FAILED=1
fi
echo ""

# Test 6: Source Files
echo -e "${YELLOW}Test 6: Source Files${NC}"
if [ -f src/main/java/com/nexus/demo/NexusWebApplication.java ] && \
   [ -f src/main/java/com/nexus/demo/controller/HomeController.java ] && \
   [ -f src/test/java/com/nexus/demo/NexusWebApplicationTests.java ]; then
    echo -e "${GREEN}✅ PASSED: All source files exist${NC}"
else
    echo -e "${RED}❌ FAILED: Some source files missing${NC}"
    FAILED=1
fi
echo ""

# Test 7: Documentation
echo -e "${YELLOW}Test 7: Documentation${NC}"
DOC_COUNT=$(find . -name "*.md" ! -path "./.qodo/*" | wc -l)
if [ $DOC_COUNT -ge 8 ]; then
    echo -e "${GREEN}✅ PASSED: Documentation complete ($DOC_COUNT files)${NC}"
else
    echo -e "${RED}❌ FAILED: Insufficient documentation${NC}"
    FAILED=1
fi
echo ""

# Final Result
echo -e "${BLUE}================================${NC}"
if [ $FAILED -eq 0 ]; then
    echo -e "${GREEN}ALL TESTS PASSED ✅${NC}"
    echo -e "${GREEN}Project is ready for submission!${NC}"
    exit 0
else
    echo -e "${RED}SOME TESTS FAILED ❌${NC}"
    echo -e "${YELLOW}Please fix the issues above${NC}"
    exit 1
fi
echo -e "${BLUE}================================${NC}"
