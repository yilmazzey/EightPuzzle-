public class State implements Comparable<State> {
    private Board board;
    private State parent;
    private String action; 
    private int g; 
    private int h; 

    public State(Board board, State parent, String action, int g, int h) {
        this.board = board;
        this.parent = parent;
        this.action = action;
        this.g = g;
        this.h = h;
    }

    public int f() {
        return g + h;
    }

   //Getter methods to manage the node and corresponding h and g values
    public Board getBoard() {
        return board;
    }

    public State getParent() {
        return parent;
    }

    public String getAction() {
        return action;
    }

    public int getG() {
        return g;
    }

    public int getH() {
        return h;
    }

    // Implement the compareTo method to allow this object to be used in a priority queue
    @Override
    public int compareTo(State other) {
        return this.f() - other.f();
    }

}

