package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(t) - maze.toX(v)) + Math.abs(maze.toY(t) - maze.toY(v));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        ExtrinsicPQ<Integer> pq = new ArrayHeap<>();
        pq.insert(s, distTo[s]);
        while (pq.size() != 0) {
            int v = pq.removeMin();
            marked[v] = true;
            announce();

            if (v == t) {
                break;
            }

            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    if (distTo[w] == Integer.MAX_VALUE) {
                        distTo[w] = distTo[v] + 1;
                        edgeTo[w] = v;
                        int priority = distTo[w] + h(w);
                        pq.insert(w, priority);
                    } else {
                        if (distTo[w] > distTo[v] + 1) {
                            distTo[w] = distTo[v] + 1;
                            edgeTo[w] = v;
                            int priority = distTo[w] + h(w);
                            pq.changePriority(w, priority);
                        }
                    }

                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

