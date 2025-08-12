# AyurSetu - Complete Ayurvedic Healthcare Platform

<div align="center">
  <img src="https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java" alt="Java 17"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-3.3.12-green?style=for-the-badge&logo=spring" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/React-18-blue?style=for-the-badge&logo=react" alt="React 18"/>
  <img src="https://img.shields.io/badge/Docker-Enabled-blue?style=for-the-badge&logo=docker" alt="Docker"/>
  <img src="https://img.shields.io/badge/Microservices-Architecture-purple?style=for-the-badge" alt="Microservices"/>
</div>

## 🌟 Overview

AyurSetu is a comprehensive microservices-based platform that bridges the gap between patients and Ayurvedic healthcare providers. The platform offers online doctor consultations, pharmacy services, and a complete healthcare management system built with modern technologies.

## 🏗️ System Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Frontend      │    │   API Gateway   │    │   Eureka        │
│   (React)       │◄──►│   (Port 8080)   │◄──►│   (Port 8761)   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                │
                ┌───────────────┼───────────────┐
                │               │               │
        ┌───────▼──────┐ ┌──────▼──────┐ ┌─────▼──────┐
        │ User Service │ │Doctor Service│ │Consultation│
        │ (Port 8081)  │ │(Port 8082)  │ │(Port 8083)│
        └──────────────┘ └─────────────┘ └───────────┘
                │               │               │
        ┌───────▼──────┐ ┌──────▼──────┐ ┌─────▼──────┐
        │Pharmacy      │ │Notification │ │   MySQL    │
        │(Port 8084)   │ │(Port 8086)  │ │  Database  │
        └──────────────┘ └─────────────┘ └───────────┘
