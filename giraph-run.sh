$HADOOP_HOME/bin/hdfs dfs -rm -r output

$HADOOP_HOME/bin/yarn jar gol.jar pdd.simulation.SimulationJob 1 /user/ps/input/seed-graph.txt /user/ps/output
    # pdd.GameOfLifeComputation \
    # --yarnjars pdd.jar \
    # --workers 1 \
    # --vertexInputFormat pdd.VertexStateInputFormat \
    # --vertexInputPath /user/ps/input/cube-graph.txt \
    # --vertexOutputFormat org.apache.giraph.io.formats.IdWithValueTextOutputFormat \
    # --outputPath /user/ps/output \
    # -ca giraph.SplitMasterWorker=false,giraph.logLevel=error
    # --vertexInputFormat org.apache.giraph.io.formats.LongDoubleDoubleAdjacencyListVertexInputFormat \
    # --vertexInputFormat org.apache.giraph.io.formats.JsonLongDoubleFloatDoubleVertexInputFormat \
