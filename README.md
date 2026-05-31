# Personal Finance Manager 💰

A full-stack Personal Finance Management application built using **Spring Boot**, **Spring Security**, **JWT Authentication**, and **MySQL**.
The application helps users track daily expenses, manage categories, set budgets, and monitor spending securely.

---

##  Features

### Authentication & Security

* User Registration
* User Login
* JWT-based Authentication
* Password Encryption using Spring Security
* Protected REST APIs

### Expense Management

* Add Expenses
* Update Expenses
* Delete Expenses
* View Expense History
* Categorize Expenses

### Category Management

* Create Categories
* View Categories
* Update Categories
* Delete Categories

### Budget Management

* Set Category-wise Budgets
* Track Spending Against Budget
* Monitor Remaining Budget
* Budget Status Reporting

### Backend Features

* RESTful API Architecture
* Layered Architecture (Controller → Service → Repository)
* DTO Pattern Implementation
* MySQL Database Integration
* JPA/Hibernate ORM

---

## 🛠️ Tech Stack

### Backend

* Java 21
* Spring Boot 3
* Spring Security
* JWT (JSON Web Token)
* Spring Data JPA
* Hibernate
* Maven

### Database

* MySQL

### Tools

* IntelliJ IDEA
* Postman
* Git & GitHub

---

## 📂 Project Structure

```text
src/main/java/com/finance/tracker

├── config
│   ├── JwtFilter.java
│   ├── JwtUtil.java
│   └── SecurityConfig.java
│
├── controller
│   ├── AuthController.java
│   ├── ExpenseController.java
│   ├── CategoryController.java
│   └── BudgetController.java
│
├── dto
│   ├── LoginRequest.java
│   ├── RegisterRequest.java
│   ├── ExpenseRequest.java
│   ├── CategoryRequest.java
│   ├── BudgetRequest.java
│   └── BudgetStatus.java
│
├── model
│   ├── User.java
│   ├── Expense.java
│   ├── Category.java
│   └── Budget.java
│
├── repository
│   ├── UserRepository.java
│   ├── ExpenseRepository.java
│   ├── CategoryRepository.java
│   └── BudgetRepository.java
│
└── service
    ├── AuthService.java
    ├── ExpenseService.java
    ├── CategoryService.java
    └── BudgetService.java
```
