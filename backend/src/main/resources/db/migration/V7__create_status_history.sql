CREATE TABLE status_history(
    id          BIGSERIAL PRIMARY KEY,
    old_status  VARCHAR(20) NOT NULL,
    new_status  VARCHAR(20) NOT NULL,
    change_at  TIMESTAMP   NOT NULL,
    employee_id BIGINT      NOT NULL,

    FOREIGN KEY (employee_id)
        REFERENCES employees (id)
);