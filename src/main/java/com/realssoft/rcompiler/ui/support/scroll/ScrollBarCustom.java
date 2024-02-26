package com.realssoft.rcompiler.ui.support.scroll;

import com.realssoft.rcompiler.ui.values.ConfigureComponent;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JScrollBar;

public class ScrollBarCustom extends JScrollBar implements ConfigureComponent
{

    public ScrollBarCustom()
    {
        configureProperties();
    }

    @Override
    public void configureProperties()
    {
        ConfigureComponent.super.configureProperties();
        this.setUI(new ModernScrollBarUI());
        this.setPreferredSize(new Dimension(5, 5));
        this.setForeground(new Color(94, 139, 231));
        this.setUnitIncrement(20);
        this.setOpaque(false);
    }

}
