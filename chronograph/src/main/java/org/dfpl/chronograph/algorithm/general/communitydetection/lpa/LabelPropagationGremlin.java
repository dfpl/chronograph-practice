package org.dfpl.chronograph.algorithm.general.communitydetection.lpa;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.dfpl.chronograph.traversal.TraversalEngine;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.GremlinFluentPipeline;

public class LabelPropagationGremlin {

    public Graph compute(Graph g) {
        // Set initial value as label
        int i = 0;
        for (Vertex v : g.getVertices()) {
            v.setProperty("value", i);
            i++;
        }

        GremlinFluentPipeline pipeline;
        HashMap<String, Integer> valueCounter = new HashMap<>();

        while (true) {
            pipeline = new TraversalEngine(g, g, Graph.class, false);

            for (Vertex v : g.getVertices())
                v.setProperty("oldValue", v.getProperty("value"));

            pipeline.V().as("s").transform((Function<Vertex, Collection<Vertex>>) v ->
                    v.getVertices(Direction.OUT, "link"),
                Collection.class, Vertex.class,
                false)
                .sideEffect((Function<Collection<Vertex>, Collection<Vertex>>) s -> {
                    valueCounter.clear();
                    for (Vertex n : s) {
                        String key = n.getProperty("oldValue").toString();
                        int count = valueCounter.getOrDefault(key, 0);
                        valueCounter.put(key, count + 1);
                    }
                    return s;
                })
                // TODO: Implement back
                // .back("s")
                .sideEffect((Function<Vertex, Vertex>) s -> {
                    String maxValue = valueCounter.entrySet().stream()
                        .max(Comparator.comparingInt(Map.Entry::getValue)).get().getKey();
                    s.setProperty("value", Integer.valueOf(maxValue));
                    return s;
                }).toList();

            boolean end = true;
            for (Vertex v : g.getVertices()) {
                if (v.getProperty("oldValue") != v.getProperty("value")) {
                    end = false;
                    break;
                }
            }
            if (end)
                break;
        }

        return g;
    }

    public void printLabels(Graph g) {
        for (Vertex v : g.getVertices()) {
            System.out.println(v + " " + v.getProperty("value").toString());
        }
    }
}
