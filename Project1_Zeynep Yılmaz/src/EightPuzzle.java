import java.awt.event.KeyEvent; // for the constants of the keys on the keyboard
import java.awt.Color; // used for coloring the tile and the number on it
import java.awt.Font;
import javax.swing.*;

// A program that partially implements the 8 puzzle.
public class EightPuzzle {
   // The main method is the entry point where the program starts execution.
   public static void main(String[] args) {
      // StdDraw setup
      // -----------------------------------------------------------------------
      // set the size of the canvas (the drawing area) in pixels
      StdDraw.setCanvasSize(500, 500);
      // set the range of both x and y values for the drawing canvas
      StdDraw.setScale(0.5,  3.5);
      // enable double buffering to animate moving the tiles on the board
      StdDraw.enableDoubleBuffering();

      // create a random board for the 8 puzzle
      Board board = new Board();

      // Solve the puzzle using both heuristic functions
      PuzzleSolver_ManhattanDistance manhattanSolver = new PuzzleSolver_ManhattanDistance(board);
      PuzzleSolver_MisplacedTiles misplacedTilesSolver = new PuzzleSolver_MisplacedTiles(board);

      final String[] solutions = new String[2];

      try {
         solutions[0] = manhattanSolver.solve();
     } catch (PuzzleSolver_ManhattanDistance.UnsolvablePuzzleException e) {
         solutions[0] = "Unsolvable";
     }

     try {
         solutions[1] = misplacedTilesSolver.solve();
     } catch (PuzzleSolver_MisplacedTiles.UnsolvablePuzzleException e) {
         solutions[1] = "Unsolvable";
     }

     SolutionDisplay.displaySolutions(solutions);

      // The main animation and user interaction loop
      // -----------------------------------------------------------------------
      while (true) {
         // draw the board, show the resulting drawing and pause for a short time
         board.draw();
         
         StdDraw.show();
         StdDraw.pause(100); // 100 ms
         // if the user has pressed the right arrow key on the keyboard
         if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT))
            // move the empty cell right
            board.moveRight();
         // if the user has pressed the left arrow key on the keyboard
         if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT))
            // move the empty cell left
            board.moveLeft();
         // if the user has pressed the up arrow key on the keyboard
         if (StdDraw.isKeyPressed(KeyEvent.VK_UP))
            // move the empty cell up
            board.moveUp();
         // if the user has pressed the down arrow key on the keyboard
         if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN))
            // move the empty cell down
            board.moveDown();
      }
   }
   
}