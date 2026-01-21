# Nearby Places Finder - Backend

A Spring Boot REST API for finding nearby places using geospatial queries with PostgreSQL and PostGIS.

## Features

- Find places within a specified radius
- Filter by place type and keyword search
- Distance-based sorting
- Redis caching for performance
- PostGIS spatial indexing for efficient queries

## Quick Start

### Prerequisites

- Docker and Docker Compose
- Java 11+ (JDK required)
- Maven (or use included Maven wrapper)

### Running the Application

1. **Start Infrastructure Services (Database & Redis):**
```bash
docker-compose up -d
```

2. **Run the Spring Boot Application:**

**Windows:**
```bash
.\mvnw.cmd spring-boot:run
```

**Linux/Mac:**
```bash
./mvnw spring-boot:run
```

3. **The API will be available at `http://localhost:8080`**

4. **Health check:**
```bash
curl http://localhost:8080/api/v1/places/health
```

### Stopping the Application

- **Stop Spring Boot App:** Press `Ctrl+C` in the terminal
- **Stop Infrastructure:** `docker-compose down`

### API Endpoints

#### Get Nearby Places
```
GET /api/v1/places/nearby?lat=10.77&lng=106.69&radius=3000&type=restaurant&q=coffee&limit=10
```

Parameters:
- `lat` (required): Latitude (-90 to 90)
- `lng` (required): Longitude (-180 to 180)
- `radius` (optional): Search radius in meters (default: 3000, max: 20000)
- `type` (optional): Filter by place type
- `q` (optional): Keyword search in place names
- `limit` (optional): Maximum results (default: 50, max: 200)

#### Get Place Details
```
GET /api/v1/places/{id}
```

### Sample Data

The database is initialized with sample places in Ho Chi Minh City area:
- ABC Coffee (restaurant)
- City Hospital (hospital)
- Quick ATM (atm)
- Pizza Palace (restaurant)
- Metro Pharmacy (pharmacy)

### Development

#### Local Development Setup

The application is configured for local development with Docker handling only the infrastructure:

1. **Infrastructure (Database & Redis):**
```bash
docker-compose up -d
```

2. **Application (Spring Boot):**
```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux/Mac  
./mvnw spring-boot:run
```

#### Building the Application

```bash
# Windows
.\mvnw.cmd clean package

# Linux/Mac
./mvnw clean package
```

#### Running Tests

```bash
# Windows
.\mvnw.cmd test

# Linux/Mac
./mvnw test
```

### Architecture

- **Spring Boot 2.7** with Java 11
- **PostgreSQL with PostGIS** for spatial data
- **Redis** for caching
- **Hibernate Spatial** for JPA spatial support
- **Docker Compose** for infrastructure services
- **Maven** for build and dependency management

### Database Schema

The `places` table includes:
- Spatial indexing with PostGIS GIST index
- Geography data type for accurate distance calculations
- Support for place types, ratings, and metadata