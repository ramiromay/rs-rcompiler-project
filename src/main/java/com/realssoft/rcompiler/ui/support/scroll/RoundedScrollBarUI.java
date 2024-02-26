package com.realssoft.rcompiler.ui.support.scroll;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

public class RoundedScrollBarUI extends BasicScrollBarUI {

    private static final int THUMB_PADDING = 3;
    @Override
    protected void configureScrollBarColors() {
        thumbColor = Color.GRAY;
        trackColor = Color.WHITE;
        setThumbBounds(0, 0, 10, 10);
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(thumbColor);
        g2.fillRoundRect(
                thumbBounds.x + THUMB_PADDING,
                thumbBounds.y + THUMB_PADDING,
                thumbBounds.width - (2 * THUMB_PADDING),
                thumbBounds.height - (2 * THUMB_PADDING),
                10, 10);
        g2.dispose();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return new BasicArrowButton(orientation,
                Color.WHITE, Color.WHITE, Color.GRAY, Color.WHITE);
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return new BasicArrowButton(orientation,
                Color.WHITE, Color.WHITE, Color.GRAY, Color.WHITE);
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(trackColor);
        g2.fillRoundRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height, 10, 10);
        g2.dispose();
    }

    @Override
    protected void setThumbBounds(int x, int y, int width, int height) {
        super.setThumbBounds(x, y, width, height);
        scrollbar.repaint();
    }

}
