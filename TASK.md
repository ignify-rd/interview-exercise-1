# Interview Task: Employee Workload Dashboard

**Time limit:** 1 hour
**Company:** Ignify

---

## Background

TaskFlow is an internal task management system. The engineering team has requested an **Employee Workload Dashboard** to help managers see at a glance how work is distributed across the team.

The database is pre-seeded.

---

## Your Task

Implement the Employee Workload Dashboard end-to-end.

### Backend

The endpoint `GET /api/reports/workload` is wired up in `ReportController` and delegates to `ReportService.getEmployeeWorkload()`.

Your requirements:

- Each entry in the response must include:
  - Employee full name and email
  - Department name
  - Total task count
  - Active task count (status is not `DONE`)
  - Overdue task count (past due date and not `DONE`)
  - Total estimated hours
  - Completion rate as a percentage

### Frontend

Implement `WorkloadDashboardComponent` located at:

```
frontend/src/app/features/dashboard/workload-dashboard/
```

Requirements:

- Display the workload data in a table
- Add a **filter by department** (departments are available at `GET /api/departments`)
- Clicking an employee row should show **that employee's tasks**

---

## API Reference

| Endpoint                      | Description                    |
|-------------------------------|--------------------------------|
| `GET /api/reports/workload`   | Employee workload summary      |
| `GET /api/employees`          | All employees                  |
| `GET /api/departments`        | All departments                |
| `GET /api/tasks`              | Tasks                          |
| `GET /api/tasks?employeeId=X` | Tasks for a specific employee  |
| `GET /api/projects`           | All projects                   |

---

## Notes

- You may add database migrations — place them in `backend/src/main/resources/db/migration/` following the naming convention `V{n}__description.sql`
- The Angular frontend uses Angular Material. Import whatever components you need.
- You may modify any file in the project
- Focus on correctness first — code cleanliness is secondary

---

# Bài tập Phỏng vấn: Bảng điều khiển Khối lượng công việc của Nhân viên (Employee Workload Dashboard)

**Thời gian làm bài:** 1 giờ
**Công ty:** Ignify

---

## Bối cảnh

TaskFlow là một hệ thống quản lý công việc nội bộ. Đội ngũ kỹ thuật đã yêu cầu tạo một **Bảng điều khiển Khối lượng công việc của Nhân viên** nhằm giúp quản lý quan sát nhanh cách thức phân bổ công việc trong nhóm.

Cơ sở dữ liệu đã được tạo sẵn.

---

## Nhiệm vụ của bạn

Xây dựng Bảng điều khiển Khối lượng công việc của Nhân viên từ đầu đến cuối.

### Backend

API endpoint `GET /api/reports/workload` đã được định tuyến sẵn trong `ReportController` và gọi sang `ReportService.getEmployeeWorkload()`.

Yêu cầu cho bạn:

- Mỗi bản ghi trong kết quả trả về phải bao gồm:
  - Họ tên đầy đủ và email của nhân viên
  - Tên phòng ban
  - Tổng số lượng nhiệm vụ
  - Số lượng nhiệm vụ đang hoạt động (trạng thái khác `DONE`)
  - Số lượng nhiệm vụ quá hạn (đã qua ngày đến hạn và trạng thái khác `DONE`)
  - Tổng số giờ ước tính
  - Tỷ lệ hoàn thành tính theo phần trăm

### Frontend

Viết code cho `WorkloadDashboardComponent` tại thư mục:

```
frontend/src/app/features/dashboard/workload-dashboard/
```

Yêu cầu:

- Hiển thị dữ liệu khối lượng công việc theo dạng bảng
- Thêm tính năng **lọc theo phòng ban** (danh sách phòng ban lấy từ `GET /api/departments`)
- Khi nhấp vào hàng của một nhân viên cụ thể, sẽ hiển thị **các nhiệm vụ của nhân viên đó**

---

## Tham khảo API

| Endpoint                      | Mô tả                            |
|-------------------------------|----------------------------------|
| `GET /api/reports/workload`   | Tóm tắt khối lượng công việc NV  |
| `GET /api/employees`          | Tất cả nhân viên                 |
| `GET /api/departments`        | Tất cả phòng ban                 |
| `GET /api/tasks`              | Nhiệm vụ                        |
| `GET /api/tasks?employeeId=X` | Nhiệm vụ của một nhân viên cụ thể|
| `GET /api/projects`           | Tất cả dự án                     |

---

## Ghi chú

- Bạn có thể thêm các database migrations — đặt chúng vào thư mục `backend/src/main/resources/db/migration/` theo quy ước đặt tên `V{n}__description.sql`
- Frontend Angular sử dụng thư viện Angular Material. Bạn hãy tự thêm (import) bất kỳ component nào cần thiết.
- Bạn có quyền chỉnh sửa bất kỳ file nào trong dự án
- Hãy tập trung vào tính chính xác trước — độ sạch của mã (code cleanliness) là ưu tiên thứ cấp
