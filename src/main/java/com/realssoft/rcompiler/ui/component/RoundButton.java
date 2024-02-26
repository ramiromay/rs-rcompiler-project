package com.realssoft.rcompiler.ui.component;

import com.realssoft.rcompiler.ui.support.shadow.ShadowRenderer;
import com.realssoft.rcompiler.ui.values.ButtonEvent;
import com.realssoft.rcompiler.ui.values.model.EventColorModel;
import com.realssoft.rcompiler.ui.values.ColorRS;
import com.realssoft.rcompiler.ui.values.ConfigureComponent;
import com.realssoft.rcompiler.ui.values.FontRS;
import com.realssoft.rcompiler.ui.values.model.RoundButtonModel;
import com.realssoft.rcompiler.ui.values.model.ShadowModel;
import com.realssoft.rcompiler.ui.util.Utils;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

@Getter
@Setter
public class RoundButton extends JButton implements ConfigureComponent
{

    private RoundButtonModel roundButtonModel;
    private EventColorModel eventColorModel;
    private ShadowModel shadowModel;
    private ButtonEvent buttonEvent;
    private boolean isCenterText;

    public RoundButton()
    {
        super();
        configureProperties();
        configureMouseEvents();
    }

    @Override
    public void configureProperties()
    {
        this.setOpaque(true);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.setFont(FontRS.changeFont(FontRS.SEGOE_UI_REGULAR, Font.PLAIN, 20));
        this.setBorder(new EmptyBorder(1, 10, 1, 10));
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
    }

    @Override
    public void configureMouseEvents()
    {
        this.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                if(buttonEvent != ButtonEvent.PRESSED)
                {
                    buttonEvent = ButtonEvent.ENTERED;
                    roundButtonModel.setBackgroundColor(eventColorModel.getEnteredColor());
                }
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                if(buttonEvent != ButtonEvent.PRESSED)
                {
                    buttonEvent = ButtonEvent.EXITED;
                    roundButtonModel.setBackgroundColor(eventColorModel.getExitedColor());
                }
            }
        });
    }

    @Override
    public void paintComponent(@NotNull Graphics g)
    {
        int cornerRadius = roundButtonModel.getCornerRadius() + 2;
        int width = getWidth();
        int height = getHeight();
        int x = (width / 2);
        int y  = (height / 2);

        Graphics2D graphics2D = (Graphics2D) g.create();
        Utils.setRendering(graphics2D);
        Shape border = new RoundRectangle2D.Double(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);
        graphics2D.setColor(roundButtonModel.getBackgroundColor());
        graphics2D.fill(border);
        if(shadowModel != null)
        {
            int shadowSize = shadowModel.getShadowSize();
            RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(shadowSize - 1, shadowSize - 1,
                    width - (shadowSize * 2) + 1, height - (shadowSize * 2) + 1 , cornerRadius, cornerRadius);
            graphics2D.setColor(ColorRS.SHADOW);
            graphics2D.draw(roundedRectangle);

            if( buttonEvent == ButtonEvent.ENTERED )
            {
                int size = shadowSize * 2;
                width -= size;
                height -= size;

                BufferedImage img = new BufferedImage(width, height , BufferedImage.TYPE_INT_ARGB);
                Graphics2D graphics = img.createGraphics();
                Utils.setRendering(graphics);
                graphics.setColor(ColorRS.WHITE);
                graphics.fillRoundRect(0, 0, width , height, cornerRadius, cornerRadius);

                ShadowRenderer render = new ShadowRenderer(shadowSize,
                        shadowModel.getShadowOpacity(),
                        shadowModel.getShadowColor());
                graphics2D.drawImage(render.createShadow(img), 0, 0, null);
                graphics2D.drawImage(img, shadowSize , shadowSize , null);
                width += size;
                height += size;
            }
            Icon imageIcon = roundButtonModel.getIcon();
            imageIcon.paintIcon(this, graphics2D, 30, 24);
            graphics2D.setPaint(roundButtonModel.getTextColor());
            if(isCenterText)
            {
                FontMetrics fm = graphics2D.getFontMetrics();
                String buttonText = roundButtonModel.getText();
                int textWidth = fm.stringWidth(buttonText);
                int textHeight = fm.getHeight();
                int textX = (width - textWidth) / 2;
                int textY = (height - textHeight) / 2 + fm.getAscent();

                graphics2D.drawString(buttonText, textX, textY);
                graphics2D.dispose();
                return;
            }
            graphics2D.drawString(roundButtonModel.getText(), x-130, y + 7);
            graphics2D.dispose();
            super.paintComponent(g);
            return;
        }
        Icon imageIcon = roundButtonModel.getIcon();
        imageIcon.paintIcon(this, graphics2D, 12, 11);
        graphics2D.setPaint(roundButtonModel.getTextColor());
        graphics2D.drawString(roundButtonModel.getText(), x-62, y + 7);
        graphics2D.dispose();
        super.paintComponent(g);
    }

}
