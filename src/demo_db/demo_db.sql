-- CREATE DATABASE db_cinema_booking;

CREATE TABLE IF NOT EXISTS ticket_bookings
(
    booking_id    SERIAL PRIMARY KEY,
    movie_title   VARCHAR(150) NOT NULL,
    customer_name VARCHAR(100) NOT NULL,
    show_time     TIMESTAMP    NOT NULL,
    booking_date  TIMESTAMP    NOT NULL,
    seat_quantity INT          NOT NULL,
    status        VARCHAR(30)  NOT NULL CHECK ( status IN ('Booked', 'Cancelled', 'CheckedIn') )
);

CREATE OR REPLACE FUNCTION fn_get_all_ticket_bookings()
    RETURNS SETOF ticket_bookings
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN QUERY
        SELECT * FROM ticket_bookings;
END;
$$;

CREATE OR REPLACE PROCEDURE proc_add_ticket_booking(
    IN p_movie_title VARCHAR(150),
    IN p_customer_name VARCHAR(100),
    IN p_show_time TIMESTAMP,
    IN p_booking_date TIMESTAMP,
    IN p_seat_quantity INT,
    IN p_status VARCHAR(30)
)
    LANGUAGE plpgsql AS
$$
BEGIN
    INSERT INTO ticket_bookings (movie_title, customer_name, show_time, booking_date, seat_quantity, status)
    VALUES (p_movie_title, p_customer_name, p_show_time, p_booking_date, p_seat_quantity, p_status);
END ;
$$;

CREATE OR REPLACE FUNCTION fn_get_ticket_booking_by_customer_name(
    IN p_customer_name VARCHAR(100)
)
    RETURNS SETOF ticket_bookings
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN QUERY
        SELECT * FROM ticket_bookings WHERE customer_name ILIKE '%' || p_customer_name || '%';
END;
$$;

CREATE OR REPLACE PROCEDURE proc_update_ticket_booking_by_id(
    IN p_booking_id INT,
    IN p_movie_title VARCHAR(150),
    IN p_customer_name VARCHAR(100),
    IN p_show_time TIMESTAMP,
    IN p_booking_date TIMESTAMP,
    IN p_seat_quantity INT,
    IN p_status VARCHAR(30)
)
    LANGUAGE plpgsql AS
$$
BEGIN
    UPDATE ticket_bookings SET
    movie_title = p_movie_title,
    customer_name = p_customer_name,
    show_time = p_show_time,
    booking_date = p_booking_date,
    seat_quantity = p_seat_quantity,
    status = p_status
    WHERE booking_id = p_booking_id;

    IF NOT FOUND THEN
        RAISE EXCEPTION 'BOOKING KHÔNG TỒN TẠI';
    END IF;

END ;
$$;

CREATE OR REPLACE PROCEDURE proc_delete_ticket_booking_by_id(
    IN p_booking_id INT
)
    LANGUAGE plpgsql AS
$$
BEGIN
    DELETE FROM ticket_bookings
    WHERE booking_id = p_booking_id;

    IF NOT FOUND THEN
        RAISE EXCEPTION 'BOOKING KHÔNG TỒN TẠI';
    END IF;
END ;
$$;

CREATE OR REPLACE FUNCTION fn_get_ticket_booking_by_movie_title_keyword(
    IN p_keyword VARCHAR(100)
)
    RETURNS SETOF ticket_bookings
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN QUERY
        SELECT * FROM ticket_bookings WHERE movie_title ILIKE '%' || p_keyword || '%';
END;
$$;

INSERT INTO ticket_bookings (movie_title, customer_name, show_time, booking_date, seat_quantity, status)
VALUES ('Harry Poster và Hòn đá phù thủy', 'Nguyễn Văn A', '01/10/2026 20:20:20', '01/10/2026 20:20:20', 1, 'Booked'),
 ('Captain America', 'Trần Đình Bằng', '01/10/2026 20:20:20', '01/10/2026 20:20:20', 6, 'CheckedIn'),
 ('Ironman', 'Lê Hồng Văn', '01/10/2026 20:20:20', '01/10/2026 20:20:20', 1, 'Booked'),
 ('Harry Poster và Hòn đá phù thủy', 'Hồng Hữu Đại', '01/10/2026 20:20:20', '01/10/2026 20:20:20', 2, 'Booked'),
 ('Captain America', 'Chấn Động', '01/10/2026 20:20:20', '01/10/2026 20:20:20', 3, 'CheckedIn');
