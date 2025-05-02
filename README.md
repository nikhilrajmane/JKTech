**JKTech-backend Data-Ingestion Application**

a) This repository contains the backend services for the Ingestion application, designed with a microservices architecture. 
b) It leverages Docker for orchestration which post management, an API gateway, and database management using PostgreSQL.

* The backend is structured into multiple services to promote scalability and maintainability:

a) API Gateway: Manages incoming requests and routes them to the appropriate services.
b) Post Service: Manages operations related to posts.
c) PostgreSQL: SQL database for relational data storage.

**Services**

a) API Gateway
Located in the api-gateway directory, this service serves as the entry point for clients. It routes requests to the appropriate backend services and handles concerns such as load balancing and rate limiting.

b) Post Service
Situated in the post-service directory, this service handles all operations related to posts, including creation, retrieval, updating, and deletion.

**Databases**

*PostgreSQL
Used for storing relational data.
Defined in docker.yml as the postgres service.
pgAdmin is included for managing PostgreSQL databases via a web interface.

**PostgreSQL Configuration**

a) Environment variables for the database (username, password, database name) are set in application.yml.
b) pgAdmin allows database administration via a web UI, accessible at http://localhost:5432.


**Running the Application**

To run the application locally:

a) Ensure Docker Desktop are installed on your system.
b) Clone the repository:
https://github.com/nikhilrajmane/JKTech.git

**Screenshots**
![Search](https://github.com/user-attachments/assets/559e90a7-2cab-46ed-887c-ada00237c7bf)
![filterFunctionality](https://github.com/user-attachments/assets/fbbc47d1-2b1c-4e41-a453-af10060de74a)
![Upload](https://github.com/user-attachments/assets/06017e2d-dd7b-4d5e-b03e-2c2cd4588474)

**Curls**

1) curl -X POST "http://localhost:9090/ingestion/api/uploads/upload?author=pooja" -H "accept: */*" -H "Content-Type: multipart/form-data" -F "file=@Panel Expectations from Coding Exercise.pdf;type=application/pdf"

2) curl -X GET "http://localhost:9090/ingestion/api/uploads/search?query=coding" -H "accept: */*"

3) curl -X GET "http://localhost:9090/ingestion/api/uploads/filter?after=2025-04-28%2022%3A24%3A05.96&author=pooja&before=2025-04-30%2022%3A24%3A05.96&page=0&size=10&sort=uploadTime&type=application%2Fpdf" -H "accept: */*"



