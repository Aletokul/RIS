**SRB**

# RIS (Razvoj Informacionih Sistema) – Sistem za rezervaciju sportskih terena

Spring Boot aplikacija razvijena u okviru studentskog projekta.  
Aplikacija omogućava upravljanje sportskim terenima, korisnicima i rezervacijama, kao i generisanje izveštaja.

---

## Tehnologije
- Java
- Spring Boot (Web, JPA, Validation)
- Maven (multi-module projekat)
- MySQL
- JSP / JSTL
- JasperReports

---

## Struktura projekta
Projekat je organizovan kao **Maven multi-module** aplikacija:
- **RezervacijeJPA** – JPA modul koji sadrži entitete i repozitorijume
- **demo** – glavni Spring Boot modul (kontroleri, servisi i JSP prikaz)

---

## Funkcionalnosti
- Registracija i prijava korisnika
- Upravljanje sportskim terenima
- Kreiranje i otkazivanje rezervacija
- Pregled rezervacija
- Generisanje izveštaja (JasperReports)

---

## Konfiguracija baze
Za pokretanje aplikacije potrebno je podesiti MySQL bazu napraviti bazu pod nazivom `ris`

Aplikacija je dostupna na adresi: http://localhost:8080

<br>

---

<br>

**ENG**

# ISD (Information Systems Development) – Sports Field Reservation System

Spring Boot application developed as part of a student project.
The application enables management of sports fields, users, and reservations, as well as report generation.

---

## Technologies
- Java
- Spring Boot (Web, JPA, Validation)
- Maven (multi-module projekat)
- MySQL
- JSP / JSTL
- JasperReports

---

## Project Structure

The project is organized as a Maven multi-module application:
- **RezervacijeJPA** – JPA module containing entities and repositories
- **demo** – main Spring Boot module (controllers, services, and JSP views)

---

## Features
- User registration and login
- Sports field management
- Reservation creation and cancellation
- Reservation overview
- Report generation (JasperReports)

---

## Database Configuration
To run the application, it is necessary to configure a MySQL database and create a database named `ris`.

The application is available at: http://localhost:8080
