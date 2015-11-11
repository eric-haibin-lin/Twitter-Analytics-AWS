#!/bin/bash
if [ "$#" != "1" ]; then
    echo "Please specify file number!"
else
    echo "Downloading..."
    aws s3 cp --recursive s3://haibinprivatebucket/ETL/query4/outputs/full_hbase/ ./


    echo "loading data into MySQL..."
    load_times=$1

    mysql --local-infile -u root -pcoding15619 -e "SET foreign_key_checks = 0;SET unique_checks = 0;SET bulk_insert_buffer_size = 1024*1024*1024;"
    echo "parameters for MySQL set"
    mysql --local-infile -u root -pcoding15619 -e "DROP DATABASE tweet;"
    echo "Drop tweet"
    mysql --local-infile -u root -pcoding15619 -e "CREATE DATABASE IF NOT EXISTS tweet;"
    echo "DATABASE tweet created"

    mysql --local-infile -u root -pcoding15619 tweet -e "CREATE TABLE IF NOT EXISTS q4 ( id INT NOT NULL AUTO_INCREMENT, q BLOB(128) NOT NULL, r MEDIUMBLOB DEFAULT NULL, PRIMARY KEY (id) ) ENGINE = MYISAM;"
    echo "TABLE q4 created"

    mysql --local-infile -u root -pcoding15619 tweet -e ""

    prefix="part-000"
    for (( load_idx=0; $load_idx<=$load_times; load_idx=$load_idx+1 ))
    do
	if [ $load_idx -lt 10 ]; then
	    filename=$prefix"0"$load_idx
	else
	    filename=$prefix$load_idx
	fi
	echo "Loading $filename..."
	mysql --local-infile -u root -pcoding15619 tweet -e "LOAD DATA LOCAL INFILE '~/$filename' REPLACE INTO TABLE q4 FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' (q, r);"
    done

    echo "Creating Index on q for TABLE q4..."
    mysql --local-infile -u root -pcoding15619 tweet -e "CREATE INDEX q_index ON q4 (q(128));"

fi
