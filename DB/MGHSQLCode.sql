CREATE TABLE android_metadata (
	locale TEXT DEFAULT 'en_US');

INSERT INTO android_metadata VALUES ('en_US');

CREATE TABLE tblNode (
	_id INTEGER PRIMARY KEY,
	nID TEXT NOT NULL UNIQUE, 
	x INTEGER NOT NULL, 
	y INTEGER NOT NULL, 
	nFloor INTEGER NOT NULL, 
	nType TEXT NOT NULL, 
	nDep TEXT NOT NULL);
	
CREATE TABLE tblNeighbors (
	_id INTEGER PRIMARY KEY,
	mNode TEXT NOT NULL, 
	nNode TEXT NOT NULL,
	FOREIGN KEY(mNode) REFERENCES tblNode(nID),
	FOREIGN KEY(nNode) REFERENCES tblNode(nID));


CREATE TABLE tblDepartment (
	
_id INTEGER PRIMARY KEY,
	
dptName TEXT NOT NULL UNIQUE,
	
dptLocation TEXT NOT NULL,
	
dptPhone TEXT);

CREATE TABLE tblDoctors (
	
_id INTEGER PRIMARY KEY,

	drFname TEXT NOT NULL,
	drMI TEXT,
	DRLname TEXT NOT NULL,
	drDeptName TEXT NOT NULL,

	drPhoneNumber TEXT,

	FOREIGN KEY(drDeptName) REFERENCES tblDepartment(dptName));

