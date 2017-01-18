package pdd.inject;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import pdd.population.CubePopulation;
import pdd.population.Population;
import pdd.cell.Cell;
import pdd.cell.CellFactory;
import pdd.cell.MagicCell;

public class AppInjector extends AbstractModule {

    @Override
    protected void configure() {
        
        bind(Population.class).to(CubePopulation.class);
        
        install(new FactoryModuleBuilder()
            .implement(Cell.class, MagicCell.class)
            .build(CellFactory.class));
               
    }

}