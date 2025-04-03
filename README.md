# 🛍️ SalesSavvy - Backend  

SalesSavvy is a **full-stack e-commerce platform** built with **Spring Boot** and **MySQL**. This repository contains the **backend**, which handles user authentication, product management, order processing, and payment integration via **Razorpay**.  

## 🚀 Features  

### 👥 User Management  
- **User Authentication**: JWT-based authentication with secure login & registration.  
- **Role-Based Access Control**: Separate access for customers and admins.  

### 🏬 Product & Order Management  
- **Product Management**: CRUD operations for products and categories.  
- **Order Processing**: Customers can place orders, and admins can manage them.  
- **Payment Integration**: Secure payment processing via **Razorpay API**.  

### 📊 Admin Features  
- **Dashboard**: Admins can manage users, orders, and products.  
- **Order Tracking**: View and update order statuses.  

## 🛠️ Tech Stack  
- **Backend**: Spring Boot (Java)  
- **Database**: MySQL  
- **Security**: JWT Authentication  
- **Payment Gateway**: Razorpay API  

## 📦 Installation & Setup  

1. **Clone the Repository**  
   ```sh
   git clone https://github.com/yourusername/SalesSavvy-Backend.git
   cd SalesSavvy-Backend
2. **Configure Database**
   ```sh
   spring.datasource.url=jdbc:mysql://localhost:3306/salessavvy
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
3. **Set Up Environment Variables**
   ```sh
   RAZORPAY_KEY_ID=your_key_id
   RAZORPAY_KEY_SECRET=your_key_secret
   JWT_SECRET=your_jwt_secret

## 🔗 API Endpoints  

| Endpoint            | Method | Description                          |
|----------------------|--------|-------------------------------------|
| `/api/auth/register` | POST   | Register a new user                 |
| `/api/auth/login`    | POST   | Authenticate user and return JWT    |
| `/api/products`      | GET    | Get all products                    |
| `/api/orders`        | POST   | Place an order                      |
| `/api/payment`       | POST   | Process payment via Razorpay        |

🤝 Contributing
Feel free to raise issues or contribute via pull requests!

🔗 Links
Frontend Repo: https://github.com/muzammilahmed10/SalesSavvy-Frontend-

Demo Video: https://drive.google.com/file/d/1uKT9qlT3uxhHhyFZ4Aoa0RJc2nuX3Vwa/view?usp=drive_link
