/* 従業員テーブル */
create table if not exists employee(
	employee_id int primary key,
	employee_name varchar(50),
	age int
);

/* ユーザーマスタ */
CREATE TABLE IF NOT EXISTS m_user(
	user_id VARCHAR(50) PRIMARY KEY,
	password VARCHAR(100),
	user_name VARCHAR(50),
	birthday DATE,
	age INT,
	marriage BOOLEAN,
	role VARCHAR(50)
);