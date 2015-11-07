#!/bin/bash
JARNAME="MapRed.jar"
if [ "$#" == "1" ]; then
    JARNAME=$1
fi
javac -cp ../lib/json-20140107.jar:.  *.java
jar cvf $JARNAME *.class
