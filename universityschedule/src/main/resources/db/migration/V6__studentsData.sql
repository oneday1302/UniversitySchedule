insert into university.users ("id", "username", "password", "email", "first_name", "last_name")
values (11, 'student', '$2a$12$kHfXTrPJXclANCF0Yhu3Xe9rNManZibWkCC8A1z1a1dkyQ70CluQq', 'maclarenhem@gmail.com', 'Hannah', 'Roberts'),
	   (12, 'BeachBum123', '$2a$12$kHfXTrPJXclANCF0Yhu3Xe9rNManZibWkCC8A1z1a1dkyQ70CluQq', 'maclarenhem@gmail.com', 'Andrew', 'Johnson'),
	   (13, 'YogaEnthusiast44', '$2a$12$kHfXTrPJXclANCF0Yhu3Xe9rNManZibWkCC8A1z1a1dkyQ70CluQq', 'maclarenhem@gmail.com', 'Abigail', 'Lewis'),
	   (14, 'HikingAdventurer', '$2a$12$kHfXTrPJXclANCF0Yhu3Xe9rNManZibWkCC8A1z1a1dkyQ70CluQq', 'maclarenhem@gmail.com', 'Daniel', 'Walker'),
	   (15, 'AnimalRightsActivist', '$2a$12$kHfXTrPJXclANCF0Yhu3Xe9rNManZibWkCC8A1z1a1dkyQ70CluQq', 'maclarenhem@gmail.com', 'Isabella', 'Robinson'),
	   (16, 'MusicMaker101', '$2a$12$kHfXTrPJXclANCF0Yhu3Xe9rNManZibWkCC8A1z1a1dkyQ70CluQq', 'maclarenhem@gmail.com', 'William', 'Wood'),
	   (17, 'FitnessFreak22', '$2a$12$kHfXTrPJXclANCF0Yhu3Xe9rNManZibWkCC8A1z1a1dkyQ70CluQq', 'maclarenhem@gmail.com', 'Ashley', 'Thompson'),
	   (18, 'CulinaryArtist77', '$2a$12$kHfXTrPJXclANCF0Yhu3Xe9rNManZibWkCC8A1z1a1dkyQ70CluQq', 'maclarenhem@gmail.com', 'Joseph', 'White'),
	   (19, 'WanderlustTraveler', '$2a$12$kHfXTrPJXclANCF0Yhu3Xe9rNManZibWkCC8A1z1a1dkyQ70CluQq', 'maclarenhem@gmail.com', 'Ashley', 'Watson'),
	   (20, 'BoardGameGuru88', '$2a$12$kHfXTrPJXclANCF0Yhu3Xe9rNManZibWkCC8A1z1a1dkyQ70CluQq', 'maclarenhem@gmail.com', 'Joseph', 'Jackson');

insert into university.users_roles ("user_id", "role")
values (11, 'STUDENT'),
	   (12, 'STUDENT'),
	   (13, 'STUDENT'),
	   (14, 'STUDENT'),
	   (15, 'STUDENT'),
	   (16, 'STUDENT'),
	   (17, 'STUDENT'),
	   (18, 'STUDENT'),
	   (19, 'STUDENT'),
	   (20, 'STUDENT');

insert into university.students ("id", "group_id")
values (11, 1), (12, 1), (13, 1), (14, 2), (15, 2), (16, 2), (17, 3), (18, 3) ,(19, 4), (20, 4);