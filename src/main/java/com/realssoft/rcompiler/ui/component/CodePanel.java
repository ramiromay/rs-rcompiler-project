package com.realssoft.rcompiler.ui.component;

import com.realssoft.rcompiler.ui.support.scroll.RoundedScrollBarUI;
import com.realssoft.rcompiler.ui.values.ColorRS;
import com.realssoft.rcompiler.ui.values.ConfigureComponent;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

public class CodePanel extends JPanel implements ConfigureComponent
{

    private JScrollPane scrollPane;
    private JScrollPane pane;

    public CodePanel()
    {
        super();
        configureComponents();
        configureProperties();
        configureLayout();
    }

    @Override
    public void configureProperties()
    {
        this.setBackground(ColorRS.WHITE);
    }

    @Override
    public void configureComponents()
    {
        Border outerBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
        Border innerBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 1, 1, ColorRS.UNSELECTED_ICON_GRAY),
                BorderFactory.createEmptyBorder(0, 0, 0, 0));
        Border comBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);

        CodeSpace codeSpace = CodeSpace.getInstance();
        BandNumbers bandNumbers = new BandNumbers(codeSpace);

        CompileButtons compileButtons = CompileButtons.getInstance();
        compileButtons.addObserver(codeSpace);

        scrollPane = new JScrollPane(codeSpace);
        scrollPane.setRowHeaderView(bandNumbers);
        scrollPane.setBorder(comBorder);
        scrollPane.getVerticalScrollBar().setUI(new RoundedScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new RoundedScrollBarUI());

        CodeSpace codeSpace1  = new CodeSpace();
        BandNumbers bandNumbers1 = new BandNumbers(codeSpace1);

        pane = new JScrollPane(codeSpace1);
        pane.setRowHeaderView(bandNumbers1);
        pane.setBorder(comBorder);
        pane.getVerticalScrollBar().setUI(new RoundedScrollBarUI());
        pane.getHorizontalScrollBar().setUI(new RoundedScrollBarUI());
        codeSpace.setPaneOptimize(codeSpace1);
    }

    @Override
    public void configureLayout()
    {
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(scrollPane,  GroupLayout.DEFAULT_SIZE,
                                        20, Short.MAX_VALUE)
                                .addComponent(pane, GroupLayout.DEFAULT_SIZE,
                                        20, Short.MAX_VALUE)
                        )

        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(scrollPane,  GroupLayout.DEFAULT_SIZE,
                        20, Short.MAX_VALUE)
                .addComponent(pane, GroupLayout.DEFAULT_SIZE,
                        20, Short.MAX_VALUE)
        );
    }

}
