package org.dfpl.chronograph.algorithm.general.centrality.betweeness;

import com.tinkerpop.blueprints.Graph;
import org.dfpl.chronograph.algorithm.general.centrality.betweenness.BetweennessCentralityGremlin;

import static org.dfpl.chronograph.creation.GraphBuilder.createSmallStaticGraph1;

public class BetweennessCentralityGremlinTest {
    public static void main(String[] args) {
        BetweennessCentralityGremlin bg = new BetweennessCentralityGremlin();

        Graph g = createSmallStaticGraph1();

        bg.compute(g);

        bg.printCentrality(g);
    }
}
