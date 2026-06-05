# ✅ TaskManager

Ein einfacher Task Manager als Spring Boot Projekt mit Login & Registrierung.

## Tech Stack

| Schicht     | Technologie                          |
|-------------|--------------------------------------|
| Backend     | Java 21, Spring Boot 3, Spring MVC   |
| Persistenz  | Spring Data JPA, Hibernate           |
| Datenbank   | H2 (Dev), MySQL (Produktion)         |
| Migration   | Flyway                               |
| Frontend    | Thymeleaf, HTML, CSS, Vanilla JS     |
| Security    | Spring Security, BCrypt, CSRF        |
| Build       | Maven                                |

## Starten (Entwicklung – H2)

```bash
./mvnw spring-boot:run
```

Dann öffne: http://localhost:8080

## Starten mit MySQL

1. MySQL-Datenbank anlegen: `CREATE DATABASE taskmanager;`
2. `application-mysql.properties` anpassen
3. Starten:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=mysql
```

## Features

- ✅ Registrierung & Login (Spring Security + BCrypt)
- ✅ CSRF-Schutz
- ✅ Task erstellen, bearbeiten, löschen
- ✅ Status-Filter (Offen / In Bearbeitung / Erledigt)
- ✅ Prioritäten (Hoch / Mittel / Niedrig)
- ✅ Fälligkeitsdatum
- ✅ Datenbankmigrationen mit Flyway

## Projektstruktur

```
src/main/java/com/example/taskmanager/
├── config/          SecurityConfig
├── controller/      AuthController, TaskController
├── model/           User, Task
├── repository/      UserRepository, TaskRepository
├── security/        CustomUserDetailsService
└── service/         UserService, TaskService

src/main/resources/
├── db/migration/    V1__init_schema.sql
├── static/          css/, js/
└── templates/       auth/, tasks/
```
