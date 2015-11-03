if [ "$#" != "1" ]; then
    echo "This script creates a table in HBase. At least 1000 testcases are required"
    echo "Usage: ./load_q2.sh test#"
    echo "Example: ./load_q2.sh testcase1_2"
else
    mkdir $1
    cd $1
    aws s3 cp --recursive s3://haibinprivatebucket/ETL/query2/outputs/$1/ .
    hadoop fs -mkdir /$1
    hadoop fs -put * /$1
    echo "=======================Creating table in HBase..==============================="
    echo "create '$1', {NAME => 'd'}" | hbase shell
    echo "=======================Converting tsv to Hfile..==============================="
    hbase org.apache.hadoop.hbase.mapreduce.ImportTsv -Dimporttsv.bulk.output=output_$1 -Dimporttsv.columns=HBASE_ROW_KEY,d:r $1 /$1
    echo "=======================Loading Hfile to HBase...==============================="
    hbase org.apache.hadoop.hbase.mapreduce.LoadIncrementalHFiles output_$1 $1
    echo "=======================Fetching top 50 records in HBase...==============================="
    echo "scan '$1', {'LIMIT' => 50} " | hbase shell
    echo "=======================Fetching some records as Testcase...==============================="
    echo "count '$1', {'INTERVAL'=> 100}" | hbase shell 2> err.log | head -n 1000 | tail -n 300 | head -n 250 | cut -d ' ' -f 5,6 > query_$1
fi