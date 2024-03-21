import java.util.*;

/*Defines the solution of the puzzle using A* algorithm
 * with  heuristic search --> manhattan distance 
 * 
 */

public class PuzzleSolver_ManhattanDistance {
    //creating a board object
    private Board board;
    //constructor
    public PuzzleSolver_ManhattanDistance(Board board){
        this.board=board;
    }

    //method that uses the A* algorithm to solve the puzzle 
    public String solve() throws UnsolvablePuzzleException {
        if (!isSolvable()) {
            throw new UnsolvablePuzzleException("The puzzle does not have a solution.");
        }
        PriorityQueue<State> openList = new PriorityQueue<>();
        Set<Board> closedList = new HashSet<>();
        Map<Board, State> stateMap = new HashMap<>();

        State initialState = new State(board, null, null, 0, manhattanDistanceHeuristic(board));
        openList.add(initialState);
        stateMap.put(board, initialState);

        while (!openList.isEmpty()) {
            State currentState = openList.poll();
            Board currentBoard = currentState.getBoard();

            // If the current state is the goal
            if (currentBoard.isGoal()) {
                return reconstructPath(currentState);
            }

            closedList.add(currentBoard);

            for (String move : currentBoard.possibleMoves()) {
                Board nextBoard = currentBoard.move(move);
                if (nextBoard == null || closedList.contains(nextBoard)) {
                    continue;
                }

                int tentativeG = currentState.getG() + 1; // Assuming a cost of 1 per move
                int h = manhattanDistanceHeuristic(nextBoard);
                State nextState = new State(nextBoard, currentState, move, tentativeG, h);

                if (!stateMap.containsKey(nextBoard) || tentativeG < stateMap.get(nextBoard).getG()) {
                    openList.add(nextState);
                    stateMap.put(nextBoard, nextState);
                }
            }
        }
        throw new UnsolvablePuzzleException("The puzzle is unsolvable.");
    }



    //before we implement the A* algorithm we need to check if the puzzle is solvable or not.
    //"It is not possible to solve an instance of 8 puzzle if number of inversions is odd in the input state"
    //the empty tile is not counted
    public boolean isSolvable(){
        //for this we will be flatten the board to 1d array, exclude the empty tile and count the inversions
        //if inversions odd==>return false. if inversions even ==> return true
        int[] Board_1d= new int[8];
        int index=0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tile tile = board.getTile(i, j);
                if (tile != null) {
                    Board_1d[index++] = tile.getNumber();
                }
            }
        } 
        // counting the number of inversions. if pairs i,j (i<j) is arr[j]<arr[i] we will be counting this as an inversion
        int inversions = 0;
        for (int i = 0; i < Board_1d.length - 1; i++) {
            for (int j = i + 1; j < Board_1d.length; j++) {
                if (Board_1d[i] > Board_1d[j]) {
                    inversions++;
                }
            }
        }
        return inversions % 2==0;
    }

    // Method to reconstruct the path from the goal state to the initial state
    private String reconstructPath(State goalState) {
        LinkedList<String> path = new LinkedList<>();
        State current = goalState;
        while (current.getAction() != null) { // We use getAction() here because the initial state's action is null
            path.addFirst(current.getAction());
            current = current.getParent();
        }
        return String.join(" ", path);
    }

    //manhattan distance heuristic
    public int manhattanDistanceHeuristic(Board board) {
        int totalManhattanDistance = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Tile tile = board.getTile(row, col);
                if (tile != null && tile.getNumber() != 0) {
                    //tiles should be in ascending order starting form the top left corner
                    //if tile number is 8 the goalRow should be 2 (index starting from 0) . 7/3 returns 2
                    int goalRow = (tile.getNumber() - 1) / 3;
                    //if tile number is 8 goalCol should be 2. 7%3 is 2
                    int goalCol = (tile.getNumber() - 1) % 3;
                    totalManhattanDistance += Math.abs(goalRow - row) + Math.abs(goalCol - col);
                }
            }
        }
        return totalManhattanDistance;
    }
    
    
    class UnsolvablePuzzleException extends Exception {
        public UnsolvablePuzzleException(String message) {
            super(message);
        }
    }
}