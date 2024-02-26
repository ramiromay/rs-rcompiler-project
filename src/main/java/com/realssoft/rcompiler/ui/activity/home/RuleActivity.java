package com.realssoft.rcompiler.ui.activity.home;

import com.realssoft.materialdesign.MaterialDesignIcon;
import com.realssoft.materialdesign.MicrosoftSegoeIcon;
import com.realssoft.rcompiler.ui.activity.ActivityManager;
import com.realssoft.rcompiler.ui.component.DynamicTable;
import com.realssoft.rcompiler.ui.component.NavBar;
import com.realssoft.rcompiler.ui.component.SquareButton;
import com.realssoft.rcompiler.ui.values.ButtonEvent;
import com.realssoft.rcompiler.ui.values.ColorRS;
import com.realssoft.rcompiler.ui.values.ConfigureComponent;
import com.realssoft.rcompiler.ui.values.FontRS;
import com.realssoft.rcompiler.ui.values.StringRS;
import com.realssoft.rcompiler.ui.values.model.EventColorModel;
import com.realssoft.rcompiler.ui.values.model.SquareButtonModel;
import com.realssoft.rcompiler.ui.values.observers.IEventObserver;
import org.jetbrains.annotations.NotNull;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;

public class RuleActivity extends JPanel implements ConfigureComponent, IEventObserver<ActivityManager>
{

    private static final RuleActivity INSTANCE = new RuleActivity();
    private JLabel lblTitle;
    private JLabel lblSubtitle;
    private JScrollPane scrollPane;
    private DynamicTable dynamicTable;

    private RuleActivity()
    {
        super();
        configureProperties();
        configureComponents();
        configureButtonsEvent();
        configureLayout();
        initializeData();
    }

    public static RuleActivity getInstance()
    {
        return INSTANCE;
    }

    private DefaultTableModel getTableModel()
    {
        return new DefaultTableModel(new Object [][] {}, StringRS.RULES_COLUMN_TABLE)
        {
            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return false;
            }
        };
    }

    private void initializeData()
    {
        dynamicTable.addRow(StringRS.RULES_SEPARATORS);
        dynamicTable.addRow(StringRS.RULES_O_ARITHMETIC);
        dynamicTable.addRow(StringRS.RULES_O_RELATIONAL);
        dynamicTable.addRow(StringRS.RULES_O_ASSIGMENT);
        dynamicTable.addRow(StringRS.RULES_O_LOGIC);
        dynamicTable.addRow(StringRS.RULES_ID);
        dynamicTable.addRow(StringRS.RULES_NUMS_INTEGERS);
        dynamicTable.addRow(StringRS.RULES_NUMS_FLOAT);
        dynamicTable.addRow(StringRS.RULES_TYPE_DATA);
        dynamicTable.addRow(StringRS.RULES_TEAM_7);
    }

    @Override
    public void configureProperties()
    {

        this.setBorder(new EmptyBorder(10, 48, 10, 48));
        this.setBackground(ColorRS.WHITE);
    }

    @Override
    public void configureComponents()
    {
        ConfigureComponent.super.configureComponents();
        lblTitle = new JLabel();
        lblTitle.setText(StringRS.RULES);
        lblTitle.setFont(FontRS.changeFont(FontRS.SEGOE_UI_BOLD, Font.PLAIN, 50));

        lblSubtitle = new JLabel();
        lblSubtitle.setForeground(ColorRS.BLACK_80);
        lblSubtitle.setText(StringRS.RULE_DESCRIPTION);
        lblSubtitle.setFont(FontRS.changeFont(FontRS.SEGOE_UI_REGULAR, Font.PLAIN, 25));

        dynamicTable = new DynamicTable();
        dynamicTable.setModel(getTableModel());

        scrollPane = new JScrollPane();
        scrollPane.setViewportView(dynamicTable);
        dynamicTable.fixTable(scrollPane);
    }

    @Override
    public void configureLayout()
    {
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(lblTitle,  GroupLayout.DEFAULT_SIZE,
                        300, Short.MAX_VALUE)
                .addComponent(lblSubtitle, GroupLayout.DEFAULT_SIZE,
                        300, Short.MAX_VALUE)
                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE,
                        500, Short.MAX_VALUE)

        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(20,20,20)
                .addComponent(lblTitle,  GroupLayout.DEFAULT_SIZE,
                        40, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblSubtitle, GroupLayout.DEFAULT_SIZE,
                        40, GroupLayout.PREFERRED_SIZE)
                .addGap(10,10,10)
                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE,
                        500, Short.MAX_VALUE)
        );
    }

    @Override
    public void update(@NotNull ActivityManager activityManager)
    {

        if(activityManager.isAncestor(this))
        {
            NavBar.getInstance().navBarElements(false, false);
            dynamicTable.clearSelection();
        }
    }
}
