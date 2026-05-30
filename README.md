# 🛒 Multi-Vendor E-Commerce Backend System

A robust, scalable **REST API backend** for a multi-vendor e-commerce platform built with **Java Spring Boot**. Supports three distinct user roles — Customer, Seller, and Admin — with secure JWT-based authentication and role-based access control.

---

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [User Roles](#user-roles)
- [Authentication & Authorization](#authentication--authorization)
- [API Documentation](#api-documentation)
- [Models](#models)
- [Security](#security)
- [Installation & Setup](#installation--setup)
- [Running the Application](#running-the-application)

---

## 📌 Project Overview

The **Multi-Vendor E-Commerce Backend System** is a backend-only REST API application that powers a full-featured e-commerce platform. It enables customers to browse and purchase products, sellers to manage their inventory, and admins to oversee the platform — all secured through JWT authentication and role-based authorization.

---

## ✨ Features

### 👤 Customer
- Register and log in securely
- Browse, search, and filter products
- Manage shopping cart
- Place orders instantly (Buy Now)
- Manage delivery addresses
- Write and read product reviews
- Maintain a wishlist
- Apply to become a seller

### 🏪 Seller
- Apply for seller role
- Manage product listings (Add, Update, Delete)
- View seller dashboard and profile
- Manage and update order statuses

### 🛡️ Admin
- Approve or reject seller applications
- View all platform users
- Monitor pending seller requests

---

## 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| **Java** | Core programming language |
| **Spring Boot** | Application framework |
| **Spring Security** | Authentication & authorization |
| **JWT (JSON Web Token)** | Stateless token-based auth |
| **MongoDB Atlas** | Cloud NoSQL database |
| **Maven** | Build and dependency management |

---

## 👥 User Roles

The system defines three roles via the `Role` enum:

| Role | Description |
|---|---|
| `CUSTOMER` | Default role on registration. Can shop, review, wishlist, and apply for seller status. |
| `SELLER` | Approved vendors who can list and manage products and orders. |
| `ADMIN` | Platform administrators with full oversight of users and seller applications. |

---

## 🔐 Authentication & Authorization

- All endpoints (except `/auth/signup` and `/auth/signin`) require a valid **JWT Bearer Token** in the `Authorization` header.
- Tokens are issued upon successful login and must be included in subsequent requests.
- **Role-Based Access Control (RBAC)** restricts endpoint access based on the authenticated user's role.

**Request Header Format:**
```
Authorization: Bearer <your_jwt_token>
```

---

## 📖 API Documentation

### 1. Auth — `/auth`

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `POST` | `/auth/signup` | Register a new user | ❌ |
| `POST` | `/auth/signin` | Login and receive JWT token | ❌ |

---

### 2. Admin — `/api/admin`

> 🔒 Requires `ADMIN` role

| Method | Endpoint | Description |
|---|---|---|
| `PUT` | `/api/admin/approve-seller/{userId}` | Approve a pending seller request |
| `GET` | `/api/admin/seller-requests` | Get all pending seller requests |
| `GET` | `/api/admin/users` | Get all registered users |

---

### 3. Seller — `/api/seller`

> 🔒 Requires `SELLER` or `CUSTOMER` role (role-specific access applies)

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/seller/roleApply` | Customer applies for seller role |
| `GET` | `/api/seller/profile` | Get seller profile |
| `GET` | `/api/seller/dashboard` | Get seller dashboard |

---

### 4. Product — `/api/product`

> 🔒 Requires authentication

| Method | Endpoint | Query Params | Description |
|---|---|---|---|
| `GET` | `/api/product/search` | `keyword` | Search products by keyword |
| `GET` | `/api/product/filter` | `category` | Filter products by category |

---

### 5. Seller Product Management — `/api/seller/product`

> 🔒 Requires `SELLER` role

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/seller/product/` | Get all products listed by the seller |
| `POST` | `/api/seller/product/` | Add a new product |
| `PATCH` | `/api/seller/product/{productId}` | Update an existing product |
| `DELETE` | `/api/seller/product/{productId}` | Delete a product |

---

### 6. Order — `/api/order`

> 🔒 Requires authentication

| Method | Endpoint | Query Params / Body | Description |
|---|---|---|---|
| `POST` | `/api/order/buy-now` | — | Purchase a product immediately |
| `GET` | `/api/order/my-orders` | — | Get all orders for the authenticated user |
| `PUT` | `/api/order/{orderId}/status` | `status` (param) | Update the status of an order |

---

### 7. Cart — `/api/cart`

> 🔒 Requires `CUSTOMER` role

| Method | Endpoint | Body | Description |
|---|---|---|---|
| `POST` | `/api/cart/add` | `CartItem` | Add a product to the cart |
| `GET` | `/api/cart/` | — | View current cart |
| `DELETE` | `/api/cart/remove/{productId}` | — | Remove a product from the cart |
| `POST` | `/api/cart/checkout` | `CheckoutRequest` | Checkout and place order from cart |

---

### 8. Address — `/api/address`

> 🔒 Requires authentication

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/address/` | Add a new delivery address |
| `GET` | `/api/address/` | Get all addresses for the user |
| `DELETE` | `/api/address/{id}` | Delete an address by ID |

---

### 9. Review — `/api/reviews`

> 🔒 Requires authentication

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/reviews/{productId}` | Add a review for a product |
| `GET` | `/api/reviews/{productId}` | Get all reviews for a product |

---

### 10. Wishlist — `/api/wishlist`

> 🔒 Requires `CUSTOMER` role

| Method | Endpoint | Query Params | Description |
|---|---|---|---|
| `POST` | `/api/wishlist/add` | `productId` | Add a product to the wishlist |
| `GET` | `/api/wishlist/` | — | Get the user's wishlist |
| `DELETE` | `/api/wishlist/remove/{productId}` | — | Remove a product from the wishlist |

---

## 🗂️ Models

The application uses the following data models stored in **MongoDB Atlas**:

| Model | Description |
|---|---|
| `User` | Stores user credentials, role, and profile information |
| `Seller` | Extended seller profile linked to a user |
| `Product` | Product details including seller, category, price, and stock |
| `Order` | Order details including items, status, and buyer information |
| `Cart` | Shopping cart linked to a customer user |
| `CartItem` | Individual item entry within a cart |
| `Address` | Delivery address associated with a user |
| `Review` | Product review with rating and comment |
| `Wishlist` | List of saved products for a user |
| `Role` *(Enum)* | Enum defining user roles: `CUSTOMER`, `SELLER`, `ADMIN` |

---

## 🔒 Security

- **Spring Security** — Provides the security filter chain and access control mechanisms.
- **JWT Authentication** — Stateless token-based authentication. Tokens are signed with a secret key and validated on every protected request.
- **Role-Based Access Control (RBAC)** — Endpoints are protected at the method or route level based on the user's assigned `Role`.
- **Password Encoding** — Passwords are stored securely using BCrypt hashing.
- **Stateless Sessions** — The application does not use HTTP sessions; all state is carried in the JWT.

---

## ⚙️ Installation & Setup

### Prerequisites

Ensure the following are installed on your system:

- [Java 17+](https://adoptium.net/)
- [Maven 3.8+](https://maven.apache.org/)
- A [MongoDB Atlas](https://www.mongodb.com/cloud/atlas) account with a cluster configured

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/multi-vendor-ecommerce-backend.git
cd multi-vendor-ecommerce-backend
```

### 2. Configure Environment Variables

Create or update `src/main/resources/application.properties` (or `application.yml`) with your configuration:

```properties
# MongoDB Atlas
spring.data.mongodb.uri=mongodb+srv://<username>:<password>@<cluster>.mongodb.net/<database>?retryWrites=true&w=majority

# JWT Configuration
app.jwt.secret=your_jwt_secret_key
app.jwt.expiration=86400000

# Server Port
server.port=8080
```

> ⚠️ **Never commit sensitive credentials to version control.** Use environment variables or a secrets manager in production.

### 3. Install Dependencies

```bash
mvn clean install
```

---

## 🚀 Running the Application

### Run with Maven

```bash
mvn spring-boot:run
```

### Run the JAR directly

```bash
mvn clean package
java -jar target/multi-vendor-ecommerce-backend-0.0.1-SNAPSHOT.jar
```

The API will be accessible at:

```
http://localhost:8080
```

### Testing the API

You can test the endpoints using tools like:

- [Postman](https://www.postman.com/)
- [Insomnia](https://insomnia.rest/)
- [curl](https://curl.se/)

**Example — Register a new user:**
```bash
curl -X POST http://localhost:8080/auth/signup \
  -H "Content-Type: application/json" \
  -d '{"username": "john", "email": "john@example.com", "password": "secret123"}'
```

**Example — Login and retrieve token:**
```bash
curl -X POST http://localhost:8080/auth/signin \
  -H "Content-Type: application/json" \
  -d '{"email": "john@example.com", "password": "secret123"}'
```

---

<div align="center">
  <sub>Built with ☕ Java & Spring Boot</sub>
</div>
