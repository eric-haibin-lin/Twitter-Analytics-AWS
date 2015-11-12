if [ "$#" != "2" ]; then
    echo "This script downloads an S3 folder to EBS. You should execute this script after attaching a ELB to this instance. The the EBS folder is created under /storage/ebs_folder_name"
    echo "Usage: ./download_to_ebs.sh s3_folder_path ebs_folder_name"
    echo "Example: ./download_to_ebs.sh s3://haibinprivatebucket/ETL/query2/outputs/full_2 q2"
else
    #uncomment this line to install file system on a new device
    echo "=======================Mounting the EBS on /storage/$2...==============================="
    #sudo mkfs.ext4 /dev/xvdf
    #sudo parted /dev/xvdf mklabel gpt
    sudo mkdir /storage
    sudo mkdir /storage/$2
    sudo mkdir /storage/$2/data
    sudo mount /dev/xvdf /storage/$2
    cd /storage/$2/data
    echo "=======================Downloading file from s3...==============================="
    sudo aws s3 cp --recursive $1 .
fi