insert into university.users ("username", "password", "email", "first_name", "last_name")
values ('admin', '$2a$12$kHfXTrPJXclANCF0Yhu3Xe9rNManZibWkCC8A1z1a1dkyQ70CluQq', 'maclarenhem@gmail.com', 'Vladyslav', 'Kovalchuk');

insert into university.users_roles ("user_id", "role")
values (1, 'TEACHER'), (1, 'ADMIN');

insert into university.teachers ("id") values (1);

insert into university.teachers_courses ("teacher_id", "course_id") values (1, 1);