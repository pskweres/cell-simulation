package pdd.cell;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class BasicCell implements Cell {
    
    @Override
    public abstract void write(DataOutput out) throws IOException;

    @Override
    public abstract void readFields(DataInput in) throws IOException;

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
    
}