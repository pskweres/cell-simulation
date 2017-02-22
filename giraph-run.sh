$HADOOP_HOME/bin/hdfs dfs -rm -r output

HADOOP_CLASSPATH=$GIRAPH_HOME/giraph-core/target/giraph-1.2.0-hadoop2-for-hadoop-2.5.1-jar-with-dependencies.jar:$($HADOOP_HOME/bin/hadoop classpath):guice-3.0/guice-3.0.jar:guice-3.0/guice-assistedinject-3.0.jar:petrinet_2.11-1.0.jar:scala-library-2.11.6.jar:scala-xml_2.11.jar:simulation.jar
export HADOOP_CLASSPATH=$HADOOP_CLASSPATH

$HADOOP_HOME/bin/yarn jar simulation.jar pdd.simulation.SimulationJob 1 /user/ps/input/seed-graph.txt /user/ps/output 10 3 pdd.population.CubePopulation pdd.cell.MagicCell
    # --yarnjars giraph.jar 
    # pdd.GameOfLifeComputation \
    # --workers 1 \
    # --vertexInputFormat pdd.VertexStateInputFormat \
    # --vertexInputPath /user/ps/input/cube-graph.txt \
    # --vertexOutputFormat org.apache.giraph.io.formats.IdWithValueTextOutputFormat \
    # --outputPath /user/ps/output \
    # -ca giraph.SplitMasterWorker=false,giraph.logLevel=error
    # --vertexInputFormat org.apache.giraph.io.formats.LongDoubleDoubleAdjacencyListVertexInputFormat \
    # --vertexInputFormat org.apache.giraph.io.formats.JsonLongDoubleFloatDoubleVertexInputFormat \
