package UserInterfaces;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.wb.swt.SWTResourceManager;

import com.mysql.jdbc.StatementImpl;

import DBContext.DailyActivityContext;
import Enumerations.EFormInitializeType;
import Models.DailyActivity;
import Models.User;
import Shared.HelperModel;

import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionListener;
import java.util.function.Consumer;

public class Dashboard_Form {
	// This form launcher
	public static void main(String[] args) {
		try {
			Dashboard_Form window = new Dashboard_Form(new User(1608940314351L, "Vuksa", "123"));
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Properties
	private User currentlyLoggedUser;
	private ArrayList<DailyActivity> dailyActivities = new ArrayList<DailyActivity>();
	private DailyActivity selectedDailyActivity;

	private String searchFilter = "";
	private Boolean filterByDate = false;
	private String dateFilter;

	// Form props
	private Display display;
	private Shell shlDashboard;

	private Text textBox_search;
	private Label label_dailyActivities_text;
	private Label label_UserNameTitle;

	private Table table_dailityActivities; // table
	private TableColumn tableColumn_title;
	private TableColumn tblclmnText;
	private TableColumn tableColumn_date;

	private Button button_ClearFilter; // buttons
	private Button button_edit;
	private Button button_remove;
	private Button button_addDailyActivity;
	private Label label_searchWarring;	
	private Button checkBox_filterDate;
	private DateTime dateTime_filterDate;

	// Constructor
	public Dashboard_Form(User inUser) {
		this.currentlyLoggedUser = inUser;
	}

	// Open function
	public void open() {
		display = Display.getDefault();
		shlDashboard = new Shell();
		shlDashboard.setBackground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		shlDashboard.setSize(1200, 800);
		shlDashboard.setText("Dashboard");
		shlDashboard.setLayout(null);

		table_dailityActivities = new Table(shlDashboard, SWT.BORDER | SWT.FULL_SELECTION);
		table_dailityActivities.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		table_dailityActivities.setHeaderBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		table_dailityActivities.setBounds(460, 157, 712, 586);
		table_dailityActivities.setHeaderVisible(true);
		table_dailityActivities.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				TableItem selection = table_dailityActivities.getSelection()[0];
				if (selection != null && selection.getData() != null && selection.getData() instanceof DailyActivity) {
					DailyActivity tmpDA = (DailyActivity) selection.getData();
					if (tmpDA != null) {
						// lblNewLabel.setText(tmpDA.getTitle());
						// selectedDailyActivity = tmpDA;
						setSelectedDailyActivity(tmpDA);
					}
				}
			}
		});

		tableColumn_title = new TableColumn(table_dailityActivities, SWT.CENTER);
		tableColumn_title.setResizable(false);
		tableColumn_title.setWidth(187);
		tableColumn_title.setText("Title");

		tblclmnText = new TableColumn(table_dailityActivities, SWT.CENTER);
		tblclmnText.setWidth(343);
		tblclmnText.setText("Text");

		tableColumn_date = new TableColumn(table_dailityActivities, SWT.CENTER);
		tableColumn_date.setWidth(171);
		tableColumn_date.setText("Date");

		label_UserNameTitle = new Label(shlDashboard, SWT.NONE);
		label_UserNameTitle.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_UserNameTitle.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		label_UserNameTitle.setFont(SWTResourceManager.getFont("Calibri", 20, SWT.BOLD));
		label_UserNameTitle.setBounds(32, 10, 348, 48);

