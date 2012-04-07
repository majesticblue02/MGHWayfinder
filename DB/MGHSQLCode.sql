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
	
INSERT INTO tblNode VALUES (1, 'f1-100s3', 512, 323, 1, 'Stair', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (2, 'f1-nel', 670, 546, 1, 'Elevator', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (3, 'f1-100C1_0', 697, 464, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (4, 'f1-100C1_1', 492, 421, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (5, 'f1-100C1_2', 359, 394, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (6, 'f1-100C1_3', 255, 909, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (7, 'f1-nr', 630, 1289, 1, 'Restroom', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (8, 'f1-100C1_4', 1267, 1355, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (9, 'f1-100C1_5', 1267, 2759, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (10, 'f1-100C2_0', 1267, 3689, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (11, 'f1-100C3_0', 1165, 4093, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (12, 'f1-100C3_1', 1165, 4195, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (13, 'f1-sel', 460, 3689, 1, 'Elevator', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (14, 'f1-100s2', 936, 4195, 1, 'Stair', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (15, 'f1-108', 1101, 2759, 1, 'Entrance', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (16, 'f1-108_0', 905, 2759, 1, 'Reception', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (17, 'f1-108_1', 905, 2939, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (18, 'f1-108_2', 646, 2939, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (19, 'f1-108A1', 646, 2828, 1, 'Entrance', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (20, 'f1-108A1_0', 646, 2738, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (21, 'f1-108A1_1', 646, 2634, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (22, 'f1-108A1_2', 646, 2558, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (23, 'f1-108A1_3', 646, 2519, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (24, 'f1-108A1_4', 646, 2477, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (25, 'f1-108A1_5', 646, 2360, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (26, 'f1-108A1_6', 646, 2281, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (27, 'f1-108A1_7', 646, 2235, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (28, 'f1-108A1_8', 646, 2220, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (29, 'f1-108A1_9', 646, 2145, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (30, 'f1-108A1_10', 646, 2100, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (31, 'f1-108A1_11', 646, 1986, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (32, 'f1-108A1_12', 646, 1847, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (33, 'f1-108B', 834, 2634, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (34, 'f1-108C', 717, 2519, 1, 'Closet', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (35, 'f1-108D', 717, 2360, 1, 'Closet', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (36, 'f1-108E', 834, 2235, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (37, 'f1-108F', 834, 2145, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (38, 'f1-108G', 717, 1986, 1, 'Closet', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (39, 'f1-108H', 834, 1847, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (40, 'f1-108I', 460, 1847, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (41, 'f1-108J', 579, 1986, 1, 'Closet', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (42, 'f1-108K', 460, 2100, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (43, 'f1-108L', 460, 2200, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (44, 'f1-108O', 460, 2558, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (45, 'f1-108P', 460, 2738, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (46, 'f1-108P_0', 227, 2738, 1, 'WP', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (47, 'f1-108Q', 227, 3005, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (48, 'f1-108T1', 579, 2281, 1, 'Restroom', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (49, 'f1-108T2', 579, 2477, 1, 'Restroom', 'Walk-In Clinic');

INSERT INTO tblNeighbors VALUES (1, 'f1-100s3', 'f1-100C1_1');
INSERT INTO tblNeighbors VALUES (2, 'f1-nel', 'f1-100C1_0');
INSERT INTO tblNeighbors VALUES (3, 'f1-100C1_0', 'f1-100C1_1');
INSERT INTO tblNeighbors VALUES (4, 'f1-100C1_1', 'f1-100C1_0');
INSERT INTO tblNeighbors VALUES (5, 'f1-100C1_2', 'f1-100C1_1');
INSERT INTO tblNeighbors VALUES (6, 'f1-100C1_3', 'f1-100C1_2');
INSERT INTO tblNeighbors VALUES (7, 'f1-nr', 'f1-100C1_3');
INSERT INTO tblNeighbors VALUES (8, 'f1-100C1_4', 'f1-100C1_3');
INSERT INTO tblNeighbors VALUES (9, 'f1-100C1_5', 'f1-100C1_4');
INSERT INTO tblNeighbors VALUES (10, 'f1-100C2_0', 'f1-100C1_5');
INSERT INTO tblNeighbors VALUES (11, 'f1-100C3_0', 'f1-100C2_0');
INSERT INTO tblNeighbors VALUES (12, 'f1-100C3_1', 'f1-100C3_0');
INSERT INTO tblNeighbors VALUES (13, 'f1-sel', 'f1-100C2_0');
INSERT INTO tblNeighbors VALUES (14, 'f1-100s2', 'f1-100C3_1');
INSERT INTO tblNeighbors VALUES (15, 'f1-108', 'f1-100C1_5');
INSERT INTO tblNeighbors VALUES (16, 'f1-108_0', 'f1-108');
INSERT INTO tblNeighbors VALUES (17, 'f1-108_1', 'f1-108_0');
INSERT INTO tblNeighbors VALUES (18, 'f1-108_2', 'f1-108A1');
INSERT INTO tblNeighbors VALUES (19, 'f1-108A1', 'f1-108A1_0');
INSERT INTO tblNeighbors VALUES (20, 'f1-108A1_0', 'f1-108A1');
INSERT INTO tblNeighbors VALUES (21, 'f1-108A1_1', 'f1-108A1_0');
INSERT INTO tblNeighbors VALUES (22, 'f1-108A1_2', 'f1-108A1_1');
INSERT INTO tblNeighbors VALUES (23, 'f1-108A1_3', 'f1-108A1_2');
INSERT INTO tblNeighbors VALUES (24, 'f1-108A1_4', 'f1-108A1_3');
INSERT INTO tblNeighbors VALUES (25, 'f1-108A1_5', 'f1-108A1_4');
INSERT INTO tblNeighbors VALUES (26, 'f1-108A1_6', 'f1-108A1_5');
INSERT INTO tblNeighbors VALUES (27, 'f1-108A1_7', 'f1-108A1_6');
INSERT INTO tblNeighbors VALUES (28, 'f1-108A1_8', 'f1-108A1_7');
INSERT INTO tblNeighbors VALUES (29, 'f1-108A1_9', 'f1-108A1_8');
INSERT INTO tblNeighbors VALUES (30, 'f1-108A1_10', 'f1-108A1_9');
INSERT INTO tblNeighbors VALUES (31, 'f1-108A1_11', 'f1-108A1_10');
INSERT INTO tblNeighbors VALUES (32, 'f1-108A1_12', 'f1-108A1_11');
INSERT INTO tblNeighbors VALUES (33, 'f1-108B', 'f1-108A1_1');
INSERT INTO tblNeighbors VALUES (34, 'f1-108C', 'f1-108A1_3');
INSERT INTO tblNeighbors VALUES (35, 'f1-108D', 'f1-108A1_5');
INSERT INTO tblNeighbors VALUES (36, 'f1-108E', 'f1-108A1_7');
INSERT INTO tblNeighbors VALUES (37, 'f1-108F', 'f1-108A1_9');
INSERT INTO tblNeighbors VALUES (38, 'f1-108G', 'f1-108A1_11');
INSERT INTO tblNeighbors VALUES (39, 'f1-108H', 'f1-108A1_12');
INSERT INTO tblNeighbors VALUES (40, 'f1-108I', 'f1-108A1_12');
INSERT INTO tblNeighbors VALUES (41, 'f1-108J', 'f1-108A1_11');
INSERT INTO tblNeighbors VALUES (42, 'f1-108K', 'f1-108A1_10');
INSERT INTO tblNeighbors VALUES (43, 'f1-108L', 'f1-108A1_8');
INSERT INTO tblNeighbors VALUES (44, 'f1-108O', 'f1-108A1_2');
INSERT INTO tblNeighbors VALUES (45, 'f1-108P', 'f1-108A1_0');
INSERT INTO tblNeighbors VALUES (46, 'f1-108P_0', 'f1-108P');
INSERT INTO tblNeighbors VALUES (47, 'f1-108Q', 'f1-108P_0');
INSERT INTO tblNeighbors VALUES (48, 'f1-108T1', 'f1-108A1_4');
INSERT INTO tblNeighbors VALUES (49, 'f1-108T2', 'f1-108A1_6');
INSERT INTO tblNeighbors VALUES (50, 'f1-100C1_1', 'f1-100C1_2');
INSERT INTO tblNeighbors VALUES (51, 'f1-100C1_2', 'f1-100C1_3');
INSERT INTO tblNeighbors VALUES (52, 'f1-100C1_3', 'f1-100C1_4');
INSERT INTO tblNeighbors VALUES (53, 'f1-100C1_4', 'f1-100C1_5');
INSERT INTO tblNeighbors VALUES (54, 'f1-100C1_5', 'f1-100C2_0');
INSERT INTO tblNeighbors VALUES (55, 'f1-100C2_0', 'f1-100C3_0');
INSERT INTO tblNeighbors VALUES (56, 'f1-100C3_0', 'f1-100C3_1');
INSERT INTO tblNeighbors VALUES (57, 'f1-100C3_1', 'f1-100C3_1');
INSERT INTO tblNeighbors VALUES (58, 'f1-108', 'f1-108_0');
INSERT INTO tblNeighbors VALUES (59, 'f1-108_0', 'f1-108_1');
INSERT INTO tblNeighbors VALUES (60, 'f1-108_1', 'f1-108_2');
INSERT INTO tblNeighbors VALUES (61, 'f1-108_2', 'f1-108_1');
INSERT INTO tblNeighbors VALUES (62, 'f1-108A1', 'f1-108_2');
INSERT INTO tblNeighbors VALUES (63, 'f1-108A1_0', 'f1-108A1_1');
INSERT INTO tblNeighbors VALUES (64, 'f1-108A1_1', 'f1-108A1_2');
INSERT INTO tblNeighbors VALUES (65, 'f1-108A1_2', 'f1-108A1_3');
INSERT INTO tblNeighbors VALUES (66, 'f1-108A1_3', 'f1-108A1_4');
INSERT INTO tblNeighbors VALUES (67, 'f1-108A1_4', 'f1-108A1_5');
INSERT INTO tblNeighbors VALUES (68, 'f1-108A1_5', 'f1-108A1_6');
INSERT INTO tblNeighbors VALUES (69, 'f1-108A1_6', 'f1-108A1_7');
INSERT INTO tblNeighbors VALUES (70, 'f1-108A1_7', 'f1-108A1_8');
INSERT INTO tblNeighbors VALUES (71, 'f1-108A1_8', 'f1-108A1_9');
INSERT INTO tblNeighbors VALUES (72, 'f1-108A1_9', 'f1-108A1_10');
INSERT INTO tblNeighbors VALUES (73, 'f1-108A1_10', 'f1-108A1_11');
INSERT INTO tblNeighbors VALUES (74, 'f1-108A1_11', 'f1-108A1_12');
INSERT INTO tblNeighbors VALUES (75, 'f1-108A1_12', 'f1-108I');
INSERT INTO tblNeighbors VALUES (76, 'f1-108P', 'f1-108P_0');
INSERT INTO tblNeighbors VALUES (77, 'f1-108P_0', 'f1-108Q');
INSERT INTO tblNeighbors VALUES (78, 'f1-108P', 'f1-108P_0');
INSERT INTO tblNeighbors VALUES (79, 'f1-108P_0', 'f1-108Q');
INSERT INTO tblNeighbors VALUES (80, 'f1-100C1_0', 'f1-nel');
INSERT INTO tblNeighbors VALUES (81, 'f1-100C1_1', 'f1-100s3');
INSERT INTO tblNeighbors VALUES (82, 'f1-100C1_3', 'f1-nr');
INSERT INTO tblNeighbors VALUES (83, 'f1-100C1_5', 'f1-108');
INSERT INTO tblNeighbors VALUES (84, 'f1-100C2_0', 'f1-sel');
INSERT INTO tblNeighbors VALUES (85, 'f1-100C3_1', 'f1-100s2');
INSERT INTO tblNeighbors VALUES (86, 'f1-108A1_0', 'f1-108P');
INSERT INTO tblNeighbors VALUES (87, 'f1-108A1_1', 'f1-108B');
INSERT INTO tblNeighbors VALUES (88, 'f1-108A1_2', 'f1-108O');
INSERT INTO tblNeighbors VALUES (89, 'f1-108A1_3', 'f1-108C');
INSERT INTO tblNeighbors VALUES (90, 'f1-108A1_4', 'f1-108T1');
INSERT INTO tblNeighbors VALUES (91, 'f1-108A1_5', 'f1-108D');
INSERT INTO tblNeighbors VALUES (92, 'f1-108A1_6', 'f1-108T2');
INSERT INTO tblNeighbors VALUES (93, 'f1-108A1_7', 'f1-108E');
INSERT INTO tblNeighbors VALUES (94, 'f1-108A1_8', 'f1-108L');
INSERT INTO tblNeighbors VALUES (95, 'f1-108A1_9', 'f1-108F');
INSERT INTO tblNeighbors VALUES (96, 'f1-108A1_10', 'f1-108K');
INSERT INTO tblNeighbors VALUES (97, 'f1-108A1_11', 'f1-108J');
INSERT INTO tblNeighbors VALUES (98, 'f1-108A1_12', 'f1-108H');
INSERT INTO tblNeighbors VALUES (99, 'f1-108A1_11', 'f1-108G');
