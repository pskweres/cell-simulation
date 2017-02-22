package pdd.inject;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import pdd.population.CubePopulation;
import pdd.population.Population;
import pdd.cell.Cell;
import pdd.cell.CellFactory;
import pdd.cell.TestCell;

public class AppInjector extends AbstractModule {

    Class populationClass;
    Class cellClass;

    public AppInjector(Class populationClass, Class cellClass) {
        this.populationClass = populationClass;
        this.cellClass = cellClass;
    }

    @Override
    protected void configure() {
        
        bind(Population.class).to(populationClass);
        
        install(new FactoryModuleBuilder()
            .implement(Cell.class, cellClass)
            .build(CellFactory.class));

               
    }

}