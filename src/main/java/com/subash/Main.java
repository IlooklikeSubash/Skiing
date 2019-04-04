package com.subash;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Main
{

    // initialize variables
    private static int[][] grid;
    private static int x;
    private static int y;
    private static Path currBestPath;
    private static Map<String, Path> visited;

    public static void main(String[] args) throws IOException
    {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("/map.txt")));)
        {

            String strLine;

            if ((strLine = br.readLine()) != null)
            {
                String[] size = strLine.split(" ");
                x = Integer.parseInt(size[0]);
                y = Integer.parseInt(size[1]);
                grid = new int[x][y];

            }
            int counterX = 0;
            while ((strLine = br.readLine()) != null)
            {

                String[] line = strLine.split(" ");
                int counterY = 0;
                for (String num : line)
                {
                    grid[counterX][counterY] = Integer.parseInt(num);
                    counterY++;
                }
                counterX++;
            }

        }
        visited = new HashMap<>(x * y);

        for (int i = 0; i < x; i++)
        {
            for (int j = 0; j < y; j++)
            {
                Path path = getPath(i, j);
                currBestPath = determineBestPath(currBestPath, path);

            }
        }

        System.out.println(currBestPath);
        System.out.println("Longest path:" + currBestPath.getPathLength());
        System.out.println("Max drop:" + (currBestPath.getPoint() - currBestPath.getEnd()));

    }

    private static String getKey(int currentRow, int currentColumn)
    {
        return currentColumn + ":" + currentRow;
    }

    private static Path getPath(int currentRow, int currentColumn)
    {
        String key = getKey(currentRow, currentColumn);
        Path path = visited.get(key);
        if (path == null)
        {
            path = visitMin(currentRow, currentColumn);
            visited.put(key, path);
        }
        return path;
    }

    private static Path visitMin(int i, int j)
    {
        int point = grid[i][j];
        Path bestPath = null;

        for (int l = 1; l <= 4; l++)
        {

            int raise = (int) Math.pow(-1, l / 2);
            int currentRow = l < 3 ? (i + raise) : i;
            int currentColumn = l > 2 ? (j + raise) : j;
            if (currentRow < 0 || currentRow > x - 1 || currentColumn < 0 || currentColumn > y - 1)
            {
                continue;
            }
            int neighbour = grid[currentRow][currentColumn];
            if (neighbour >= point)
            {
                continue;
            }
            Path path = getPath(currentRow, currentColumn);
            bestPath = determineBestPath(bestPath, path);
        }

        return bestPath == null ? new Path(null, point, 1, point) : new Path(bestPath, point, bestPath.getPathLength() + 1, bestPath.getEnd());
    }

    private static Path determineBestPath(Path currentBest, Path path)
    {
        if (currentBest == null || currentBest.getPathLength() < path.getPathLength())
        {
            return path;
        }
        if (currentBest.getPathLength() > path.getPathLength())
        {
            return currentBest;
        }

        return ((currentBest.getPoint() - currentBest.getEnd()) > (path.getPoint() - path.getEnd())) ? currentBest : path;

    }

}