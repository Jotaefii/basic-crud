CREATE TABLE employees(
    id            BIGSERIAL PRIMARY KEY,
    user_id       BIGINT      NOT NULL UNIQUE,
    department_id BIGINT      NOT NULL,
    status        VARCHAR(20) NOT NULL DEFAULT 'ATIVO',

    FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE,

    FOREIGN KEY (department_id)
        REFERENCES departments (id)
);
