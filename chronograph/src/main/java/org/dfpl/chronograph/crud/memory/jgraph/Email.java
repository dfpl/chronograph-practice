package org.dfpl.chronograph.crud.memory.jgraph;
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


    public JGraph getG() {
        return (JGraph)g;
    }

    public void setG(Graph g) {
        this.g = g;
    }


    public Email() throws IOException {
        this.g = new JGraph();
        BufferedReader reader = new BufferedReader(
                new FileReader("D:\\Email.txt"));
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

   public Email(Graph g) throws IOException {
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
