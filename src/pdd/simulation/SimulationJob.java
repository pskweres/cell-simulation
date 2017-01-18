package pdd.simulation;

import org.apache.hadoop.conf.Configuration; 
import org.apache.hadoop.fs.Path; 
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat; 
import org.apache.hadoop.util.Tool; 
import org.apache.hadoop.util.ToolRunner; 
import org.apache.log4j.Logger;
import org.apache.giraph.job.GiraphJob;
import org.apache.giraph.conf.GiraphConfiguration;
import org.apache.giraph.io.formats.IdWithValueTextOutputFormat;
import org.apache.giraph.io.formats.GiraphFileInputFormat;
import org.apache.giraph.conf.BooleanConfOption;
import org.apache.giraph.io.formats.TextDoubleDoubleAdjacencyListVertexInputFormat;
import org.apache.giraph.io.formats.JsonLongDoubleFloatDoubleVertexInputFormat;
import pdd.simulation.SimulationComputation;
import pdd.simulation.TestComputation;
import pdd.cell.CellInputFormat;
import pdd.cell.CellLocation;
import pdd.population.CubePopulation;

public class SimulationJob implements Tool { 
    private Configuration conf; 
 
    @Override 
    public void setConf(Configuration conf) { 
        this.conf = conf; 
    } 
 
    @Override 
    public Configuration getConf() { 
        return conf; 
    } 
 
    @Override 
    public int run(String[] args) throws Exception { 
 
        int numberOfWorkers = Integer.parseInt(args[0]); 
        String inputLocation = args[1]; 
        String outputLocation = args[2]; 
 
        GiraphJob job = new GiraphJob(getConf(), getClass().getName()); 
        GiraphConfiguration gconf = job.getConfiguration(); 
        gconf.setWorkerConfiguration(numberOfWorkers, numberOfWorkers, 100.0f); 
 
        GiraphFileInputFormat.addVertexInputPath(gconf, new Path(inputLocation)); 
        FileOutputFormat.setOutputPath(job.getInternalJob(), new Path(outputLocation)); 
 

        // gconf.setComputationClass(DummyComputation.class); 
        // gconf.setVertexInputFormatClass(JsonLongDoubleFloatDoubleVertexInputFormat.class); 
 
        // gconf.setComputationClass(SimulationComputation.class); 
        gconf.setComputationClass(TestComputation.class);
        gconf.setVertexInputFormatClass(CellInputFormat.class); 
 
        gconf.setMasterComputeClass(SimulationMasterCompute.class);
        gconf.setVertexOutputFormatClass(IdWithValueTextOutputFormat.class);
        gconf.setBoolean("giraph.SplitMasterWorker", false);
 
        boolean verbose = false; 
        if (job.run(verbose)) { 
            return 0; 
        } else { 
            return -1; 
        } 
    } 
 
    public static void main(String[] args) throws Exception { 
        int ret = ToolRunner.run(new SimulationJob(), args); 
        if (ret == 0) { 
            System.out.println("Ended Good"); 
        } else { 
            System.out.println("Ended with Failure"); 
        } 
        System.exit(ret); 
    } 
}