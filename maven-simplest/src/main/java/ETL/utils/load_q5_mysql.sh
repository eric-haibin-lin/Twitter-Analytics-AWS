#!/bin/bash
if [ "$#" != "1" ]; then
    echo "Please specify file number!"
else
    echo "Downloading..."
    aws s3 cp --recursive s3://haibinprivatebucket/ETL/query5/outputs/full_mysql/ ./q5_data/

    echo "loading data into MySQL..."
    load_times=$1

    mysql --local-infile -u root -pcoding15619 -e "SET foreign_key_checks = 0;SET unique_checks = 0;SET bulk_insert_buffer_size = 1024*1024*1024;"
    echo "parameters for MySQL set"

    mysql --local-infile -u root -pcoding15619 -e "CREATE DATABASE IF NOT EXISTS tweet;"
    echo "DATABASE tweet created"

    mysql --local-infile -u root -pcoding15619 tweet -e "CREATE TABLE IF NOT EXISTS q5 (uid bigint NOT NULL, count bigint NOT NULL,PRIMARY KEY (uid)) ENGINE = MYISAM;"
    echo "TABLE q5 created"

    prefix="part-000"
    for (( load_idx=0; $load_idx<=$load_times; load_idx=$load_idx+1 ))
    do
	if [ $load_idx -lt 10 ]; then
	    filename=$prefix"0"$load_idx
	else
	    filename=$prefix$load_idx
	fi
	echo "Loading $filename..."
	mysql --local-infile -u root -pcoding15619 tweet -e "LOAD DATA LOCAL INFILE './q5_data/$filename' REPLACE INTO TABLE q5 FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' (uid, count);"
    
    done
fi