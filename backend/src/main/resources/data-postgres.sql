DO
$$
    DECLARE
        account_number_1 varchar(26) := '31102028922276300531213133';
        account_number_2 varchar(26) := '56213112333131823123137851';
        customer_id_1    varchar(10) := '1234567890';
        customer_id_2    varchar(10) := '0987654321';
    BEGIN
        INSERT INTO account (account_number, balance)
        VALUES (account_number_1, 1312.45),
               (account_number_2, 2311.00);

        INSERT INTO card (id, card_number, account_number, cvv, valid_thru)
        VALUES ('123e4567-e89b-42d3-a456-556642440000', '5645 5678 9012 3456', account_number_1, '123', '12/24'),
               ('123e4567-e89b-42d3-a456-556642440001', '4345 3628 2376 1231', account_number_1, '321', '08/24'),
               ('123e4567-e89b-42d3-a456-556642440002', '1234 5678 9012 3456', account_number_2, '456', '11/25');

        INSERT INTO customer_info (customer_id, first_name, second_name, surname, email, pesel, identity_document_number)
        VALUES (customer_id_1, 'Jack', '', 'Sparrow', 'jacksparrow@example.com', '01234567891', 'ABC123456'),
               (customer_id_2, 'John', 'Quincy', 'Adams', 'john.adams@example.com', '12345678901', 'XYZ987654');

        INSERT INTO customer_account (account_number, customer_id)
        VALUES (account_number_1, customer_id_1),
               (account_number_2, customer_id_2);

        INSERT INTO customer_credentials (customer_id, key_hash)
        VALUES (customer_id_1, '$2a$12$gVFOb/PUY0Lsg25uFsOKyOXTsCLPwLnO9IFfLiRXVk3CVGlVwgp1y'),
               (customer_id_2, '$2a$12$gVFOb/PUY0Lsg25uFsOKyOXTsCLPwLnO9IFfLiRXVk3CVGlVwgp1y');

        -- when password = 'test_123'
        INSERT INTO customer_secret(customer_id, secret_index, secret)
        VALUES (customer_id_1, 0, 1513),
               (customer_id_1, 1, 3881),
               (customer_id_1, 2, 12588),
               (customer_id_1, 3, 34966),
               (customer_id_1, 4, 81194),
               (customer_id_1, 5, 164325),
               (customer_id_1, 6, 300217),
               (customer_id_1, 7, 507727),
               (customer_id_2, 0, 1513),
               (customer_id_2, 1, 3881),
               (customer_id_2, 2, 12588),
               (customer_id_2, 3, 34966),
               (customer_id_2, 4, 81194),
               (customer_id_2, 5, 164325),
               (customer_id_2, 6, 300217),
               (customer_id_2, 7, 507727);
    END
$$;