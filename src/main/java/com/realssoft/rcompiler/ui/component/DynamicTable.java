package com.realssoft.rcompiler.ui.component;

import com.realssoft.rcompiler.ui.support.scroll.ScrollBarCustom;
import com.realssoft.rcompiler.ui.values.ColorRS;
import com.realssoft.rcompiler.ui.values.ConfigureComponent;
import org.jetbrains.annotations.NotNull;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.Color;
import java.awt.Component;

public class DynamicTable extends JTable implements ConfigureComponent
{
    public DynamicTable()
    {
        super();
       configureProperties();
    }

    public void addRow(Object[] row)
    {
        DefaultTableModel mod = (DefaultTableModel) getModel();
        mod.addRow(row);
    }

    public void fixTable(@NotNull JScrollPane scroll)
    {
        scroll.getViewport().setBackground(ColorRS.WHITE);
        scroll.setVerticalScrollBar(new ScrollBarCustom());
        JPanel p = new JPanel();
        p.setBackground(ColorRS.WHITE);
        scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p);
        scroll.setBackground(Color.WHITE);
        scroll.setBorder(new EmptyBorder(5, 10, 5, 10));
    }

    @Override
    public void configureProperties()
    {
        this.setShowHorizontalLines(true);
        this.setGridColor(new Color(230, 230, 230));
        this.setRowHeight(40);
        this.getTableHeader().setReorderingAllowed(false);
        this.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln,
                                                           boolean bln1, int i, int i1)
            {
                TableHeader header = new TableHeader(String.valueOf(o));
                if (i1 == 4)
                {
                    header.setHorizontalAlignment(JLabel.CENTER);
                }
                return header;
            }
        });
        this.setDefaultRenderer(Object.class, new TextAreaCellRenderer(-1));
    }

    @Override
    public TableCellEditor getCellEditor(int row, int col)
    {
        return super.getCellEditor(row, col);
    }

}
