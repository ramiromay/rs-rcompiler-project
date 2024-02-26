package com.realssoft;

import com.realssoft.rcompiler.logic.model.TokenModel;
import com.realssoft.rcompiler.logic.model.TokenErrorModel;
import com.realssoft.rcompiler.logic.model.TriploModel;
import com.realssoft.rcompiler.ui.activity.ActivityManager;
import com.realssoft.rcompiler.ui.component.CardView;
import com.realssoft.rcompiler.ui.component.Menu;
import com.realssoft.rcompiler.ui.component.PanelTransparent;
import com.realssoft.rcompiler.ui.component.NavBar;
import com.realssoft.rcompiler.ui.support.dialog.GlassPanePopup;
import com.realssoft.rcompiler.ui.support.notification.Notifications;
import com.realssoft.rcompiler.ui.values.ColorRS;
import com.realssoft.rcompiler.ui.values.ConfigureComponent;
import com.realssoft.rcompiler.ui.values.PathRS;
import com.realssoft.rcompiler.ui.config.ConfigProperties;
import net.miginfocom.swing.MigLayout;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Main extends JFrame implements ConfigureComponent
{

    public static final Map<String, TokenModel> TOKEN_MAP = new HashMap<>();
    public static final Map<String, TokenErrorModel> TOKEN_ERROR_MAP = new HashMap<>();
    public static final Map<Integer, TriploModel> TRIPLO_MAP = new HashMap<>();

    private ConfigProperties configProperties;
    private PanelTransparent mainWindow;
    private NavBar navBar;

    private Main()
    {

        super();
        configureProperties();
        configureBorder();
        configureLayout();
        configureComponents();
        loadConfiguration();
        navBar.setResizable(this);
        GlassPanePopup.install(this);
    }

    @Override
    public void configureProperties()
    {
        configProperties = ConfigProperties.getInstance();
        mainWindow = new PanelTransparent();
        navBar = NavBar.getInstance();

        this.setIconImage(new ImageIcon(Objects.requireNonNull(CardView.class
                .getResource(PathRS.ROUNDED_COMPANY_lOGO_IMAGE))).getImage());
    }

    @Override
    public void configureComponents()
    {
        MigLayout layout = new MigLayout("fill", "0[]0[100%, fill]0", "0[fill]0");
        mainWindow.setLayout(layout);

        Menu menu = new Menu();
        mainWindow.add(menu, "w 250!");

        ActivityManager container = ActivityManager.getInstance();
        mainWindow.add(container, "w 100%, h 100%");
    }

    @Override
    public void configureLayout() {
        int horizontal = configProperties.getSystemSizeWidth() - 2;
        int vertical = configProperties.getSystemSizeHeight();

        GroupLayout maiWindowLayout = new GroupLayout(mainWindow);
        mainWindow.setLayout(maiWindowLayout);
        maiWindowLayout.setHorizontalGroup(
                maiWindowLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, horizontal, Short.MAX_VALUE)
        );
        maiWindowLayout.setVerticalGroup(
                maiWindowLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, vertical - 52, Short.MAX_VALUE)
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(navBar, GroupLayout.DEFAULT_SIZE,
                        horizontal, Short.MAX_VALUE)
                .addComponent(mainWindow, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(navBar, GroupLayout.PREFERRED_SIZE,
                                50, GroupLayout.PREFERRED_SIZE)
                        .addComponent(mainWindow, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))

        );
        this.pack();
    }

    @Override
    public void configureBorder() {
        Border outerBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
        Border innerBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 0, 0, 0),
                BorderFactory.createLineBorder(ColorRS.UNSELECTED_ICON_GRAY, 1)
        );
        Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
        this.setUndecorated(true);
        this.getRootPane().setBorder(compoundBorder);
    }

    private void loadConfiguration()
    {
        if(configProperties.isSystemBootConfiguration())
        {
            this.setLocationRelativeTo(null);
            configProperties.setSystemBootConfiguration(false);
            return;
        }

        if(configProperties.isSystemStateMaximized())
        {
            this.setMaximizedBounds(GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getMaximumWindowBounds());
            this.setExtendedState(this.getExtendedState() | Frame.MAXIMIZED_BOTH);
            return;
        }

        if(configProperties.isSystemMoved())
        {
            this.setLocation(configProperties.getSystemLocationX(),
                    configProperties.getSystemLocationY());
            return;
        }
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        EventQueue.invokeLater(() ->
        {
            try
            {
                Main main = new Main();
                main.setVisible(true);
                Notifications.getInstance().setJFrame(main);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

}
