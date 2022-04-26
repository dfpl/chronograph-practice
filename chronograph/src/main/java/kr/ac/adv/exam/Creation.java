package kr.ac.adv.exam;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Creation {

    public static TinkerGraph getDBLPGraph() throws IOException {
        TinkerGraph g = new TinkerGraph();
        BufferedReader bufferedReader = new BufferedReader(
            new FileReader("src/main/java/kr/ac/sejong/com-dblp.ungraph.txt"));
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            if (line.startsWith("#")) {
                continue;
            }

            String[] strings = line.split("\t");
            if (g.getVertex(strings[0]) == null) {
                g.addVertex(strings[0]);
            }

            if (g.getVertex(strings[1]) == null) {
                g.addVertex(strings[1]);
            }
            Vertex source = g.getVertex(strings[0]);
            Vertex destination = g.getVertex(strings[1]);
            Edge edge1;
            Edge edge2;

            String label = "";

            try{
                edge1 = g.addEdge(source.getId().toString() + "|" + label + "|" + destination.getId().toString(),
                    source, destination, label);
            }catch(Exception e){
                edge1 = g.getEdge(
                    destination.getId().toString() + "|" + label + "|" + source.getId().toString());}
            try {
                edge2 = g.addEdge(
                    destination.getId().toString() + "|" + label + "|" + source.getId().toString(),
                    destination, source, label);
            } catch (Exception e) {
                edge2 = g.getEdge(
                    destination.getId().toString() + "|" + label + "|" + source.getId().toString());
            }
            edge1.setProperty("weight", 1);
            edge2.setProperty("weight", 2);


        }
        return g;
    }

    public static TinkerGraph getTempGraph() {
        TinkerGraph g = new TinkerGraph();
        Vertex v1 = g.addVertex("1");
        Vertex v2 = g.addVertex("2");
        Vertex v3 = g.addVertex("3");
        Vertex v4 = g.addVertex("4");
        Vertex v5 = g.addVertex("5");
        g.addEdge("v1->v2", v1, v2, "");
        g.addEdge("v2->v1", v2, v1, "");
        g.addEdge("v2->v3", v2, v3, "");
        g.addEdge("v3->v2", v3, v2, "");
        g.addEdge("v3->v4", v3, v4, "");
        g.addEdge("v4->v3", v4, v3, "");
        return g;

    }
    public static TinkerGraph getEmailGraph() throws IOException {
        TinkerGraph g = new TinkerGraph();
        BufferedReader reader = new BufferedReader(
            new FileReader("D:\\Email.txt"));
        while (true) {
            String str = reader.readLine();
            if (str == null)
                break;
            if (str.startsWith("#"))
                continue;

            String[] arr = str.split("\t");
            Vertex source , in;
            
            Vertex sender = g.addVertex(arr[0]);
            Vertex receiver = g.addVertex(arr[1]);
            Edge e = g.addEdge(sender, receiver, "");

        }
    }



}
