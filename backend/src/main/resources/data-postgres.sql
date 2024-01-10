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
    END
$$;