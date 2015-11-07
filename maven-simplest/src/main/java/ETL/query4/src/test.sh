if [ "$#" != "3" ]; then
    echo "Usage: ./test.sh [Filter|MySQL|HBase] [filter|mapred|map|red] test#"
    echo "Example: ./test.sh MySQL mapred 1"
else
    if [ "$2" == "mapred" ]; then
        cat ../../test_cases/test_case_$3/input | java -classpath MapRed.jar:../lib/json-20140107.jar $1Mapper | sort | java -classpath MapRed.jar:../lib/json-20140107.jar $1Reducer
    elif [ "$2" == "map" ]; then
        cat ../../test_cases/test_case_$3/input | java -classpath MapRed.jar:../lib/json-20140107.jar $1Mapper
    elif [ "$2" == "filter" ]; then
        cat ../../test_cases/test_case_$3/input | java -classpath Filter.jar:../lib/json-20140107.jar $1Mapper | sort | java -classpath Filter.jar:../lib/json-20140107.jar $1Reducer
    fi
fi
