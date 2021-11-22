package CustomControls;

import javax.swing.*;
import java.awt.*;

public class CustomLabel extends JLabel
{
    public CustomLabel(String text, int x, int y, int width, int height, int swingConstants, Font font)
    {
        setForeground(new Color(143, 143, 143));
        setBounds(x,y,width,height);
        setHorizontalAlignment(swingConstants);
        setFont(font);
        setText(text);
    }
    public CustomLabel(String text, int swingConstants, Font font)
    {
        setForeground(new Color(143, 143, 143));
        setHorizontalAlignment(swingConstants);
        setFont(font);
        setText(text);
    }
}
