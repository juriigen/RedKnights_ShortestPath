package com.company;

import java.util.LinkedList;

public class Vertex {
    private final int mX;
    private final int mY;
    private String mName;
    private boolean visited;
    private LinkedList<Vertex> mAdjVertices = new LinkedList<>();

    public Vertex(int x, int y) {
        mName = "(" + x + "," + y + ")";
        mX = x;
        mY = y;
    }

    @Override
    public String toString() {
        return mName;
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Vertex)
        {
            Vertex v=(Vertex)o;
            if(this.getX()==v.getX()&&this.getY()==v.getY())
                return true;
            else return false;
        }
        else return false;

    }

    @Override
    public int hashCode() {
        int result = mX;
        result = 31 * result + mY;
        return result;
    }

    public LinkedList<Vertex> getAdjVertices() {
        return mAdjVertices;
    }

    public String getName() {
        return mName;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void addAdjVertex(Vertex v) {
        mAdjVertices.add(v);
    }
}
