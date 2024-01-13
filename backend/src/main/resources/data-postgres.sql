DO
$$
    DECLARE
        account_number_1 varchar(32) := '31 1020 2892 2276 3005 3121 3133';
        customer_id_1    varchar(10) := '1234567890';
    BEGIN
        INSERT INTO account (account_number, balance)
        VALUES (account_number_1, 1312.45);

        INSERT INTO card (card_number, account_number, cvv, valid_thru)
        VALUES ('5645 5678 9012 3456', account_number_1, '123', '12/24'),
               ('4345 3628 2376 1231', account_number_1, '321', '08/24');

        INSERT INTO customer_info (customer_id, first_name, second_name, surname, email, pesel, identity_card_number)
        VALUES (customer_id_1, 'Jack', '', 'Sparrow', 'jacksparrow@example.com', '01234567891', 'ABC123456');

        INSERT INTO customer_account (account_number, customer_id)
        VALUES (account_number_1, customer_id_1);

        INSERT INTO customer_credentials (customer_id, key_hash)
        VALUES (customer_id_1, '$2a$10$WOf7TwI0334NG70cN.OpheGoR/to6X4nJ1nUy8nC8eAwwc2CfuUae');

        -- when password = 'test_pass123'
        INSERT INTO customer_secret(customer_id, secret_index, secret)
        VALUES (customer_id_1, 0, -2003643029664899710),
               (customer_id_1, 1, -65400837966611911474),
               (customer_id_1, 2, -407424389813492140149),
               (customer_id_1, 3, -1406557637870676512263),
               (customer_id_1, 4, -3605899158445077340657),
               (customer_id_1, 5, -7713162764850148324521),
               (customer_id_1, 6, -14600677507405884549195),
               (customer_id_1, 7, -25305387673438822486434),
               (customer_id_1, 8, -41028852787282039994157),
               (customer_id_1, 9, -63137247610275156316566),
               (customer_id_1, 10,-93161362140764332084324),
               (customer_id_1, 11,-132796601614102269314174);
    END
$$;