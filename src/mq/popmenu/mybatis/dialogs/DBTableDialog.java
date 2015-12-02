package mq.popmenu.mybatis.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.ControlEditor;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class DBTableDialog extends Dialog{

	private ViewForm viewForm = null;  
	  
    private ToolBar toolBar = null;  
  
    private Composite composite = null;  
  
    private Table table = null;  
  
    private Menu menu = null;  
  
    private ScrolledComposite scrolledComposite;
	protected DBTableDialog(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID){
			
		}
		super.buttonPressed(buttonId);
	}
	
	// 创建ViewForm面板放置工具栏和表格  
	@Override
	protected Control createDialogArea(Composite parent) {
		getShell().setText("数据库表选择框"); //设置Dialog的标头   
		/*viewForm = new ViewForm(parent, SWT.NONE | SWT.H_SCROLL | SWT.V_SCROLL);  
        // viewForm.setTopCenterSeparate(true);  
        createToolBar();  
        viewForm.setTopLeft(toolBar);  
        Composite tablecomposite = new Composite(parent, SWT.NONE);  
        tablecomposite.setLayout(new FillLayout());  */
        createComposite(parent);  
        //parent.setContent(scrolledComposite);  
		return parent;
	}
  
    // 创建工具栏  
    private void createToolBar()  
    {  
        toolBar = new ToolBar(viewForm, SWT.FLAT);  
        final ToolItem add = new ToolItem(toolBar, SWT.PUSH);  
        add.setText("全选");  
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.substring(1, path.length());
        add.setImage(new Image(toolBar.getDisplay(), path + "icons/quanxuan.png"));  
        final ToolItem del = new ToolItem(toolBar, SWT.PUSH);  
        del.setText("取消");  
        del.setImage(new Image(toolBar.getDisplay(), path + "icons/quxiao.png"));  
        // 工具栏按钮事件处理  
        Listener listener = new Listener() {  
            @Override  
            public void handleEvent(Event event)  
            {  
               //event.widget == add
            }  
        };  
        // 为工具栏的按钮注册事件  
        add.addListener(SWT.Selection, listener);  
        del.addListener(SWT.Selection, listener);  
    }  
  
    // 创建放置表格的面板  
    private void createComposite(Composite parent)  
    {  
    	scrolledComposite = new ScrolledComposite(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
    	scrolledComposite.setLayoutData(new GridData(GridData.FILL_BOTH));    
    	scrolledComposite.setExpandHorizontal(true);    
    	scrolledComposite.setExpandVertical(true);    
    	scrolledComposite.setLayout(new GridLayout(1, false));    
    	
        //GridLayout gridLayout = new GridLayout();  
        //gridLayout.numColumns = 1;  
        composite = new Composite(scrolledComposite, SWT.NONE);  
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));  
        composite.setLayout(new GridLayout(1, true));    
        
        //composite.setSize(scrolledComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));//关键点3
        scrolledComposite.setContent(composite); 
        createTable();  
        scrolledComposite.setMinHeight(200);    
        scrolledComposite.setMinWidth(200);  
        composite.setSize(200, 200);  
    }  
  
    // 创建表格  
    private void createTable()  
    {  
        // 表格布局  
        //GridData gridData = new org.eclipse.swt.layout.GridData();  
       /* gridData.horizontalAlignment = SWT.FILL;  
        gridData.grabExcessHorizontalSpace = true;  
        gridData.grabExcessVerticalSpace = true;  
        gridData.verticalAlignment = SWT.FILL;  */
  
        // 创建表格，使用SWT.FULL_SELECTION样式，可同时选中一行  
        table = new Table(composite, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION | SWT.CHECK);  
        table.setSize(200, 200);
        table.setHeaderVisible(true);// 设置显示表头  
        //table.setLayoutData(gridData);// 设置表格布局  
        table.setLinesVisible(true);// 设置显示表格线/*  
        // 创建表头的字符串数组  
        String[] tableHeader = {"表名"};  
        for (int i = 0; i < tableHeader.length; i++)  
        {  
            TableColumn tableColumn = new TableColumn(table, SWT.NONE);  
            tableColumn.setText(tableHeader[i]);  
            // 设置表头可移动，默认为false  
            tableColumn.setMoveable(true);  
        }  
        // 添加三行数据  
        TableItem item = new TableItem(table, SWT.NONE);  
        item.setText(new String[]{"张三"});  
        // 设置图标  
        // item.setImage( ImageFactory.loadImage(  
        // table.getDisplay(),ImageFactory.ICON_BOY));  
  
        for (int i = 0; i < 50; i++)  
        {  
            item = new TableItem(table, SWT.NONE);  
            item.setText(new String[]{"李四"});  
        }  
        // 设置图标  
        // item.setImage( ImageFactory.loadImage(  
        // table.getDisplay(),ImageFactory.ICON_BOY));  
  
        item = new TableItem(table, SWT.NONE);  
        item.setText(new String[]{"周五"});  
        // 设置图标  
        // item.setImage( ImageFactory.loadImage(  
        // table.getDisplay(),ImageFactory.ICON_GIRL));  
  
        // 添加可编辑的单元格  
        // /******************************************************  
        TableItem[] items = table.getItems();  
        for (int i = 0; i < items.length; i++)  
        {  
            // 第一列设置，创建TableEditor对象  
            final TableEditor editor = new TableEditor(table);  
            // 创建一个文本框，用于输入文字  
            final Text text = new Text(table, SWT.NONE);  
            // 将文本框当前值，设置为表格中的值  
            text.setText(items[i].getText(0));  
            // 设置编辑单元格水平填充  
            editor.grabHorizontal = true;  
            // 关键方法，将编辑单元格与文本框绑定到表格的第一列  
            editor.setEditor(text, items[i], 0);  
            // 当文本框改变值时，注册文本框改变事件，该事件改变表格中的数据。  
            // 否则即使改变的文本框的值，对表格中的数据也不会影响  
            text.addModifyListener(new ModifyListener()  
            {  
                public void modifyText(ModifyEvent e)  
                {  
                    editor.getItem().setText(1, text.getText());  
                }  
  
            });  
            // 同理，为第二列绑定下 拉框CCombo  
            final TableEditor editor1 = new TableEditor(table);  
            final CCombo combo = new CCombo(table, SWT.NONE);  
            combo.add("男");  
            combo.add("女");  
            combo.setText(items[i].getText(1));  
            editor1.grabHorizontal = true;  
            editor1.setEditor(combo, items[i], 1);  
            combo.addModifyListener(new ModifyListener()  
            {  
                public void modifyText(ModifyEvent e)  
                {  
                    editor1.getItem().setText(1, combo.getText());  
                }  
  
            });  
  
        }  
        // *****************************************************/  
        // /***************************************************  
        // 创建TableCursor对象，使用上下左右键可以控制表格  
        final TableCursor cursor = new TableCursor(table, SWT.NONE);  
        // 创建可编辑的控件  
        final ControlEditor editor = new ControlEditor(cursor);  
        editor.grabHorizontal = true;  
        editor.grabVertical = true;  
        // 为TableCursor对象注册事件  
        cursor.addSelectionListener(new SelectionAdapter()  
        {  
            // 但移动光标，在单元格上单击回车所触发的事件  
            public void widgetDefaultSelected(SelectionEvent e)  
            {  
                // 创建一个文本框控件  
                final Text text = new Text(cursor, SWT.NONE);  
                // 获得当前光标所在的行TableItem对象  
                TableItem row = cursor.getRow();  
                // 获得当前光标所在的列数  
                int column = cursor.getColumn();  
                // 当前光标所在单元格的值赋给文本框  
                text.setText(row.getText(column));  
                // 为文本框注册键盘事件  
                text.addKeyListener(new KeyListener()  
                {  
                    @Override  
                    public void keyPressed(KeyEvent e)  
                    {  
                        // 此时在文本框上单击回车后，这是表格中的数据为修改后文本框中的数据  
                        // 然后释放文本框资源  
                        if (e.character == SWT.CR)  
                        {  
                            TableItem row = cursor.getRow();  
                            int column = cursor.getColumn();  
                            row.setText(column, text.getText());  
                            text.dispose();  
                        }  
                        // 如果在文本框中单击了ESC键，则并不对表格中的数据进行修改  
                        if (e.character == SWT.ESC)  
                        {  
                            text.dispose();  
                        }  
                    }  
  
                    @Override  
                    public void keyReleased(KeyEvent e)  
                    {  
                        // TODO Auto-generated method stub  
  
                    }  
                });  
                // 注册焦点事件  
                text.addFocusListener(new FocusListener()  
                {  
                    // 当该文本框失去焦点时，释放文本框资源  
                    public void focusLost(FocusEvent e)  
                    {  
                        text.dispose();  
                    }  
  
                    @Override  
                    public void focusGained(FocusEvent e)  
                    {  
                        // TODO Auto-generated method stub  
  
                    }  
                });  
                // 将该文本框绑定到可编辑的控件上  
                editor.setEditor(text);  
                // 设置文本框的焦点  
                text.setFocus();  
            }  
  
            // 移动光标到一个单元格上所触发的事件  
            public void widgetSelected(SelectionEvent e)  
            {  
                table.setSelection(new TableItem[]{cursor.getRow()});  
            }  
        });  
        cursor.addMouseListener(new MouseListener()  
        {  
  
            @Override  
            public void mouseDoubleClick(MouseEvent e)  
            {  
                // TODO Auto-generated method stub  
  
            }  
  
            @Override  
            public void mouseDown(MouseEvent e)  
            {  
                if (e.button == 3)  
                { // 右键按下，显示右键菜单  
                    menu.setVisible(true);  
                }  
            }  
  
            @Override  
            public void mouseUp(MouseEvent e)  
            {  
                // TODO Auto-generated method stub  
  
            }  
  
        });  
        // ******************************************************/  
        // 重新布局表格  
        for (int i = 0; i < tableHeader.length; i++)  
        {  
            table.getColumn(i).pack();  
        }  
        // /****************************************************  
        // 为单元格注册选中事件  
        table.addSelectionListener(new SelectionAdapter()  
        {  
            public void widgetSelected(SelectionEvent e)  
            {  
                // 获得所有的行数  
                int total = table.getItemCount();  
                // 循环所有行  
                for (int i = 0; i < total; i++) {  
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
