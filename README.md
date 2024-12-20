# Car Management System

This project is a backend application for managing car details, implemented using Spring Boot. It allows users to perform CRUD operations on car data, including searching, pagination, sorting, and more. It also includes validation and proper exception handling for input data.

## Features
- **CRUD Operations**: Create, Read, Update, and Delete car details.
- **Global Search**: Search cars by name, model, year, color, or fuel type.
- **Pagination & Sorting**: Efficient handling of large datasets.
- **Validation**: Ensure proper data submission for car attributes.
- **API Documentation**: Use of Swagger/OpenAPI to document endpoints.
- **Exception Handling**: Proper handling of errors and invalid inputs.
- - **Swagger Implement**: http://localhost:9090/swagger-ui/index.html


## Tech Stack
- **Backend**: Spring Boot (Java)
- **Database**: MySQL
- **API Documentation**: Swagger (SpringDoc OpenAPI)
- **Cloud Hosting**: Deployed on [Insert Cloud Platform here]

## API Endpoints

### 1. **Create a Car**
- **URL**: `http://localhost:9090/cars/create`
- **Method**: `POST`
- **Request Body**:
    ```json
    {
      "carName": "Toyota",
      "model": "Camry",
      "year": 2023,
      "price": 35000,
      "color": "Red",
      "fuelType": "Petrol"
    }
    ```
- **Response**:
    ```json
    {
        "status": "SUCCESS",
        "data": {
            "carId": "22bf2954-2121-4c54-adce-857eaf95b0e4",
            "name": "Toyota",
            "model": "Camry",
            "year": 2023,
            "price": 35000,
            "color": "Red",
            "fuelType": "Petrol",
            "createDate": "2024-12-19T12:08:24.349556"
        }
    }
    ```

### 2. **Get a Car by ID**
- **URL**: `http://localhost:9090/cars/get/{id}`
- **Method**: `GET`
- **Response**:
    ```json
    {
        "status": "SUCCESS",
        "data": {
            "carId": "22bf2954-2121-4c54-adce-857eaf95b0e4",
            "name": "Toyota",
            "model": "Camry",
            "year": 2023,
            "price": 35000,
            "color": "Red",
            "fuelType": "Petrol",
            "createDate": "2024-12-19T12:08:24.349556"
        }
    }
    ```

### 3. **Get All Cars**
- **URL**: `http://localhost:9090/cars/getAll`
- **Method**: `GET`
- **Response**:
    ```json
    {
        "status": "SUCCESS",
        "data": [
            {
                "carId": "22bf2954-2121-4c54-adce-857eaf95b0e4",
                "name": "Mercedes-benz",
                "model": "2021",
                "year": 2021,
                "price": 20000.0,
                "color": "White",
                "fuelType": "Electric",
                "createDate": "2024-12-19T12:08:24.349556"
            },
            {
                "carId": "da792ce1-5a50-4fc8-9ba6-a913dabfc78a",
                "name": "Audi A4",
                "model": "2025",
                "year": 2025,
                "price": 42000.0,
                "color": "Black",
                "fuelType": "Electric",
                "createDate": "2024-12-19T13:12:25.121515"
            }
        ]
    }
    ```

### 4. **Pagination & Sorting**
- **URL**: `http://localhost:9090/cars/getPaginationAndSorting?offset=0&pageSize=4&field=name&order=ASC`
- **Method**: `GET`
- **Response**:
    ```json
    {
        "status": "SUCCESS",
        "data": [
            {
                "carId": "22bf2954-2121-4c54-adce-857eaf95b0e4",
                "name": "Audi A4",
                "model": "2025",
                "year": 2025,
                "price": 42000.0,
                "color": "Black",
                "fuelType": "Electric",
                "createDate": "2024-12-19T13:12:25.121515"
            }
        ]
    }
    ```

### 5. **Update a Car**
- **URL**: `http://localhost:9090/cars/update/{id}`
- **Method**: `PUT`
- **Request Body**:
    ```json
    {
      "carName": "Toyota",
      "model": "Corolla",
      "year": 2024,
      "price": 33000,
      "color": "Blue",
      "fuelType": "Diesel"
    }
    ```
- **Response**:
    ```json
    {
        "status": "SUCCESS",
        "data": {
            "carId": "22bf2954-2121-4c54-adce-857eaf95b0e4",
            "name": "Toyota",
            "model": "Corolla",
            "year": 2024,
            "price": 33000,
            "color": "Blue",
            "fuelType": "Diesel",
            "createDate": "2024-12-19T12:08:24.349556"
        }
    }
    ```

### 6. **Hard Delete a Car**
- **URL**: `http://localhost:9090/cars/hard-delete/{id}`
- **Method**: `DELETE`
- **Response**:
    ```json
    {
        "status": "SUCCESS",
        "message": "Car deleted successfully."
    }
    ```

### 7. **Delete a Car by Name**
- **URL**: `http://localhost:9090/cars/deleteByName/{name}`
- **Method**: `DELETE`
- **Response**:
    ```json
    {
        "status": "SUCCESS",
        "message": "Car deleted successfully."
    }
    ```

### 8. **Search Cars**
- **URL**: `http://localhost:9090/cars/search?year={year}`
- **Method**: `GET`
- **Query Parameters**:
    - `year`: Search by car year
- **Response**:
    ```json
    {
        "status": "SUCCESS",
        "data": [
            {
                "carId": "22bf2954-2121-4c54-adce-857eaf95b0e4",
                "name": "Mercedes-benz",
                "model": "2021",
                "year": 2021,
                "price": 20000.0,
                "color": "White",
                "fuelType": "Electric",
                "createDate": "2024-12-19T12:08:24.349556"
            }
        ]
    }
    ```

### 9. **Filter Cars**
- **URL**: `http://localhost:9090/cars/filter?sortBy={field}`
- **Method**: `GET`
- **Query Parameters**:
    - `sortBy`: Sort cars by the specified field (e.g., `price`, `year`)
- **Response**:
    ```json
    {
        "status": "SUCCESS",
        "data": [
            {
                "carId": "22bf2954-2121-4c54-adce-857eaf95b0e4",
                "name": "Toyota",
                "model": "Corolla",
                "year": 2024,
                "price": 33000,
                "color": "Blue",
                "fuelType": "Diesel",
                "createDate": "2024-12-19T12:08:24.349556"
            }
        ]
    }
    ```

## Running the Project

1. **Clone the repository**:
    ```bash
    git clone https://github.com/yourusername/car-management-system.git
    ```

2. **Configure the database**:
    - Set up MySQL database and update `application.properties` with database credentials.
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/car_db
    spring.datasource.username=root
    spring.datasource.password=password
    ```

3. **Run the application**:
    ```bash
    ./mvnw spring-boot:run
    ```

4. **API Documentation**: After starting the application, visit Swagger UI for detailed API documentation:
    - URL: `http://localhost:8080/swagger-ui.html`

## Exception Handling and Validation

- **Validation**: Every car attribute is validated using annotations such as `@NotNull`, `@Size`, `@Min`, `@Max`, etc.
- **Global Exception Handler**: Custom exception handling to catch and return user-friendly error messages.
    - For invalid car attributes (e.g., invalid year or price), a `BadRequestException` is thrown.
    - For any unexpected errors, a `InternalServerErrorException` is returned.

### Example of Global Exception Handling
