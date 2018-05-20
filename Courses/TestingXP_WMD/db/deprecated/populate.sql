-- Installs default entries
-- Either all are inserted, or none
START TRANSACTION;

-- Default roles
INSERT INTO role (name) VALUES ('administrator');
INSERT INTO role (name) VALUES ('instructor');
INSERT INTO role (name) VALUES ('student');

-- Default levels
INSERT INTO level (name) VALUES ('Easy');
INSERT INTO level (name) VALUES ('Medium');
INSERT INTO level (name) VALUES ('Hard');

-- Get the actual ids of the levels
SELECT @easy_id:=id		FROM level WHERE name='Easy';
SELECT @medium_id:=id	FROM level WHERE name='Medium';
SELECT @hard_id:=id		FROM level WHERE name='Hard';
	
-- Get the actual id of the admin and student roles
SELECT @admin_id:=id 	FROM role WHERE name='administrator';
SELECT @student_id:=id 	FROM role WHERE name='student';

-- Default Administrator
INSERT INTO user (name,password,role_id,level_id) 
	VALUES ('admin', 'admin', 'Admin', 'Hard');
	
-- Testing users (one for each level)
INSERT INTO user (name,password,role_id,level_id) 
	VALUES ('testEasy', 'testEasy', @student_id, @easy_id);
INSERT INTO user (name,password,role_id,level_id) 
	VALUES ('testMedium', 'testMedium', @student_id, @medium_id);
INSERT INTO user (name,password,role_id,level_id) 
	VALUES ('testHard', 'testHard', @student_id, @hard_id);

SELECT @user_admin:=id FROM user WHERE name='admin';
SELECT @user_easy:=id FROM user WHERE name='testEasy';
SELECT @user_medium:=id FROM user WHERE name='testMedium';
SELECT @user_hard:=id FROM user WHERE name='testHard';
	
-- Create Default Assignment
INSERT INTO assignment (name,enabled)
	VALUES ('testAssignment', '1');
	
SELECT @ass_id:=id FROM assignment WHERE name='testAssignment';

-- Assign Assignments to Users
INSERT INTO user_assignment VALUES (@user_easy, @ass_id);
INSERT INTO user_assignment VALUES (@user_medium, @ass_id);
INSERT INTO user_assignment VALUES (@user_hard, @ass_id);

-- Create default problems
SET @problem1='<?xml version="1.0" encoding="UTF-8"?><problem><question><text val="Fraction Question #1"/><newline/><text val="If there are 42 males in your class and that only represents "/><fraction num="2" den="3"/><newline/><text val="of the total number of people in the class, how many females are in the class?"/><newline/></question><answer><integer int="21"/><text val="people"/></answer></problem>';
SET @problem2='<?xml version="1.0" encoding="UTF-8"?><problem><question><text val="Fraction Question #2"/><newline/><text val="If there are 42 females in your class out of 63 students what"/><text val=" portion represents the number of males in the class?"/></question><answer><fraction num="1" den="3"/><text val="males"/></answer></problem>';
SET @problem3='<?xml version="1.0" encoding="UTF-8"?><problem><question><text val="Integer Question #1"/><newline/><text val="If 55% flowers in a garden are white, and there are 20"/><text val="flowers in the garden, how many white flowers are there?"/></question><answer><integer int="11"/><text val="white flowers"/></answer></problem>';
SET @problem4='<?xml version="1.0" encoding="UTF-8"?><problem><question><text val="Integer Question #2"/><newline/><text val="If you roll 4 die with a total of 20 points, what is your"/><text val="average roll per die?"/></question><answer><integer int="5"/><text val="points per die"/></answer></problem>';
SET @problem5='<?xml version="1.0" encoding="UTF-8"?><problem><question><text val="Fraction Question #3"/><newline/><text val="What is"/><fraction num="2" den="3"/><text val=" + 2"/><fraction num="1" den="4"/></question><answer><integer int="2"/><fraction num="11" den="12"/></answer></problem>';

-- Easy problems
INSERT INTO problem VALUES (@ass_id, @easy_id, 1, 'Problem1', @problem1);
INSERT INTO problem VALUES (@ass_id, @easy_id, 2, 'Problem2', @problem2);
INSERT INTO problem VALUES (@ass_id, @easy_id, 3, 'Problem3', @problem3);
INSERT INTO problem VALUES (@ass_id, @easy_id, 4, 'Problem4', @problem4);
INSERT INTO problem VALUES (@ass_id, @easy_id, 5, 'Problem5', @problem5);

-- Medium problems
INSERT INTO problem VALUES (@ass_id, @medium_id, 1, 'Problem1', @problem1);
INSERT INTO problem VALUES (@ass_id, @medium_id, 2, 'Problem2', @problem2);
INSERT INTO problem VALUES (@ass_id, @medium_id, 3, 'Problem3', @problem3);
INSERT INTO problem VALUES (@ass_id, @medium_id, 4, 'Problem4', @problem4);
INSERT INTO problem VALUES (@ass_id, @medium_id, 5, 'Problem5', @problem5);

-- Hard problems
INSERT INTO problem VALUES (@ass_id, @hard_id, 1, 'Problem1', @problem1);
INSERT INTO problem VALUES (@ass_id, @hard_id, 2, 'Problem2', @problem2);
INSERT INTO problem VALUES (@ass_id, @hard_id, 3, 'Problem3', @problem3);
INSERT INTO problem VALUES (@ass_id, @hard_id, 4, 'Problem4', @problem4);
INSERT INTO problem VALUES (@ass_id, @hard_id, 5, 'Problem5', @problem5);

-- Problem statuses
INSERT INTO problem_status VALUES (@user_easy, @ass_id, 1, 0, 0);
INSERT INTO problem_status VALUES (@user_easy, @ass_id, 2, 0, 0);
INSERT INTO problem_status VALUES (@user_easy, @ass_id, 3, 0, 0);
INSERT INTO problem_status VALUES (@user_easy, @ass_id, 4, 0, 0);
INSERT INTO problem_status VALUES (@user_easy, @ass_id, 5, 0, 0);

INSERT INTO problem_status VALUES (@user_medium, @ass_id, 1, 0, 0);
INSERT INTO problem_status VALUES (@user_medium, @ass_id, 2, 0, 0);
INSERT INTO problem_status VALUES (@user_medium, @ass_id, 3, 0, 0);
INSERT INTO problem_status VALUES (@user_medium, @ass_id, 4, 0, 0);
INSERT INTO problem_status VALUES (@user_medium, @ass_id, 5, 0, 0);

INSERT INTO problem_status VALUES (@user_hard, @ass_id, 1, 0, 0);
INSERT INTO problem_status VALUES (@user_hard, @ass_id, 2, 0, 0);
INSERT INTO problem_status VALUES (@user_hard, @ass_id, 3, 0, 0);
INSERT INTO problem_status VALUES (@user_hard, @ass_id, 4, 0, 0);
INSERT INTO problem_status VALUES (@user_hard, @ass_id, 5, 0, 0);

COMMIT;