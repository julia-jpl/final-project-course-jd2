INSERT INTO USER(unique_number, last_name, first_name, middle_name, email, password, is_deleted, role_id)
VALUES ('3422b448-2900-4fd2-9183-8000de6f8343', 'RestLastName', 'RestFirstName',
'RestMiddleName', 'rest@myhost.com', '$2y$12$cURVZSxO7mIi8zsULxj5ReC49s66wFyJTNXiN/96z3DAvvSeH8ERG', 0,
(SELECT id from ROLE WHERE role_name='SECURE_REST_API'));
INSERT INTO USER(unique_number, last_name, first_name, middle_name, email, password, is_deleted, role_id)
VALUES ('3422b448-2900-4fd2-9183-8770de6f8343', 'SaleUserName', 'SaleFirstName',
'SaleMiddleName', 'sale@myhost.com', '$2y$12$cURVZSxO7mIi8zsULxj5ReC49s66wFyJTNXiN/96z3DAvvSeH8ERG', 0,
(SELECT id from ROLE WHERE role_name='SALE_USER'));
INSERT INTO USER(unique_number, last_name, first_name, middle_name, email, password, is_deleted, role_id)
VALUES ('3422b448-2900-4fd2-9183-8770de6f8222', 'CustomerUserName', 'CustomerFirstName',
'CustomerMiddleName', 'customer@myhost.com', '$2y$12$cURVZSxO7mIi8zsULxj5ReC49s66wFyJTNXiN/96z3DAvvSeH8ERG', 0,
(SELECT id from ROLE WHERE role_name='CUSTOMER_USER'));