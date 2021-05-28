INSERT INTO ORDERS(uuid, number, seller_id, seller, status_id)
VALUES ('3422b448-2900-4fd2-9183-8000de6f8343', '22222222222',
(SELECT id FROM USER WHERE email='sale@myhost.com'), 'SaleUserNameSaleFirstName',
(SELECT id from ORDER_STATUS WHERE status_name='NEW'));