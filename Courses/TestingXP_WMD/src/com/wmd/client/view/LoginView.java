package com.wmd.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.wmd.client.ApplicationWrapper;
import com.wmd.client.msg.Role;
import com.wmd.client.msg.UserMsg;
import com.wmd.client.service.UpdatePasswordService;
import com.wmd.client.service.UpdatePasswordServiceAsync;
import com.wmd.client.service.UserSignInService;
import com.wmd.client.service.UserSignInServiceAsync;
import com.wmd.client.view.instructor.InstructorDashboard;
import com.wmd.client.view.student.StudentDashboard;

/**
 * Login view. This is what the user sees when they login to the system. Should
 * have user name and password fields, as well as a submit button. Add some
 * visuals, so it doesn't look completely bland and boring.
 * 
 * @author Kevin Rexroth, Stephen Jurnack, and Phil Diffenderfer
 */
public class LoginView extends View
{
	/**
	 * Create the service used to log the user in.
	 */
	private static final UserSignInServiceAsync userSignInService = GWT
			.create(UserSignInService.class);

	// Declare the fields for the login screen
	private AbsolutePanel imageFields;
	private AbsolutePanel usernameFields;
	private AbsolutePanel passwordFields;
	private AbsolutePanel loginFields;
	private Label usernameLabel;
	private TextBox usernameBox;
	private Label passwordLabel;
	private PasswordTextBox passwordBox;
	private Label invalidLoginField;
	private Button loginButton;

	/**
	 * LoginView constructor.
	 * 
	 * Create the actual login screen.
	 */
	public LoginView()
	{
		// Load the layout
		loadLayout();

		// Create the fields to hold the labels and textboxes
		// for the login screen
		this.imageFields = new AbsolutePanel();
		this.imageFields.setStyleName("image-fields");

		this.usernameFields = new AbsolutePanel();
		this.usernameFields.setStyleName("username-fields");

		this.passwordFields = new AbsolutePanel();
		this.passwordFields.setStyleName("password-fields");

		this.loginFields = new AbsolutePanel();
		this.loginFields.setStyleName("login-fields");

		// Create the labels and textboxes for the login screen
		Image banner = new Image("Resources/Images/MathRocketBanner.png");

		this.invalidLoginField = new Label(
				"Invalid Username/Password Combination!");
		this.invalidLoginField.setStyleName("invalid-login");
		this.invalidLoginField.setVisible(false);

		this.usernameLabel = new Label("USERNAME:");
		this.usernameLabel.setStyleName("username-label");

		this.usernameBox = new TextBox();
		this.usernameBox.setSize("225px", "30px");
		this.usernameBox.setStyleName("username-box");

		this.passwordLabel = new Label("PASSWORD:");
		this.passwordLabel.setStyleName("password-label");

		this.passwordBox = new PasswordTextBox();
		this.passwordBox.setSize("225px", "30px");
		this.passwordBox.setStyleName("password-box");

		// If the user presses 'Enter' after typing in their password
		// Sign them in
		this.passwordBox.addKeyPressHandler(new KeyPressHandler()
		{
			public void onKeyPress(KeyPressEvent event)
			{
				if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER)
				{
					triggerLogin();
				}
			}
		});

		this.loginButton = new Button("LOGIN");
		this.loginButton.setStyleName("button-login");
		this.loginButton.setSize("90px", "50px");

