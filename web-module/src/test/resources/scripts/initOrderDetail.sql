INSERT INTO ORDER_DETAIL (order_id, item_name, total_price, item_quantity, customer_id, customer_telephone,
customer_identifier) VALUES ((SELECT id FROM ORDERS WHERE number='22222222222'), 'shampoo', 20.00, 2, (SELECT id FROM USER WHERE email = 'sale@myhost.com'),
'+375-29-4444-44-44', 'CustomerName');