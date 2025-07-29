# 📌 Task Management App

A lightweight web application for managing daily tasks, built with **Spring Boot** and a responsive **Thymeleaf** frontend.  
It supports full task lifecycle management including creation, updating, completion, and filtering — all wrapped in a modern architecture with clean code practices and Docker support for smooth deployment.

---

## ✅ Features

- Full CRUD operations for tasks
- Filter tasks by status or due date
- Mark tasks as completed
- Responsive UI with **Thymeleaf**
- Clean data transfer using **DTOs** and **MapStruct**
- Input validation (e.g., required title, valid future due date)
- Centralized global exception handling
- Unit and integration tests
- Docker support for containerized deployment

---

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot**
- **Spring Web & Spring Data JPA**
- **H2 / PostgreSQL**
- **Thymeleaf**
- **MapStruct**
- **Lombok**
- **JUnit + Spring Test**
- **Docker**

---

## 📂 Project Structure

```bash
.
├── docker-compose.yml
├── mvnw / mvnw.cmd
├── pom.xml
├── README.md
├── script.sh
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.example.siemens_initial_project.siemens_initial_project
│   │   │       ├── controller
│   │   │       ├── dto
│   │   │       ├── exception_handling
│   │   │       ├── mapper
│   │   │       ├── model
│   │   │       ├── repository
│   │   │       ├── services
│   │   │       ├── swagger
│   │   │       └── SiemensInitialProjectApplication.java
│   │   └── resources
│   │       ├── application.properties
│   │       ├── application-docker.properties
│   │       ├── application-local.properties
│   │       └── templates/tasks
│   │           ├── create_task.html
│   │           ├── list.html
│   │           └── update.html
│   └── test
│       └── java/com.example.siemens_initial_project.siemens_initial_project
│           ├── controller
│           ├── integration
│           ├── repository
│           ├── services
│           └── SiemensInitialProjectApplicationTests.java
└── target (build output)


🚀 Getting Started
🔧 Run Locally
Make sure you have Java 17 and Maven installed.

bash
Copy
Edit
./mvnw spring-boot:run
Then open your browser at:
http://localhost:8080/tasks

🐳 Run with Docker
Make sure Docker is installed, then run:

bash
Copy
Edit
docker-compose up --build
This will start:

Spring Boot app on port 8080

PostgreSQL database on port 5433

Default PostgreSQL credentials:

ini
Copy
Edit
POSTGRES_DB=siemens_project  
POSTGRES_USER=postgres  
POSTGRES_PASSWORD=26122003
Visit the app at:
http://localhost:8080/tasks

📘 API Documentation
If you want to explore the REST API, visit:
http://localhost:8080/swagger-ui.html

