# AyurSetu Platform - Online Doctor Consultation & Pharmacy

A comprehensive microservices-based platform for online doctor consultations and pharmacy management built with Spring Boot and Docker.

## 🏗️ Architecture Overview

The platform consists of the following microservices:

### ✅ **Currently Implemented Services:**
- **Eureka Service** (Port: 8761) - Service Discovery
- **API Gateway** (Port: 8080) - Centralized routing and security
- **User Service** (Port: 8081) - User management and authentication
- **Doctor Service** (Port: 8082) - Doctor management and profiles
- **Consultation Service** (Port: 8083) - Appointment and consultation management
- **Pharmacy Service** (Port: 8084) - Medicine and order management
- **Notification Service** (Port: 8086) - Email, SMS, and push notifications

### 🔮 **Future Implementation:**
- **Payment Service** (Port: 8085) - Payment processing (to be implemented)

## 🚀 Features

### Core Features
- **User Management**: Registration, authentication, profile management
- **Doctor Management**: Doctor profiles, specializations, availability
- **Consultation Booking**: Video/audio calls, chat, in-person appointments
- **Pharmacy**: Medicine catalog, prescription management, order processing
- **Real-time Notifications**: Email, SMS, and push notifications
- **Prescription Management**: Digital prescriptions and medicine recommendations

### 🔮 **Future Features:**
- **Payment Processing**: Multiple payment methods, secure transactions (to be implemented)

### Technical Features
- **Microservices Architecture**: Scalable and maintainable
- **Service Discovery**: Eureka for service registration
- **API Gateway**: Centralized routing and security
- **Event-Driven Communication**: Kafka for asynchronous messaging
- **Database**: MySQL with separate databases per service
- **Containerization**: Docker for easy deployment
- **Docker Ready**: For production deployment
- **Security**: JWT authentication, role-based access
- **Monitoring**: Actuator endpoints for health checks

## 🛠️ Technology Stack

- **Backend**: Spring Boot 3.3.12, Spring Cloud 2023.0.4
- **Database**: MySQL 8.0
- **Message Broker**: Apache Kafka
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Security**: Spring Security, JWT
- **Containerization**: Docker
- **Orchestration**: Docker Compose
- **Build Tool**: Maven
- **Language**: Java 17

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker and Docker Compose
- MySQL 8.0 (if running locally)
- Apache Kafka (if running locally)

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone <repository-url>
cd ayursetu-backend
```

### 2. Build All Services
```bash
mvn clean install -DskipTests
```

### 3. Start with Docker Compose
```bash
docker-compose up -d
```

### 4. Access Services
- **Eureka Dashboard**: http://localhost:8761
- **API Gateway**: http://localhost:8080
- **User Service**: http://localhost:8081
- **Doctor Service**: http://localhost:8082
- **Consultation Service**: http://localhost:8083
- **Pharmacy Service**: http://localhost:8084
- **Notification Service**: http://localhost:8086

**Note**: Payment Service (Port 8085) will be implemented in the future.

## 📚 API Documentation

### User Service APIs
- `POST /api/users/register` - User registration
- `POST /api/users/login` - User login
- `GET /api/users/{id}` - Get user profile
- `PUT /api/users/{id}` - Update user profile

### Doctor Service APIs
- `POST /api/doctors` - Create doctor profile
- `GET /api/doctors` - Get all doctors
- `GET /api/doctors/{id}` - Get doctor by ID
- `GET /api/doctors/specialization/{specialization}` - Get doctors by specialization
- `GET /api/doctors/available` - Get available doctors

### Consultation Service APIs
- `POST /api/consultations` - Book consultation
- `GET /api/consultations/user/{userId}` - Get user consultations
- `GET /api/consultations/doctor/{doctorId}` - Get doctor consultations
- `PATCH /api/consultations/{id}/status` - Update consultation status

### Pharmacy Service APIs
- `GET /api/pharmacy/medicines` - Get all medicines
- `GET /api/pharmacy/medicines/{id}` - Get medicine by ID
- `POST /api/pharmacy/orders` - Create order
- `GET /api/pharmacy/orders/user/{userId}` - Get user orders

## 🔧 Configuration

### Environment Variables
Each service can be configured using environment variables:

```yaml
# Database Configuration
SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/userdb
SPRING_DATASOURCE_USERNAME: root
SPRING_DATASOURCE_PASSWORD: root1234

