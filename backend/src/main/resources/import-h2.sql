-- H2 Database Sequence Reset Script
-- This script resets sequences for H2 database after import.sql runs
-- H2 doesn't support subquery in ALTER TABLE, so we need to calculate values first

-- 🔑 Reset Profile sequence
ALTER TABLE profile ALTER COLUMN id RESTART WITH 31;

-- 🔑 Reset Refresh Token sequence  
ALTER TABLE refresh_token ALTER COLUMN id RESTART WITH 1;

-- 🔑 Reset Password Reset Token sequence
ALTER TABLE password_reset_token ALTER COLUMN id RESTART WITH 1;

-- 🔑 Reset Role sequence
ALTER TABLE role ALTER COLUMN id RESTART WITH 4;

-- 🔑 Reset Permission sequence
ALTER TABLE permission ALTER COLUMN id RESTART WITH 1;

-- 🔑 Reset Role Permission sequence
ALTER TABLE role_permission ALTER COLUMN id RESTART WITH 1;

-- 🔑 Reset Sharing List sequence
ALTER TABLE sharing_list ALTER COLUMN id RESTART WITH 1;

-- 🔑 Reset Sharing List Collaborator sequence
ALTER TABLE sharing_list_collaborator ALTER COLUMN id RESTART WITH 1;

-- 🔑 Reset Outlet Type sequence
ALTER TABLE outlet_type ALTER COLUMN id RESTART WITH 1;

-- 🔑 Reset Outlet Category sequence
ALTER TABLE outlet_category ALTER COLUMN id RESTART WITH 1;

-- 🔑 Reset Outlet Feature sequence
ALTER TABLE outlet_feature ALTER COLUMN id RESTART WITH 7;

-- 🔑 Reset Outlet Feature Mapping sequence
ALTER TABLE outlet_feature_mapping ALTER COLUMN id RESTART WITH 1;

-- 🔑 Reset Outlet Image sequence
ALTER TABLE outlet_image ALTER COLUMN id RESTART WITH 1;

-- 🔑 Reset Operating Hours sequence
ALTER TABLE operating_hours ALTER COLUMN id RESTART WITH 1;

-- 🔑 Reset Menu Item Type sequence
ALTER TABLE menu_item_type ALTER COLUMN id RESTART WITH 1;

-- 🔑 Reset Menu Item Category sequence
ALTER TABLE menu_item_category ALTER COLUMN id RESTART WITH 1;

-- 🔑 Reset Menu Item Sub Category sequence
ALTER TABLE menu_item_sub_category ALTER COLUMN id RESTART WITH 1;

-- 🔑 Reset Outlet Menu Item sequence
ALTER TABLE outlet_menu_item ALTER COLUMN id RESTART WITH 1;

-- 🔑 Reset Menu Item Feature sequence
ALTER TABLE menu_item_feature ALTER COLUMN id RESTART WITH 1;

-- 🔑 Reset Outlet Menu Item Feature sequence
ALTER TABLE outlet_menu_item_feature ALTER COLUMN id RESTART WITH 1;

-- 🔑 Reset Review Image sequence
ALTER TABLE review_image ALTER COLUMN id RESTART WITH 1;

-- 🔑 Reset Review Reply sequence
ALTER TABLE review_reply ALTER COLUMN id RESTART WITH 1;

-- 🔑 Reset Review Reaction sequence
ALTER TABLE review_reaction ALTER COLUMN id RESTART WITH 1;

-- 🔑 Reset Review Report sequence
ALTER TABLE review_report ALTER COLUMN id RESTART WITH 1;

-- 🔑 Reset Notification sequence
ALTER TABLE notification ALTER COLUMN id RESTART WITH 1;

-- 🔑 Reset Membership Plan sequence
ALTER TABLE membership_plan ALTER COLUMN id RESTART WITH 1;

-- 🔑 Reset User Membership sequence
ALTER TABLE user_membership ALTER COLUMN id RESTART WITH 1;

-- 🔑 Reset Advertisement sequence
ALTER TABLE advertisement ALTER COLUMN id RESTART WITH 1;

-- 🔑 Reset Country sequence (75 countries imported)
ALTER TABLE country ALTER COLUMN id RESTART WITH 76;

-- 🔑 Reset Province sequence
ALTER TABLE province ALTER COLUMN id RESTART WITH 6;

-- 🔑 Reset District sequence
ALTER TABLE district ALTER COLUMN id RESTART WITH 1;

