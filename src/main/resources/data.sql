DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS company;

CREATE TABLE employee (
    id INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(20),
    age INT,
    gender VARCHAR(10),
    company_id INT
);

INSERT INTO employee(name, age, gender, company_id)
VALUES('Eric', 18, 'male', 1),
('Chris', 18, 'male', 1),
('Ang', 18, 'female', 2),
('Hai feng', 18, 'male', 2);

CREATE TABLE company(
    company_id INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(20)
);

INSERT INTO company(name)
VALUES('oocl'),
('tw');