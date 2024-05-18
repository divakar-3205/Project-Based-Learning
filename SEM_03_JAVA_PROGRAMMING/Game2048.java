import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
public class Game2048 {
 // Size of the game grid
 private static final int SIZE = 4;
 //Instance variables
 private int[][] grid;
 private Random random;
 private int score;
 private int highScore;
 private JFrame frame;
 private JPanel gridPanel;
 private JLabel[][] gridLabels;
 private JLabel scoreLabel;
 private JLabel highScoreLabel;
 private boolean winConditionReached;
 public Game2048() {
 // Constructor code
 grid = new int[SIZE][SIZE];
 random = new Random();
 score = 0;
 highScore = 0;
 // Create the GUI components
 frame = new JFrame(" 2048 Game");
 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 frame.setSize(750, 750);
 frame.setLayout(new BorderLayout());
 gridPanel = new JPanel(new GridLayout(SIZE, SIZE));
 gridLabels = new JLabel[SIZE][SIZE];
 for (int i = 0; i < SIZE; i++) {

 for (int j = 0; j < SIZE; j++) {
 gridLabels[i][j] = new JLabel("", JLabel.CENTER);
 gridLabels[i][j].setFont(new Font("SANS_SERIF", Font.BOLD, 24));
 gridLabels[i][j].setOpaque(true);
 gridLabels[i][j].setBackground(Color.LIGHT_GRAY);
 gridPanel.add(gridLabels[i][j]);
 }
 }
 // Add components to the frame
 frame.add(gridPanel, BorderLayout.CENTER);
 JPanel infoPanel = new JPanel(new GridLayout(2, 2));
 scoreLabel = new JLabel("SCORE: 0", JLabel.CENTER);
 highScoreLabel = new JLabel("HIGH SCORE: 0", JLabel.CENTER);
 infoPanel.add(scoreLabel);
 infoPanel.add(highScoreLabel);
 frame.add(infoPanel, BorderLayout.NORTH);
 frame.addKeyListener(new KeyAdapter() {
 // Handle key events
 public void keyPressed(KeyEvent e) {
 int keyCode = e.getKeyCode();
 if (keyCode == KeyEvent.VK_UP) {
 moveUp();
 } else if (keyCode == KeyEvent.VK_DOWN) {
 moveDown();
 } else if (keyCode == KeyEvent.VK_LEFT) {
 moveLeft();
 } else if (keyCode == KeyEvent.VK_RIGHT) {
 moveRight();
 }
 updateGridLabels();
 updateScore();
 if (isGameOver()) {
 showGameOverMessage();
 }
 }
 });
 // Set frame properties and initialize the grid
 frame.setFocusable(true);
 frame.requestFocus();
 frame.setVisible(true);
 initializeGrid();
 updateGridLabels();
 }
 public void initializeGrid() {
 // Grid initialization code
 for (int i = 0; i < SIZE; i++) {
 for (int j = 0; j < SIZE; j++) {
 grid[i][j] = 0;
 }
 }
 // Add two new numbers to random positions
 addNewNumber();
 addNewNumber();
 }
 public void addNewNumber() {
 // New number generation code
 int row, col;
 do {
 // Generate random row and column indices
 row = random.nextInt(SIZE);
 col = random.nextInt(SIZE);
 } while (grid[row][col] != 0);
 // Assign a new number (2 or 4) to the empty cell
 grid[row][col] = (random.nextInt(2) + 1) * 2;
 }
 public void updateGridLabels() {
 // Update the GUI grid labels with the current grid state
 for (int i = 0; i < SIZE; i++) {
 for (int j = 0; j < SIZE; j++) {
 if (grid[i][j] == 0) {
 gridLabels[i][j].setText("");
 gridLabels[i][j].setBackground(Color.LIGHT_GRAY);
 } else if (grid[i][j] == 2048) {
 winConditionReached = true;
 gridLabels[i][j].setText(String.valueOf(grid[i][j]));
 gridLabels[i][j].setBackground(getTileColor(grid[i][j]));
 } else {
 // Set the text of each label to the corresponding grid cell value
 gridLabels[i][j].setText(String.valueOf(grid[i][j]));
 gridLabels[i][j].setBackground(getTileColor(grid[i][j]));
 }
 // Add grid lines
 gridLabels[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY));
 }
 }
 }
 public Color getTileColor(int value) {
 //Maps each tile value to a specific color
 switch (value) {
 case 2:
 return new Color(238, 228, 218);
 case 4:
 return new Color(237, 224, 200);
 case 8:
 return new Color(242, 177, 121);
 case 16:
 return new Color(245, 149, 99);
 case 32:
 return new Color(246, 124, 95);
 case 64:
 return new Color(246, 94, 59);
 case 128:
 return new Color(237, 207, 114);
 case 256:
 return new Color(237, 204, 97);
 case 512:
 return new Color(237, 200, 80);
 case 1024:
 return new Color(237, 197, 63);
 case 2048:
 return new Color(237, 194, 46);
 default:
 return Color.WHITE;
 }
 }
 public void moveUp() {
 //Moves and merges the tiles upwards
 int prevScore = score;
 boolean moved = false;
 for (int j = 0; j < SIZE; j++) {
 int mergeValue = -1;
 for (int i = 1; i < SIZE; i++) {
 if (grid[i][j] != 0) {
 int row = i;
 while (row > 0 && (grid[row - 1][j] == 0 || grid[row - 1][j] == grid[row][j])) 
{
 if (grid[row - 1][j] == grid[row][j] && mergeValue != row - 1) {
 grid[row - 1][j] *= 2;
 score += grid[row - 1][j];
 grid[row][j] = 0;
 mergeValue = row - 1;
 moved = true;
 } else if (grid[row - 1][j] == 0) {
 grid[row - 1][j] = grid[row][j];
 grid[row][j] = 0;
 moved = true;
 }
 row--;
 }
 }
 }
 }
 if (moved) {
 addNewNumber();
 updateScore();
 }
 }
 public void moveDown() {
 //Moves and merges the tiles downwards
 int prevScore = score;
 boolean moved = false;
 for (int j = 0; j < SIZE; j++) {
 int mergeValue = -1;
 for (int i = SIZE - 2; i >= 0; i--) {
 if (grid[i][j] != 0) {
 int row = i;
 while (row < SIZE - 1 && (grid[row + 1][j] == 0 || grid[row + 1][j] == 
grid[row][j])) {
 if (grid[row + 1][j] == grid[row][j] && mergeValue != row + 1) {
 grid[row + 1][j] *= 2;
 score += grid[row + 1][j];
 grid[row][j] = 0;
 mergeValue = row + 1;
 moved = true;
 } else if (grid[row + 1][j] == 0) {
 grid[row + 1][j] = grid[row][j];
 grid[row][j] = 0;
 moved = true;
 }
 row++;
 }
 }
 }
 }
 if (moved) {
 addNewNumber();
 updateScore();
 }
 }
 public void moveLeft() {
 //Moves and merges the tiles towards left
 int prevScore = score;
 boolean moved = false;
 for (int i = 0; i < SIZE; i++) {
 int mergeValue = -1;
 for (int j = 1; j < SIZE; j++) {
 if (grid[i][j] != 0) {
 int col = j;
 while (col > 0 && (grid[i][col - 1] == 0 || grid[i][col - 1] == grid[i][col])) {
 if (grid[i][col - 1] == grid[i][col] && mergeValue != col - 1) {
 grid[i][col - 1] *= 2;
 score += grid[i][col - 1];
 grid[i][col] = 0;
 mergeValue = col - 1;
 moved = true;
 } else if (grid[i][col - 1] == 0) {
 grid[i][col - 1] = grid[i][col];
 grid[i][col] = 0;
 moved = true;
 }
 col--;
 }
 }
 }
 }
 if (moved) {
 addNewNumber();
 updateScore();
 }
 }
 public void moveRight() {
 //Moves and merges the tiles towards right
 int prevScore = score;
 boolean moved = false;
 for (int i = 0; i < SIZE; i++) {
 int mergeValue = -1;
 for (int j = SIZE - 2; j >= 0; j--) {
 if (grid[i][j] != 0) {
 int col = j;
 while (col < SIZE - 1 && (grid[i][col + 1] == 0 || grid[i][col + 1] == 
grid[i][col])) {
 if (grid[i][col + 1] == grid[i][col] && mergeValue != col + 1) {
 grid[i][col + 1] *= 2;
 score += grid[i][col + 1];
 grid[i][col] = 0;
 mergeValue = col + 1;
 moved = true;
 } else if (grid[i][col + 1] == 0) {
 grid[i][col + 1] = grid[i][col];
 grid[i][col] = 0;
 moved = true;
 }
 col++;
 }
 }

 }
 }
 if (moved) {
 addNewNumber();
 updateScore();
 }
 }
 public boolean isGameOver() {
 //Game-over condition
 if(winConditionReached){
 return true;
 }
 for (int i = 0; i < SIZE; i++) {
 for (int j = 0; j < SIZE; j++) {
 if (grid[i][j] == 0 ||
 (i > 0 && grid[i][j] == grid[i - 1][j]) ||
 (i < SIZE - 1 && grid[i][j] == grid[i + 1][j]) ||
 (j > 0 && grid[i][j] == grid[i][j - 1]) ||
 (j < SIZE - 1 && grid[i][j] == grid[i][j + 1])) {
 return false;
 }
 }
 }
 return true;
 }
 public void showGameOverMessage() {
 //Displays game-over message
 String message;
 if (winConditionReached) {
 message = "Congratulations! You reached the 2048 tile!\nDo you want to continue playing?";
 } else {
 message = "Game over! Do you want to play again?";
 }
 int choice = JOptionPane.showConfirmDialog(frame, message, "Game Over", 
JOptionPane.YES_NO_OPTION);
 if (choice == JOptionPane.YES_OPTION) {
 restartGame();
 } else {
 System.exit(0);
 }
 }
 public void restartGame() {
 //Restarts the game
 score = 0;
 winConditionReached=false;
 updateScore();

 initializeGrid();
 updateGridLabels();
 }
 public void updateScore() {
 //Updates the score
 scoreLabel.setText("SCORE: " + score);
 if (score > highScore) {
 highScore = score;
 highScoreLabel.setText("HIGH SCORE: " + highScore);
 }
 }
 public static void main(String[] args) {
 //Main method
 SwingUtilities.invokeLater(new Runnable() {
 public void run() {
 new Game2048();
 }
 });
 }
}
