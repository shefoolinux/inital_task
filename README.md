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

## 🧪 Running the App

### 🔧 Local Setup

1. Go to /src/main/resources:
    and change **spring.profiles.active=docker** to **spring.profiles.active=local**

2. Clone the repository:
   ```bash
   git clone https://github.com/shefoolinux/initial_task
   cd initial_task

3. Run the app using Maven:
   ```bash
   mvn spring-boot:run

4. Visit the app:
   ```bash
   http://localhost:8080/tasks
   

### 🐳 Run with Docker

1. Make sure Docker is installed and running:
   ```bash
   docker-compose up --build


---

## 🧪 Documentation


### 📘 API Documentation – Swagger UI

1. Once the app is running locally, you can access the Swagger UI here:
   ```bash
   http://localhost:8080/swagger-ui.html
   
### 📚 Code Documentation – JavaDoc

1. Go to /src/main/resources:
    and change **spring.profiles.active=docker** to **spring.profiles.active=local**

2. To generate JavaDoc for the project:
   ```bash
   mvn javadoc:javadoc

3. The generated documentation will be available at:
   ```bash
   target/report/apidocs/index.html

4. Open it in your browser to explore detailed documentation for classes and methods:


