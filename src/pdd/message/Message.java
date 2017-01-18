package pdd.message;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.Text;

import pdd.cell.CellLocation;

public class Message implements Writable {
    
    static final int VERTEX_INIT = 1;
    static final int BOOLEAN = 2;

    int type;
    String payload;
    CellLocation sender;

    public Message() {
        this.type = 0;
        this.payload = "";
        this.sender = new CellLocation();
    }


    public Message(int type, String payload) {
        this.type = type;
        this.payload = payload;
        this.sender = new CellLocation();
    }

    public Message(int type, String payload, CellLocation sender) {
        this.type = type;
        this.payload = payload;
        this.sender = sender;
    }

    public int getType() {
        return type;
    }

    public String getPayload() {
        return payload;
    }

    public CellLocation getSender() {
        return sender;
    }

    public void setSender(CellLocation sender) {
        this.sender = sender;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        sender.write(out);
        out.writeInt(type);
        out.writeBytes(payload + '\n');
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        sender = CellLocation.read(in);
        type = in.readInt();
        payload = in.readLine();
    }

}