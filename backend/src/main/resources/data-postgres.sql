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

        INSERT INTO customer_credentials (customer_id, salt, key_hash)
        VALUES (customer_id_1, -5866925587908670930, '$2a$10$fAelH8X6tJjMDnZZavsDWOMbmON9c5gNR1/eMJ1.xsgIvvESCPv.m');

        -- when password = 'test_pass123'
        INSERT INTO customer_secret(customer_id, secret_index, secret)
        VALUES (customer_id_1, 0, 4115123462542955483),
               (customer_id_1, 1, 56899727825074956083),
               (customer_id_1, 2, 331634795463222570832),
               (customer_id_1, 3, 1135251013831780138670),
               (customer_id_1, 4, 2909047736404134772664),
               (customer_id_1, 5, 6228692982672266359972),
               (customer_id_1, 6, 11804223438146747562042),
               (customer_id_1, 7, 20480044454356743814347),
               (customer_id_1, 8, 33234930048850013326636),
               (customer_id_1, 9, 51182022905192907082815),
               (customer_id_1, 10,75568834372970368840769),
               (customer_id_1, 11,107777244467785935132743);
    END
$$;