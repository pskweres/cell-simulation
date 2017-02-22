$HADOOP_HOME/bin/hdfs namenode -format

$HADOOP_HOME/sbin/start-dfs.sh

$HADOOP_HOME/bin/hdfs dfs -mkdir /user
$HADOOP_HOME/bin/hdfs dfs -mkdir /user/ps

$HADOOP_HOME/bin/hdfs dfs -put input input
$HADOOP_HOME/bin/hdfs dfs -put net.spept net.spept

$HADOOP_HOME/sbin/start-yarn.sh
