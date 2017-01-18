package pdd.cell;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Random;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.DoubleWritable;
import com.google.inject.assistedinject.*;
import pdd.cell.Cell;
import pdd.cell.CellLocation;


public class MagicCell extends BasicCell {
    
    static final int MAX_VALUE = 100;
    int magicValue;
    boolean producedFGF19;
    boolean receivedFGF19;

    public boolean isFGF19produced() {
        return producedFGF19;
    }

    public boolean isFGF19required() {
        return (magicValue % 7 == 0 ? true : false);
    }

    public void addFGF19() {
        receivedFGF19 = true;
    }

    public void removeFGF19() {
        producedFGF19 = false;
    }

    public void init() {
        Random generator = new Random();
        magicValue = generator.nextInt(MAX_VALUE);
        producedFGF19 = (magicValue % 25 == 0 ? true : false);
        receivedFGF19 = false;
    }

    public void nextState() {
        if (receivedFGF19)
            magicValue += 55;
        else
            magicValue += 1;
        magicValue %= MAX_VALUE;
        producedFGF19 = (magicValue % 25 == 0 ? true : false);
        receivedFGF19 = false;
    }

    @AssistedInject
    public MagicCell() {
        this.magicValue = 0;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(magicValue);
        out.writeBoolean(producedFGF19);
        out.writeBoolean(receivedFGF19);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        magicValue = in.readInt();
        producedFGF19 = in.readBoolean();
        receivedFGF19 = in.readBoolean();
    }

    public String toString() {
        return Integer.toString(magicValue);
    }

}