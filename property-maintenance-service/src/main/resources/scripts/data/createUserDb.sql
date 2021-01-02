1. mysql -u root -p
2. create user ssp_db_admin@% identified by 'Welcome2@ssp';
   create user 'ssp_db_admin'@'%' identified by 'Welcome2@ssp';
3. GRANT ALL PRIVILEGES ON * . * TO ssp_db_admin@localhost;
   GRANT ALL PRIVILEGES ON * . * TO 'ssp_db_admin'@'%';

4. quit and login as new user `ssp_db_admin`
	mysql -u ssp_db_admin -p

5. CREATE DATABASE sspdb CHARACTER SET utf8 COLLATE utf8_general_ci;
6. USE sspdb
7. source /tmp/createDb.sql 