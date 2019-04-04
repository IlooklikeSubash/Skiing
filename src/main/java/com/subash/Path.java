package com.subash;

public class Path
{
    private final Path next;
    private final int point;
    private final int pathLength;
    private final int end;

    public Path(Path next, int point, int pathLength, int end)
    {
        this.next = next;
        this.point = point;
        this.pathLength = pathLength;
        this.end = end;
    }

    public Path getNext()
    {
        return next;
    }

    public int getPathLength()
    {
        return pathLength;
    }

    public int getEnd()
    {
        return end;
    }

    public int getPoint()
    {
        return point;
    }

    @Override
    public String toString()
    {
        return point + "->" + next;
    }
}
