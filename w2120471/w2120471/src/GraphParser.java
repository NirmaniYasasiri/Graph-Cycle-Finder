/*
 * Name: Nirmani Yasasiri
 * Student w2120471/20231489
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GraphParser {

    // This method reads a graph from a file and converts it into a DirectedGraph object
    public static DirectedGraph parseFromFile(String fileName) throws IOException {
        // Create an empty directed graph
        DirectedGraph graph = new DirectedGraph();

        // Open the file for reading
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        int lineNumber = 0;

        // Read the first line, which should contain the number of vertices
        line = reader.readLine();
        lineNumber++;

        // Check if the file is empty or the first line is blank
        if (line == null || line.trim().isEmpty()) {
            reader.close();
            throw new IOException("Input File is Empty!!");
        }

        int numberOfVertices;

        // Try to convert the first line into an integer
        try {
            numberOfVertices = Integer.parseInt(line.trim());
        } catch (NumberFormatException e) {
            // If conversion fails, throw an error saying the number of vertices is invalid
            reader.close();
            throw new IOException("Invalid Number of Vertices at Line 1: " + line);
        }

        // Add vertices from 0 up to numberOfVertices - 1 into the graph
        for (int i = 0; i < numberOfVertices; i++) {
            graph.addVertex(i);
        }

        // Read the remaining lines, where each line represents one edge
        while ((line = reader.readLine()) != null) {
            lineNumber++;
            line = line.trim();

            // Skip empty lines in the file
            if (line.isEmpty()) {
                continue;
            }

            // Split the line into parts using spaces
            String[] parts = line.split("\\s+");

            // Each edge line must contain exactly 2 values: from and to
            if (parts.length != 2) {
                reader.close();
                throw new IOException("Invalid Edge Format at Line " + lineNumber + ": " + line);
            }

            try {
                // Convert the two parts into integers
                int from = Integer.parseInt(parts[0]);
                int to = Integer.parseInt(parts[1]);

                // Add the edge to the graph
                graph.addEdge(from, to);
            } catch (NumberFormatException e) {
                // If the values are not valid integers, throw an error
                reader.close();
                throw new IOException("Invalid Integers at Line " + lineNumber + ": " + line);
            }
        }

        // Close the reader after finishing reading the file
        reader.close();

        // Return the completed graph
        return graph;
    }
}