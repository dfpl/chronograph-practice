package kr.ac.adv.exam;

import com.tinkerpop.blueprints.Graph;
import java.util.Iterator;

public class getNumber {

    public static long getVertexNumber(Graph graph) {
        return graph.getVertices().stream().count();
    }

    public static long getEdgeNumber(Graph graph) {
        return graph.getEdges().stream().count();
    }


}
