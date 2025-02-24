# Order Management System

## Overview
The **Order Management System** is a Java-based application designed to efficiently manage client orders in a warehouse. It provides an interactive graphical user interface (GUI) for handling clients, products, and orders while dynamically updating product quantities and generating bills automatically. The system follows a **layered architecture** to ensure modularity, maintainability, and scalability.

## Features
- **Client Management**: Add, edit, and delete client records with validation.
- **Product Management**: Manage inventory by adding, updating, and removing products.
- **Order Processing**: Create orders, validate product availability, update stock, and generate bills.
- **Dynamic GUI Updates**: Automatically refreshes tables upon data modification.
- **Transaction Management**: Ensures consistency and atomicity in order creation.
- **Validation Mechanism**: Prevents invalid data entries and enforces business rules.

## Technologies Used
- **Programming Language**: Java
- **GUI Framework**: Swing
- **Database**: MySQL
- **Design Patterns**: DAO (Data Access Object), MVC (Model-View-Controller), Singleton, Strategy Pattern
- **Development Tools**: IntelliJ IDEA, MySQL Workbench

## Architecture
The system is structured into multiple layers:
- **Presentation Layer**: Manages the GUI and user interactions.
- **Business Logic Layer**: Encapsulates core functionality and enforces business rules.
- **Data Access Layer**: Handles database operations via DAO classes.
- **Model Layer**: Defines entities such as Client, Product, Order, and Bill.

## Installation & Setup
1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/order-management-system.git
   ```
2. Import the project into your Java IDE.
3. Configure the MySQL database:
   - Create a new database.
   - Run the provided SQL script to set up tables.
   - Update database credentials in the configuration file.
4. Compile and run the application.


This project demonstrates strong object-oriented programming skills, database interaction, and software architecture principles, making it a valuable addition to my portfolio for software development roles.

