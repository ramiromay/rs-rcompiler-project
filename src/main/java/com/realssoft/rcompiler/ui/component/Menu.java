package com.realssoft.rcompiler.ui.component;

import com.realssoft.rcompiler.ui.values.ConfigureComponent;
import net.miginfocom.swing.MigLayout;
import javax.swing.JPanel;

public class Menu extends JPanel implements ConfigureComponent
{

    public Menu()
    {
        super();
        configureProperties();
        configureComponents();
    }

    @Override
    public void configureProperties()
    {
        ConfigureComponent.super.configureProperties();
        this.setOpaque(false);
    }

    @Override
    public void configureComponents()
    {
        ConfigureComponent.super.configureComponents();
        MigLayout layout = new MigLayout("wrap", "0[]0", "0[]0[]0");
        this.setLayout(layout);

        CompanyLogo companyLogo = new CompanyLogo();
        this.add(companyLogo, "w 100%, h 200!");

        MenuButtons menuButtons = new MenuButtons();
        this.add(menuButtons, "w 100%, h 200!");
    }

}
