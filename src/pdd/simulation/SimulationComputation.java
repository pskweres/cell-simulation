package pdd.simulation;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.giraph.edge.EdgeFactory;
import org.apache.giraph.graph.BasicComputation;
import org.apache.giraph.graph.Vertex;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.log4j.Logger;
import pdd.inject.AppInjector;
import pdd.cell.BasicCell;
import pdd.cell.CellFactory;
import pdd.cell.Cell;
import pdd.cell.CellLocation;
import pdd.message.BooleanMessageEncoder;
import pdd.message.Message;
import pdd.message.VertexInitMessageDecoder;
import pdd.message.VertexInitMessageEncoder;
import pdd.population.Population;

public class SimulationComputation<V extends BasicCell> extends BasicComputation<CellLocation, V, NullWritable, Message> {

    private static final Logger LOG = Logger.getLogger(SimulationComputation.class);

    private static Population population;
    private static CellFactory cellFactory;

    private static int MAX_STEPS = 10;

    @Inject
    public void setPopulation(Population population) {
        this.population = population;
    }

    @Inject
    public void setCellFactory(CellFactory factory) {
        this.cellFactory = factory;
    }

    @Override
    public void compute(Vertex<CellLocation, V, NullWritable> vertex, Iterable<Message> messages) throws IOException {

        if (getSuperstep() == MAX_STEPS) {
            vertex.voteToHalt();
            return;
        }
        
        if (getSuperstep() == 0) {
            // inject population class
            Injector injector = Guice.createInjector(new AppInjector());
            injector.injectMembers(this);

            // init vertices and send neighbours
            List<CellLocation> locations = population.getLocations();
            VertexInitMessageEncoder encoder = new VertexInitMessageEncoder();
            for (CellLocation l : locations) {
                sendMessage(l, encoder.encode(population.getNeighbours(l)));
            }

            // remove seed vertex
            removeVertexRequest(vertex.getId());

        } 
        
        if (getSuperstep() == 1) {
            
            // init veretex values
            Cell cell = cellFactory.create();
            cell.init();
            vertex.setValue((V) cell);

            // init edges to neighbours
            VertexInitMessageDecoder decoder = new VertexInitMessageDecoder();
            for (Message m : messages) {
                List<CellLocation> neighbours = decoder.decode(m);
                for (CellLocation n : neighbours) {
                    vertex.addEdge(EdgeFactory.create(n, NullWritable.get()));
                }
            }
        }

        if (getSuperstep() < 2)
            return;

        if ((getSuperstep() - 2) % 4 == 0) {

            // check if FGF19 is produced
            V cell = vertex.getValue();
            if (cell.isFGF19produced()) {
                // send message to neighbours about available FGF19
                BooleanMessageEncoder encoder = new BooleanMessageEncoder();
                Message msg = encoder.encode(cell.isFGF19produced());
                msg.setSender(vertex.getId());
                sendMessageToAllEdges(vertex, msg);
            }
        }

        if ((getSuperstep() - 2) % 4 == 1) {
            
            // neighbours who have FGF19
            List<CellLocation> neighboursWithFGF19 = new ArrayList<CellLocation>();
            for (Message m : messages) {
                CellLocation neighbour = m.getSender();
                neighboursWithFGF19.add(neighbour);
            }

            // choose one of neighbours with FGF19 and send request
            if (neighboursWithFGF19.size() > 0) {
                Random rand = new Random();
                int index = rand.nextInt(neighboursWithFGF19.size());
                CellLocation requestRecipient = neighboursWithFGF19.get(index);
                
                BooleanMessageEncoder encoder = new BooleanMessageEncoder();
                Message msg = encoder.encode(true);
                msg.setSender(vertex.getId());
                sendMessage(requestRecipient, msg);
                aggregate(SimulationMasterCompute.MESSAGE_COUNT, new LongWritable(1));
            }

        }

        if ((getSuperstep() - 2) % 4 == 2) {
            
            // neighbours who require FGF19
            List<CellLocation> neighboursRequiringFGF19 = new ArrayList<CellLocation>();
            for (Message m : messages) {
                CellLocation neighbour = m.getSender();
                neighboursRequiringFGF19.add(neighbour);
            }

            // choose one of neighbours who require FGF19 and confirm request for FGF19
            if (neighboursRequiringFGF19.size() > 0) {
                Random rand = new Random();
                int index = rand.nextInt(neighboursRequiringFGF19.size());
                CellLocation requestRecipient = neighboursRequiringFGF19.get(index);
                
                BooleanMessageEncoder encoder = new BooleanMessageEncoder();
                Message msg = encoder.encode(true);
                sendMessage(requestRecipient, msg);

                // FGF19 is used after confirming request to neighbour
                V cell = vertex.getValue();
                cell.removeFGF19();
                vertex.setValue(cell);
            }

        }

        if ((getSuperstep() - 2) % 4 == 3) {

            // should receive at most one message from cell providing FGF19
            CellLocation provider = null;
            for (Message m : messages) {
                provider = m.getSender();
            }

            // FGF19 is added if received message
            if (provider != null) {
                V cell = vertex.getValue();
                cell.addFGF19();
                vertex.setValue(cell);
            }

            // transition to next state if no FGF19 request message has been sent since production
            LongWritable msgCount = getAggregatedValue(SimulationMasterCompute.MESSAGE_COUNT);
            if (msgCount.get() == 0) {
                V cell = vertex.getValue();
                cell.nextState();
                vertex.setValue(cell);
            }

        }

        //should loop to FGF19 production superstep

    }

}