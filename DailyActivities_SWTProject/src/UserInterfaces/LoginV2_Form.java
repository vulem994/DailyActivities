package UserInterfaces;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;

import DBContext.DailyActivityContext;
import DBContext.UserContext;
import Enumerations.EFormInitializeType;
import Models.DailyActivity;
import Models.User;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;

public class LoginV2_Form
{
	//Launcher
	public static void main(String[] args) 
	{
		try 
		{
			LoginV2_Form window = new LoginV2_Form();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Properties
	
	
	//FormProperties
	private Display display;
	private Shell shlLogin;
	
	private Text textBox_userName;
	private Text textBox_password;
	
	private Label label_userNameWarring;
	private Label label_passWarring;

	

	//Open function
	public void open() 
	{
		display = Display.getDefault();
		 shlLogin = new Shell();
		shlLogin.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		shlLogin.setSize(613, 422);
		shlLogin.setText("Login");
		
		Label label_dailyActivities_text = new Label(shlLogin, SWT.NONE);
		label_dailyActivities_text.setAlignment(SWT.CENTER);
		label_dailyActivities_text.setText("Daily activities");
		label_dailyActivities_text.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_dailyActivities_text.setFont(SWTResourceManager.getFont("Calibri", 20, SWT.BOLD | SWT.ITALIC));
		label_dailyActivities_text.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		label_dailyActivities_text.setBounds(117, 10, 348, 48);
		
		Label separator_upper = new Label(shlLogin, SWT.SEPARATOR | SWT.HORIZONTAL);
		separator_upper.setBounds(106, 232, 379, 2);
		
		Label label_username = new Label(shlLogin, SWT.NONE);
		label_username.setText("Username");
		label_username.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		label_username.setFont(SWTResourceManager.getFont("Calibri", 14, SWT.BOLD));
		label_username.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		label_username.setBounds(120, 106, 115, 43);
		
		Label label_password = new Label(shlLogin, SWT.NONE);
		label_password.setText("Password");
		label_password.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_password.setFont(SWTResourceManager.getFont("Calibri", 14, SWT.BOLD));
		label_password.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		label_password.setBounds(120, 168, 136, 29);
		
		textBox_userName = new Text(shlLogin, SWT.BORDER);
		textBox_userName.setBounds(264, 109, 201, 26);
		textBox_userName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				label_userNameWarring.setText("");
			}
		});
		
		textBox_password = new Text(shlLogin, SWT.BORDER | SWT.PASSWORD);
		textBox_password.setBounds(262, 171, 203, 26);
		textBox_password.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				label_passWarring.setText("");

			}
		});
		
		Button button_login = new Button(shlLogin, SWT.FLAT);
		button_login.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				Login();
			}
		});
		button_login.setText("Login");
		button_login.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND));
		button_login.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		button_login.setBounds(176, 261, 274, 38);
		
		Label separator_bottom = new Label(shlLogin, SWT.SEPARATOR | SWT.HORIZONTAL);
		separator_bottom.setBounds(106, 64, 379, 2);
		
		Link link_register = new Link(shlLogin, SWT.NONE);
		link_register.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {			
				link_register.setCursor(new Cursor(display, SWT.CURSOR_HAND));
			}
		});
		link_register.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
		link_register.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) 
			{
				RegisterNewUser();			
			}
		});
		link_register.setLinkForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		link_register.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		link_register.setBounds(508, 345, 77, 20);
		link_register.setText("Register");
		
		Label lblNewLabel = new Label(shlLogin, SWT.NONE);
		lblNewLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblNewLabel.setBounds(340, 345, 168, 20);
		lblNewLabel.setText("You dont have account? ");
		
		label_userNameWarring = new Label(shlLogin, SWT.NONE);
		label_userNameWarring.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		label_userNameWarring.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		label_userNameWarring.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		label_userNameWarring.setBounds(264, 141, 244, 20);
		
		label_passWarring = new Label(shlLogin, SWT.NONE);
		label_passWarring.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		label_passWarring.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		label_passWarring.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		label_passWarring.setBounds(264, 203, 244, 20);
		
		
		

		shlLogin.open();
		shlLogin.layout();
		while (!shlLogin.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	//Work with login & register functions
	private void Login()
	{
		String typedUserName =textBox_userName.getText();
		String typedPassword = textBox_password.getText();
		
		User tmpUser = UserContext.get(typedUserName);
		if(tmpUser != null)
		{
			
			if(typedPassword.equals(tmpUser.getPassword()))
			{
				OpenDashboard(tmpUser);
			}
			else
			{
				this.label_passWarring.setText("Invaild password");
			}
		}
		else
		{
			this.label_userNameWarring.setText("User does not exist");
		}
	}
	
	

	
	private Boolean RegisterNewUser()
	{
		User tmpUser = new User();
		AddEditUser_Dialog dialog = new AddEditUser_Dialog(shlLogin,
				Dialog.DIALOG_PERSISTSIZE);
		Object result = dialog.open(tmpUser, EFormInitializeType.New);
		if ((Boolean) result == true) 
		{
			Boolean resultDB = UserContext.add(tmpUser);
			if (resultDB) 
			{
				//return AddDailyActivity(tmpDA);
			}

		}
		return true;
	}
	
	
	//OpenDashboard function
	private void OpenDashboard(User inUser)
	{
		if(inUser != null)
		{
			Dashboard_Form dashboard = new Dashboard_Form(inUser);
			display.close();
			dashboard.open();
		}
		else
		{
			
		}
	}
}//[Class]
