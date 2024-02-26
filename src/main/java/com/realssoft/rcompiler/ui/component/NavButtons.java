package com.realssoft.rcompiler.ui.component;

import com.realssoft.materialdesign.MaterialDesignIcon;
import com.realssoft.materialdesign.MicrosoftSegoeIcon;
import com.realssoft.rcompiler.ui.values.ButtonEvent;
import com.realssoft.rcompiler.ui.values.ColorRS;
import com.realssoft.rcompiler.ui.values.ConfigureComponent;
import com.realssoft.rcompiler.ui.values.model.EventColorModel;
import com.realssoft.rcompiler.ui.values.model.SquareButtonModel;
import com.realssoft.rcompiler.ui.config.ConfigProperties;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GraphicsEnvironment;

public class NavButtons extends JPanel implements ConfigureComponent
{

    private SquareButton btnClose;
    private SquareButton btnMinimize;
    private SquareButton btnResize;
    private MaterialDesignIcon iconMax;
    private MaterialDesignIcon iconRestore;
    private SquareButtonModel maxRestoreModel;
    private ConfigProperties configProperties;

    public NavButtons()
    {
        super();
        configureProperties();
        configureComponents();
        configureLayout();
    }

    public void validateFrameState()
    {
        if(configProperties.isSystemStateMaximized())
        {
            maxRestoreModel.setIcon(iconRestore);
            return;
        }
        maxRestoreModel.setIcon(iconMax);
    }

    private void changeFrameState(boolean state)
    {
        configProperties.setSystemStateMaximized(state);
        validateFrameState();
    }

    @Override
    public void configureProperties()
    {
        ConfigureComponent.super.configureProperties();
        this.setBorder(new EmptyBorder(3,0,7,0));
        this.setBackground(ColorRS.WHITE);
    }

    @Override
    public void configureFrameEvent(JFrame frame)
    {
        ConfigureComponent.super.configureFrameEvent(frame);
        frame.addWindowStateListener(we ->
        {
            if (we.getNewState() == JFrame.MAXIMIZED_BOTH)
            {
                changeFrameState(true);
                return;
            }
            if (we.getNewState() == JFrame.NORMAL)
            {
                changeFrameState(false);
                configProperties.setSystemLocation(frame.getLocation());
            }
        });
        btnClose.addActionListener(ae ->
        {
            configProperties.close();
            System.exit(0);
        });
        btnResize.addActionListener(ae ->
        {
            if (frame.getExtendedState() == JFrame.MAXIMIZED_BOTH)
            {
                frame.setSize(configProperties.getSystemSizeWidth(),
                        configProperties.getSystemSizeHeight());
                frame.setLocation(configProperties.getSystemLocationX(),
                        configProperties.getSystemLocationY());
                frame.setExtendedState(JFrame.NORMAL);
                return;
            }
            frame.setMaximizedBounds(GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getMaximumWindowBounds());
            frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        });
        btnMinimize.addActionListener(ae -> frame.setState(JFrame.ICONIFIED));
    }

    @Override
    public void configureComponents()
    {
        ConfigureComponent.super.configureComponents();
        configProperties = ConfigProperties.getInstance();
        iconMax = new MaterialDesignIcon();
        iconMax.setSingleColor(ColorRS.WHITE);
        iconMax.setIcon(MicrosoftSegoeIcon.CHROME_MAXIMIZE);
        iconMax.setSize(13);

        iconRestore = new MaterialDesignIcon();
        iconRestore.setSingleColor(ColorRS.WHITE);
        iconRestore.setIcon(MicrosoftSegoeIcon.CHROME_RESTORE);
        iconRestore.setSize(14);

        MaterialDesignIcon iconClose = new MaterialDesignIcon();
        iconClose.setSingleColor(ColorRS.WHITE);
        iconClose.setIcon(MicrosoftSegoeIcon.CHROME_CLOSE);
        iconClose.setSize(13);

        MaterialDesignIcon iconMinimize = new MaterialDesignIcon();
        iconMinimize.setSingleColor(ColorRS.WHITE);
        iconMinimize.setIcon(MicrosoftSegoeIcon.CHROME_MINIMIZE);
        iconMinimize.setSize(15);

        EventColorModel eventModelMinMax = new EventColorModel();
        eventModelMinMax.setEnteredColor(ColorRS.SELECTED_ICON_TRANSPARENT_GRAY);
        eventModelMinMax.setExitedColor(ColorRS.WHITE);

        EventColorModel eventModelClose = new EventColorModel();
        eventModelClose.setEnteredColor(ColorRS.RED);
        eventModelClose.setExitedColor(ColorRS.WHITE);

        SquareButtonModel minModel = new SquareButtonModel();
        minModel.setIconColor(ColorRS.BLACK);
        minModel.setIcon(iconMinimize);
        minModel.setBackgroundColor(eventModelMinMax.getExitedColor());

        SquareButtonModel closeModel = new SquareButtonModel();
        closeModel.setIconColor(ColorRS.BLACK);
        closeModel.setIcon(iconClose);
        closeModel.setBackgroundColor(eventModelClose.getExitedColor());
        closeModel.setDynamicColor(true);

        maxRestoreModel = new SquareButtonModel();
        maxRestoreModel.setIconColor(ColorRS.BLACK);
        maxRestoreModel.setIcon(iconRestore);
        maxRestoreModel.setBackgroundColor(eventModelClose.getExitedColor());

        btnClose = new SquareButton();
        btnClose.setSquareButtonModel(closeModel);
        btnClose.setEventColorModel(eventModelClose);
        btnClose.setButtonEvent(ButtonEvent.EXITED);

        btnMinimize = new SquareButton();
        btnMinimize.setSquareButtonModel(minModel);
        btnMinimize.setEventColorModel(eventModelMinMax);
        btnMinimize.setButtonEvent(ButtonEvent.EXITED);

        btnResize = new SquareButton();
        btnResize.setSquareButtonModel(maxRestoreModel);
        btnResize.setEventColorModel(eventModelMinMax);
        btnResize.setButtonEvent(ButtonEvent.EXITED);
    }

    @Override
    public void configureLayout()
    {
        ConfigureComponent.super.configureLayout();
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnMinimize, GroupLayout.PREFERRED_SIZE,
                                60, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnResize, GroupLayout.PREFERRED_SIZE,
                                60, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnClose, GroupLayout.PREFERRED_SIZE,
                                60, GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(btnClose, GroupLayout.DEFAULT_SIZE,
                        42, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnResize, GroupLayout.DEFAULT_SIZE,
                        42, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnMinimize, GroupLayout.DEFAULT_SIZE,
                        42, GroupLayout.PREFERRED_SIZE)
        );
    }

}
