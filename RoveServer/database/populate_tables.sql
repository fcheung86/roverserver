
INSERT INTO `rove`.`users` (`user_id`, `username`, `real_name`, `email`, `password`, `salt`, `created_on`, `updated_on`) 
VALUES ('1', 'iceheat710', 'Justin To', 'iceheat710@gmail.com', 'c6fb68ea5bf84899ef7e02032f03e5ede65a40af9885251c017347786e8b4c4b', '1565449986', now(), now());

INSERT INTO `rove`.`users` (`user_id`, `username`, `real_name`, `email`, `password`, `salt`, `created_on`, `updated_on`) 
VALUES ('2', 'dkShoguN', 'Farran Cheung', 'fcheung86@gmail.com', 'c6fb68ea5bf84899ef7e02032f03e5ede65a40af9885251c017347786e8b4c4b', '1565449986', now(), now());

INSERT INTO `rove`.`users` (`user_id`, `username`, `real_name`, `email`, `password`, `salt`, `created_on`, `updated_on`) 
VALUES ('3', 'pthiezo', 'Phong Thieu', 'pthieu@gmail.com', 'c6fb68ea5bf84899ef7e02032f03e5ede65a40af9885251c017347786e8b4c4b', '1565449986', now(), now());

INSERT INTO `rove`.`users` (`user_id`, `username`, `real_name`, `email`, `password`, `salt`, `created_on`, `updated_on`)
VALUES ('4', 'carrothell', 'Mark Ho', 'mark.sk.ho@gmail.com', 'c6fb68ea5bf84899ef7e02032f03e5ede65a40af9885251c017347786e8b4c4b', '1565449986', now(), now());

INSERT INTO `rove`.`posts` (`post_id`, `user_id`, `latitude`, `longitude`, `message`, `address`, `city`) 
VALUES ('1', '1', '43.61', '-79.65', 'Justin was here!', 'Hwy 10/Eglinton', 'Mississauga');

INSERT INTO `rove`.`posts` (`post_id`, `user_id`, `latitude`, `longitude`, `message`, `address`, `city`) 
VALUES ('2', '2', '43.82', '-79.31', 'Farran was here!', 'Kennedy/Steeles', 'Markham');

INSERT INTO `rove`.`posts` (`post_id`, `user_id`, `latitude`, `longitude`, `message`, `address`, `city`) 
VALUES ('3', '3', '43.78', '-79.29', 'Phong was here!', 'Kennedy/Sheppard', 'Scarborough');

INSERT INTO `rove`.`posts` (`post_id`, `user_id`, `latitude`, `longitude`, `message`, `address`, `city`) 
VALUES ('4', '4', '43.86', '-79.39', 'Mark was here!', 'Leslie/16th', 'Richmond Hill');

