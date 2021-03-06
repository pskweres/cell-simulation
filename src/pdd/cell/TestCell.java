package pdd.cell;

import java.util.*;
import java.io.*;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import com.google.inject.assistedinject.*;
import pdd.cell.Cell;
import pdd.cell.CellLocation;
import pdd.util.Util;
import petrify.snoopy.parser.package$;
import petrify.snoopy.parser.ParseResult;
import petrify.model.PetriNet;
import petrify.model.State;
import petrify.model.Place;
import scala.collection.JavaConverters;
import scala.Option;

import org.apache.hadoop.conf.Configuration; 
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.Path;
import java.net.URI;

public class TestCell extends BasicCell {

    static final String PRODUCED_FGF19_PLACE_NAME = "FGF19";
    static final String REQUIRED_FGF19_PLACE_NAME = "ATF4";

    PetriNet net;
    State currentState;
    Place producedFGF19Place;
    Place requiredFGF19Place;

    private Map<Place, Object> stateAsMap(State state) {
        return JavaConverters.mapAsJavaMapConverter(currentState.placesValues()).asJava();
    }

    public boolean isFGF19produced() {
        Map<Place, Object> values = stateAsMap(currentState);
        int produced = 0;
        produced = (int) values.get(producedFGF19Place);
        return (produced > 0 ? true : false);
    }

    public boolean isFGF19required() {
        Map<Place, Object> values = stateAsMap(currentState);
        int required = 0;
        required = (int) values.get(requiredFGF19Place);
        return (required > 0 ? true : false);
    }

    public void addFGF19() {
        Option<State> newState = currentState.incrementPlace(producedFGF19Place);
        if (newState.isDefined()) {
            currentState = newState.get();
        }
    }

    public void removeFGF19() {
        Option<State> newState = currentState.decrementPlace(producedFGF19Place);
        if (newState.isDefined()) {
            currentState = newState.get();
        }
    }

    public void init() {
        URI uri = URI.create("net.spept");
        // Configuration conf = new Configuration();
        // FileSystem file = FileSystem.get(uri, conf);
        // FSDataInputStream in = file.open(new Path(uri));
        ParseResult result = package$.MODULE$.tupleToParseResult(package$.MODULE$.read(uri.toString()));
        net = result.net();
        currentState = result.state();

        Map<Place, Object> values = stateAsMap(currentState);
        for (Map.Entry<Place, Object> entry : values.entrySet()) {
            if (entry.getKey().name().equals(PRODUCED_FGF19_PLACE_NAME)) {
                producedFGF19Place = entry.getKey();
            }
            if (entry.getKey().name().equals(REQUIRED_FGF19_PLACE_NAME)) {
                requiredFGF19Place = entry.getKey();
            }
        }

    }

    public void nextState() {
        currentState = net.apply(currentState);
    }

    @AssistedInject
    public TestCell() {
    }

    public String toString() {
        return currentState.toString();
    }

    public Text toText() {
        Serializable[] fields = new Serializable[4];
        fields[0] = currentState;
        fields[1] = net;
        fields[2] = producedFGF19Place;
        fields[3] = requiredFGF19Place;
        Text text = new Text(Util.arrayToString(fields));
        return text;
    }


    public void fromText(Text text) {
        Object[] fields = Util.arrayFromString(text.toString());
        currentState = (State) fields[0];
        net = (PetriNet) fields[1];
        producedFGF19Place = (Place) fields[2];
        requiredFGF19Place = (Place) fields[3];   
    }

}