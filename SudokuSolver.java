import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuSolver {
    private JFrame frame;
    private JPanel panel;
    private JTextField[][] fields = new JTextField[9][9];
    private boolean solving = false;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                SudokuSolver window = new SudokuSolver();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public SudokuSolver() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Sudoku Solver");
        frame.setBounds(100, 100, 450, 520);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(9, 9));

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                fields[i][j] = new JTextField();
                fields[i][j].setHorizontalAlignment(JTextField.CENTER);
                fields[i][j].setFont(new Font("Tahoma", Font.BOLD, 18));
                panel.add(fields[i][j]);
            }
        }

        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solving = true;
                int[][] grid = new int[9][9];
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        String text = fields[i][j].getText();
                        if (text.equals("")) {
                            grid[i][j] = 0;
                        } else {
                            grid[i][j] = Integer.parseInt(text);
                        }
                    }
                }
                if (solveSudoku(grid)) {
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            fields[i][j].setText(String.valueOf(grid[i][j]));
                        }
                    }
                    JOptionPane.showMessageDialog(frame, "Sudoku solved!");
                } else {
                    JOptionPane.showMessageDialog(frame, "No solution found.");
                }
                solving = false;
            }
        });
        frame.getContentPane().add(solveButton, BorderLayout.SOUTH);
    }

    private boolean solveSudoku(int[][] grid) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(grid, i, j, num)) {
                            grid[i][j] = num;
                            if (solveSudoku(grid)) {
                                return true;
                            }
                            grid[i][j] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int[][] grid, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == num) {
                return false;
            }
        }

        for (int i = 0; i < 9; i++) {
            if (grid[i][col] == num) {
                return false;
            }
        }

        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[startRow + i][startCol + j] == num) {
                    return false;
                }
            }
        }

        return true;
    }
}