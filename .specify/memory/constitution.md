<!--
Sync Impact Report
- Version: [CONSTITUTION_VERSION] -> 1.0.0
- Modified Principles: Initialized 17 core principles.
- Added Sections: Project Context, Core Principles, Governance.
- Removed Sections: None (first version).
- TODOs: None.
-->

# SUN Booking Tours Constitution

## Project Context
SUN Booking Tours is a tour booking system.
This constitution defines the mandatory engineering principles, architectural boundaries, quality standards, and development rules that must be followed throughout the entire project.

## Core Principles

### I. Requirement-First Development
* All implementation must be based on approved project requirements and specifications.
* Do not invent business features that are not explicitly required.
* Do not remove, reinterpret, or silently modify approved requirements.
* If a requirement is ambiguous, it must be clarified before implementation.
* If existing code conflicts with the approved specification, the conflict must be identified before modifying the implementation.
* Requirements must define system behavior before implementation decisions are made.
* The approved specification is the primary source of truth for system functionality.

### II. Scope Control
SUN Booking Tours contains three primary user roles:
* Guest
* User
* Admin

Only functionality defined by the approved SUN Booking requirements may be implemented.
The development team must avoid unnecessary features, speculative functionality, implementing future features that have not yet been requested, and introducing additional business processes without an approved requirement. Keep the system focused on the required SUN Booking functionality. When uncertain whether functionality belongs to the project scope, clarification must occur before implementation.

### III. Simple and Maintainable Design
* Prefer the simplest solution that fully satisfies the approved requirements.
* Avoid over-engineering, unnecessary abstraction, and unnecessary modules, services, entities, interfaces, layers, and database tables.
* Reuse existing project structures when they already satisfy the requirement correctly.
* Do not restructure working parts of the system without a clear reason.
* Each class, method, module, and component should have a clear responsibility.
* Complexity must be justified by an actual project requirement.
New architectural components must solve a real problem rather than being introduced only because a particular design pattern exists.

### IV. Java Backend Requirement
The backend of SUN Booking Tours must be implemented using Java.
* Core backend functionality, business logic, authentication and authorization logic, booking-related logic, payment-related logic, and review, comment, like, and rating business logic must be implemented on the backend.
* Administrative operations must be enforced by backend authorization.
* Backend code must follow standard Java naming conventions and object-oriented design principles. Java classes and methods should have focused responsibilities. Avoid unnecessary inheritance and excessive abstraction. Prefer clear and maintainable Java code over overly complex design patterns.

The exact Java version, framework, ORM, database, build tool, authentication technology, testing framework, and deployment technology must be defined later in the implementation plan. Java must not be replaced by another backend programming language unless the approved project requirements are explicitly changed.

### V. Separation of Responsibilities
Business logic must not be placed directly inside controllers or user-interface components.
Controllers should primarily:
1. Receive requests.
2. Validate request data.
3. Verify authentication and authorization when required.
4. Call the appropriate business/service logic.
5. Return an appropriate response.

Business rules must have a clear source of truth. The same business rule must not be duplicated across multiple controllers, services, or modules.
Important business areas requiring clear separation include: Authentication, Authorization, Tour management, Booking, Booking cancellation, Payment, User profile management, Review management, Comments, Likes, Ratings, Administrative management, Revenue management.
Persistence-related implementation must not unnecessarily contain business rules.

### VI. Role-Based Authorization
The system contains the following primary roles:
- **Guest**: A Guest represents a user who has not authenticated. Guest functionality must not provide access to authenticated User or Admin operations.
- **User**: A User represents an authenticated customer of SUN Booking Tours. A User must not be able to access administrative functionality. A User must only manage resources they are authorized to manage.
- **Admin**: Admin functionality must be restricted to authorized administrators.

Authorization must be enforced by the backend. Hiding functionality in the frontend is not sufficient authorization. Every protected operation must verify that the current user has the required permission before the operation is executed.

### VII. Database Integrity
The database must accurately represent the approved SUN Booking business requirements.
The database design must use appropriate: Primary keys, Foreign keys, Unique constraints, Required/not-null constraints, Relationship constraints, Data validation constraints where appropriate.
Relationships between entities must be explicit and consistent. Avoid duplicated and redundant data whenever practical. Database integrity must not depend only on frontend validation.
Do not create a new database entity or table when an existing model can correctly and clearly satisfy the requirement. Every newly introduced entity or table must have a clear business or technical justification. Changes to the ERD or database schema must be justified by an approved requirement.
Do not introduce separate entities or tables such as Cancellation, Refund, Booking Status History, Payment History, Audit History unless an approved or clarified requirement explicitly requires such a separate entity. A business action does not automatically require a separate database table.

### VIII. Booking Consistency
Booking operations must maintain valid and consistent system data.
The system must prevent invalid booking operations. Booking-related state changes must follow the approved business rules. The system must not leave a booking in a contradictory or partially updated state. Booking rules must not be invented during implementation. If the allowed booking workflow is unclear, it must be clarified in the specification before implementation. Tour booking and tour cancellation must respect all approved booking rules.

