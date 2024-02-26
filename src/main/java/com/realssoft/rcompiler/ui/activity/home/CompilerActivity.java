package com.realssoft.rcompiler.ui.activity.home;

import com.realssoft.rcompiler.ui.activity.ActivityManager;
import com.realssoft.rcompiler.ui.component.BuildOutput;
import com.realssoft.rcompiler.ui.component.CodePanel;
import com.realssoft.rcompiler.ui.component.NavBar;
import com.realssoft.rcompiler.ui.values.ConfigureComponent;
import com.realssoft.rcompiler.ui.values.observers.IEventObserver;
import javax.swing.GroupLayout;
import javax.swing.JPanel;

public class CompilerActivity extends JPanel implements ConfigureComponent, IEventObserver<ActivityManager>
{

    private static final CompilerActivity INSTANCE = new CompilerActivity();
    private CodePanel codePanel;
    private BuildOutput buildOutput;

    private CompilerActivity()
    {
        super();
        configureProperties();
        configureComponents();
        configureLayout();
    }

    public static CompilerActivity getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void configureProperties() {
        ConfigureComponent.super.configureProperties();
    }

    @Override
    public void configureComponents() {
        ConfigureComponent.super.configureComponents();
        codePanel = new CodePanel();
        buildOutput = new BuildOutput();
    }

    @Override
    public void configureLayout() {
        ConfigureComponent.super.configureLayout();
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(codePanel,  GroupLayout.DEFAULT_SIZE,
                        200, Short.MAX_VALUE)
                .addComponent(buildOutput, GroupLayout.DEFAULT_SIZE,
                        200, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(codePanel, GroupLayout.DEFAULT_SIZE,
                        400, Short.MAX_VALUE)
                .addComponent(buildOutput, GroupLayout.DEFAULT_SIZE,
                        150, Short.MAX_VALUE)
        );

    }

    @Override
    public void update(ActivityManager activityManager)
    {
        NavBar.getInstance().navBarElements(false, false);
    }
}
