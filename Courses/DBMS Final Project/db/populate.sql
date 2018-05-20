
-- Default groups
INSERT INTO groups (name, modify_root, modify_owner, modify_member)	--1
	VALUES ('Project Leader', 		'1', '1', '1');
INSERT INTO groups (name, modify_root, modify_owner, modify_member)	--2
	VALUES ('Senior Developer', 	'1', '1', '0');
INSERT INTO groups (name, modify_root, modify_owner, modify_member)	--3
	VALUES ('Senior Analyst', 		'1', '0', '0');
INSERT INTO groups (name, modify_root, modify_owner, modify_member)	--4
	VALUES ('Developer', 			'0', '0', '0');
INSERT INTO groups (name, modify_root, modify_owner, modify_member)	--5
	VALUES ('Intern', 				'0', '0', '0');
	
-- Default users
INSERT INTO users (alias, password, first_name, last_name, email, enabled)
	VALUES ('WhiteRider', 'G4nd4lf', 'Gandalf', 'Greyham', 'whiterider@gmail.com', '1');
INSERT INTO users (alias, password, first_name, last_name, email, enabled)
	VALUES ('Underhill', 'Fr0d0B4gg1n5', 'Frodo', 'Baggins', 'fbaggins@gmail.com', '0');
INSERT INTO users (alias, password, first_name, last_name, email, enabled)
	VALUES ('TheBrave', 'R0s3yC0tt0n', 'Samwise', 'Gamgee', 'sgamgee@gmail.com', '0');
INSERT INTO users (alias, password, first_name, last_name, email, enabled)
	VALUES ('Elessar', '4r4g0rn', 'Aragorn', '', 'kingofgondor@gmail.com', '1');
INSERT INTO users (alias, password, first_name, last_name, email, enabled)
	VALUES ('ElfPrince', 'P01ntyE4rs', 'Legolas', '', 'mirkwoodprince@gmail.com', '1');
	
-- Empty pages
INSERT INTO pages (name, title, parent_id)	--1
	VALUES ('CDSL', 'C Data Structure Library', NULL);
INSERT INTO pages (name, title, parent_id)	--2
	VALUES ('Array', 'Array based structures', 1);
INSERT INTO pages (name, title, parent_id)	--3
	VALUES ('Reference', 'Reference based structures', 1);
INSERT INTO pages (name, title, parent_id)	--4
	VALUES ('Stack', 'ArrayStack', 2);
INSERT INTO pages (name, title, parent_id)	--5
	VALUES ('Stack', 'LinkStack', 3);
INSERT INTO pages (name, title, parent_id)	--6
	VALUES ('Queue', 'ArrayQueue', 2);
INSERT INTO pages (name, title, parent_id)	--7
	VALUES ('Queue', 'LinkQueue', 3);

-- Comments 
INSERT INTO comments (page_id, user_alias, content, response_to, avg_rating, ratings)	--0
	VALUES (1, 'WhiteRider', 'I think I rule', NULL, 2.0, 5);
INSERT INTO comments (page_id, user_alias, content, response_to, avg_rating, ratings)	--1
	VALUES (1, 'Underhill', 'I agree', 0, 1.0, 10);
INSERT INTO comments (page_id, user_alias, content, response_to, avg_rating, ratings)	--2
	VALUES (1, 'WhiteRider', 'I know right', 1, 0.8, 8);
INSERT INTO comments (page_id, user_alias, content, response_to, avg_rating, ratings)	--3
	VALUES (1, 'ElfPrince', 'I disagree', 0, 5.0, 7);
INSERT INTO comments (page_id, user_alias, content, response_to, avg_rating, ratings)	--4
	VALUES (2, 'TheBrave', 'Im brave on the inside', NULL, 8.0, 9);
INSERT INTO comments (page_id, user_alias, content, response_to, avg_rating, ratings)	--5
	VALUES (2, 'Underhill', 'Im king', NULL, 0.0, 3);

-- Ownerships (groups own pages)
INSERT INTO ownerships (group_id, page_id) VALUES (1, 1);
INSERT INTO ownerships (group_id, page_id) VALUES (2, 1);
INSERT INTO ownerships (group_id, page_id) VALUES (3, 1);
INSERT INTO ownerships (group_id, page_id) VALUES (4, 1);
INSERT INTO ownerships (group_id, page_id) VALUES (5, 1);

-- Memberships (users in group)
INSERT INTO memberships (group_id, user_alias) VALUES (1, 'WhiteRider');
INSERT INTO memberships (group_id, user_alias) VALUES (2, 'Elessar');
INSERT INTO memberships (group_id, user_alias) VALUES (3, 'ElfPrince');
INSERT INTO memberships (group_id, user_alias) VALUES (4, 'Underhill');
INSERT INTO memberships (group_id, user_alias) VALUES (5, 'TheBrave');
