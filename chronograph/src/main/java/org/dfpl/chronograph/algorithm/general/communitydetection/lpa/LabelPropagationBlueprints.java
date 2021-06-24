package org.dfpl.chronograph.algorithm.general.communitydetection.lpa;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class LabelPropagationBlueprints {

    public void compute(Graph g, String... labels) {
        // Set initial value as label
        int i = 0;
        for (Vertex v : g.getVertices()) {
            v.setProperty("value", i);
            i++;
        }

        int t = 0;
        while (true) {
            System.out.println("Iteration: " + t);
            for (Vertex v : g.getVertices()) {
                v.setProperty("oldValue", v.getProperty("value"));
            }

            HashMap<String, Integer> valueCounter = new HashMap<>();
            for (Vertex v : g.getVertices()) {
                for (Vertex n : v.getVertices(Direction.OUT, labels)) {
                    String key = n.getProperty("oldValue").toString();
                    int count = valueCounter.getOrDefault(key, 0);
                    valueCounter.put(key, count + 1);
                }

                String maxValue = valueCounter.entrySet().stream()
                    .max(Comparator.comparingInt(Map.Entry::getValue)).get().getKey();
                v.setProperty("value", Integer.valueOf(maxValue));
            }

            boolean end = true;
            for (Vertex v : g.getVertices()) {

                if (v.getProperty("oldValue") != v.getProperty("value")) {
                    end = false;
                    break;
                }
            }
            if (end)
                break;
            t++;
        }
    }

    public void printLabels(Graph g) {
        for (Vertex v : g.getVertices()) {
            System.out.println(v + " " + v.getProperty("value").toString());
        }
    }

}