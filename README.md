# 🚀 Amdox Task Management System

A comprehensive full-stack task management application built with Java Spring Boot, featuring role-based access control, real-time notifications, and collaborative task management.

## ✨ Features

### Core Features
- **Task Assignment and Prioritization**: Assign tasks to team members with clear deadlines and priority levels (LOW, MEDIUM, HIGH, URGENT)
- **Role-Based Permissions**: Three-tier access control system (ADMIN, EDITOR, VIEWER)
- **Deadline Tracking and Notifications**: Automated email reminders for upcoming deadlines
- **Real-Time Collaboration**: WebSocket-based real-time updates, comments, and file sharing
- **Progress Reporting**: Analytics and reports on task completion and team performance
- **Secure Authentication**: JWT-based authentication and authorization

### Additional Features
- Kanban board support (TODO, IN_PROGRESS, REVIEW, COMPLETED, BLOCKED)
- Task comments and discussions
- File attachments
- User notifications system
- Task search and filtering
- Email notifications
- Deadline reminders

## 🛠️ Technology Stack

- **Backend**: Java 17, Spring Boot 3.2.0
- **Security**: Spring Security, JWT
- **Database**: MySQL
- **ORM**: JPA/Hibernate
- **Build Tool**: Maven
- **Email**: Spring Mail
- **Real-time**: WebSocket
- **API**: RESTful APIs

## 📋 Prerequisites

Before you begin, ensure you have the following installed:
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- Git
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

## 🚀 Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/amdox-task-management.git
cd amdox-task-management
```

### 2. Configure Database

Create a MySQL database:

```sql
CREATE DATABASE amdox_task_db;
```

Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/amdox_task_db?createDatabaseIfNotExist=true
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### 3. Configure Email (Optional)

For email notifications, update the email configuration in `application.properties`:

```properties
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
```

To generate Gmail App Password:
1. Go to Google Account Settings
2. Security > 2-Step Verification
3. App Passwords > Generate new password

### 4. Build the Project

```bash
mvn clean install
```

### 5. Run the Application

```bash
mvn spring-boot:run
```

Or run the JAR file:

```bash
java -jar target/task-management-1.0.0.jar
```

The application will start on **http://localhost:8080**

## 📁 Project Structure

```
amdox-task-management/
├── src/main/java/com/amdox/taskmanagement/
│   ├── config/              # Configuration classes
│   │   └── WebSocketConfig.java
│   ├── controller/          # REST API controllers
│   │   ├── AuthController.java
│   │   ├── TaskController.java
│   │   ├── CommentController.java
│   │   ├── UserController.java
│   │   └── NotificationController.java
│   ├── dto/                 # Data Transfer Objects
│   │   ├── TaskRequest.java
│   │   ├── TaskResponse.java
│   │   ├── AuthResponse.java
│   │   └── ...
│   ├── entity/              # Database entities
│   │   ├── User.java
│   │   ├── Task.java
│   │   ├── Comment.java
│   │   ├── Attachment.java
│   │   └── Notification.java
│   ├── repository/          # Data access layer
│   │   ├── UserRepository.java
│   │   ├── TaskRepository.java
│   │   └── ...
│   ├── security/            # Security configuration
│   │   ├── SecurityConfig.java
│   │   ├── JwtUtil.java
│   │   ├── JwtAuthenticationFilter.java
│   │   └── CustomUserDetailsService.java
│   ├── service/             # Business logic
│   │   ├── TaskService.java
│   │   ├── AuthService.java
│   │   ├── CommentService.java
│   │   ├── EmailService.java
│   │   └── SchedulerService.java
│   └── AmdoxTaskManagementApplication.java
├── src/main/resources/
│   └── application.properties
└── pom.xml
```

## 🔐 API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user

### Tasks
- `GET /api/tasks` - Get all tasks
- `GET /api/tasks/{id}` - Get task by ID
- `GET /api/tasks/my-tasks` - Get current user's tasks
- `GET /api/tasks/status/{status}` - Get tasks by status
- `POST /api/tasks` - Create new task
- `PUT /api/tasks/{id}` - Update task
- `DELETE /api/tasks/{id}` - Delete task

### Comments
- `GET /api/comments/task/{taskId}` - Get comments for a task
- `POST /api/comments` - Add comment
- `DELETE /api/comments/{id}` - Delete comment

### Users
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}/role` - Update user role (Admin only)
- `PUT /api/users/{id}/status` - Enable/disable user (Admin only)

