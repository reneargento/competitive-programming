package algorithms.graph;

import java.util.*;

/**
 * Created by Rene Argento on 04/11/19.
 */
// O(V + E) algorithm, faster than the traditional breadth-first search but requires access to both source and target nodes.
public class BidirectionalBreadthFirstSearch {

    public class PathNode {
        private int id;
        private PathNode previousNode;

        public PathNode(int id, PathNode previousNode) {
            this.id = id;
            this.previousNode = previousNode;
        }

        public int getID() {
            return id;
        }

        public LinkedList<Integer> collapse(boolean startsWithRoot) {
            LinkedList<Integer> path = new LinkedList<>();
            PathNode node = this;

            while (node != null) {
                if (startsWithRoot) {
                    path.addLast(node.id);
                } else {
                    path.addFirst(node.id);
                }
                node = node.previousNode;
            }
            return path;
        }
    }

    public class BFSData {
        public Queue<PathNode> toVisit;
        public Map<Integer, PathNode> visited;

        public BFSData(int id) {
            toVisit = new LinkedList<>();
            visited = new HashMap<>();
            PathNode pathSource = new PathNode(id, null);
            toVisit.add(pathSource);
            visited.put(id, pathSource);
        }

        public boolean isFinished() {
            return toVisit.isEmpty();
        }
    }

    public List<Integer> findPathBidirectionalBFS(List<Integer>[] adjacent, int source, int target) {
        BFSData sourceData = new BFSData(source);
        BFSData targetData = new BFSData(target);

        while (!sourceData.isFinished() && !targetData.isFinished()) {
            // Search from source
            int collisionID = searchLevel(adjacent, sourceData, targetData);
            if (collisionID != -1) {
                return mergePaths(sourceData, targetData, collisionID);
            }

            // Search from target
            collisionID = searchLevel(adjacent, targetData, sourceData);
            if (collisionID != -1) {
                return mergePaths(sourceData, targetData, collisionID);
            }
        }
        return null;
    }

    // Search one level and return a collision, if any
    private int searchLevel(List<Integer>[] adjacent, BFSData primaryData, BFSData secondaryData) {
        int nodesInLevelCount = primaryData.toVisit.size();

        for (int i = 0; i < nodesInLevelCount; i++) {
            PathNode pathNode = primaryData.toVisit.poll();
            int id = pathNode.getID();

            if (secondaryData.visited.containsKey(id)) {
                return id;
            }

            for (int neighbor : adjacent[id]) {
                if (!primaryData.visited.containsKey(neighbor)) {
                    PathNode next = new PathNode(neighbor, pathNode);
                    primaryData.visited.put(neighbor, next);
                    primaryData.toVisit.add(next);
                }
            }
        }
        return -1;
    }

    private List<Integer> mergePaths(BFSData sourceBFSData, BFSData targetBFSData, int connectionID) {
        PathNode endNode1 = sourceBFSData.visited.get(connectionID);
        PathNode endNode2 = targetBFSData.visited.get(connectionID);

        LinkedList<Integer> pathOne = endNode1.collapse(false);
        LinkedList<Integer> pathTwo = endNode2.collapse(true);

        pathTwo.removeFirst(); // Remove connection ID
        pathOne.addAll(pathTwo);
        return pathOne;
    }

}