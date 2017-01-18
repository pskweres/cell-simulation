#!/bin/bash

rm -rf build
mkdir build

find src/ -name "*.java" > sources.txt
javac -Xlint:unchecked -Xdiags:verbose -cp $GIRAPH_HOME/giraph-core/target/giraph-1.2.0-hadoop2-for-hadoop-2.5.1-jar-with-dependencies.jar:$($HADOOP_HOME/bin/hadoop classpath):guice-3.0/guice-3.0.jar:guice-3.0/guice-assistedinject-3.0.jar  -d build/ @sources.txt
rm sources.txt

cp giraph.jar ./gol.jar

jar -uvf gol.jar -C build/ .
