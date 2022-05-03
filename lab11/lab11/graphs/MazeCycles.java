package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private boolean cycleFound = false;
    private boolean drawEdge = false;
    private Maze maze;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        s = 0;
    }

    @Override
    public void solve() {
        dfsCycle(s, s);
    }

    // Helper methods go here
    private void dfsCycle(int v, int p) {
        marked[v] = true;
        announce();
        for (int w : maze.adj(v)) {
            if (marked[w] && w != p) {
                edgeTo[w] = v;
                announce();
                cycleFound = true;
                break;
            } else if (!marked[w]) {
                dfsCycle(w, v);
                if (cycleFound) {
                    if (!drawEdge) {
                        if (edgeTo[w] == Integer.MAX_VALUE) {
                            edgeTo[w] = v;
                            announce();
                        } else {
                            drawEdge = true;
                        }
                    }
                    break;
                }
            }
        }
    }
}

