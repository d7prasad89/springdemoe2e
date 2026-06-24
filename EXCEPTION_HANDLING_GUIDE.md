# Global Exception Handling Guide

## Overview
I've implemented a comprehensive global exception handling system for your Spring Boot REST API. This ensures that when anything goes wrong, users receive custom error messages instead of default error pages.

---

## 📁 Files Created

### 1. **ResourceNotFoundException.java** 
Custom exception for when a requested resource is not found in the database.

### 2. **ErrorResponse.java**
A standardized error response object with the following properties:
- `status` - HTTP status code
- `message` - Detailed error message
- `error` - Error type/category
- `timestamp` - When the error occurred
- `path` - The API endpoint that was called

### 3. **GlobalExceptionHandler.java**
Spring's `@ControllerAdvice` that intercepts all exceptions globally and returns custom responses.

**Handles:**
- `ResourceNotFoundException` → 404 Not Found
- `NoHandlerFoundException` → 404 for invalid endpoints
- `IllegalArgumentException` → 400 Bad Request
- `Exception` (all other errors) → 500 Internal Server Error

### 4. **Updated SongController.java**
Enhanced with examples showing how to throw exceptions

### 5. **Updated application.properties**
Added configuration to enable proper error handling

---

## 🧪 Testing the Global Exception Handler

### Test 1: Valid Request (Success)
```
GET http://localhost:8080/api/songs
GET http://localhost:8080/api/songs/5
```

**Expected Response:**
```json
{
  "id": "5",
  "name": "Song 5",
  "artist": "Artist 5"
}
```

---

### Test 2: Invalid Song ID (IllegalArgumentException)
```
GET http://localhost:8080/api/songs/0
```

**Expected Response:**
```json
{
  "status": 400,
  "message": "Song ID must be a positive number",
  "error": "Invalid Argument",
  "timestamp": "2026-06-24T10:30:45.123",
  "path": "/api/songs/0"
}
```

---

### Test 3: Song Not Found (ResourceNotFoundException)
```
GET http://localhost:8080/api/songs/150
```

**Expected Response:**
```json
{
  "status": 404,
  "message": "Song with ID 150 not found in database",
  "error": "Resource Not Found",
  "timestamp": "2026-06-24T10:30:45.123",
  "path": "/api/songs/150"
}
```

---

### Test 4: Invalid Endpoint (NoHandlerFoundException)
```
GET http://localhost:8080/api/invalid-endpoint
```

**Expected Response:**
```json
{
  "status": 404,
  "message": "The requested endpoint does not exist: http://localhost:8080/api/invalid-endpoint",
  "error": "Endpoint Not Found",
  "timestamp": "2026-06-24T10:30:45.123",
  "path": "http://localhost:8080/api/invalid-endpoint"
}
```

---

## 🔧 How to Use in Your Own Endpoints

### Throwing Custom Exceptions

```java
@GetMapping("/{id}")
public ResponseEntity<YourObject> getById(@PathVariable Long id) {
    
    // Validation check
    if (id == null || id <= 0) {
        throw new IllegalArgumentException("ID must be positive");
    }
    
    // Check if resource exists
    YourObject object = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Resource with ID " + id + " not found"
        ));
    
    return ResponseEntity.ok(object);
}
```

---

## 📋 Configuration Applied

In `application.properties`, the following properties were added:

```properties
# Show error messages in response
server.error.include-message=always

# Include binding errors
server.error.include-binding-errors=always

# Stack trace only on demand (add ?trace=true to URL)
server.error.include-stacktrace=on_param

# Don't include exception details by default
server.error.include-exception=false

# Throw exception for missing mappings (instead of sending 404 page)
spring.mvc.throw-exception-if-no-handler-found=true

# Disable static resource mapping
spring.web.resources.add-mappings=false
```

---

## ✅ Benefits of This Approach

1. **Consistency** - All errors follow the same format
2. **User-Friendly** - Custom messages instead of generic error pages
3. **Centralized** - Single place to manage all error handling
4. **Maintainable** - Easy to add new exception handlers
5. **Debugging** - Includes timestamps and paths for debugging
6. **Flexible** - Can customize responses per exception type

---

## 🚀 To Extend Further

To add more exception handlers, simply add methods to `GlobalExceptionHandler.java`:

```java
@ExceptionHandler(YourCustomException.class)
public ResponseEntity<ErrorResponse> handleYourException(
        YourCustomException ex, 
        WebRequest request) {
    
    ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.YOUR_STATUS.value(),
            ex.getMessage(),
            "Your Error Type",
            LocalDateTime.now(),
            request.getDescription(false).replace("uri=", "")
    );
    
    return new ResponseEntity<>(errorResponse, HttpStatus.YOUR_STATUS);
}
```

---

## 📝 Notes

- The `@ControllerAdvice` annotation makes this handler global - it catches exceptions from all `@RestController` classes
- `@ExceptionHandler` methods are processed in order, so more specific exceptions should be listed first
- The `LocalDateTime.now()` automatically includes timezone information
- Use appropriate HTTP status codes (400, 404, 500, etc.) for different scenarios

