package com.realssoft.rcompiler.ui.component;

import com.realssoft.rcompiler.ui.activity.home.AlphabeticActivity;
import com.realssoft.rcompiler.ui.activity.home.CompilerActivity;
import com.realssoft.rcompiler.ui.activity.home.RuleActivity;
import com.realssoft.rcompiler.ui.values.ColorRS;
import com.realssoft.rcompiler.ui.values.ConfigureComponent;
import com.realssoft.rcompiler.ui.config.ConfigProperties;
import net.miginfocom.swing.MigLayout;
import org.jetbrains.annotations.NotNull;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class NavBar extends PanelTransparent implements ConfigureComponent
{

    private static final NavBar INSTANCE = new NavBar();
    private int positionX;
    private int positionY;
    private JPanel panelTransparent;
    private PanelSolid panelSolid;
    private NavButtons titleButtons;
    private CompileButtons compileButtons;
    private ConfigProperties configProperties;

    private NavBar()
    {
        super();
        configureComponents();
    }

    public static NavBar getInstance()
    {
        return INSTANCE;
    }

    public void navBarElements(boolean btnBack, boolean compileButtons)
    {
        this.panelSolid.setVisibleButton(btnBack);
        this.compileButtons.setVisible(compileButtons);
    }

    private @NotNull MouseAdapter applyMouseAdapter(JFrame frame)
    {
        return new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent me)
            {
                if (SwingUtilities.isLeftMouseButton(me)
                        && me.getClickCount() == 2)
                {
                    if (frame.getExtendedState() == JFrame.MAXIMIZED_BOTH)
                    {
                        frame.setExtendedState(JFrame.NORMAL);
                        frame.setSize(configProperties.getSystemSizeWidth(),
                                configProperties.getSystemSizeHeight());
                        frame.setLocation(configProperties.getSystemLocationX(),
                                configProperties.getSystemLocationY());
                        return;
                    }
                    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                }
            }

            @Override
            public void mousePressed(MouseEvent me)
            {
                if (frame.getExtendedState() != JFrame.MAXIMIZED_BOTH
                        && SwingUtilities.isLeftMouseButton(me))
                {
                    positionX = me.getX() + 3;
                    positionY = me.getY() + 3;
                }
            }
        };
    }

    private @NotNull MouseMotionAdapter applyMouseMotionAdapter(JFrame frame)
    {
        return new MouseMotionAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent me)
            {
                configProperties.validateMovement();
                if (SwingUtilities.isLeftMouseButton(me))
                {
                    if (frame.getExtendedState() == JFrame.MAXIMIZED_BOTH)
                    {
                        frame.setExtendedState(JFrame.NORMAL);
                        frame.setLocation(
                                me.getXOnScreen() - positionX,
                                me.getYOnScreen() - positionY
                        );
                        configProperties.setSystemLocation(frame.getLocation());
                        return;
                    }
                    frame.setLocation(frame.getLocation().x + me.getX() - positionX,
                            frame.getLocation().y + me.getY() - positionY);
                    configProperties.setSystemLocation(frame.getLocation());
                    configProperties.setSystemSize(frame.getWidth(), frame.getHeight());
                }
            }
        };
    }

    private void applyMotion(JFrame frame)
    {
        MouseAdapter mouseAdapter = applyMouseAdapter(frame);
        MouseMotionAdapter mouseMotionAdapter = applyMouseMotionAdapter(frame);
        panelSolid.addMouseListener(mouseAdapter);
        panelSolid.addMouseMotionListener(mouseMotionAdapter);
        panelTransparent.addMouseListener(mouseAdapter);
        panelTransparent.addMouseMotionListener(mouseMotionAdapter);
    }

    public void setResizable(JFrame frame)
    {
        ComponentResizer resize = new ComponentResizer();
        resize.setSnapSize(new Dimension(10, 10));
        resize.setMinimumSize(new Dimension(1280, 800));
        resize.setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
        resize.registerComponent(frame);
        applyMotion(frame);
        titleButtons.validateFrameState();
        titleButtons.configureFrameEvent(frame);
    }

    @Override
    public void configureComponents()
    {
        configProperties = ConfigProperties.getInstance();
        MigLayout layout = new MigLayout("fill", "0[]0[100%, fill]0[]0", "0[fill]0");
        this.setLayout(layout);

        panelTransparent = new JPanel();
        panelTransparent.setOpaque(false);
        this.add(panelTransparent,"w 250!, grow");

        panelSolid = new PanelSolid();
        panelSolid.addObserver(AlphabeticActivity.getInstance());
        panelSolid.addObserver(RuleActivity.getInstance());
        panelSolid.addObserver(CompilerActivity.getInstance());
        this.add(panelSolid, "grow, push");
        panelSolid.setVisibleButton(false);

        JPanel cbPanel = new JPanel(new GridBagLayout ());
        cbPanel.setBackground(ColorRS.WHITE);
        cbPanel.setBorder(new EmptyBorder(1,1,1,1));
        this.add(cbPanel, "growx");

        compileButtons = CompileButtons.getInstance();
        cbPanel.add(
                compileButtons,
                new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER,
                GridBagConstraints.CENTER,
                new Insets (0,0,0,0), 0, 0)
        );
        compileButtons.setVisible(false);

        JPanel separator = new JPanel();
        separator.setBackground(ColorRS.WHITE);
        this.add(separator, "w 20!, grow");

        titleButtons = new NavButtons();
        this.add(titleButtons, "w 180!, grow");
    }

    @Override
    public void configureLayout()
    {
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addComponent(panelTransparent, GroupLayout.PREFERRED_SIZE,
                        250, GroupLayout.PREFERRED_SIZE)
                .addComponent(panelSolid,  GroupLayout.PREFERRED_SIZE,
                        150, Short.MAX_VALUE)
                .addComponent(compileButtons, GroupLayout.PREFERRED_SIZE,
                        320, Short.MAX_VALUE)
                .addGap(10,10,10)
                .addComponent(titleButtons,  GroupLayout.PREFERRED_SIZE,
                        180, GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(panelTransparent, GroupLayout.PREFERRED_SIZE,
                        250, Short.MAX_VALUE)
                .addComponent(panelSolid,  GroupLayout.PREFERRED_SIZE,
                        150, Short.MAX_VALUE)
                .addComponent(compileButtons, GroupLayout.PREFERRED_SIZE,
                        150, Short.MAX_VALUE)
                .addComponent(titleButtons,  GroupLayout.PREFERRED_SIZE,
                        180, Short.MAX_VALUE)
        );
    }

}
