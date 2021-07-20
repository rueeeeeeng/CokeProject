package login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import db.DB_Lib;
import main.CustomerMain;

public class Customer_Login extends JFrame implements ActionListener {

	private JButton btnOK;
	private JButton btnFindID, btnFindPW;
	private JButton btnJoin;
	private JLabel lbl1, lbl2;
	private JTextField tfID;
	private JPasswordField tfPW;
	private JPanel p1, p2, p3, p2_left, p2_right;

	public Customer_Login(String title, int width, int height) {
		setTitle(title); // 제목
		setSize(width, height); // 너비, 높이
		setLocationRelativeTo(this); // 위치
//		setResizable(false); //창 사이즈 조정 금지
//   	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프로그램이 끝나면 닫고 종료한다 

		// 레이아웃
		setLayout(new BorderLayout());

		p1 = new JPanel();
		p1.setBorder(BorderFactory.createEmptyBorder());

		ImageIcon imgs = new ImageIcon("Images/쇼핑몰로고.png");
		JLabel lbl = new JLabel(imgs);
		p1.add(lbl);
		add(p1, BorderLayout.NORTH);
		p1.setBackground(Color.WHITE);

		p2 = new JPanel();
		p2.setLayout(new BorderLayout());

		p2_left = new JPanel();
		p2_left.setBackground(Color.white);
		p2_left.setLayout(new GridLayout(2, 2));
		p2_left.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		lbl1 = new JLabel("ID:", JLabel.CENTER);
		tfID = new JTextField(10);

		lbl2 = new JLabel("PW:", JLabel.CENTER);
		tfPW = new JPasswordField(10);

		p2_left.add(lbl1);
		p2_left.add(tfID);
		p2_left.add(lbl2);
		p2_left.add(tfPW);

		p2.add(p2_left);

		p2_right = new JPanel();
		p2_right.setLayout(new BorderLayout());
		p2_right.setBackground(Color.red);
		
		btnOK = new JButton("OK");
		btnOK.addActionListener(this);
		btnOK.setBackground(Color.WHITE);
		//btnOK.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		//btnOK.setBorder(new LineBorder(Color.WHITE));
		p2_right.add(btnOK);
		
		p2.add(p2_right, BorderLayout.EAST);
		add(p2);

		p3 = new JPanel();
		btnFindID = new JButton("ID 찾기");
		btnFindID.addActionListener(this);
		btnFindPW = new JButton("PW 찾기");
		btnFindPW.addActionListener(this);
		p3.add(btnFindID); 
		p3.add(btnFindPW); 
		btnJoin = new JButton("회원가입");
		btnJoin.addActionListener(this);
		p3.add(btnJoin);
		p3.setBackground(Color.WHITE);

		add(p3, BorderLayout.SOUTH);

		setVisible(true);
	}

	public static void main(String[] args) {
		db.DB_Lib.init();
		new Customer_Login("고객 로그인", 400, 230);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnOK) {
			String id = tfID.getText();
			String pw = tfPW.getText();
//         System.out.println(id + " : " + pw);

			boolean check = checkIDPW(id, pw);
			if (check) {
				System.out.println("로그인 성공");
				JOptionPane.showMessageDialog(null, "로그인 성공했습니다");
				String sql2 = "INSERT INTO CONNECTUSER VALUES('" + id + "')";
				ResultSet rs2 = db.DB_Lib.getResultSet(sql2); // 수정
				new CustomerMain("메인", 500, 400);
				dispose();
			} else {
				System.out.println("로그인 실패");
				JOptionPane.showMessageDialog(null, "로그인 실패했습니다.");
				tfID.setText("");
				tfPW.setText("");
			}

		} else if (obj == btnFindID) {
			new FindId("ID 찾기", 300, 150);
		} else if (obj == btnFindPW) {
			new FindPw("pw 찾기", 350, 200);
		} else if (obj == btnJoin) {
			new JoinMemberShip("회원가입");
		}
	}

	private boolean checkIDPW(String id, String pw) {
		boolean check = false;
		String sql1 = "SELECT * FROM CUSTOMER WHERE ID = '" + id + "' AND PW = '" + pw + "'";
		ResultSet rs1 = db.DB_Lib.getResultSet(sql1);
		try {
			if (rs1.next()) {
				System.out.println(id + " " + pw);
				check = true;
			} else {
				System.out.println("해당 사용자가 없습니다.");
				check = false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return check;
	}

}