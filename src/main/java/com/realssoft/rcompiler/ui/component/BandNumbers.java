package com.realssoft.rcompiler.ui.component;

import com.realssoft.rcompiler.ui.values.ColorRS;
import com.realssoft.rcompiler.ui.values.FontRS;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyleConstants;
import javax.swing.text.Utilities;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BandNumbers extends JPanel implements CaretListener, DocumentListener
{

    public static final float LEFT = 0.0f;
    public static final float CENTER = 0.5f;
    public static final float RIGHT = 1.0f;
    public static final int DEFAULT_BORDER_GAP = 15;
    private static final int HEIGHT_SIZE = Integer.MAX_VALUE - 1000000;
    private HashMap<String, FontMetrics> fonts;
    private final JTextComponent component;
    private Color currentLineForeground;
    private float digitAlignment;
    private int minimumDisplayDigits;
    private int lastDigits;
    private int lastHeight;
    @Getter
    private int lastLine;

    public BandNumbers(JTextComponent component)
    {
        this(component, 3);
    }

    public BandNumbers(JTextComponent component, int minimumDisplayDigits)
    {
        this.component = component;
        this.setFont(FontRS.changeFont(FontRS.SEGOE_UI_REGULAR, Font.BOLD, 20) );
        this.setBackground(ColorRS.WHITE);
        setBorderGap(15);
        setCurrentLineForeground(ColorRS.SELECTED_ICON_BLUE);
        setDigitAlignment(LEFT);
        setMinimumDisplayDigits(minimumDisplayDigits);
        component.getDocument().addDocumentListener(this);
        component.addCaretListener(this);
    }

    public void setBorderGap(int borderGap)
    {
        Border outerBorderVerHor = BorderFactory.createEmptyBorder(0, DEFAULT_BORDER_GAP, 0, borderGap);
        Border innerBorderVerHor = BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 1, 0, 1, ColorRS.UNSELECTED_ICON_GRAY),
                BorderFactory.createEmptyBorder(0, 0, 0, 0));
        Border comBorderVerHor = BorderFactory.createCompoundBorder(innerBorderVerHor, outerBorderVerHor);
        this.setBorder(comBorderVerHor);
        lastDigits = 0;
        setPreferredWidth();
    }

    public Color getCurrentLineForeground()
    {
        return currentLineForeground == null
                ? getForeground()
                : currentLineForeground;
    }

    public void setCurrentLineForeground(Color currentLineForeground)
    {
        this.currentLineForeground = currentLineForeground;
    }

    public void setDigitAlignment(float digitAlignment)
    {
        this.digitAlignment = digitAlignment > 1.0f
                ? 1.0f
                : digitAlignment < 0.0f
                ? -1.0f
                : digitAlignment;
    }

    public void setMinimumDisplayDigits(int minimumDisplayDigits)
    {
        this.minimumDisplayDigits = minimumDisplayDigits;
        setPreferredWidth();
    }

    private void setPreferredWidth()
    {
        Element root = component.getDocument().getDefaultRootElement();
        int lines = root.getElementCount();
        int digits = Math.max(String.valueOf(lines).length(), minimumDisplayDigits);

        if (lastDigits != digits)
        {
            lastDigits = digits;
            FontMetrics fontMetrics = getFontMetrics( getFont() );
            int width = fontMetrics.charWidth('0') * digits;
            Insets insets = getInsets();
            int preferredWidth = insets.left + insets.right + width;

            Dimension d = getPreferredSize();
            d.setSize(preferredWidth, HEIGHT_SIZE);
            setPreferredSize(d);
            setSize(d);
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        FontMetrics fontMetrics = component.getFontMetrics(component.getFont());
        Insets insets = getInsets();
        int availableWidth = getSize().width - insets.left - insets.right;
        Rectangle clip = g.getClipBounds();
        int rowStartOffset = component.viewToModel( new Point(0, clip.y) );
        int endOffset = component.viewToModel( new Point(0, clip.y + clip.height) );

        while (rowStartOffset <= endOffset)
        {
            try
            {
                if (isCurrentLine(rowStartOffset))
                    g.setColor( getCurrentLineForeground() );
                else
                    g.setColor( getForeground() );

                String lineNumber = getTextLineNumber(rowStartOffset);
                int stringWidth = fontMetrics.stringWidth( lineNumber );
                int x = getOffsetX(availableWidth, stringWidth) + insets.left;
                int y = getOffsetY(rowStartOffset, fontMetrics);
                g.drawString(lineNumber, x, y);
                rowStartOffset = Utilities.getRowEnd(component, rowStartOffset) + 1;
            }
            catch(Exception e) {break;}
        }
    }

    private boolean isCurrentLine(int rowStartOffset)
    {
        int caretPosition = component.getCaretPosition();
        Element root = component.getDocument().getDefaultRootElement();
        return root.getElementIndex(rowStartOffset) == root.getElementIndex(caretPosition);
    }

    protected String getTextLineNumber(int rowStartOffset)
    {
        Element root = component.getDocument().getDefaultRootElement();
        int index = root.getElementIndex(rowStartOffset);
        Element line = root.getElement(index);

        if (line.getStartOffset() == rowStartOffset)
            return String.valueOf(index + 1);
        else
            return "";
    }

    private int getOffsetX(int availableWidth, int stringWidth)
    {
        return (int) ((availableWidth - stringWidth) * digitAlignment);
    }

    private int getOffsetY(int rowStartOffset, @NotNull FontMetrics fontMetrics)
            throws BadLocationException
    {
        Rectangle r = component.modelToView( rowStartOffset );
        int lineHeight = fontMetrics.getHeight();
        int y = r.y + r.height;
        int descent = 0;

        if (r.height == lineHeight)
        {
            descent = fontMetrics.getDescent();
        }
        else  		{
            if (fonts == null)
                fonts = new HashMap<>();

            Element root = component.getDocument().getDefaultRootElement();
            int index = root.getElementIndex( rowStartOffset );
            Element line = root.getElement( index );

            for (int i = 0; i < line.getElementCount(); i++)
            {
                Element child = line.getElement(i);
                AttributeSet as = child.getAttributes();
                String fontFamily = (String)as.getAttribute(StyleConstants.FontFamily);
                Integer fontSize = (Integer)as.getAttribute(StyleConstants.FontSize);
                String key = fontFamily + fontSize;
                FontMetrics fm = fonts.get(key);

                if (fm == null)
                {
                    Font font = new Font(fontFamily, Font.PLAIN, fontSize);
                    fm = component.getFontMetrics( font );
                    fonts.put(key, fm);
                }

                descent = Math.max(descent, fm.getDescent());
            }
        }

        return y - descent;
    }

    @Override
    public void caretUpdate(CaretEvent e)
    {
        int caretPosition = component.getCaretPosition();
        Element root = component.getDocument().getDefaultRootElement();
        int currentLine = root.getElementIndex( caretPosition );

        if (lastLine != currentLine)
        {
            repaint();
            lastLine = currentLine;
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e)
    {
        documentChanged();
    }

    @Override
    public void insertUpdate(DocumentEvent e)
    {
        documentChanged();
    }

    @Override
    public void removeUpdate(DocumentEvent e)
    {
        documentChanged();
    }

    private void documentChanged()
    {
        SwingUtilities.invokeLater(() -> {
            try
            {
                int endPos = component.getDocument().getLength();
                Rectangle rect = component.modelToView(endPos);

                if (rect != null && rect.y != lastHeight)
                {
                    setPreferredWidth();
                    repaint();
                    lastHeight = rect.y;
                }


            }
            catch (BadLocationException ex) {
                Logger.getLogger(CodePanel.class.getName()).log(Level.SEVERE, "Error", ex);
            }
        });
    }

}
