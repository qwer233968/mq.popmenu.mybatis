package mq.popmenu.mybatis.dialogs;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
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
import org.eclipse.swt.widgets.Text;

public class GeneratorDialog extends Dialog{

	private IJavaProject project;
	
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
			label.setText("账户: ");
			Text usertext = new Text(userComposite, SWT.BORDER);
			usertext.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
		{
			Composite pwdComposite = createComposite(parent, 2);
			Label label = new Label(pwdComposite, SWT.NONE);
			label.setText("密码: ");
			Text pwdtext = new Text(pwdComposite, SWT.BORDER|SWT.PASSWORD);
			pwdtext.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
		{
			Composite dbComposite = createComposite(parent, 2);
			Label label = new Label(dbComposite, SWT.NONE);
			label.setText("数据库类型: ");
			Combo cb = new Combo(dbComposite, SWT.DROP_DOWN|SWT.READ_ONLY);
			cb.add("mysql");
			cb.select(1);
		}
		{
			Composite driverComposite = createComposite(parent, 2);
			Text drivertext = new Text(driverComposite, SWT.BORDER|SWT.READ_ONLY);
			
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
		}
		return parent;
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
}