INSERT INTO tblNode VALUES (1, '108Q', 1428, 352, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (2, '108P_0', 1523, 352, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (3, '108P', 1523, 413, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (4, '108A1_0', 1523, 483, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (5, '108A1_1', 1560, 483, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (6, '108A1_2', 1582, 483, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (7, '108A1_3', 1594, 483, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (8, '108A1_4', 1614, 483, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (9, '108A1_5', 1665, 483, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (10, '108A1_6', 1680, 483, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (11, '108A1_7', 1695, 483, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (12, '108A1_8', 1710, 483, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (13, '108A1_9', 1730, 483, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (14, '108A1_10', 1744, 483, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (15, '108A1_11', 1781, 483, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (16, '108A1_12', 1835, 483, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (17, '108O', 1582, 413, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (18, '108L', 1710, 413, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (19, '108K', 1744, 413, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (20, '108I', 1835, 413, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (21, '108J', 1781, 456, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (22, 'T1', 1680, 456, 1, 'Bathroom', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (23, 'T2', 1614, 456, 1, 'Bathroom', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (24, '108C', 1594, 504, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (25, '108D', 1665, 504, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (26, '108G', 1781, 504, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (27, '108B', 1560, 547, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (28, '108E', 1695, 542, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (29, '108F', 1730, 542, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (30, '108H', 1835, 542, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (31, '108', 1523, 637, 1, 'Reception', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (32, '108_0', 1523, 573, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (33, '108_1', 1453, 573, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (34, '108_2', 1453, 483, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (35, '100C1', 1523, 697, 1, 'Hallway', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (36, '100C2', 1193, 697, 1, 'Hallway', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (37, 'EL', 1193, 483, 1, 'Elevator', 'Walk-In Clinic');
	
INSERT INTO tblNeighbors VALUES (1, '108Q', '108P_0');
INSERT INTO tblNeighbors VALUES (2, '108P_0', '108Q');
INSERT INTO tblNeighbors VALUES (3, '108P_0', '108P');
INSERT INTO tblNeighbors VALUES (4, '108P', '108P_0');
INSERT INTO tblNeighbors VALUES (5, '108P', '108A1_0');
INSERT INTO tblNeighbors VALUES (6, '108A1_0', '108P');
INSERT INTO tblNeighbors VALUES (7, '108A1_0', '108_2');
INSERT INTO tblNeighbors VALUES (8, '108A1_0', '108A1_1');
INSERT INTO tblNeighbors VALUES (9, '108A1_1', '108A1_0');
INSERT INTO tblNeighbors VALUES (10, '108A1_1', '108B');
INSERT INTO tblNeighbors VALUES (11, '108A1_1', '108A1_2');
INSERT INTO tblNeighbors VALUES (12, '108A1_2', '108A1_1');
INSERT INTO tblNeighbors VALUES (13, '108A1_2', '108O');
INSERT INTO tblNeighbors VALUES (14, '108A1_2', '108A1_3');
INSERT INTO tblNeighbors VALUES (15, '108A1_3', '108A1_2');
INSERT INTO tblNeighbors VALUES (16, '108A1_3', '108C');
INSERT INTO tblNeighbors VALUES (17, '108A1_3', '108A1_4');
INSERT INTO tblNeighbors VALUES (18, '108A1_4', '108A1_3');
INSERT INTO tblNeighbors VALUES (19, '108A1_4', 'T2');
INSERT INTO tblNeighbors VALUES (20, '108A1_4', '108A1_5');
INSERT INTO tblNeighbors VALUES (21, '108A1_5', '108A1_4');
INSERT INTO tblNeighbors VALUES (22, '108A1_5', '108D');
INSERT INTO tblNeighbors VALUES (23, '108A1_5', '108A1_6');
INSERT INTO tblNeighbors VALUES (24, '108A1_6', '108A1_5');
INSERT INTO tblNeighbors VALUES (25, '108A1_6', 'T1');
INSERT INTO tblNeighbors VALUES (26, '108A1_6', '108A1_7');
INSERT INTO tblNeighbors VALUES (27, '108A1_7', '108A1_6');
INSERT INTO tblNeighbors VALUES (28, '108A1_7', '108E');
INSERT INTO tblNeighbors VALUES (29, '108A1_7', '108A1_8');
INSERT INTO tblNeighbors VALUES (30, '108A1_8', '108A1_7');
INSERT INTO tblNeighbors VALUES (31, '108A1_8', '108L');
INSERT INTO tblNeighbors VALUES (32, '108A1_8', '108A1_9');
INSERT INTO tblNeighbors VALUES (33, '108A1_9', '108A1_8');
INSERT INTO tblNeighbors VALUES (34, '108A1_9', '108F');
INSERT INTO tblNeighbors VALUES (35, '108A1_9', '108A1_10');
INSERT INTO tblNeighbors VALUES (36, '108A1_10', '108A1_9');
INSERT INTO tblNeighbors VALUES (37, '108A1_10', '108K');
INSERT INTO tblNeighbors VALUES (38, '108A1_10', '108A1_11');
INSERT INTO tblNeighbors VALUES (39, '108A1_11', '108A1_10');
INSERT INTO tblNeighbors VALUES (40, '108A1_11', '108J');
INSERT INTO tblNeighbors VALUES (41, '108A1_11', '108G');
INSERT INTO tblNeighbors VALUES (42, '108A1_11', '108A1_12');
INSERT INTO tblNeighbors VALUES (43, '108A1_12', '108A1_11');
INSERT INTO tblNeighbors VALUES (44, '108A1_12', '108I');
INSERT INTO tblNeighbors VALUES (45, '108A1_12', '108H');
INSERT INTO tblNeighbors VALUES (46, '108O', '108A1_2');
INSERT INTO tblNeighbors VALUES (47, '108L', '108A1_8');
INSERT INTO tblNeighbors VALUES (48, '108K', '108A1_10');
INSERT INTO tblNeighbors VALUES (49, '108I', '108A1_12');
INSERT INTO tblNeighbors VALUES (50, '108J', '108A1_11');
INSERT INTO tblNeighbors VALUES (51, 'T1', '108A1_6');
INSERT INTO tblNeighbors VALUES (52, 'T2', '108A1_4');
INSERT INTO tblNeighbors VALUES (53, '108C', '108A1_3');
INSERT INTO tblNeighbors VALUES (54, '108D', '108A1_5');
INSERT INTO tblNeighbors VALUES (55, '108G', '108A1_11');
INSERT INTO tblNeighbors VALUES (56, '108B', '108A1_1');
INSERT INTO tblNeighbors VALUES (57, '108E', '108A1_7');
INSERT INTO tblNeighbors VALUES (58, '108F', '108A1_9');
INSERT INTO tblNeighbors VALUES (59, '108H', '108A1_12');
INSERT INTO tblNeighbors VALUES (60, '108', '100C1');
INSERT INTO tblNeighbors VALUES (61, '108', '108_0');
INSERT INTO tblNeighbors VALUES (62, '108_0', '108');
INSERT INTO tblNeighbors VALUES (63, '108_0', '108_1');
INSERT INTO tblNeighbors VALUES (64, '108_1', '108_0');
INSERT INTO tblNeighbors VALUES (65, '108_1', '108_2');
INSERT INTO tblNeighbors VALUES (66, '108_2', '108_1');
INSERT INTO tblNeighbors VALUES (67, '108_2', '108A1_0');
INSERT INTO tblNeighbors VALUES (68, '100C1', '100C2');
INSERT INTO tblNeighbors VALUES (69, '100C1', '108');
INSERT INTO tblNeighbors VALUES (70, '100C2', 'EL');
INSERT INTO tblNeighbors VALUES (71, '100C2', '100C1');
INSERT INTO tblNeighbors VALUES (72, 'EL', '100C2');

INSERT INTO tblDepartment VALUES (1, 'Allergy and Immunology', 'Cox 201, 100 Blossom Street, Boston, MA 02114', '617-726-3850');
INSERT INTO tblDepartment VALUES (2, 'Diabetes', 'Multiple locations', '617-726-2000');
INSERT INTO tblDepartment VALUES (3, 'Endocrinology', '15 Fruit Street, WACC 730, Boston, MA 02114', '617-726-8720');
INSERT INTO tblDepartment VALUES (4, 'Heart Center', '55 Fruit Street, Boston, MA 02114', '866-MGH-8910');
INSERT INTO tblDepartment VALUES (5, 'Urology', '55 Fruit Street, Boston, MA 02114', '617-726-4530');
INSERT INTO tblDepartment VALUES (6, 'Surgery', '55 Fruit Street, White 506, Boston, MA 02114', '617-643-1010');
INSERT INTO tblDepartment VALUES (7, 'Neurology', 'Wang Ambulatory Care Center, 8th Floor, Suite 835, 15 Parkman Street, Boston, MA 02114', '617-726-5533');
INSERT INTO tblDepartment VALUES (8, 'Thyroid', '55 Fruit Street, GRB 740, Boston, MA 02114', '617-726-2862');
INSERT INTO tblDepartment VALUES (9, 'Vascular Center', '55 Fruit Street, Boston, MA 02114', '877-644-8346');



INSERT INTO tblDoctors VALUES (1, 'Aleena', null, 'Banerji', 'Allergy and Immunology', '617-726-3850');

INSERT INTO tblDoctors VALUES (2, 'Ellen', null, 'Dutta', 'Allergy and Immunology', '617-726-3850');

INSERT INTO tblDoctors VALUES (3, 'Daniel', null, 'Hamilos', 'Allergy and Immunology', '617-726-3850');

INSERT INTO tblDoctors VALUES (4, 'Joseph', null, 'Avruch', 'Diabetes', '617-726-8722');

INSERT INTO tblDoctors VALUES (5, 'Lloyd', null, 'Axelrod', 'Diabetes', '617-726-8722');

INSERT INTO tblDoctors VALUES (6, 'Clemens', null, 'Bergwitz', 'Endocrinology', '617-726-8720');

INSERT INTO tblDoctors VALUES (7, 'Richard', 'F.', 'Bringhurst', 'Endocrinology', '617-726-8720');

INSERT INTO tblDoctors VALUES (8, 'Suhny', null, 'Abbara', 'Heart Center', '617-726-0796');

INSERT INTO tblDoctors VALUES (9, 'Lauren', null, 'Abbate', 'Heart Center', '617-726-0796');

INSERT INTO tblDoctors VALUES (10, 'Douglas', null, 'Dahl', 'Urology', '617-726-0875');

INSERT INTO tblDoctors VALUES (11, 'Nancy', null, 'Cantelmo', 'Surgery', '617-726-4464');

INSERT INTO tblDoctors VALUES (12, 'Robert', null, 'Ackerman', 'Neurology', '617-726-8382');

INSERT INTO tblDoctors VALUES (13, 'Mark', null, 'Albers', 'Neurology', '617-726-1728');

INSERT INTO tblDoctors VALUES (14, 'Giuseppe', null, 'Barbesino', 'Thyroid', '617-726-7573');

INSERT INTO tblDoctors VALUES (15, 'Gilbert', null, 'Daniels', 'Thyroid', '617-726-8430');

INSERT INTO tblDoctors VALUES (16, 'Cary', null, 'Akins', 'Vascular Center', '617-726-8218');

INSERT INTO tblDoctors VALUES (17, 'Arvind', null, 'Agnihotri', 'Vascular Center', '617-726-5608');





