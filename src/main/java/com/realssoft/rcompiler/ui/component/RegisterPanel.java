package com.realssoft.rcompiler.ui.component;

import com.realssoft.materialdesign.MaterialDesignIcon;
import com.realssoft.materialdesign.MicrosoftSegoeIcon;
import com.realssoft.rcompiler.ui.support.dialog.GlassPanePopup;
import com.realssoft.rcompiler.ui.values.ButtonEvent;
import com.realssoft.rcompiler.ui.values.ColorRS;
import com.realssoft.rcompiler.ui.values.ConfigureComponent;
import com.realssoft.rcompiler.ui.values.model.EventColorModel;
import com.realssoft.rcompiler.ui.values.model.SquareButtonModel;
import javax.swing.*;
import javax.swing.border.*;

public class RegisterPanel extends JPanel implements ConfigureComponent
{

    private SquareButton btnRestore;
    private SquareButton btnTokenTable;
    private SquareButton btnErrorTable;
    private MaterialDesignIcon iconMinimize;
    private MaterialDesignIcon iconRestore;
    private SquareButtonModel modelRestore;

    public RegisterPanel()
    {
        super();
        configureProperties();
        configureBorder();
        configureComponents();
        configureButtonsEvent();
        configureLayout();
    }

    @Override
    public void configureProperties()
    {
        ConfigureComponent.super.configureProperties();
        this.setBackground(ColorRS.WHITE);
    }

    @Override
    public void configureBorder() {
        ConfigureComponent.super.configureBorder();
        ConfigureComponent.super.configureBorder();
        Border outerBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
        Border innerBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 1, 1, 1, ColorRS.UNSELECTED_ICON_GRAY),
                BorderFactory.createEmptyBorder(1, 1, 1, 0));
        Border comBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);

        this.setBorder(comBorder);
    }

    @Override
    public void configureComponents()
    {
        ConfigureComponent.super.configureComponents();
        iconMinimize = new MaterialDesignIcon();
        iconMinimize.setSingleColor(ColorRS.WHITE);
        iconMinimize.setIcon(MicrosoftSegoeIcon.SHOW_BCC);
        iconMinimize.setSize(18);

        iconRestore = new MaterialDesignIcon();
        iconRestore.setSingleColor(ColorRS.WHITE);
        iconRestore.setIcon(MicrosoftSegoeIcon.HIDE_BCC);
        iconRestore.setSize(18);

        MaterialDesignIcon iconTokenTable = new MaterialDesignIcon();
        iconTokenTable.setSingleColor(ColorRS.WHITE);
        iconTokenTable.setIcon(MicrosoftSegoeIcon.TRACKERS);
        iconTokenTable.setSize(18);

        MaterialDesignIcon iconErrorTable = new MaterialDesignIcon();
        iconErrorTable.setSingleColor(ColorRS.WHITE);
        iconErrorTable.setIcon(MicrosoftSegoeIcon.DIAGNOSTIC);
        iconErrorTable.setSize(18);

        EventColorModel eventModel = new EventColorModel();
        eventModel.setEnteredColor(ColorRS.SELECTED_ICON_TRANSPARENT_GRAY);
        eventModel.setExitedColor(ColorRS.WHITE);

        modelRestore = new SquareButtonModel();
        modelRestore.setIconColor(ColorRS.BLACK);
        modelRestore.setIcon(iconMinimize);
        modelRestore.setBackgroundColor(eventModel.getExitedColor());

        SquareButtonModel modelTT = new SquareButtonModel();
        modelTT.setIconColor(ColorRS.BLACK);
        modelTT.setIcon(iconTokenTable);
        modelTT.setBackgroundColor(eventModel.getExitedColor());

        SquareButtonModel modelET = new SquareButtonModel();
        modelET.setIconColor(ColorRS.BLACK);
        modelET.setIcon(iconErrorTable);
        modelET.setBackgroundColor(eventModel.getExitedColor());

        btnRestore = new SquareButton();
        btnRestore.setSquareButtonModel(modelRestore);
        btnRestore.setEventColorModel(eventModel);
        btnRestore.setButtonEvent(ButtonEvent.EXITED);

        btnTokenTable = new SquareButton();
        btnTokenTable.setSquareButtonModel(modelTT);
        btnTokenTable.setEventColorModel(eventModel);
        btnTokenTable.setButtonEvent(ButtonEvent.EXITED);

        btnErrorTable = new SquareButton();
        btnErrorTable.setSquareButtonModel(modelET);
        btnErrorTable.setEventColorModel(eventModel);
        btnErrorTable.setButtonEvent(ButtonEvent.EXITED);
    }

    @Override
    public void configureButtonsEvent() {
        ConfigureComponent.super.configureButtonsEvent();
        btnTokenTable.addActionListener(ae->
                GlassPanePopup.showPopup(new TableMessage(false))
        );
        btnErrorTable.addActionListener(ae ->
                GlassPanePopup.showPopup(new TableMessage(true))
        );
    }

    @Override
    public void configureLayout()
    {
        ConfigureComponent.super.configureLayout();
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnRestore, GroupLayout.PREFERRED_SIZE,
                                42, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnTokenTable, GroupLayout.PREFERRED_SIZE,
                                42, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnErrorTable, GroupLayout.PREFERRED_SIZE,
                                42, GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(btnRestore, GroupLayout.DEFAULT_SIZE,
                        42, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnTokenTable, GroupLayout.DEFAULT_SIZE,
                        42, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnErrorTable, GroupLayout.DEFAULT_SIZE,
                        42, GroupLayout.PREFERRED_SIZE)
        );
    }

}
