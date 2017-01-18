package pdd.cell;

import pdd.cell.Cell;
import pdd.cell.CellLocation;

public interface CellFactory {
    public Cell create(String payload);
    public Cell create();
}