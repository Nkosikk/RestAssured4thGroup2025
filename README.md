# REST Assured API Testing Project - 4th Group 2025

This project demonstrates API testing using REST Assured framework for automated testing of HTTP services. The project includes comprehensive login API testing with both positive and negative test scenarios.

## 🎯 Project Overview

This REST Assured test suite validates the login functionality of the Ndosi Automation API. The tests cover:
- Successful login with valid credentials
- Error handling with invalid credentials
- Response validation using multiple approaches (TestNG assertions and Hamcrest matchers)
- Comprehensive logging and reporting

## 🛠️ Technology Stack

- **Java**: 21
- **Maven**: Build and dependency management
- **REST Assured**: 5.4.0 - API testing framework
- **TestNG**: 7.8.0 - Testing framework
- **Jackson**: 2.15.2 - JSON processing
- **JSON Path**: 5.4.0 - JSON path expressions

## 📁 Project Structure

```
RestAssured4thGroup2025/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/
    │   └── resources/
    └── test/
        ├── java/
        │   └── Basics.LoginTest.java
        └── resources/
            └── testng.xml
```

## 🚀 Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6.0 or higher
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

### Installation

1. **Clone the repository**:
   ```bash
   git clone <your-repository-url>
   cd RestAssured4thGroup2025
   ```

2. **Install dependencies**:
   ```bash
   mvn clean install
   ```

3. **Compile the project**:
   ```bash
   mvn compile test-compile
   ```

## 🧪 Test Cases

### Basics.LoginTest.java

The main test class contains three comprehensive test methods:

#### 1. `testLoginWithValidCredentials()`
- **Purpose**: Tests successful login with valid user credentials
- **Method**: POST request to `/API/login`
- **Payload**: 
  ```json
  {
    "email": "nkosi@gmail.com",
    "password": "12345678"
  }
  ```
- **Expected**: HTTP 200, successful login response with user data and JWT token
- **Validation**: Response status, user information, and authentication token

#### 2. `testLoginWithValidCredentialsUsingHamcrestMatchers()`
- **Purpose**: Alternative validation approach using REST Assured's built-in Hamcrest matchers
- **Method**: Same login request with different assertion style
- **Features**: Fluent API validation, automatic logging

#### 3. `testLoginWithInvalidCredentials()`
- **Purpose**: Tests error handling with invalid credentials
- **Payload**: Invalid email and password combination
- **Expected**: HTTP 401/400/403, error message response
- **Validation**: Proper error status codes and error messages

## 🏃‍♂️ Running Tests

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=Basics.LoginTest
```

### Run Specific Test Method
```bash
mvn test -Dtest=Basics.LoginTest#testLoginWithValidCredentials
```

### Run with TestNG XML Configuration
```bash
mvn test -DsuiteXmlFile=src/test/resources/testng.xml
```

## 📊 Test Results

### Successful Test Execution Example:
```
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

### Sample API Response (Valid Login):
```json
{
    "success": true,
    "message": "Login successful",
    "data": {
        "user": {
            "id": "ac1d63a3-1ec9-4a1b-a272-3173a6cf7ba2",
            "firstName": "Nkosingiphile",
            "lastName": "Cele",
            "email": "nkosi@gmail.com",
            "createdAt": "2025-09-25 16:24:44",
            "updatedAt": "2025-09-25 16:24:44"
        },
        "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9..."
    }
}
```

## 🔧 Configuration

### API Endpoint
- **Base URL**: `https://www.ndosiautomation.co.za`
- **Login Endpoint**: `/API/login`
- **Method**: POST
- **Content-Type**: `application/json`

### Test Configuration (testng.xml)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<suite name="LoginTestSuite">
    <test name="LoginTests">
        <classes>
            <class name="Basics.LoginTest"/>
        </classes>
    </test>
</suite>
```

## 📈 Features

### REST Assured Capabilities Demonstrated:
- ✅ HTTP POST requests with JSON payload
- ✅ Request/Response header management
- ✅ Response status code validation
- ✅ JSON response body parsing and validation
- ✅ Multiple assertion approaches (TestNG + Hamcrest)
- ✅ Comprehensive logging and debugging
- ✅ Error handling and negative testing
- ✅ Base URI configuration
- ✅ Content-Type header setting

### Testing Best Practices:
- ✅ Separation of positive and negative test cases
- ✅ Detailed response logging for debugging
- ✅ Clear test method naming conventions
- ✅ Proper assertions with meaningful error messages
- ✅ TestNG framework integration
- ✅ Maven project structure

## 🐛 Troubleshooting

### Common Issues:

1. **Dependencies not resolved**:
   ```bash
   mvn clean install -U
   ```

2. **SSL/TLS issues**:
   - Add system property: `-Dhttps.protocols=TLSv1.2`

3. **Network connectivity**:
   - Verify API endpoint accessibility
   - Check firewall/proxy settings

## 📚 Additional Resources

- [REST Assured Documentation](https://rest-assured.io/)
- [TestNG Documentation](https://testng.org/doc/)
- [Maven Documentation](https://maven.apache.org/guides/)
- [Hamcrest Matchers Guide](http://hamcrest.org/JavaHamcrest/)

## 👥 Contributors

- **Nkosingiphile Cele** - Project Developer
- **4th Group 2025** - Development Team

## 📄 License

This project is created for educational and testing purposes.

---

**Project Created**: October 20, 2025  
**Last Updated**: October 20, 2025  
**Framework Version**: REST Assured 5.4.0
