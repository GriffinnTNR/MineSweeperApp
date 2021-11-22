import CustomControls.*;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;

public class MainPanel extends JPanel
{
    SQLService sqlService;
    User currentUser;
    public MainPanel(SQLService sqlService)
    {
        this.sqlService = sqlService;
        setBounds(0,0,800,600);
        setLayout(null);
        setBackground(new Color(77, 14, 27));
        initLoginPanel();
    }
    public void initLoginPanel()
    {
        JTextField usernameText = new CustomTextField(300,175,200,40);
        JPasswordField passwordText = new CustomPasswordField(300,225,200,40);
        JButton loginButton = new CustomButton("Login",300,300,200,40);
        JButton registerButton = new CustomButton("Register",300,350,200,40);

        registerButton.addActionListener(e -> doRegistration(usernameText.getText(),String.valueOf(passwordText.getPassword())));
        loginButton.addActionListener(e -> doLogin(usernameText.getText(),String.valueOf(passwordText.getPassword())));

        add(usernameText);
        add(passwordText);
        add(new CustomLabel("Username:",175,175,100,40, SwingConstants.LEFT, new Font("Monospaced",Font.BOLD,18)));
        add(new CustomLabel("Password:",175,225,100,40, SwingConstants.LEFT, new Font("Monospaced",Font.BOLD,18)));
        add(loginButton);
        add(registerButton);
    }
    public void initGamePanel()
    {
        JPanel leaderboardPanel = new CustomPanel(0,30,200,570);
        JLabel userLabel = new CustomLabel(currentUser.username,0,0,200,30,SwingConstants.CENTER,new Font("Monospaced",Font.BOLD,20));
        //JLabel userLabel = new JLabel(currentUser.username, SwingConstants.CENTER);
        //userLabel.setBounds(0,0,200,30);
        JLabel statusBar = new CustomLabel("",200,0,600,30,SwingConstants.LEFT,new Font("Monospaced",Font.PLAIN,16));

        JPanel board = new Board(statusBar, sqlService, currentUser,leaderboardPanel);
        board.setBounds(240,40,510,510);
        board.setBackground(new Color(77, 14, 27));

        add(userLabel);
        add(leaderboardPanel);
        add(statusBar);
        add(board);
        updateUI();
    }
    public void doLogin(String username, String password)
    {
        currentUser = sqlService.users.stream().filter(x-> x.username.equals(username) && x.password.equals(password)).findFirst().orElse(new User());
        if(currentUser.id == 0)
        {
            System.out.println("Invalid password or username");
        }
        else
        {
            removeAll();
            updateUI();
            initGamePanel();
        }
    }
    public void doRegistration(String username, String password)
    {
        if(username != null && password != null) {
            sqlService.addUser(username, password);
            doLogin(username, password);
        }
        else
            System.out.print("Username or password is null");
    }
}
