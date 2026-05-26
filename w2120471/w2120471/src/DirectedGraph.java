/*
 * Name: Nirmani Yasasiri
 * Student w2120471/20231489
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DirectedGraph {

    // Stores the graph as an adjacency list
    // Key = vertex, Value = set of outgoing neighbouring vertices
    private final Map<Integer, Set<Integer>> adjacencyList;

    // Constructor initializes the adjacency list
    public DirectedGraph() {
        adjacencyList = new HashMap<>();
    }

    // Adds a vertex to the graph if it does not already exist
    public void addVertex(int vertex) {
        adjacencyList.putIfAbsent(vertex, new HashSet<>());
    }

    // Adds a directed edge from one vertex to another
    public void addEdge(int from, int to) {
        // Make sure both vertices exist in the graph
        addVertex(from);
        addVertex(to);

        // Add 'to' as an outgoing neighbour of 'from'
        adjacencyList.get(from).add(to);
    }

    // Returns all vertices in the graph
    public Set<Integer> getVertices() {
        return adjacencyList.keySet();
    }

    // Returns all outgoing neighbours of a given vertex
    public Set<Integer> getOutgoingNeighbors(int vertex) {
        return adjacencyList.getOrDefault(vertex, new HashSet<>());
    }

    // Checks whether the graph is empty
    public boolean isEmpty() {
        return adjacencyList.isEmpty();
    }

    // Finds and returns a sink vertex
    // A sink is a vertex with no outgoing edges
    public Integer findSink() {
        for (Integer vertex : adjacencyList.keySet()) {
            if (adjacencyList.get(vertex).isEmpty()) {
                return vertex;
            }
        }
        return null;
    }

    // Removes a vertex from the graph
    public void removeVertex(int vertex) {
        // Remove the vertex itself from the adjacency list
        adjacencyList.remove(vertex);

        // Remove the vertex from all neighbour sets
        for (Set<Integer> neighbors : adjacencyList.values()) {
            neighbors.remove(vertex);
        }
    }

    // Creates and returns a deep copy of the graph
    public DirectedGraph copy() {
        DirectedGraph copiedGraph = new DirectedGraph();

        // Copy all vertices first
        for (Integer vertex : adjacencyList.keySet()) {
            copiedGraph.addVertex(vertex);
        }

        // Copy all edges
        for (Map.Entry<Integer, Set<Integer>> entry : adjacencyList.entrySet()) {
            Integer from = entry.getKey();
            for (Integer to : entry.getValue()) {
                copiedGraph.addEdge(from, to);
            }
        }

        return copiedGraph;
    }

    // Prints the graph as an adjacency list
    public void printGraph() {
        System.out.println("Graph Adjacency List:");
        for (Integer vertex : adjacencyList.keySet()) {
            System.out.println(vertex + " -> " + adjacencyList.get(vertex));
        }
    }
}