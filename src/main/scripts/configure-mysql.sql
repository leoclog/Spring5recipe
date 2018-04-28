#Use to run mysql db docker image 
#docker run --name mysql57 -e MYSQL_ROOT_PASSWORD=guru -d mysql:5.7
#docker exec -it mysql57 bash
##mysql -uroot -p mysql
#>alter user 'root'@'%' identified by 'guru'
#>quit
#
#docker inspect mysql57 | grep -i ipaddress
#
#mysql -uroot -p -h <ipaddress> -P 3306
#
#


#connect to mysql and run as root user
#Create Databases
CREATE DATABASE sfg_dev;
CREATE DATABASE sfg_prod;

#Create database service accounts
CREATE USER 'sfg_dev_user'@'%' IDENTIFIED BY 'guru';
CREATE USER 'sfg_prod_user'@'%' IDENTIFIED BY 'guru';

#Database Grants
GRANT SELECT ON sfg_dev.* TO 'sfg_dev_user'@'%';
GRANT INSERT ON sfg_dev.* TO 'sfg_dev_user'@'%';
GRANT DELETE ON sfg_dev.* TO 'sfg_dev_user'@'%';
GRANT UPDATE ON sfg_dev.* TO 'sfg_dev_user'@'%';
GRANT SELECT ON sfg_prod.* TO 'sfg_prod_user'@'%';
GRANT INSERT ON sfg_prod.* TO 'sfg_prod_user'@'%';
GRANT DELETE ON sfg_prod.* TO 'sfg_prod_user'@'%';
GRANT UPDATE ON sfg_prod.* TO 'sfg_prod_user'@'%';