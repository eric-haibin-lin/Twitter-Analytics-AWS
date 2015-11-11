if [ "$#" != "2" ]; then
    echo "This script creates a table in HBase using all files under target directory. At least 1000 testcases are required"
    echo "Usage: ./load_q2.sh table_name file_path"
    echo "Example: ./load_q2.sh testcase1_2_table ./testcase1_2"
else

    cd $2
    /home/hadoop/bin/hadoop fs -mkdir /$1
    /home/hadoop/bin/hadoop fs -put * /$1
    echo "=======================Creating table in HBase..==============================="
    echo "create '$1', {NAME => 'd', COMPRESSION => 'SNAPPY', BLOOMFILTER => 'ROW', BLOCKSIZE => '32768', IN_MEMORY => true }" | hbase shell
    echo "disable '$1'" | hbase shell
    echo "alter '$1', METHOD => 'table_att', MAX_FILESIZE => '33554432'" | hbase shell
    echo "enable '$1'" | hbase shell
    echo "=======================Converting tsv to Hfile..==============================="
    /home/hadoop/hbase/bin/hbase org.apache.hadoop.hbase.mapreduce.ImportTsv -Dimporttsv.bulk.output=output_$1 -Dimporttsv.columns=HBASE_ROW_KEY,d:r $1 /$1
    echo "=======================Loading Hfile to HBase...==============================="
    /home/hadoop/hbase/bin/hbase org.apache.hadoop.hbase.mapreduce.LoadIncrementalHFiles output_$1 $1
    echo "=======================Fetching top 50 records in HBase...==============================="
    echo "scan '$1', {'LIMIT' => 50} " | hbase shell
    echo "=======================Fetching some records as Testcase...==============================="
    #sudo echo "count '$1', {'INTERVAL'=> 100}" | hbase shell 2> err.log | head -n 1000 | tail -n 300 | head -n 250 | cut -d ' ' -f 5,6 > ~/query_$1
fi