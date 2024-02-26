package com.realssoft.rcompiler.ui.component;

import com.realssoft.materialdesign.MaterialDesignIcon;
import com.realssoft.materialdesign.MicrosoftSegoeIcon;
import com.realssoft.rcompiler.logic.file.FileManager;
import com.realssoft.rcompiler.ui.values.ButtonEvent;
import com.realssoft.rcompiler.ui.values.ColorRS;
import com.realssoft.rcompiler.ui.values.ConfigureComponent;
import com.realssoft.rcompiler.ui.values.FontRS;
import com.realssoft.rcompiler.ui.values.model.EventColorModel;
import com.realssoft.rcompiler.ui.values.model.SquareButtonModel;
import com.realssoft.rcompiler.ui.values.observers.IEventObservable;
import com.realssoft.rcompiler.ui.values.observers.IEventObserver;
import com.realssoft.rcompiler.ui.util.Utils;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.ArrayList;

public class CompileButtons extends JPanel
        implements ConfigureComponent, IEventObservable<Integer>
{

    private static final CompileButtons INSTANCE = new CompileButtons();
    private ArrayList<IEventObserver<Integer>> observers;
    private JLabel lblNameFile;
    private SquareButton btnRun;
    private SquareButton btnNewFile;
    private SquareButton btnOpenFile;
    private SquareButton btnSave;
    private FileManager fileManager;
    private int widthLabel = 150;
    private int state = 1;

    private CompileButtons()
    {
        super();
        configureProperties();
        configureComponents();
        configureButtonsEvent();
        configureLayout();
    }

    public static CompileButtons getInstance()
    {
        return INSTANCE;
    }

    public static int getTextWidth(String text, Font font) {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        return fm.stringWidth(text);
    }

    @Override
    public void configureProperties() {
        ConfigureComponent.super.configureProperties();
        this.setBackground(ColorRS.SELECTED_ICON_BLUE);
        this.setBorder(new EmptyBorder(3,6,3,3));
    }

    @Override
    public void configureComponents()
    {
        fileManager = FileManager.getInstance();
        observers = new ArrayList<>();
        EventColorModel colorModel = new EventColorModel();
        colorModel.setEnteredColor(ColorRS.SELECTED_ICON_TRANSPARENT_GRAY);
        colorModel.setExitedColor(ColorRS.SELECTED_ICON_BLUE);

        MaterialDesignIcon iconApplication = new MaterialDesignIcon();
        iconApplication.setSingleColor(ColorRS.WHITE);
        iconApplication.setColorIcon(ColorRS.RED);
        iconApplication.setIcon(MicrosoftSegoeIcon.FAVICON);
        iconApplication.setSize(18);

        MaterialDesignIcon iconPlaySolid = new MaterialDesignIcon();
        iconPlaySolid.setSingleColor(ColorRS.WHITE);
        iconPlaySolid.setIcon(MicrosoftSegoeIcon.PLAY);
        iconPlaySolid.setSize(18);

        MaterialDesignIcon iconMore = new MaterialDesignIcon();
        iconMore.setSingleColor(ColorRS.WHITE);
        iconMore.setIcon(MicrosoftSegoeIcon.ADD);
        iconMore.setSize(18);

        MaterialDesignIcon iconOpen = new MaterialDesignIcon();
        iconOpen.setSingleColor(ColorRS.WHITE);
        iconOpen.setIcon(MicrosoftSegoeIcon.OPEN_LOCAL);
        iconOpen.setSize(18);

        MaterialDesignIcon iconSave = new MaterialDesignIcon();
        iconSave.setSingleColor(ColorRS.WHITE);
        iconSave.setIcon(MicrosoftSegoeIcon.SAVE);
        iconSave.setSize(18);

        SquareButtonModel runModel = new SquareButtonModel();
        runModel.setIcon(iconPlaySolid);
        runModel.setIconColor(ColorRS.WHITE);
        runModel.setBackgroundColor(colorModel.getExitedColor());

        SquareButtonModel moreModel = new SquareButtonModel();
        moreModel.setIcon(iconMore);
        moreModel.setIconColor(ColorRS.WHITE);
        moreModel.setBackgroundColor(colorModel.getExitedColor());

        SquareButtonModel openModel = new SquareButtonModel();
        openModel.setIcon(iconOpen);
        openModel.setIconColor(ColorRS.WHITE);
        openModel.setBackgroundColor(colorModel.getExitedColor());

        SquareButtonModel saveModel = new SquareButtonModel();
        saveModel.setIcon(iconSave);
        saveModel.setIconColor(ColorRS.WHITE);
        saveModel.setBackgroundColor(colorModel.getExitedColor());

        lblNameFile = new JLabel();
        lblNameFile.setForeground(ColorRS.WHITE);
        lblNameFile.setText(" File");
        lblNameFile.setFont(FontRS.changeFont(FontRS.SEGOE_UI_REGULAR, Font.PLAIN, 18));
        lblNameFile.setIcon(iconApplication.toIcon());

        btnRun = new SquareButton();
        btnRun.setSquareButtonModel(runModel);
        btnRun.setEventColorModel(colorModel);
        btnRun.setButtonEvent(ButtonEvent.EXITED);

        btnNewFile = new SquareButton();
        btnNewFile.setSquareButtonModel(moreModel);
        btnNewFile.setEventColorModel(colorModel);
        btnNewFile.setButtonEvent(ButtonEvent.EXITED);

        btnOpenFile = new SquareButton();
        btnOpenFile.setSquareButtonModel(openModel);
        btnOpenFile.setEventColorModel(colorModel);
        btnOpenFile.setButtonEvent(ButtonEvent.EXITED);

        btnSave = new SquareButton();
        btnSave.setSquareButtonModel(saveModel);
        btnSave.setEventColorModel(colorModel);
        btnSave.setButtonEvent(ButtonEvent.EXITED);
    }

    @Override
    public void configureButtonsEvent()
    {
        btnRun.addActionListener(ae -> {
            state = 1;
            notifyEvent();
        });
        btnNewFile.addActionListener(ae -> {
            state = 2;
            notifyEvent();
            lblNameFile.setText(" File");
            widthLabel = 150;
            configureLayout();
        });
        btnOpenFile.addActionListener(ae -> {
            state = 3;
            notifyEvent();
            String fileName = fileManager.getFileName().toString();
            if (!fileName.isEmpty())
            {
                lblNameFile.setText(" " + fileName);
                widthLabel =  getTextWidth(
                        fileName,
                        FontRS.changeFont(FontRS.SEGOE_UI_REGULAR, Font.PLAIN, 18)) + 40;
                configureLayout();
            }
        });
        btnSave.addActionListener(ae -> {
            state = 4;
            notifyEvent();
            String fileName = fileManager.getFileName().toString();
            if (!fileName.isEmpty())
            {
                lblNameFile.setText(" " + fileName);
                widthLabel =  getTextWidth(fileName, FontRS.changeFont(FontRS.SEGOE_UI_REGULAR, Font.PLAIN, 18)) + 40;
                configureLayout();
            }
        });
    }

    @Override
    public void configureLayout() {
        int pref = 40;
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblNameFile, GroupLayout.PREFERRED_SIZE,
                                widthLabel, Short.MAX_VALUE)
                        .addComponent(btnRun, GroupLayout.PREFERRED_SIZE,
                                pref, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNewFile, GroupLayout.PREFERRED_SIZE,
                                pref, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnOpenFile, GroupLayout.PREFERRED_SIZE,
                                pref, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSave, GroupLayout.PREFERRED_SIZE,
                                pref, GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(lblNameFile, GroupLayout.PREFERRED_SIZE,
                        pref, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnRun, GroupLayout.DEFAULT_SIZE,
                        pref, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnNewFile, GroupLayout.DEFAULT_SIZE,
                        pref, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnOpenFile, GroupLayout.DEFAULT_SIZE,
                        pref, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnSave, GroupLayout.PREFERRED_SIZE,
                        pref, GroupLayout.PREFERRED_SIZE)
        );
    }

    @Override
    protected void paintComponent(@NotNull Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g.create();
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10);
        Utils.setRendering(g2d);
        g2d.setColor(getBackground());
        g2d.fill(roundedRectangle);
        g2d.dispose();
    }

    @Override
    public void addObserver(IEventObserver<Integer> iEventObserver)
    {
        observers.add(iEventObserver);
    }

    @Override
    public void notifyEvent()
    {
        observers.forEach(x -> x.update(state));
    }
}
