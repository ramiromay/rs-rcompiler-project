package com.realssoft.rcompiler.ui.activity;

import com.realssoft.rcompiler.ui.values.ColorRS;
import com.realssoft.rcompiler.ui.values.ConfigureComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ActivityManager extends JPanel implements ConfigureComponent
{

    private static final ActivityManager INSTANCE = new ActivityManager();

    private ActivityManager()
    {
        super();
        configureProperties();
    }

    public static ActivityManager getInstance()
    {
        return INSTANCE;
    }

    public void showActivity(Component activity)
    {
        removeAll();
        add(activity);
        repaint();
        revalidate();
    }

    public boolean isAncestor(Component c)
    {
        Container p;
        if (c == null || ((p = c.getParent()) == null))
        {
            return false;
        }
        while (p != null)
        {
            if (p == this)
            {
                return true;
            }
            p = p.getParent();
        }
        return false;
    }

    @Override
    public void configureProperties()
    {
        ConfigureComponent.super.configureProperties();
        this.setBackground(ColorRS.WHITE);
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(0, 0, 0, 0));
    }

}
