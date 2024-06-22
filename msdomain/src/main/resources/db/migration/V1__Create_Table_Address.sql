-- Create address table
CREATE TABLE address (
    id BIGSERIAL PRIMARY KEY,
    city VARCHAR(60),
    country VARCHAR(60),
    number VARCHAR(20),
    state VARCHAR(30),
    street VARCHAR(30)
);

-- Create customer_contract table
CREATE TABLE customer_contract (
    id BIGSERIAL PRIMARY KEY,
    contract_time SMALLINT,
    monthly_amount FLOAT8,
    printer_type SMALLINT CHECK (printer_type BETWEEN 0 AND 10),
    printing_franchise INTEGER,
    start_contract DATE
);

-- Create customer table
CREATE TABLE customer (
    id BIGSERIAL PRIMARY KEY,
    pay_day SMALLINT NOT NULL,
    address_id BIGINT UNIQUE,
    contract_id BIGINT UNIQUE,
    cpf VARCHAR(14) UNIQUE,
    cnpj VARCHAR(18) UNIQUE,
    dtype VARCHAR(31) NOT NULL,
    bank_code VARCHAR(25),
    client_name VARCHAR(180),
    financial_situation VARCHAR(25) CHECK (financial_situation IN ('PAGO', 'PENDENTE', 'INADIMPLENTE', 'CANCELADO')),
    phone_number VARCHAR(20),
    primary_email VARCHAR(20) UNIQUE,
    whatsapp VARCHAR(20),
    CONSTRAINT fk_address FOREIGN KEY (address_id) REFERENCES address(id),
    CONSTRAINT fk_contract FOREIGN KEY (contract_id) REFERENCES customer_contract(id)
);

-- Create customer_email_list table
CREATE TABLE customer_email_list (
    customer_id BIGINT NOT NULL,
    email_list VARCHAR(100),
    CONSTRAINT fk_customer_email_list FOREIGN KEY (customer_id) REFERENCES customer(id)
);

-- Create monthly_payment table
CREATE TABLE monthly_payment (
    id BIGSERIAL PRIMARY KEY,
    amount_printer FLOAT8,
    excess_value_prints_color FLOAT8,
    excess_value_printspb FLOAT8,
    expiration_date DATE,
    month_payment INTEGER,
    monthly_amount FLOAT8,
    payment_date DATE,
    printing_franchise_color INTEGER,
    printing_franchisepb INTEGER,
    quantity_prints_color INTEGER,
    quantity_printspb INTEGER,
    rate_excess_black_and_white_printing FLOAT8,
    rate_excess_color_printing FLOAT8,
    year_payment INTEGER,
    customer_id BIGINT,
    invoice_number VARCHAR(30),
    payment_status VARCHAR(25) CHECK (payment_status IN ('PAGO', 'PENDENTE', 'ATRASADO', 'CANCELADO')),
    ticket_number VARCHAR(100),
    CONSTRAINT fk_monthly_payment_customer FOREIGN KEY (customer_id) REFERENCES customer(id)
);

-- Create multi_printer table
CREATE TABLE multi_printer (
    id BIGSERIAL PRIMARY KEY,
    amount_printer FLOAT8,
    impression_counter_before INTEGER,
    impression_counter_initial INTEGER,
    impression_counter_now INTEGER,
    machine_value FLOAT8,
    monthly_printer_amount FLOAT8,
    print_type SMALLINT CHECK (print_type BETWEEN 0 AND 10),
    printing_franchise INTEGER,
    customer_id BIGINT,
    brand VARCHAR(30),
    machine_status VARCHAR(25) CHECK (machine_status IN ('LOCADA', 'DISPONIVEL', 'MANUTENCAO', 'INATIVA')),
    model VARCHAR(40),
    serial_number VARCHAR(60) UNIQUE,
    CONSTRAINT fk_multi_printer_customer FOREIGN KEY (customer_id) REFERENCES customer(id)
);
