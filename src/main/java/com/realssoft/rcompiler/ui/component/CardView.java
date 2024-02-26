package com.realssoft.rcompiler.ui.component;

import com.realssoft.rcompiler.ui.support.shadow.ShadowRenderer;
import com.realssoft.rcompiler.ui.values.ButtonEvent;
import com.realssoft.rcompiler.ui.values.ColorRS;
import com.realssoft.rcompiler.ui.values.ConfigureComponent;
import com.realssoft.rcompiler.ui.values.FontRS;
import com.realssoft.rcompiler.ui.values.model.CardViewModel;
import com.realssoft.rcompiler.ui.values.model.ShadowModel;
import com.realssoft.rcompiler.ui.util.Utils;
import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

@Getter
@Setter
public class CardView extends JPanel implements ConfigureComponent
{

    private CardViewModel cardViewModel;
    private ShadowModel shadowModel;
    private ButtonEvent buttonEvent;

    public CardView(CardViewModel cardViewModel, ShadowModel shadowModel)
    {
        this.cardViewModel = cardViewModel;
        this.shadowModel = shadowModel;
        this.buttonEvent = ButtonEvent.EXITED;
        configureProperties();
        configureComponents();
    }

    @Override
    public void configureProperties()
    {
        ConfigureComponent.super.configureProperties();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.setBorder(new EmptyBorder(20,7,0,0));
        this.setOpaque(false);
    }

    @Override
    public void configureComponents()
    {
        ConfigureComponent.super.configureComponents();
        MigLayout layout = new MigLayout("wrap", "20[]50", "0[]0[]0");
        this.setLayout(layout);

        JLabel lblTitle = new JLabel(cardViewModel.getTitle());
        lblTitle.setForeground(ColorRS.GRAY);
        lblTitle.setFont(FontRS.changeFont(FontRS.SEGOE_UI_BOLD, Font.PLAIN, 20));
        this.add(lblTitle, " w 100%, h 30!");

        JLabel lblDescription = new JLabel(cardViewModel.getDescription());
        lblDescription.setForeground(ColorRS.BLACK_80);
        lblDescription.setFont(FontRS.changeFont(FontRS.SEGOE_UI_REGULAR,Font.PLAIN, 17));
        lblDescription.setBorder(new EmptyBorder(10,0,0,0));
        this.add(lblDescription, " w 100%, h 55!");
    }

    @Override
    protected void paintComponent(Graphics graphics)
    {
        int shadowSize = shadowModel.getShadowSize();
        int cornerRadius = cardViewModel.getCornerRadius();
        int size = shadowSize * 2;
        int width = getWidth() - size;
        int height = getHeight() - size;

        Graphics2D g2 = (Graphics2D) graphics;
        Utils.setRendering(g2);
        if(buttonEvent == ButtonEvent.ENTERED)
        {
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = img.createGraphics();
            Utils.setRendering(g);
            g.setColor(ColorRS.SHADOW);
            g.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);

            ShadowRenderer render = new ShadowRenderer(shadowSize, shadowModel.getShadowOpacity(),
                    shadowModel.getShadowColor());
            g2.drawImage(render.createShadow(img), 0, 0, null);
            g2.drawImage(img, shadowSize, shadowSize, null);
        }
        g2 = (Graphics2D) graphics.create(); //NOT DELETE
        g2.setClip(new RoundRectangle2D.Float(shadowSize, shadowSize, width, height, cornerRadius, cornerRadius));
        g2.drawImage(cardViewModel.getBackgroundImage(), shadowSize, shadowSize, width, height, null);
        g2.dispose();
        super.paintComponent(graphics);
    }

}