### IX. Payment Consistency
Payment operations must maintain valid and consistent system data.
The SUN Booking requirement specifies payment by internet banking. The implementation must not invent additional payment methods unless they are later approved. Payment operations must not leave booking and payment information inconsistent. Invalid payment transitions must be prevented. Sensitive payment-related information must not be exposed unnecessarily. The implementation must not assume refund functionality unless refund behavior is explicitly defined by an approved requirement.

### X. Authentication and Security
Authentication and authorization must be securely implemented.
Sensitive values must never be hard-coded in source code, including: Passwords, API keys, Access tokens, OAuth credentials, Database passwords, Secret keys, Private credentials.
Secrets must be provided through appropriate environment or secure configuration mechanisms. Passwords must never be stored as plain text. All protected backend operations must verify authentication and authorization. Third-party authentication credentials must be handled securely.
The project requirements may include authentication through Facebook, Twitter, Google. Implementation details for these providers must be defined during technical planning.

### XI. Input Validation and Error Handling
All external input must be validated.
This includes input originating from: Forms, API requests, URL parameters, Query parameters, Authentication providers, Payment-related requests, Administrative operations.
Invalid input must be rejected safely. Validation must occur on the backend even if frontend validation already exists. Errors must be handled predictably.
Error responses must not expose sensitive internal information such as: Passwords, Secrets, Tokens, Database credentials, Internal stack traces in production.
Business validation errors should be distinguishable from unexpected system errors.

### XII. Code Quality
Code must be readable, consistent, maintainable, testable, easy to understand.
Names for the following must clearly communicate their purpose: Classes, Methods, Variables, APIs, Database tables, Database columns, DTOs, Services, Repositories.
Avoid: Duplicate code, Duplicate business logic, Large methods with multiple unrelated responsibilities, Unclear naming, Dead code, Unnecessary comments explaining obvious code, Excessive abstraction, Premature optimization.
Code should communicate intent clearly.

### XIII. Testing
Critical functionality must receive appropriate automated testing.
Priority areas include: Authentication, Authorization, User permissions, Tour booking, Tour cancellation, Payment, Review management, Comment functionality, Like functionality, Rating functionality, Administrative operations, Important database constraints, Important business rules.
Testing must include important failure scenarios, not only successful scenarios. Tests should verify business behavior rather than merely confirming that methods execute. Changes to critical business logic should include corresponding tests whenever practical.

### XIV. Existing Project Preservation
SUN Booking Tours is an existing project.
Before changing existing architecture or implementation: Inspect the relevant existing code. Understand the current behavior. Understand existing database relationships. Identify dependencies. Determine whether the existing implementation already satisfies the requirement.
Existing working functionality should be preserved unless an approved requirement requires modification. Do not rewrite working components unnecessarily. Do not restructure the entire project merely to follow a preferred architecture. Prefer incremental improvement over unnecessary rewriting. Existing project conventions should be preserved when they remain reasonable and do not conflict with this constitution.

### XV. ERD and Data Model Governance
The ERD must be derived from actual SUN Booking requirements.
Do not design database entities solely because an activity exists. For example: "Cancel tour" does not automatically require a `Cancellation` table. A change in booking status does not automatically require a `BookingStatusHistory` table. Payment does not automatically require multiple payment-history tables. Review management does not automatically require separate entities for every review action.
Before adding an entity, determine whether it represents a distinct business object requiring independent persistence. If an existing entity can satisfy the requirement clearly and correctly, prefer the existing entity.
Any proposed ERD change must state:
1. Which approved requirement requires the change.
2. Why the current model cannot satisfy that requirement.
3. What relationship the new entity has with existing entities.

### XVI. Specification Is the Source of Truth
Approved specifications define the expected behavior of SUN Booking Tours.
The project must follow this workflow:
**Constitution → Specification → Clarification → Plan → Checklist → Tasks → Analysis → Implementation → Convergence**
Important unresolved requirements must be clarified before implementation begins. Implementation must not silently resolve major business ambiguity. When Constitution, Specification, Plan, Tasks, ERD, and source code disagree, the inconsistency must be explicitly identified and resolved. The implementation must ultimately conform to the approved specification and this constitution.

### XVII. Change Discipline
Any significant project change must be traceable to one of the following: An approved requirement, A clarified requirement, A confirmed bug, A necessary technical change, A security requirement, A data-integrity requirement.
Do not introduce unrelated refactoring while implementing a feature unless required for correctness. When a proposed change increases complexity, the reason must be clear.
When multiple solutions satisfy the requirements, prefer the solution that:
1. Introduces less unnecessary complexity.
2. Preserves more existing working functionality.
3. Is easier to understand and maintain.
4. Maintains database integrity.
5. Respects the approved specification.

## Governance

### XVIII. Constitution Authority
This constitution applies to the entire SUN Booking Tours project.
All specifications, implementation plans, tasks, architecture decisions, database changes, and source-code modifications must comply with these principles.
If a later project decision conflicts with this constitution, the conflict must be explicitly addressed rather than silently ignored.
Changes to this constitution should only be made when the project's agreed engineering principles or mandatory technical constraints genuinely change.

**Version**: 1.0.0 | **Ratified**: 2026-08-17 | **Last Amended**: 2026-08-17
