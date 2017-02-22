package pdd.cell;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.Text;

public interface Cell {
    
    public boolean isFGF19produced();
    public boolean isFGF19required();
    public void removeFGF19();
    public void addFGF19();

    public void init();
    public void nextState();

    public Text toText();
    public void fromText(Text text);

}