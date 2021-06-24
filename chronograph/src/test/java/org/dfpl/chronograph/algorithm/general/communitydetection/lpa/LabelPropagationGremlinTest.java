package org.dfpl.chronograph.algorithm.general.communitydetection.lpa;

import com.tinkerpop.blueprints.Graph;
import org.junit.Test;

import static org.dfpl.chronograph.creation.GraphBuilder.createSmallStaticGraph2;

public class LabelPropagationGremlinTest {
    @Test
    public void testSmallScaleGraph2() {
        LabelPropagationGremlin lpg = new LabelPropagationGremlin();
        Graph g = createSmallStaticGraph2();
        lpg.compute(g);
        lpg.printLabels(g);
    }
}
