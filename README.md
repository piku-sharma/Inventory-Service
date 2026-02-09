Inventory Service is a backend microservice responsible for managing product stock, warehouse inventory and availability.  
It exposes CRUD and stock-management APIs that can be used by other services (e.g., order, catalog) in a distributed system.

## 🚀 Features

- Manage inventory records by productId
- Track stock availability and quantities
- Increase / decrease stock with concurrency safety
- RESTful API endpoints for service integration
- Unit test coverage for core functionality
- Clean package structure for easy maintenance

## 🧠 Tech Stack

- **Language:** Java  
- **Framework:** Spring Boot (assumed standard for Java backend)  
- **Build Tool:** Maven / Gradle  
- **Database:** PostgreSQL / MySQL (configurable via application properties)  
- **JSON Mapping:** Jackson  
- **Testing:** JUnit / Mockito

## 🔌 API Endpoints

sample curl call:
get:
curl --location --request GET 'http://localhost:8080/order/all' \
--header 'Content-Type: application/json' \
--data-raw '{ 
"productId": 1002, 
"quantity": 3 
} '

put:
curl --location --request POST 'http://localhost:8091/inventory/update' \
--header 'Content-Type: application/json' \
--data-raw '{ 
"productId": 1002, 
"quantity": 27 
} '

## 🛠️ Environment Setup

### Prerequisites

- Java 17+
- Maven / Gradle
- A relational database (PostgreSQL / MySQL)

### Clone & Build

```bash
git clone https://github.com/piku-sharma/Inventory-Service.git
cd Inventory-Service
To build with Maven:

./mvnw clean install
Run Locally
./mvnw spring-boot:run
