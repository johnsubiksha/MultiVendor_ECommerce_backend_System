# 🛒 MultiVendor E-Commerce Backend System

A scalable backend system for a multi-vendor e-commerce platform built using Spring Boot and MongoDB. It supports Customer, Seller, and Admin roles with secure JWT-based authentication and role-based access control.

---

## 🌟 Features

* 🔐 JWT-based Authentication & Authorization
* 👥 Role-Based Access (Customer, Seller, Admin)
* 🛍️ Product Management (Add, Update, Delete)
* ⭐ Product Reviews & Ratings
* 🛒 Shopping Cart Management
* 📦 Order Placement & Tracking
* 🧾 Seller Order Handling
* 🛠️ Admin Controls & Management
* ⚡ Scalable RESTful APIs

---

## 🛠️ Tech Stack

**Backend**

* Spring Boot
* Java

**Database**

* MongoDB

**Security**

* Spring Security
* JWT (JSON Web Token)

---

## 📌 System Architecture

* RESTful API-based backend
* Role-based request handling
* Secure endpoints using JWT authentication
* MongoDB for flexible and scalable data storage

---

## 🔑 Key Functionalities

### 1. Authentication & Authorization

* User registration and login
* JWT token generation and validation
* Role-based access control

---

### 2. Product Management (Seller)

* Add new products
* Update product details
* Delete products
* View seller-specific products

---

### 3. Cart Management (Customer)

* Add items to cart
* Remove items from cart
* Update quantities

---

### 4. Order Management

* Place orders
* View order history
* Track order status

---

### 5. Seller Order Handling

* Sellers can view orders for their products
* Update order status (Processing, Shipped, Delivered)

---

### 6. Product Reviews & Ratings

* Customers can add reviews and ratings
* View product ratings and feedback

---

### 7. Admin Controls

* Manage users (Customers & Sellers)
* Monitor products and orders
* System-level control

---

## 🔌 API Endpoints (Sample)

### Authentication

* POST /auth/register
* POST /auth/login

### Products

* GET /products
* POST /products
* PUT /products/{id}
* DELETE /products/{id}

### Cart

* POST /cart/add
* DELETE /cart/remove

### Orders

* POST /orders
* GET /orders

---

## ⚙️ Installation & Setup

1. Clone the repository:

```bash
git clone [https://github.com/Vishvapriya-24/your-repo-name](https://github.com/johnsubiksha/MultiVendor_ECommerce_backend_System)
```

---
