package mq.popmenu.mybatis.dialogs;

import java.util.List;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.qxp.ctrl.mysql.DatabaseMetaDateApplication;

public class GeneratorDialog extends Dialog{

	private IJavaProject project;
	private Text urltext;
	private Text usertext;
	private Text pwdtext;
	private Text drivertext;
	
	public GeneratorDialog(Shell parent, IJavaProject project) {
		super(parent);
		this.project = project;
	}
	
	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID){
			
		}
		super.buttonPressed(buttonId);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 30;
		layout.marginHeight = 10;
		getShell().setText("Mybatis 代码自动生成"); //设置Dialog的标头
		parent.setLayout(layout);
		parent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		{
			Composite userComposite = createComposite(parent, 2);
			Label label = new Label(userComposite, SWT.NONE);
			label.setText("链接: ");
			urltext = new Text(userComposite, SWT.BORDER);
			urltext.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
		{
			Composite userComposite = createComposite(parent, 2);
			Label label = new Label(userComposite, SWT.NONE);
			label.setText("账户: ");
			usertext = new Text(userComposite, SWT.BORDER);
			usertext.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
		{
			Composite pwdComposite = createComposite(parent, 2);
			Label label = new Label(pwdComposite, SWT.NONE);
			label.setText("密码: ");
			pwdtext = new Text(pwdComposite, SWT.BORDER|SWT.PASSWORD);
			pwdtext.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
		{
			Composite dbComposite = createComposite(parent, 2);
			Label label = new Label(dbComposite, SWT.NONE);
			label.setText("数据库类型: ");
			Combo cb = new Combo(dbComposite, SWT.DROP_DOWN|SWT.READ_ONLY);
			cb.add("mysql");
			cb.select(0);
		}
		{
			Composite driverComposite = createComposite(parent, 2);
			drivertext = new Text(driverComposite, SWT.BORDER|SWT.READ_ONLY);
			drivertext.setLayoutData(new GridData(GridData.FILL_BOTH));
			Button driverbtn = new Button(driverComposite, SWT.NONE);
			driverbtn.setText("驱动选择");
			driverbtn.addSelectionListener(new SelectionListener(){
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					FileDialog f =new FileDialog(getShell(), SWT.BORDER);
					String result = f.open();
					drivertext.setText(result);
				}});
		}
		{
			Composite db_btnComposite = createComposite(parent, 2);
			Button testbtn = new Button(db_btnComposite, SWT.NONE);
			testbtn.setText("测试连接");
			Button databtn = new Button(db_btnComposite, SWT.NONE);
			databtn.setText("获取内容");
			testbtn.addSelectionListener(new SelectionListener(){
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					String url = urltext.getText();
					String user = usertext.getText();
					String pwd = pwdtext.getText();
					System.out.println("drivertext" + drivertext.getText());
					String[] jarfiles = {drivertext.getText()};
					testConn(jarfiles, url, user, pwd);
				}});
			databtn.addSelectionListener(new SelectionListener(){
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					DBTableDialog db_dialog = new DBTableDialog(getShell());
					db_dialog.open();
				}});
			
		}
		{
			/*ScrolledComposite c2 = new ScrolledComposite(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
			Composite c = new Composite(c2, SWT.NONE);
			c2.setContent(c);
			c2.setExpandHorizontal(true);
			c2.setExpandVertical(true);
			c2.setMinWidth(200);
			c2.setMinHeight(200);
			c.setLayout(new FormLayout());
			final TableViewer tableViewer = new TableViewer(c, SWT.BORDER);
			Table table = tableViewer.getTable();
			final FormData formData = new FormData();
			formData.bottom = new FormAttachment(100, -5);
			formData.top = new FormAttachment(0, 5);
			formData.left = new FormAttachment(0, 5);
			table.setLayoutData(formData);
			tableViewer.setLabelProvider(new LabelProviderImpl());
			tableViewer.setContentProvider(new ContentProviderImpl());
			tableViewer.setInput(new Object());*/
			
		}
		return parent;
	}
	
	public void testConn(String[] jarfiles, String url, String user, String pwd){
		try {
			DatabaseMetaDateApplication dm = new DatabaseMetaDateApplication(jarfiles, url, user, pwd);
			dm.colseCon();
			MessageDialog.openInformation(
					getShell(),
					"Success",
					"连接成功.");
		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openInformation(
					getShell(),
					"Error",
					"无法连接数据库.");
		}
	}
	
	public List<String> getDBTable(String[] jarfiles, String url, String user, String pwd){
		try {
			DatabaseMetaDateApplication dm = new DatabaseMetaDateApplication(jarfiles, url, user, pwd);
			List<String> list = dm.getAllTableList(null);
			dm.colseCon();
			return list;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public Composite createComposite(Composite parent, int columnsLength){
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layoutComposite = new GridLayout();
		layoutComposite.numColumns = columnsLength;
		layoutComposite.marginHeight = 10;
		composite.setLayout(layoutComposite);
		composite.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,2,2));
		return composite;
	}
	
	// 创建放置表格的面板  
    private void createTableComposite(Composite parent)  {  
    	ViewForm viewForm = new ViewForm(parent, SWT.NONE | SWT.H_SCROLL | SWT.V_SCROLL);  
        GridLayout gridLayout = new GridLayout();  
        gridLayout.numColumns = 1;  
        Composite composite = new Composite(viewForm, SWT.NONE | SWT.H_SCROLL | SWT.V_SCROLL);  
        composite.setLayout(gridLayout);  
        createTable(composite);  
    }  
    
 // 创建表格  
    private void createTable(Composite composite)  
    {  
        // 表格布局  
        GridData gridData = new org.eclipse.swt.layout.GridData();  
        gridData.horizontalAlignment = SWT.FILL;  
        gridData.grabExcessHorizontalSpace = true;  
        gridData.grabExcessVerticalSpace = true;  
        gridData.verticalAlignment = SWT.FILL;  
  
        // 创建表格，使用SWT.FULL_SELECTION样式，可同时选中一行  
        Table table = new Table(composite, SWT.MULTI | SWT.FULL_SELECTION | SWT.CHECK);  
        table.setHeaderVisible(true);// 设置显示表头  
        table.setLayoutData(gridData);// 设置表格布局  
        table.setLinesVisible(true);// 设置显示表格线/*  
        // 创建表头的字符串数组  
        String[] tableHeader = {"姓名", "性别", "电话", "电子邮件"};  
        for (int i = 0; i < tableHeader.length; i++)  
        {  
            TableColumn tableColumn = new TableColumn(table, SWT.NONE);  
            tableColumn.setText(tableHeader[i]);  
            // 设置表头可移动，默认为false  
            tableColumn.setMoveable(true);  
        }  
        // 添加三行数据  
        TableItem item = new TableItem(table, SWT.NONE);  
        item.setText(new String[]{"张三", "男", "123", ""});  
        // 设置图标  
        // item.setImage( ImageFactory.loadImage(  
        // table.getDisplay(),ImageFactory.ICON_BOY));  
  
        for (int i = 0; i < 5; i++)  
        {  
            item = new TableItem(table, SWT.NONE);  
            item.setText(new String[]{"李四", "男", "4582", ""});  
        }  
        // 设置图标  
        // item.setImage( ImageFactory.loadImage(  
        // table.getDisplay(),ImageFactory.ICON_BOY));  
  
        item = new TableItem(table, SWT.NONE);  
        item.setText(new String[]{"周五", "女", "567", ""});  
        // /****************************************************  
        // 为单元格注册选中事件  
        table.addSelectionListener(new SelectionAdapter()  
        {  
            public void widgetSelected(SelectionEvent e)  
            {  
                // 获得所有的行数  
                int total = table.getItemCount();  
                // 循环所有行  
                for (int i = 0; i < total; i++)  
                {  
                    TableItem item = table.getItem(i);  
                    // 如果该行为选中状态，改变背景色和前景色，否则颜色设置  
                    if (table.isSelected(i))  
                    {  
                        item.setBackground(getShell().getDisplay().getSystemColor(  
                                SWT.COLOR_RED));  
                        item.setForeground(getShell().getDisplay().getSystemColor(  
                                SWT.COLOR_WHITE));  
                    }  
                    else  
                    {  
                        item.setBackground(null);  
                        item.setForeground(null);  
                    }  
                }  
            }  
  
        });  
        // ******************************************************/  
    }
}
