# AyurSetu - Healthcare Microservices Platform

AyurSetu is a comprehensive healthcare platform built with microservices architecture, providing online doctor consultation and pharmacy services.

## 🏥 Project Overview

AyurSetu is a modern healthcare platform that connects patients with doctors for online consultations and provides pharmacy services. The platform is built using Spring Boot microservices architecture for scalability and maintainability.

## 🏗️ Architecture

The project follows a microservices architecture with the following services:

### Backend Services

1. **API Gateway** (`api-gateway`)
   - Central entry point for all client requests
   - Route management and load balancing
   - Authentication and authorization

2. **User Service** (`user-service`)
   - User registration and authentication
   - Profile management
   - JWT token management
   - Password reset functionality

3. **Doctor Service** (`doctor-service`)
   - Doctor registration and profile management
   - Specialization management
   - Doctor search and filtering

4. **Consultation Service** (`consultation-service`)
   - Appointment booking and management
   - Consultation scheduling
   - Doctor-patient communication

5. **Pharmacy Service** (`pharmacy-service`)
   - Medicine catalog management
   - Order processing
   - Inventory management

6. **Notification Service** (`notification-service`)
   - Email notifications
   - SMS notifications
   - Push notifications

7. **Eureka Service** (`eureka-service`)
   - Service discovery and registration
   - Load balancing

### Database
- **MySQL** for data persistence
- Separate databases for each service

## 🚀 Technology Stack

- **Backend**: Spring Boot, Spring Cloud
- **Database**: MySQL
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Authentication**: JWT
- **Containerization**: Docker
- **Build Tool**: Maven

## 📁 Project Structure

```
ayursetu-backend/
├── api-gateway/           # API Gateway Service
├── user-service/          # User Management Service
├── doctor-service/        # Doctor Management Service
├── consultation-service/   # Consultation Service
├── pharmacy-service/      # Pharmacy Service
├── notification-service/   # Notification Service
├── eureka-service/        # Service Discovery
├── mysql/                 # Database initialization
└── docker-compose.yml     # Docker orchestration
```

## 🛠️ Prerequisites

- Java 11 or higher
- Maven 3.6+
- Docker and Docker Compose
- MySQL 8.0+

## 🚀 Quick Start

### Using Docker Compose (Recommended)

1. **Clone the repository**
   ```bash
   git clone https://github.com/bhaveshrmahajan/ayursetu.git
   cd ayursetu
   ```

2. **Start all services**
   ```bash
   cd ayursetu-backend
   docker-compose up -d
   ```

3. **Access the application**
   - API Gateway: http://localhost:8080
   - Eureka Dashboard: http://localhost:8761
   - User Service: http://localhost:8081
   - Doctor Service: http://localhost:8082
   - Consultation Service: http://localhost:8083
   - Pharmacy Service: http://localhost:8084
   - Notification Service: http://localhost:8085

### Manual Setup

1. **Start MySQL Database**
   ```bash
   docker run --name mysql-ayursetu -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=ayursetu -p 3306:3306 -d mysql:8.0
   ```

2. **Start Eureka Service**
   ```bash
   cd ayursetu-backend/eureka-service
   mvn spring-boot:run
   ```

3. **Start Individual Services**
   ```bash
   # Start each service in separate terminals
   cd ayursetu-backend/user-service && mvn spring-boot:run
   cd ayursetu-backend/doctor-service && mvn spring-boot:run
   cd ayursetu-backend/consultation-service && mvn spring-boot:run
   cd ayursetu-backend/pharmacy-service && mvn spring-boot:run
   cd ayursetu-backend/notification-service && mvn spring-boot:run
   cd ayursetu-backend/api-gateway && mvn spring-boot:run
   ```

## 📚 API Documentation

### User Service APIs
- `POST /api/users/register` - User registration
- `POST /api/users/login` - User login
- `GET /api/users/profile` - Get user profile
- `PUT /api/users/profile` - Update user profile

### Doctor Service APIs
- `POST /api/doctors` - Register doctor
- `GET /api/doctors` - Get all doctors
- `GET /api/doctors/{id}` - Get doctor by ID
- `GET /api/specializations` - Get all specializations

### Consultation Service APIs
- `POST /api/consultations` - Book consultation
- `GET /api/consultations` - Get consultations
- `GET /api/consultations/{id}` - Get consultation by ID

### Pharmacy Service APIs
- `GET /api/medicines` - Get all medicines
- `POST /api/orders` - Place order
- `GET /api/orders` - Get orders

## 🔧 Configuration

Each service has its own configuration in `application.yml`:
- Database connections
- Service discovery settings
- JWT configurations
- Email/SMS service configurations

## 🐳 Docker Support

Each service includes a Dockerfile for containerization:

```bash
# Build individual services
docker build -t ayursetu-user-service ./user-service
docker build -t ayursetu-doctor-service ./doctor-service
# ... repeat for other services

# Run with docker-compose
docker-compose up -d
```

## 🧪 Testing

Run tests for each service:
```bash
cd ayursetu-backend/user-service && mvn test
cd ayursetu-backend/doctor-service && mvn test
# ... repeat for other services
```

## 📝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🤝 Support

For support and questions:
- Create an issue on GitHub
- Contact: bhaveshrmahajan

## 🔄 Version History

- **v1.0.0** - Initial release with core microservices
  - User management
  - Doctor management
  - Consultation booking
  - Pharmacy services
  - Notification system

---

**AyurSetu** - Connecting Healthcare with Technology 🏥💻

