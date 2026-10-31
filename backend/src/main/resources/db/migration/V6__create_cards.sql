CREATE TABLE cards(
    id          BIGSERIAL PRIMARY KEY,
    employee_id BIGINT         NOT NULL UNIQUE,
    salary      NUMERIC(15, 2) NOT NULL,

    FOREIGN KEY (employee_id)
        REFERENCES employees (id) ON DELETE CASCADE
);