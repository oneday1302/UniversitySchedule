create table users
(
	id serial not null constraint users_pkey primary key,
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
	id serial not null constraint teachers_pkey primary key constraint teachers_users_id_fk references users
);

create table courses
(
	id serial not null constraint courses_pkey primary key,
	name text not null unique
);

create table teachers_courses
(
	teacher_id integer not null references teachers (id) on delete cascade on update cascade,
	course_id integer not null references courses (id) on delete cascade on update cascade,
	constraint teachers_courses_pkey primary key (teacher_id, course_id)
);

create table groups
(
	id serial not null constraint groups_pkey primary key,
	name text not null unique
);

create table students
(
	id serial not null constraint students_pkey primary key constraint students_users_id_fk references users,
	group_id integer not null references groups (id)
);

create table classrooms
(
	id serial not null constraint classrooms_pkey primary key,
	name text not null unique
);

create table lessons
(
	id serial not null constraint lessons_pkey primary key,
	course_id integer not null references courses (id),
	teacher_id integer not null references teachers (id),
	gorup_id integer not null references groups (id),
	classroom_id integer not null references classrooms (id),
	lesson_date date not null,
	start_time time not null,
	end_time time not null
);