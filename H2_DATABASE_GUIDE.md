# H2 Database Configuration for Testing

## Overview
H2 is an in-memory relational database perfect for testing. It's lightweight, fast, and doesn't require a running database server.

---

## 📁 Setup Files Created

### 1. **pom.xml** (Updated)
Added H2 dependency with `test` scope:
```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>test</scope>
</dependency>
```

### 2. **application-test.properties** (New)
Test-specific configuration file with H2 settings:
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
```

### 3. **Springdemoe2eApplicationTests.java** (Updated)
Enhanced test class with:
- `@ActiveProfiles("test")` - Activates test profile
- `@AutoConfigureMockMvc` - Auto-configures MockMvc for testing
- 5 comprehensive integration tests

---

## 🔑 Key Configuration Properties

### **Database Connection**
```properties
spring.datasource.url=jdbc:h2:mem:testdb      # In-memory database
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa                  # Default SA user
spring.datasource.password=                    # No password needed
```

### **Schema Generation**
```properties
spring.jpa.hibernate.ddl-auto=create-drop     # Creates tables on startup, drops on shutdown
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.show-sql=true                      # Shows executed SQL queries
```

### **H2 Console (Optional)**
```properties
spring.h2.console.enabled=true                # Access at http://localhost:8080/h2-console
spring.h2.console.path=/h2-console
```

---

## ✅ Available Tests

### **Test 1: Context Loads**
```java
void contextLoads()
```
Verifies the application starts successfully with H2 database.

### **Test 2: Get All Songs (Success)**
```java
void testGetAllSongs()
```
- **URL:** `GET /api/songs`
- **Expected:** 200 OK with success message

### **Test 3: Get Song by ID (Success)**
```java
void testGetSongById_Success()
```
- **URL:** `GET /api/songs/5`
- **Expected:** 200 OK with song details

### **Test 4: Get Song by ID (Invalid ID)**
```java
void testGetSongById_InvalidId()
```
- **URL:** `GET /api/songs/0`
- **Expected:** 400 Bad Request with validation error

### **Test 5: Get Song by ID (Not Found)**
```java
void testGetSongById_NotFound()
```
- **URL:** `GET /api/songs/150`
- **Expected:** 404 Not Found with resource error

### **Test 6: Invalid Endpoint**
```java
void testInvalidEndpoint()
```
- **URL:** `GET /api/invalid-endpoint`
- **Expected:** 404 Not Found with endpoint error

---

## 🚀 Running Tests

### **Run All Tests**
```bash
mvn test
```

### **Run Specific Test**
```bash
mvn test -Dtest=Springdemoe2eApplicationTests
```

### **Run Specific Test Method**
```bash
mvn test -Dtest=Springdemoe2eApplicationTests#testGetAllSongs
```

### **Run with Verbose Output**
```bash
mvn test -X
```

---

## 🔍 How H2 Works for Testing

1. **In-Memory Database**: H2 creates tables in RAM, not on disk
2. **Fast**: No I/O overhead, tests run quickly
3. **Isolated**: Each test gets a fresh database (due to `create-drop` DDL mode)
4. **Self-Contained**: No external database needed

### **Data Flow During Tests**
```
Test Starts
    ↓
H2 Database Created in Memory
    ↓
DDL-Auto Creates Tables
    ↓
Test Executes (MockMvc makes HTTP calls)
    ↓
Assertions Check Response
    ↓
Test Ends
    ↓
Database Destroyed (dropped)
```

---

## 🔧 Creating Your Own Tests

### **Example: Testing with Data Setup**
```java
@SpringBootTest
@ActiveProfiles("test")
class MyServiceTests {
    
    @Autowired
    private SongRepository songRepository;
    
    @BeforeEach
    void setup() {
        // Insert test data
        Song song = new Song();
        song.setId(1L);
        song.setName("Test Song");
        songRepository.save(song);
    }
    
    @Test
    void testFindSong() {
        Song found = songRepository.findById(1L).orElse(null);
        assertNotNull(found);
        assertEquals("Test Song", found.getName());
    }
}
```

---

## 📊 Test Profile vs Production Profile

| Feature | Test (H2) | Production (MySQL) |
|---------|-----------|-------------------|
| Database | In-Memory | MySQL Server |
| DDL Auto | create-drop | validate/update |
| Console | Enabled | Disabled |
| Performance | Very Fast | Network Dependent |
| Data Persistence | Temporary | Permanent |
| Startup Time | < 1 second | ~5-10 seconds |

---

## 🛠️ File Locations

```
springdemoe2e/
├── pom.xml                                    # Updated with H2 dependency
├── src/
│   ├── main/
│   │   └── resources/
│   │       └── application.properties         # Production config (MySQL)
│   └── test/
│       ├── java/
│       │   └── Springdemoe2eApplicationTests.java    # Updated tests
│       └── resources/
│           └── application-test.properties    # Test config (H2)
```

---

## 🎯 Profile Activation

### **Activate Test Profile**
- **Using Annotation:** `@ActiveProfiles("test")`
- **Using Command Line:** `mvn test -Dspring.profiles.active=test`
- **Using Environment Variable:** `SPRING_PROFILES_ACTIVE=test`

When test profile is active, Spring loads `application-test.properties` instead of `application.properties`.

---

## 📝 Common Issues & Solutions

### **Issue: "Driver not found for H2"**
**Solution:** Ensure H2 dependency is added to pom.xml with scope=test

### **Issue: "Table not found"**
**Solution:** Set `spring.jpa.hibernate.ddl-auto=create-drop` to auto-create tables

### **Issue: Tests pass but data persists**
**Solution:** Use `create-drop` mode instead of `update` to clear data between tests

### **Issue: Port already in use**
**Solution:** H2 in-memory database doesn't use ports (unlike MySQL)

---

## ✨ Advantages of H2 for Testing

✅ **No Database Server Required** - Runs in JVM process  
✅ **Fast** - In-memory operations are quick  
✅ **Isolation** - Each test has its own database  
✅ **Easy Setup** - Just add dependency, no configuration needed  
✅ **Drop-In Replacement** - Compatible with SQL standards  
✅ **Debugging** - H2 Console for inspecting test data  


