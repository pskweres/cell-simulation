package pdd.message;

import java.util.List;
import java.util.ArrayList;
import java.lang.StringBuilder;
import pdd.cell.CellLocation;
import pdd.message.MessageDecoder;
import pdd.message.Message;

public class VertexInitMessageDecoder implements MessageDecoder<List<CellLocation>> {
    
    public List<CellLocation> decode(Message msg) {
        List<CellLocation> neigbours = new ArrayList<CellLocation>();

        if (msg.getType() == Message.VERTEX_INIT) {
            String[] tokens = msg.getPayload().split(",");
            for (int i = 0; i < tokens.length; i += 3) {        
                int x = Integer.parseInt(tokens[i]);
                int y = Integer.parseInt(tokens[i + 1]);
                int z = Integer.parseInt(tokens[i + 2]);
                CellLocation loc = new CellLocation(x, y, z);
                neigbours.add(loc);
            }
            return neigbours;
        } else {
            return null;
        }
    }

}