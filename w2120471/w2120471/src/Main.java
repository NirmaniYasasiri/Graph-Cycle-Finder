/*
 * Name: Nirmani Yasasiri
 * Student ID: w2120471 / 20231489
 */

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    // Main method where program execution starts
    public static void main(String[] args) {
        // Scanner is used to read file path input from the user
        Scanner scanner = new Scanner(System.in);

        // Infinite loop to allow testing multiple files until user exits
        while (true) {
            String fileName;

            // If a file name is given as a command-line argument, use it first
            if (args.length == 1) {
                fileName = args[0];
                args = new String[0]; // Clear args so next loop asks user for input
            } else {
                // Ask user to enter the file path manually
                System.out.print("Enter the File Path (or type exit to stop):");
                fileName = scanner.nextLine().trim();
            }

            // End the program if the user types "exit"
            if (fileName.equalsIgnoreCase("exit")) {
                System.out.println("Program ended.");
                break;
            }

            // Remove quotation marks if the file path is surrounded by quotes
            if (fileName.startsWith("\"") && fileName.endsWith("\"")) {
                fileName = fileName.substring(1, fileName.length() - 1);
            }

            try {
                // Parse the file and build the original graph
                DirectedGraph originalGraph = GraphParser.parseFromFile(fileName);

                // Display the original graph
                System.out.println("\nOriginal Graph Loaded From The File: " + fileName);
                originalGraph.printGraph();
                System.out.println();

                // Create a copy of the graph for sink elimination
                // This keeps the original graph unchanged for cycle finding
                DirectedGraph workingGraph = originalGraph.copy();

                // Check whether the graph is acyclic using sink elimination
                boolean acyclic = isAcyclicBySinkElimination(workingGraph);

                System.out.println();

                if (acyclic) {
                    // If all vertices can be removed by sink elimination, graph is acyclic
                    System.out.println("Output: YES - The graph is acyclic !!");
                } else {
                    // If sink elimination fails, the graph contains a cycle
                    System.out.println("Output: NO - The graph is not acyclic !!");

                    // Use CycleFinder to identify and reconstruct one cycle
                    CycleFinder cycleFinder = new CycleFinder();
                    List<Integer> cycle = cycleFinder.findCycle(originalGraph);

                    // Print the detected cycle if found
                    if (!cycle.isEmpty()) {
                        System.out.print("Cycle Found: ");
                        for (int i = 0; i < cycle.size(); i++) {
                            System.out.print(cycle.get(i));
                            if (i < cycle.size() - 1) {
                                System.out.print(" -> ");
                            }
                        }
                        System.out.println();
                    } else {
                        // If no cycle could be reconstructed
                        System.out.println("No cycle could be reconstructed !!");
                    }
                }

            } catch (IOException e) {
                // Handle file reading or parsing errors
                System.out.println("Error Reading File: " + e.getMessage());
            }

            // Separator message before testing another file
            System.out.println("\n-------------------------------------");
            System.out.println("*** You Can Test Another File Now ***");
        }

        // Close the scanner before program ends
        scanner.close();
    }

    // Checks whether a graph is acyclic using the sink elimination algorithm
    public static boolean isAcyclicBySinkElimination(DirectedGraph graph) {
        System.out.println("Running Sink Elimination...");

        // Continue until all vertices are removed
        while (!graph.isEmpty()) {
            // Find a sink vertex (a vertex with no outgoing edges)
            Integer sink = graph.findSink();

            // If no sink exists, the graph contains a cycle
            if (sink == null) {
                System.out.println("No sink found. The graph contains a Cycle !!");
                return false;
            }

            // Remove the sink from the graph
            System.out.println("Sink Found and Removed: " + sink);
            graph.removeVertex(sink);
        }

        // If all vertices are removed, the graph is acyclic
        System.out.println("All vertices removed Successfully !!");
        return true;
    }
}