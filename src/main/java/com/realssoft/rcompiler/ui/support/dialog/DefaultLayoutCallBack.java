package com.realssoft.rcompiler.ui.support.dialog;

import java.awt.Component;
import java.awt.Dimension;
import net.miginfocom.layout.BoundSize;
import net.miginfocom.layout.ComponentWrapper;
import net.miginfocom.layout.LayoutCallback;
import net.miginfocom.layout.UnitValue;

public class DefaultLayoutCallBack extends LayoutCallback
{

    private final Component parent;

    public DefaultLayoutCallBack(Component parent)
    {
        this.parent = parent;
    }

    @Override
    public BoundSize[] getSize(ComponentWrapper cw)
    {
        Dimension ps = parent.getSize();
        Dimension s = ((Component) cw.getComponent()).getPreferredSize();
        int margin = 50;
        int w = s.width;
        int h = s.height;
        if (s.getWidth() > ps.getWidth() - margin)
        {
            w = Math.max(ps.width - margin, cw.getMinimumWidth(0));
        }
        if (s.getHeight() > ps.getHeight() - margin)
        {
            h = Math.max(ps.height - margin, cw.getMinimumHeight(0));
        }
        return new BoundSize[]{new BoundSize(new UnitValue(w), ""),
                new BoundSize(new UnitValue(h), "")};
    }
}