# Kafka Configuration
SPRING_KAFKA_BOOTSTRAP_SERVERS: kafka:9092

# Eureka Configuration
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE: http://eureka-service:8761/eureka/
```

### Database Configuration
The platform uses separate databases for each service:
- `userdb` - User Service
- `doctordb` - Doctor Service
- `consultationdb` - Consultation Service
- `pharmacydb` - Pharmacy Service
- `notificationdb` - Notification Service

**Note**: `paymentdb` will be created when Payment Service is implemented.

## 🐳 Docker Deployment

### Build Images
```bash
# Build all services
docker-compose build

# Build specific service
docker-compose build user-service
```

### Run Services
```bash
# Start all services
docker-compose up -d

# Start specific service
docker-compose up -d user-service

# View logs
docker-compose logs -f

# Stop services
docker-compose down
```

## 🐳 Docker Compose Deployment

### Prerequisites
- Docker and Docker Compose installed
- At least 4GB RAM available

### Deploy with Docker Compose
```bash
# Start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down

# Rebuild and restart
docker-compose up -d --build
```

**Note**: Kubernetes deployment files have been removed to simplify the setup. The platform is optimized for Docker Compose deployment.

## 🔒 Security

### Authentication
- JWT-based authentication
- Role-based access control (USER, DOCTOR, ADMIN)
- Password encryption using BCrypt

### API Security
- CORS configuration
- Input validation
- SQL injection prevention
- XSS protection

## 📊 Monitoring & Health Checks

### Actuator Endpoints
Each service exposes health check endpoints:
- `GET /actuator/health` - Service health
- `GET /actuator/info` - Service information
- `GET /actuator/metrics` - Service metrics

### Logging
- Structured logging with SLF4J
- Log levels configurable per service
- Centralized log aggregation (ELK stack ready)

## 🧪 Testing

### Unit Tests
```bash
# Run all tests
mvn test

# Run tests for specific service
mvn test -pl user-service
```

### Integration Tests
```bash
# Run integration tests
mvn verify
```

## 📈 Performance

### Optimization Features
- Connection pooling with HikariCP
- JPA batch processing
- Kafka message batching
- Caching with Redis (optional)
- Database indexing

### Scalability
- Horizontal scaling with Kubernetes
- Load balancing with API Gateway
- Database read replicas
- Microservices can scale independently

## 🚨 Error Handling

### Global Exception Handling
- Centralized error handling
- Consistent error responses
- Proper HTTP status codes
- Detailed error logging

### Circuit Breaker Pattern
- Resilience4j for fault tolerance
- Fallback mechanisms
- Service degradation handling

## 🔄 Event-Driven Architecture

### Kafka Topics
- `user-events` - User-related events
- `doctor-events` - Doctor-related events
- `consultation-events` - Consultation events
- `pharmacy-events` - Pharmacy events
- `notification-events` - Notification events

**Note**: `payment-events` topic will be added when Payment Service is implemented.

## 📝 Development Guidelines

### Code Style
- Follow Java coding conventions
- Use meaningful variable names
- Add comprehensive comments
- Write unit tests for all business logic

### Git Workflow
- Feature branches for new development
- Pull requests for code review
- Semantic versioning
- Conventional commits

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

For support and questions:
- Create an issue in the repository
- Contact the development team
- Check the documentation

## 🔄 Version History

- **v1.0.0** - Initial release with core microservices (User, Doctor, Consultation, Pharmacy, Notification)
- **v1.1.0** - Planned: Payment Service implementation
- Future versions will include additional features and improvements

---

## 🎯 **Current Status**

✅ **All core services are fully operational and ready for production use!**

### **Working Services:**
- ✅ Eureka Service Discovery
- ✅ API Gateway
- ✅ User Service
- ✅ Doctor Service  
- ✅ Consultation Service
- ✅ Pharmacy Service
- ✅ Notification Service

### **Infrastructure:**
- ✅ MySQL Database (all databases created)
- ✅ Apache Kafka (message broker)
- ✅ Docker Compose orchestration

**Note**: This is a comprehensive microservices platform. Make sure to review security configurations and update credentials before production deployment. The Payment Service will be implemented in future versions.
