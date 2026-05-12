# Expense Tracker Console Application

A lightweight Java console application designed to manage and track daily expenses. The project follows clean code principles and provides a robust foundation for personal financial management.

## Features

- **Add Expenses:** Easily record new expenses with details like name, shop, price, date, and category.
- **View & Filter:** Browse through your expenses with advanced filtering by name, shop, date range, and expense type.
- **Update & Delete:** Full CRUD (Create, Read, Update, Delete) operations for managing your expense history.
- **Data Persistence:** Expenses are automatically saved to and loaded from a `expenses.json` file using the Jackson library.
- **Data Validation:** Built-in checks to ensure all financial data (like prices and names) are valid.

## Tech Stack

- **Java 17+**
- **Jackson (JSR310):** For JSON serialization and handling Java 8 Date/Time API.
- **JUnit 5:** Comprehensive test suite covering domain logic and service layers.
- **Maven/Gradle:** (Depending on your build tool).

## Project Structure

- `domain`: Contains core entities like `Expense` and `ExpenseType`.
- `logic`: Contains business logic (`ExpenseService`), data access (`FileExpenseRepository`), and user interface (`Console`).
- `Main.java`: The entry point of the application.

## How to Run

1. Clone the repository.
2. Ensure you have a Java SDK installed.
3. Run the `Main.java` file.
4. Follow the on-screen menu instructions.

## Future Plans (Roadmap) 🚀

The project is currently in its console-based phase, but the following enhancements are planned:

- **Web Migration:** Transforming the application into a full-scale web application using **Spring Boot**.
- **Database Integration:** Replacing JSON file storage with a relational **SQL Database** (PostgreSQL/MySQL) for better data integrity and scalability.
- **REST API:** Implementing endpoints for frontend integration.
- **User Authentication:** Secure login for multiple users to track their individual expenses.

---
*Created as part of the PBM / Personal Finance project.*