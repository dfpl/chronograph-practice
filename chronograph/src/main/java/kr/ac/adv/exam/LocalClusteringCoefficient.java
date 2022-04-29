package kr.ac.adv.exam;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import java.util.Map;
import java.util.Map.Entry;

public class LocalClusteringCoefficient {

    Graph graph;

    LocalClusteringCoefficient(Graph graph) {
        this.graph = graph;
        int numberOfTriangles = 0;

    }

    public int getNumberOfTriangles() {
        int numberOfTriangles = 0;
        ClosenessCentrality closenessCentrality = new ClosenessCentrality(graph);
        for (Vertex vertex1 : graph.getVertices()) {
            closenessCentrality.execute(vertex1);

        }
        return numberOfTriangles;
    }


}
