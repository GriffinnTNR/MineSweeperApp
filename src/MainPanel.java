import CustomControls.*;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class MainPanel extends JPanel
{
    User currentUser;
    public MainPanel()
    {
        setBounds(0,0,800,620);
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

        registerButton.addActionListener(e -> doRegistration(usernameText.getText().substring(0,1).toUpperCase()+usernameText.getText().substring(1),Converter.hashPassword(String.valueOf(passwordText.getPassword()))));
        loginButton.addActionListener(e -> doLogin(usernameText.getText().substring(0,1).toUpperCase()+usernameText.getText().substring(1),Converter.hashPassword(String.valueOf(passwordText.getPassword()))));

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
        JLabel userLabel = new CustomLabel(currentUser.username,0,0,200,30,SwingConstants.CENTER,new Font("Monospaced",Font.BOLD,24));
        JLabel statusBar = new CustomLabel("",240,0,600,60,SwingConstants.LEFT,new Font("Monospaced",Font.PLAIN,16));

        JPanel board = new Board(statusBar, currentUser,leaderboardPanel);
        board.setBounds(240,60,510,510);
        board.setBackground(new Color(77, 14, 27));

        add(userLabel);
        add(leaderboardPanel);
        add(statusBar);
        add(board);
        updateUI();
    }
    public void doLogin(String username, String password)
    {
        currentUser = SQLService.sqlService.users.stream().filter(x-> x.username.equals(username) && x.password.equals(password)).findFirst().orElse(new User());
        if(currentUser.id == 0)
        {
            showMessageDialog(null, "Invalid password or username");
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
        if(isUsernameValid(username,password)) {
            SQLService.sqlService.addUser(username, password);
            doLogin(username, password);
        }
    }
    public boolean isUsernameValid(String username, String password)
    {
        if(SQLService.sqlService.users.stream().filter(x-> x.username.equals(username)).findFirst().orElse(new User()).id != 0)
        {
            showMessageDialog(null, "This username is already taken");
            return false;
        }
        else if(username.equals(null) || password.equals(null) || username.equals("") || password.equals(""))
        {
            showMessageDialog(null, "Username or password is empty");
            return false;
        }
        else if(username.length() > 9)
        {
            showMessageDialog(null, "Username is too long (Max: 9)");
            return false;
        }
        else
            return true;
    }
}
