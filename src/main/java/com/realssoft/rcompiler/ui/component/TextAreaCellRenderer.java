package com.realssoft.rcompiler.ui.component;

import com.realssoft.rcompiler.ui.values.ColorRS;
import com.realssoft.rcompiler.ui.values.FontRS;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class TextAreaCellRenderer extends JTextArea implements TableCellRenderer
{

    private final List<List<Integer>> rowAndCellHeights = new ArrayList<>();
    private final int hoverRow;

    public TextAreaCellRenderer(int hoverRow)
    {
        this.hoverRow = hoverRow;
        this.setWrapStyleWord(true);
        this.setLineWrap(true);
        this.setOpaque(true);
        this.setBorder(new EmptyBorder(8, 10, 8, 10));
        this.setFont(FontRS.changeFont(FontRS.SEGOE_UI_REGULAR, Font.PLAIN, 18));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column)
    {
        this.setText(Objects.toString(value, ""));
        this.adjustRowHeight(table, row, column);
        this.setForeground(ColorRS.TEXT_COLOR_TABLE);
        if (isSelected)
        {
            this.setBackground(ColorRS.SELECT_ROW);
        }
        else
        {
            if (row == hoverRow)
            {
                this.setBackground(ColorRS.HOVER_ROW_COLOR);
            }
            else
            {
                if (row % 2 == 0)
                {
                    this.setBackground(ColorRS.WHITE);
                }
                else
                {
                    this.setBackground(ColorRS.GRAY_2);
                }
            }
        }
        return this;
    }

    private void adjustRowHeight(JTable table, int row, int column)
    {

        this.setBounds(table.getCellRect(row, column, false));
        int preferredHeight = getPreferredSize().height;
        while (rowAndCellHeights.size() <= row)
        {
            rowAndCellHeights.add(new ArrayList<>(column));
        }
        List<Integer> list = rowAndCellHeights.get(row);
        while (list.size() <= column)
        {
            list.add(0);
        }
        list.set(column, preferredHeight);
        int max = list.stream().max(Comparator.comparingInt(x -> x)).get();
        if (table.getRowHeight(row) != max)
        {
            table.setRowHeight(row, max);
        }
    }
}
