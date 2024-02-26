package com.realssoft.rcompiler.ui.component;

import com.realssoft.materialdesign.MaterialDesignIcon;
import com.realssoft.materialdesign.MicrosoftSegoeIcon;
import com.realssoft.rcompiler.ui.activity.ActivityManager;
import com.realssoft.rcompiler.ui.activity.home.HomeActivity;
import com.realssoft.rcompiler.ui.values.ButtonEvent;
import com.realssoft.rcompiler.ui.values.ColorRS;
import com.realssoft.rcompiler.ui.values.ConfigureComponent;
import com.realssoft.rcompiler.ui.values.model.EventColorModel;
import com.realssoft.rcompiler.ui.values.model.SquareButtonModel;
import com.realssoft.rcompiler.ui.values.observers.IEventObservable;
import com.realssoft.rcompiler.ui.values.observers.IEventObserver;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;

public class PanelSolid extends JPanel implements ConfigureComponent, IEventObservable
{

    private SquareButton btnBack;
    private ActivityManager activityManager;
    private ArrayList<IEventObserver> eventObservers;

    public PanelSolid()
    {
        super();
        configureProperties();
        configureComponents();
        configureButtonsEvent();
        configureLayout();
    }

    public void setVisibleButton(boolean isVisible)
    {
        btnBack.setVisible(isVisible);
    }

    @Override
    public void configureProperties()
    {
        ConfigureComponent.super.configureProperties();
        this.setBackground(ColorRS.WHITE);
        this.setBorder(new EmptyBorder(5,20,5,0));
    }

    @Override
    public void configureComponents()
    {
        ConfigureComponent.super.configureComponents();
        eventObservers = new ArrayList<>();
        activityManager = ActivityManager.getInstance();
        MaterialDesignIcon icon = new MaterialDesignIcon();
        icon.setSingleColor(ColorRS.WHITE);
        icon.setColorIcon(ColorRS.RED);
        icon.setIcon(MicrosoftSegoeIcon.CHROME_BACK);
        icon.setSize(18);

        EventColorModel colorModel = new EventColorModel();
        colorModel.setEnteredColor(ColorRS.SELECTED_ICON_TRANSPARENT_GRAY);
        colorModel.setExitedColor(ColorRS.WHITE);

        SquareButtonModel model = new SquareButtonModel();
        model.setIcon(icon);
        model.setIconColor(ColorRS.BLACK);
        model.setBackgroundColor(colorModel.getExitedColor());

        btnBack = new SquareButton();
        btnBack.setSquareButtonModel(model);
        btnBack.setEventColorModel(colorModel);
        btnBack.setButtonEvent(ButtonEvent.EXITED);
    }

    @Override
    public void configureButtonsEvent() {
        btnBack.addActionListener(ae -> {
            notifyEvent();
            activityManager.showActivity(HomeActivity.getInstance());
        });
    }

    @Override
    public void configureLayout()
    {
        ConfigureComponent.super.configureLayout();
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(btnBack, GroupLayout.DEFAULT_SIZE,
                        40, GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(btnBack, GroupLayout.DEFAULT_SIZE,
                        40, GroupLayout.PREFERRED_SIZE)
        );
    }

    @Override
    public void addObserver(IEventObserver iEventObserver)
    {
        eventObservers.add(iEventObserver);
    }

    @Override
    public void notifyEvent()
    {
        eventObservers.forEach(x -> x.update(ActivityManager.getInstance()));
    }
}
