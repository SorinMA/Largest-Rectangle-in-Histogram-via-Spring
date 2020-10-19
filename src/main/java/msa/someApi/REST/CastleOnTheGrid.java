package msa.someApi.REST;

import org.springframework.web.bind.annotation.RestController;

import msa.someApi.REST.RESTObjects.CastleOnTheGridInput;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.*;

@RestController
public class CastleOnTheGrid {
    @RequestMapping("/castleOnTheGrid")
	public String castleOnTheGrid() throws IOException {
		return "You are given a square grid with some cells open (.) and some blocked (X). Your playing piece can move along any row or column until it reaches the edge of the grid or a blocked cell. Given a grid, a start and an end position, determine the number of moves it will take to get to the end position.";
	}
    
    /**
     * 
     * @param CastelOnTheGridInput
     * @return integer
     * example of input:
        {
            "grid": [".X.", ".X.", "..."], 
            "startX": 0,
            "startY": 0,
            "goalX": 0,
            "goalY": 2
        }
        Output for this  example: 3;
     */

    @RequestMapping(value = "/castleOnTheGrid", method = RequestMethod.POST)
	public int castleOnTheGrid(@RequestBody CastleOnTheGridInput input) throws IOException {
        String[] grid;
        int startX;
        int startY;
        int goalX;
        int goalY;

        try {
            grid = (String[]) input.getGrid();
            startX = (int) input.getStartX();
            startY = (int) input.getStartY();
            goalX = (int) input.getGoalX();
            goalY = (int) input.getGoalY();
        } catch (Exception e) {
            return -1;
        }
        
        int rowsGrig = grid.length;
        int colsGrid = grid[0].length();
        int[][] newGrid = new int[rowsGrig + 1][colsGrid + 1]; // formatig the grid (0 for ok -1 for X)

        for (int i = 0 ; i < rowsGrig ; i ++ ) {
            for (int j = 0 ; j < colsGrid ; j++ ) {
                if (grid[i].charAt(j) == 'X')
                    newGrid[i][j] = -1;
            }
        }

        class Node { // data structure to store our Nodes
            int x;
            int y;

            Node () {
                this.x = 0;
                this.y = 0;
            }

            Node (int x, int y) {
                this.x = x;
                this.y = y;
            }

            boolean cmpNodes (Node n) {
                if (this.x == n.x && this.y == n.y) {
                    return true;
                }
                return false;
            }

            public String toString() { // to print our nodes
                return "(X: " + this.x + ", Y: " + this.y  + ") ";
            }
        }

        Node start = new Node(startX, startY); // start
        Node stop = new Node(goalX, goalY); // stop

        ArrayList<Node> toVisit = new ArrayList<Node>(); // to visit : to see what at this moment we need to visit
        toVisit.add(start);
        int steps = 0; // how meny steps we did from start to stop

        while (toVisit.size() != 0) {
            ArrayList<Node> newToVisit = new ArrayList<Node>(); // to visit : to see what to visit in future

            for (Node node : toVisit) {
                if (node.cmpNodes(stop) == true) // if we found our dear node
                    return steps;

                int x = node.x;
                int y = node.y;

                newGrid[x][y] = -1; // mark this node as seen (aka we cannot reuse it)

                for (int i = x - 1 ; i >= 0 ; i -= 1) { // add the nodes who are before X
                    if (newGrid[i][y] != -1)
                        newToVisit.add(new Node(i, y));
                    else // if we hit a wall (aka X) we stop, we cannot go ruther
                        break;
                }

                for (int i = x + 1 ; i < rowsGrig ; i += 1) { // add the nodes who are after X
                    if (newGrid[i][y] != -1)
                        newToVisit.add(new Node(i, y));
                    else // if we hit a wall (aka X) we stop, we cannot go ruther
                        break;
                }

                for (int i = y - 1 ; i >= 0 ; i -= 1) { // add the nodes who are before Y
                    if (newGrid[x][i] != -1)
                        newToVisit.add(new Node(x, i));
                    else // if we hit a wall (aka X) we stop, we cannot go ruther
                        break;
                }

                for (int i = y + 1 ; i < colsGrid ; i += 1) { // add the nodes who are after Y
                    if (newGrid[x][i] != -1)
                        newToVisit.add(new Node(x, i));
                    else // if we hit a wall (aka X) we stop, we cannot go ruther
                        break;
                }
            }

            toVisit = newToVisit;
            steps += 1;
        }

        return -1;
    }

}
