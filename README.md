# UnitReadiness

A Java Spring Boot REST API for tracking fictional military equipment and calculating unit equipment readiness.

This project demonstrates backend development using RESTful APIs, database persistence, input validation, and automated testing.

## Features

- Create, retrieve, update, and delete equipment records
- Track equipment status as Fully Mission Capable (FMC) or Non-Mission Capable (NMC)
- Assign equipment to fictional military units
- Calculate equipment readiness percentages
- Validate incoming API requests
- Return appropriate HTTP error responses

## Technology Stack

- Java 21
- Spring Boot 4
- Spring Data JPA / Hibernate
- H2 Database
- Maven
- JUnit 5

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/equipment` | Retrieve all equipment |
| GET | `/api/equipment/{id}` | Retrieve equipment by ID |
| POST | `/api/equipment` | Create equipment |
| PUT | `/api/equipment/{id}` | Update equipment |
| DELETE | `/api/equipment/{id}` | Delete equipment |
| GET | `/api/equipment/readiness` | Calculate readiness statistics |

## Example Equipment

```json
{
  "name": "AN/PRC-152 Radio",
  "equipmentType": "Communications",
  "status": "FMC",
  "assignedUnit": "DEMO-001"
}
```

## Running the Application

Requirements: Java 21.

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

The API will be available at `http://localhost:8080`.

## Running Tests

```powershell
.\mvnw.cmd test
```

The project includes automated tests covering database operations, readiness calculations, input validation, and HTTP endpoint behavior.

## Disclaimer

UnitReadiness is an independent educational portfolio project. It uses fictional equipment and unit information and does not contain actual military readiness data.