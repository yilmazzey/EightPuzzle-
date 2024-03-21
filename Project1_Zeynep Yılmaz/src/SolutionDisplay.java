import javax.swing.*;
import java.awt.*;

public class SolutionDisplay extends JFrame {

    public SolutionDisplay(String solutionManhattan, String solutionMisplacedTiles) {
        setTitle("8 Puzzle Solutions");
        JPanel panel = new JPanel(new GridLayout(4, 1)); 
        getContentPane().add(panel);

        JLabel labelManhattan = new JLabel("Solution using Manhattan Distance:");
        panel.add(labelManhattan);

        JTextArea textAreaManhattan = new JTextArea(5, 30); 
        textAreaManhattan.setText(solutionManhattan);
        textAreaManhattan.setEditable(false); 
        JScrollPane scrollPaneManhattan = new JScrollPane(textAreaManhattan);
        panel.add(scrollPaneManhattan);
        
        JLabel labelMisplacedTiles = new JLabel("Solution using Misplaced Tiles:");
        panel.add(labelMisplacedTiles);

        JTextArea textAreaMisplacedTiles = new JTextArea(5, 30); 
        textAreaMisplacedTiles.setText(solutionMisplacedTiles);
        textAreaMisplacedTiles.setEditable(false); 
        JScrollPane scrollPaneMisplacedTiles = new JScrollPane(textAreaMisplacedTiles);
        panel.add(scrollPaneMisplacedTiles);


        setSize(400, 400); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void displaySolutions(String[] solutions) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SolutionDisplay(solutions[0], solutions[1]);
            }
        });
    }
}
