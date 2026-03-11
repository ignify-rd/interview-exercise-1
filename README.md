# TaskFlow — Ignify Technical Interview

> **This is a technical interview exercise by [Ignify](https://ignify.io).**

## About

TaskFlow is a team task management system. The codebase is pre-built with full CRUD functionality for employees, projects, departments, and tasks. Your job is to implement a missing feature under time pressure — see **[TASK.md](TASK.md)** for the full brief.

## Stack

| Layer    | Technology                          |
|----------|-------------------------------------|
| Backend  | Java 21, Spring Boot 3.3, JPA, Flyway |
| Database | PostgreSQL 16                       |
| Frontend | Angular 18, Angular Material        |

## Running the Project

**Prerequisites:** Docker and Docker Compose installed.

```bash
docker compose up --build
```

That's it. The first build will take a few minutes as Maven downloads dependencies.

| Service  | URL                         |
|----------|-----------------------------|
| Frontend | http://localhost:4200       |
| Backend  | http://localhost:8080       |
| API docs | http://localhost:8080/api/  |

## Development (without Docker)

**Backend:**
```bash
cd backend
mvn spring-boot:run
```
Requires a local PostgreSQL instance. See `backend/src/main/resources/application.yml` for connection config.

**Frontend:**
```bash
cd frontend
npm install
npm start
```
Runs on http://localhost:4200 with API proxy to http://localhost:8080.

---

# TaskFlow — Bài kiểm tra kỹ thuật Ignify

> **Đây là bài tập phỏng vấn kỹ thuật của [Ignify](https://ignify.io).**

## Giới thiệu

TaskFlow là hệ thống quản lý công việc nhóm. Source code đã được xây dựng sẵn đầy đủ chức năng CRUD cho nhân viên, dự án, phòng ban và nhiệm vụ. Công việc của bạn là tự lập trình một tính năng còn thiếu với thời gian có hạn — xem **[TASK.md](TASK.md)** để biết yêu cầu chi tiết.

## Công nghệ sử dụng

| Tầng (Layer) | Công nghệ                           |
|--------------|-------------------------------------|
| Backend      | Java 21, Spring Boot 3.3, JPA, Flyway |
| Database     | PostgreSQL 16                       |
| Frontend     | Angular 18, Angular Material        |

## Chạy dự án

**Yêu cầu môi trường:** Đã cài đặt Docker và Docker Compose.

```bash
docker compose up --build
```

Chỉ cần vậy thôi. Lần build đầu tiên sẽ mất vài phút do Maven phải tải các thư viện phụ thuộc.

| Dịch vụ  | URL                         |
|----------|-----------------------------|
| Frontend | http://localhost:4200       |
| Backend  | http://localhost:8080       |
| API docs | http://localhost:8080/api/  |

## Phát triển (không dùng Docker)

**Backend:**
```bash
cd backend
mvn spring-boot:run
```
Yêu cầu có sẵn CSDL PostgreSQL chạy ở máy trạm (local). Xem file `backend/src/main/resources/application.yml` để tùy chỉnh cấu hình kết nối.

**Frontend:**
```bash
cd frontend
npm install
npm start
```
Chạy trên http://localhost:4200 với API proxy trỏ đến http://localhost:8080.
