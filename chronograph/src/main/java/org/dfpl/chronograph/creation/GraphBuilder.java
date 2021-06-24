package org.dfpl.chronograph.creation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.dfpl.chronograph.crud.memory.ChronoGraph;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Time;
import com.tinkerpop.blueprints.TimeInstant;
import com.tinkerpop.blueprints.Vertex;

public class GraphBuilder {

    public static Graph createSmallStaticGraph1() {
        Graph g = new ChronoGraph();

        Vertex a = g.addVertex("A");
        Vertex b = g.addVertex("B");
        Vertex c = g.addVertex("C");
        Vertex d = g.addVertex("D");
        Vertex e = g.addVertex("E");

        g.addEdge(a, c, "link");
        g.addEdge(c, a, "link");

        g.addEdge(b, c, "link");
        g.addEdge(c, b, "link");

        g.addEdge(c, d, "link");
        g.addEdge(d, c, "link");

        g.addEdge(d, e, "link");
        g.addEdge(e, d, "link");

        return g;
    }

    public static Graph createSmallStaticGraph2() {
        Graph g = new ChronoGraph();

        Vertex a = g.addVertex("A");
        Vertex b = g.addVertex("B");
        Vertex c = g.addVertex("C");
        Vertex d = g.addVertex("D");
        Vertex e = g.addVertex("E");
        Vertex f = g.addVertex("F");

        g.addEdge(a, b, "link");
        g.addEdge(b, a, "link");

        g.addEdge(a, c, "link");
        g.addEdge(c, a, "link");

        g.addEdge(c, b, "link");
        g.addEdge(b, c, "link");

        g.addEdge(c, d, "link");
        g.addEdge(d, c, "link");

        g.addEdge(b, d, "link");
        g.addEdge(d, b, "link");

        g.addEdge(d, e, "link");
        g.addEdge(e, d, "link");

        g.addEdge(e, f, "link");
        g.addEdge(f, e, "link");

        g.addEdge(d, f, "link");
        g.addEdge(f, d, "link");

        return g;
    }

    public static Graph createSmallStaticGraph3() {
        Graph g = new ChronoGraph();

        Vertex v1 = g.addVertex("1");
        Vertex v2 = g.addVertex("2");
        Vertex v3 = g.addVertex("3");
        Vertex v4 = g.addVertex("4");
        Vertex v5 = g.addVertex("5");
        Vertex v6 = g.addVertex("6");
        Vertex v7 = g.addVertex("7");
        Vertex v8 = g.addVertex("8");

        g.addEdge(v1, v2, "c");
        g.addEdge(v1, v3, "c");
        g.addEdge(v1, v4, "c");
        g.addEdge(v2, v3, "c");
        g.addEdge(v2, v5, "c");
        g.addEdge(v3, v4, "c");
        g.addEdge(v3, v5, "c");
        g.addEdge(v3, v6, "c");
        g.addEdge(v4, v6, "c");
        g.addEdge(v7, v8, "c");

        return g;
    }

    /**
     * http://snap.stanford.edu/data/email-Eu-core-temporal.html
     *
     * @return G with e(from|sendEmailTo|to)_t
     * @throws IOException
     */
    public static Graph createSNAPEmailEuCoreTemporal(String fileLoc) throws IOException {
        Graph g = new ChronoGraph();

        BufferedReader br = new BufferedReader(new FileReader(fileLoc));
        while (true) {
            String line = br.readLine();
            if (line == null)
                break;

            String[] arr = line.split("\\s");

            Vertex from = null;
            try {
                from = g.addVertex(arr[0]);
            } catch (Exception e) {
                from = g.getVertex(arr[0]);
            }
            Vertex to = null;
            try {
                to = g.addVertex(arr[1]);
            } catch (Exception e) {
                to = g.getVertex(arr[1]);
            }
            Edge fromTo = g.addEdge(from, to, "sendEmailTo");
            Edge toFrom = g.addEdge(to, from, "sendEmailTo");

            Time time = new TimeInstant(Integer.parseInt(arr[2]));

            fromTo.addEvent(time);
            toFrom.addEvent(time);
        }
        br.close();

        return g;
    }

