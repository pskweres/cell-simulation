package pdd.cell;

import org.apache.hadoop.io.Writable;

public interface Cell extends Writable {
    
    public boolean isFGF19produced();
    public boolean isFGF19required();
    public void removeFGF19();
    public void addFGF19();

    public void init();
    public void nextState();

}