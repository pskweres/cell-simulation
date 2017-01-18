package pdd.cell;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.WritableComparable;

public class CellLocation implements WritableComparable<CellLocation> {
    
    int x, y, z;

    public CellLocation() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public CellLocation(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(z);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        x = in.readInt();
        y = in.readInt();
        z = in.readInt();
    }

    public static CellLocation read(DataInput in) throws IOException {
        CellLocation loc = new CellLocation();
        loc.readFields(in);
        return loc;
    }

    @Override
    public int compareTo(CellLocation loc) {
        int cmp = 0;
        
        cmp = (this.x < loc.x ? -1 : (this.x == loc.x ? 0 : 1));
        if (cmp != 0)
            return cmp;
        
        cmp = (this.y < loc.y ? -1 : (this.y == loc.y ? 0 : 1));
        if (cmp != 0)
            return cmp;

        cmp = (this.z < loc.z ? -1 : (this.z == loc.z ? 0 : 1));
        return cmp;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int hashCode = 17;
        hashCode = 31 * hashCode + x;
        hashCode = 31 * hashCode + y;
        hashCode = 31 * hashCode + z;
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CellLocation))
            return false;
        CellLocation loc = (CellLocation) o;
        if (hashCode() == loc.hashCode()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return Integer.toString(x) + "," + Integer.toString(y) + "," + Integer.toString(z);
    }
}