package helper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class to find the Dfs.
 */
public class Dfs {

  /**
   * dfs.
   * @param root root.
   * @param adjacencyLists adjacencyLists.
   * @param visited visited.
   * @param res res.
   * @return List.
   */
  private List<Integer> dfs(int root, Map<Integer, List<Integer>> adjacencyLists,
                            Set<Integer> visited, List<Integer> res) {
    visited.add(root);
    res.add(root);

    for (int adjacentNode : adjacencyLists.get(root)) {
      if (!visited.contains(adjacentNode)) {
        dfs(adjacentNode, adjacencyLists, visited, res);
      }
    }
    return res;
  }

  /**
   * Perform dfs.
   * @param adjacencyLists adjacencyLists.
   * @return List.
   */
  public List<Integer> performDfs(Map<Integer, List<Integer>> adjacencyLists) {
    Set<Integer> visited = new HashSet<>();
    List<Integer> res = new ArrayList<>();
    for (int node : adjacencyLists.keySet()) {
      if (!visited.contains(node)) {
        dfs(node, adjacencyLists, visited, res);
      }
    }
    return res;
  }
}
