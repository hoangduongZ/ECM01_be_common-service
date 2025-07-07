-- DATABASE SCHEMA CHO WEBSITE BÁN HÀNG ĐỒ LƯU NIỆM (PostgreSQL)
-- Best Practices: Normalized, Indexed, Constraints, Audit trails

-- Enable UUID extension
CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";

-- =============================================
-- 1. BẢNG QUẢN LÝ NGƯỜI DÙNG VÀ PHÂN QUYỀN
-- =============================================

-- Bảng vai trò hệ thống
CREATE TABLE roles
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    permissions JSONB, -- JSON format để lưu quyền hạn
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Bảng thông tin người dùng
CREATE TABLE users
(
    id                SERIAL PRIMARY KEY,
    uuid              UUID         NOT NULL UNIQUE                                      DEFAULT uuid_generate_v4(),
    email             VARCHAR(255) NOT NULL UNIQUE,
    phone             VARCHAR(20),
    password_hash     VARCHAR(255) NOT NULL,
    first_name        VARCHAR(100) NOT NULL,
    last_name         VARCHAR(100) NOT NULL,
    avatar_url        VARCHAR(500),
    date_of_birth     DATE,
    gender            VARCHAR(10) CHECK (gender IN ('male', 'female', 'other')),
    role_id           INTEGER      NOT NULL                                             DEFAULT 2, -- 1=Admin, 2=Customer
    status            VARCHAR(20) CHECK (status IN ('active', 'inactive', 'suspended')) DEFAULT 'active',
    email_verified_at TIMESTAMP WITH TIME ZONE NULL,
    last_login_at     TIMESTAMP WITH TIME ZONE NULL,
    created_at        TIMESTAMP WITH TIME ZONE                                          DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMP WITH TIME ZONE                                          DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_users_phone ON users (phone);
CREATE INDEX idx_users_status ON users (status);

-- Bảng địa chỉ khách hàng
CREATE TABLE user_addresses
(
    id              SERIAL PRIMARY KEY,
    user_id         INTEGER      NOT NULL,
    title           VARCHAR(100) NOT NULL, -- Nhà riêng, Cơ quan, etc.
    recipient_name  VARCHAR(200) NOT NULL,
    recipient_phone VARCHAR(20)  NOT NULL,
    address_line_1  VARCHAR(255) NOT NULL,
    address_line_2  VARCHAR(255),
    ward            VARCHAR(100) NOT NULL,
    district        VARCHAR(100) NOT NULL,
    city            VARCHAR(100) NOT NULL,
    postal_code     VARCHAR(20),
    country         VARCHAR(100)             DEFAULT 'Vietnam',
    is_default      BOOLEAN                  DEFAULT FALSE,
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE INDEX idx_user_addresses_user_default ON user_addresses (user_id, is_default);

-- =============================================
-- 2. BẢNG QUẢN LÝ SẢN PHẨM
-- =============================================

-- Bảng danh mục sản phẩm (hỗ trợ cây phân cấp)
CREATE TABLE categories
(
    id               SERIAL PRIMARY KEY,
    name             VARCHAR(200) NOT NULL,
    slug             VARCHAR(200) NOT NULL UNIQUE,
    description      TEXT,
    parent_id        INTEGER NULL,
    image_url        VARCHAR(500),
    sort_order       INTEGER                  DEFAULT 0,
    is_active        BOOLEAN                  DEFAULT TRUE,
    meta_title       VARCHAR(200),
    meta_description TEXT,
    created_at       TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (parent_id) REFERENCES categories (id)
);

CREATE INDEX idx_categories_parent ON categories (parent_id);
CREATE INDEX idx_categories_slug ON categories (slug);
CREATE INDEX idx_categories_active ON categories (is_active);

-- Bảng thương hiệu
CREATE TABLE brands
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(200) NOT NULL UNIQUE,
    slug        VARCHAR(200) NOT NULL UNIQUE,
    description TEXT,
    logo_url    VARCHAR(500),
    website     VARCHAR(500),
    is_active   BOOLEAN                  DEFAULT TRUE,
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_brands_slug ON brands (slug);
CREATE INDEX idx_brands_active ON brands (is_active);

-- Bảng thuộc tính sản phẩm (Size, Color, Material, etc.)
CREATE TABLE attributes
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(100) NOT NULL UNIQUE,
    slug          VARCHAR(100) NOT NULL UNIQUE,
    type          VARCHAR(20) CHECK (type IN ('text', 'number', 'select', 'multiselect', 'boolean')) DEFAULT 'text',
    is_required   BOOLEAN                                                                            DEFAULT FALSE,
    is_filterable BOOLEAN                                                                            DEFAULT TRUE,
    sort_order    INTEGER                                                                            DEFAULT 0,
    created_at    TIMESTAMP WITH TIME ZONE                                                           DEFAULT CURRENT_TIMESTAMP
);

-- Bảng giá trị thuộc tính
CREATE TABLE attribute_values
(
    id           SERIAL PRIMARY KEY,
    attribute_id INTEGER      NOT NULL,
    value        VARCHAR(255) NOT NULL,
    sort_order   INTEGER DEFAULT 0,

    FOREIGN KEY (attribute_id) REFERENCES attributes (id) ON DELETE CASCADE,
    UNIQUE (attribute_id, value)
);

-- Bảng sản phẩm chính
CREATE TABLE products
(
    id                  SERIAL PRIMARY KEY,
    uuid                UUID           NOT NULL UNIQUE                                                DEFAULT uuid_generate_v4(),
    name                VARCHAR(500)   NOT NULL,
    slug                VARCHAR(500)   NOT NULL UNIQUE,
    short_description   TEXT,
    description         TEXT,
    sku                 VARCHAR(100) UNIQUE,
    brand_id            INTEGER,
    category_id         INTEGER        NOT NULL,

    -- Thông tin giá cả
    price               DECIMAL(12, 2) NOT NULL,
    compare_price       DECIMAL(12, 2), -- Giá gốc để tính % giảm
    cost_price          DECIMAL(12, 2), -- Giá vốn

    -- Quản lý kho
    track_inventory     BOOLEAN                                                                       DEFAULT TRUE,
    inventory_quantity  INTEGER                                                                       DEFAULT 0,
    allow_backorder     BOOLEAN                                                                       DEFAULT FALSE,
    low_stock_threshold INTEGER                                                                       DEFAULT 10,

    -- Thông tin vật lý
    weight              DECIMAL(8, 2),  -- kg
    length              DECIMAL(8, 2),  -- cm
    width               DECIMAL(8, 2),  -- cm
    height              DECIMAL(8, 2),  -- cm

    -- SEO và marketing
    meta_title          VARCHAR(200),
    meta_description    TEXT,
    featured_image_url  VARCHAR(500),
    gallery_images      JSONB,          -- JSON array của URLs

    -- Trạng thái
    status              VARCHAR(20) CHECK (status IN ('draft', 'active', 'inactive', 'out_of_stock')) DEFAULT 'draft',
    is_featured         BOOLEAN                                                                       DEFAULT FALSE,

    -- Thống kê
    view_count          INTEGER                                                                       DEFAULT 0,
    sales_count         INTEGER                                                                       DEFAULT 0,
    rating_average      DECIMAL(3, 2)                                                                 DEFAULT 0,
    rating_count        INTEGER                                                                       DEFAULT 0,

    created_at          TIMESTAMP WITH TIME ZONE                                                      DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP WITH TIME ZONE                                                      DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (brand_id) REFERENCES brands (id),
    FOREIGN KEY (category_id) REFERENCES categories (id)
);

CREATE INDEX idx_products_slug ON products (slug);
CREATE INDEX idx_products_sku ON products (sku);
CREATE INDEX idx_products_category ON products (category_id);
CREATE INDEX idx_products_brand ON products (brand_id);
CREATE INDEX idx_products_status ON products (status);
CREATE INDEX idx_products_featured ON products (is_featured);
CREATE INDEX idx_products_price ON products (price);
CREATE INDEX idx_products_created ON products (created_at);

-- Bảng thuộc tính của sản phẩm
CREATE TABLE product_attributes
(
    id                 SERIAL PRIMARY KEY,
    product_id         INTEGER NOT NULL,
    attribute_id       INTEGER NOT NULL,
    attribute_value_id INTEGER,
    custom_value       VARCHAR(255), -- Cho trường hợp giá trị tùy chỉnh

    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE,
    FOREIGN KEY (attribute_id) REFERENCES attributes (id),
    FOREIGN KEY (attribute_value_id) REFERENCES attribute_values (id),
    UNIQUE (product_id, attribute_id)
);

-- Bảng biến thể sản phẩm (variants)
CREATE TABLE product_variants
(
    id                 SERIAL PRIMARY KEY,
    product_id         INTEGER NOT NULL,
    sku                VARCHAR(100) UNIQUE,
    name               VARCHAR(300), -- Tên biến thể: "iPhone 13 - 128GB - Đỏ"

    -- Giá riêng cho variant
    price              DECIMAL(12, 2),
    compare_price      DECIMAL(12, 2),
    cost_price         DECIMAL(12, 2),

    -- Kho hàng riêng
    inventory_quantity INTEGER                  DEFAULT 0,

    -- Thông tin vật lý riêng
    weight             DECIMAL(8, 2),
    image_url          VARCHAR(500),

    sort_order         INTEGER                  DEFAULT 0,
    is_active          BOOLEAN                  DEFAULT TRUE,
    created_at         TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);

CREATE INDEX idx_product_variants_product ON product_variants (product_id);
CREATE INDEX idx_product_variants_sku ON product_variants (sku);
CREATE INDEX idx_product_variants_active ON product_variants (is_active);

-- Bảng thuộc tính của variant
CREATE TABLE variant_attributes
(
    id                 SERIAL PRIMARY KEY,
    variant_id         INTEGER NOT NULL,
    attribute_id       INTEGER NOT NULL,
    attribute_value_id INTEGER NOT NULL,

    FOREIGN KEY (variant_id) REFERENCES product_variants (id) ON DELETE CASCADE,
    FOREIGN KEY (attribute_id) REFERENCES attributes (id),
    FOREIGN KEY (attribute_value_id) REFERENCES attribute_values (id),
    UNIQUE (variant_id, attribute_id)
);

-- =============================================
-- 3. BẢNG QUẢN LÝ ĐƠN HÀNG
-- =============================================

-- Bảng giỏ hàng
CREATE TABLE shopping_carts
(
    id         SERIAL PRIMARY KEY,
    user_id    INTEGER,
    session_id VARCHAR(255), -- Cho khách vãng lai
    product_id INTEGER NOT NULL,
    variant_id INTEGER,
    quantity   INTEGER NOT NULL         DEFAULT 1,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE,
    FOREIGN KEY (variant_id) REFERENCES product_variants (id) ON DELETE CASCADE
);

CREATE INDEX idx_shopping_carts_user ON shopping_carts (user_id);
CREATE INDEX idx_shopping_carts_session ON shopping_carts (session_id);
CREATE INDEX idx_shopping_carts_product ON shopping_carts (product_id);

-- Bảng mã giảm giá
CREATE TABLE coupons
(
    id                       SERIAL PRIMARY KEY,
    code                     VARCHAR(50)                                                                 NOT NULL UNIQUE,
    name                     VARCHAR(200)                                                                NOT NULL,
    description              TEXT,

    -- Loại giảm giá
    type                     VARCHAR(20) CHECK (type IN ('percentage', 'fixed_amount', 'free_shipping')) NOT NULL,
    value                    DECIMAL(12, 2)                                                              NOT NULL, -- % hoặc số tiền

    -- Điều kiện áp dụng
    minimum_amount           DECIMAL(12, 2)           DEFAULT 0,
    maximum_discount         DECIMAL(12, 2),                                                                       -- Giới hạn số tiền giảm tối đa

    -- Giới hạn sử dụng
    usage_limit              INTEGER,                                                                              -- Tổng số lần sử dụng
    usage_limit_per_customer INTEGER                  DEFAULT 1,
    used_count               INTEGER                  DEFAULT 0,

    -- Thời gian hiệu lực
    start_date               TIMESTAMP WITH TIME ZONE                                                    NOT NULL,
    end_date                 TIMESTAMP WITH TIME ZONE                                                    NOT NULL,

    is_active                BOOLEAN                  DEFAULT TRUE,
    created_at               TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at               TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_coupons_code ON coupons (code);
CREATE INDEX idx_coupons_active_dates ON coupons (is_active, start_date, end_date);

-- Bảng đơn hàng
CREATE TABLE orders
(
    id               SERIAL PRIMARY KEY,
    uuid             UUID           NOT NULL UNIQUE   DEFAULT uuid_generate_v4(),
    order_number     VARCHAR(50)    NOT NULL UNIQUE,

    -- Thông tin khách hàng
    user_id          INTEGER,
    customer_email   VARCHAR(255)   NOT NULL,
    customer_phone   VARCHAR(20),
    customer_name    VARCHAR(200)   NOT NULL,

    -- Địa chỉ giao hàng
    shipping_address JSONB          NOT NULL, -- JSON format
    billing_address  JSONB,                   -- JSON format

    -- Thông tin đơn hàng
    status           VARCHAR(20) CHECK (status IN
                                        ('pending', 'confirmed', 'processing', 'shipped', 'delivered', 'cancelled',
                                         'refunded')) DEFAULT 'pending',

    -- Tính toán giá
    subtotal         DECIMAL(12, 2) NOT NULL, -- Tổng tiền hàng
    tax_amount       DECIMAL(12, 2)                   DEFAULT 0,
    shipping_fee     DECIMAL(12, 2)                   DEFAULT 0,
    discount_amount  DECIMAL(12, 2)                   DEFAULT 0,
    total_amount     DECIMAL(12, 2) NOT NULL, -- Tổng thanh toán

    -- Mã giảm giá đã sử dụng
    coupon_id        INTEGER,
    coupon_code      VARCHAR(50),

    -- Thông tin giao hàng
    shipping_method  VARCHAR(100),
    tracking_number  VARCHAR(100),
    shipped_at       TIMESTAMP WITH TIME ZONE NULL,
    delivered_at     TIMESTAMP WITH TIME ZONE NULL,

    -- Ghi chú
    notes            TEXT,
    admin_notes      TEXT,

    created_at       TIMESTAMP WITH TIME ZONE         DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP WITH TIME ZONE         DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (coupon_id) REFERENCES coupons (id)
);

CREATE INDEX idx_orders_user ON orders (user_id);
CREATE INDEX idx_orders_status ON orders (status);
CREATE INDEX idx_orders_order_number ON orders (order_number);
CREATE INDEX idx_orders_email ON orders (customer_email);
CREATE INDEX idx_orders_created ON orders (created_at);

-- Bảng chi tiết đơn hàng
CREATE TABLE order_items
(
    id           SERIAL PRIMARY KEY,
    order_id     INTEGER        NOT NULL,
    product_id   INTEGER        NOT NULL,
    variant_id   INTEGER,

    -- Thông tin tại thời điểm đặt hàng (snapshot)
    product_name VARCHAR(500)   NOT NULL,
    product_sku  VARCHAR(100),
    variant_name VARCHAR(300),

    quantity     INTEGER        NOT NULL,
    unit_price   DECIMAL(12, 2) NOT NULL,
    total_price  DECIMAL(12, 2) NOT NULL,

    created_at   TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products (id),
    FOREIGN KEY (variant_id) REFERENCES product_variants (id)
);

CREATE INDEX idx_order_items_order ON order_items (order_id);
CREATE INDEX idx_order_items_product ON order_items (product_id);

-- =============================================
-- 4. BẢNG THANH TOÁN
-- =============================================

-- Bảng giao dịch thanh toán
CREATE TABLE payments
(
    id               SERIAL PRIMARY KEY,
    uuid             UUID                                                                                    NOT NULL UNIQUE DEFAULT uuid_generate_v4(),
    order_id         INTEGER                                                                                 NOT NULL,

    -- Thông tin thanh toán
    payment_method   VARCHAR(20) CHECK (payment_method IN
                                        ('cod', 'bank_transfer', 'momo', 'zalopay', 'vnpay', 'credit_card')) NOT NULL,
    amount           DECIMAL(12, 2)                                                                          NOT NULL,
    currency         VARCHAR(3)                                                                                              DEFAULT 'VND',

    -- Trạng thái
    status           VARCHAR(20) CHECK (status IN ('pending', 'processing', 'completed', 'failed', 'cancelled',
                                                   'refunded'))                                                              DEFAULT 'pending',

    -- Thông tin từ gateway
    transaction_id   VARCHAR(255), -- ID từ cổng thanh toán
    gateway_response JSONB,        -- Response từ gateway

    -- Thời gian
    paid_at          TIMESTAMP WITH TIME ZONE NULL,
    created_at       TIMESTAMP WITH TIME ZONE                                                                                DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP WITH TIME ZONE                                                                                DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (order_id) REFERENCES orders (id)
);

CREATE INDEX idx_payments_order ON payments (order_id);
CREATE INDEX idx_payments_status ON payments (status);
CREATE INDEX idx_payments_transaction ON payments (transaction_id);

-- =============================================
-- 5. BẢNG ĐÁNH GIÁ VÀ NHẬN XÉT
-- =============================================

-- Bảng đánh giá sản phẩm
CREATE TABLE product_reviews
(
    id               SERIAL PRIMARY KEY,
    product_id       INTEGER  NOT NULL,
    user_id          INTEGER  NOT NULL,
    order_id         INTEGER,                                -- Chỉ cho phép đánh giá nếu đã mua

    rating           SMALLINT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    title            VARCHAR(200),
    content          TEXT,

    -- Thông tin hữu ích
    helpful_count    INTEGER                  DEFAULT 0,

    -- Phản hồi từ shop
    admin_reply      TEXT,
    admin_replied_at TIMESTAMP WITH TIME ZONE NULL,

    is_verified      BOOLEAN                  DEFAULT FALSE, -- Đã xác thực mua hàng
    is_approved      BOOLEAN                  DEFAULT TRUE,

    created_at       TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (order_id) REFERENCES orders (id),

    UNIQUE (user_id, product_id, order_id)
);

CREATE INDEX idx_product_reviews_product ON product_reviews (product_id);
CREATE INDEX idx_product_reviews_rating ON product_reviews (rating);
CREATE INDEX idx_product_reviews_approved ON product_reviews (is_approved);

-- =============================================
-- 6. BẢNG QUẢN LÝ NỘI DUNG
-- =============================================

-- Bảng bài viết/tin tức
CREATE TABLE posts
(
    id               SERIAL PRIMARY KEY,
    title            VARCHAR(500) NOT NULL,
    slug             VARCHAR(500) NOT NULL UNIQUE,
    excerpt          TEXT,
    content          TEXT,
    featured_image   VARCHAR(500),

    author_id        INTEGER      NOT NULL,
    category_id      INTEGER,

    status           VARCHAR(20) CHECK (status IN ('draft', 'published', 'private')) DEFAULT 'draft',

    -- SEO
    meta_title       VARCHAR(200),
    meta_description TEXT,

    -- Thống kê
    view_count       INTEGER                                                         DEFAULT 0,

    published_at     TIMESTAMP WITH TIME ZONE NULL,
    created_at       TIMESTAMP WITH TIME ZONE                                        DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP WITH TIME ZONE                                        DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (author_id) REFERENCES users (id)
);

CREATE INDEX idx_posts_slug ON posts (slug);
CREATE INDEX idx_posts_status ON posts (status);
CREATE INDEX idx_posts_published ON posts (published_at);

-- =============================================
-- 7. BẢNG CẤU HÌNH HỆ THỐNG
-- =============================================

-- Bảng cài đặt hệ thống
CREATE TABLE settings
(
    id          SERIAL PRIMARY KEY,
    key_name    VARCHAR(100) NOT NULL UNIQUE,
    value       TEXT,
    description TEXT,
    type        VARCHAR(20) CHECK (type IN ('string', 'number', 'boolean', 'json')) DEFAULT 'string',
    created_at  TIMESTAMP WITH TIME ZONE                                            DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP WITH TIME ZONE                                            DEFAULT CURRENT_TIMESTAMP
);

-- Bảng log hoạt động
CREATE TABLE activity_logs
(
    id          SERIAL PRIMARY KEY,
    user_id     INTEGER,
    action      VARCHAR(100) NOT NULL,
    description TEXT,
    ip_address  INET,
    user_agent  TEXT,
    properties  JSONB, -- Dữ liệu bổ sung
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE INDEX idx_activity_logs_user ON activity_logs (user_id);
CREATE INDEX idx_activity_logs_action ON activity_logs (action);
CREATE INDEX idx_activity_logs_created ON activity_logs (created_at);

-- =============================================
-- 8. DỮ LIỆU MẪU
-- =============================================

-- Thêm vai trò mặc định
