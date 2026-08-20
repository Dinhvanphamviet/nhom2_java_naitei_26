# Quickstart & Validation Guide

**Prerequisites**:
- Java 21 (LTS)
- Maven 3.9+
- Node.js (Version compatible with Vite 8 requirements)
- PostgreSQL database running on port 5432.

**Setup**:
1. Run `database/schema.sql` against the local PostgreSQL database to set up the baseline schema.

**Backend**:
1. Navigate to backend: `cd backend`
2. Build the application: `mvn clean install`
3. Start the Spring Boot API: `mvn spring-boot:run`
4. The API will run on `http://localhost:8080`.

**Frontend**:
1. Navigate to frontend: `cd frontend`
2. Install dependencies: `npm install`
3. Start the dev server: `npm run dev`
4. The React application will run on `http://localhost:5173`.

**Validation Scenarios**:
1. **Health Check**: `curl http://localhost:8080/actuator/health` (Should return 200 OK).
2. **Guest Registration**: Navigate to the frontend UI at `http://localhost:5173`, fill out the registration form, and submit.
3. **User Sign In**: Log in through the frontend UI. The backend will issue an HttpOnly Secure JWT cookie.
4. **Tour Search**: View the tours list on the frontend and verify data is loaded via the `/api/tours` endpoint.
5. **SePay Webhook Test**: 
   - Initiate a booking checkout on the frontend.
   - Note the generated transaction reference.
   - Use a tool like Postman to POST a mock JSON payload (matching SePay's webhook format) to `/api/payments/webhook` to simulate a successful transfer and verify the booking status updates to confirmed.
