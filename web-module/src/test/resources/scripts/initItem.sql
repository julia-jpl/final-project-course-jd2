insert into ITEM (uuid, unique_number, name, description, is_deleted, group_id) values
('3123b448-2900-4fd2-9183-6780de6f8343', 'HB-912345678966422', 'shampoo', 'description', 0,
 (SELECT id from ITEM_GROUP WHERE group_name='HEALTH_BEAUTY'));