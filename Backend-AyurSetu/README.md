# AyurSetu Backend - Microservices Architecture

This directory contains all the backend microservices for the AyurSetu healthcare platform.

## 🏗️ Microservices Architecture

### Services Overview
- **User-Service** - User management, authentication, and profile management
- **Doctor-Service** - Doctor profiles, specializations, availability, and reviews
- **Pharmacy-Service** - Medicine inventory and prescription management
- **Appointment-Service** - Appointment booking and scheduling
- **Payment-Service** - Secure payment processing and transaction management
- **API-Gateway** - Spring Cloud Gateway with centralized routing and security

## 🛠️ Technology Stack

- **Spring Boot 3.5.4** - Microservices framework
- **Spring Security** - Authentication and authorization
- **JPA/Hibernate** - Database ORM
- **MySQL** - Primary database
- **Redis** - Caching and rate limiting
- **Apache Kafka** - Event-driven messaging
- **Spring Cloud Gateway** - API Gateway
- **Docker** - Containerization
- **JWT** - JSON Web Tokens for authentication

## 📁 Directory Structure

```
backend/
├── User-Service/           # User management microservice
├── Doctor-Service/         # Doctor management microservice
├── Pharmacy-Service/       # Pharmacy management microservice
├── Appointment-Service/    # Appointment booking microservice
├── Payment-Service/        # Payment processing microservice
├── API-Gateway/           # Spring Cloud Gateway
├── docker-compose.yml     # Docker orchestration
└── README.md              # This file
```

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.6+
- Docker and Docker Compose
- MySQL 8.0+
- Redis 6.0+
- Apache Kafka 2.8+

### Running with Docker Compose
```bash
# Start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop all services
docker-compose down
```

### Running Individual Services
```bash
# User Service
cd User-Service
mvn spring-boot:run

# Doctor Service
cd Doctor-Service
mvn spring-boot:run

# API Gateway
cd API-Gateway
mvn spring-boot:run
```

## 📚 API Documentation

### Service Ports
- **API Gateway**: 8080
- **User Service**: 8081
- **Doctor Service**: 8082
- **Pharmacy Service**: 8083
- **Appointment Service**: 8084
- **Payment Service**: 8085

### API Endpoints

#### User Service (Port: 8081)
- `POST /api/users/register` - User registration
- `POST /api/users/login` - User authentication
- `GET /api/users/profile` - Get user profile
- `PUT /api/users/profile` - Update user profile

#### Doctor Service (Port: 8082)
- `POST /api/doctors/register` - Doctor registration
- `GET /api/doctors` - Get all doctors
- `GET /api/doctors/{id}` - Get doctor by ID
- `GET /api/doctors/specialization/{id}` - Get doctors by specialization
- `POST /api/doctors/{id}/reviews` - Add doctor review

#### API Gateway (Port: 8080)
- All services are accessible through the gateway
- Rate limiting and authentication are handled centrally

## 🔐 Security

- JWT-based authentication
- Role-based access control (USER, DOCTOR, ADMIN)
- Password encryption using BCrypt
- API rate limiting with Redis
- CORS configuration

## 🧪 Testing

### Running Tests
```bash
# Unit tests
mvn test

# Integration tests
mvn verify

# End-to-end tests
mvn spring-boot:run -Dspring.profiles.active=test
```

## 📊 Monitoring

- Application metrics using Spring Boot Actuator
- Centralized logging
- Health check endpoints
- Circuit breaker monitoring

## 🚀 Deployment

### Docker Deployment
```bash
# Build Docker images
docker-compose build

# Deploy to production
docker-compose -f docker-compose.prod.yml up -d
```

### Kubernetes Deployment
```bash
# Apply Kubernetes manifests
kubectl apply -f k8s/
```

## 🔄 Inter-Service Communication

### Synchronous Communication
- **WebClient** - For direct service-to-service calls
- **Load Balancing** - Using Spring Cloud LoadBalancer

### Asynchronous Communication
- **Apache Kafka** - Event-driven messaging
- **Event Sourcing** - For audit trails and data consistency

## 📝 Environment Variables

### Common Variables
- `SPRING_PROFILES_ACTIVE` - Active profile (dev, docker, prod)
- `SPRING_DATASOURCE_URL` - Database connection URL
- `SPRING_REDIS_HOST` - Redis host
- `SPRING_KAFKA_BOOTSTRAP_SERVERS` - Kafka bootstrap servers

### Service-Specific Variables
- `JWT_SECRET` - JWT signing secret
- `EMAIL_HOST` - SMTP host for email service
- `PAYMENT_GATEWAY_KEY` - Payment gateway API key

## 🤝 Contributing

1. Follow microservices best practices
2. Write comprehensive tests
3. Update API documentation
4. Follow coding standards
5. Add proper logging and monitoring

---

For more details about individual services, see their respective README files. 