		button_ClearFilter = new Button(shlDashboard, SWT.NONE); // BUTTON TEST
		button_ClearFilter.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// lblNewLabel.setText(text.getText());
				// CallMockup();
				// Dialog_Test testDialog = new Dialog_Test(shlDashboard,0);
				// Object result = testDialog.open();
				// Integer I = 0;
				setSearchFilter("");
				textBox_search.setText(searchFilter);
				RefreshTable();
			}
		});
		button_ClearFilter.setBounds(780, 119, 81, 30);
		button_ClearFilter.setText("Clear filter");
		button_ClearFilter.setVisible(false);

		textBox_search = new Text(shlDashboard, SWT.BORDER);
		textBox_search.setBounds(527, 121, 247, 30);
		textBox_search.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				setSearchFilter(textBox_search.getText());
				RefreshTable();
			}
		});

		button_addDailyActivity = new Button(shlDashboard, SWT.NONE); // BUTTON ADD*****
		button_addDailyActivity.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				CreateNewAndAddDailyActivity();
			}
		});
		button_addDailyActivity.setBounds(32, 157, 290, 30);
		button_addDailyActivity.setText("Add");

		button_edit = new Button(shlDashboard, SWT.NONE); // BUTTON EDIT*****
		button_edit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (selectedDailyActivity != null) {
					EditDailyActivity(selectedDailyActivity);
				}
			}
		});
		button_edit.setText("Edit");
		button_edit.setBounds(32, 208, 290, 30);

		button_remove = new Button(shlDashboard, SWT.NONE); // BUTTON REMOVE******
		button_remove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (selectedDailyActivity != null) {
					RemoveDailyActivity(selectedDailyActivity);
				}
			}
		});
		button_remove.setText("Remove");
		button_remove.setBounds(32, 260, 290, 30);

		Button button_logout = new Button(shlDashboard, SWT.NONE);
		button_logout.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				LogOut();
			}
		});
		button_logout.setBounds(191, 713, 131, 30);
		button_logout.setText("Logout");

		Button btnClose = new Button(shlDashboard, SWT.NONE);
		btnClose.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				display.close();
				display.dispose();
			}
		});
		btnClose.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		btnClose.setText("Close");
		btnClose.setBounds(32, 713, 131, 30);

		label_dailyActivities_text = new Label(shlDashboard, SWT.NONE);
		label_dailyActivities_text.setText("Daily activities");
		label_dailyActivities_text.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_dailyActivities_text.setFont(SWTResourceManager.getFont("Calibri", 20, SWT.BOLD | SWT.ITALIC));
		label_dailyActivities_text.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		label_dailyActivities_text.setBounds(454, 10, 348, 48);

		Label lblNewLabel_1 = new Label(shlDashboard, SWT.NONE);
		lblNewLabel_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblNewLabel_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblNewLabel_1.setBounds(460, 123, 70, 20);
		lblNewLabel_1.setText("Search:");

		label_searchWarring = new Label(shlDashboard, SWT.WRAP);
		label_searchWarring.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_searchWarring.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		label_searchWarring.setBounds(527, 75, 326, 40);
		
		checkBox_filterDate = new Button(shlDashboard, SWT.CHECK | SWT.RIGHT);
		checkBox_filterDate.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		checkBox_filterDate.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		checkBox_filterDate.setAlignment(SWT.CENTER);
		checkBox_filterDate.setBounds(892, 123, 143, 20);
		checkBox_filterDate.setText("Select by date");
		checkBox_filterDate.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		checkBox_filterDate.addSelectionListener(new SelectionAdapter()
		{
		    @Override
		    public void widgetSelected(SelectionEvent e)
		    {
		        if (checkBox_filterDate.getSelection())
		        {
		        	setDateFilterEnabled(true);
		        }		          
		        else
		        {
		        	setDateFilterEnabled(false);
		        }
		            
		    }
		});
		
		dateTime_filterDate = new DateTime(shlDashboard, SWT.BORDER);
		dateTime_filterDate.setForeground(SWTResourceManager.getColor(SWT.COLOR_LINK_FOREGROUND));
		dateTime_filterDate.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		dateTime_filterDate.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		dateTime_filterDate.setBounds(1041, 121, 131, 30);
		dateTime_filterDate.addSelectionListener (new SelectionAdapter () {
		        public void widgetSelected (SelectionEvent e) 
		        {
		        	setDateFilter(HelperModel.getDateStringFromDateTimeWidget(dateTime_filterDate));
		        }
		    });

	

		// On open functions-------------------------------
		UpdateUserInterface();
		LoadResourcesForLoggedInUser();

		shlDashboard.open();
		shlDashboard.layout();
		while (!shlDashboard.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	// On load functions ---
	private void UpdateUserInterface() {
		if (currentlyLoggedUser != null && currentlyLoggedUser.getUserName() != null && label_UserNameTitle != null) {
			label_UserNameTitle.setText(currentlyLoggedUser.getFullName());
		}
		this.button_edit.setEnabled(false);
		this.button_remove.setEnabled(false);
		this.dateTime_filterDate.setVisible(filterByDate);
		this.searchFilter = "";
		this.dateFilter = HelperModel.getDateStringFromDateTimeWidget(dateTime_filterDate);

	}

	private void LoadResourcesForLoggedInUser() {
		if (currentlyLoggedUser != null) {
			dailyActivities = DailyActivityContext.getForUser(currentlyLoggedUser.getId());
			//dailyActivities = DailyActivityContext.getAll(); //test
			RefreshTable();
		}
	}

	// Work with Table functions---
	private void RefreshTable() {
		if (table_dailityActivities != null) {
			table_dailityActivities.removeAll();

			if (dailyActivities != null && dailyActivities.size() > 0) {
				dailyActivities.forEach((dA) -> AddRowToTable(dA));
			}
		}
	}

	private void AddRowToTable(DailyActivity inDailyActivity) {
		AddRowToTable(inDailyActivity, true);
	}

	private void AddRowToTable(DailyActivity inDailyActivity, Boolean isFiltered) 
	{
		if (table_dailityActivities != null && inDailyActivity != null) 
		{
			//filter by date
			if(this.filterByDate && !inDailyActivity.getDate().equals(this.dateFilter))
			{
				return;
			}
			//filter by search
			if (isFiltered && searchFilter != null && searchFilter.length() >= 3 && !searchFilter.isBlank()
					&& !searchFilter.isEmpty()) {
				if (!inDailyActivity.getTitle().toLowerCase().contains(searchFilter.toLowerCase())
						&& !inDailyActivity.getText().toLowerCase().contains(searchFilter.toLowerCase())) {
					return;
				}
			}
			
		
			TableItem tmpTableItem = new TableItem(table_dailityActivities, SWT.LEFT);
			String[] tmpTableItemTextsArray = new String[] { inDailyActivity.getTitle(), inDailyActivity.getText(),
					inDailyActivity.getDate() };
			tmpTableItem.setText(tmpTableItemTextsArray);
			tmpTableItem.setData(inDailyActivity);
		}
	}

	// Work with daily activities functions---
	private Boolean CreateNewAndAddDailyActivity() {
		if (currentlyLoggedUser != null) {
			DailyActivity tmpDA = new DailyActivity(currentlyLoggedUser.getId());
			AddEditDailyActivity_Dialog dialog = new AddEditDailyActivity_Dialog(shlDashboard,
					Dialog.DIALOG_PERSISTSIZE);
			Object result = dialog.open(tmpDA, EFormInitializeType.New);
			if ((Boolean) result == true) {
				Boolean resultDB = DailyActivityContext.add(tmpDA);
				if (resultDB) {
					return AddDailyActivity(tmpDA);
				}

			}
		}
		return false;
	}

	private Boolean AddDailyActivity(DailyActivity inDailyActivity) {
		if (!dailyActivities.contains(inDailyActivity)) {
			dailyActivities.add(inDailyActivity);
			this.RefreshTable();
			return true;
		}
		return false;
	}

	private Boolean RemoveDailyActivity(DailyActivity inDailyActivity) {
		Boolean resultDB = DailyActivityContext.delete(inDailyActivity.getId());
		if (resultDB) {
			if (dailyActivities.contains(inDailyActivity)) {

				dailyActivities.remove(inDailyActivity);

				this.RefreshTable();
				this.setSelectedDailyActivity(null);
			}
			return true;
		}

		return false;
	}

	private Boolean EditDailyActivity(DailyActivity inDailyActivity) {
		AddEditDailyActivity_Dialog dialog = new AddEditDailyActivity_Dialog(shlDashboard, 0);
		Object result = dialog.open(inDailyActivity, EFormInitializeType.Edit);
		if ((Boolean) result == true) {
			Boolean resultDB = DailyActivityContext.update(inDailyActivity);
			this.RefreshTable();
		}
		return false;
	}

	// GET & SET functions---
	public void setSelectedDailyActivity(DailyActivity inDailyActivity) {
		if (selectedDailyActivity != inDailyActivity) {
			selectedDailyActivity = inDailyActivity;
			if (selectedDailyActivity != null) {
				this.button_edit.setEnabled(true);
				this.button_remove.setEnabled(true);
			} else {
				this.button_edit.setEnabled(false);
				this.button_remove.setEnabled(false);
			}
		}
	}

	private void setSearchFilter(String inText) {
		// TODO eventualno dodati preskakanje praznih char-ova
		searchFilter = inText;
		if (searchFilter.isEmpty()) {
			button_ClearFilter.setVisible(false);
		} else {
			button_ClearFilter.setVisible(true);
		}

		if (searchFilter.length() < 3 && !searchFilter.isBlank() && !searchFilter.isEmpty()) {
			label_searchWarring.setText("Minimum number of characters required for filtering is 3.");
		} else {
			label_searchWarring.setText("");
		}
	}
	
	public Boolean getDateFilterEnabled()
	{
		return this.filterByDate;
	}
	public void setDateFilterEnabled(Boolean isEnabled)
	{
		this.filterByDate = isEnabled;
		dateTime_filterDate.setVisible(filterByDate);
		RefreshTable();
	}
	public String getDateFilter()
	{
		return this.dateFilter;
	}
	public void setDateFilter(String inDateFilter)
	{
		this.dateFilter = inDateFilter;
		RefreshTable();
	}
	
	//Other functions
	private void LogOut()
	{
		LoginV2_Form loginForm = new LoginV2_Form();
		display.close();
		loginForm.open();
	}

	// Test Mockup functions ---
	public void CallMockup() {
		DailyActivity tmpDailyActivity1 = new DailyActivity((long) 1934, "Ustajanje", "Ustati u 8", "23.12.2020.");
		DailyActivity tmpDailyActivity2 = new DailyActivity((long) 2034, "Trcanje", "5 km bentom", "23.12.2020.");
		DailyActivity tmpDailyActivity3 = new DailyActivity((long) 3134, "Ucenje", "Pregledati koncepte OOP-a",
				"23.12.2020.");
		DailyActivity tmpDailyActivity4 = new DailyActivity((long) 4334, "Vecera", "Naruci picu", "23.12.2020.");

		dailyActivities.add(tmpDailyActivity1);
		dailyActivities.add(tmpDailyActivity2);
		dailyActivities.add(tmpDailyActivity3);
		dailyActivities.add(tmpDailyActivity4);

		RefreshTable();
	}
}
