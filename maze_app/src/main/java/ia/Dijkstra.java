package ia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dijkstra {
    private Integer[][] graph;
    private int n;
    private List<Integer> distanceList;
    private List<Integer> parentList;

    public Dijkstra(Integer[][] graph, int source) {
        this.graph = graph;
        this.n = graph.length;
        this.distanceList = new ArrayList<>();
        this.parentList = new ArrayList<>();
        runDijkstra(source);
    }

    public void runDijkstra(int source) {
        int[] distance = new int[n];
        int[] parent = new int[n];
        boolean[] visited = new boolean[n];

        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(visited, false);

        distance[source] = 0;
        parent[source] = source;


        for (int count = 0; count < n - 1; count++) {
            int u = findMinDistanceVertex(distance, visited);
            visited[u] = true;

            for (int v = 0; v < n; v++) {
                if (!visited[v] && graph[u][v] != 0 && distance[u] != Integer.MAX_VALUE && distance[u] + graph[u][v] < distance[v]) {
                    distance[v] = distance[u] + graph[u][v];
                    parent[v]=u;
                }
            }
        }

        distanceList.addAll(Arrays.asList(Arrays.stream(distance).boxed().toArray(Integer[]::new)));
        parentList.addAll(Arrays.asList(Arrays.stream(parent).boxed().toArray(Integer[]::new)));
    }

    private int findMinDistanceVertex(int[] distance, boolean[] visited) {
        int minDistance = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int v = 0; v < n; v++) {
            if (!visited[v] && distance[v] <= minDistance) {
                minDistance = distance[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

    public List<Integer> getDistanceList() {
        return distanceList;
    }

    public List<Integer> getParentList() {
        return parentList;
    }
}
