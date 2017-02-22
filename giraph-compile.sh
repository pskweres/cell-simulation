#!/bin/bash

rm -rf build
mkdir build

find src/ -name "*.java" > sources.txt
CLASSPATH=$GIRAPH_HOME/giraph-core/target/giraph-1.2.0-hadoop2-for-hadoop-2.5.1-jar-with-dependencies.jar:$($HADOOP_HOME/bin/hadoop classpath):guice-3.0/guice-3.0.jar:guice-3.0/guice-assistedinject-3.0.jar:petrinet_2.11-1.0.jar:scala-library-2.11.6.jar:scala-xml_2.11.jar
javac -Xlint:unchecked -Xdiags:verbose -cp $CLASSPATH -d build/ @sources.txt
rm sources.txt

jar -cvf simulation.jar -C build/ .
