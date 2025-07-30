# AyurSetu - Online Doctor Consultation & Pharmacy Platform

A comprehensive full-stack healthcare platform enabling online doctor consultations, pharmacy services, and appointment management.

## 🏥 Project Overview

AyurSetu is a full-stack healthcare platform that connects patients with doctors for online consultations, manages pharmacy services, and handles appointment scheduling with secure payment processing.

## 📁 Project Structure

```
ayursetu/
├── backend/                 # Backend microservices
│   ├── User-Service/       # User management microservice
│   ├── Doctor-Service/     # Doctor management microservice
│   ├── Pharmacy-Service/   # Pharmacy management microservice
│   ├── Appointment-Service/ # Appointment booking microservice
│   ├── Payment-Service/    # Payment processing microservice
│   ├── API-Gateway/        # Spring Cloud Gateway
│   └── docker-compose.yml  # Docker orchestration
├── frontend/               # React.js frontend application
│   ├── src/
│   ├── public/
│   └── package.json
└── README.md              # This file
```

## 🏗️ Architecture

### Backend (Microservices)
- **User Service** - User management, authentication, and profile management
- **Doctor Service** - Doctor profiles, specializations, availability, and reviews
- **Pharmacy Service** - Medicine inventory and prescription management
- **Appointment Service** - Appointment booking and scheduling
- **Payment Service** - Secure payment processing and transaction management
- **API Gateway** - Centralized routing, authentication, and rate limiting

### Frontend
- **React.js Application** - Modern, responsive user interface
- **Material-UI Components** - Professional design system
- **Redux State Management** - Centralized state management
- **Real-time Features** - Live appointment booking and notifications

## 🛠️ Technology Stack

### Backend
- **Spring Boot 3.5.4** - Microservices framework
- **Spring Security** - Authentication and authorization
- **JPA/Hibernate** - Database ORM
- **MySQL** - Primary database
- **Redis** - Caching and rate limiting
- **Apache Kafka** - Event-driven messaging
- **Spring Cloud Gateway** - API Gateway
- **Docker** - Containerization

### Frontend
- **React.js** - Frontend framework
- **Material-UI** - UI component library
- **Redux** - State management
- **Axios** - HTTP client
- **React Router** - Client-side routing

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Node.js 16+
- Docker and Docker Compose
- MySQL 8.0+
- Redis 6.0+

### Backend Setup
```bash
cd backend
docker-compose up -d
```

### Frontend Setup
```bash
cd frontend
npm install
npm start
```

## 📚 Documentation

- [Backend Documentation](./backend/README.md)
- [Frontend Documentation](./frontend/README.md)
- [API Documentation](./backend/API-Gateway/README.md)

## 🔐 Security Features

- JWT-based authentication
- Role-based access control (USER, DOCTOR, ADMIN)
- API rate limiting
- Secure payment processing
- CORS configuration

## 🧪 Testing

- Unit tests for all microservices
- Integration tests
- End-to-end testing
- Frontend component testing

## 🚀 Deployment

- Docker containerization
- Kubernetes ready
- CI/CD pipeline support
- Environment-specific configurations

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## 📝 License

This project is licensed under the MIT License.

## 👥 Team

- **Full-Stack Development:** [Your Name]
- **DevOps:** [Your Name]

## 📞 Support

For support and questions, please contact:
- Email: [your-email@example.com]
- GitHub Issues: [Create an issue](https://github.com/bhaveshrmahajan/ayursetu/issues)

---

**AyurSetu** - Bridging the gap between patients and healthcare providers through technology. 