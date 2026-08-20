# Research: SUN Booking Tours Architecture & Stack

## 1. Full-Stack Architecture

- **Decision**: Separated Full-Stack Web Application (React frontend, Spring Boot REST API backend).
- **Rationale**: Clean separation of concerns. The frontend and backend are independent codebases communicating exclusively via REST API.

## 2. Frontend Technology Stack

- **Decision**: React 19.2.x + Vite 8.1.x + TypeScript + Tailwind CSS 4.x + React Router.
- **Rationale**: Fast, modern frontend development. Node.js compatibility must align with Vite 8 requirements.
- **State Management**: Built-in React State and Context. No Redux is required.

## 3. Backend Technology Stack

- **Decision**: Java 21 (LTS) and Spring Boot 4.1.0.
- **Rationale**: Java 21 is the approved LTS Java version for this project, providing mature features and strong Spring Boot ecosystem support. Spring Boot 4.1.0 provides the required backend capabilities.

## 4. Authentication, Token Storage, and JWT Flow

- **Decision**: Stateless SUN Booking JWT + HttpOnly Secure Cookie + CSRF protection.
- **Conceptual Flow**:
  1. User authenticates.
  2. Spring Boot authenticates identity.
  3. Backend creates SUN Booking JWT.
  4. Backend sends JWT using an HttpOnly Secure cookie.
  5. Browser stores the cookie automatically.
  6. React sends API requests with credentials enabled.
  7. Browser automatically includes the authentication cookie.
  8. Backend extracts and validates JWT (using a custom `BearerTokenResolver` or equivalent cookie-aware mechanism for Spring Security OAuth2 Resource Server).
  9. Spring Security enforces authorization.
- **Note**: React MUST NOT access the HttpOnly JWT directly. JWT storage is strictly via HttpOnly cookie, not localStorage.

## 5. CSRF Strategy

- **Decision**: Explicit Spring Security CSRF token mechanism.
- **Rationale**: Because authentication uses cookies, an explicit CSRF strategy is required. The frontend will read the separate CSRF token when required and send it in the appropriate request header for state-changing requests. SameSite cookie configuration will be used as defense-in-depth, not as the sole protection. Safe GET/HEAD/OPTIONS requests remain read-only.

## 6. CORS and Credentials

- **Decision**: Configured frontend origins only, with credentials.
- **Rationale**: Because React/Vite and Spring Boot run as separated applications, CORS must allow only configured frontend origins. Credentials must be permitted where required for authentication cookies. Production must not use wildcard origins for authenticated APIs. Frontend API requests requiring authentication must send credentials. Development configuration must support the Vite frontend origin and Spring Boot backend origin.

## 7. Social Authentication Flow

- **Decision**: Spring Security OAuth2 Client integration.
- **Conceptual Flow**:
  1. React redirects/initiates provider login.
  2. Spring Security OAuth2 Client performs the provider authorization flow.
  3. Backend receives the authenticated provider identity.
  4. Backend maps or creates User and SocialAccount.
  5. Backend issues the SUN Booking JWT cookie.
  6. Frontend continues using SUN Booking authentication.
- **Supported Providers**: Google, Facebook, Twitter/X. Provider access tokens will not be stored unnecessarily.

## 8. Payment Integration (SePay Test Mode)

- **Decision**: SePay Test Mode + VietQR + Webhook integration. Capacity is reserved for 15 minutes during checkout to prevent overbooking.
- **Rationale**: SePay provides a seamless VietQR generation and webhook-based confirmation flow.
- **Payment Architecture**:
  1. User has an eligible Booking.
  2. User initiates checkout. Backend temporarily reserves capacity for 15 minutes.
  3. Backend creates/prepares Payment and generates VietQR code (via SePay formatting) embedding a unique transaction reference.
  4. User scans and pays via internet banking.
  5. SePay Webhook calls backend `/api/payments/webhook` with the transaction result.
  6. Backend verifies webhook signature, extracts reference, confirms Payment, and secures the capacity reservation permanently.
  7. If unpaid after 15 minutes, capacity is released automatically.
- **Security**: The frontend must never be allowed to mark a payment as SUCCESS directly. The backend remains authoritative for payment state via the Webhook. Booking ownership must be validated. Payment provider secrets (Webhook secret) must remain backend-only. External payment results must be verified via SePay webhook signature verification. Payment processing must prevent duplicate state changes.
- **Business Clarification**: User Bank Account Management has been explicitly excluded from the scope. Do not store or manage user bank accounts.

## 9. Database Mapping & Revenue

- **Decision**: Spring Data JPA using Hibernate. Revenue calculated dynamically.
- **Rationale**: Revenue is precisely queried by summing `amount` from `payment` where `status = 'SUCCESS'`. No separate `revenue` table is required.
