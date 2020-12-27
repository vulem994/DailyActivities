package UserInterfaces;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;

import DBContext.UserContext;
import Enumerations.EFormInitializeType;
import Models.DailyActivity;
import Models.User;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class AddEditUser_Dialog extends Dialog {
	// Properties
	private User user;

	// Base propeties
	protected Object result;
	protected Shell shlRegister;

	// Form properties
	private Text text_userName;
	private Text text_fullName;
	private Text text_password;
	private Text text_repeatPassword;

	private Label label_passwordWarring;
	private Label label_fullNameWarring;
	private Label label_userWarring;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public AddEditUser_Dialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	// Open dialog functions
	public Object open(User inUser) // Without optional parameter (Initialize type)
	{
		return open(inUser, EFormInitializeType.New);
	}

	public Object open(User inUser, EFormInitializeType inFormInitializeType) {
		this.user = inUser;
		createContents();
		LoadObject(); // load objecct

		shlRegister.open();
		shlRegister.layout();
		Display display = getParent().getDisplay();
		getParent().setEnabled(false); // disable parent form
		while (!shlRegister.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlRegister = new Shell(getParent(), getStyle());
		shlRegister.setText("Register");
		shlRegister.setSize(450, 420);

		Label label = new Label(shlRegister, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(39, 66, 338, 9);

		Label label_title = new Label(shlRegister, SWT.NONE);
		label_title.setText("Register user");
		label_title.setFont(SWTResourceManager.getFont("Segoe UI", 19, SWT.NORMAL));
		label_title.setAlignment(SWT.CENTER);
		label_title.setBounds(39, 10, 338, 50);

		Label lblUsername = new Label(shlRegister, SWT.NONE);
		lblUsername.setText("Username:");
		lblUsername.setBounds(49, 81, 70, 20);

		Label lblPassword = new Label(shlRegister, SWT.NONE);
		lblPassword.setText("Password");
		lblPassword.setBounds(49, 192, 70, 20);

		Button btnCancel = new Button(shlRegister, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = false;
				getParent().setEnabled(true);
				shlRegister.dispose();
			}
		});
		btnCancel.setText("Cancel");
		btnCancel.setBounds(10, 319, 90, 30);

		Button button_ok = new Button(shlRegister, SWT.NONE);
		button_ok.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Boolean validityResult = CheckDataValidity();
				if (validityResult) {
					ApplyObject();
					result = true;
					getParent().setEnabled(true);
					shlRegister.dispose();
				}

			}
		});
		button_ok.setText("Register");
		button_ok.setBounds(337, 319, 90, 30);
		
				text_userName = new Text(shlRegister, SWT.BORDER);
				text_userName.setBounds(183, 81, 173, 26);
				text_userName.addModifyListener(new ModifyListener() {
					@Override
					public void modifyText(ModifyEvent e) {
						label_userWarring.setText("");
					}
				});

		text_fullName = new Text(shlRegister, SWT.BORDER);
		text_fullName.setBounds(183, 135, 173, 26);
		text_fullName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				label_fullNameWarring.setText("");
			}
		});

		text_password = new Text(shlRegister, SWT.BORDER | SWT.PASSWORD);
		text_password.setBounds(183, 189, 173, 26);
		text_password.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				label_passwordWarring.setText("");
			}
		});

		text_repeatPassword = new Text(shlRegister, SWT.BORDER | SWT.PASSWORD);
		text_repeatPassword.setBounds(183, 244, 173, 26);
		text_repeatPassword.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {

			}
		});

		Label lblRepe = new Label(shlRegister, SWT.NONE);
		lblRepe.setText("Repeat password");
		lblRepe.setBounds(49, 247, 128, 20);

		Label lblUsername_1 = new Label(shlRegister, SWT.NONE);
		lblUsername_1.setText("Full name:");
		lblUsername_1.setBounds(49, 135, 70, 20);

		label_userWarring = new Label(shlRegister, SWT.NONE);
		label_userWarring.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		label_userWarring.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		label_userWarring.setBounds(183, 113, 244, 22);

		label_fullNameWarring = new Label(shlRegister, SWT.NONE);
		label_fullNameWarring.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		label_fullNameWarring.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		label_fullNameWarring.setBounds(183, 167, 244, 22);

		label_passwordWarring = new Label(shlRegister, SWT.NONE);
		label_passwordWarring.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		label_passwordWarring.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		label_passwordWarring.setBounds(183, 221, 244, 22);

	}

	// work with data functions
	private void LoadObject() {
		if (user != null) {
			this.text_userName.setText(user.getUserName());
			this.text_password.setText(user.getPassword());
			// this.text_repeatPassword.setText(user.getPassword());
			this.text_userName.setText(user.getFullName());
		}
	}

	private void ApplyObject() {
		if (user != null) {
			user.setUserName(text_userName.getText());
			user.setPassword(text_password.getText());
			user.setFullName(text_userName.getText());
		}
	}

	private Boolean CheckDataValidity() {
		Boolean success = true;
		User tmpUser = UserContext.get(this.text_userName.getText());
		if(tmpUser != null){
			this.label_userWarring.setText("Username is alredy exist");
			success = false;
			
		}else if (this.text_userName.getText().isEmpty() || this.text_userName.getText().isBlank()) {
			this.label_userWarring.setText("Username is required.");
			success = false;
		} else if (this.text_userName.getText().length() < 4) {
			this.label_userWarring.setText("Username can not be shorter than 4 char.");
			success =  false;
		} 
		
		if (this.text_password.getText().isEmpty() || this.text_password.getText().isBlank()) {
			this.label_passwordWarring.setText("Enter password.");
			success = false;
		} else if (this.text_password.getText().length() < 4) {
			this.label_passwordWarring.setText("Password can not be shorter than 4 char.");
			success = false;
		} else if (!this.text_password.getText().equals(this.text_repeatPassword.getText())) {
			this.label_passwordWarring.setText("Passwords does not match");
			success = false;
		} 
		
		if (this.text_fullName.getText().isEmpty() || this.text_fullName.getText().isBlank()) {
			this.label_fullNameWarring.setText("Full name field is empty.");
			success = false;
		}

		return success;
	}

}
