package CustomControls;

import javax.swing.*;
import java.awt.*;

public class CustomTextField extends JTextField
{
    public CustomTextField(int x, int y, int width, int height)
    {
        setBounds(x,y,width,height);
        setBorder(null);
        setBackground(new Color(143, 143, 143));
        setText("");
        setFont(new Font("Monospaced",Font.BOLD,18));
    }
}
