package org.dfpl.chronograph.crud.memory.test;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import org.dfpl.chronograph.crud.memory.jgraph.JGraph;

/**
 * Hello world!
 *
 */
public class Email {
    private Graph g;


    public Graph getG() {
        return g;
    }

    public void setG(Graph g) {
        this.g = g;
    }


    Email() throws IOException {
        this.g = new JGraph();
        BufferedReader reader = new BufferedReader(
                new FileReader("/Users/ahnjaehyun/Documents/gitRepo/chronograph/chronograph/src/main/java/org/dfpl/chronograph/crud/memory/test/email.txt"));
        while (true) {
            String str = reader.readLine();
            if (str == null)
                break;
            if (str.startsWith("#"))
                continue;

            String[] arr = str.split("\t");
//            Vertex sender = arr[0];
//            Vertex receiver = arr[1];
//            if(g.getVertex(arr[0]) == null) {
//                Vertex out = g.addVertex(arr[0]);
//            }
//            if(g.getVertex(arr[1]) == null)
//            {
//                Vertex in = g.addVertex(arr[1]);
//            }
            Vertex sender = g.addVertex(arr[0]);
            Vertex receiver = g.addVertex(arr[1]);
            Edge e = g.addEdge(sender, receiver, "");

        }
    }

    Email(Graph g) throws IOException {
        BufferedReader reader = new BufferedReader(
                new FileReader("email.txt"));
        while (true) {
            String str = reader.readLine();
            if (str == null)
                break;
            if (str.startsWith("#"))
                continue;

            String[] arr = str.split("\t");
            Vertex sender = g.addVertex(arr[0]);
            Vertex receiver = g.addVertex(arr[1]);
            g.addEdge(sender, receiver, "");
        }
    }
}
