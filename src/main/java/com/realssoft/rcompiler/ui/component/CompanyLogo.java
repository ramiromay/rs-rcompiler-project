package com.realssoft.rcompiler.ui.component;

import com.realssoft.rcompiler.ui.values.ColorRS;
import com.realssoft.rcompiler.ui.values.ConfigureComponent;
import com.realssoft.rcompiler.ui.values.FontRS;
import com.realssoft.rcompiler.ui.values.PathRS;
import com.realssoft.rcompiler.ui.values.StringRS;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.util.Objects;

public class CompanyLogo extends JPanel implements ConfigureComponent
{

    private CircleImageView logo;
    private JLabel nameCompany;

    public CompanyLogo()
    {
        super();
        configureProperties();
        configureComponents();
        configureLayout();
    }

    @Override
    public void configureProperties()
    {
        ConfigureComponent.super.configureProperties();
        this.setOpaque(false);
        this.setBorder(new EmptyBorder(0,0,0,0));
    }

    @Override
    public void configureComponents()
    {
        ConfigureComponent.super.configureComponents();
        logo = new CircleImageView();
        logo.setIcon(new ImageIcon(Objects.requireNonNull(
                CompanyLogo.class.getResource(PathRS.COMPANY_LOGO_IMAGE))));

        nameCompany = new JLabel();
        nameCompany.setFont(FontRS.changeFont(FontRS.SEGOE_UI_BOLD, Font.PLAIN, 24));
        nameCompany.setText(StringRS.NAME_COMPANY);
        nameCompany.setForeground(ColorRS.GRAY);
    }

    @Override
    public void configureLayout()
    {
        ConfigureComponent.super.configureLayout();
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(logo, GroupLayout.Alignment.TRAILING)
                                .addComponent(nameCompany))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(logo, GroupLayout.DEFAULT_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                .addComponent(nameCompany, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))

        );
    }

}
