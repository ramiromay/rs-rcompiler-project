package com.realssoft.rcompiler.ui.component;

import com.realssoft.rcompiler.ui.values.ButtonEvent;
import com.realssoft.rcompiler.ui.values.ColorRS;
import com.realssoft.rcompiler.ui.values.ConfigureComponent;
import com.realssoft.rcompiler.ui.values.model.EventColorModel;
import com.realssoft.rcompiler.ui.values.model.SquareButtonModel;
import com.realssoft.rcompiler.ui.util.Utils;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Getter
@Setter
public class SquareButton extends JButton implements ConfigureComponent
{

    private SquareButtonModel squareButtonModel;
    private EventColorModel eventColorModel;
    private ButtonEvent buttonEvent;

    public SquareButton()
    {
        super();
        configureProperties();
        configureMouseEvents();
    }

    @Override
    public void configureProperties()
    {
        ConfigureComponent.super.configureProperties();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.setContentAreaFilled(false);
        this.setFocusable(false);
    }

    @Override
    public void configureMouseEvents()
    {
        ConfigureComponent.super.configureMouseEvents();
        this.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent me)
            {
                buttonEvent = ButtonEvent.ENTERED;
                if(squareButtonModel.isDynamicColor())
                {
                    squareButtonModel.setIconColor(ColorRS.WHITE);
                }
                squareButtonModel.setBackgroundColor(eventColorModel.getEnteredColor());
            }

            @Override
            public void mouseExited(MouseEvent me)
            {
                buttonEvent = ButtonEvent.EXITED;
                if(squareButtonModel.isDynamicColor())
                {
                    squareButtonModel.setIconColor(ColorRS.BLACK);
                }
                squareButtonModel.setBackgroundColor(eventColorModel.getExitedColor());
            }
        });
    }

    @Override
    protected void paintComponent(@NotNull Graphics graphics)
    {
        int width = getWidth();
        int height = getHeight();
        Graphics2D g2 = (Graphics2D) graphics.create();
        Utils.setRendering(g2);
        g2.setColor(squareButtonModel.getBackgroundColor());
        g2.fillRect(0, 0, width, height);
        Icon imageIcon = squareButtonModel.getIcon()
                .toIcon(squareButtonModel.getIconColor());
        imageIcon.paintIcon(this, g2, (width - imageIcon.getIconWidth()) / 2,
                ((height- imageIcon.getIconHeight()) / 2) + 1);
        g2.dispose();
        super.paintComponent(graphics);
    }

}
