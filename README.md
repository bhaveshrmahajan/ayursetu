# AyurSetu - Online Doctor Consultation & Pharmacy Platform

A comprehensive microservices-based healthcare platform enabling online doctor consultations, pharmacy services, and appointment management.

## 🏥 Project Overview

AyurSetu is a full-stack healthcare platform that connects patients with doctors for online consultations, manages pharmacy services, and handles appointment scheduling with secure payment processing.

## 🏗️ Architecture

### Microservices Architecture
- **User Service** - User management, authentication, and profile management
- **Doctor Service** - Doctor profiles, specializations, availability, and reviews
- **Pharmacy Service** - Medicine inventory and prescription management
- **Appointment Service** - Appointment booking and scheduling
- **Payment Service** - Secure payment processing and transaction management
- **API Gateway** - Centralized routing, authentication, and rate limiting

### Technology Stack
- **Backend:** Spring Boot 3.5.4, Spring Security, JPA/Hibernate, Java 17
- **Database:** MySQL, Redis (caching and rate limiting)
- **Messaging:** Apache Kafka (event-driven communication)
- **API Gateway:** Spring Cloud Gateway
- **Frontend:** React.js, Material-UI, Redux
- **Containerization:** Docker, Docker Compose
- **Authentication:** JWT (JSON Web Tokens)

## 🚀 Features

### Core Features
- User registration and authentication with JWT
- Doctor profile management and specialization handling
- Real-time appointment booking and scheduling
- Pharmacy inventory management
- Secure payment processing
- Review and rating system
- Role-based access control

### Technical Features
- Microservices architecture with service discovery
- Event-driven communication using Kafka
- Circuit breaker pattern for fault tolerance
- Redis-based rate limiting and caching
- Comprehensive testing suite
- Docker containerization
- API Gateway with centralized security

## 📁 Project Structure

```
AyurSetu Backend/
├── User-Service/           # User management microservice
├── Doctor-Service/         # Doctor management microservice
├── Pharmacy-Service/       # Pharmacy management microservice
├── Appointment-Service/    # Appointment booking microservice
├── Payment-Service/        # Payment processing microservice
├── API-Gateway/           # Spring Cloud Gateway
└── docker-compose.yml     # Docker orchestration
```

## 🛠️ Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+
- Apache Kafka 2.8+
- Docker and Docker Compose
- Node.js 16+ (for frontend)

## 🔧 Setup Instructions

### 1. Clone the Repository
```bash
git clone <repository-url>
cd AyurSetu-Backend
```

### 2. Database Setup
```bash
# Start MySQL and Redis using Docker
docker-compose up -d mysql redis kafka
```

### 3. Build and Run Services
```bash
# Build all services
mvn clean install

# Run individual services
cd User-Service && mvn spring-boot:run
cd Doctor-Service && mvn spring-boot:run
# ... repeat for other services
```

### 4. Using Docker Compose
```bash
# Build and run all services
docker-compose up --build
```

## 📚 API Documentation

### User Service (Port: 8081)
- `POST /api/users/register` - User registration
- `POST /api/users/login` - User authentication
- `GET /api/users/profile` - Get user profile
- `PUT /api/users/profile` - Update user profile

### Doctor Service (Port: 8082)
- `POST /api/doctors/register` - Doctor registration
- `GET /api/doctors` - Get all doctors
- `GET /api/doctors/{id}` - Get doctor by ID
- `GET /api/doctors/specialization/{id}` - Get doctors by specialization
- `POST /api/doctors/{id}/reviews` - Add doctor review

### API Gateway (Port: 8080)
- All services are accessible through the gateway
- Rate limiting and authentication are handled centrally

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

## 🔐 Security

- JWT-based authentication
- Role-based access control (USER, DOCTOR, ADMIN)
- Password encryption using BCrypt
- API rate limiting
- CORS configuration

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

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Team

- **Backend Development:** [Your Name]
- **Frontend Development:** [Your Name]
- **DevOps:** [Your Name]

## 📞 Support

For support and questions, please contact:
- Email: [your-email@example.com]
- GitHub Issues: [Create an issue](https://github.com/yourusername/ayursetu-backend/issues)

---

**AyurSetu** - Bridging the gap between patients and healthcare providers through technology. 