    public static Graph createSmallTemporalGraph1() {
        Graph g = new ChronoGraph();

        Vertex a = g.addVertex("A");
        Vertex b = g.addVertex("B");
        Vertex c = g.addVertex("C");
        Vertex d = g.addVertex("D");
        Vertex e = g.addVertex("E");

        Time time5 = new TimeInstant(5);
        Time time8 = new TimeInstant(8);
        Time time10 = new TimeInstant(10);
        Time time12 = new TimeInstant(12);
        Time time13 = new TimeInstant(13);
        Time time14 = new TimeInstant(14);
        Time time16 = new TimeInstant(16);

        // Edges from A
        Edge ad = a.addEdge("links", d);
        ad.addEvent(time5);

        Edge ab = a.addEdge("links", b);
        ab.addEvent(time10);

        // Edges from B
        Edge bc = b.addEdge("links", c);
        bc.addEvent(time8);
        bc.addEvent(time16);

        Edge ba = b.addEdge("links", a);
        ba.addEvent(time10);

        Edge bd = b.addEdge("links", d);
        bd.addEvent(time12);

        // Edges from C
        Edge cb = c.addEdge("links", b);
        cb.addEvent(time8);
        cb.addEvent(time16);

        Edge cd = c.addEdge("links", d);
        cd.addEvent(time13);

        Edge ce = c.addEdge("links", e);
        ce.addEvent(time14);

        // Edges from D
        Edge da = d.addEdge("links", a);
        da.addEvent(time5);

        Edge db = d.addEdge("links", b);
        db.addEvent(time12);

        Edge dc = d.addEdge("links", c);
        dc.addEvent(time13);

        // Edges from E
        Edge ec = e.addEdge("links", c);
        ec.addEvent(time14);

        return g;
    }

    public static Graph createSmallTemporalGraph2() {
        Graph graph = new ChronoGraph();

        Vertex a = graph.addVertex("A");
        Vertex b = graph.addVertex("B");
        Vertex c = graph.addVertex("C");
        Vertex f = graph.addVertex("F");
        Vertex g = graph.addVertex("G");

        Time time2 = new TimeInstant(2);
        Time time3 = new TimeInstant(3);
        Time time4 = new TimeInstant(4);
        Time time5 = new TimeInstant(5);
        Time time7 = new TimeInstant(7);

        // Edges from A
        Edge ab = a.addEdge("links", b);
        ab.addEvent(time2);

        Edge af = a.addEdge("links", f);
        af.addEvent(time7);

        Edge ac = a.addEdge("links", c);
        ac.addEvent(time4);

        // Edges from B
        Edge ba = b.addEdge("links", a);
        ba.addEvent(time5);

        Edge bf = b.addEdge("links", f);
        bf.addEvent(time3);

        // Edges from C
        Edge cf = c.addEdge("links", f);
        cf.addEvent(time5);

        // Edges from F
        Edge fg = f.addEdge("links", g);
        fg.addEvent(time3);

        return graph;
    }

    public static Graph createSmallTemporalGraph3() {
        Graph graph = new ChronoGraph();

        Vertex a = graph.addVertex("A");
        Vertex b = graph.addVertex("B");
        Vertex c = graph.addVertex("C");
        Vertex f = graph.addVertex("F");
        Vertex g = graph.addVertex("G");

        Time time3 = new TimeInstant(3);
        Time time5 = new TimeInstant(5);
        Time time6 = new TimeInstant(6);
        Time time7 = new TimeInstant(7);
        Time time8 = new TimeInstant(8);
        Time time9 = new TimeInstant(9);

        // Edges from A
        Edge af = a.addEdge("links", f);
        af.addEvent(time3);
        af.addEvent(time7);

        Edge ab = a.addEdge("links", b);
        ab.addEvent(time6);

        // Edges from B
        Edge bc = b.addEdge("links", c);
        bc.addEvent(time7);

        Edge ba = b.addEdge("links", a);
        ba.addEvent(time8);

        // Edges from C
        Edge cb = c.addEdge("links", b);
        cb.addEvent(time6);

        // Edges from F
        Edge fc = f.addEdge("links", c);
        fc.addEvent(time5);

        Edge fg = f.addEdge("links", g);
        fg.addEvent(time7);

        // Edges from G
        Edge ga = g.addEdge("links", a);
        ga.addEvent(time9);

        for (Edge edge : graph.getEdges()) {
            edge.setOrderByStart(false);
        }

        return graph;
    }

}
