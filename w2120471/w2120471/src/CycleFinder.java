/*
 * Name: Nirmani Yasasiri
 * Student w2120471/20231489
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CycleFinder {

    // Stores all vertices that have already been visited during DFS
    private final Set<Integer> visited;

    // Stores vertices currently in the recursion stack
    // This helps to detect back edges, which indicate a cycle
    private final Set<Integer> recursionStack;

    // Stores the parent of each vertex so the cycle path can be reconstructed
    private final Map<Integer, Integer> parent;

    // Stores the detected cycle
    private List<Integer> cycle;

    // Constructor initializes all data structures
    public CycleFinder() {
        visited = new HashSet<>();
        recursionStack = new HashSet<>();
        parent = new HashMap<>();
        cycle = new ArrayList<>();
    }

    // Finds and returns a cycle in the graph if one exists
    public List<Integer> findCycle(DirectedGraph graph) {
        // Go through each vertex in the graph
        for (Integer vertex : graph.getVertices()) {
            // Start DFS only if the vertex has not been visited yet
            if (!visited.contains(vertex)) {
                if (dfs(graph, vertex)) {
                    // If a cycle is found, return it
                    return cycle;
                }
            }
        }
        // Return an empty list if no cycle exists
        return new ArrayList<>();
    }

    // Performs Depth-First Search to detect a cycle
    private boolean dfs(DirectedGraph graph, Integer current) {
        // Mark current vertex as visited
        visited.add(current);

        // Add current vertex to the recursion stack
        recursionStack.add(current);

        // Check all outgoing neighbours of the current vertex
        for (Integer neighbor : graph.getOutgoingNeighbors(current)) {
            // If neighbour has not been visited, continue DFS
            if (!visited.contains(neighbor)) {
                // Store current as the parent of neighbour
                parent.put(neighbor, current);

                if (dfs(graph, neighbor)) {
                    return true;
                }
            }
            // If neighbour is already in the recursion stack, a cycle is found
            else if (recursionStack.contains(neighbor)) {
                buildCycle(current, neighbor);
                return true;
            }
        }

        // Remove current vertex from recursion stack after exploring all neighbours
        recursionStack.remove(current);
        return false;
    }

    // Reconstructs the cycle path using the parent map
    private void buildCycle(Integer current, Integer start) {
        List<Integer> tempCycle = new ArrayList<>();

        // Add the starting vertex of the cycle
        tempCycle.add(start);

        Integer node = current;

        // Move backwards using parent links until reaching the start again
        while (node != null && !node.equals(start)) {
            tempCycle.add(0, node);
            node = parent.get(node);
        }

        // Add the start again at the end to show the full cycle
        tempCycle.add(start);

        // Save the completed cycle
        cycle = tempCycle;
    }
}