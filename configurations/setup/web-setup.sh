echo "=================Creating directory for web================="
mkdir web
cd web
echo "=================Installing java 8=========================="
wget https://s3.amazonaws.com/haibinprivatebucket/Web/setup/jdk-8u60-linux-x64.rpm
sudo rpm -i jdk-8u60-linux-x64.rpm
echo "=================Installing maven=========================="
wget https://s3.amazonaws.com/haibinprivatebucket/Web/setup/apache-maven-3.3.3-bin.tar.gz
tar -xzf apache-maven-3.3.3-bin.tar.gz
export PATH=/home/hadoop/web/apache-maven-3.3.3/bin:$PATH
echo "=================Installing git============================"
sudo yum install git
y
echo "=================Cloning code============================"
git clone https://coding_squirrels@bitbucket.org/codingsquirrels/phase1.git


