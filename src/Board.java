import CustomControls.CustomLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.Random;

public class Board extends JPanel {

    private final int NUM_IMAGES = 13;
    private final int CELL_SIZE = 30;

    private final int COVER_FOR_CELL = 10;
    private final int MARK_FOR_CELL = 10;
    private final int EMPTY_CELL = 0;
    private final int MINE_CELL = 9;
    private final int COVERED_MINE_CELL = MINE_CELL + COVER_FOR_CELL;
    private final int MARKED_MINE_CELL = COVERED_MINE_CELL + MARK_FOR_CELL;

    private final int DRAW_MINE = 9;
    private final int DRAW_COVER = 10;
    private final int DRAW_MARK = 11;
    private final int DRAW_WRONG_MARK = 12;

    private final int N_MINES = 40;
    private final int N_ROWS = 17;
    private final int N_COLS = 17;

    private final int BOARD_WIDTH = N_COLS * CELL_SIZE + 1;
    private final int BOARD_HEIGHT = N_ROWS * CELL_SIZE + 1;

    private int[] field;
    private boolean inGame;
    private int minesLeft;
    private Image[] img;

    private int allCells;
    private final JLabel statusBar;
    private JPanel leaderboardPanel;

    Instant start = Instant.now();
    Instant finish = Instant.now();

    User currentUser;

    public Board(JLabel statusBar, User current, JPanel leaderboardPanel) {
        currentUser = current;
        this.statusBar = statusBar;
        this.leaderboardPanel = leaderboardPanel;
        updateLeaderboardPanel(this.leaderboardPanel);
        initBoard();
    }

    private void initBoard() {

        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

        img = new Image[NUM_IMAGES];

        for (int i = 0; i < NUM_IMAGES; i++) {

            var path = "src/resources/" + i + ".png";
            img[i] = (new ImageIcon(path)).getImage();
        }

        addMouseListener(new MinesAdapter());
        newGame();
    }

