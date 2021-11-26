import javax.swing.*;

public class Main
{
    public static void main(String[] args)
    {
        SQLService sql = new SQLService();
        JFrame frame = new JFrame("Minesweeper");

        frame.setSize(800,620);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new MainPanel(sql));
    }
}
