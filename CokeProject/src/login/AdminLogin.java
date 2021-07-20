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
import manager.ProductAddAndDelete;

public class AdminLogin extends JFrame implements ActionListener {

	private JPanel panBase, panCenter, panSouth;
	private JButton btnOK;
	private JButton btnFindID, btnFindPW;
	private JButton btnJoin;
	private JLabel lbl1, lbl2;
	private JTextField tfID;
	private JPasswordField tfPW;

	public AdminLogin(String title, int width, int height) {
		setTitle(title); // 제목
		setSize(width, height); // 너비, 높이
		setLocationRelativeTo(this); // 위치
		setResizable(false);
//   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프로그램이 끝나면 닫고 종료한다 

		// 레이아웃
		setLayout(new BorderLayout());

		JPanel p1 = new JPanel();
		p1.setBorder(BorderFactory.createEmptyBorder());

		ImageIcon imgs = new ImageIcon("Images/쇼핑몰로고.png");
		JLabel lbl = new JLabel(imgs);
		p1.add(lbl);
		add(p1, BorderLayout.NORTH);
		p1.setBackground(Color.WHITE);

		createPanBase();
		add(panBase);

		setVisible(true);
	}

	private void createPanBase() {
		panBase = new JPanel();
		panBase.setLayout(new BorderLayout());
//      panBase.setBackground(Color.red);

		createPanCenter();
		panBase.add(panCenter, BorderLayout.CENTER); //
		createPanSouth();
		panBase.add(panSouth, BorderLayout.SOUTH);
	}

	private void createPanCenter() {
		panCenter = new JPanel(); // 중앙에 JPanel 만들기
		panCenter.setBackground(Color.WHITE);
//      panCenter.setBackground(Color.BLUE); // 중앙에 배경색이 파란색
//      panCenter.setLayout(new GridLayout(2, 2));
//      panCenter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		lbl1 = new JLabel("ID :", JLabel.CENTER);
		tfID = new JTextField(10);

		lbl2 = new JLabel("PW :", JLabel.CENTER);
		tfPW = new JPasswordField(10);

		btnOK = new JButton("확인");
		btnOK.addActionListener(this);

		panCenter.add(lbl1);
		panCenter.add(tfID);
		panCenter.add(lbl2);
		panCenter.add(tfPW);
		panCenter.add(btnOK);

	}

	private void createPanSouth() {
		panSouth = new JPanel(); // 남쪽에 JPanel 만들기
		panSouth.setBackground(Color.white); // 남쪽에 배경색이 노란색
//
//		btnFindID = new JButton("ID 찾기");
//		btnFindID.addActionListener(this);
//		btnFindID.setBackground(Color.WHITE);
//		btnFindPW = new JButton("PW 찾기");
//		btnFindPW.addActionListener(this);
//		panSouth.add(btnFindID); // 로그인 버튼 생성(남쪽)
//		panSouth.add(btnFindPW); // 로그인 버튼 생성(남쪽)
//		btnJoin = new JButton("회원가입");
//		btnJoin.addActionListener(this);
//		panSouth.add(btnJoin);
//		panSouth.setBackground(Color.WHITE);

//      btnOK.addActionListener(new ActionListener() {
//         public void actionPerformed(ActionEvent e) {
//            String id = "cola";
//            String pw = "0517";
//            
//            if(id.equals(tfID.getText())&&pw.equals(tfPW.getText())) {
//               JOptionPane.showMessageDialog(null, "로그인에 성공하였습니다.");
//            } else {
//               JOptionPane.showMessageDialog(null, "로그인에 실패하였습니다.");
//            }
//         }
//      });

	}

	public static void main(String[] args) {
		db.DB_Lib.init();
		new AdminLogin("관리자 로그인", 350, 230);

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
				new ProductAddAndDelete();
				dispose(); //or setVisible
			} else {
				System.out.println("로그인 실패");
				JOptionPane.showMessageDialog(null, "로그인 실패했습니다.");
				tfID.setText("");
				tfPW.setText("");
			}

		}
//		else if(obj==btnFindID) {
//			new FindId("ID 찾기", 300, 150);
//		}
//		else if(obj==btnFindPW) {
//			new findPw("pw 찾기", 350, 200);
//		}
//		else if(obj==btnJoin) {
//			new JoinMemberShip("회원가입");
//		}
//		else if(obj =)

	}

	private boolean checkIDPW(String id, String pw) {
		boolean check = false;
		String sql1 = "SELECT * FROM ADMIN WHERE ID = '" + id + "' AND PW = '" + pw + "'";
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