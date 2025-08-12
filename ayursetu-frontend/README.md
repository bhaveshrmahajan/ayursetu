# AyurSetu Frontend

A modern, responsive React frontend for the AyurSetu Ayurvedic Healthcare Platform. This application provides a comprehensive interface for patients to connect with Ayurvedic doctors, book consultations, and access authentic medicines.

## Features

### 🏠 **Home Page**
- Attractive hero section with call-to-action buttons
- Feature highlights showcasing platform benefits
- Statistics and testimonials
- Responsive design with smooth animations

### 👤 **User Authentication**
- User registration with comprehensive form validation
- Secure login with JWT token management
- Password visibility toggle
- Form validation with real-time error feedback

### 🏥 **Doctor Management**
- Browse and search for Ayurvedic doctors
- Filter by specialization, city, and availability
- Detailed doctor profiles with ratings and reviews
- Doctor availability status and verification badges

### 📅 **Consultation Booking**
- Easy appointment scheduling
- Doctor selection with detailed profiles
- Date and time picker with availability checking
- Symptom description and medical history forms
- Multi-language support for consultations

### 💊 **Pharmacy**
- Browse authentic Ayurvedic medicines
- Search and filter by categories
- Shopping cart functionality
- Stock status and pricing information
- Medicine ratings and reviews

### 📊 **Dashboard**
- User statistics and consultation history
- Quick action buttons for common tasks
- Recent consultations overview
- Available doctors and medicines preview
- Profile summary with edit functionality

### 👤 **User Profile**
- Complete profile management
- Edit personal information
- Account security settings
- Data download and account deletion options

## Technology Stack

- **React 18** - Modern React with hooks and functional components
- **React Router 6** - Client-side routing
- **Bootstrap 5** - Responsive UI framework
- **React Bootstrap** - Bootstrap components for React
- **Axios** - HTTP client for API communication
- **React Icons** - Icon library
- **React Toastify** - Toast notifications
- **Framer Motion** - Animation library
- **JWT Decode** - JWT token handling
- **Moment.js** - Date manipulation

## Project Structure

```
src/
├── components/          # Reusable UI components
│   ├── Navbar.js       # Navigation bar
│   ├── Footer.js       # Footer component
│   └── ProtectedRoute.js # Authentication wrapper
├── contexts/           # React contexts
│   └── AuthContext.js  # Authentication state management
├── pages/              # Page components
│   ├── Home.js         # Landing page
│   ├── Login.js        # Login page
│   ├── Register.js     # Registration page
│   ├── Dashboard.js    # User dashboard
│   ├── DoctorList.js   # Doctor listing
│   ├── DoctorDetail.js # Doctor profile
│   ├── Consultation.js # Booking page
│   ├── Pharmacy.js     # Medicine store
│   └── Profile.js      # User profile
├── services/           # API services
│   └── api.js         # API configuration and endpoints
├── App.js             # Main app component
├── index.js           # Entry point
└── index.css          # Global styles
```

## Getting Started

### Prerequisites

- Node.js (v16 or higher)
- npm or yarn
- Backend services running (see backend README)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd ayursetu-frontend
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Configure environment variables**
   Create a `.env` file in the root directory:
   ```env
   REACT_APP_API_URL=http://localhost:8080
   ```

4. **Start the development server**
   ```bash
   npm start
   ```

5. **Open your browser**
   Navigate to `http://localhost:3000`

## API Integration

The frontend integrates with the following backend services:

- **User Service** (Port 8081) - Authentication and user management
- **Doctor Service** (Port 8082) - Doctor profiles and availability
- **Consultation Service** (Port 8083) - Appointment booking
- **Pharmacy Service** (Port 8084) - Medicine inventory
- **Notification Service** (Port 8085) - Email and SMS notifications

## Key Features

### 🔐 **Authentication System**
- JWT-based authentication
- Automatic token refresh
- Protected routes
- Session management

### 🎨 **Modern UI/UX**
- Clean, professional design
- Responsive layout for all devices
- Smooth animations and transitions
- Intuitive navigation

### 📱 **Mobile Responsive**
- Bootstrap 5 responsive grid
- Mobile-first design approach
- Touch-friendly interface
- Optimized for all screen sizes

### ⚡ **Performance Optimized**
- Lazy loading of components
- Optimized bundle size
- Efficient state management
- Fast loading times

## Available Scripts

- `npm start` - Start development server
- `npm build` - Build for production
- `npm test` - Run tests
- `npm eject` - Eject from Create React App

## Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `REACT_APP_API_URL` | Backend API base URL | `http://localhost:8080` |

## Deployment

### Build for Production
```bash
npm run build
```

### Deploy to Netlify
1. Connect your GitHub repository to Netlify
2. Set build command: `npm run build`
3. Set publish directory: `build`
4. Add environment variables in Netlify dashboard

### Deploy to Vercel
1. Install Vercel CLI: `npm i -g vercel`
2. Run: `vercel`
3. Follow the prompts

## Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature-name`
3. Commit changes: `git commit -am 'Add feature'`
4. Push to branch: `git push origin feature-name`
5. Submit a pull request

## Troubleshooting

### Common Issues

1. **API Connection Errors**
   - Ensure backend services are running
   - Check API URL in environment variables
   - Verify CORS configuration

2. **Build Errors**
   - Clear node_modules and reinstall
   - Check for missing dependencies
   - Verify React version compatibility

3. **Authentication Issues**
   - Clear browser storage
   - Check JWT token expiration
   - Verify backend authentication endpoints

## Support

For support and questions:
- Create an issue in the repository
- Contact the development team
- Check the backend documentation

## License

This project is licensed under the MIT License - see the LICENSE file for details.

---

**AyurSetu Frontend** - Bringing Ayurvedic healthcare to your fingertips! 🌿





