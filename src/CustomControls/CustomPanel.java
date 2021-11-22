package CustomControls;

import javax.swing.*;
import java.awt.*;

public class CustomPanel extends JPanel
{
    public CustomPanel(int x, int y, int width, int height)
    {
        setBounds(x,y,width,height);
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setBackground(new Color(77, 14, 27));
    }
}
