import java.util.*;


/*Defines the solution of the puzzle using A* algorithm
 * with two different heuristic search --> manhattan distance & displaced tiles
 */

public class PuzzleSolver_MisplacedTiles {
    //creating a board object
    private Board board;
    public PuzzleSolver_MisplacedTiles(Board board){
        this.board=board;
    }

    //method that uses the A* algorithm to solve the puzzle 
    public String solve() throws UnsolvablePuzzleException {
        if (!isSolvable()) {
            throw new UnsolvablePuzzleException("The puzzle does not have a solution.");
        }
        PriorityQueue<State> openList = new PriorityQueue<>();
        Set <Board> closedList = new HashSet<>();
        Map<Board, State> stateMap = new HashMap<>();

        State initialState = new State(board, null, null, 0, misplacedTilesHeuristic(board));
        openList.add(initialState);
        stateMap.put(board, initialState);

        while (!openList.isEmpty()) {
            State currentState = openList.poll();
            Board currentBoard = currentState.getBoard();

            if (currentBoard.isGoal()) {
                return reconstructPath(currentState);
            }

            closedList.add(currentBoard);

            for (String move : currentBoard.possibleMoves()) {
                Board nextBoard = currentBoard.move(move);
                if (nextBoard == null || closedList.contains(nextBoard)) {
                    continue;
                }

                int tentativeG = currentState.getG() + 1;
                int h = misplacedTilesHeuristic(nextBoard);
                State nextState = new State(nextBoard, currentState, move, tentativeG, h);

                if (!stateMap.containsKey(nextBoard) || tentativeG < stateMap.get(nextBoard).getG()) {
                    openList.add(nextState);
                    stateMap.put(nextBoard, nextState);
                }
            }
        }
        throw new UnsolvablePuzzleException("The puzzle cannot be solved.");
    }

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


    //misplaced tiles heuristic function 
    public int misplacedTilesHeuristic(Board board) {
        int misplaced = 0;
        int goalNumber = 1;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Tile tile = board.getTile(row, col);
                if (tile != null && tile.getNumber() != goalNumber) {
                    misplaced++;
                }
                //calculating the next expected number
                //using the modulo to go back to 1 after 8
                goalNumber = (goalNumber % 8) + 1; 
            }
        }
        return misplaced;
    }

    private String reconstructPath(State goalState) {
        LinkedList<String> path = new LinkedList<>();
        State current = goalState;
        while (current != null && current.getAction() != null) {
            path.addFirst(current.getAction());
            current = current.getParent();
        }
        return String.join(" ", path);
    }
    
    
    class UnsolvablePuzzleException extends Exception {
        public UnsolvablePuzzleException(String message) {
            super(message);
        }
    }
}