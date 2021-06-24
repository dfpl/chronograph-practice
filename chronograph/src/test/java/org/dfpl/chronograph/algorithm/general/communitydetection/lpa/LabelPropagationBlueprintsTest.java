package org.dfpl.chronograph.algorithm.general.communitydetection.lpa;

import com.tinkerpop.blueprints.Graph;
import org.junit.Test;

import static org.dfpl.chronograph.creation.GraphBuilder.createSmallStaticGraph2;

public class LabelPropagationBlueprintsTest {
    @Test
    public void testSmallStaticGraph2() {
        LabelPropagationBlueprints lpa = new LabelPropagationBlueprints();
        Graph g = createSmallStaticGraph2();

        lpa.compute(g, "link");
        lpa.printLabels(g);
    }
}
