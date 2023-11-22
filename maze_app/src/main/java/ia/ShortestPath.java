package ia;

import java.util.ArrayList;
import java.util.List;

public class ShortestPath {
    private List<String> path=new ArrayList<>();

    public ShortestPath(MazeToGraph graph, int firstNode, int lastNode){
        Dijkstra dij=new Dijkstra(graph.getAdjacencyMatrix(),firstNode);
        List<Integer> father=dij.getParentList();
        List<Integer> shortestPath=new ArrayList<>();
        int current_node=lastNode;
        shortestPath.add(current_node);
        while (current_node!=firstNode) {
            current_node=father.get(current_node);
            shortestPath.add(current_node);
        }
        for (int k = 0; k <shortestPath.size(); k++) {
            int node =shortestPath.get(k);
            int i=node%graph.getX();
            int j=(int) node/graph.getX();
            path.add(i+","+j);
        }
    }

    public List<String> getPath() {
        return path;
    }
}
