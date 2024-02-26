package com.realssoft.rcompiler.ui.component;

import com.realssoft.Main;
import com.realssoft.rcompiler.logic.file.GenerateTokenFile;
import com.realssoft.rcompiler.logic.file.GenerateTriploFile;
import com.realssoft.rcompiler.logic.utils.MapUtils;
import com.realssoft.rcompiler.ui.values.ColorRS;
import com.realssoft.rcompiler.ui.values.ConfigureComponent;
import com.realssoft.rcompiler.ui.values.FontRS;
import com.realssoft.rcompiler.ui.values.observers.IEventObserver;
import org.jetbrains.annotations.NotNull;
import javax.swing.BorderFactory;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.Color;
import java.awt.Font;
import java.util.concurrent.CompletableFuture;

public class RunPanel extends JTextPane implements ConfigureComponent,
        IEventObserver<Integer>
{

    private StyledDocument doc;
    private GenerateTokenFile generateTokenFile;
    private GenerateTriploFile  generateTriploFile;

    public RunPanel()
    {
        super();
        configureProperties();
        configureBorder();
        configureComponents();
    }

    @Override
    public void configureProperties()
    {
        ConfigureComponent.super.configureProperties();
        this.setBackground(ColorRS.WHITE);
        this.setEditable(false);
        this.setFocusable(false);
        this.setFont(FontRS.changeFont(FontRS.SEGOE_UI_REGULAR, Font.BOLD, 18));
    }

    @Override
    public void configureBorder() {
        ConfigureComponent.super.configureBorder();
        Border outerBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
        Border innerBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 1, 1, 1, ColorRS.UNSELECTED_ICON_GRAY),
                BorderFactory.createEmptyBorder(20, 20, 0, 0));
        Border comBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);

        this.setBorder(comBorder);
    }

    @Override
    public void configureComponents() {
        ConfigureComponent.super.configureComponents();
        CompileButtons compileButtons = CompileButtons.getInstance();
        compileButtons.addObserver(this);
        generateTokenFile = new GenerateTokenFile();
        generateTriploFile = new GenerateTriploFile();
        doc = this.getStyledDocument();
    }

    @Override
    public void update(@NotNull Integer generic)
    {
        if (generic == 1)
        {
//            System.out.println("Run panel");
            //generateTokenFile.start();
            generateTriploFile.save();
            if(Main.TOKEN_ERROR_MAP.size() == 0)
            {
                Style style = this.addStyle("BackStyle", null);
                StyleConstants.setForeground(style, ColorRS.BLACK);
                this.setText("Complete, process finished with exit code 0");
                doc.setCharacterAttributes(0, doc.getLength(), style, false);
                return;
            }
            Style style = this.addStyle("RedStyle", null);
            StyleConstants.setForeground(style, Color.RED);
            this.setText("Errors Language RS :\n");
            Main.TOKEN_ERROR_MAP.forEach((key, value) -> {
                try
                {
                    doc.insertString(doc.getLength(),
                            "\t" +value.getDescription()
                                    + " : '" + value.getTokenModel().getLexeme()
                                    + "' in the lines " + MapUtils.getRowsError(value)
                                    + "\n",
                            null);
                }
                catch (BadLocationException e)
                {
                    throw new RuntimeException(e);
                }
            });
            doc.setCharacterAttributes(0, doc.getLength(), style, false);
        }
        if (generic == 2)
        {
            CompletableFuture.runAsync(() -> this.setText(""));
        }
    }
}
