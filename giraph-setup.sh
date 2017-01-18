$HADOOP_HOME/bin/hdfs namenode -format

$HADOOP_HOME/sbin/start-dfs.sh

$HADOOP_HOME/bin/hdfs dfs -mkdir /user
$HADOOP_HOME/bin/hdfs dfs -mkdir /user/ps

python3.5 gen-graph.py 10 > input/cube-graph.txt
$HADOOP_HOME/bin/hdfs dfs -put input input

$HADOOP_HOME/sbin/start-yarn.sh
