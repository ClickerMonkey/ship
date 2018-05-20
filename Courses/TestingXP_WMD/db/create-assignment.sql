SET autocommit=0;
START TRANSACTION;

SET @problem='<?xml version="1.0" encoding="UTF-8"?><problem><question><text val="Fraction Question #1"/><newline/><text val="If there are 42 males in your class and that only represents "/><fraction><num><integer int="2"/></num><den><integer int="3"/></den></fraction><integer int="3"/><den/><newline/><text val="of the total number of people in the class, how many females are in the class?"/><newline/></question><answer><integer int="21"/><text val="people"/></answer></problem>';

SELECT @user_id:=id FROM user WHERE level='Easy';

INSERT INTO assignment (name,enabled) VALUES ('Big Assignment','1');
SELECT @ass_id:=id FROM assignment WHERE name='Big Assignment';

INSERT INTO problem VALUES (@ass_id, 'Easy', 1, 'Problem #1', @problem);
INSERT INTO problem VALUES (@ass_id, 'Easy', 2, 'Problem #2', @problem);
INSERT INTO problem VALUES (@ass_id, 'Easy', 3, 'Problem #3', @problem);
INSERT INTO problem VALUES (@ass_id, 'Easy', 4, 'Problem #4', @problem);
INSERT INTO problem VALUES (@ass_id, 'Easy', 5, 'Problem #5', @problem);
INSERT INTO problem VALUES (@ass_id, 'Easy', 6, 'Problem #6', @problem);
INSERT INTO problem VALUES (@ass_id, 'Easy', 7, 'Problem #7', @problem);
INSERT INTO problem VALUES (@ass_id, 'Easy', 8, 'Problem #8', @problem);
INSERT INTO problem VALUES (@ass_id, 'Easy', 9, 'Problem #9', @problem);
INSERT INTO problem VALUES (@ass_id, 'Easy', 10, 'Problem #10', @problem);
INSERT INTO problem VALUES (@ass_id, 'Easy', 11, 'Problem #11', @problem);
INSERT INTO problem VALUES (@ass_id, 'Easy', 12, 'Problem #12', @problem);
INSERT INTO problem VALUES (@ass_id, 'Easy', 13, 'Problem #13', @problem);
INSERT INTO problem VALUES (@ass_id, 'Easy', 14, 'Problem #14', @problem);
INSERT INTO problem VALUES (@ass_id, 'Easy', 15, 'Problem #15', @problem);
INSERT INTO problem VALUES (@ass_id, 'Easy', 16, 'Problem #16', @problem);
INSERT INTO problem VALUES (@ass_id, 'Easy', 17, 'Problem #17', @problem);
INSERT INTO problem VALUES (@ass_id, 'Easy', 18, 'Problem #18', @problem);
INSERT INTO problem VALUES (@ass_id, 'Easy', 19, 'Problem #19', @problem);
INSERT INTO problem VALUES (@ass_id, 'Easy', 20, 'Problem #20', @problem);

INSERT INTO problem_status VALUES (@user_id, @ass_id, 1, 0, 0);
INSERT INTO problem_status VALUES (@user_id, @ass_id, 2, 0, 0);
INSERT INTO problem_status VALUES (@user_id, @ass_id, 3, 0, 0);
INSERT INTO problem_status VALUES (@user_id, @ass_id, 4, 0, 0);
INSERT INTO problem_status VALUES (@user_id, @ass_id, 5, 0, 0);
INSERT INTO problem_status VALUES (@user_id, @ass_id, 6, 0, 0);
INSERT INTO problem_status VALUES (@user_id, @ass_id, 7, 0, 0);
INSERT INTO problem_status VALUES (@user_id, @ass_id, 8, 0, 0);
INSERT INTO problem_status VALUES (@user_id, @ass_id, 9, 0, 0);
INSERT INTO problem_status VALUES (@user_id, @ass_id, 10, 0, 0);
INSERT INTO problem_status VALUES (@user_id, @ass_id, 11, 0, 0);
INSERT INTO problem_status VALUES (@user_id, @ass_id, 12, 0, 0);
INSERT INTO problem_status VALUES (@user_id, @ass_id, 13, 0, 0);
INSERT INTO problem_status VALUES (@user_id, @ass_id, 14, 0, 0);
INSERT INTO problem_status VALUES (@user_id, @ass_id, 15, 0, 0);
INSERT INTO problem_status VALUES (@user_id, @ass_id, 16, 0, 0);
INSERT INTO problem_status VALUES (@user_id, @ass_id, 17, 0, 0);
INSERT INTO problem_status VALUES (@user_id, @ass_id, 18, 0, 0);
INSERT INTO problem_status VALUES (@user_id, @ass_id, 19, 0, 0);
INSERT INTO problem_status VALUES (@user_id, @ass_id, 20, 0, 0);

INSERT INTO user_assignment VALUES (@user_id, @ass_id);

COMMIT;