# 🏬 Common Service - eCommerce System

> **Common-service** là service chịu trách nhiệm xử lý toàn bộ logic liên quan đến **sản phẩm, thuộc tính, danh mục, đơn hàng, thanh toán, đánh giá**, và **nội dung CMS**. Service này đóng vai trò trung tâm trong hệ thống eCommerce theo kiến trúc **microservices**.

---

## 📦 Thành phần chính

### 1. Quản lý sản phẩm
- `categories`: danh mục phân cấp sản phẩm
- `brands`: thương hiệu
- `products`: sản phẩm chính
- `product_variants`: các biến thể (màu sắc, size…)
- `attributes` & `attribute_values`: thuộc tính và giá trị (VD: "Màu sắc" → "Đỏ")
- `product_attributes`: ánh xạ thuộc tính sản phẩm
- `variant_attributes`: ánh xạ thuộc tính biến thể

### 2. Giỏ hàng và đơn hàng
- `shopping_carts`: giỏ hàng tạm
- `orders` & `order_items`: đơn hàng & chi tiết
- `coupons`: mã giảm giá

### 3. Thanh toán
- `payments`: thông tin giao dịch từ các cổng như MoMo, VNPAY, COD, etc.

### 4. Đánh giá và nhận xét
- `product_reviews`: đánh giá của khách hàng

### 5. Quản lý nội dung
- `posts`: bài viết, tin tức
- `settings`: cấu hình hệ thống

### 6. Hoạt động & báo cáo
- `activity_logs`: log thao tác người dùng
- `daily_stats`: thống kê đơn hàng & bán hàng theo ngày

---

## 🛠️ Khởi tạo Database

### 1. Cần bật extension UUID:
```sql
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
