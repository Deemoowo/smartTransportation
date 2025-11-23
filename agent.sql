/*
 Navicat Premium Dump SQL

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 90100 (9.1.0)
 Source Host           : localhost:3306
 Source Schema         : agent

 Target Server Type    : MySQL
 Target Server Version : 90100 (9.1.0)
 File Encoding         : 65001

 Date: 20/11/2025 14:00:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for citibike_trips_202402
-- ----------------------------
DROP TABLE IF EXISTS `citibike_trips_202402`;
CREATE TABLE `citibike_trips_202402` (
  `started_at` datetime DEFAULT NULL,
  `ended_at` datetime DEFAULT NULL,
  `start_station_name` varchar(255) DEFAULT NULL,
  `end_station_name` varchar(255) DEFAULT NULL,
  `start_lat` decimal(10,8) DEFAULT NULL,
  `start_lng` decimal(11,8) DEFAULT NULL,
  `end_lat` decimal(10,8) DEFAULT NULL,
  `end_lng` decimal(11,8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of citibike_trips_202402
-- ----------------------------
BEGIN;
INSERT INTO `citibike_trips_202402` (`started_at`, `ended_at`, `start_station_name`, `end_station_name`, `start_lat`, `start_lng`, `end_lat`, `end_lng`) VALUES ('2024-02-04 00:26:24', '2024-02-04 00:45:33', 'W 52 St & 11 Ave', 'E 85 St & 3 Ave', 40.76727216, -73.99392888, 40.77801203, -73.95407149);
INSERT INTO `citibike_trips_202402` (`started_at`, `ended_at`, `start_station_name`, `end_station_name`, `start_lat`, `start_lng`, `end_lat`, `end_lng`) VALUES ('2024-02-14 00:11:29', '2024-02-14 00:18:37', 'Graham Ave & Herbert St', 'Broadway & Berry St', 40.71929301, -73.94500379, 40.71036130, -73.96530390);
INSERT INTO `citibike_trips_202402` (`started_at`, `ended_at`, `start_station_name`, `end_station_name`, `start_lat`, `start_lng`, `end_lat`, `end_lng`) VALUES ('2024-02-13 00:58:10', '2024-02-13 01:12:16', 'Broadway & W 238 St', 'W 192 St & University Ave', 40.88524000, -73.90077000, 40.86689900, -73.90194200);
INSERT INTO `citibike_trips_202402` (`started_at`, `ended_at`, `start_station_name`, `end_station_name`, `start_lat`, `start_lng`, `end_lat`, `end_lng`) VALUES ('2024-02-02 00:21:19', '2024-02-02 00:25:37', 'N 10 St & Berry St', 'Broadway & Berry St', 40.72025300, -73.95708200, 40.71036130, -73.96530390);
INSERT INTO `citibike_trips_202402` (`started_at`, `ended_at`, `start_station_name`, `end_station_name`, `start_lat`, `start_lng`, `end_lat`, `end_lng`) VALUES ('2024-02-10 00:20:47', '2024-02-10 00:26:05', 'N 7 St & Driggs Ave', 'Broadway & Berry St', 40.71696700, -73.95638800, 40.71036130, -73.96530390);
INSERT INTO `citibike_trips_202402` (`started_at`, `ended_at`, `start_station_name`, `end_station_name`, `start_lat`, `start_lng`, `end_lat`, `end_lng`) VALUES ('2024-02-10 00:13:56', '2024-02-10 00:19:23', 'W 100 St & Broadway', 'W 84 St & Columbus Ave', 40.79737210, -73.97041192, 40.78499979, -73.97283406);
INSERT INTO `citibike_trips_202402` (`started_at`, `ended_at`, `start_station_name`, `end_station_name`, `start_lat`, `start_lng`, `end_lat`, `end_lng`) VALUES ('2024-02-10 00:31:32', '2024-02-10 00:52:42', 'Hope St & Union Ave', 'Carroll St & Smith St', 40.71167351, -73.95141312, 40.68061100, -73.99475825);
INSERT INTO `citibike_trips_202402` (`started_at`, `ended_at`, `start_station_name`, `end_station_name`, `start_lat`, `start_lng`, `end_lat`, `end_lng`) VALUES ('2024-02-10 00:18:37', '2024-02-10 00:39:44', 'West End Ave & W 60 St', 'Riverside Dr & W 82 St', 40.77237000, -73.99005000, 40.78720869, -73.98128127);
INSERT INTO `citibike_trips_202402` (`started_at`, `ended_at`, `start_station_name`, `end_station_name`, `start_lat`, `start_lng`, `end_lat`, `end_lng`) VALUES ('2024-02-10 00:39:19', '2024-02-10 00:49:45', 'Carmine St & 6 Ave', 'E 10 St & Ave A', 40.73038599, -74.00214988, 40.72740794, -73.98142006);
COMMIT;

-- ----------------------------
-- Table structure for complaints
-- ----------------------------
DROP TABLE IF EXISTS `complaints`;
CREATE TABLE `complaints` (
  `unique_key` bigint NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `closed_at` datetime DEFAULT NULL,
  `agency` varchar(64) DEFAULT NULL,
  `complaint_type` varchar(128) DEFAULT NULL,
  `descriptor` varchar(255) DEFAULT NULL,
  `status` varchar(64) DEFAULT NULL,
  `resolution_description` text,
  `borough` varchar(64) DEFAULT NULL,
  `latitude` decimal(10,7) DEFAULT NULL,
  `longitude` decimal(10,7) DEFAULT NULL,
  PRIMARY KEY (`unique_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of complaints
-- ----------------------------
BEGIN;
INSERT INTO `complaints` (`unique_key`, `created_at`, `closed_at`, `agency`, `complaint_type`, `descriptor`, `status`, `resolution_description`, `borough`, `latitude`, `longitude`) VALUES (60448388, '2024-02-29 23:17:00', '2024-03-04 06:37:00', 'DEP', 'Air Quality', 'Air: Odor/Fumes, Vehicle Idling (AD3)', 'Closed', 'The Department of Environmental Protection will keep the location under observation for idling commercial vehicles.__Summonses will be issued when warranted.', 'MANHATTAN', 40.7853460, -73.9534460);
INSERT INTO `complaints` (`unique_key`, `created_at`, `closed_at`, `agency`, `complaint_type`, `descriptor`, `status`, `resolution_description`, `borough`, `latitude`, `longitude`) VALUES (60450717, '2024-02-29 23:52:05', '2024-07-08 14:01:37', 'TLC', 'For Hire Vehicle Complaint', 'Driver Complaint - Non Passenger', 'Closed', 'A hearing was scheduled at the OATH/Taxi and Limousine Tribunal.', 'MANHATTAN', 40.7681426, -73.9628192);
INSERT INTO `complaints` (`unique_key`, `created_at`, `closed_at`, `agency`, `complaint_type`, `descriptor`, `status`, `resolution_description`, `borough`, `latitude`, `longitude`) VALUES (60452507, '2024-02-29 23:28:27', '2024-02-29 23:58:59', 'NYPD', 'Noise - Street/Sidewalk', 'Loud Music/Party', 'Closed', 'The Police Department responded to the complaint and with the information available observed no evidence of the violation at that time.', 'MANHATTAN', 40.8035744, -73.9485606);
INSERT INTO `complaints` (`unique_key`, `created_at`, `closed_at`, `agency`, `complaint_type`, `descriptor`, `status`, `resolution_description`, `borough`, `latitude`, `longitude`) VALUES (60453491, '2024-02-29 23:44:18', '2024-02-29 23:58:20', 'NYPD', 'Noise - Vehicle', 'Engine Idling', 'Closed', 'The Police Department responded and upon arrival those responsible for the condition were gone.', 'MANHATTAN', 40.7631215, -73.9817918);
INSERT INTO `complaints` (`unique_key`, `created_at`, `closed_at`, `agency`, `complaint_type`, `descriptor`, `status`, `resolution_description`, `borough`, `latitude`, `longitude`) VALUES (60453718, '2024-02-29 23:47:52', '2024-03-01 00:03:51', 'NYPD', 'Noise - Street/Sidewalk', 'Loud Music/Party', 'Closed', 'The Police Department responded to the complaint and took action to fix the condition.', 'MANHATTAN', 40.8302225, -73.9476836);
INSERT INTO `complaints` (`unique_key`, `created_at`, `closed_at`, `agency`, `complaint_type`, `descriptor`, `status`, `resolution_description`, `borough`, `latitude`, `longitude`) VALUES (60454314, '2024-02-29 23:49:54', '2024-03-01 16:47:17', 'DOT', 'Street Condition', 'Defective Hardware', 'Closed', 'The Department of Transportation inspected the condition and determined it was in compliance with their standards, not hazardous, or a valid permit exists.', 'MANHATTAN', 40.7595543, -73.9892973);
INSERT INTO `complaints` (`unique_key`, `created_at`, `closed_at`, `agency`, `complaint_type`, `descriptor`, `status`, `resolution_description`, `borough`, `latitude`, `longitude`) VALUES (60454712, '2024-02-29 23:41:46', '2024-03-01 00:05:49', 'NYPD', 'Noise - Vehicle', 'Engine Idling', 'Closed', 'The Police Department responded to the complaint and with the information available observed no evidence of the violation at that time.', 'MANHATTAN', 40.7322553, -73.9927042);
INSERT INTO `complaints` (`unique_key`, `created_at`, `closed_at`, `agency`, `complaint_type`, `descriptor`, `status`, `resolution_description`, `borough`, `latitude`, `longitude`) VALUES (60455494, '2024-02-29 23:47:24', '2024-03-01 16:48:09', 'DOT', 'Street Condition', 'Defective Hardware', 'Closed', 'The Department of Transportation inspected the condition and issued a Corrective Action Repair (CAR) to the contractor or utility company. The responsible party has a maximum of 30 days to correct the condition. If the condition still exists, a summons will be issued. In some cases, the Department of Transportation will make the repairs and charge the responsible party for the costs.', 'MANHATTAN', 40.7591645, -73.9883914);
INSERT INTO `complaints` (`unique_key`, `created_at`, `closed_at`, `agency`, `complaint_type`, `descriptor`, `status`, `resolution_description`, `borough`, `latitude`, `longitude`) VALUES (60457359, '2024-02-29 23:27:27', '2024-03-01 00:04:06', 'NYPD', 'Noise - Street/Sidewalk', 'Loud Music/Party', 'Closed', 'The Police Department responded to the complaint and took action to fix the condition.', 'MANHATTAN', 40.7429543, -74.0079682);
INSERT INTO `complaints` (`unique_key`, `created_at`, `closed_at`, `agency`, `complaint_type`, `descriptor`, `status`, `resolution_description`, `borough`, `latitude`, `longitude`) VALUES (60457362, '2024-02-29 23:36:25', '2024-03-01 00:00:23', 'NYPD', 'Noise - Street/Sidewalk', 'Loud Music/Party', 'Closed', 'The Police Department responded to the complaint and with the information available observed no evidence of the violation at that time.', 'MANHATTAN', 40.7731001, -73.9644736);
COMMIT;

-- ----------------------------
-- Table structure for motor_vehicle_collisions
-- ----------------------------
DROP TABLE IF EXISTS `motor_vehicle_collisions`;
CREATE TABLE `motor_vehicle_collisions` (
  `borough` varchar(64) DEFAULT NULL,
  `latitude` decimal(10,7) DEFAULT NULL,
  `longitude` decimal(10,7) DEFAULT NULL,
  `on_street_name` varchar(255) DEFAULT NULL,
  `cross_street_name` varchar(255) DEFAULT NULL,
  `number_of_persons_injured` int DEFAULT NULL,
  `number_of_persons_killed` int DEFAULT NULL,
  `number_of_pedestrians_injured` int DEFAULT NULL,
  `number_of_pedestrians_killed` int DEFAULT NULL,
  `number_of_cyclist_injured` int DEFAULT NULL,
  `number_of_cyclist_killed` int DEFAULT NULL,
  `number_of_motorist_injured` int DEFAULT NULL,
  `number_of_motorist_killed` int DEFAULT NULL,
  `contributing_factor_vehicle_1` varchar(255) DEFAULT NULL,
  `contributing_factor_vehicle_2` varchar(255) DEFAULT NULL,
  `contributing_factor_vehicle_3` varchar(255) DEFAULT NULL,
  `contributing_factor_vehicle_4` varchar(255) DEFAULT NULL,
  `contributing_factor_vehicle_5` varchar(255) DEFAULT NULL,
  `collision_id` bigint NOT NULL,
  `vehicle_type_code_1` varchar(255) DEFAULT NULL,
  `vehicle_type_code_2` varchar(255) DEFAULT NULL,
  `vehicle_type_code_3` varchar(255) DEFAULT NULL,
  `vehicle_type_code_4` varchar(255) DEFAULT NULL,
  `vehicle_type_code_5` varchar(255) DEFAULT NULL,
  `crash_datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`collision_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of motor_vehicle_collisions
-- ----------------------------
BEGIN;
INSERT INTO `motor_vehicle_collisions` (`borough`, `latitude`, `longitude`, `on_street_name`, `cross_street_name`, `number_of_persons_injured`, `number_of_persons_killed`, `number_of_pedestrians_injured`, `number_of_pedestrians_killed`, `number_of_cyclist_injured`, `number_of_cyclist_killed`, `number_of_motorist_injured`, `number_of_motorist_killed`, `contributing_factor_vehicle_1`, `contributing_factor_vehicle_2`, `contributing_factor_vehicle_3`, `contributing_factor_vehicle_4`, `contributing_factor_vehicle_5`, `collision_id`, `vehicle_type_code_1`, `vehicle_type_code_2`, `vehicle_type_code_3`, `vehicle_type_code_4`, `vehicle_type_code_5`, `crash_datetime`) VALUES ('UNKNOWN', 40.7188100, -73.8326600, 'GRAND CENTRAL PKWY', 'Unknown', 1, 0, 0, 0, 0, 0, 1, 0, 'Unsafe Speed', 'Other Vehicular', 'Unspecified', 'Unspecified', 'Unspecified', 4706092, 'Sedan', 'Station Wagon/Sport Utility Vehicle', 'Station Wagon/Sport Utility Vehicle', 'Sedan', 'Unknown', '2024-02-29 18:15:00');
INSERT INTO `motor_vehicle_collisions` (`borough`, `latitude`, `longitude`, `on_street_name`, `cross_street_name`, `number_of_persons_injured`, `number_of_persons_killed`, `number_of_pedestrians_injured`, `number_of_pedestrians_killed`, `number_of_cyclist_injured`, `number_of_cyclist_killed`, `number_of_motorist_injured`, `number_of_motorist_killed`, `contributing_factor_vehicle_1`, `contributing_factor_vehicle_2`, `contributing_factor_vehicle_3`, `contributing_factor_vehicle_4`, `contributing_factor_vehicle_5`, `collision_id`, `vehicle_type_code_1`, `vehicle_type_code_2`, `vehicle_type_code_3`, `vehicle_type_code_4`, `vehicle_type_code_5`, `crash_datetime`) VALUES ('UNKNOWN', 40.6749530, -73.8020500, 'VAN WYCK EXPWY', 'Unknown', 2, 0, 0, 0, 0, 0, 2, 0, 'Unsafe Speed', 'Other Vehicular', 'Unspecified', 'Unspecified', 'Unspecified', 4706343, 'Sedan', 'Tractor Truck Diesel', 'Unknown', 'Unknown', 'Unknown', '2024-02-29 19:00:00');
INSERT INTO `motor_vehicle_collisions` (`borough`, `latitude`, `longitude`, `on_street_name`, `cross_street_name`, `number_of_persons_injured`, `number_of_persons_killed`, `number_of_pedestrians_injured`, `number_of_pedestrians_killed`, `number_of_cyclist_injured`, `number_of_cyclist_killed`, `number_of_motorist_injured`, `number_of_motorist_killed`, `contributing_factor_vehicle_1`, `contributing_factor_vehicle_2`, `contributing_factor_vehicle_3`, `contributing_factor_vehicle_4`, `contributing_factor_vehicle_5`, `collision_id`, `vehicle_type_code_1`, `vehicle_type_code_2`, `vehicle_type_code_3`, `vehicle_type_code_4`, `vehicle_type_code_5`, `crash_datetime`) VALUES ('BRONX', 40.8309100, -73.9204700, 'GRAND CONCOURSE', 'EAST 165 STREET', 3, 0, 0, 0, 0, 0, 3, 0, 'Failure to Yield Right-of-Way', 'Driver Inattention/Distraction', 'Unspecified', 'Unspecified', 'Unspecified', 4706469, 'Sedan', 'Moped', 'Unknown', 'Unknown', 'Unknown', '2024-02-29 16:33:00');
INSERT INTO `motor_vehicle_collisions` (`borough`, `latitude`, `longitude`, `on_street_name`, `cross_street_name`, `number_of_persons_injured`, `number_of_persons_killed`, `number_of_pedestrians_injured`, `number_of_pedestrians_killed`, `number_of_cyclist_injured`, `number_of_cyclist_killed`, `number_of_motorist_injured`, `number_of_motorist_killed`, `contributing_factor_vehicle_1`, `contributing_factor_vehicle_2`, `contributing_factor_vehicle_3`, `contributing_factor_vehicle_4`, `contributing_factor_vehicle_5`, `collision_id`, `vehicle_type_code_1`, `vehicle_type_code_2`, `vehicle_type_code_3`, `vehicle_type_code_4`, `vehicle_type_code_5`, `crash_datetime`) VALUES ('BROOKLYN', 40.7275540, -73.9365200, 'Unknown', 'Unknown', 0, 0, 0, 0, 0, 0, 0, 0, 'Driver Inattention/Distraction', 'Unspecified', 'Unspecified', 'Unspecified', 'Unspecified', 4709821, 'Station Wagon/Sport Utility Vehicle', 'Unknown', 'Unknown', 'Unknown', 'Unknown', '2024-02-29 17:40:00');
INSERT INTO `motor_vehicle_collisions` (`borough`, `latitude`, `longitude`, `on_street_name`, `cross_street_name`, `number_of_persons_injured`, `number_of_persons_killed`, `number_of_pedestrians_injured`, `number_of_pedestrians_killed`, `number_of_cyclist_injured`, `number_of_cyclist_killed`, `number_of_motorist_injured`, `number_of_motorist_killed`, `contributing_factor_vehicle_1`, `contributing_factor_vehicle_2`, `contributing_factor_vehicle_3`, `contributing_factor_vehicle_4`, `contributing_factor_vehicle_5`, `collision_id`, `vehicle_type_code_1`, `vehicle_type_code_2`, `vehicle_type_code_3`, `vehicle_type_code_4`, `vehicle_type_code_5`, `crash_datetime`) VALUES ('MANHATTAN', 40.7684360, -73.9890600, 'WEST 56 STREET', '10 AVENUE', 1, 0, 0, 0, 0, 0, 1, 0, 'Driver Inattention/Distraction', 'Unspecified', 'Unspecified', 'Unspecified', 'Unspecified', 4709840, 'Sedan', 'Sedan', 'Unknown', 'Unknown', 'Unknown', '2024-02-29 02:14:00');
INSERT INTO `motor_vehicle_collisions` (`borough`, `latitude`, `longitude`, `on_street_name`, `cross_street_name`, `number_of_persons_injured`, `number_of_persons_killed`, `number_of_pedestrians_injured`, `number_of_pedestrians_killed`, `number_of_cyclist_injured`, `number_of_cyclist_killed`, `number_of_motorist_injured`, `number_of_motorist_killed`, `contributing_factor_vehicle_1`, `contributing_factor_vehicle_2`, `contributing_factor_vehicle_3`, `contributing_factor_vehicle_4`, `contributing_factor_vehicle_5`, `collision_id`, `vehicle_type_code_1`, `vehicle_type_code_2`, `vehicle_type_code_3`, `vehicle_type_code_4`, `vehicle_type_code_5`, `crash_datetime`) VALUES ('BRONX', 40.8073270, -73.9165800, 'Unknown', 'Unknown', 0, 0, 0, 0, 0, 0, 0, 0, 'Aggressive Driving/Road Rage', 'Unspecified', 'Unspecified', 'Unspecified', 'Unspecified', 4710741, 'Sedan', 'Station Wagon/Sport Utility Vehicle', 'Unknown', 'Unknown', 'Unknown', '2024-02-29 10:07:00');
INSERT INTO `motor_vehicle_collisions` (`borough`, `latitude`, `longitude`, `on_street_name`, `cross_street_name`, `number_of_persons_injured`, `number_of_persons_killed`, `number_of_pedestrians_injured`, `number_of_pedestrians_killed`, `number_of_cyclist_injured`, `number_of_cyclist_killed`, `number_of_motorist_injured`, `number_of_motorist_killed`, `contributing_factor_vehicle_1`, `contributing_factor_vehicle_2`, `contributing_factor_vehicle_3`, `contributing_factor_vehicle_4`, `contributing_factor_vehicle_5`, `collision_id`, `vehicle_type_code_1`, `vehicle_type_code_2`, `vehicle_type_code_3`, `vehicle_type_code_4`, `vehicle_type_code_5`, `crash_datetime`) VALUES ('MANHATTAN', 40.7239100, -73.9879760, '1 AVENUE', 'EAST 2 STREET', 1, 0, 0, 0, 0, 0, 1, 0, 'Driver Inattention/Distraction', 'Unspecified', 'Unspecified', 'Unspecified', 'Unspecified', 4711472, 'Taxi', 'Unknown', 'Unknown', 'Unknown', 'Unknown', '2024-02-29 02:11:00');
INSERT INTO `motor_vehicle_collisions` (`borough`, `latitude`, `longitude`, `on_street_name`, `cross_street_name`, `number_of_persons_injured`, `number_of_persons_killed`, `number_of_pedestrians_injured`, `number_of_pedestrians_killed`, `number_of_cyclist_injured`, `number_of_cyclist_killed`, `number_of_motorist_injured`, `number_of_motorist_killed`, `contributing_factor_vehicle_1`, `contributing_factor_vehicle_2`, `contributing_factor_vehicle_3`, `contributing_factor_vehicle_4`, `contributing_factor_vehicle_5`, `collision_id`, `vehicle_type_code_1`, `vehicle_type_code_2`, `vehicle_type_code_3`, `vehicle_type_code_4`, `vehicle_type_code_5`, `crash_datetime`) VALUES ('UNKNOWN', NULL, NULL, 'EAST 98 STREET', 'TAPSCOTT STREET', 0, 0, 0, 0, 0, 0, 0, 0, 'Unspecified', 'Unspecified', 'Unspecified', 'Unspecified', 'Unspecified', 4711787, 'Sedan', 'Sedan', 'Unknown', 'Unknown', 'Unknown', '2024-02-29 21:49:00');
INSERT INTO `motor_vehicle_collisions` (`borough`, `latitude`, `longitude`, `on_street_name`, `cross_street_name`, `number_of_persons_injured`, `number_of_persons_killed`, `number_of_pedestrians_injured`, `number_of_pedestrians_killed`, `number_of_cyclist_injured`, `number_of_cyclist_killed`, `number_of_motorist_injured`, `number_of_motorist_killed`, `contributing_factor_vehicle_1`, `contributing_factor_vehicle_2`, `contributing_factor_vehicle_3`, `contributing_factor_vehicle_4`, `contributing_factor_vehicle_5`, `collision_id`, `vehicle_type_code_1`, `vehicle_type_code_2`, `vehicle_type_code_3`, `vehicle_type_code_4`, `vehicle_type_code_5`, `crash_datetime`) VALUES ('UNKNOWN', NULL, NULL, 'ALEXANDER HAMILTON BRIDGE', 'Unknown', 1, 0, 0, 0, 0, 0, 1, 0, 'Driver Inattention/Distraction', 'Unspecified', 'Unspecified', 'Unspecified', 'Unspecified', 4714277, 'Sedan', 'Unknown', 'Unknown', 'Unknown', 'Unknown', '2024-02-29 18:40:00');
INSERT INTO `motor_vehicle_collisions` (`borough`, `latitude`, `longitude`, `on_street_name`, `cross_street_name`, `number_of_persons_injured`, `number_of_persons_killed`, `number_of_pedestrians_injured`, `number_of_pedestrians_killed`, `number_of_cyclist_injured`, `number_of_cyclist_killed`, `number_of_motorist_injured`, `number_of_motorist_killed`, `contributing_factor_vehicle_1`, `contributing_factor_vehicle_2`, `contributing_factor_vehicle_3`, `contributing_factor_vehicle_4`, `contributing_factor_vehicle_5`, `collision_id`, `vehicle_type_code_1`, `vehicle_type_code_2`, `vehicle_type_code_3`, `vehicle_type_code_4`, `vehicle_type_code_5`, `crash_datetime`) VALUES ('BROOKLYN', 40.6364480, -73.9451300, 'NEW YORK AVENUE', 'FARRAGUT ROAD', 1, 0, 0, 0, 1, 0, 0, 0, 'Unspecified', 'Unspecified', 'Unspecified', 'Unspecified', 'Unspecified', 4760457, 'Sedan', 'Bike', 'Unknown', 'Unknown', 'Unknown', '2024-02-29 13:51:00');
COMMIT;

-- ----------------------------
-- Table structure for nyc_permitted_events
-- ----------------------------
DROP TABLE IF EXISTS `nyc_permitted_events`;
CREATE TABLE `nyc_permitted_events` (
  `event_id` bigint NOT NULL,
  `event_name` varchar(255) DEFAULT NULL,
  `start_at` datetime DEFAULT NULL,
  `end_at` datetime DEFAULT NULL,
  `event_borough` varchar(64) DEFAULT NULL,
  `event_location` text,
  `event_street_side` varchar(64) DEFAULT NULL,
  `street_closure_type` varchar(64) DEFAULT NULL,
  `latitude` decimal(10,7) DEFAULT NULL,
  `longitude` decimal(10,7) DEFAULT NULL,
  `geocode_query` varchar(255) DEFAULT NULL,
  `geocode_status` varchar(64) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of nyc_permitted_events
-- ----------------------------
BEGIN;
INSERT INTO `nyc_permitted_events` (`event_id`, `event_name`, `start_at`, `end_at`, `event_borough`, `event_location`, `event_street_side`, `street_closure_type`, `latitude`, `longitude`, `geocode_query`, `geocode_status`) VALUES (758822, 'YSL Loveshine Cart', '2024-02-09 12:00:00', '2024-02-09 18:00:00', 'Manhattan', 'LAFAYETTE STREET between BROOME STREET and SPRING STREET', 'West', 'Curb Lane Only', 40.7223025, -73.9971855, 'Lafayette St & Spring St', 'OK');
INSERT INTO `nyc_permitted_events` (`event_id`, `event_name`, `start_at`, `end_at`, `event_borough`, `event_location`, `event_street_side`, `street_closure_type`, `latitude`, `longitude`, `geocode_query`, `geocode_status`) VALUES (698301, 'Street Health Outreach and Wellness Program SHOW', '2024-02-02 09:00:00', '2024-02-02 17:00:00', 'Manhattan', 'GRAND STREET between FORSYTH STREET and CHRYSTIE STREET', 'North', 'Curb Lane Only', 40.7178134, -73.9931814, 'Grand St & Forsyth St, Manhattan, New York, NY, USA', 'OK');
INSERT INTO `nyc_permitted_events` (`event_id`, `event_name`, `start_at`, `end_at`, `event_borough`, `event_location`, `event_street_side`, `street_closure_type`, `latitude`, `longitude`, `geocode_query`, `geocode_status`) VALUES (754093, 'IEG - AES Annex417 Production curb lane', '2024-02-09 06:30:00', '2024-02-09 17:00:00', 'Manhattan', 'EAST 38 STREET between 5 AVENUE and MADISON AVENUE', 'South', 'Curb Lane Only', 40.7509848, -73.9822725, 'E 38th St & 5 Ave', 'OK');
INSERT INTO `nyc_permitted_events` (`event_id`, `event_name`, `start_at`, `end_at`, `event_borough`, `event_location`, `event_street_side`, `street_closure_type`, `latitude`, `longitude`, `geocode_query`, `geocode_status`) VALUES (702875, 'Street Health Outreach  Wellness Program', '2024-02-23 10:00:00', '2024-02-23 18:00:00', 'Manhattan', 'MT MORRIS PARK WEST between W 124TH STREET and WEST 123RD STREET', 'West', 'Curb Lane Only', 40.8061513, -73.9446602, 'Mt Morris Park West & W 124th St, Manhattan, New York, NY, USA', 'OK');
INSERT INTO `nyc_permitted_events` (`event_id`, `event_name`, `start_at`, `end_at`, `event_borough`, `event_location`, `event_street_side`, `street_closure_type`, `latitude`, `longitude`, `geocode_query`, `geocode_status`) VALUES (747821, 'Mt Sinai Mobile Van - Prostate Cancer Screening at 75 W. 125th St.', '2024-02-01 12:00:00', '2024-02-01 15:00:00', 'Manhattan', 'WEST 125 STREET between 5 AVENUE and MALCOLM X BOULEVARD', 'North', 'Curb Lane Only', 40.8061620, -73.9426089, 'W 125th St & 5 Ave, Manhattan, New York, NY, USA', 'OK');
INSERT INTO `nyc_permitted_events` (`event_id`, `event_name`, `start_at`, `end_at`, `event_borough`, `event_location`, `event_street_side`, `street_closure_type`, `latitude`, `longitude`, `geocode_query`, `geocode_status`) VALUES (758759, 'Buccellati Flower Cart Activation', '2024-02-14 12:00:00', '2024-02-14 17:00:00', 'Manhattan', 'MADISON AVENUE between EAST 63 STREET and EAST 64 STREET', 'West', 'Partial Sidewalk Closure', 42.6526626, -73.7681129, 'Madison Ave & E 63 St, New York, NY, USA', 'OK');
INSERT INTO `nyc_permitted_events` (`event_id`, `event_name`, `start_at`, `end_at`, `event_borough`, `event_location`, `event_street_side`, `street_closure_type`, `latitude`, `longitude`, `geocode_query`, `geocode_status`) VALUES (759298, 'Load-In/Out / Guest Drop Off / Red Carpet', '2024-02-21 09:00:00', '2024-02-21 21:00:00', 'Manhattan', 'IRVING PLACE between EAST 15 STREET and EAST 16 STREET', 'West', 'Sidewalk and Curb Lane Closure', 40.7353934, -73.9879531, 'Irving Pl & E 16 St, Manhattan, New York, NY, USA', 'OK');
INSERT INTO `nyc_permitted_events` (`event_id`, `event_name`, `start_at`, `end_at`, `event_borough`, `event_location`, `event_street_side`, `street_closure_type`, `latitude`, `longitude`, `geocode_query`, `geocode_status`) VALUES (702875, 'Street Health Outreach  Wellness Program', '2024-02-12 10:00:00', '2024-02-12 18:00:00', 'Manhattan', 'MT MORRIS PARK WEST between W 124TH STREET and WEST 123RD STREET', 'West', 'Curb Lane Only', 40.8061513, -73.9446602, 'Mt Morris Park West & W 124th St, Manhattan, New York, NY, USA', 'OK');
INSERT INTO `nyc_permitted_events` (`event_id`, `event_name`, `start_at`, `end_at`, `event_borough`, `event_location`, `event_street_side`, `street_closure_type`, `latitude`, `longitude`, `geocode_query`, `geocode_status`) VALUES (758067, 'Gucci Airstream', '2024-02-09 08:00:00', '2024-02-09 16:30:00', 'Manhattan', '14 STREET between 9 AVENUE and WASHINGTON STREET', 'South', 'Sidewalk and Curb Lane Closure', 43.1583149, -77.6043792, '14 St & Washington St, Manhattan, New York, NY, USA', 'OK');
INSERT INTO `nyc_permitted_events` (`event_id`, `event_name`, `start_at`, `end_at`, `event_borough`, `event_location`, `event_street_side`, `street_closure_type`, `latitude`, `longitude`, `geocode_query`, `geocode_status`) VALUES (751919, 'the 26th Lunar New Year Parade', '2024-02-25 13:00:00', '2024-02-25 16:00:00', 'Manhattan', 'MOTT STREET between BROOME STREET and CANAL STREET', 'Both', 'Full Street Closure', 42.2542549, -71.7902743, 'Mott St & Canal St', 'OK');
INSERT INTO `nyc_permitted_events` (`event_id`, `event_name`, `start_at`, `end_at`, `event_borough`, `event_location`, `event_street_side`, `street_closure_type`, `latitude`, `longitude`, `geocode_query`, `geocode_status`) VALUES (751919, 'the 26th Lunar New Year Parade', '2024-02-25 13:00:00', '2024-02-25 16:00:00', 'Manhattan', 'HESTER STREET between BOWERY and MULBERRY STREET', 'Both', 'Full Street Closure', 40.7174636, -73.9952580, 'Hester St & Bowery, Manhattan, New York, NY, USA', 'OK');
INSERT INTO `nyc_permitted_events` (`event_id`, `event_name`, `start_at`, `end_at`, `event_borough`, `event_location`, `event_street_side`, `street_closure_type`, `latitude`, `longitude`, `geocode_query`, `geocode_status`) VALUES (751919, 'the 26th Lunar New Year Parade', '2024-02-25 13:00:00', '2024-02-25 16:00:00', 'Manhattan', 'MOTT STREET between CANAL STREET and CHATHAM SQUARE', 'Both', 'Full Street Closure', 42.2542549, -71.7902743, 'Mott St & Canal St', 'OK');
INSERT INTO `nyc_permitted_events` (`event_id`, `event_name`, `start_at`, `end_at`, `event_borough`, `event_location`, `event_street_side`, `street_closure_type`, `latitude`, `longitude`, `geocode_query`, `geocode_status`) VALUES (751919, 'the 26th Lunar New Year Parade', '2024-02-25 13:00:00', '2024-02-25 16:00:00', 'Manhattan', 'EAST BROADWAY between CHATHAM SQUARE and FORSYTH STREET', 'Both', 'Full Street Closure', 40.7136945, -73.9977248, 'East Broadway & Chatham Sq, Manhattan, New York, NY, USA', 'OK');
INSERT INTO `nyc_permitted_events` (`event_id`, `event_name`, `start_at`, `end_at`, `event_borough`, `event_location`, `event_street_side`, `street_closure_type`, `latitude`, `longitude`, `geocode_query`, `geocode_status`) VALUES (751919, 'the 26th Lunar New Year Parade', '2024-02-25 13:00:00', '2024-02-25 16:00:00', 'Manhattan', 'ELDRIDGE STREET between EAST BROADWAY and HESTER STREET', 'Both', 'Full Street Closure', NULL, NULL, NULL, 'NOT_FOUND');
INSERT INTO `nyc_permitted_events` (`event_id`, `event_name`, `start_at`, `end_at`, `event_borough`, `event_location`, `event_street_side`, `street_closure_type`, `latitude`, `longitude`, `geocode_query`, `geocode_status`) VALUES (751919, 'the 26th Lunar New Year Parade', '2024-02-25 13:00:00', '2024-02-25 16:00:00', 'Manhattan', 'HESTER STREET between ELDRIDGE STREET and FORSYTH STREET', 'Both', 'Full Street Closure', 36.1522373, -80.1972529, 'Hester St & Forsyth St', 'OK');
INSERT INTO `nyc_permitted_events` (`event_id`, `event_name`, `start_at`, `end_at`, `event_borough`, `event_location`, `event_street_side`, `street_closure_type`, `latitude`, `longitude`, `geocode_query`, `geocode_status`) VALUES (751919, 'the 26th Lunar New Year Parade', '2024-02-25 13:00:00', '2024-02-25 16:00:00', 'Manhattan', 'FORSYTH STREET between HESTER STREET and GRAND STREET', 'Both', 'Full Street Closure', 36.1522373, -80.1972529, 'Forsyth St & Hester St', 'OK');
INSERT INTO `nyc_permitted_events` (`event_id`, `event_name`, `start_at`, `end_at`, `event_borough`, `event_location`, `event_street_side`, `street_closure_type`, `latitude`, `longitude`, `geocode_query`, `geocode_status`) VALUES (751919, 'the 26th Lunar New Year Parade', '2024-02-25 13:00:00', '2024-02-25 16:00:00', 'Manhattan', 'CHATHAM SQUARE between MOTT STREET and EAST BROADWAY', 'Both', 'Full Street Closure', 40.7136945, -73.9977248, 'Chatham Sq & East Broadway, Manhattan, New York, NY, USA', 'OK');
INSERT INTO `nyc_permitted_events` (`event_id`, `event_name`, `start_at`, `end_at`, `event_borough`, `event_location`, `event_street_side`, `street_closure_type`, `latitude`, `longitude`, `geocode_query`, `geocode_status`) VALUES (751919, 'the 26th Lunar New Year Parade', '2024-02-25 13:00:00', '2024-02-25 16:00:00', 'Manhattan', 'FORSYTH STREET between GRAND STREET and DELANCEY STREET', 'Both', 'Full Street Closure', 40.7178134, -73.9931814, 'Forsyth St & Grand St', 'OK');
INSERT INTO `nyc_permitted_events` (`event_id`, `event_name`, `start_at`, `end_at`, `event_borough`, `event_location`, `event_street_side`, `street_closure_type`, `latitude`, `longitude`, `geocode_query`, `geocode_status`) VALUES (751919, 'the 26th Lunar New Year Parade', '2024-02-25 13:00:00', '2024-02-25 16:00:00', 'Manhattan', 'BROOME STREET between FORSYTH STREET and ELDRIDGE STREET', 'Both', 'Full Street Closure', 40.7189390, -73.9926629, 'Broome St & Forsyth St, Manhattan, New York, NY, USA', 'OK');
COMMIT;

-- ----------------------------
-- Table structure for subway_ridership
-- ----------------------------
DROP TABLE IF EXISTS `subway_ridership`;
CREATE TABLE `subway_ridership` (
  `transit_timestamp` datetime DEFAULT NULL,
  `station_complex_id` int DEFAULT NULL,
  `station_complex` varchar(128) DEFAULT NULL,
  `borough` varchar(64) DEFAULT NULL,
  `ridership` int DEFAULT NULL,
  `latitude` decimal(9,6) DEFAULT NULL,
  `longitude` decimal(9,6) DEFAULT NULL,
  `stratum` varchar(128) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of subway_ridership
-- ----------------------------
BEGIN;
INSERT INTO `subway_ridership` (`transit_timestamp`, `station_complex_id`, `station_complex`, `borough`, `ridership`, `latitude`, `longitude`, `stratum`) VALUES ('2024-02-01 19:00:00', 119, '1 Av (L)', 'Manhattan', 803, 40.730953, -73.981630, '2024-02-01_1 Av (L)');
INSERT INTO `subway_ridership` (`transit_timestamp`, `station_complex_id`, `station_complex`, `borough`, `ridership`, `latitude`, `longitude`, `stratum`) VALUES ('2024-02-01 07:00:00', 119, '1 Av (L)', 'Manhattan', 86, 40.730953, -73.981630, '2024-02-01_1 Av (L)');
INSERT INTO `subway_ridership` (`transit_timestamp`, `station_complex_id`, `station_complex`, `borough`, `ridership`, `latitude`, `longitude`, `stratum`) VALUES ('2024-02-01 23:00:00', 119, '1 Av (L)', 'Manhattan', 28, 40.730953, -73.981630, '2024-02-01_1 Av (L)');
INSERT INTO `subway_ridership` (`transit_timestamp`, `station_complex_id`, `station_complex`, `borough`, `ridership`, `latitude`, `longitude`, `stratum`) VALUES ('2024-02-01 14:00:00', 119, '1 Av (L)', 'Manhattan', 32, 40.730953, -73.981630, '2024-02-01_1 Av (L)');
INSERT INTO `subway_ridership` (`transit_timestamp`, `station_complex_id`, `station_complex`, `borough`, `ridership`, `latitude`, `longitude`, `stratum`) VALUES ('2024-02-01 10:00:00', 119, '1 Av (L)', 'Manhattan', 18, 40.730953, -73.981630, '2024-02-01_1 Av (L)');
INSERT INTO `subway_ridership` (`transit_timestamp`, `station_complex_id`, `station_complex`, `borough`, `ridership`, `latitude`, `longitude`, `stratum`) VALUES ('2024-02-01 03:00:00', 119, '1 Av (L)', 'Manhattan', 2, 40.730953, -73.981630, '2024-02-01_1 Av (L)');
INSERT INTO `subway_ridership` (`transit_timestamp`, `station_complex_id`, `station_complex`, `borough`, `ridership`, `latitude`, `longitude`, `stratum`) VALUES ('2024-02-01 15:00:00', 119, '1 Av (L)', 'Manhattan', 546, 40.730953, -73.981630, '2024-02-01_1 Av (L)');
INSERT INTO `subway_ridership` (`transit_timestamp`, `station_complex_id`, `station_complex`, `borough`, `ridership`, `latitude`, `longitude`, `stratum`) VALUES ('2024-02-01 05:00:00', 119, '1 Av (L)', 'Manhattan', 49, 40.730953, -73.981630, '2024-02-01_1 Av (L)');
INSERT INTO `subway_ridership` (`transit_timestamp`, `station_complex_id`, `station_complex`, `borough`, `ridership`, `latitude`, `longitude`, `stratum`) VALUES ('2024-02-01 04:00:00', 119, '1 Av (L)', 'Manhattan', 2, 40.730953, -73.981630, '2024-02-01_1 Av (L)');
INSERT INTO `subway_ridership` (`transit_timestamp`, `station_complex_id`, `station_complex`, `borough`, `ridership`, `latitude`, `longitude`, `stratum`) VALUES ('2024-02-01 04:00:00', 119, '1 Av (L)', 'Manhattan', 3, 40.730953, -73.981630, '2024-02-01_1 Av (L)');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
