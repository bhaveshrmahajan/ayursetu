# AyurSetu Frontend - React.js Application

This directory contains the React.js frontend application for the AyurSetu healthcare platform.

## 🎨 Frontend Overview

The AyurSetu frontend is a modern, responsive web application built with React.js that provides an intuitive interface for patients, doctors, and administrators to interact with the healthcare platform.

## 🛠️ Technology Stack

- **React.js 18** - Frontend framework
- **Material-UI (MUI)** - UI component library
- **Redux Toolkit** - State management
- **React Router** - Client-side routing
- **Axios** - HTTP client for API calls
- **React Hook Form** - Form handling
- **React Query** - Data fetching and caching
- **Socket.io** - Real-time communication
- **TypeScript** - Type safety (optional)

## 📁 Project Structure

```
frontend/
├── public/                 # Static files
│   ├── index.html
│   ├── favicon.ico
│   └── manifest.json
├── src/                   # Source code
│   ├── components/        # Reusable components
│   │   ├── common/       # Common UI components
│   │   ├── auth/         # Authentication components
│   │   ├── doctor/       # Doctor-related components
│   │   ├── patient/      # Patient-related components
│   │   └── admin/        # Admin components
│   ├── pages/            # Page components
│   │   ├── Home/
│   │   ├── Login/
│   │   ├── Register/
│   │   ├── DoctorDashboard/
│   │   ├── PatientDashboard/
│   │   └── AdminDashboard/
│   ├── services/         # API services
│   │   ├── authService.js
│   │   ├── doctorService.js
│   │   ├── appointmentService.js
│   │   └── paymentService.js
│   ├── store/            # Redux store
│   │   ├── slices/
│   │   └── store.js
│   ├── utils/            # Utility functions
│   ├── hooks/            # Custom React hooks
│   ├── constants/        # Constants and configurations
│   ├── styles/           # Global styles
│   ├── App.js            # Main App component
│   └── index.js          # Entry point
├── package.json          # Dependencies and scripts
└── README.md            # This file
```

## 🚀 Quick Start

### Prerequisites
- Node.js 16+
- npm or yarn
- Backend services running

### Installation
```bash
# Install dependencies
npm install

# Start development server
npm start

# Build for production
npm run build

# Run tests
npm test
```

### Environment Variables
Create a `.env` file in the frontend directory:

```env
REACT_APP_API_URL=http://localhost:8080
REACT_APP_SOCKET_URL=ws://localhost:8080
REACT_APP_STRIPE_PUBLIC_KEY=your_stripe_public_key
```

## 🎯 Features

### User Authentication
- User registration and login
- JWT token management
- Role-based access control
- Password reset functionality

### Patient Features
- Browse doctors by specialization
- Book appointments
- View appointment history
- Real-time chat with doctors
- Payment processing
- Prescription management

### Doctor Features
- Doctor profile management
- Availability scheduling
- Appointment management
- Patient communication
- Prescription writing
- Earnings tracking

### Admin Features
- User management
- Doctor verification
- System monitoring
- Analytics dashboard

## 🎨 UI/UX Design

### Design System
- **Material-UI Components** - Consistent design language
- **Responsive Design** - Mobile-first approach
- **Accessibility** - WCAG 2.1 compliant
- **Dark/Light Theme** - User preference support

### Key Pages
- **Landing Page** - Platform introduction
- **Login/Register** - Authentication
- **Dashboard** - Role-specific dashboards
- **Doctor Search** - Find and filter doctors
- **Appointment Booking** - Schedule consultations
- **Chat Interface** - Real-time communication

## 🔄 State Management

### Redux Store Structure
```javascript
{
  auth: {
    user: null,
    token: null,
    isAuthenticated: false
  },
  doctors: {
    list: [],
    selected: null,
    loading: false
  },
  appointments: {
    list: [],
    current: null,
    loading: false
  },
  ui: {
    theme: 'light',
    notifications: []
  }
}
```

## 🌐 API Integration

### Service Layer
- **Centralized API calls** - Using Axios interceptors
- **Error handling** - Global error management
- **Loading states** - User feedback
- **Caching** - React Query for data caching

### Real-time Features
- **WebSocket integration** - Live chat and notifications
- **Push notifications** - Browser notifications
- **Live updates** - Real-time data synchronization

## 🧪 Testing

### Testing Strategy
- **Unit Tests** - Component testing with Jest and React Testing Library
- **Integration Tests** - API integration testing
- **E2E Tests** - End-to-end testing with Cypress

### Running Tests
```bash
# Unit tests
npm test

# E2E tests
npm run cypress:open

# Test coverage
npm run test:coverage
```

## 🚀 Deployment

### Build Process
```bash
# Production build
npm run build

# Analyze bundle
npm run analyze
```

### Deployment Options
- **Netlify** - Static site hosting
- **Vercel** - React.js optimized hosting
- **AWS S3 + CloudFront** - Scalable hosting
- **Docker** - Containerized deployment

## 🔧 Development

### Code Quality
- **ESLint** - Code linting
- **Prettier** - Code formatting
- **Husky** - Git hooks
- **TypeScript** - Type safety

### Development Tools
- **React Developer Tools** - Component inspection
- **Redux DevTools** - State management debugging
- **Storybook** - Component documentation

## 📱 Mobile Support

### Responsive Design
- **Mobile-first approach** - Optimized for mobile devices
- **Progressive Web App** - PWA capabilities
- **Touch-friendly** - Mobile-optimized interactions

## 🔐 Security

### Frontend Security
- **Input validation** - Client-side validation
- **XSS protection** - Sanitized inputs
- **CSRF protection** - Token-based protection
- **Secure storage** - Encrypted local storage

## 🤝 Contributing

1. Follow React.js best practices
2. Write comprehensive tests
3. Update component documentation
4. Follow the design system
5. Ensure accessibility compliance

---

For API documentation, see the backend README files. 