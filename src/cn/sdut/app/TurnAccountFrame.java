package cn.sdut.app;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cn.sdut.biz.AccountBiz;

/*创建账户窗口，输入姓名和初始金额*/
@SuppressWarnings("serial")
public class TurnAccountFrame extends JFrame implements ActionListener {
	
	JLabel lb_outAccountId;
	JTextField tf_outAccountId;
	JLabel lb_password;
	JTextField tf_password;
	JLabel lb_inAccountId;
	JTextField tf_inAccountId;	
	JLabel lb_amount;
	JTextField tf_amount;
	
	JTextField tf_SecurityCode;
	JButton bt_SecurityCode;
	
    JButton bt_OK;
    JButton bt_reset;
    JScrollPane sp_message;
    JTextArea ta_message;
    
    String s=null;
	
	public TurnAccountFrame(String title) {
		super(title);
		init();
	}

	private void init() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setBounds(300, 300, 320, 300);
		setLayout(new FlowLayout());
		lb_outAccountId=new JLabel("请输入转出账号:");
		tf_outAccountId=new JTextField(16);
		lb_password=new JLabel("请输入用户密码:");
		tf_password=new JTextField(16);
		lb_inAccountId=new JLabel("请输入转入账号:");
		tf_inAccountId=new JTextField(16);
		
		lb_amount=new JLabel("请输入转账金额:");
		tf_amount=new JTextField(16);
		
		tf_SecurityCode = new JTextField(12);
		bt_SecurityCode = new JButton("点我获取验证码");
		
        bt_OK=new JButton("确定");
        bt_reset=new JButton("重置");
        ta_message=new JTextArea(8,28);
        ta_message.setLineWrap(true);
        sp_message=new JScrollPane(ta_message);
        
        add(lb_outAccountId);
        add(tf_outAccountId);
        add(lb_password);
        add(tf_password);
        add(lb_inAccountId);
        add(tf_inAccountId);      
        add(lb_amount);
        add(tf_amount);
        
        add(tf_SecurityCode);
		add(bt_SecurityCode);
		
        add(bt_OK);
        add(bt_reset);
        add(sp_message);
        setVisible(true);
        
        bt_OK.addActionListener(this);
        bt_reset.addActionListener(this);
        bt_SecurityCode.addActionListener(this);
		
		bt_OK.setEnabled(false);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String str_outAccountId=tf_outAccountId.getText();
		String str_password=tf_password.getText();
		String str_inAccountId=tf_inAccountId.getText();
		String amount = tf_amount.getText();
		String Scode = tf_SecurityCode.getText();
		
		
		if(e.getActionCommand() == "点我获取验证码"){
				if(str_outAccountId == null || "".equals(str_outAccountId)
						|| str_inAccountId == null || "".equals(str_inAccountId)
						|| str_password == null || "".equals(str_password)
						|| amount == null || "".equals(amount)){
					
					ta_message.setText("用户名,密码、金额均不能为空");
					return;
					
				}else{
					int accountId = Integer.parseInt(str_outAccountId);
					AccountBiz accountBiz = new AccountBiz();
					String strReturn = accountBiz.getScodeById(accountId);
					
					if(strReturn.equals("用户不存在！")){
						ta_message.setText("账号或密码输入错误");
					    return;
					}else{
						s = strReturn;
						ta_message.setText("验证码已发，请注意查收");
					}
					bt_OK.setEnabled(true);
					bt_SecurityCode.setEnabled(false);
				}
			
		}
		
		if(e.getActionCommand()=="确定")
		{

			if( str_outAccountId == null|| "".equals(str_outAccountId)
				|| str_inAccountId == null || "".equals(str_inAccountId)
				|| str_password == null || "".equals(str_password)
				|| amount == null || "".equals(amount) 
				|| Scode == null || "".equals(Scode) ){
				ta_message.setText("对不起，您输入的转出账号、密码、输入账号、验证码有空值");
			}
			else{
				if(Scode.equals(s)){
				
					try {
						int outAccountId=Integer.parseInt(str_outAccountId);
						int inAccountId=Integer.parseInt(str_inAccountId);
						float turnAmount=Float.parseFloat(amount);
						AccountBiz accountBiz=new AccountBiz();
						String strReturn = accountBiz.turnAccount(outAccountId, str_password, inAccountId, turnAmount);
						ta_message.setText(strReturn);
					} catch (NumberFormatException e1) {
						ta_message.setText("对不起，您输入的转出账号、输入账号、转账金额有非法字符");
					}
				}else{
					ta_message.setText("验证码输入不正确，请重新输入");
					bt_SecurityCode.setEnabled(true);
				}
			
			}
			
		}
		
		
		if(e.getActionCommand()=="重置")
		{
			tf_outAccountId.setText("");
			tf_amount.setText("");
			tf_outAccountId.setText("");
			tf_password.setText("");
			tf_SecurityCode.setText("");
			tf_inAccountId.setText("");
			ta_message.setText("");
		}
	
		
	}

	
}