		// Make the login button sign in the user
		this.loginButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				triggerLogin();
			}
		});

		// Add the fields to the login screen
		this.imageFields.add(banner);
		this.usernameFields.add(this.invalidLoginField);
		this.usernameFields.add(this.usernameLabel);
		this.usernameFields.add(this.usernameBox);
		this.passwordFields.add(this.passwordLabel);
		this.passwordFields.add(this.passwordBox);
		this.loginFields.add(this.loginButton);

		super.getLayout().setWidget(0, 0, this.imageFields);
		super.getLayout().setWidget(1, 0, this.usernameFields);
		super.getLayout().setWidget(2, 0, this.passwordFields);
		super.getLayout().setWidget(3, 0, this.loginFields);

		super.getLayout().setStyleName("loginPage");

		// Give focus to the username field
		// Needs a timer, because Firefox doesn't
		// support the setFocus() method sometimes
		Timer t = new Timer()
		{
			@Override
			public void run()
			{
				LoginView.this.usernameBox.setFocus(true);
			}
		};
		t.schedule(1);
	}

	/**
	 * Submit the username and password values
	 */
	private void triggerLogin()
	{
		// Get the user's information
		String username = this.usernameBox.getText();
		String password = this.passwordBox.getText();

		// Clear the login fields
		this.usernameBox.setText("");
		this.passwordBox.setText("");

		// Submit values for login
		login(username, password);
	}

	/**
	 * Submit the user information to the database
	 * 
	 * @param username
	 *            - The username that is being used to login
	 * @param password
	 *            - The password that is being used to login
	 */
	private void login(final String username, final String password)
	{
		// Send the information to the database
		userSignInService.signInUser(username, password,
				new AsyncCallback<UserMsg>()
				{
					// If the login is successful,
					// set the view, based on their role
					public void onSuccess(UserMsg result)
					{
						signin(result);
					}

					// If the login fails,
					// let the user know
					public void onFailure(Throwable caught)
					{
						Window.alert(caught.getMessage());
					}
				});

	}

	/**
	 * Change the view, based on what role the user has
	 * 
	 * @param user
	 *            - The user that is logging in
	 */
	private void signin(UserMsg user)
	{
		// Let the user know if they entered
		// an invalid username/password and
		// somehow got to this point
		if (user == null)
		{
			this.invalidLoginField.setVisible(true);
			return;
		}

		if ((user.getRole() == Role.Instructor) && (user.getPassword() == null || user.getPassword().length() == 0))
		{
			promptNewPassword(user);
		}

		// Get the user's role
		// and set the view to their respective dashboard
		Widget dashboard = null;
		switch (user.getRole())
		{
		case Student:
			dashboard = new StudentDashboard(user);
			break;
		case Instructor:
			dashboard = new InstructorDashboard(user);
			break;
		case Admin:
			dashboard = new InstructorDashboard(user);
			break;
		}

		// Set the view
		RootPanel.get("application_html_wrapper").clear();
		ApplicationWrapper.getInstance().setDashboard(dashboard);
		ApplicationWrapper.getInstance().clearWidget();
	}

	/**
	 * Prompts the user for a new password and a re-entry of that password for
	 * confirmation.
	 * 
	 * @param user - The user whos password is being changed.
	 */
	private void promptNewPassword(final UserMsg user)
	{
		final DialogBox passwordPrompt = new DialogBox();

		VerticalPanel passwordPanel = new VerticalPanel();

		final PasswordTextBox password = new PasswordTextBox();
		final PasswordTextBox passwordReentry = new PasswordTextBox();

		Button submitNewPassword = new Button();
		submitNewPassword.setText("Set New Password.");
		submitNewPassword.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				String newPassword = password.getText();
				String resubmitPassword = passwordReentry.getText();

				if (newPassword.equals(resubmitPassword))
				{					
					final UpdatePasswordServiceAsync updatePasswordService = GWT.create(UpdatePasswordService.class);
					
					updatePasswordService.updateUserPassword(newPassword, user.getUserId(), new AsyncCallback<Boolean>()
					{
						@Override
						public void onFailure(Throwable caught)
						{
							Window.alert("Error submitting your new password!");
						}

						@Override
						public void onSuccess(Boolean result)
						{
							//Do nothing						
						}
					});

										
					password.setText("");
					passwordReentry.setText("");
					passwordPrompt.hide();
				}
				else
				{
					Window.alert("Passwords don't match. Try again");
					
					password.setText("");
					passwordReentry.setText("");
				}
			}
		});

		passwordPanel.add(password);
		passwordPanel.add(passwordReentry);
		passwordPanel.add(submitNewPassword);
		passwordPrompt.add(passwordPanel);
		passwordPrompt.setGlassEnabled(true);
		passwordPrompt.center();
	}

	/**
	 * Loads the layout of the LoginView.
	 */
	public void loadLayout()
	{
		// Set the preferences for the login screen layout
		super.getLayout().setWidth("100%");
		super.getLayout().setHeight("100%");
		super.getLayout().setCellPadding(0);
		super.getLayout().setCellSpacing(0);
	}
}