package UserInterfaces;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import Enumerations.EFormInitializeType;
import Models.DailyActivity;
import Shared.HelperModel;

import org.eclipse.swt.widgets.Label;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;

import com.ibm.icu.util.Calendar;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class AddEditDailyActivity_Dialog extends Dialog {
	// Properties
	private DailyActivity dailyActivity;

	// Base properties
	protected Object result;
	protected Shell shlDailyActivity;

	// Form properties
	private Label label_title;
	private Text textBox_title;
	private Text textBox_textDesc;
	private Label lblNewLabel;
	private Label lblTextDescription;
	private Button btnCancel;
	private Button button_ok;
	private DateTime dateTime_date;
	
	

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public AddEditDailyActivity_Dialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	// Open dialog function
	public Object open(DailyActivity inDailyActivity) // Without optional parameter (Initialize type)
	{
		return open(inDailyActivity, EFormInitializeType.New);
	}

	public Object open(DailyActivity inDailyActivity, EFormInitializeType inDialogInitializeType) {
		createContents();

		this.dailyActivity = inDailyActivity;
		LoadObject();
		this.UpdateUIByInitializeType(inDialogInitializeType);

		shlDailyActivity.open();
		shlDailyActivity.layout();
		Display display = getParent().getDisplay();
		this.getParent().setEnabled(false);
		while (!shlDailyActivity.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	// Create contents
	private void createContents() {
		shlDailyActivity = new Shell(getParent(), getStyle());
		shlDailyActivity.setSize(443, 394);
		shlDailyActivity.setText("Daily activity");
		shlDailyActivity.setLayout(null);

		Label label = new Label(shlDailyActivity, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(39, 66, 338, 9);

		label_title = new Label(shlDailyActivity, SWT.NONE);
		label_title.setAlignment(SWT.CENTER);
		label_title.setFont(SWTResourceManager.getFont("Segoe UI", 19, SWT.NORMAL));
		label_title.setBounds(39, 10, 338, 50);
		label_title.setText("Daily activity");

		textBox_title = new Text(shlDailyActivity, SWT.BORDER);
		textBox_title.setBounds(154, 81, 202, 26);

		textBox_textDesc = new Text(shlDailyActivity, SWT.BORDER | SWT.WRAP);
		textBox_textDesc.setBounds(154, 129, 202, 102);

		lblNewLabel = new Label(shlDailyActivity, SWT.NONE);
		lblNewLabel.setBounds(49, 81, 70, 20);
		lblNewLabel.setText("Title");

		lblTextDescription = new Label(shlDailyActivity, SWT.NONE);
		lblTextDescription.setText("Text ");
		lblTextDescription.setBounds(49, 132, 70, 20);

		btnCancel = new Button(shlDailyActivity, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = false;
				getParent().setEnabled(true);
				shlDailyActivity.dispose();
			}
		});
		btnCancel.setBounds(10, 319, 90, 30);
		btnCancel.setText("Cancel");

		button_ok = new Button(shlDailyActivity, SWT.NONE);
		button_ok.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ApplyObject();
				result = true;
				getParent().setEnabled(true);
				shlDailyActivity.dispose();
			}
		});
		button_ok.setBounds(337, 319, 90, 30);
		button_ok.setText("Ok");

		dateTime_date = new DateTime(shlDailyActivity, SWT.BORDER);
		dateTime_date.setBounds(154, 247, 110, 28);

		Label lblDate = new Label(shlDailyActivity, SWT.NONE);
		lblDate.setText("Date");
		lblDate.setBounds(39, 247, 70, 20);

	}

	// Get Set functions
	public DailyActivity getDailyActivity() {
		return dailyActivity;
	}

	// work with UI functions
	private void UpdateUIByInitializeType(EFormInitializeType inDialogInitializeType) {
		if (inDialogInitializeType == EFormInitializeType.New) {
			label_title.setText("New daily activity");
			button_ok.setText("Create new");
		} else if (inDialogInitializeType == EFormInitializeType.Edit) {
			label_title.setText("Edit daily activity");
			button_ok.setText("Save");
		} else {
			label_title.setText("Daily activity");
			button_ok.setText("Ok");
		}
	}

	// work with data functions
	private void LoadObject() {
		if (dailyActivity != null) {
			this.textBox_title.setText(dailyActivity.getTitle());
			this.textBox_textDesc.setText(dailyActivity.getText());
			//setDateStringToDateTimeWidget(dailyActivity.getDate());
			HelperModel.setDateStringToDateTimeWidget(this.dateTime_date, dailyActivity.getDate());
		}
	}

	private void ApplyObject() {
		if (dailyActivity != null) {
			dailyActivity.setTitle(textBox_title.getText());
			dailyActivity.setText(textBox_textDesc.getText());
			//dailyActivity.setDate(getDateStringFromDateTimeWidget());
			dailyActivity.setDate(HelperModel.getDateStringFromDateTimeWidget(this.dateTime_date));
		}
	}

}// [Class]
