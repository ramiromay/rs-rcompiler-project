package com.realssoft.rcompiler.ui.activity.home;

import com.realssoft.rcompiler.ui.activity.ActivityManager;
import com.realssoft.rcompiler.ui.component.CardView;
import com.realssoft.rcompiler.ui.component.NavBar;
import com.realssoft.rcompiler.ui.component.RoundButton;
import com.realssoft.rcompiler.ui.values.ButtonEvent;
import com.realssoft.rcompiler.ui.values.StringRS;
import com.realssoft.rcompiler.ui.values.model.EventColorModel;
import com.realssoft.rcompiler.ui.values.ColorRS;
import com.realssoft.rcompiler.ui.values.ConfigureComponent;
import com.realssoft.rcompiler.ui.values.FontRS;
import com.realssoft.rcompiler.ui.values.PathRS;
import com.realssoft.rcompiler.ui.values.model.CardViewModel;
import com.realssoft.rcompiler.ui.values.model.RoundButtonModel;
import com.realssoft.rcompiler.ui.values.model.ShadowModel;
import org.jetbrains.annotations.NotNull;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class HomeActivity extends JPanel implements ConfigureComponent
{

	private static final HomeActivity INSTANCE = new HomeActivity();
	private JLabel lblTitle;
	private JLabel lblSubtitle;
	private CardView cvRCompiler;
	private CardView cvTest;
	private RoundButton btnAlphabet;
	private RoundButton btnRules;
	private ActivityManager activityManager;

	private HomeActivity()
	{
		super();
		configureProperties();
		configureComponents();
		configureButtonsEvent();
		configureMouseEvents();
		configureLayout();
	}

	public static HomeActivity getInstance()
	{
		return INSTANCE;
	}

	private void changeCardViewStatus(@NotNull CardView cardView, ButtonEvent status)
	{
		cardView.setButtonEvent(status);
		cardView.repaint();
	}

	@Override
	public void configureProperties()
	{
		this.setBorder(new EmptyBorder(10, 48, 10, 48));
		this.setBackground(ColorRS.WHITE);
	}

	@Override
	public void configureComponents()
	{
		ConfigureComponent.super.configureComponents();
		ShadowModel cardViewShadowModel = new ShadowModel();
		cardViewShadowModel.setShadowSize(7);
		cardViewShadowModel.setShadowOpacity(0.3f);
		cardViewShadowModel.setShadowColor(ColorRS.BLACK);

		ShadowModel roundButtonShadowModel = new ShadowModel();
		roundButtonShadowModel.setShadowSize(7);
		roundButtonShadowModel.setShadowOpacity(0.2f);
		roundButtonShadowModel.setShadowColor(ColorRS.BLACK);

		CardViewModel btnRCompilerModel = new CardViewModel();
		btnRCompilerModel.setTitle(StringRS.RC_COMPILER);
		btnRCompilerModel.setDescription(StringRS.RCC_DESCRIPTION);
		btnRCompilerModel.setCornerRadius(25);
		btnRCompilerModel.setBackgroundImage(new ImageIcon(Objects.requireNonNull(CardView.class
				.getResource(PathRS.R_COMPILER_BACKGROUND_IMAGE))).getImage());

		CardViewModel cardViewModel = new CardViewModel();
		cardViewModel.setTitle(StringRS.RC_TEST);
		cardViewModel.setDescription(StringRS.RCT_DESCRIPTION);
		cardViewModel.setCornerRadius(25);
		cardViewModel.setBackgroundImage(new ImageIcon(Objects.requireNonNull(CardView.class
				.getResource(PathRS.TEST_BACKGROUND_IMAGE))).getImage());

		RoundButtonModel btnAlphabetModel = new RoundButtonModel();
		btnAlphabetModel.setIcon(new ImageIcon(Objects.requireNonNull(CardView.class
				.getResource(PathRS.SIGMA_ICON))));
		btnAlphabetModel.setText(StringRS.ALPHABETIC);
		btnAlphabetModel.setTextColor(ColorRS.BLACK_80);
		btnAlphabetModel.setBackgroundColor(ColorRS.WHITE);
		btnAlphabetModel.setCornerRadius(20);

		RoundButtonModel btnSyntaxModel = new RoundButtonModel();
		btnSyntaxModel.setIcon(new ImageIcon(Objects.requireNonNull(CardView.class
				.getResource(PathRS.RULES_ICON))));
		btnSyntaxModel.setText(StringRS.RULES);
		btnSyntaxModel.setTextColor(ColorRS.BLACK_80);
		btnSyntaxModel.setBackgroundColor(ColorRS.WHITE);
		btnSyntaxModel.setCornerRadius(20);

		EventColorModel eventColorModel = new EventColorModel();
		eventColorModel.setEnteredColor(ColorRS.WHITE);
		eventColorModel.setExitedColor(ColorRS.WHITE);

		lblTitle = new JLabel();
		lblTitle.setText(StringRS.WELCOME);
		lblTitle.setFont(FontRS.changeFont(FontRS.SEGOE_UI_BOLD, Font.PLAIN, 40));

		cvRCompiler = new CardView(btnRCompilerModel, roundButtonShadowModel);
		cvRCompiler.setButtonEvent(ButtonEvent.EXITED);

		cvTest = new CardView(cardViewModel, roundButtonShadowModel);
		cvTest.setButtonEvent(ButtonEvent.EXITED);

		lblSubtitle = new JLabel();
		lblSubtitle.setText(StringRS.RS);
		lblSubtitle.setFont(FontRS.changeFont(FontRS.SEGOE_UI_BOLD, Font.PLAIN, 24));

		btnAlphabet = new RoundButton();
		btnAlphabet.setShadowModel(roundButtonShadowModel);
		btnAlphabet.setRoundButtonModel(btnAlphabetModel);
		btnAlphabet.setEventColorModel(eventColorModel);
		btnAlphabet.setFont(FontRS.changeFont(FontRS.SEGOE_UI_REGULAR, Font.PLAIN, 20));
		btnAlphabet.setButtonEvent(ButtonEvent.EXITED);

		btnRules = new RoundButton();
		btnRules.setShadowModel(roundButtonShadowModel);
		btnRules.setRoundButtonModel(btnSyntaxModel);
		btnRules.setEventColorModel(eventColorModel);
		btnRules.setFont(FontRS.changeFont(FontRS.SEGOE_UI_REGULAR, Font.PLAIN, 20));
		btnRules.setButtonEvent(ButtonEvent.EXITED);


	}

	@Override
	public void configureButtonsEvent()
	{
		activityManager = ActivityManager.getInstance();
		btnAlphabet.addActionListener(e ->
		{
			NavBar.getInstance().navBarElements(true, false);
			btnAlphabet.getMouseListeners()[1].mouseExited(null);
			activityManager.showActivity(AlphabeticActivity.getInstance());

		});

		btnRules.addActionListener(ae ->
		{
			NavBar.getInstance().navBarElements(true, false);
			btnRules.getMouseListeners()[1].mouseExited(null);
			activityManager.showActivity(RuleActivity.getInstance());
		});
	}

	@Override
	public void configureMouseEvents()
	{
		ConfigureComponent.super.configureMouseEvents();
		cvRCompiler.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				NavBar.getInstance().navBarElements(true, true);
				cvRCompiler.getMouseListeners()[0].mouseExited(null);
				activityManager.showActivity(CompilerActivity.getInstance());
			}
			@Override
			public void mouseEntered(MouseEvent e)
			{
				changeCardViewStatus(cvRCompiler, ButtonEvent.ENTERED);
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				changeCardViewStatus(cvRCompiler, ButtonEvent.EXITED);
			}
		});

		cvTest.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				System.out.println("Se dio click");
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				changeCardViewStatus(cvTest, ButtonEvent.ENTERED);
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				changeCardViewStatus(cvTest, ButtonEvent.EXITED);
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
				.addComponent(lblTitle,  GroupLayout.DEFAULT_SIZE,
						500, Short.MAX_VALUE)
				.addGroup(layout.createSequentialGroup()
						.addComponent(cvRCompiler, GroupLayout.PREFERRED_SIZE,
								462, GroupLayout.PREFERRED_SIZE)
						.addGap(10,10, GroupLayout.PREFERRED_SIZE)
						.addComponent(cvTest, GroupLayout.PREFERRED_SIZE,
								462, GroupLayout.PREFERRED_SIZE)
				)
				.addComponent(lblSubtitle, GroupLayout.DEFAULT_SIZE,
						500, Short.MAX_VALUE)
				.addGroup(layout.createSequentialGroup()
						.addComponent(btnAlphabet, GroupLayout.PREFERRED_SIZE,
								462, GroupLayout.PREFERRED_SIZE)
						.addGap(10,10, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnRules, GroupLayout.PREFERRED_SIZE,
								462, GroupLayout.PREFERRED_SIZE)
				)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGap(20,20, GroupLayout.PREFERRED_SIZE)
				.addComponent(lblTitle,  GroupLayout.DEFAULT_SIZE,
						70, GroupLayout.PREFERRED_SIZE)
				.addGap(40,40, GroupLayout.PREFERRED_SIZE)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(cvRCompiler, GroupLayout.DEFAULT_SIZE,
								270, GroupLayout.PREFERRED_SIZE)
						.addComponent(cvTest, GroupLayout.DEFAULT_SIZE,
								270, GroupLayout.PREFERRED_SIZE)
				)
				.addGap(30,30, GroupLayout.PREFERRED_SIZE)
				.addComponent(lblSubtitle, GroupLayout.DEFAULT_SIZE,
						70, GroupLayout.PREFERRED_SIZE)
				.addGap(5,5, GroupLayout.PREFERRED_SIZE)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(btnAlphabet, GroupLayout.DEFAULT_SIZE,
								90, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnRules, GroupLayout.DEFAULT_SIZE,
								90, GroupLayout.PREFERRED_SIZE)
				)
		);
	}

}
