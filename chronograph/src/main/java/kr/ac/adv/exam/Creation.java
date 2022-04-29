package kr.ac.adv.exam;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.dfpl.chronograph.crud.memory.ChronoVertex;

public class Creation {

    public static ChronoGraph getGraph() throws IOException {
        ChronoGraph g = new ChronoGraph();
        BufferedReader bufferedReader = new BufferedReader(
            new FileReader("src/main/java/kr/ac/adv/exam/com-dblp.ungraph.txt"));
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            if (line.startsWith("#")) {
                continue;
            }

            String[] strings = line.split("\t");

            Vertex source = g.addVertex(strings[0]);
            Vertex destination = g.addVertex(strings[1]);

            Edge edge1 = g.addEdge(source, destination, "");
            Edge edge2 = g.addEdge(destination, source, "");
            edge1.setProperty("weight", 1);
            edge2.setProperty("weight", 1);
        }
        return g;
    }

    public static ChronoGraph getTempGraph() {
        ChronoGraph g = new ChronoGraph();
        Vertex v1 = g.addVertex("1");
        Vertex v2 = g.addVertex("2");
        Vertex v3 = g.addVertex("3");
//        Vertex v4 = g.addVertex("4");
//        Vertex v5 = g.addVertex("5");
        g.addEdge(v1, v2, "");
        g.addEdge(v2, v1, "");
        g.addEdge( v2, v3, "");
        g.addEdge(v3, v2, "");
        g.addEdge(v1, v3, "");
        g.addEdge(v3, v1, "");

//        g.addEdge( v3, v4, "");
//        g.addEdge( v4, v3, "");
//        g.addEdge( v1, v5, "");
        g.getEdges().stream().forEach(e->{
            e.setProperty("weight", 1);
        });
        return g;

    }


}
