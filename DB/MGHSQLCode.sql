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
	
CREATE TABLE tblInterFloor	(
	_id INTEGER PRIMARY KEY,
	mNode TEXT NOT NULL,
	nNode TEXT NOT NULL,
	FOREIGN KEY(mNode) REFERENCES tblNode(nID),
	FOREIGN KEY(nNode) REFERENCES tblNode(nID));
	
INSERT INTO tblNode VALUES (1, 'F1-EL', 245, 109, 1, 'Elevator', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (2, 'F1-C2_0', 245, 387, 1, 'Waypoint', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (3, 'F1-C2_1', 245, 583, 1, 'Waypoint', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (4, 'F1-RS', 160, 583, 1, 'Restroom', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (5, 'F1-C3_0', 74, 340, 1, 'Waypoint', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (6, 'F1-S2', 74, 277, 1, 'Stair', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (7, 'F1-C1_0', 583, 387, 1, 'Waypoint', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (8, 'F1-C1_1', 907, 387, 1, 'Waypoint', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (9, 'F1-C1_2', 1120, 387, 1, 'Waypoint', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (10, 'F1-C1_3', 1070, 538, 1, 'Waypoint', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (11, 'F1-C1_4', 1158, 170, 1, 'Waypoint', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (12, 'F1-RN', 1070, 170, 1, 'Restroom', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (13, 'F1-RX', 907, 538, 1, 'Desk', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (14, 'F1-E2', 1021, 772, 1, 'EXIT', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (15, 'F1-E1', 1193, 15, 1, 'EXIT', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (16, 'F1-108', 583, 340, 1, 'Loby', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (17, 'F1-108_0', 583, 277, 1, 'Waypoint', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (18, 'F1-108_1', 509, 277, 1, 'Waypoint', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (19, 'F1-108_2', 509, 176, 1, 'Waypoint', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (20, 'F1-108R', 583, 243, 1, 'Desk', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (21, 'F1-108A_0', 583, 176, 1, 'Waypoint', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (22, 'F1-108A_1', 619, 176, 1, 'Waypoint', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (23, 'F1-108A_2', 647, 176, 1, 'Waypoint', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (24, 'F1-108A_3', 684, 176, 1, 'Waypoint', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (25, 'F1-108A_4', 734, 176, 1, 'Waypoint', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (26, 'F1-108A_5', 762, 176, 1, 'Waypoint', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (27, 'F1-108A_6', 774, 176, 1, 'Waypoint', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (28, 'F1-108A_7', 793, 176, 1, 'Waypoint', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (29, 'F1-108A_8', 806, 176, 1, 'Waypoint', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (30, 'F1-108A_9', 848, 176, 1, 'Waypoint', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (31, 'F1-108A_10', 907, 176, 1, 'Waypoint', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (32, 'F1-108B', 619, 243, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (33, 'F1-108C', 647, 201, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (34, 'F1-108D', 734, 201, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (35, 'F1-108E', 762, 243, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (36, 'F1-108F', 793, 243, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (37, 'F1-108G', 848, 201, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (38, 'F1-108H', 907, 243, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (39, 'F1-108I', 907, 109, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (40, 'F1-108J', 848, 155, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (41, 'F1-108K', 806, 109, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (42, 'F1-108L', 774, 109, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (43, 'F1-108O', 647, 109, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (44, 'F1-108P', 583, 109, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (45, 'F1-108P_0', 583, 47, 1, 'Waypoint', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (46, 'F1-108Q', 487, 47, 1, 'Exam Room', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (47, 'F1-T1', 684, 155, 1, 'Restroom', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (48, 'F1-T2', 734, 155, 1, 'Restroom', 'Walk-In Clinic');
INSERT INTO tblNode VALUES (49, 'F2-EL', 631, 134, 2, 'Elevator', 'Clinical Labs');
INSERT INTO tblNode VALUES (50, 'F2-C1_1', 631, 451, 2, 'Waypoint', 'Clinical Labs');
INSERT INTO tblNode VALUES (51, 'F2-C1_0', 409, 451, 2, 'Waypoint', 'Clinical Labs');
INSERT INTO tblNode VALUES (52, 'F2-S2', 409, 376, 2, 'Stair', 'Clinical Labs');
INSERT INTO tblNode VALUES (53, 'F2-R1', 306, 451, 2, 'Restroom', 'Clinical Labs');
INSERT INTO tblNode VALUES (54, 'F2-LAB', 600, 593, 2, 'Lab', 'Clinical Labs');
INSERT INTO tblNode VALUES (55, 'F2-XR', 663, 593, 2, 'Xray', 'Radiology');
INSERT INTO tblNode VALUES (56, 'F2-LAB_0', 600, 736, 2, 'Lab', 'Clinical Labs');
INSERT INTO tblNode VALUES (57, 'F2-LAB_1', 346, 736, 2, 'Lab', 'Clinical Labs');
INSERT INTO tblNode VALUES (58, 'F2-LAB_2', 346, 911, 2, 'Lab', 'Clinical Labs');
INSERT INTO tblNode VALUES (59, 'F2-LAB_3', 94, 911, 2, 'Lab', 'Clinical Labs');
INSERT INTO tblNode VALUES (60, 'F2-LAB_4', 94, 1136, 2, 'Lab', 'Clinical Labs');
INSERT INTO tblNode VALUES (61, 'F2-LAB_5', 171, 1136, 2, 'Lab', 'Clinical Labs');
INSERT INTO tblNode VALUES (62, 'F2-LAB_A', 171, 1157, 2, 'Lab', 'Clinical Labs');
INSERT INTO tblNode VALUES (63, 'F2-LAB_B', 208, 1333, 2, 'Lab', 'Clinical Labs');
INSERT INTO tblNode VALUES (64, 'F2-LAB_C', 363, 1378, 2, 'Lab', 'Clinical Labs');
INSERT INTO tblNode VALUES (65, 'F2-LAB_D', 346, 1420, 2, 'Lab', 'Clinical Labs');
INSERT INTO tblNode VALUES (66, 'F2-LAB_E', 94, 1051, 2, 'Lab', 'Clinical Labs');
INSERT INTO tblNode VALUES (67, 'F2-LAB_F', 94, 824, 2, 'Lab', 'Clinical Labs');
INSERT INTO tblNode VALUES (68, 'F2-LAB_B0', 274, 1394, 2, 'Lab', 'Clinical Labs');
INSERT INTO tblNode VALUES (69, 'F2-LAB_R', 346, 1003, 2, 'Lab', 'Clinical Labs');
INSERT INTO tblNode VALUES (70, 'F2-R2', 346, 1136, 2, 'Restroom', 'Clinical Labs');
INSERT INTO tblNode VALUES (71, 'F2-CL2', 94, 1213, 2, 'Closet', 'Clinical Labs');
INSERT INTO tblNode VALUES (72, 'F2-CL1', 94, 736, 2, 'Closet', 'Clinical Labs');

INSERT INTO tblNeighbors VALUES (1, 'F1-EL', 'F1-C2_0');
INSERT INTO tblNeighbors VALUES (2, 'F1-C2_0', 'F1-EL');
INSERT INTO tblNeighbors VALUES (3, 'F1-C2_1', 'F1-RS');
INSERT INTO tblNeighbors VALUES (4, 'F1-RS', 'F1-C2_1');
INSERT INTO tblNeighbors VALUES (5, 'F1-C3_0', 'F1-S2');
INSERT INTO tblNeighbors VALUES (6, 'F1-S2', 'F1-C3_0');
INSERT INTO tblNeighbors VALUES (7, 'F1-C1_0', 'F1-C2_0');
INSERT INTO tblNeighbors VALUES (8, 'F1-C1_1', 'F1-C1_0');
INSERT INTO tblNeighbors VALUES (9, 'F1-C1_2', 'F1-C1_1');
INSERT INTO tblNeighbors VALUES (10, 'F1-C1_3', 'F1-C1_2');
INSERT INTO tblNeighbors VALUES (11, 'F1-C1_4', 'F1-C1_2');
INSERT INTO tblNeighbors VALUES (12, 'F1-RN', 'F1-C1_4');
INSERT INTO tblNeighbors VALUES (13, 'F1-RX', 'F1-C1_3');
INSERT INTO tblNeighbors VALUES (14, 'F1-E2', 'F1-C1_3');
INSERT INTO tblNeighbors VALUES (15, 'F1-E1', 'F1-C1_4');
INSERT INTO tblNeighbors VALUES (16, 'F1-108', 'F1-C1_0');
INSERT INTO tblNeighbors VALUES (17, 'F1-108_0', 'F1-108');
INSERT INTO tblNeighbors VALUES (18, 'F1-108_1', 'F1-108_0');
INSERT INTO tblNeighbors VALUES (19, 'F1-108_2', 'F1-108_1');
INSERT INTO tblNeighbors VALUES (20, 'F1-108R', 'F1-108_0');
INSERT INTO tblNeighbors VALUES (21, 'F1-108A_0', 'F1-108_2');
INSERT INTO tblNeighbors VALUES (22, 'F1-108A_1', 'F1-108A_0');
INSERT INTO tblNeighbors VALUES (23, 'F1-108A_2', 'F1-108A_1');
INSERT INTO tblNeighbors VALUES (24, 'F1-108A_3', 'F1-108A_2');
INSERT INTO tblNeighbors VALUES (25, 'F1-108A_4', 'F1-108A_3');
INSERT INTO tblNeighbors VALUES (26, 'F1-108A_5', 'F1-108A_4');
INSERT INTO tblNeighbors VALUES (27, 'F1-108A_6', 'F1-108A_5');
INSERT INTO tblNeighbors VALUES (28, 'F1-108A_7', 'F1-108A_6');
INSERT INTO tblNeighbors VALUES (29, 'F1-108A_8', 'F1-108A_7');
INSERT INTO tblNeighbors VALUES (30, 'F1-108A_9', 'F1-108A_8');
INSERT INTO tblNeighbors VALUES (31, 'F1-108A_10', 'F1-108A_9');
INSERT INTO tblNeighbors VALUES (32, 'F1-108B', 'F1-108A_1');
INSERT INTO tblNeighbors VALUES (33, 'F1-108C', 'F1-108A_2');
INSERT INTO tblNeighbors VALUES (34, 'F1-108D', 'F1-108A_4');
INSERT INTO tblNeighbors VALUES (35, 'F1-108E', 'F1-108A_5');
INSERT INTO tblNeighbors VALUES (36, 'F1-108F', 'F1-108A_7');
INSERT INTO tblNeighbors VALUES (37, 'F1-108G', 'F1-108A_9');
INSERT INTO tblNeighbors VALUES (38, 'F1-108H', 'F1-108A_10');
INSERT INTO tblNeighbors VALUES (39, 'F1-108I', 'F1-108A_10');
INSERT INTO tblNeighbors VALUES (40, 'F1-108J', 'F1-108A_9');
INSERT INTO tblNeighbors VALUES (41, 'F1-108K', 'F1-108A_8');
INSERT INTO tblNeighbors VALUES (42, 'F1-108L', 'F1-108A_6');
INSERT INTO tblNeighbors VALUES (43, 'F1-108O', 'F1-108A_2');
INSERT INTO tblNeighbors VALUES (44, 'F1-108P', 'F1-108A_0');
INSERT INTO tblNeighbors VALUES (45, 'F1-108P_0', 'F1-108P');
INSERT INTO tblNeighbors VALUES (46, 'F1-108Q', 'F1-108P_0');
INSERT INTO tblNeighbors VALUES (47, 'F1-T1', 'F1-108A_3');
INSERT INTO tblNeighbors VALUES (48, 'F1-T2', 'F1-108A_4');
INSERT INTO tblNeighbors VALUES (49, 'F2-EL', 'F2-C1_1');
INSERT INTO tblNeighbors VALUES (50, 'F2-C1_1', 'F2-EL');
INSERT INTO tblNeighbors VALUES (51, 'F2-C1_0', 'F2-R1');
INSERT INTO tblNeighbors VALUES (52, 'F2-S2', 'F2-C1_0');
INSERT INTO tblNeighbors VALUES (53, 'F2-R1', 'F2-C1_0');
INSERT INTO tblNeighbors VALUES (54, 'F2-LAB', 'F2-C1_1');
INSERT INTO tblNeighbors VALUES (55, 'F2-XR', 'F2-C1_1');
INSERT INTO tblNeighbors VALUES (56, 'F2-LAB_0', 'F2-LAB');
INSERT INTO tblNeighbors VALUES (57, 'F2-LAB_1', 'F2-LAB_0');
INSERT INTO tblNeighbors VALUES (58, 'F2-LAB_2', 'F2-LAB_1');
INSERT INTO tblNeighbors VALUES (59, 'F2-LAB_3', 'F2-LAB_2');
INSERT INTO tblNeighbors VALUES (60, 'F2-LAB_4', 'F2-LAB_E');
INSERT INTO tblNeighbors VALUES (61, 'F2-LAB_5', 'F2-LAB_4');
INSERT INTO tblNeighbors VALUES (62, 'F2-LAB_A', 'F2-LAB_5');
INSERT INTO tblNeighbors VALUES (63, 'F2-LAB_B', 'F2-LAB_A');
INSERT INTO tblNeighbors VALUES (64, 'F2-LAB_C', 'F2-LAB_B0');
INSERT INTO tblNeighbors VALUES (65, 'F2-LAB_D', 'F2-LAB_B0');
INSERT INTO tblNeighbors VALUES (66, 'F2-LAB_E', 'F2-LAB_4');
INSERT INTO tblNeighbors VALUES (67, 'F2-LAB_F', 'F2-LAB_3');
INSERT INTO tblNeighbors VALUES (68, 'F2-LAB_B0', 'F2-LAB_B');
INSERT INTO tblNeighbors VALUES (69, 'F2-LAB_R', 'F2-LAB_2');
INSERT INTO tblNeighbors VALUES (70, 'F2-R2', 'F2-LAB_5');
INSERT INTO tblNeighbors VALUES (71, 'F2-CL2', 'F2-LAB_4');
INSERT INTO tblNeighbors VALUES (72, 'F2-CL1', 'F2-LAB_F');
INSERT INTO tblNeighbors VALUES (73, 'F1-C2_0', 'F1-C3_0');
INSERT INTO tblNeighbors VALUES (74, 'F1-C2_1', 'F1-C2_0');
INSERT INTO tblNeighbors VALUES (75, 'F1-C3_0', 'F1-C2_0');
INSERT INTO tblNeighbors VALUES (76, 'F1-C1_0', 'F1-C1_1');
INSERT INTO tblNeighbors VALUES (77, 'F1-C1_1', 'F1-C1_2');
INSERT INTO tblNeighbors VALUES (78, 'F1-C1_2', 'F1-C1_3');
INSERT INTO tblNeighbors VALUES (79, 'F1-C1_3', 'F1-E2');
INSERT INTO tblNeighbors VALUES (80, 'F1-C1_4', 'F1-E1');
INSERT INTO tblNeighbors VALUES (81, 'F1-108', 'F1-108_0');
INSERT INTO tblNeighbors VALUES (82, 'F1-108_0', 'F1-108R');
INSERT INTO tblNeighbors VALUES (83, 'F1-108_1', 'F1-108_2');
INSERT INTO tblNeighbors VALUES (84, 'F1-108_2', 'F1-108A_0');
INSERT INTO tblNeighbors VALUES (85, 'F1-108A_0', 'F1-108P');
INSERT INTO tblNeighbors VALUES (86, 'F1-108A_1', 'F1-108A_2');
INSERT INTO tblNeighbors VALUES (87, 'F1-108A_2', 'F1-108A_3');
INSERT INTO tblNeighbors VALUES (88, 'F1-108A_3', 'F1-108A_4');
INSERT INTO tblNeighbors VALUES (89, 'F1-108A_4', 'F1-108A_5');
INSERT INTO tblNeighbors VALUES (90, 'F1-108A_5', 'F1-108A_6');
INSERT INTO tblNeighbors VALUES (91, 'F1-108A_6', 'F1-108A_7');
INSERT INTO tblNeighbors VALUES (92, 'F1-108A_7', 'F1-108A_8');
INSERT INTO tblNeighbors VALUES (93, 'F1-108A_8', 'F1-108A_9');
INSERT INTO tblNeighbors VALUES (94, 'F1-108A_9', 'F1-108A_10');
INSERT INTO tblNeighbors VALUES (95, 'F1-108A_10', 'F1-108I');
INSERT INTO tblNeighbors VALUES (96, 'F1-108P', 'F1-108P_0');
INSERT INTO tblNeighbors VALUES (97, 'F1-108P_0', 'F1-108Q');
INSERT INTO tblNeighbors VALUES (98, 'F2-C1_1', 'F2-C1_0');
INSERT INTO tblNeighbors VALUES (99, 'F2-C1_0', 'F2-S2');
INSERT INTO tblNeighbors VALUES (100, 'F2-LAB', 'F2-LAB_0');
INSERT INTO tblNeighbors VALUES (101, 'F2-LAB_0', 'F2-LAB_1');
INSERT INTO tblNeighbors VALUES (102, 'F2-LAB_1', 'F2-LAB_2');
INSERT INTO tblNeighbors VALUES (103, 'F2-LAB_2', 'F2-LAB_3');
INSERT INTO tblNeighbors VALUES (104, 'F2-LAB_3', 'F2-LAB_E');
INSERT INTO tblNeighbors VALUES (105, 'F2-LAB_4', 'F2-LAB_5');
INSERT INTO tblNeighbors VALUES (106, 'F2-LAB_5', 'F2-LAB_A');
INSERT INTO tblNeighbors VALUES (107, 'F2-LAB_A', 'F2-LAB_B');
INSERT INTO tblNeighbors VALUES (108, 'F2-LAB_B', 'F2-LAB_B0');
INSERT INTO tblNeighbors VALUES (109, 'F2-LAB_E', 'F2-LAB_3');
INSERT INTO tblNeighbors VALUES (110, 'F2-LAB_F', 'F2-CL1');
INSERT INTO tblNeighbors VALUES (111, 'F2-LAB_B0', 'F2-LAB_C');
INSERT INTO tblNeighbors VALUES (112, 'F2-LAB_R', 'F2-R2');
INSERT INTO tblNeighbors VALUES (113, 'F2-R2', 'F2-LAB_R');
INSERT INTO tblNeighbors VALUES (114, 'F1-C2_0', 'F1-C1_0');
INSERT INTO tblNeighbors VALUES (115, 'F1-C1_0', 'F1-108');
INSERT INTO tblNeighbors VALUES (116, 'F1-C1_2', 'F1-C1_4');
INSERT INTO tblNeighbors VALUES (117, 'F1-C1_3', 'F1-RX');
INSERT INTO tblNeighbors VALUES (118, 'F1-C1_4', 'F1-RN');
INSERT INTO tblNeighbors VALUES (119, 'F1-108_0', 'F1-108_1');
INSERT INTO tblNeighbors VALUES (120, 'F1-108A_0', 'F1-108A_1');
INSERT INTO tblNeighbors VALUES (121, 'F1-108A_1', 'F1-108B');
INSERT INTO tblNeighbors VALUES (122, 'F1-108A_2', 'F1-108C');
INSERT INTO tblNeighbors VALUES (123, 'F1-108A_3', 'F1-T1');
INSERT INTO tblNeighbors VALUES (124, 'F1-108A_4', 'F1-108D');
INSERT INTO tblNeighbors VALUES (125, 'F1-108A_5', 'F1-108E');
INSERT INTO tblNeighbors VALUES (126, 'F1-108A_6', 'F1-108L');
INSERT INTO tblNeighbors VALUES (127, 'F1-108A_7', 'F1-108F');
INSERT INTO tblNeighbors VALUES (128, 'F1-108A_8', 'F1-108K');
INSERT INTO tblNeighbors VALUES (129, 'F1-108A_9', 'F1-108J');
INSERT INTO tblNeighbors VALUES (130, 'F1-108A_10', 'F1-108H');
INSERT INTO tblNeighbors VALUES (131, 'F2-C1_1', 'F2-LAB');
INSERT INTO tblNeighbors VALUES (132, 'F2-C1_0', 'F2-C1_1');
INSERT INTO tblNeighbors VALUES (133, 'F2-LAB_2', 'F2-LAB_R');
INSERT INTO tblNeighbors VALUES (134, 'F2-LAB_3', 'F2-LAB_F');
INSERT INTO tblNeighbors VALUES (135, 'F2-LAB_4', 'F2-CL2');
INSERT INTO tblNeighbors VALUES (136, 'F2-LAB_5', 'F2-R2');
INSERT INTO tblNeighbors VALUES (137, 'F2-LAB_B0', 'F2-LAB_D');
INSERT INTO tblNeighbors VALUES (138, 'F1-C2_0', 'F1-C2_1');
INSERT INTO tblNeighbors VALUES (139, 'F1-108A_2', 'F1-108O');
INSERT INTO tblNeighbors VALUES (140, 'F1-108A_4', 'F1-T2');
INSERT INTO tblNeighbors VALUES (141, 'F1-108A_9', 'F1-108G');
INSERT INTO tblNeighbors VALUES (142, 'F2-C1_1', 'F2-XR');

INSERT INTO tblInterFloor VALUES (1, 'F1-EL', 'F2-EL');
INSERT INTO tblInterFloor VALUES (2, 'F2-EL', 'F1-EL');
INSERT INTO tblInterFloor VALUES (3, 'F1-S2', 'F2-S2');
INSERT INTO tblInterFloor VALUES (4, 'F2-S2', 'F1-S2');