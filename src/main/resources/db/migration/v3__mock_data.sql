-- Thêm cài đặt hệ thống cơ bản
INSERT INTO settings (key_name, value, description)
VALUES ('site_name', 'Ecommerce Shop', 'Tên website'),
       ('site_email', 'info@souvenirshop.com', 'Email liên hệ'),
       ('site_phone', '0123456789', 'Số điện thoại'),
       ('currency', 'VND', 'Đơn vị tiền tệ'),
       ('tax_rate', '10', 'Thuế VAT (%)'),
       ('free_shipping_threshold', '500000', 'Miễn phí ship từ'),
       ('inventory_tracking', 'true', 'Theo dõi tồn kho');

-- product module
-- mock categories
INSERT INTO categories(name, slug, parent_id, sort_order)
VALUES
    ('Smartphone', 'smartphone', NULL, 1),
    ('Tablet', 'tablet', NULL, 2),
    ('Phụ kiện', 'phu-kien', NULL, 3);

-- mock brand
INSERT INTO brands(name, slug, logo_url, website)
VALUES
    ('Apple', 'apple', 'https://example.com/apple-logo.png', 'https://apple.com'),
    ('Samsung', 'samsung', 'https://example.com/samsung-logo.png', 'https://samsung.com'),
    ('Xiaomi', 'xiaomi', 'https://example.com/xiaomi-logo.png', 'https://xiaomi.com');

-- mock attributes
INSERT INTO attributes(name, slug, type, is_required, is_filterable, sort_order)
VALUES
    ('RAM', 'ram', 'select', TRUE, TRUE, 1),
    ('ROM', 'rom', 'select', TRUE, TRUE, 2),
    ('Chip', 'chip', 'text', FALSE, TRUE, 3),
    ('Màn hình', 'man-hinh', 'text', FALSE, TRUE, 4),
    ('Pin', 'pin', 'text', FALSE, TRUE, 5),
    ('Màu sắc', 'mau-sac', 'select', FALSE, TRUE, 6);

-- mock attribute_value
INSERT INTO attribute_values(attribute_id, value, sort_order)
VALUES
    ((SELECT id FROM attributes WHERE slug = 'ram'), '8GB', 1),
    ((SELECT id FROM attributes WHERE slug = 'ram'), '12GB', 2);

INSERT INTO attribute_values(attribute_id, value, sort_order)
VALUES
    ((SELECT id FROM attributes WHERE slug = 'rom'), '128GB', 1),
    ((SELECT id FROM attributes WHERE slug = 'rom'), '256GB', 2),
    ((SELECT id FROM attributes WHERE slug = 'rom'), '512GB', 3);

INSERT INTO attribute_values(attribute_id, value, sort_order)
VALUES
    ((SELECT id FROM attributes WHERE slug = 'mau-sac'), 'Đen', 1),
    ((SELECT id FROM attributes WHERE slug = 'mau-sac'), 'Titan', 2),
    ((SELECT id FROM attributes WHERE slug = 'mau-sac'), 'Xanh lá', 3);

INSERT INTO products (
    name, slug, short_description, description, brand_id, category_id, price, status
)
VALUES (
           'iPhone 15 Pro Max',
           'iphone-15-pro-max',
           'Flagship Apple năm 2023',
           'Chip A17 Pro, khung Titanium, camera 48MP',
           (SELECT id FROM brands WHERE slug = 'apple'),
           (SELECT id FROM categories WHERE slug = 'iphone'),
           34990000,
           'active'
       );

-- mock product variants
WITH product_ref AS (
    SELECT id AS product_id FROM products WHERE slug = 'iphone-15-pro-max'
)
INSERT INTO product_variants (product_id, sku, price, inventory_quantity, is_active)
VALUES
((SELECT product_id FROM product_ref), 'IP15PM-256-TITAN', 34990000, 50, TRUE),
((SELECT product_id FROM product_ref), 'IP15PM-512-DEN',   39990000, 30, TRUE),
((SELECT product_id FROM product_ref), 'IP15PM-256-XANH',  36990000, 20, TRUE);

-- mock product_variant_attributes
-- Lấy id biến thể tương ứng
WITH ram AS (
    SELECT id FROM attributes WHERE slug = 'ram'
), rom AS (
    SELECT id FROM attributes WHERE slug = 'rom'
), color AS (
    SELECT id FROM attributes WHERE slug = 'mau-sac'
), variants AS (
    SELECT id, sku FROM product_variants WHERE sku IN ('IP15PM-256-TITAN', 'IP15PM-512-DEN', 'IP15PM-256-XANH')
)
-- Gán biến thể 1: 8GB / 256GB / Titan
INSERT INTO product_variant_attributes (variant_id, attribute_id, attribute_value_id)
VALUES
((SELECT id FROM variants WHERE sku = 'IP15PM-256-TITAN'), (SELECT id FROM ram), (SELECT id FROM attribute_values WHERE value = '8GB' AND attribute_id = (SELECT id FROM ram))),
((SELECT id FROM variants WHERE sku = 'IP15PM-256-TITAN'), (SELECT id FROM rom), (SELECT id FROM attribute_values WHERE value = '256GB' AND attribute_id = (SELECT id FROM rom))),
((SELECT id FROM variants WHERE sku = 'IP15PM-256-TITAN'), (SELECT id FROM color), (SELECT id FROM attribute_values WHERE value = 'Titan' AND attribute_id = (SELECT id FROM color)));

-- Biến thể 2: 8GB / 512GB / Đen
INSERT INTO product_variant_attributes (variant_id, attribute_id, attribute_value_id)
VALUES
    ((SELECT id FROM variants WHERE sku = 'IP15PM-512-DEN'), (SELECT id FROM ram), (SELECT id FROM attribute_values WHERE value = '8GB' AND attribute_id = (SELECT id FROM ram))),
    ((SELECT id FROM variants WHERE sku = 'IP15PM-512-DEN'), (SELECT id FROM rom), (SELECT id FROM attribute_values WHERE value = '512GB' AND attribute_id = (SELECT id FROM rom))),
    ((SELECT id FROM variants WHERE sku = 'IP15PM-512-DEN'), (SELECT id FROM color), (SELECT id FROM attribute_values WHERE value = 'Đen' AND attribute_id = (SELECT id FROM color)));

-- Biến thể 3: 12GB / 256GB / Xanh lá
INSERT INTO product_variant_attributes (variant_id, attribute_id, attribute_value_id)
VALUES
    ((SELECT id FROM variants WHERE sku = 'IP15PM-256-XANH'), (SELECT id FROM ram), (SELECT id FROM attribute_values WHERE value = '12GB' AND attribute_id = (SELECT id FROM ram))),
    ((SELECT id FROM variants WHERE sku = 'IP15PM-256-XANH'), (SELECT id FROM rom), (SELECT id FROM attribute_values WHERE value = '256GB' AND attribute_id = (SELECT id FROM rom))),
    ((SELECT id FROM variants WHERE sku = 'IP15PM-256-XANH'), (SELECT id FROM color), (SELECT id FROM attribute_values WHERE value = 'Xanh lá' AND attribute_id = (SELECT id FROM color)));
