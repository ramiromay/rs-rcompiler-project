package com.realssoft.rcompiler.ui.activity.home;

import com.realssoft.rcompiler.ui.activity.ActivityManager;
import com.realssoft.rcompiler.ui.component.DynamicTable;
import com.realssoft.rcompiler.ui.component.NavBar;
import com.realssoft.rcompiler.ui.values.ColorRS;
import com.realssoft.rcompiler.ui.values.ConfigureComponent;
import com.realssoft.rcompiler.ui.values.FontRS;
import com.realssoft.rcompiler.ui.values.StringRS;
import com.realssoft.rcompiler.ui.values.observers.IEventObserver;
import org.jetbrains.annotations.NotNull;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;

public class AlphabeticActivity extends JPanel implements ConfigureComponent, IEventObserver<ActivityManager>
{

    private static final AlphabeticActivity INSTANCE = new AlphabeticActivity();
    private JLabel lblTitle;
    private JLabel lblSubtitle;
    private JScrollPane scrollPane;
    private DynamicTable dynamicTable;

    private AlphabeticActivity()
    {
        super();
        configureProperties();
        configureComponents();
        configureButtonsEvent();
        configureLayout();
        initializeData();

    }

    public static AlphabeticActivity getInstance()
    {
        return INSTANCE;
    }

    private DefaultTableModel getTableModel()
    {
        return new DefaultTableModel(new Object [][] {}, StringRS.ALPHABETIC_COLUMN_TABLE)
        {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return false;
            }
        };
    }

    private void initializeData()
    {
        Object [] row = new Object[2];
        char character;
        for(int i = 33; i <= 125; i ++)
        {
            if(i == 33 || i >= 37 && i <= 38 || i >= 40 && i <= 62 || i >= 65 && i <= 91 || i == 93 || i >= 97)
            {
                character = (char) i;
                row[0] = character;
                row[1] = i;
                dynamicTable.addRow(row);
            }
        }
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
        lblTitle.setText(StringRS.ALPHABETIC);
        lblTitle.setFont(FontRS.changeFont(FontRS.SEGOE_UI_BOLD, Font.PLAIN, 50));

        lblSubtitle = new JLabel();
        lblSubtitle.setForeground(ColorRS.BLACK_80);
        lblSubtitle.setText(StringRS.ALPHABETIC_DESCRIPTION);
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
