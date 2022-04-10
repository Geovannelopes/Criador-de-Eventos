/* ====================== Admins ================================ */

INSERT INTO TB_USERS (id, name, email) VALUES (1, 'Bruno', 'bruno@email.com');
INSERT INTO TB_ADMINS (user_id, phoneNumber) VALUES (1, '(15) 1111-1111');

INSERT INTO TB_USERS (id, name, email) VALUES (2, 'Geovanne', 'geovanne@email.com');
INSERT INTO TB_ADMINS (user_id, phoneNumber) VALUES (2, '(15) 2222-2222');

INSERT INTO TB_USERS (id, name, email) VALUES (3, 'Ricardo', 'ricardo@email.com');
INSERT INTO TB_ADMINS (user_id, phoneNumber) VALUES (3, '(15) 3333-3333');

/* ====================== Events ================================ */

INSERT INTO TB_EVENTS (name, description, startDate, endDate, startTime, endTime, emailContact, amountFreeTickets, amountPayedTickets, priceTicket, ADMIN_USER_ID) VALUES ('Rodeio', 'Rodeio Descrição', '2021-01-01', '2021-02-02', '01:01:01', '02:02:02', 'rodeio@email.com', 5, 9, 10, 1);
INSERT INTO TB_EVENTS (name, description, startDate, endDate, startTime, endTime, emailContact, amountFreeTickets, amountPayedTickets, priceTicket, ADMIN_USER_ID) VALUES ('Teatro', 'Teatro Descrição', '2021-03-03', '2021-04-04', '03:03:03', '04:04:04', 'teatro@email.com', 5, 9, 20, 1);
INSERT INTO TB_EVENTS (name, description, startDate, endDate, startTime, endTime, emailContact, amountFreeTickets, amountPayedTickets, priceTicket, ADMIN_USER_ID) VALUES ('Show', 'Show Descrição', '2021-05-05', '2021-06-06', '05:05:05', '06:06:06', 'show@email.com', 5, 9, 30, 2);

/* ======================== Attendees ================================= */

INSERT INTO TB_USERS (id, name, email) VALUES (4, 'Joao', 'joao@email.com');
INSERT INTO TB_ATTENDEES (user_id, balance) VALUES (4, 0);

INSERT INTO TB_USERS (id, name, email) VALUES (5, 'Pedro', 'pedro@email.com');
INSERT INTO TB_ATTENDEES (user_id, balance) VALUES (5, 0);

INSERT INTO TB_USERS (id, name, email) VALUES (6, 'Carlos', 'carlos@email.com');
INSERT INTO TB_ATTENDEES (user_id, balance) VALUES (6, 0);

/* ====================== Places ========================================*/

INSERT INTO TB_PLACES (id, name, address) VALUES (1, 'Lugar1', 'Rua1');
INSERT INTO TB_PLACES (id, name, address) VALUES (2, 'Lugar2', 'Rua2');
INSERT INTO TB_PLACES (id, name, address) VALUES (3, 'Lugar3', 'Rua3');

/* ====================== Tickets ========================================*/

INSERT INTO TB_TICKETS (id, attend_user_id, date, price, type, event_id) VALUES (1, 4, now(), 0, 'Free', 1);
INSERT INTO TB_TICKETS (id, attend_user_id, date, price, type, event_id) VALUES (2, 5, now(), 0, 'Free', 1);
INSERT INTO TB_TICKETS (id, attend_user_id, date, price, type, event_id) VALUES (3, 6, now(), 10, 'Payed', 1);

INSERT INTO TB_TICKETS (id, attend_user_id, date, price, type, event_id) VALUES (4, 4, now(), 0, 'Free', 2);
INSERT INTO TB_TICKETS (id, attend_user_id, date, price, type, event_id) VALUES (5, 5, now(), 20, 'Payed', 2);
INSERT INTO TB_TICKETS (id, attend_user_id, date, price, type, event_id) VALUES (6, 6, now(), 20, 'Payed', 2);

INSERT INTO TB_TICKETS (id, attend_user_id, date, price, type, event_id) VALUES (7, 4, now(), 30, 'Payed', 3);
INSERT INTO TB_TICKETS (id, attend_user_id, date, price, type, event_id) VALUES (8, 5, now(), 30, 'Payed', 3);
INSERT INTO TB_TICKETS (id, attend_user_id, date, price, type, event_id) VALUES (9, 6, now(), 30, 'Payed', 3);