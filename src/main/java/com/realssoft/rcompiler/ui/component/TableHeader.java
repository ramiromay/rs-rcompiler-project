package com.realssoft.rcompiler.ui.component;

import com.realssoft.rcompiler.ui.values.ColorRS;
import com.realssoft.rcompiler.ui.values.ConfigureComponent;
import com.realssoft.rcompiler.ui.values.FontRS;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class TableHeader extends JLabel implements ConfigureComponent
{

    public TableHeader(String text)
    {
        super(text);
        configureProperties();
    }

    @Override
    public void configureProperties()
    {
        this.setOpaque(true);
        this.setBackground(ColorRS.WHITE);
        this.setFont(FontRS.changeFont(FontRS.SEGOE_UI_BOLD, Font.PLAIN, 20));
        this.setForeground(ColorRS.GRAY_80);
        this.setBorder(new EmptyBorder(10, 5, 10, 5));
    }

    @Override
    protected void paintComponent(Graphics grphcs)
    {
        super.paintComponent(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(ColorRS.TRANSPARENT_BACKGROUND);
        g2.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
    }

}
