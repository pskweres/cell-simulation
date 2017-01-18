package pdd.message;

import pdd.message.Message;

public interface MessageDecoder<T> {
    
    public T decode(Message msg);
    
}