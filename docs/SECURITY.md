# Nexus Security and Access Control Guide

## User Authentication

### Create Users
1. Navigate to: Settings → Security → Users
2. Click "Create local user"
3. Configure user details and assign roles

### Disable Anonymous Access
1. Settings → Security → Anonymous Access
2. Uncheck "Allow anonymous users to access the server"

## Role-Based Access Control (RBAC)

### Built-in Roles
- **nx-admin**: Full administrative access
- **nx-developer**: Development access
- **nx-anonymous**: Anonymous user access

### Create Custom Roles

#### Developer Role
```
Privileges:
- nx-repository-view-maven2-maven-snapshots-*
- nx-repository-view-maven2-maven-releases-read
- nx-repository-view-maven2-maven-releases-browse
```

#### Release Manager Role
```
Privileges:
- nx-repository-view-maven2-maven-releases-*
- nx-repository-admin-maven2-maven-releases-*
```

#### CI/CD Role
```
Privileges:
- nx-repository-view-maven2-*-*
- nx-repository-view-docker-*-*
```

## Repository-Level Permissions

### Content Selectors

Create fine-grained access control:

1. Navigate to: Settings → Security → Content Selectors
2. Create selector with CSEL expression

**Examples:**
```
# Specific groupId
coordinate.groupId == "com.nexus.demo"

# GroupId pattern
coordinate.groupId =~ "^com\\.nexus\\..*"

# Only releases (no snapshots)
coordinate.version !~ ".*SNAPSHOT"
```

### Assign Privileges

1. Create privilege with content selector
2. Assign to role
3. Assign role to users

## Authentication Realms

Enable required realms:
- **Local Authenticating Realm**: Default
- **Docker Bearer Token Realm**: Required for Docker
- **LDAP Realm**: For LDAP integration (optional)

Navigate to: Settings → Security → Realms

## SSL/TLS Configuration

### Generate Certificate
```bash
keytool -genkeypair \
  -keystore /opt/nexus/etc/ssl/keystore.jks \
  -storepass changeit \
  -alias nexus \
  -keyalg RSA \
  -keysize 2048 \
  -validity 365
```

### Configure HTTPS
Edit `/opt/nexus/etc/nexus.properties`:
```properties
application-port-ssl=8443
nexus-args=${jetty.etc}/jetty.xml,${jetty.etc}/jetty-https.xml
```

## Token-Based Authentication

### Create User Token
1. Login as user
2. Click username → "User Token"
3. Click "Access user token"
4. Use token in Maven settings

## Security Best Practices

1. Change default admin password immediately
2. Disable anonymous access in production
3. Use HTTPS/SSL for production
4. Implement least privilege access
5. Regular security audits
6. Use strong passwords
7. Enable audit logging
8. Regular backups
9. Keep Nexus updated
10. Use service accounts for CI/CD

## Audit Logging

Logs location: `/opt/sonatype-work/nexus3/log/audit/audit.log`

View logs:
```bash
tail -f /opt/sonatype-work/nexus3/log/audit/audit.log
```

## Backup and Recovery

### Backup
```bash
sudo systemctl stop nexus
sudo tar -czf nexus-backup-$(date +%Y%m%d).tar.gz /opt/sonatype-work/nexus3
sudo systemctl start nexus
```

### Restore
```bash
sudo systemctl stop nexus
sudo tar -xzf nexus-backup-YYYYMMDD.tar.gz -C /
sudo chown -R nexus:nexus /opt/sonatype-work/nexus3
sudo systemctl start nexus
```
