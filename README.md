# Amdox Task Management System - Replit Setup

## Overview
A comprehensive Java Spring Boot task management application with JWT authentication, role-based access control, and real-time notifications via WebSocket. The system supports task assignment, prioritization, deadline tracking, comments, and file attachments.

## Recent Changes (November 4, 2025)
- Migrated from MySQL to PostgreSQL for Replit compatibility
- Updated server configuration to run on port 5000 with 0.0.0.0 binding
- Configured database connection to use Replit's PostgreSQL environment variables
- Made email configuration optional (requires MAIL_* environment variables if needed)
- Set up Maven build and Spring Boot workflow

## Project Architecture

### Technology Stack
- **Backend**: Java 17, Spring Boot 3.2.0
- **Security**: Spring Security with JWT authentication
- **Database**: PostgreSQL (via Replit managed database)
- **ORM**: JPA/Hibernate
- **Build Tool**: Maven
- **Real-time**: WebSocket for notifications
- **Email**: Spring Mail (optional)

### Project Structure
```
src/main/java/com/amdox/taskmanagement/
├── config/              # WebSocket and other configurations
├── controller/          # REST API endpoints
│   ├── AuthController.java
│   ├── TaskController.java
│   ├── CommentController.java
│   ├── UserController.java
│   └── NotificationController.java
├── dto/                 # Data Transfer Objects
├── entity/              # JPA entities (User, Task, Comment, etc.)
├── repository/          # Spring Data JPA repositories
├── security/            # JWT and Spring Security configuration
└── service/             # Business logic layer
```

### Key Features
1. **Authentication & Authorization**
   - JWT-based authentication
   - Three user roles: ADMIN, EDITOR, VIEWER
   - Secure password hashing

2. **Task Management**
   - CRUD operations for tasks
   - Task status tracking (TODO, IN_PROGRESS, REVIEW, COMPLETED, BLOCKED)
   - Priority levels (LOW, MEDIUM, HIGH, URGENT)
   - Deadline management with automated reminders
   - Task assignment to users

3. **Collaboration**
   - Task comments
   - File attachments
   - Real-time notifications via WebSocket

4. **Notifications**
   - In-app notification system
   - Email notifications (optional)
   - Scheduled deadline reminders

## Environment Setup

### Required Environment Variables
- `DATABASE_URL` - PostgreSQL connection string (automatically provided by Replit)
- `PGHOST`, `PGPORT`, `PGUSER`, `PGPASSWORD`, `PGDATABASE` - PostgreSQL credentials (automatically provided)

### Optional Email Configuration
If you want email notifications, add these secrets:
- `MAIL_HOST` - SMTP server (default: smtp.gmail.com)
- `MAIL_PORT` - SMTP port (default: 587)
- `MAIL_USERNAME` - Email account
- `MAIL_PASSWORD` - Email password or app-specific password

### JWT Configuration
The JWT secret is configured in `application.properties`. For production, update:
- `jwt.secret` - Change to a secure random value
- `jwt.expiration` - Token expiration time in milliseconds (default: 24 hours)

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login and receive JWT token

### Tasks
- `GET /api/tasks` - Get all tasks
- `GET /api/tasks/{id}` - Get specific task
- `GET /api/tasks/my-tasks` - Get current user's tasks
- `GET /api/tasks/status/{status}` - Filter by status
- `POST /api/tasks` - Create task
- `PUT /api/tasks/{id}` - Update task
- `DELETE /api/tasks/{id}` - Delete task

### Comments
- `GET /api/comments/task/{taskId}` - Get task comments
- `POST /api/comments` - Add comment
- `DELETE /api/comments/{id}` - Delete comment

### Users
- `GET /api/users` - List all users
- `GET /api/users/{id}` - Get user details
- `PUT /api/users/{id}/role` - Update user role (Admin only)
- `PUT /api/users/{id}/status` - Enable/disable user (Admin only)

### Notifications
- `GET /api/notifications` - Get all notifications
- `GET /api/notifications/unread` - Get unread notifications
- `GET /api/notifications/unread/count` - Count unread
- `PUT /api/notifications/{id}/read` - Mark as read
- `PUT /api/notifications/read-all` - Mark all as read

## Database Schema
Hibernate auto-creates these tables on startup:
- `users` - User accounts with roles
- `tasks` - Task details and status
- `comments` - Task comments
- `attachments` - File attachments
- `notifications` - User notifications

## Testing the API

1. **Register a user**:
```bash
curl -X POST http://localhost:5000/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "email": "admin@example.com",
    "password": "admin123",
    "fullName": "Admin User"
  }'
```

2. **Login to get JWT token**:
```bash
curl -X POST http://localhost:5000/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

3. **Create a task** (use token from login):
```bash
curl -X POST http://localhost:5000/api/tasks \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "title": "Setup Development Environment",
    "description": "Install all required tools",
    "priority": "HIGH",
    "deadline": "2025-12-31T23:59:59"
  }'
```

## User Preferences
- Clean, production-ready code
- Follow Spring Boot best practices
- Comprehensive error handling
- Secure authentication and authorization
- RESTful API design

## Notes
- The application runs on port 5000 (required for Replit web preview)
- Database tables are auto-created on first run
- First registered user gets VIEWER role (manually update to ADMIN via database)
- Email notifications are optional and disabled by default
- JWT tokens expire after 24 hours by default
