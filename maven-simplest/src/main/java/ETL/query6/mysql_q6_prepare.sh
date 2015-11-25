mkdir ~/q6
aws s3 cp --recursive s3://haibinprivatebucket/ETL/query6/outputs/full_3_2_2 ~/q6

mysql --local-infile -u root -pcoding15619 -e "SET foreign_key_checks = 0;SET unique_checks = 0;SET bulk_insert_buffer_size = 1024*1024*1024;"
echo "parameters for MySQL set"

mysql --local-infile -u root -pcoding15619 -e "CREATE DATABASE IF NOT EXISTS tweet;"
echo "DATABASE tweet created"

mysql --local-infile -u root -pcoding15619 -e tweet "DROP TABLE q6;"  
mysql --local-infile -u root -pcoding15619 tweet -e "CREATE TABLE IF NOT EXISTS q6 ( id INT NOT NULL AUTO_INCREMENT, q varchar(32) NOT NULL, r blob DEFAULT NULL, PRIMARY KEY (id) ) ENGINE = MYISAM;"
echo "TABLE q6 created"

mysql --local-infile -u root -pcoding15619 tweet -e ""

mysql --local-infile -u root -pcoding15619 tweet -e "LOAD DATA LOCAL INFILE '~/q6/part-00000' REPLACE INTO TABLE q6 FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' (q, r);"

mysql --local-infile -u root -pcoding15619 tweet -e "LOAD DATA LOCAL INFILE '~/q6/part-00001' REPLACE INTO TABLE q6 FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' (q, r);"

echo "Creating Index on q for TABLE q6..."
mysql --local-infile -u root -pcoding15619 tweet -e "CREATE INDEX q6_index ON q6 (q);"