```

## 🚀 Features

### Core Healthcare Services
- **👥 User Management**: Registration, authentication, profile management
- **👨‍⚕️ Doctor Management**: Doctor profiles, specializations, availability
- **📅 Consultation Booking**: Video/audio calls, chat, in-person appointments
- **💊 Pharmacy**: Medicine catalog, prescription management, order processing
- **🔔 Real-time Notifications**: Email, SMS, and push notifications
- **📋 Prescription Management**: Digital prescriptions and medicine recommendations

### Technical Features
- **🏗️ Microservices Architecture**: Scalable and maintainable
- **🔍 Service Discovery**: Eureka for service registration
- **🚪 API Gateway**: Centralized routing and security
- **📡 Event-Driven Communication**: Kafka for asynchronous messaging
- **🗄️ Database**: MySQL with separate databases per service
- **🐳 Containerization**: Docker for easy deployment
- **🔒 Security**: JWT authentication, role-based access
- **📊 Monitoring**: Actuator endpoints for health checks

## 🛠️ Technology Stack

### Backend
- **Framework**: Spring Boot 3.3.12, Spring Cloud 2023.0.4
- **Language**: Java 17
- **Database**: MySQL 8.0
- **Message Broker**: Apache Kafka
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Security**: Spring Security, JWT
- **Build Tool**: Maven

### Frontend
- **Framework**: React 18
- **Routing**: React Router 6
- **UI Framework**: Bootstrap 5, React Bootstrap
- **HTTP Client**: Axios
- **State Management**: React Context API
- **Icons**: React Icons
- **Animations**: Framer Motion
- **Build Tool**: Create React App

### Infrastructure
- **Containerization**: Docker
- **Orchestration**: Docker Compose
- **Monitoring**: Spring Boot Actuator

## 📁 Project Structure

```
AyurSetu/
├── ayursetu-backend/           # Backend microservices
│   ├── api-gateway/           # API Gateway service
│   ├── consultation-service/   # Consultation management
│   ├── doctor-service/        # Doctor management
│   ├── eureka-service/        # Service discovery
│   ├── notification-service/  # Notification system
│   ├── pharmacy-service/      # Pharmacy management
│   ├── user-service/          # User management
│   ├── docker-compose.yml     # Docker orchestration
│   └── README.md             # Backend documentation
├── ayursetu-frontend/         # React frontend application
│   ├── src/
│   │   ├── components/        # Reusable UI components
│   │   ├── pages/            # Page components
│   │   ├── contexts/         # React contexts
│   │   └── services/         # API services
│   ├── package.json          # Frontend dependencies
│   └── README.md            # Frontend documentation
└── README.md                 # This file - Project overview
```

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Node.js 16 or higher
- Maven 3.6+
- Docker and Docker Compose
- MySQL 8.0 (if running locally)

### 1. Clone the Repository
```bash
git clone <repository-url>
cd AyurSetu
```

### 2. Start Backend Services
```bash
cd ayursetu-backend
docker-compose up -d
```

### 3. Start Frontend Application
```bash
cd ayursetu-frontend
npm install
npm start
```

### 4. Access the Application
- **Frontend**: http://localhost:3000
- **API Gateway**: http://localhost:8080
- **Eureka Dashboard**: http://localhost:8761

## 📚 Service Details

### Backend Services

| Service | Port | Description | Status |
|---------|------|-------------|---------|
| **Eureka Service** | 8761 | Service discovery and registration | ✅ Active |
| **API Gateway** | 8080 | Centralized routing and security | ✅ Active |
| **User Service** | 8081 | User management and authentication | ✅ Active |
| **Doctor Service** | 8082 | Doctor profiles and specializations | ✅ Active |
| **Consultation Service** | 8083 | Appointment and consultation management | ✅ Active |
| **Pharmacy Service** | 8084 | Medicine and order management | ✅ Active |
| **Notification Service** | 8086 | Email, SMS, and push notifications | ✅ Active |

### Frontend Components

| Component | Description | Status |
|-----------|-------------|---------|
| **Home Page** | Landing page with features and statistics | ✅ Active |
| **Authentication** | Login, registration, and password reset | ✅ Active |
| **Doctor Management** | Browse, search, and filter doctors | ✅ Active |
| **Consultation Booking** | Appointment scheduling system | ✅ Active |
| **Pharmacy** | Medicine catalog and ordering | ✅ Active |
| **Dashboard** | User dashboard and profile management | ✅ Active |

## 🔧 Configuration

### Environment Variables

#### Backend
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

#### Frontend
```env
REACT_APP_API_URL=http://localhost:8080
```

## 🐳 Docker Deployment

### Backend Services
```bash
cd ayursetu-backend
docker-compose up -d
```

### Frontend Application
```bash
cd ayursetu-frontend
docker build -t ayursetu-frontend .
docker run -p 3000:3000 ayursetu-frontend
```

## 🔒 Security Features

- **JWT Authentication**: Secure token-based authentication
- **Role-Based Access Control**: USER, DOCTOR, ADMIN roles
- **Password Encryption**: BCrypt hashing
- **CORS Configuration**: Cross-origin resource sharing
- **Input Validation**: Comprehensive data validation
- **SQL Injection Prevention**: Parameterized queries

## 📊 API Documentation

### Core Endpoints

#### User Management
- `POST /api/users/register` - User registration
- `POST /api/users/login` - User authentication
- `GET /api/users/{id}` - Get user profile
- `PUT /api/users/{id}` - Update user profile

#### Doctor Services
- `GET /api/doctors` - List all doctors
- `GET /api/doctors/{id}` - Get doctor details
- `GET /api/doctors/specialization/{specialization}` - Filter by specialization

#### Consultation Booking
- `POST /api/consultations` - Book consultation
- `GET /api/consultations/user/{userId}` - Get user consultations
- `PATCH /api/consultations/{id}/status` - Update status

#### Pharmacy
- `GET /api/pharmacy/medicines` - List medicines
- `POST /api/pharmacy/orders` - Create order
- `GET /api/pharmacy/orders/user/{userId}` - Get user orders

## 🧪 Testing

### Backend Testing
```bash
cd ayursetu-backend
mvn test
```

### Frontend Testing
```bash
cd ayursetu-frontend
npm test
```

## 📈 Performance & Scalability

- **Horizontal Scaling**: Microservices can scale independently
- **Load Balancing**: API Gateway distributes traffic
- **Connection Pooling**: HikariCP for database connections
- **Caching**: Redis-ready architecture
- **Message Batching**: Kafka optimization
- **Database Indexing**: Optimized queries

## 🔄 Development Workflow

### Backend Development
1. Create feature branch
2. Implement changes in relevant service
3. Add unit tests
4. Test with Docker Compose
5. Submit pull request

### Frontend Development
1. Create feature branch
2. Implement UI components
3. Test responsiveness
4. Integrate with backend APIs
5. Submit pull request

## 🤝 Contributing

We welcome contributions! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Development Guidelines
- Follow Java coding conventions for backend
- Use meaningful variable names and add comments
- Write unit tests for all business logic
- Ensure responsive design for frontend
- Test cross-browser compatibility

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support & Contact

- **GitHub Issues**: [Create an issue](https://github.com/your-repo/issues)
- **Documentation**: Check individual service READMEs
- **Backend Issues**: See [Backend README](ayursetu-backend/README.md)
- **Frontend Issues**: See [Frontend README](ayursetu-frontend/README.md)

## 🎯 Roadmap

### Current Version (v1.0.0)
- ✅ Complete microservices architecture
- ✅ User authentication and management
- ✅ Doctor consultation system
- ✅ Pharmacy management
- ✅ Notification system
- ✅ React frontend

### Future Versions
- 🔮 Payment processing service
- 🔮 Video consultation integration
- 🔮 AI-powered health recommendations
- 🔮 Mobile applications (iOS/Android)
- 🔮 Advanced analytics dashboard

## 🏆 Acknowledgments

- Spring Boot team for the excellent framework
- React team for the powerful frontend library
- Docker team for containerization technology
- Open source community for various libraries and tools

---

<div align="center">
  <strong>AyurSetu</strong> - Bridging Traditional Ayurveda with Modern Technology 🌿
  
  <p>Built with ❤️ for better healthcare accessibility</p>
</div>
