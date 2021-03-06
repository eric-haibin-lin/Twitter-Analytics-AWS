if [ "$#" != "3" ]; then
    echo "Usage: ./mapred.sh [MySQL|HBase] [mapred|map|red] test#"
    echo "Example: ./mapred.sh MySQL mapred 1"
else
    if [ "$2" == "mapred" ]; then
        cat ../../test_cases/test_case_$3/input | java -classpath MapRed.jar:../json-20140107.jar $1MapperQ3 | sort | java -classpath MapRed.jar:../json-20140107.jar $1ReducerQ3
    elif [ "$2" == "map" ]; then
        cat ../../test_cases/test_case_$3/input | java -classpath MapRed.jar:../json-20140107.jar $1MapperQ3
    fi
fi
