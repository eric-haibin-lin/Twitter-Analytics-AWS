if [ "$#" != "2" ]; then
    echo "This script downloads an S3 folder to ELB. You should execute this script after attaching a ELB to this instance. The the ELB folder is created under /storage/ebs_folder_name"
    echo "Usage: ./download_to_elb.sh s3_folder_path ebs_folder_name"
    echo "Example: ./load_q2.sh s3://haibinprivatebucket/ETL/query2/outputs/full_1 q2"
else
    echo "=======================Mounting the EBS on /storage/$2...==============================="
    sudo parted /dev/xvdf mklabel gpt
    sudo mkdir /storage
    sudo mkdir /storage/$2
    sudo mount /dev/xvdf /storage/$2
    cd /storage/$2
    echo "=======================Downloading file from s3...==============================="
    sudo aws s3 cp --recursive $1 .
fi