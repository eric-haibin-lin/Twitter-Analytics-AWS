if [ "$#" != "1" ]; then
    echo "This script backs up all the data in HBase to target s3 location now. Default backup time interval is 30 minutes "
    echo "Usage: ./backup.sh s3_location"
    echo "Example: ./backup.sh s3://haibinprivatebucket/ETL/query2/backups/full_1/"
else
    /home/hadoop/lib/hbase.jar emr.hbase.backup.Main --set-scheduled-backup true --backup-dir  $1 --incremental-backup-time-interval 30 --incremental-backup-time-unit minutes --start-time now
fi