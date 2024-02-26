package com.realssoft.rcompiler.ui.component;

import com.realssoft.Main;
import com.realssoft.materialdesign.MaterialDesignIcon;
import com.realssoft.materialdesign.MicrosoftSegoeIcon;
import com.realssoft.rcompiler.logic.utils.MapUtils;
import com.realssoft.rcompiler.ui.support.dialog.GlassPanePopup;
import com.realssoft.rcompiler.ui.values.ButtonEvent;
import com.realssoft.rcompiler.ui.values.ColorRS;
import com.realssoft.rcompiler.ui.values.ConfigureComponent;
import com.realssoft.rcompiler.ui.values.FontRS;
import com.realssoft.rcompiler.ui.values.model.EventColorModel;
import com.realssoft.rcompiler.ui.values.model.RoundButtonModel;
import com.realssoft.rcompiler.ui.values.model.ShadowModel;
import org.jetbrains.annotations.NotNull;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.Graphics;

public class TableMessage extends JPanel implements ConfigureComponent
{

    private DynamicTable table;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private RoundButton button;
    private final boolean isActivate;

    public TableMessage(boolean isActivate)
    {
        this.isActivate = isActivate;
        configureProperties();
        configureComponents();
        configureButtonsEvent();
        configureLayout();
        showData();
    }

    private void showData()
    {
        if(isActivate)
        {
            model.addColumn("TOKEN");
            model.addColumn("LEXEMA");
            model.addColumn("FILA(S)");
            model.addColumn("DESCRIPCIÓN");
            Main.TOKEN_ERROR_MAP.forEach((key, value) -> model.addRow(
                    new Object[] {
                            value.getTokenModel()
                                    .getToken(),
                            value.getTokenModel()
                                    .getLexeme(),
                            MapUtils.getRowsError(value),
                            value.getDescription()
                    }
            ));

            return;
        }
        model.addColumn("LEXEMA");
        model.addColumn("TOKEN");
        model.addColumn("TIPO");
        Main.TOKEN_MAP.forEach((key, value) -> model.addRow(
                new Object[]{
                        value.getLexeme(),
                        value.getToken(),
                        value.getDatatype()
                })
        );
        table.removeAll();
        table.setModel(model);
    }

    @Override
    public void configureProperties() {
        this.setBackground(ColorRS.WHITE);
    }

    @Override
    public void configureComponents() {
        ConfigureComponent.super.configureComponents();
        ShadowModel shadowModel = new ShadowModel();
        shadowModel.setShadowSize(7);
        shadowModel.setShadowOpacity(0.3f);
        shadowModel.setShadowColor(ColorRS.BLACK);

        EventColorModel eventColorModel = new EventColorModel();
        eventColorModel.setEnteredColor(ColorRS.WHITE);
        eventColorModel.setExitedColor(ColorRS.WHITE);

        MaterialDesignIcon buttonIcon = new MaterialDesignIcon();
        buttonIcon.setSingleColor(ColorRS.SELECTED_ICON_BLUE);
        buttonIcon.setColorIcon(ColorRS.RED);
        buttonIcon.setIcon(MicrosoftSegoeIcon.STATUS_ERROR_FULL);
        buttonIcon.setSize(22);

        RoundButtonModel buttonModel = new RoundButtonModel();
        buttonModel.setIcon(buttonIcon.toIcon());
        buttonModel.setText("Cerrar");
        buttonModel.setTextColor(ColorRS.RED);
        buttonModel.setBackgroundColor(ColorRS.WHITE);
        buttonModel.setCornerRadius(20);

        model = new DefaultTableModel();
        table = new DynamicTable();
        table.setModel(model);

        scrollPane = new JScrollPane();
        scrollPane.setViewportView(table);
        table.fixTable(scrollPane);

        button = new RoundButton();
        button.setCenterText(true);
        button.setShadowModel(shadowModel);
        button.setRoundButtonModel(buttonModel);
        button.setEventColorModel(eventColorModel);
        button.setFont(FontRS.changeFont(FontRS.SEGOE_UI_REGULAR, Font.PLAIN, 22));
        button.setButtonEvent(ButtonEvent.EXITED);
    }

    @Override
    public void configureButtonsEvent() {
        ConfigureComponent.super.configureButtonsEvent();
        button.addActionListener(ae-> GlassPanePopup.closePopupLast());
    }

    @Override
    public void configureLayout() {
        ConfigureComponent.super.configureLayout();
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE,
                                700, Short.MAX_VALUE)
                        .addComponent(button, GroupLayout.DEFAULT_SIZE,
                                100, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE,
                                500, GroupLayout.PREFERRED_SIZE)
                        .addGap(10,10,10)
                        .addComponent(button, GroupLayout.DEFAULT_SIZE,
                               70, Short.MAX_VALUE)
        );

    }

    @Override
    protected void paintComponent(@NotNull Graphics g) {
        int width = getWidth();
        int height = getHeight();
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, width - 1, height - 1, 20, 20);
    }

}
