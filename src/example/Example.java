import org.apache.hadoop.conf.Configuration; 
import org.apache.hadoop.fs.Path; 
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FSDataInputStream;
import java.net.URI;
import java.io.IOException;

public class Example {
    public static void main(String[] args) {
        URI uri = URI.create("input/seed-graph.txt");
        System.out.println("ASDF");
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://localhost:9000");
        try {
            FileSystem file = FileSystem.get(uri, conf);
            FSDataInputStream in = file.open(new Path(uri));

            String line;
            while((line = in.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException ex) {
            System.out.println(ex);
        }

    }
}