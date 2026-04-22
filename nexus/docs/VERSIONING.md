# Version Management Guide

## Snapshot vs Release Versions

### Snapshot Versions
- Development versions with `-SNAPSHOT` suffix
- Can be redeployed multiple times
- Stored in `maven-snapshots` repository
- Maven always checks for latest snapshot

### Release Versions
- Production-ready versions without `-SNAPSHOT`
- Immutable (cannot be redeployed)
- Stored in `maven-releases` repository
- Follow semantic versioning: MAJOR.MINOR.PATCH

## Version Lifecycle

```bash
# Development phase
mvn versions:set -DnewVersion=1.0.0-SNAPSHOT
mvn deploy

# Release
mvn versions:set -DnewVersion=1.0.0
mvn clean deploy

# Next development cycle
mvn versions:set -DnewVersion=1.1.0-SNAPSHOT
```

## Retrieve Specific Versions

```xml
<dependency>
    <groupId>com.nexus.demo</groupId>
    <artifactId>nexus-web-app</artifactId>
    <version>1.0.0</version>
</dependency>
```

## List Available Versions

```bash
curl -u admin:password \
  "http://localhost:8081/service/rest/v1/search?name=nexus-web-app"
```
