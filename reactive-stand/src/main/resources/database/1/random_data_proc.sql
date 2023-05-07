CREATE
OR REPLACE PROCEDURE add_random_rows(
    rows_number INT
) AS
$$
BEGIN
for i in 1 .. rows_number LOOP
INSERT INTO data (uuid, txt, state)
VALUES (md5(random()::TEXT)::UUID,
        upper(
                substring(
                        (SELECT string_agg(md5(random()::TEXT), '')
                         FROM generate_series(
                                 1,
                                 CEIL(100 / 32.)::integer)), 1, 100)),
        floor((100. * random())::INTEGER));
END LOOP;
END;
$$
LANGUAGE plpgsql;
