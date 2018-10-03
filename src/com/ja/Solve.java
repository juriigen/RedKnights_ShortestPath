package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.List;


public class Solve {

    private int xi,yi,xf,yf,n;

    /**
     * Creates  board as a graph represented as adjacency list
     *
     * @return root node of the graph
     */
    private static Vertex  createBoardGraph(Vertex source,ProblemConditions p) {
        //first we are creating all possible nodes in the graph
        //without associating any adjacent nodes to them
        final ArrayList<Vertex> vertexes = new ArrayList<>();
        for (int i = 0; i < p.getBoardSize(); i++) {
            for (int j=0; j < p.getBoardSize(); j++) {
                vertexes.add(new Vertex(i, j));
            }
        }

        //and now we are actually associating adjacent vertices
        //to each created vertex
        for (Vertex v : vertexes) {
            if (v.getAdjVertices().isEmpty())
                createAdjListForVertex(v, vertexes,p);
        }

        //we return the source Vertex as it will represent the entire graph
        return vertexes.get(source.getX()*p.getBoardSize()+source.getY());
    }

    /**
     * Creates adjacency list for provided node , according to problem definition
     *
     * @param vertexes are used to define connections between one another
     */
    private static void createAdjListForVertex(Vertex v, ArrayList<Vertex> vertexes,ProblemConditions p) {
        //Here we are just trying to create adjacent nodes in all possible directions

        int xROrL = v.getX();
        int xULOrUR = v.getX() - p.getFareMostMove();
        int xLLOrLR = v.getX() + p.getFareMostMove();


        int yR = v.getY() + p.getFareMostMove();
        int yL = v.getY() - p.getFareMostMove();
        int yULOrLL = v.getY() - p.getNearestMove();
        int yUROrLR = v.getY() + p.getNearestMove();


        //upper left
        createAdjVertexIfInBounds(v, xULOrUR, yULOrLL, vertexes,p);
        //upper right
        createAdjVertexIfInBounds(v, xULOrUR, yUROrLR, vertexes,p);
        //right
        createAdjVertexIfInBounds(v, xROrL, yR, vertexes,p);
        //lower right
        createAdjVertexIfInBounds(v, xLLOrLR, yUROrLR, vertexes,p);
        //lower left
        createAdjVertexIfInBounds(v, xLLOrLR, yULOrLL, vertexes,p);
        //left
        createAdjVertexIfInBounds(v, xROrL, yL, vertexes,p);


    }

    /**
     * adds adjacent nodes according to constrains in problem definition
     *
     * @param vertexes will be used to define connections between one another
     */
    private static void createAdjVertexIfInBounds(Vertex v, int x, int y, ArrayList<Vertex> vertexes,ProblemConditions p) {
        //check problemConditions
        if (p.isPositionOnBoard(x, y)) {

            //we don't want to assign new instances of Vertexes
            //as adjacent vertices , that will not work.
            //we need to reuse the same instances and assign
            //connections between them
            final int indexOf = vertexes.indexOf(new Vertex(x, y));
            final Vertex reusedVertex = vertexes.get(indexOf);
            v.addAdjVertex(reusedVertex);
        }
    }

    /**
     * Finds a shortest path from source to destination
     *
     * @param from source node
     * @param to   destination node
     * @return string complete representation of solved problem
     */
    private static String solveProblem(final Vertex from, final Vertex to,BfsAlgorithm bfs) {
        final List<Vertex> path = new ArrayList<>();
        final HashMap<Vertex, Vertex> parentToChild = new HashMap<>();
        bfs.run(from, new BfsAlgorithm.BfsAction() {

            private Vertex currParent;

            @Override
            public boolean onVisitParent(Vertex parent) {
                currParent = parent;
                return false;
            }

            @Override
            public boolean onVisitChild(Vertex vertex) {
                parentToChild.put(vertex, currParent);
                if (vertex.equals(to)) return true;
                else return false;
            }
        });
        path.add(to);
        Vertex par = parentToChild.get(to);
        while (par != null) {
            path.add(par);
            par = parentToChild.get(par);
        }

        Collections.reverse(path);
        if(path.size()-1==0)
            return "Impossible";
        else return   (path.size() - 1) + "\n"
                + pathToString(path);
    }

    /**
     * Converts a list of nodes that represents a path
     * to simple string representation
     *
     * @param path list of nodes
     */
    private static String pathToString(List<Vertex> path) {
        StringBuilder sb = new StringBuilder();
        int n=0;
        while(n!=path.size()-1)
        {    Vertex v=path.get(n);
            Vertex v1=path.get(n+1);
            if((v1.getX()==v.getX()-2)&&(v1.getY()==v.getY()-1))
                sb.append("UL ");
            if((v1.getX()==v.getX()-2)&&(v1.getY()==v.getY()+1))
                sb.append("UR ");
            if((v1.getX()==v.getX())&&(v1.getY()==v.getY()+2))
                sb.append("R ");
            if((v1.getX()==v.getX()+2)&&(v1.getY()==v.getY()+1))
                sb.append("LR ");
            if((v1.getX()==v.getX()+2)&&(v1.getY()==v.getY()-1))
                sb.append("LL ");
            if((v1.getX()==v.getX())&&(v1.getY()==v.getY()-2))
                sb.append("L ");
            n++;
        }

        return sb.toString();
    }



    /**
     * Uses bfs internally to convert a graph to list of
     * distinct Vertices
     *
     * @param vert source Vertex
     * @return list of all child nodes
     */
    private static List<Vertex> graphToList(Vertex vert,BfsAlgorithm bfs) {
        final List<Vertex> list = new ArrayList<>();
        bfs.run(vert, new BfsAlgorithm.BfsAction() {
            @Override
            public boolean onVisitParent(Vertex v) {
                list.add(v);
                return false;
            }

            @Override
            public boolean onVisitChild(Vertex vertex) {
                return false;
            }
        });
        return list;
    }


    public static void main(String[] args) {
        Solve s=new Solve();
        s.readInput();
        //we are representing available moves on NxN board
        //by vertex adjacency list
        ProblemConditions p=new ProblemConditions(s.n,1,2);

        Vertex source= createBoardGraph(new Vertex(s.xi,s.yi),p);

        //printing the graph for visualisation
        BfsAlgorithm bfs=new BfsAlgorithm();


        //printing the solution
        System.out.println(solveProblem(source,new Vertex(s.xf,s.yf),bfs));
    }
    //The method for reading from keyboard and setting the constraints
    public void readInput()
    {
        Scanner scanner=new Scanner(System.in);

        n=scanner.nextInt();
        while((n<5)||(n>200))
        {

            n=scanner.nextInt();
        }

        xi=scanner.nextInt();
        while((xi<0)||(xi>n-1))
        {
            xi=scanner.nextInt();
        }

        yi=scanner.nextInt();
        while((yi<0)||(yi>n-1))
        {

            yi=scanner.nextInt();
        }

        xf=scanner.nextInt();
        while((xf<0)||(xf>n-1))
        {

            xf=scanner.nextInt();
        }

        yf=scanner.nextInt();
        while((yf<0)||(yf>n-1))
        {
            yf=scanner.nextInt();
        }

    }
}