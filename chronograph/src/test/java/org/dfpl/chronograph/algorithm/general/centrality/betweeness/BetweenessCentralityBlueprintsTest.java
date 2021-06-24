package org.dfpl.chronograph.algorithm.general.centrality.betweeness;

import com.tinkerpop.blueprints.Graph;
import org.dfpl.chronograph.algorithm.general.centrality.betweenness.BetweennessCentralityBlueprints;
import org.junit.Test;

import static org.dfpl.chronograph.creation.GraphBuilder.createSmallStaticGraph1;

public class BetweenessCentralityBlueprintsTest {

    @Test
    public void testSmallStaticGraph1() {
        BetweennessCentralityBlueprints bb = new BetweennessCentralityBlueprints();

        Graph g = createSmallStaticGraph1();

        bb.compute(g);
        bb.printCentrality(g);
    }
}
