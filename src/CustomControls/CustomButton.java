package CustomControls;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CustomButton extends JButton implements MouseListener
{
    private Color defaultColor = new Color(143, 143, 143);
    private Color mouseOverColor = new Color(200, 70, 70);

    public CustomButton(String text, int x, int y, int width, int height)
    {
        setBounds(x,y,width,height);
        setBackground(defaultColor);
        setBorder(null);
        setText(text);
        addMouseListener(this);
    }
    public void mouseClicked(MouseEvent e) { }

    public void mousePressed(MouseEvent e) { }

    public void mouseReleased(MouseEvent e) { }

    public void mouseEntered(MouseEvent e) {

        if(e.getSource()==this) {  this.setBackground(this.mouseOverColor); }

    }

    public void mouseExited(MouseEvent e) {

        if(e.getSource()==this) { this.setBackground(this.defaultColor); }

    }
}
