package com.realssoft.rcompiler.ui.component;

import com.realssoft.rcompiler.ui.values.ColorRS;
import lombok.Getter;
import lombok.Setter;
import javax.swing.JPanel;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

@Getter
@Setter
public class PanelTransparent extends JPanel
{

    private float transparency = 0.5f;

    public PanelTransparent()
    {
        super();
        configureProperties();
    }

    public void configureProperties()
    {
        this.setOpaque(false);
        this.setBackground(ColorRS.TRANSPARENT_BACKGROUND);
    }

    @Override
    protected void paintComponent(Graphics graphics)
    {
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
        g2.setColor(this.getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setComposite(AlphaComposite.SrcOver);
        super.paintComponent(graphics);
    }

}
