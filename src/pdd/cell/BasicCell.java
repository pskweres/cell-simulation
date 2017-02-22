package pdd.cell;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.Text;

public abstract class BasicCell implements Cell {

    @Override
    public abstract boolean isFGF19produced();
    
    @Override
    public abstract boolean isFGF19required();
    
    @Override
    public abstract void init();
    
    @Override
    public abstract void nextState();

    @Override
    public abstract void addFGF19();
    
    @Override
    public abstract void removeFGF19();

    @Override
    public abstract Text toText();
    
    @Override
    public abstract void fromText(Text text);
    
}