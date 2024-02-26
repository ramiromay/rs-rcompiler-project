package com.realssoft.rcompiler.ui.component;

import com.realssoft.materialdesign.MaterialDesignIcon;
import com.realssoft.materialdesign.MicrosoftSegoeIcon;
import com.realssoft.rcompiler.ui.activity.ActivityManager;
import com.realssoft.rcompiler.ui.activity.about.AboutActivity;
import com.realssoft.rcompiler.ui.activity.home.HomeActivity;
import com.realssoft.rcompiler.ui.values.ButtonEvent;
import com.realssoft.rcompiler.ui.values.ColorRS;
import com.realssoft.rcompiler.ui.values.ConfigureComponent;
import com.realssoft.rcompiler.ui.values.FontRS;
import com.realssoft.rcompiler.ui.values.StringRS;
import com.realssoft.rcompiler.ui.values.model.EventColorModel;
import com.realssoft.rcompiler.ui.values.model.RoundButtonModel;
import org.jetbrains.annotations.NotNull;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;

public class MenuButtons extends JPanel implements ConfigureComponent
{

    private ActivityManager activityManager;
    private MaterialDesignIcon homeIcon;
    private MaterialDesignIcon aboutIcon;
    private RoundButtonModel btnHomeModel;
    private RoundButtonModel btnAboutModel;
    private RoundButton btnHome;
    private RoundButton btnAbout;

    public MenuButtons()
    {
        super();
        configureProperties();
        configureComponents();
        configureButtonsEvent();
        configureButtonsEvent();
        configureLayout();
    }

    private void changeButton(@NotNull RoundButton btnCurrent,
                              @NotNull MaterialDesignIcon iconBtnCurrent,
                              @NotNull RoundButtonModel modelBtnCurrent,
                              @NotNull RoundButton btnNewCurrent,
                              @NotNull MaterialDesignIcon iconBtnNewCurrent,
                              @NotNull RoundButtonModel modelBtnNewCurrent)
    {

        modelBtnCurrent.setTextColor(ColorRS.SELECTED_ICON_BLUE);
        modelBtnCurrent.setBackgroundColor(ColorRS.SELECTED_BACKGROUND_BLUE);
        modelBtnCurrent.setIcon(iconBtnCurrent.toIcon(ColorRS.SELECTED_ICON_BLUE));
        btnCurrent.setFont(FontRS.changeFont(FontRS.SEGOE_UI_BOLD, Font.PLAIN, 20));
        btnCurrent.setButtonEvent(ButtonEvent.PRESSED);
        btnCurrent.repaint();

        modelBtnNewCurrent.setTextColor(ColorRS.BLACK_80);
        modelBtnNewCurrent.setBackgroundColor(ColorRS.TRANSPARENT_BACKGROUND_100);
        modelBtnNewCurrent.setIcon(iconBtnNewCurrent.toIcon(ColorRS.UNSELECTED_ICON_GRAY));
        btnNewCurrent.setFont(FontRS.changeFont(FontRS.SEGOE_UI_REGULAR, Font.PLAIN, 20));
        btnNewCurrent.setButtonEvent(ButtonEvent.EXITED);
        btnNewCurrent.repaint();
    }

    @Override
    public void configureProperties()
    {
        ConfigureComponent.super.configureProperties();
        this.setOpaque(false);
        this.setBorder(new EmptyBorder(0,15,0,15));
    }

    @Override
    public void configureComponents()
    {
        ConfigureComponent.super.configureComponents();
        EventColorModel eventColorModel = new EventColorModel();
        eventColorModel.setEnteredColor(ColorRS.ENTERED_ICON_GRAY);
        eventColorModel.setExitedColor(ColorRS.TRANSPARENT_BACKGROUND_100);

        activityManager = ActivityManager.getInstance();
        activityManager.showActivity(HomeActivity.getInstance());

        homeIcon = new MaterialDesignIcon();
        homeIcon.setSingleColor(ColorRS.SELECTED_ICON_BLUE);
        homeIcon.setColorIcon(ColorRS.SELECTED_ICON_BLUE);
        homeIcon.setIcon(MicrosoftSegoeIcon.HOME_SOLID);
        homeIcon.setSize(22);

        aboutIcon = new MaterialDesignIcon();
        aboutIcon.setSingleColor(ColorRS.UNSELECTED_ICON_GRAY);
        aboutIcon.setColorIcon(ColorRS.UNSELECTED_ICON_GRAY);
        aboutIcon.setIcon(MicrosoftSegoeIcon.INFO_SOLID);
        aboutIcon.setSize(22);

        btnHomeModel = new RoundButtonModel();
        btnHomeModel.setIcon(homeIcon.toIcon());
        btnHomeModel.setText(StringRS.MENU_BUTTON_HOME);
        btnHomeModel.setTextColor(ColorRS.SELECTED_ICON_BLUE);
        btnHomeModel.setBackgroundColor(ColorRS.SELECTED_BACKGROUND_BLUE);
        btnHomeModel.setCornerRadius(22);

        btnAboutModel = new RoundButtonModel();
        btnAboutModel.setIcon(aboutIcon.toIcon());
        btnAboutModel.setText(StringRS.MENU_BUTTON_ABOUT);
        btnAboutModel.setTextColor(ColorRS.BLACK_80);
        btnAboutModel.setBackgroundColor(ColorRS.TRANSPARENT_BACKGROUND_100);
        btnAboutModel.setCornerRadius(22);

        btnHome = new RoundButton();
        btnHome.setRoundButtonModel(btnHomeModel);
        btnHome.setEventColorModel(eventColorModel);
        btnHome.setFont(FontRS.changeFont(FontRS.SEGOE_UI_BOLD, Font.PLAIN, 20));
        btnHome.setButtonEvent(ButtonEvent.PRESSED);

        btnAbout = new RoundButton();
        btnAbout.setRoundButtonModel(btnAboutModel);
        btnAbout.setEventColorModel(eventColorModel);
        btnAbout.setFont(FontRS.changeFont(FontRS.SEGOE_UI_REGULAR, Font.PLAIN, 20));
        btnAbout.setButtonEvent(ButtonEvent.EXITED);
    }

    @Override
    public void configureButtonsEvent()
    {
        ConfigureComponent.super.configureButtonsEvent();
        btnHome.addActionListener(e ->
        {
            if(btnHome.getButtonEvent() != ButtonEvent.PRESSED)
            {
                activityManager.showActivity(HomeActivity.getInstance());
                changeButton(btnHome, homeIcon, btnHomeModel,
                        btnAbout, aboutIcon, btnAboutModel);
            }

        });

        btnAbout.addActionListener(e ->
        {
            if(btnAbout.getButtonEvent() != ButtonEvent.PRESSED)
            {
                activityManager.showActivity(AboutActivity.getInstance());
                changeButton(btnAbout, aboutIcon, btnAboutModel,
                        btnHome, homeIcon, btnHomeModel);
                NavBar.getInstance().navBarElements(false, false);
            }

        });
    }

    @Override
    public void configureLayout()
    {
        ConfigureComponent.super.configureLayout();
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(btnHome, GroupLayout.PREFERRED_SIZE,
                        220, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnAbout, GroupLayout.PREFERRED_SIZE,
                        220, GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnHome, GroupLayout.DEFAULT_SIZE,
                                48, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAbout, GroupLayout.DEFAULT_SIZE,
                                48, GroupLayout.PREFERRED_SIZE))
        );
    }

}
