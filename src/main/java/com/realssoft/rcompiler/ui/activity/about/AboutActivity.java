package com.realssoft.rcompiler.ui.activity.about;

import com.realssoft.rcompiler.ui.values.ColorRS;
import com.realssoft.rcompiler.ui.values.FontRS;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;

public class AboutActivity extends JPanel
{

	private static final AboutActivity ABOUT_ACTIVITY = new AboutActivity();
	private JLabel label = new JLabel("About");
	private AboutActivity()
	{
		label.setFont(FontRS.changeFont(FontRS.SEGOE_UI_BOLD, Font.PLAIN, 40));
		this.setBackground(ColorRS.WHITE);
		this.add(label);
	}

	public static AboutActivity getInstance()
	{
		return ABOUT_ACTIVITY;
	}

}