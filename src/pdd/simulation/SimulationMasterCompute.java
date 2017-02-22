package pdd.simulation;

import org.apache.hadoop.io.LongWritable;
import org.apache.log4j.Logger;
import org.apache.giraph.aggregators.LongSumAggregator;
import org.apache.giraph.master.DefaultMasterCompute;

public class SimulationMasterCompute extends DefaultMasterCompute {

    private static final Logger LOG = Logger.getLogger(SimulationMasterCompute.class);

    public static final String MESSAGE_COUNT = "MessageCountAggregator";

    @Override
    public void compute() {

        Long numOfRounds = getContext().getConfiguration().getLong("numOfRounds", 0);

        if (getSuperstep() - 2 >= numOfRounds) {
            haltComputation();
        }

        if ((getSuperstep() - 2) % 4 == 0) {
            setAggregatedValue(MESSAGE_COUNT, new LongWritable(0));
        }
        LOG.info("Total number of messages sent in " + getSuperstep() + " is " + getAggregatedValue(MESSAGE_COUNT));
    }
     
    @Override
    public void initialize() throws InstantiationException, IllegalAccessException {
        registerAggregator(MESSAGE_COUNT, LongSumAggregator.class);
    }
}