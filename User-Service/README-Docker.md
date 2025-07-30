# AyurSetu User Service - Docker Setup

This document provides instructions for running the AyurSetu User Service using Docker.

## Prerequisites

- Docker installed on your system
- Docker Compose installed on your system

## Quick Start

### 1. Build and Run with Docker Compose

```bash
# Build and start all services
docker-compose up --build

# Run in detached mode
docker-compose up -d --build
```

### 2. Access the Application

- **User Service API**: http://localhost:8081
- **MySQL Database**: localhost:3306
  - Username: root
  - Password: root1234
  - Database: userdb

### 3. API Endpoints

The following endpoints are available:

- `POST /api/users/register` - Register a new user
- `POST /api/users/login` - User login
- `GET /api/users/{email}` - Get user by email
- `PUT /api/users/{email}` - Update user
- `DELETE /api/users/{email}` - Delete user
- `GET /api/users/all` - Get all users
- `POST /api/users/forgot-password` - Forgot password
- `POST /api/users/reset-password` - Reset password
- `POST /api/users/reset-password-with-token` - Reset password with token

## Docker Commands

### Build the Application
```bash
docker build -t ayursetu-user-service .
```

### Run Individual Containers
```bash
# Run MySQL only
docker-compose up mysql

# Run application only (requires MySQL to be running)
docker-compose up user-service
```

### Stop Services
```bash
# Stop all services
docker-compose down

# Stop and remove volumes
docker-compose down -v
```

### View Logs
```bash
# View all logs
docker-compose logs

# View specific service logs
docker-compose logs user-service
docker-compose logs mysql

# Follow logs in real-time
docker-compose logs -f user-service
```

### Clean Up
```bash
# Remove all containers, networks, and volumes
docker-compose down -v --remove-orphans

# Remove all images
docker system prune -a
```

## Environment Variables

The following environment variables can be customized in the `docker-compose.yml` file:

- `SPRING_DATASOURCE_URL`: Database connection URL
- `SPRING_DATASOURCE_USERNAME`: Database username
- `SPRING_DATASOURCE_PASSWORD`: Database password
- `SPRING_MAIL_HOST`: SMTP host
- `SPRING_MAIL_PORT`: SMTP port
- `SPRING_MAIL_USERNAME`: Email username
- `SPRING_MAIL_PASSWORD`: Email password

## Troubleshooting

### Common Issues

1. **Port Already in Use**
   - Change the port mappings in `docker-compose.yml`
   - Example: `"8082:8081"` to use port 8082 on host

2. **Database Connection Issues**
   - Ensure MySQL container is healthy before starting the application
   - Check database credentials in environment variables

3. **Email Configuration**
   - Update email credentials in `docker-compose.yml`
   - Ensure Gmail app password is correctly set

### Health Checks

The MySQL container includes health checks to ensure it's ready before starting the application. You can check the status with:

```bash
docker-compose ps
```

## Development

For development, you can mount the source code as a volume:

```yaml
# Add to docker-compose.yml under user-service
volumes:
  - ./src:/app/src
```

This allows for hot reloading during development.

## Production Deployment

For production deployment:

1. Update environment variables with production values
2. Use external database if needed
3. Configure proper logging
4. Set up monitoring and health checks
5. Use Docker secrets for sensitive data

## Security Notes

- Change default passwords in production
- Use Docker secrets for sensitive information
- Configure proper network security
- Regularly update base images 