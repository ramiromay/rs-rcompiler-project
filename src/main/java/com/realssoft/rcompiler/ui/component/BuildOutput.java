package com.realssoft.rcompiler.ui.component;

import com.realssoft.rcompiler.ui.support.scroll.RoundedScrollBarUI;
import com.realssoft.rcompiler.ui.support.scroll.ScrollBarCustom;
import com.realssoft.rcompiler.ui.values.ConfigureComponent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BuildOutput extends JPanel implements ConfigureComponent
{

    private RegisterPanel registerPanel;
    private JScrollPane scrollPane;
    private GroupLayout layout;

    public BuildOutput()
    {
        super();
        configureProperties();
        configureComponents();
        configureLayout();
    }

    @Override
    public void configureProperties() {
        ConfigureComponent.super.configureProperties();

    }

    @Override
    public void configureComponents() {
        ConfigureComponent.super.configureComponents();
        registerPanel = new RegisterPanel();

        JScrollBar scrollBar = new JScrollBar();
        scrollBar.setUI(new RoundedScrollBarUI());

        RunPanel runPanel = new RunPanel();
        scrollPane = new JScrollPane(scrollBar);
        scrollPane.setBorder(new EmptyBorder(0,0,0,0));
        scrollPane.setViewportView(runPanel);
    }

    @Override
    public void configureLayout() {
        ConfigureComponent.super.configureLayout();
        layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(registerPanel, GroupLayout.DEFAULT_SIZE,
                        200, Short.MAX_VALUE)
                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE,
                        200, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(registerPanel, GroupLayout.DEFAULT_SIZE,
                        42, GroupLayout.PREFERRED_SIZE)
                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE,
                        150, Short.MAX_VALUE)
        );
    }
}
