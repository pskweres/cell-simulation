package pdd.message;

import pdd.message.Message;

public interface MessageEncoder<T> {
    
    public Message encode(T data);

}