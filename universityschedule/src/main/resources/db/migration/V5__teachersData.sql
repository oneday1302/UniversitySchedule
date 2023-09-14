insert into university.users ("id", "username", "password", "email", "first_name", "last_name")
values (1, 'admin', '$2a$12$kHfXTrPJXclANCF0Yhu3Xe9rNManZibWkCC8A1z1a1dkyQ70CluQq', 'maclarenhem@gmail.com', 'Vladyslav', 'Kovalchuk'),
	   (2, 'teacher', '$2a$12$kHfXTrPJXclANCF0Yhu3Xe9rNManZibWkCC8A1z1a1dkyQ70CluQq', 'maclarenhem@gmail.com', 'Emily', 'Smith'),
	   (3, 'MusicLover45', '$2a$12$kHfXTrPJXclANCF0Yhu3Xe9rNManZibWkCC8A1z1a1dkyQ70CluQq', 'maclarenhem@gmail.com', 'Michael', 'Jones'),
	   (4, 'NatureExplorer77', '$2a$12$kHfXTrPJXclANCF0Yhu3Xe9rNManZibWkCC8A1z1a1dkyQ70CluQq', 'maclarenhem@gmail.com', 'Emma', 'Taylor'),
	   (5, 'FitnessFanatic99', '$2a$12$kHfXTrPJXclANCF0Yhu3Xe9rNManZibWkCC8A1z1a1dkyQ70CluQq', 'maclarenhem@gmail.com', 'Joshua', 'Williams'),
	   (6, 'Bookworm123', '$2a$12$kHfXTrPJXclANCF0Yhu3Xe9rNManZibWkCC8A1z1a1dkyQ70CluQq', 'maclarenhem@gmail.com', 'Madison', 'Brown'),
	   (7, 'AdventureSeeker55', '$2a$12$kHfXTrPJXclANCF0Yhu3Xe9rNManZibWkCC8A1z1a1dkyQ70CluQq', 'maclarenhem@gmail.com', 'Matthew', 'Davies'),
	   (8, 'FoodieDelight', '$2a$12$kHfXTrPJXclANCF0Yhu3Xe9rNManZibWkCC8A1z1a1dkyQ70CluQq', 'maclarenhem@gmail.com', 'Olivia', 'Evans'),
	   (9, 'TravelBug22', '$2a$12$kHfXTrPJXclANCF0Yhu3Xe9rNManZibWkCC8A1z1a1dkyQ70CluQq', 'maclarenhem@gmail.com', 'Ethan', 'Wilson'),
	   (10, 'GamingGuru88', '$2a$12$kHfXTrPJXclANCF0Yhu3Xe9rNManZibWkCC8A1z1a1dkyQ70CluQq', 'maclarenhem@gmail.com', 'Hannah', 'Thomas');

insert into university.users_roles ("user_id", "role")
values (1, 'TEACHER'),
	   (1, 'ADMIN'),
	   (2, 'TEACHER'),
	   (3, 'TEACHER'),
	   (4, 'TEACHER'),
	   (5, 'TEACHER'),
	   (6, 'TEACHER'),
	   (7, 'TEACHER'),
	   (8, 'TEACHER'),
	   (9, 'TEACHER'),
	   (10, 'TEACHER');

insert into university.teachers ("id")
values (1), (2), (3), (4), (5), (6), (7), (8) ,(9), (10);

insert into university.teachers_courses ("teacher_id", "course_id")
values (1, 1), (2, 2), (3, 3), (4, 4), (5, 5), (6, 6), (7, 7), (8, 8), (9, 9), (10, 10);