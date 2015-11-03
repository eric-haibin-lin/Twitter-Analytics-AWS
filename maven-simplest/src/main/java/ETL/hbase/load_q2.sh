if [ "$#" != "1" ]; then
    echo "This script creates a table in HBase"
    echo "Usage: ./load_q2.sh test#"
    echo "Example: ./load_q2.sh testcase1_2"
    echo "TODO: modify s3 script. Don't use it now"
else
    mkdir $1
    cd $1
    aws s3 cp s3://haibinprivatebucket/ETL/query2/outputs/$1/ .
    hadoop fs -mkdir /$1
    hadoop fs -put * /$1
    echo "create '$1', {NAME => 'data'}" | hbase shell
    hbase org.apache.hadoop.hbase.mapreduce.ImportTsv -Dimporttsv.bulk.output=output_$1 -Dimporttsv.columns=HBASE_ROW_KEY,data:result $1 /$1
    hbase org.apache.hadoop.hbase.mapreduce.LoadIncrementalHFiles output_$1 $1
fi