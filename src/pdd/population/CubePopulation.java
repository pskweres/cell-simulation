package pdd.population;

import pdd.population.Population;
import pdd.cell.CellLocation;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class CubePopulation implements Population {

    int cubeSize = 0;

    public CubePopulation() {
        cubeSize = 10;
    }

    public CubePopulation(int cubeSize) {
        this.cubeSize = cubeSize;
    }

    @Override
    public List<CellLocation> getNeighbours(CellLocation c) {
        List<CellLocation> neighbours = new ArrayList<CellLocation>();
        for (int i = c.getX() - 1; i <= c.getX() + 1; ++i) {
            for (int j = c.getY() - 1; j <= c.getY() + 1; ++j) {
                for (int k = c.getZ() - 1; k <= c.getZ() + 1; ++k) {
                    if (i >= 0 && i < cubeSize && j >= 0 && j < cubeSize && k >= 0 && k < cubeSize) {
                        if (!(i == c.getX() && j == c.getY() && k == c.getZ())) {
                            neighbours.add(new CellLocation(i, j, k));
                        }                        
                    }
                }
            }
        }
        return neighbours;
    }

    @Override
    public List<CellLocation> getLocations() {
        List<CellLocation> locations = new ArrayList<CellLocation>();
        for (int i = 0; i < cubeSize; ++i) {
            for (int j = 0; j < cubeSize; ++j) {
                for (int k = 0; k < cubeSize; ++k) {
                    CellLocation loc = new CellLocation(i, j, k);
                    locations.add(loc);
                }
            }
        }
        return locations;
    }

}