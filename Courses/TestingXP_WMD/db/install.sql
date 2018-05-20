-- -----------------------------------------------------------------------------
-- MathRocket Table Installation
-- 	The following SQL commands create the template of our application. 
-- 	The format of a table creation is follows:
--			Column Name(s)	Attribute(s)
--			Foreign Key(s)	Reference
--			Primary Key
-- -----------------------------------------------------------------------------

DROP TABLE IF EXISTS user_assignment;
DROP TABLE IF EXISTS problem_status;
DROP TABLE IF EXISTS problem;
DROP TABLE IF EXISTS assignment;
DROP TABLE IF EXISTS user;

-- A basic user, all fields (except enabled) should be provided. The user's
-- name and password can only be a maximum of 32 characters. The default for
-- enabled is true (1) but a user may be disabled. If the users role or level is
-- removed then the user will be removed. If the user is removed then any
-- assignments it has and problem statuses will be removed.
CREATE TABLE IF NOT EXISTS user (

  id				INT UNSIGNED NOT NULL AUTO_INCREMENT,
  role				ENUM('Admin','Instructor','Student') NOT NULL,
  level				ENUM('Easy', 'Medium', 'Hard') NOT NULL,
  name				VARCHAR(32) NOT NULL,
  password			VARCHAR(32) NOT NULL,
  enabled			TINYINT(1) DEFAULT '1' NOT NULL,

  PRIMARY KEY (id)

) TYPE=InnoDB;

-- Create an indice table for searching by name
CREATE UNIQUE INDEX user_name_index
	ON user (name);

	
-- An assignment with a name and whether its enabled (able to take). The 
-- assignment name cannot exceed 128 characters. By default an assignment is
-- disabled unless specified on the insert. If an assignment has been removed
-- then all associated problems, problem statuses, and user assigns will be
-- removed as well.
CREATE TABLE IF NOT EXISTS assignment (

  id				INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name				VARCHAR(128) NOT NULL,
  enabled			TINYINT(1) DEFAULT '0',

  PRIMARY KEY (id)

) TYPE=InnoDB;
	
-- A problem on an assignment with a level (and its number in the assignment) 
-- as well as its name must be given. If the associated assignment or level is
-- deleted then this problem will be deleted as well. If this problem is deleted
-- then all associated problem statuses will be deleted.
CREATE TABLE IF NOT EXISTS problem (

  assignment_id		INT UNSIGNED NOT NULL,
  level				ENUM('Easy', 'Medium', 'Hard') NOT NULL,
  problem_number	INT UNSIGNED NOT NULL,
  name				VARCHAR(128) NOT NULL,
  statement			BLOB,

  FOREIGN KEY (assignment_id)		
  	REFERENCES assignment(id)
  	ON DELETE CASCADE ON UPDATE CASCADE,
  	
  PRIMARY KEY (assignment_id, level, problem_number)

) TYPE=InnoDB;

-- A relation saying a user has a list of assignments to take. If the user or
-- assignment is removed this relation will automatically be removed. If user
-- assignment is removed then no other entries are affected.
CREATE TABLE IF NOT EXISTS user_assignment (

  user_id 			INT UNSIGNED NOT NULL,
  assignment_id		INT UNSIGNED NOT NULL,

  FOREIGN KEY (user_id)
	 REFERENCES user(id)
	 ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (assignment_id)
	 REFERENCES assignment(id)	
	 ON DELETE CASCADE ON UPDATE CASCADE,

  PRIMARY KEY (user_id, assignment_id)

) TYPE=InnoDB;

-- A status of a problem for a user taking an assignment. The default for the 
-- tries and correct are zero. When correct is 1 it signals true, and when its 
-- zero it implies false. If a user, assignment, or problem is removed then
-- this status will automatically be deleted. If a problem status is removed
-- no other entries are affected.
CREATE TABLE IF NOT EXISTS problem_status (

  user_id 			INT UNSIGNED NOT NULL,
  assignment_id		INT UNSIGNED NOT NULL,
  problem_number	INT UNSIGNED NOT NULL,
  tries				SMALLINT UNSIGNED DEFAULT '0',
  correct			TINYINT(1) UNSIGNED DEFAULT '0',

  FOREIGN KEY (user_id)
	 REFERENCES user(id) 
	 ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (assignment_id)
	 REFERENCES assignment(id)
	 ON DELETE CASCADE ON UPDATE CASCADE,
--  FOREIGN KEY (problem_number)
--	 REFERENCES problem(problem_number)
--	 ON DELETE CASCADE ON UPDATE CASCADE,

  PRIMARY KEY (user_id, assignment_id, problem_number)

) TYPE=InnoDB;
