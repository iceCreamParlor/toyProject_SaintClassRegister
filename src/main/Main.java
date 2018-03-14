package main;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import register.SubjectRegister;
import subject.Subject;
import subject.SubjectInput;

public class Main {
	public static void main(String[] args) {
		Subject timeTable[][] = new Subject[9][8]; // row에는 교시, col에는 요일 정보가 입력되어 있음
		for(int i=0; i< 9; i++) {
			for(int j=0; j<8; j++) {
				timeTable[i][j]= null;
			}
		}
		final ArrayList<Subject> allCourse = new ArrayList<>();
		final ArrayList<Subject> pastCourse = new ArrayList<>();
		final ArrayList<Subject> myCourse = new ArrayList<>();

		//ToTalSubject.txt(개설 교과목 정보)로부터 과목 정보 입력받기
		SubjectInput.Input(allCourse);
		//PastSubject.txt(과거 수강 과목 정보)로부터 과목 정보 입력받기
		SubjectInput.PastSubjectInput(pastCourse);
		
		new MainMenu(allCourse, pastCourse, myCourse, timeTable);
 	}
}
class MainMenu extends Frame{
	Label l1, l2;
	Panel p1= new Panel();Panel p2= new Panel();Panel p3 = new Panel();
	Button btn = new Button("로그인");
	TextField ID, pwd;
	MainMenu(ArrayList<Subject> allCourse, ArrayList<Subject> pastCourse,ArrayList<Subject> myCourse, Subject[][] timeTable){
		super("SAINT");
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				setVisible(false);
				dispose();
			}
		});
		
		this.setBounds(200, 200, 500, 500);
		this.setLayout(new FlowLayout());
		l1 = new Label("아이디");l2 = new Label("비밀번호");
		ID = new TextField(30); pwd = new TextField(30);
		p1.add(l1);p1.add(ID);
		p2.add(l2);p2.add(pwd);
		p3.add(btn);
		this.add(new Panel().add(new Label("                                                              ")));
		this.add(p1);this.add(p2); 
		this.add(new Panel().add(new Label("                                                              ")));
		this.add(btn);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String ID_par = ID.getText();
				String pwd_par= pwd.getText();
				if(ID_par.equals("admin") && pwd_par.equals("1234")) {	//로그인 성공
					SubjectRegister.Register(allCourse, pastCourse, myCourse, timeTable);
					MainMenu.this.dispose();
					MainMenu.this.setVisible(false);
				}
				else {		//로그인 실패
					Frame f = new Frame();
					f.setBounds(200,200, 200, 200);
					f.setLayout(new FlowLayout());
					f.add(new Label("ID/비밀번호를 확인하세요"));
					f.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent we) {
							f.dispose();
							f.setVisible(false);
						}
					});
					f.setVisible(true);
				}
			}
		});
		this.setVisible(true);
	}
}