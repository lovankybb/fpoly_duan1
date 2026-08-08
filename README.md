# Project 1 - FPT Polytechnic (fpoly_duan1)

![FPT Polytechnic](https://img.shields.io/badge/FPT%20Polytechnic-Project%201-orange?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Completed-success?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)

---

## 📌 Overview

**Project 1 (fpoly_duan1)** is a practical, capstone-level course at **FPT Polytechnic**. It requires students to
integrate knowledge of system analysis and design, database management, and software development to build a complete,
real-world application.

> **Project  Name:** Atelier - E-commerce website for technology products

---

## Key Features

- **Authentication & Authorization:** Login, logout, role management (Admin, Staff), password reset, and password
  recovery.
- **Product & Service Management:** Add, update, delete, search, and filter product categories.
- **Sales & Invoice Processing:** Create invoices, process payments, apply discount codes, and print receipts.
- **Customer Management:** Maintain detailed user profiles and transaction histories.
- **Analytics & Reporting:** Track revenue, new user, product.
---

## Tech Stack

### Backend / Core

- **Programming Language:** Java (JDK  17), JSP

### Database

- **DBMS:** Microsoft SQL Server
- **Connectivity:** JDBC

### Tools & IDEs

- **IDE:** NetBeans / IntelliJ IDEA / Eclipse / VS Code
- **Version Control:** Git & GitHub

## How to run
### Set up
- Clone this project: 
  ``git clone https://github.com/lovankybb/fpoly_duan1``
- Create file `application.properties` at `/src/main/resources/application.properties`.
```
#application.properties

app.jwt.secret-key=
# The JWT token duration in days
app.jwt.duration=10

app.datasource.database=
app.datasource.username=sa
app.datasource.password=

app.static.image.path=


#VNPay

app.vnpay.url=
app.vnpay.return-url=
app.vnpay.tmn-code=
app.vnpay.hash-secret=

```

### Run project
Run as a normal Dynamic Java Web Project

