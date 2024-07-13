create sequence users_seq start with 1 increment by 1;

create table users
(
	id integer default nextval('users_seq') primary key,
	username text not null unique,
	password text not null,
	email text not null,
	first_name text not null,
	last_name text not null
);

create table users_roles
(
	user_id integer not null references users (id) on delete cascade on update cascade,
	role text not null,
	constraint users_roles_pkey primary key (user_id, role)
);

create table teachers
(
    id integer not null constraint teachers_pkey primary key constraint teachers_users_id_fk references users
);

create sequence courses_seq start with 1 increment by 1;

create table courses
(
	id integer default nextval('courses_seq') primary key,
	name text not null unique
);

create table teachers_courses
(
	teacher_id integer not null references teachers (id) on delete cascade on update cascade,
	course_id integer not null references courses (id),
	constraint teachers_courses_pkey primary key (teacher_id, course_id)
);

create sequence groups_seq start with 1 increment by 1;

create table groups
(
	id integer default nextval('groups_seq') primary key,
	name text not null unique
);

create table students
(
    id integer not null constraint students_pkey primary key constraint students_users_id_fk references users,
	group_id integer not null references groups (id)
);

create sequence classrooms_seq start with 1 increment by 1;

create table classrooms
(
	id integer default nextval('classrooms_seq') primary key,
	name text not null unique
);

create sequence lessons_seq start with 1 increment by 1;

create table lessons
(
	id integer default nextval('lessons_seq') primary key,
	course_id integer not null references courses (id),
	teacher_id integer not null references teachers (id),
	group_id integer not null references groups (id),
	classroom_id integer not null references classrooms (id),
	lesson_date date not null,
	start_time time not null,
	end_time time not null
);