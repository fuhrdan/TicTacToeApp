import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeApp {
    private JFrame frame;
    private JButton[][] buttons = new JButton[9][9];
    private boolean isXTurn = true; // X goes first
    private JPanel gridPanel;
    private JButton resetButton;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToeApp::new);
    }

    public TicTacToeApp() {
        frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create a panel for the 9x9 grid
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(9, 9));
        frame.add(gridPanel, BorderLayout.CENTER);

        // Create buttons for the grid
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JButton button = new JButton("");
                button.setFont(new Font("Arial", Font.BOLD, 20));
                button.setFocusPainted(false);
                button.addActionListener(new ButtonClickListener(i, j));
                buttons[i][j] = button;
                gridPanel.add(button);
            }
        }

        // Frame settings
        frame.setSize(600, 600);
        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = buttons[row][col];
            if (!button.getText().isEmpty() || resetButton != null) {
                return; // Ignore clicks on already-filled cells or after the game ends
            }

            button.setText(isXTurn ? "X" : "O");
            button.setForeground(isXTurn ? Color.BLUE : Color.RED);
            isXTurn = !isXTurn;

            if (checkForWinner(row, col)) {
                displayWinner();
            }
        }
    }

    private boolean checkForWinner(int lastRow, int lastCol) {
        String currentPlayer = buttons[lastRow][lastCol].getText();

        // Check row
        boolean rowWin = true;
        for (int col = 0; col < 9; col++) {
            if (!buttons[lastRow][col].getText().equals(currentPlayer)) {
                rowWin = false;
                break;
            }
        }

        // Check column
        boolean colWin = true;
        for (int row = 0; row < 9; row++) {
            if (!buttons[row][lastCol].getText().equals(currentPlayer)) {
                colWin = false;
                break;
            }
        }

        // Check diagonal (top-left to bottom-right)
        boolean diagonal1Win = true;
        if (lastRow == lastCol) {
            for (int i = 0; i < 9; i++) {
                if (!buttons[i][i].getText().equals(currentPlayer)) {
                    diagonal1Win = false;
                    break;
                }
            }
        } else {
            diagonal1Win = false;
        }

        // Check diagonal (top-right to bottom-left)
        boolean diagonal2Win = true;
        if (lastRow + lastCol == 8) {
            for (int i = 0; i < 9; i++) {
                if (!buttons[i][8 - i].getText().equals(currentPlayer)) {
                    diagonal2Win = false;
                    break;
                }
            }
        } else {
            diagonal2Win = false;
        }

        return rowWin || colWin || diagonal1Win || diagonal2Win;
    }

    private void displayWinner() {
        JOptionPane.showMessageDialog(frame, "We have a winner!");
        resetButton = new JButton("Reset Game");
        resetButton.addActionListener(e -> resetGame());
        frame.add(resetButton, BorderLayout.SOUTH);
        frame.revalidate();
    }

    private void resetGame() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setForeground(Color.BLACK);
            }
        }
        isXTurn = true;
        frame.remove(resetButton);
        resetButton = null;
        frame.revalidate();
        frame.repaint();
    }
}
