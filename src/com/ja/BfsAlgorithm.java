package com.company;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BfsAlgorithm {
    /**
     * Running multi purpose bfs algorithm
     * @param vert source vertex
     * @param action defines actions to take on each vertex traversal
     */
    public void run(Vertex vert, BfsAction action) {
        List<Vertex> trackVisitedList = new ArrayList<>();
        Queue<Vertex> q = new LinkedList<>();
        q.add(vert);
        vert.setVisited(true);
        trackVisitedList.add(vert);
        while (!q.isEmpty()) {
            Vertex n = (Vertex) q.poll();

            //we are breaking if needed
            if (action.onVisitParent(n))
                break;

            for (Vertex adj : n.getAdjVertices()) {
                if (!adj.isVisited()) {
                    adj.setVisited(true);
                    trackVisitedList.add(adj);
                    q.add(adj);

                    //we are breaking if needed
                    if (action.onVisitChild(adj)) {
                        //we are emptying the quee to break from the loop
                        q = new LinkedList();
                        break;
                    }
                }
            }
        }

        //reset vertexes state
        for (Vertex v : trackVisitedList) {
            v.setVisited(false);
        }
    }

    /**
     * That interface will help us to generify BFS Algorithm usage
     * for different purposes
     */
    public interface BfsAction {
        /**
         * @return true if want to interrupt traversal
         */
        boolean onVisitParent(Vertex vertex);

        /**
         * @return true if want to interrupt traversal
         */
        boolean onVisitChild(Vertex vertex);
    }
}