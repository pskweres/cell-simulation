package pdd.message;

import pdd.message.MessageDecoder;
import pdd.message.Message;

public class BooleanMessageDecoder implements MessageDecoder<Boolean> {
    
    public Boolean decode(Message msg) {
        if (msg.getType() == Message.BOOLEAN) {
            if ("1".equals(msg.getPayload())) {
                return true;
            }
            return false;
        } else {
            return null;
        }
    }

}