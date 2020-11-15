/* 従業員テーブル */
create table if not exists employee(
	employee_id int primary key,
	employee_name varchar(50),
	age int
);