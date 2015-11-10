if [ "$#" != "2" ]; then
    echo "This script creates a table in HBase using all files under target directory. At least 1000 testcases are required"
    echo "Usage: ./load_q4.sh table_name file_path"
    echo "Example: ./load_q4.sh testcase1_2_table ./testcase1_2"
else
    sudo cd $2
    /home/hadoop/bin/hadoop fs -mkdir /$1
    /home/hadoop/bin/hadoop fs -put * /$1
    echo "=======================Creating table in HBase..==============================="
    echo "create '$1', {NAME => 'd'}" | hbase shell
    echo "=======================Converting tsv to Hfile..==============================="
    /home/hadoop/hbase/bin/hbase org.apache.hadoop.hbase.mapreduce.ImportTsv -Dimporttsv.bulk.output=output_$1 -Dimporttsv.columns=HBASE_ROW_KEY,d:v $1 /$1
    echo "=======================Loading Hfile to HBase...==============================="
    /home/hadoop/hbase/bin/hbase org.apache.hadoop.hbase.mapreduce.LoadIncrementalHFiles output_$1 $1
    echo "=======================Fetching top 50 records in HBase...==============================="
    echo "scan '$1', {'LIMIT' => 50} " | hbase shell
    echo "=======================Fetching some records as Testcase...==============================="
    #sudo echo "count '$1', {'INTERVAL'=> 100}" | hbase shell 2> err.log | head -n 1000 | tail -n 300 | head -n 250 | cut -d ' ' -f 5,6 > ~/query_$1
fi