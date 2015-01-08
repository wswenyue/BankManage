package cn.sdut.app;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener {

	public MainFrame(String title) {
		super(title);
		init();
	}
	JLabel label_info;
	JButton bt_createAccount;
	JButton bt_saveMoney;
	JButton bt_getMoney;
	JButton bt_turnAccount;
	JButton bt_select;
	JButton bt_updatePassword;
	JButton bt_destroyAccount;
	JButton bt_exit;
	private void init() {
		label_info=new JLabel("----欢迎使用银行管理系统----");
		label_info.setHorizontalAlignment(JLabel.CENTER);
		bt_createAccount=new JButton("开户");
		bt_saveMoney=new JButton("存款");
		bt_getMoney=new JButton("取款");
		bt_turnAccount=new JButton("转账");
		bt_select=new JButton("查询");
		bt_updatePassword=new JButton("修改密码");
		bt_destroyAccount=new JButton("销户");
		bt_exit=new JButton("退出");
		add(label_info);		
		add(bt_createAccount);
		add(bt_saveMoney);
		add(bt_getMoney);
		add(bt_turnAccount);
		add(bt_select);
		add(bt_updatePassword);
		add(bt_destroyAccount);
		add(bt_exit);
		
		bt_createAccount.addActionListener(this);
		bt_saveMoney.addActionListener(this);
		bt_getMoney.addActionListener(this);
		bt_turnAccount.addActionListener(this);
		bt_select.addActionListener(this);
		bt_updatePassword.addActionListener(this);
		bt_destroyAccount.addActionListener(this);
		bt_exit.addActionListener(this);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(300, 300, 300, 300);
		setLayout(new GridLayout(9,1));
		setVisible(true);
			}

	public static void main(String[] args) {
		new MainFrame("欢迎进入银行管理系统");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="开户")
		{
			new CreateAccountFrame("开户");
		}
		else
		{
			if(e.getActionCommand()=="存款")
			{
				new SaveMoneyFrame("存款");
			}
			else
			{
				if(e.getActionCommand()=="取款")
				{
					new GetMoneyFrame("取款");
				}
				else
				{
					if(e.getActionCommand()=="转账")
					{
						new TurnAccountFrame("转账");
					}
					else
					{
						if(e.getActionCommand()=="查询")
						{
							new SelectAccountFrame("查询");
						}
						else
						{
							if(e.getActionCommand()=="修改密码")
							{
								new ModPasswordFrame("修改密码");
							}
							else
							{
								if(e.getActionCommand()=="销户")
								{
									new DestroyAccountFrame("销户");
								}
								else
								{									
									System.exit(0);
								}
							}
						}
					}
				}
			}
		}	
	}
}
