package pdd.message;

import java.util.List;
import java.lang.StringBuilder;
import pdd.cell.CellLocation;
import pdd.message.MessageEncoder;
import pdd.message.Message;

public class VertexInitMessageEncoder implements MessageEncoder<List<CellLocation>> {
    
    public Message encode(List<CellLocation> neigbours) {
        StringBuilder payload = new StringBuilder("");
        String prefix = "";
        for (CellLocation loc : neigbours) {
            payload.append(prefix);
            prefix = ",";
            payload.append(loc.toString());
        }
        return new Message(Message.VERTEX_INIT, payload.toString());
    }

}