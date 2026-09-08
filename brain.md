# FoxBrain Project Brain

## Purpose
This file is the permanent project memory and development rules for the FoxBrain Institute Management & Learning Web Application.

## Project Goal
Build a professional, maintainable, production-style web application for FoxBrain Institute with:
- Public institute website
- Student portal
- Teacher/Faculty portal
- Admin panel
- Course, batch, admission and enrollment management
- Attendance
- Assignments
- Examinations and results
- Fees/payment tracking
- Certificates
- Announcements and notifications
- Enquiries
- Reports and dashboards
- Authentication and role-based authorization

## Development Direction

### Initial Stack
- Java
- Eclipse IDE
- Maven
- JSP
- Servlets
- JDBC
- MySQL
- Apache Tomcat
- HTML
- CSS
- JavaScript
- Bootstrap where useful

### Future Enterprise Stack
After the fundamentals are understood, gradually migrate toward:
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA
- Hibernate
- REST APIs

Do not introduce complex technologies without explaining why they are needed.

## Architecture Principle

Initial application flow:

Browser
→ Servlet/Controller
→ Service
→ DAO
→ JDBC
→ MySQL

Use a clean MVC/layered architecture.

Business logic must not be placed directly in JSP pages.

## Package Convention

Use the base package:

`com.foxbrain`

Expected packages:
- controller
- dao
- model
- service
- util
- filter
- exception

Do not randomly rename packages, classes, or important entities between stages.

## Security Rules

Security is part of the architecture from the beginning.

Required:
- Password hashing
- PreparedStatement
- Input validation
- Session management
- Role-based authorization
- Unauthorized-page protection
- Secure logout/session invalidation
- Appropriate output escaping

Never store passwords as plaintext.

## Database Principles

Use a normalized relational MySQL database.

Consider relationships carefully before creating tables.

Important principles:
- Primary keys
- Foreign keys
- Appropriate data types
- Constraints
- Useful indexes
- Referential integrity
- Preserve important historical records
- Prefer status/archiving over destructive deletion for important records

## UI/UX Principles

The application should look like a professional institute system:
- Modern
- Clean
- Responsive
- Mobile-friendly
- Accessible
- Consistent
- Reusable components

Create reusable navbar, footer, sidebar, cards, forms, tables, alerts, modals and dashboard components.

Use actual FoxBrain branding only after it is provided.

## Information Integrity Rule

Never invent real FoxBrain Institute information.

If information has not been confirmed, write:

**PLACEHOLDER — REPLACE WITH REAL FOXBRAIN INFORMATION**

Clearly distinguish:
- Confirmed information
- Proposed design decisions
- Placeholder information

## Development Rule

Build incrementally.

For every implementation stage:
1. Explain what is being built.
2. Explain the architecture.
3. Show folder/package structure.
4. Explain database tables involved.
5. Give required SQL.
6. Give complete required files where practical.
7. State exactly where files belong in Eclipse.
8. Explain how the code works.
9. Explain how to run and test it.
10. Give common errors and fixes.
11. Only then proceed.

Do not dump the entire application into one response.

## Coding Rules

- Prefer complete files over disconnected fragments.
- Always state filename and location.
- Use meaningful names.
- Avoid unnecessary complexity.
- Keep naming consistent.
- Avoid deprecated APIs unless their use is explicitly justified.
- Preserve previous architecture and decisions.
- Do not rewrite working modules unnecessarily.

## Debugging Rule

When an error is provided:
1. Identify the likely cause.
2. Explain the cause.
3. State what to check.
4. Provide the correction.
5. Explain why it fixes the problem.

Do not immediately rewrite the whole application.

## Teaching Rule

Teach concepts using:

**What it is → Why we need it → How it works → Code → How to test it**

Assume the developer is learning Java web development.

Do not assume prior knowledge of:
- Servlets
- JSP
- JDBC
- Maven
- Spring
- Hibernate
- REST APIs
- Enterprise architecture

