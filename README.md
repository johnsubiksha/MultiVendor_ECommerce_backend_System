# 🛒 Multi-Vendor E-Commerce Backend System

A scalable and secure **REST API backend** for a Multi-Vendor E-Commerce Platform built using **Java Spring Boot**, **MongoDB Atlas**, and **JWT Authentication**.

The system supports three user roles:

* **Customer**
* **Seller**
* **Admin**

Each role has dedicated functionalities with **Role-Based Access Control (RBAC)** to ensure secure access to resources.

---

# 📋 Table of Contents

* [Project Overview](#project-overview)
* [Features](#features)
* [Technology Stack](#technology-stack)
* [User Roles](#user-roles)
* [Authentication & Authorization](#authentication--authorization)
* [API Documentation](#api-documentation)
* [Data Models](#data-models)
* [Security Features](#security-features)
* [Installation & Setup](#installation--setup)
* [Running the Application](#running-the-application)
* [Testing the API](#testing-the-api)

---

# 📌 Project Overview

The Multi-Vendor E-Commerce Backend System provides a complete backend solution for an online marketplace where multiple sellers can manage and sell products while customers can browse, purchase, review, and wishlist products.

The application includes:

* User Authentication & Authorization
* Product Management
* Seller Management
* Cart & Checkout
* Order Processing
* Payment Handling
* Wishlist Management
* Review System
* Address Management
* Admin Analytics Dashboard

---

# ✨ Features

## 👤 Customer Features

* User Registration & Login
* Forgot Password & Password Reset via OTP
* Browse Products
* Search Products
* Filter Products by Category
* View Product Stock Availability
* Add Products to Cart
* Checkout Cart
* Buy Products Instantly
* Manage Delivery Addresses
* Add Product Reviews
* Maintain Wishlist
* Manage User Profile
* Change Password
* Apply for Seller Role

---

## 🏪 Seller Features

* Apply for Seller Role
* Manage Product Listings

  * Add Products
  * Update Products
  * Delete Products
* View Seller Dashboard
* View Seller Profile
* Manage Order Status

---

## 🛡️ Admin Features

* Approve Seller Applications
* View Pending Seller Requests
* Manage Platform Users
* Monitor Revenue Analytics
* Monitor Order Statistics
* Track Top Products
* Track Seller Performance
* Access Dashboard Analytics

---

# 🛠️ Technology Stack

| Technology      | Purpose                        |
| --------------- | ------------------------------ |
| Java 17         | Core Programming Language      |
| Spring Boot     | Backend Framework              |
| Spring Security | Authentication & Authorization |
| JWT             | Stateless Authentication       |
| MongoDB Atlas   | Cloud NoSQL Database           |
| Maven           | Dependency Management          |
| Lombok          | Boilerplate Code Reduction     |

---

# 👥 User Roles

## CUSTOMER

Default role assigned during registration.

Permissions:

* Browse Products
* Place Orders
* Manage Cart
* Add Reviews
* Manage Wishlist
* Manage Addresses
* Apply for Seller Role

---

## SELLER

Approved vendors on the platform.

Permissions:

* Manage Products
* View Dashboard
* Manage Orders
* Access Seller Profile

---

## ADMIN

Platform administrators.

Permissions:

* Manage Users
* Approve Sellers
* View Analytics
* Monitor Platform Performance

---

# 🔐 Authentication & Authorization

The application uses **JWT (JSON Web Token)** for authentication.

All protected endpoints require:

```http
Authorization: Bearer <jwt_token>
```

## Public Endpoints

```http
POST /auth/signup
POST /auth/signin
POST /auth/forgot-password
POST /auth/verify-otp
POST /auth/reset-password
```

All other endpoints require authentication.

Role-based restrictions are enforced using Spring Security.

---

# 📖 API Documentation

---

# 1. Authentication Controller

**Base Path:** `/auth`

| Method | Endpoint           | Description                        |
| ------ | ------------------ | ---------------------------------- |
| POST   | `/signup`          | Register a new user                |
| POST   | `/signin`          | Authenticate user and generate JWT |
| POST   | `/forgot-password` | Send OTP to email                  |
| POST   | `/verify-otp`      | Verify OTP                         |
| POST   | `/reset-password`  | Reset password                     |

---

# 2. Admin Controller

**Base Path:** `/api/admin`

🔒 Requires ADMIN Role

| Method | Endpoint                   |
| ------ | -------------------------- |
| PUT    | `/approve-seller/{userId}` |
| GET    | `/seller-requests`         |
| GET    | `/users`                   |

---

# 3. Seller Controller

**Base Path:** `/api/seller`

🔒 Requires Authentication

| Method | Endpoint     |
| ------ | ------------ |
| POST   | `/apply`     |
| GET    | `/profile`   |
| GET    | `/dashboard` |

---

# 4. Product Controller

**Base Path:** `/api/product`

🔒 Requires Authentication

## Get All Products

```http
GET /api/product/all
```

### Query Parameters

| Parameter | Default |
| --------- | ------- |
| page      | 0       |
| size      | 10      |
| sortBy    | name    |
| direction | asc     |

---

## Get Product Stock

```http
GET /api/product/stock/{productId}
```

---

## Search Products

```http
GET /api/product/search?keyword=laptop
```

### Query Parameters

| Parameter | Default |
| --------- | ------- |
| page      | 0       |
| size      | 10      |
| sortBy    | name    |
| direction | asc     |

---

## Filter Products

```http
GET /api/product/filter?category=Electronics
```

### Query Parameters

| Parameter | Default |
| --------- | ------- |
| page      | 0       |
| size      | 10      |
| sortBy    | name    |
| direction | asc     |

---

# 5. Seller Product Controller

**Base Path:** `/api/seller/product`

🔒 Requires SELLER Role

| Method | Endpoint       | Description         |
| ------ | -------------- | ------------------- |
| GET    | `/`            | Get Seller Products |
| POST   | `/`            | Add Product         |
| PATCH  | `/{productId}` | Update Product      |
| DELETE | `/{productId}` | Delete Product      |

---

# 6. Order Controller

**Base Path:** `/api/order`

🔒 Requires Authentication

| Method | Endpoint            |
| ------ | ------------------- |
| POST   | `/buy-now`          |
| GET    | `/my-orders`        |
| PUT    | `/{orderId}/status` |

---

# 7. Cart Controller

**Base Path:** `/api/cart`

🔒 Requires CUSTOMER Role

| Method | Endpoint              |
| ------ | --------------------- |
| POST   | `/add`                |
| GET    | `/`                   |
| DELETE | `/remove/{productId}` |
| POST   | `/checkout`           |

---

# 8. Address Controller

**Base Path:** `/api/address`

🔒 Requires Authentication

| Method | Endpoint |
| ------ | -------- |
| POST   | `/`      |
| GET    | `/`      |
| DELETE | `/{id}`  |

---

# 9. Review Controller

**Base Path:** `/api/reviews`

🔒 Requires Authentication

| Method | Endpoint       |
| ------ | -------------- |
| POST   | `/{productId}` |
| GET    | `/{productId}` |

---

# 10. Wishlist Controller

**Base Path:** `/api/wishlist`

🔒 Requires CUSTOMER Role

| Method | Endpoint              |
| ------ | --------------------- |
| POST   | `/add`                |
| GET    | `/`                   |
| DELETE | `/remove/{productId}` |

---

# 11. Payment Controller

**Base Path:** `/api/payment`

🔒 Requires Authentication

| Method | Endpoint |
| ------ | -------- |
| POST   | `/pay`   |

---

# 12. Analytics Controller

**Base Path:** `/api/admin/analytics`

🔒 Requires ADMIN Role

| Method | Endpoint        |
| ------ | --------------- |
| GET    | `/revenue`      |
| GET    | `/dashboard`    |
| GET    | `/orders`       |
| GET    | `/top-products` |
| GET    | `/top-sellers`  |

---

# 13. User Controller

**Base Path:** `/api/user`

🔒 Requires Authentication

| Method | Endpoint           |
| ------ | ------------------ |
| GET    | `/profile`         |
| PUT    | `/profile`         |
| POST   | `/change-password` |

---

# 🗂️ Data Models

The application uses the following MongoDB collections:

* User
* Seller
* Product
* Order
* Cart
* CartItem
* Address
* Review
* Wishlist
* Payment

---

# 🔒 Security Features

* JWT Authentication
* Spring Security Integration
* BCrypt Password Encryption
* Stateless Session Management
* Role-Based Access Control (RBAC)
* Secure API Endpoints

---

# ⚙️ Installation & Setup

## Prerequisites

* Java 17+
* Maven 3.8+
* MongoDB Atlas Account

---

## Clone Repository

```bash
git clone https://github.com/your-username/multi-vendor-ecommerce-backend.git

cd multi-vendor-ecommerce-backend
```

---

## Configure Application Properties

```properties
# MongoDB
spring.data.mongodb.uri=mongodb+srv://<username>:<password>@cluster.mongodb.net/database

# JWT
app.jwt.secret=your_jwt_secret
app.jwt.expiration=86400000

# Server
server.port=8080
```

---

## Install Dependencies

```bash
mvn clean install
```

---

# 🚀 Running the Application

## Run Using Maven

```bash
mvn spring-boot:run
```

---

## Build and Run JAR

```bash
mvn clean package

java -jar target/multi-vendor-ecommerce-backend.jar
```

---

Application will start on:

```http
http://localhost:8080
```

---

# 🧪 Testing the API

Recommended tools:

* Postman
* Insomnia
* Swagger UI (if configured)
* cURL

### Example Registration

```bash
curl -X POST http://localhost:8080/auth/signup \
-H "Content-Type: application/json" \
-d '{
  "username":"john",
  "email":"john@example.com",
  "password":"password123"
}'
```

### Example Login

```bash
curl -X POST http://localhost:8080/auth/signin \
-H "Content-Type: application/json" \
-d '{
  "email":"john@example.com",
  "password":"password123"
}'
```

---

<div align="center">

### Built with ☕ Java, Spring Boot & MongoDB Atlas

Secure • Scalable • Production Ready

</div>
