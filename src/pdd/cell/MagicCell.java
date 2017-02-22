package pdd.cell;

import java.util.*;
import java.io.*;
import java.util.Random;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import com.google.inject.assistedinject.*;
import pdd.cell.Cell;
import pdd.cell.CellLocation;
import pdd.util.Util;


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

    public String toString() {
        return Integer.toString(magicValue);
    }

    @Override
    public Text toText() {
        Serializable[] fields = new Serializable[3];
        fields[0] = magicValue;
        fields[1] = producedFGF19;
        fields[2] = receivedFGF19;
        Text text = new Text(Util.arrayToString(fields));
        return text;
    }
    
    @Override
    public void fromText(Text text) {
        Object[] fields = Util.arrayFromString(text.toString());
        magicValue = (int) fields[0];
        producedFGF19 = (boolean) fields[1];
        receivedFGF19 = (boolean) fields[2];   
    }

}