# AI Prompt Log for TechNova Smart E-Shop

## Phase 1: Business Analysis
**Role:** Senior Business Analyst
**Prompt 1:**
"Tôi là một Full-stack Developer kiêm System Analyst. Tôi có một yêu cầu thô từ khách hàng như sau: [Raw Requirements]. Hãy đóng vai Senior BA, bóc tách yêu cầu này thành các Actor, Functional Requirements (FR) và Non-functional Requirements (NFR)."

**Prompt 2:**
"Dựa trên các yêu cầu đã bóc tách, hãy sinh mã Mermaid cho:
1. Sơ đồ Use Case tổng quan.
2. Sơ đồ ERD bao gồm các bảng: Users, Roles, Products, Orders, Order_Items với các quan hệ chuẩn hóa."

**Prompt 3:**
"Hãy tổng hợp tất cả các thông tin trên thành một tài liệu SRS chuẩn IEEE."

## Phase 2: Backend Development
**Role:** Senior Java Developer
**Prompt 4:**
"Dựa trên ERD, hãy tạo các Entity JPA cho Spring Boot. Lưu ý: 
- Sử dụng FetchType.LAZY cho mọi quan hệ.
- Sử dụng Lombok.
- Đảm bảo cấu trúc chuẩn JPA để tránh N+1 sau này."

**Prompt 5:**
"Tạo Repository cho User và Order. Sử dụng @EntityGraph để giải quyết bài toán N+1 khi lấy User kèm Roles và Order kèm Items/Products."

**Prompt 6:**
"Cấu hình Spring Security 6 với JWT. 
- API /api/v1/public/**: permitAll.
- API /api/v1/admin/products/**: STAFF hoặc MANAGER.
- API /api/v1/admin/users/**: MANAGER.
- Viết Custom AccessDeniedHandler để trả về JSON 403."

**Prompt 7:**
"Tạo application.properties với cấu hình MySQL và JWT Secret."
