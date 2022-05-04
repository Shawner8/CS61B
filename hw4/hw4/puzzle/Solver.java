package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

public class Solver {

    private static class SearchNode implements Comparable<SearchNode> {
        public WorldState worldState;
        public int moves;
        public SearchNode prev;
        int priority;

        public SearchNode (WorldState w, int m, SearchNode ref, int p) {
            worldState = w;
            moves = m;
            prev = ref;
            priority = p;
        }

        @Override
        public int compareTo (SearchNode o) {
            return Integer.compare(priority, o.priority);
        }
    }

    private SearchNode sentinel;
    private SearchNode goal;

    /** Constructor which solves the puzzle, computing
     *  everything necessary for moves() and solution() to
     *  not have to solve the problem again. Solves the
     *  puzzle using the A* algorithm. Assumes a solution exists.
     *  A* Tree Search implementation
     */
    public Solver(WorldState initial) {
        Map<WorldState, Integer> cache = new HashMap<>();

        sentinel = new SearchNode(null, Integer.MAX_VALUE, null, Integer.MAX_VALUE);
        SearchNode init = new SearchNode(initial, 0, sentinel, 0);

        MinPQ<SearchNode> pq = new MinPQ<>();
        pq.insert(init);

        while (!pq.isEmpty()) {
            SearchNode v = pq.delMin();
            if (v.worldState.isGoal()) {
                goal = v;
                break;
            } else {
                for (WorldState w : v.worldState.neighbors()) {
                    if (!w.equals(v.prev.worldState)) {
                        int moves = v.moves + 1;
                        if (!cache.containsKey(w)) {
                            cache.put(w, w.estimatedDistanceToGoal());
                        }
                        int priority = moves + cache.get(w);
                        SearchNode n = new SearchNode(w, moves, v, priority);
                        pq.insert(n);
                    }
                }
            }
        }
    }

    /** Returns the minimum number of moves to solve the puzzle starting
     *  at the initial WorldState.
     */
    public int moves() {
        return goal.moves;
    }

    /** Returns a sequence of WorldStates from the initial WorldState
     *  to the solution.
     */
    public Iterable<WorldState> solution() {
        Deque<WorldState> seq = new LinkedList<>();
        SearchNode w = goal;
        while (w.prev != sentinel) {
            seq.addFirst(w.worldState);
            w = w.prev;
        }
        seq.addFirst(w.worldState);
        return seq;
    }
}
