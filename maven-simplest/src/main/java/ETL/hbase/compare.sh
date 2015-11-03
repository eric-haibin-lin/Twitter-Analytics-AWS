if [ "$#" != "3" ]; then
    echo "This script creates query 2 for our server and reference server"
    echo "Usage: ./compare.sh test_file localhost ec2-54-147-18-88.compute-1.amazonaws.com"
    echo "Example: ./load_q2.sh testcase1_2"
else
    rm err.log
    rm tmp1 2>> err.log
    rm tmp2 2>> err.log
    cat query_$1 | while read line
    do
        if [ "$line" != "" ]; then
            formattedline=${line// /+}
            curl "$2/q2?userid=${formattedline//_/&tweet_time=}" 2>> err.log | tail -n +2 >> tmp1
            curl "$3/q2?userid=${formattedline//_/&tweet_time=}" 2>> err.log | tail -n +2 >> tmp2
            diff tmp1 tmp2
        fi
    done
fi