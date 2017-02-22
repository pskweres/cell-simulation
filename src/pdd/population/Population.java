package pdd.population;

import pdd.cell.CellLocation;
import java.util.List;

public interface Population {

    public List<CellLocation> getNeighbours(CellLocation c);
    public List<CellLocation> getLocations();
    public void setSize(long n);

}