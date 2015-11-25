mkdir ~/q6_data
aws s3 cp --recursive s3://haibinprivatebucket/ETL/query6/outputs/full_2 ~/q6_data

mysql --local-infile -u root -pcoding15619 -e "SET foreign_key_checks = 0;SET unique_checks = 0;SET bulk_insert_buffer_size = 1024*1024*1024;"
echo "parameters for MySQL set"

mysql --local-infile -u root -pcoding15619 -e "CREATE DATABASE IF NOT EXISTS tweet;"
echo "DATABASE tweet created"

mysql --local-infile -u root -pcoding15619 -e tweet "DROP TABLE q6;"  
mysql --local-infile -u root -pcoding15619 tweet -e "CREATE TABLE IF NOT EXISTS q6 ( id INT NOT NULL AUTO_INCREMENT, q varchar(32) NOT NULL, r blob DEFAULT NULL, PRIMARY KEY (id) ) ENGINE = MYISAM;"

echo "TABLE q6 created"

mysql --local-infile -u root -pcoding15619 tweet -e ""

echo "Loading data for TABLE q6"
mysql --local-infile -u root -pcoding15619 tweet -e "LOAD DATA LOCAL INFILE '~/q6_data/part-00000' REPLACE INTO TABLE q6 FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' (q, r);"
mysql --local-infile -u root -pcoding15619 tweet -e "LOAD DATA LOCAL INFILE '~/q6_data/part-00001' REPLACE INTO TABLE q6 FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' (q, r);"
mysql --local-infile -u root -pcoding15619 tweet -e "LOAD DATA LOCAL INFILE '~/q6_data/part-00002' REPLACE INTO TABLE q6 FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' (q, r);"

echo "Loading data for TABLE q6 20% completed"
mysql --local-infile -u root -pcoding15619 tweet -e "LOAD DATA LOCAL INFILE '~/q6_data/part-00003' REPLACE INTO TABLE q6 FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' (q, r);"
mysql --local-infile -u root -pcoding15619 tweet -e "LOAD DATA LOCAL INFILE '~/q6_data/part-00004' REPLACE INTO TABLE q6 FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' (q, r);"
mysql --local-infile -u root -pcoding15619 tweet -e "LOAD DATA LOCAL INFILE '~/q6_data/part-00005' REPLACE INTO TABLE q6 FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' (q, r);"

echo "Loading data for TABLE q6 40% completed"
mysql --local-infile -u root -pcoding15619 tweet -e "LOAD DATA LOCAL INFILE '~/q6_data/part-00006' REPLACE INTO TABLE q6 FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' (q, r);"
mysql --local-infile -u root -pcoding15619 tweet -e "LOAD DATA LOCAL INFILE '~/q6_data/part-00007' REPLACE INTO TABLE q6 FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' (q, r);"
mysql --local-infile -u root -pcoding15619 tweet -e "LOAD DATA LOCAL INFILE '~/q6_data/part-00008' REPLACE INTO TABLE q6 FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' (q, r);"

echo "Loading data for TABLE q6 60% completed"
mysql --local-infile -u root -pcoding15619 tweet -e "LOAD DATA LOCAL INFILE '~/q6_data/part-00009' REPLACE INTO TABLE q6 FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' (q, r);"
mysql --local-infile -u root -pcoding15619 tweet -e "LOAD DATA LOCAL INFILE '~/q6_data/part-00010' REPLACE INTO TABLE q6 FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' (q, r);"
mysql --local-infile -u root -pcoding15619 tweet -e "LOAD DATA LOCAL INFILE '~/q6_data/part-00011' REPLACE INTO TABLE q6 FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' (q, r);"
mysql --local-infile -u root -pcoding15619 tweet -e "LOAD DATA LOCAL INFILE '~/q6_data/part-00012' REPLACE INTO TABLE q6 FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' (q, r);"
mysql --local-infile -u root -pcoding15619 tweet -e "LOAD DATA LOCAL INFILE '~/q6_data/part-00013' REPLACE INTO TABLE q6 FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' (q, r);"
echo "Loading data for TABLE q6 100% completed"

echo "Creating Index on q for TABLE q6..."
mysql --local-infile -u root -pcoding15619 tweet -e "CREATE INDEX q6_index ON q6 (q);"
echo "Index on q for TABLE q6 created"

