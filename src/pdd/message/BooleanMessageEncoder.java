package pdd.message;

import java.util.List;
import java.lang.StringBuilder;
import pdd.cell.CellLocation;
import pdd.message.MessageEncoder;
import pdd.message.Message;

public class BooleanMessageEncoder implements MessageEncoder<Boolean> {
    
    public Message encode(Boolean bit) {
        if (bit) {
            return new Message(Message.BOOLEAN, "1");
        }
        return new Message(Message.BOOLEAN, "0");
    }

}