### Notifications
- `GET /api/notifications` - Get all notifications
- `GET /api/notifications/unread` - Get unread notifications
- `GET /api/notifications/unread/count` - Get unread count
- `PUT /api/notifications/{id}/read` - Mark as read
- `PUT /api/notifications/read-all` - Mark all as read

## 🔑 Authentication

Include JWT token in request headers:

```
Authorization: Bearer YOUR_JWT_TOKEN
```

## 👥 User Roles

1. **ADMIN**
   - Full system access
   - Manage all users
   - Create, update, delete any task
   - Assign roles

2. **EDITOR**
   - Create and manage own tasks
   - Update assigned tasks
   - Add comments
   - View all tasks

3. **VIEWER**
   - View assigned tasks
   - View comments
   - Limited editing

## 🧪 Testing

Test the API using tools like:
- **Postman** - Import the collection
- **cURL** - Command line testing
- **Swagger UI** (if configured)

### Sample Registration Request:

```json
POST /api/auth/register
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "password123",
  "fullName": "John Doe"
}
```

### Sample Task Creation:

```json
POST /api/tasks
Headers: Authorization: Bearer YOUR_TOKEN
{
  "title": "Complete Project Documentation",
  "description": "Write comprehensive documentation",
  "priority": "HIGH",
  "deadline": "2024-12-31T23:59:59",
  "assignedToId": 2,
  "category": "Documentation",
  "tags": "urgent,documentation"
}
```

## 📤 Push to GitHub

### First Time Setup

1. **Create a new repository on GitHub**
   - Go to https://github.com/new
   - Name it: `amdox-task-management`
   - Don't initialize with README

2. **Initialize Git and push**:

```bash
# Initialize git repository
git init

# Add all files
git add .

# Commit
git commit -m "Initial commit: Amdox Task Management System"

# Add remote repository
git remote add origin https://github.com/YOUR_USERNAME/amdox-task-management.git

# Push to GitHub
git branch -M main
git push -u origin main
```

### Update Existing Repository

```bash
# Add changes
git add .

# Commit changes
git commit -m "Your commit message"

# Push to GitHub
git push origin main
```

## 🔧 Configuration

### Change Server Port

Edit `application.properties`:
```properties
server.port=8081
```

### Enable H2 Database (for testing)

Replace MySQL config with:
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

## 📊 Database Schema

The application automatically creates the following tables:
- `users` - User information
- `tasks` - Task details
- `comments` - Task comments
- `attachments` - File attachments
- `notifications` - User notifications

## 🐛 Troubleshooting

### Port Already in Use
```bash
# Find process using port 8080
lsof -i :8080
# Kill the process
kill -9 PID
```

### Database Connection Error
- Verify MySQL is running
- Check username/password
- Ensure database exists

### JWT Token Expiration
- Default expiration: 24 hours
- Update in `application.properties`: `jwt.expiration=86400000`

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License.

## 👨‍💻 Author

**Your Name**
- GitHub: [https://github.com/Sasikumarjada/Amdox-Task-Management/edit/main/README.md]
- Email: sasikumarjada2004@gmail.com

## 🙏 Acknowledgments

- Spring Boot Documentation
- JWT Authentication Best Practices
- MySQL Database

## 📞 Support

For support, email your.email@example.com or create an issue in the GitHub repository.

---

**Built with ❤️ using Java Spring Boot**
