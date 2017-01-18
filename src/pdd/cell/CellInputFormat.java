package pdd.cell;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;
import org.apache.giraph.graph.Vertex;
import org.apache.giraph.edge.Edge;
import org.apache.giraph.edge.EdgeFactory;
import org.apache.giraph.io.VertexReader;
import org.apache.giraph.io.VertexInputFormat;
import org.apache.giraph.io.formats.TextVertexInputFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.io.IOException;

public class CellInputFormat extends TextVertexInputFormat<CellLocation, NullWritable, NullWritable> {

    private static final Pattern SEPARATOR = Pattern.compile("[\t ]");

    @Override
    public TextVertexReader createVertexReader(InputSplit split, TaskAttemptContext context) throws IOException {
        return new VertexStateVertexReader();
    }

    public class VertexStateVertexReader extends TextVertexReaderFromEachLineProcessed<String[]> {
        private CellLocation id;

        @Override
        protected String[] preprocessLine(Text line) throws IOException {
            String[] tokens = SEPARATOR.split(line.toString());
            int x = Integer.parseInt(tokens[0]);
            int y = Integer.parseInt(tokens[1]);
            int z = Integer.parseInt(tokens[2]);
            id = new CellLocation(x, y, z);
            return tokens;
        }

        @Override
        protected CellLocation getId(String[] tokens) throws IOException {
            return id;
        }

        @Override
        protected NullWritable getValue(String[] tokens) throws IOException {
            return null;
        }

        @Override
        protected Iterable<Edge<CellLocation, NullWritable>> getEdges(String[] tokens) throws IOException {
            List<Edge<CellLocation, NullWritable>> edges = new ArrayList<Edge<CellLocation, NullWritable>>();
            for (int n = 3; n < tokens.length; n += 3) {
                int x = Integer.parseInt(tokens[n]);
                int y = Integer.parseInt(tokens[n + 1]);
                int z = Integer.parseInt(tokens[n + 2]);
                CellLocation targetVertex = new CellLocation(x, y, z);
                edges.add(EdgeFactory.create(targetVertex, null));
            }
            return edges;
        }
    }
}