    private void newGame() {

        start = Instant.now();
        int cell;

        var random = new Random();
        inGame = true;
        minesLeft = N_MINES;

        allCells = N_ROWS * N_COLS;
        field = new int[allCells];

        for (int i = 0; i < allCells; i++) {

            field[i] = COVER_FOR_CELL;
        }

        statusBar.setText("Marks left: "+Integer.toString(minesLeft));

        int i = 0;

        while (i < N_MINES) {

            int position = (int) (allCells * random.nextDouble());

            if ((position < allCells)
                    && (field[position] != COVERED_MINE_CELL)) {

                int current_col = position % N_COLS;
                field[position] = COVERED_MINE_CELL;
                i++;

                if (current_col > 0) {
                    cell = position - 1 - N_COLS;
                    if (cell >= 0) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                    cell = position - 1;
                    if (cell >= 0) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }

                    cell = position + N_COLS - 1;
                    if (cell < allCells) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                }

                cell = position - N_COLS;
                if (cell >= 0) {
                    if (field[cell] != COVERED_MINE_CELL) {
                        field[cell] += 1;
                    }
                }

                cell = position + N_COLS;
                if (cell < allCells) {
                    if (field[cell] != COVERED_MINE_CELL) {
                        field[cell] += 1;
                    }
                }

                if (current_col < (N_COLS - 1)) {
                    cell = position - N_COLS + 1;
                    if (cell >= 0) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + N_COLS + 1;
                    if (cell < allCells) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + 1;
                    if (cell < allCells) {
                        if (field[cell] != COVERED_MINE_CELL) {
                            field[cell] += 1;
                        }
                    }
                }
            }
        }
    }

    private void findEmptyCells(int j) {

        int current_col = j % N_COLS;
        int cell;

        if (current_col > 0)
        {
            cell = j - N_COLS - 1;
            if (cell >= 0)
                helpToFindEmptyCells(cell);
            cell = j - 1;
            if (cell >= 0)
                helpToFindEmptyCells(cell);
            cell = j + N_COLS - 1;
            if (cell < allCells)
                helpToFindEmptyCells(cell);
        }

        cell = j - N_COLS;
        if (cell >= 0)
            helpToFindEmptyCells(cell);

        cell = j + N_COLS;
        if (cell < allCells)
            helpToFindEmptyCells(cell);

        if (current_col < (N_COLS - 1))
        {
            cell = j - N_COLS + 1;
            if (cell >= 0)
                helpToFindEmptyCells(cell);
            cell = j + N_COLS + 1;
            if (cell < allCells)
                helpToFindEmptyCells(cell);
            cell = j + 1;
            if (cell < allCells)
                helpToFindEmptyCells(cell);
        }

    }
    public void helpToFindEmptyCells(int cell)
    {
        if (field[cell] > MINE_CELL) {
            field[cell] -= COVER_FOR_CELL;
            if (field[cell] == EMPTY_CELL) {
                findEmptyCells(cell);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        int uncover = 0;

        for (int i = 0; i < N_ROWS; i++) {

            for (int j = 0; j < N_COLS; j++) {

                int cell = field[(i * N_COLS) + j];

                if (inGame && cell == MINE_CELL) {

                    inGame = false;
                }

                if (!inGame) {

                    if (cell == COVERED_MINE_CELL) {
                        cell = DRAW_MINE;
                    } else if (cell == MARKED_MINE_CELL) {
                        cell = DRAW_MARK;
                    } else if (cell > COVERED_MINE_CELL) {
                        cell = DRAW_WRONG_MARK;
                    } else if (cell > MINE_CELL) {
                        cell = DRAW_COVER;
                    }

                } else {

                    if (cell > COVERED_MINE_CELL) {
                        cell = DRAW_MARK;
                    } else if (cell > MINE_CELL) {
                        cell = DRAW_COVER;
                        uncover++;
                    }
                }

                g.drawImage(img[cell], (j * CELL_SIZE),
                        (i * CELL_SIZE), this);
            }
        }

        if (uncover == 0 && inGame) {

            inGame = false;
            finish = Instant.now();
            int time = (int) Duration.between(start, finish).toSeconds();
            statusBar.setText("<html>Game won, Time: " + Converter.secondsToString(time) + "<br>click somewhere on board if you want to restart</html>");
            if(currentUser.bestTimeInSeconds == null || currentUser.bestTimeInSeconds > time)
            {
                currentUser.bestTimeInSeconds = time;
                SQLService.getInstance().updateUser(currentUser);
                SQLService.getInstance().getAllUsers();
                updateLeaderboardPanel(leaderboardPanel);
            }

        } else if (!inGame) {

            finish = Instant.now();
            statusBar.setText("<html>Game lost,<br>click somewhere on board if you want to restart</html>");
        }
    }
    private void updateLeaderboardPanel(JPanel panel)
    {
        panel.removeAll();
        panel.add(new CustomLabel("     ",SwingConstants.CENTER, new Font("Monospaced", Font.BOLD, 24)));
        panel.add(new CustomLabel("Leaderboard",SwingConstants.CENTER, new Font("Monospaced", Font.BOLD, 24)));
        panel.add(new CustomLabel("     ",SwingConstants.CENTER, new Font("Monospaced", Font.BOLD, 20)));
        for (User users : SQLService.getInstance().users.stream().filter(x -> x.bestTimeInSeconds != null).sorted(Comparator.comparingInt(User::getBestTimeInSeconds)).limit(10).toList())
            panel.add(new CustomLabel(users.username + " -> " + Converter.secondsToString(users.bestTimeInSeconds),SwingConstants.LEFT, new Font("Monospaced",Font.PLAIN,18)));
        panel.updateUI();
    }

    private class MinesAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {

            int x = e.getX();
            int y = e.getY();

            int cCol = x / CELL_SIZE;
            int cRow = y / CELL_SIZE;

            boolean doRepaint = false;

            if (!inGame) {

                newGame();
                repaint();
            }

            if ((x < N_COLS * CELL_SIZE) && (y < N_ROWS * CELL_SIZE)) {

                if (e.getButton() == MouseEvent.BUTTON3) {

                    if (field[(cRow * N_COLS) + cCol] > MINE_CELL) {

                        doRepaint = true;

                        if (field[(cRow * N_COLS) + cCol] <= COVERED_MINE_CELL) {

                            if (minesLeft > 0) {
                                field[(cRow * N_COLS) + cCol] += MARK_FOR_CELL;
                                minesLeft--;
                                String msg = "Marks left: " + Integer.toString(minesLeft);
                                statusBar.setText(msg);
                            } else {
                                statusBar.setText("No marks left");
                            }
                        } else {

                            field[(cRow * N_COLS) + cCol] -= MARK_FOR_CELL;
                            minesLeft++;
                            String msg = "Marks left: " + Integer.toString(minesLeft);
                            statusBar.setText(msg);
                        }
                    }

                } else {

                    if (field[(cRow * N_COLS) + cCol] > COVERED_MINE_CELL) {

                        return;
                    }

                    if ((field[(cRow * N_COLS) + cCol] > MINE_CELL)
                            && (field[(cRow * N_COLS) + cCol] < MARKED_MINE_CELL)) {

                        field[(cRow * N_COLS) + cCol] -= COVER_FOR_CELL;
                        doRepaint = true;

                        if (field[(cRow * N_COLS) + cCol] == MINE_CELL) {
                            inGame = false;
                        }

                        if (field[(cRow * N_COLS) + cCol] == EMPTY_CELL) {
                            findEmptyCells((cRow * N_COLS) + cCol);
                        }
                    }
                }

                if (doRepaint) {
                    repaint();
                }
            }
        }
    }
}
