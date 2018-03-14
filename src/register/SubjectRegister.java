package register;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import subject.Subject;

public class SubjectRegister {
	
	public static void Register(ArrayList<Subject> allCourse, ArrayList<Subject> pastCourse, ArrayList<Subject> myCourse, Subject[][] timeTable) {
		new Register_Subject(allCourse, pastCourse, myCourse, timeTable);
		
	}
	
	static boolean PrerequisiteCheck(String str, ArrayList<Subject> allCourse, ArrayList<Subject> pastCourse) {
		boolean check= false;
		Iterator<Subject> itr = allCourse.iterator();
		Subject inp= null;
		while(itr.hasNext()) {
			inp = itr.next();
			if(inp.getName().equals(str) || inp.getCode().equals(str))	break;
		}
		if(inp.getPreRequisite()==null)	check= true;
		else{
			String prerequisite = inp.getPreRequisite();
			itr = pastCourse.iterator();
			while(itr.hasNext()) {
				if(prerequisite.equals(itr.next().getCode()))	check = true;
			}
		}
		return check;	//true를 반환하면 수강 가능한 것이다!
	}
	
	static boolean TimeTableCheck(String str, ArrayList<Subject> allCourse, Subject[][] timeTable) {
		Iterator<Subject> itr = allCourse.iterator();
		Subject inp= null;
		while(itr.hasNext()) {
			inp = itr.next();
			if(inp.getName().equals(str) || inp.getCode().equals(str))	break;
		}
		if(timeTable[inp.getSchedule()[0].getTime()][inp.getSchedule()[0].getDayInt() ]==null && timeTable[inp.getSchedule()[1].getTime()][inp.getSchedule()[1].getDayInt() ]==null) {
			return true;
		}
		
		else return false;	//true 가 반환되면 수강 신청 가능 !
	}
	
	static boolean CheckPastCourse(String str, ArrayList<Subject> pastCourse) {
		boolean check = false;
		Iterator<Subject> itr = pastCourse.iterator();
		while(itr.hasNext()) {
			Subject temp = itr.next();
			if(temp.getName().equals(str) ||temp.getCode().equals(str)) {
				check = true;
			}
		}
		return check; // true가 반환되면 과거에 수강한 내력이 있는 것
	}
	
	static boolean CheckAllCourse(String str, ArrayList<Subject> allCourse) {
		boolean check = false;
		Iterator<Subject> itr = allCourse.iterator();
		while(itr.hasNext()) {
			Subject temp = itr.next();
			if(str.contains(temp.getName()) || str.equals(temp.getCode())) {
				check = true;
			}
		}
		return check; // true가 반환되면 개설 교과목 정보에 있는 과목
	}
}

class Delete_Subject extends Frame{
	Button btn[] = new Button[7];
	Label name[] = new Label[7];
	Label code[] = new Label[7];
	Label time1[] = new Label[7];
	Label time2[] = new Label[7];
	
	Delete_Subject(ArrayList<Subject> myCourse, Subject[][] timeTable){
		super("수강신청 정정");
		this.setLayout(new GridLayout(7, 1));
		this.setBounds(200, 200, 700, 500);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				Delete_Subject.this.setVisible(false);
				dispose();
			}
		});
		if(myCourse.isEmpty()==true) {		
			Frame result = new Frame();
			Label temp = new Label("수강 신청 내역이 없습니다");
			result.setBounds(200, 200, 300, 200);
			result.add(temp);
			result.setVisible(true);
			result.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent we) {
					result.dispose();
					result.setVisible(false);
					Delete_Subject.this.dispose();
					Delete_Subject.this.setVisible(false);
				}
			});
		}
		else {
			for(int i=0; i<myCourse.size(); i++) {
				if(myCourse.get(i)==null)	break;
				else {
					Panel temp = new Panel();
					int index = i;
					name[i] = new Label(myCourse.get(i).getName());
					code[i] = new Label( myCourse.get(i).getCode() );
					time1[i] = new Label( myCourse.get(i).getSchedule()[0].getDay() +"  " +  myCourse.get(i).getSchedule()[0].getTime() + " 교시");
					time2[i]= new Label( myCourse.get(i).getSchedule()[1].getDay() +"  " +  myCourse.get(i).getSchedule()[1].getTime() + " 교시");
					btn[i] = new Button("삭제");
					btn[i].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent ae) {
							Iterator<Subject> itr = myCourse.iterator();
							Subject sbj;
							while(itr.hasNext()) {
								sbj = itr.next();
								if(sbj.getCode().equals(code[index].getText())) {
									itr.remove();
								}
							}
							for(int i=0; i<9; i++) {
								for(int j=0; j<8; j++) {
									if(timeTable[i][j]==null)	continue;
									else if(timeTable[i][j].getCode().equals(code[index].getText())) {
										timeTable[i][j] = null;
									}
								}
							}
							Frame notify = new Frame();
							notify.setBounds(200, 200, 200, 200);
							notify.add(new Label("수강 신청이 취소되었습니다."));
							notify.setVisible(true);
							notify.addWindowListener(new WindowAdapter() {
								public void windowClosing(WindowEvent we) {
									notify.dispose();
									notify.setVisible(false);
									dispose();
									setVisible(false);
								}
							});
						}
					});
					temp.add(name[i]);	temp.add(code[i]);	temp.add(time1[i]);	temp.add(time2[i]);temp.add(btn[i]);
					this.add(temp);
				}
			}
		}
		this.setVisible(true);
	}
}

class Register_Subject extends Frame{
	boolean saveCheck = false;
	MenuBar menubar= new MenuBar();
	Menu m_subject, m_change, m_account;
	MenuItem mi_save,mi_allSubject, mi_mySubject , mi_changeSubject, mi_myPastCourse, mi_logout;
	
	Label trash = new Label();
	TextField tf[] = new TextField[7];
	Button btn_code = new Button("저장");
	TextField tf_name = new TextField(15);
	Button btn_name = new Button("저장");
	Register_Subject(ArrayList<Subject> allCourse, ArrayList<Subject> pastCourse, ArrayList<Subject> myCourse, Subject[][] timeTable){
		super("2018학년도 1학기 수강신청");
		class EventHandler implements ActionListener{
			public void actionPerformed(ActionEvent ae) {
				Object obj = ae.getSource();
				if(obj == mi_allSubject) {
					new ViewAllSubjectClass(allCourse);
				}else if(obj == mi_mySubject) {
					new MyTimeTable(timeTable);
				}else if(obj == mi_myPastCourse) {
					new ViewAllPastSubjectClass(pastCourse);
				}else if(obj == mi_changeSubject) {
					new Delete_Subject(myCourse, timeTable);
				}else if(obj == mi_logout) {
					new Logout(myCourse);
				}
				else if(obj == mi_save) {
					new Save(myCourse);
				}
			}
		}
		
		this.setMenuBar(menubar);
		m_subject = new Menu("과목");
		m_change = new Menu("변경");
		m_account = new Menu("계정");
		menubar.add(m_subject);
		menubar.add(m_change);
		menubar.add(m_account);
		mi_allSubject = new MenuItem("개설 교과목 정보");
		mi_mySubject = new MenuItem("내 시간표");
		mi_myPastCourse = new MenuItem("과거 수강 기록");
		mi_logout = new MenuItem("로그아웃");
		mi_save = new MenuItem("저장");
		mi_changeSubject = new MenuItem("수강신청 정정");
		m_subject.add(mi_allSubject);m_subject.add(mi_mySubject);m_subject.add(mi_myPastCourse);
		m_change.add(mi_changeSubject);
		m_account.add(mi_logout);
		m_account.add(mi_save);
		mi_allSubject.addActionListener(new EventHandler());
		mi_mySubject.addActionListener(new EventHandler());
		mi_changeSubject.addActionListener(new EventHandler());
		mi_myPastCourse.addActionListener(new EventHandler());
		mi_logout.addActionListener(new EventHandler());
		mi_save.addActionListener(new EventHandler());
		
		this.setBounds(200, 200, 1200, 200);
		this.setLayout(new FlowLayout());
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				setVisible(false);
				dispose();
			}
		});
		Panel p_name = new Panel();
		Label lb_name = new Label("과목명으로 수강신청");
		p_name.add(lb_name);
		p_name.add(tf_name);
		p_name.add(btn_name);
		
		Panel p_code = new Panel();
		Label lb_code = new Label("과목 코드로 수강신청");
		p_code.add(lb_code);
		for(int i=0; i<7; i++) {
			tf[i] = new TextField(10);
			p_code.add(tf[i]);
		}
		p_code.add(btn_code);
		this.add(p_name);this.add(p_code);
		
		btn_name.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Frame result = new Frame();
				result.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent we) {
						result.dispose();
						result.setVisible(false);
					}
				});
				result.setLayout(new FlowLayout());
				result.setBounds(200, 200, 500, 500);
				result.setVisible(true);
				Label lb = new Label();
				String subject = tf_name.getText();
				if(subject==null || subject.equals("") || subject.equals(" "));
				else {
					if(SubjectRegister.CheckPastCourse(subject, pastCourse)) {
						lb.setText(subject + " 는 과거에 수강한 과목이므로, 수강 신청 할 수 없습니다.");
						result.add(lb);
					}
					else if(SubjectRegister.CheckAllCourse(subject, allCourse)== false) {
						lb.setText(subject + " 은/는 이번 학기에 개설되지 않은 과목이므로, 수강 신청 할 수 없습니다.");
						result.add(lb);
					}
					else if(subject.isEmpty()) {
						lb.setText(subject + "빈 문자열이 입력되었습니다");
						result.add(lb);
					}
					else if(SubjectRegister.TimeTableCheck(subject, allCourse, timeTable) == false) {
						lb.setText(subject + " 은/는 다른 과목과 시간이 겹치므로, 수강 신청 할 수 없습니다.");
						result.add(lb);
					}
					else if(SubjectRegister.PrerequisiteCheck(subject, allCourse, pastCourse)==false) {
						lb.setText(subject + "  은/는 선수 과목을 수강신청하지 않아, 수강 신청 할 수 없습니다.");
						result.add(lb);
					}
					else { //수강 신청 성공한 경우
						Iterator<Subject> itr = allCourse.iterator();
						while(itr.hasNext()) {
							Subject inp = itr.next();
							if(inp.getName().equals(subject) || inp.getCode().equals(subject) ) {
								timeTable[inp.getSchedule()[0].getTime()][inp.getSchedule()[0].getDayInt()] = inp;
								timeTable[inp.getSchedule()[1].getTime()][inp.getSchedule()[1].getDayInt()] = inp;
								myCourse.add(inp);
								lb.setText(timeTable[inp.getSchedule()[1].getTime()][inp.getSchedule()[1].getDayInt()].getName() + " 과목을  수강신청 하였습니다.");
								result.add(lb);
								break;
							}
						}
					}
					tf_name.setText("");
				}
			}
		});
		btn_code.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int cnt=0;
				Label[] resultLabel = new Label[7];
				Frame resultFrame = new Frame("수강신청 결과");
				resultFrame.setLayout(new FlowLayout());
				resultFrame.setBounds(200, 200, 500, 500);
				resultFrame.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent we) {
						resultFrame.setVisible(false);
						resultFrame.dispose();
					}
				});
				resultFrame.setVisible(true);
				for(int i=0; i<7; i++) {
					String subject = tf[i].getText();
					if(subject==null || subject.equals("") || subject.equals(" "))	continue;
					else {
						if(SubjectRegister.CheckPastCourse(subject, pastCourse)) {
							
							resultLabel[cnt] = new Label(subject + " 는 과거에 수강한 과목이므로, 수강 신청 할 수 없습니다.", Label.LEFT);
							cnt++;
						}
						else if(SubjectRegister.CheckAllCourse(subject, allCourse)== false) {
							resultLabel[cnt] = new Label(subject + " 은/는 이번 학기에 개설되지 않은 과목이므로, 수강 신청 할 수 없습니다.", Label.LEFT);
							cnt++;
						}
						else if(subject.isEmpty()) {
							resultLabel[cnt] = new Label("빈 문자열이 입력되었습니다", Label.LEFT);
							cnt++;
						}
						else if(SubjectRegister.TimeTableCheck(subject, allCourse, timeTable) == false) {
							resultLabel[cnt] = new Label(subject + " 은/는 다른 과목과 시간이 겹치므로, 수강 신청 할 수 없습니다.", Label.LEFT);
							cnt++;
						}
						else if(SubjectRegister.PrerequisiteCheck(subject, allCourse, pastCourse)==false) {
							resultLabel[cnt] = new Label(subject + " 은/는 선수 과목을 수강신청하지 않아, 수강 신청 할 수 없습니다.", Label.LEFT);
							cnt++;
						}
						else { //수강 신청 성공한 경우
							Iterator<Subject> itr = allCourse.iterator();
							while(itr.hasNext()) {
								Subject inp = itr.next();
								if(inp.getName().equals(subject) || inp.getCode().equals(subject) ) {
									timeTable[inp.getSchedule()[0].getTime()][inp.getSchedule()[0].getDayInt()] = inp;
									timeTable[inp.getSchedule()[1].getTime()][inp.getSchedule()[1].getDayInt()] = inp;
									myCourse.add(inp);
									resultLabel[cnt] = new Label(timeTable[inp.getSchedule()[1].getTime()][inp.getSchedule()[1].getDayInt()].getName() + " 과목을  수강신청 하였습니다.", Label.LEFT);
									cnt++;
									break;
								}
							}
						}
						for(int k= cnt; k<7; k++) {
							resultLabel[k] = new Label();
						}
						for(int k=0; k< 7; k++) {
							resultFrame.add( resultLabel[k] );
							tf[k].setText("");
						}
					}
				}
			}
		});
	}
	class ViewAllSubjectClass extends Frame{
		ViewAllSubjectClass(ArrayList<Subject> allCourse){
			super("개설 교과목 정보");
			this.setBounds(200, 200, 1000, 800);
			this.setLayout(new GridLayout(12, 7));
			this.add(new Label("과목 코드"));
			this.add(new Label("과목명"));
			this.add(new Label("수업 시간 (1)"));
			this.add(new Label("시간 시간 (2)"));
			this.add(new Label("선수 과목"));
			this.add(new Label("교수진"));
			this.add(new Label("강의실"));
			Iterator<Subject> itr = allCourse.iterator();
			while(itr.hasNext()) {
				Subject sbj = itr.next();
				this.add(new Label(sbj.getCode()));
				this.add(new Label(sbj.getName()));
				this.add(new Label(sbj.getSchedule()[0].getDay()+"요일"+sbj.getSchedule()[0].getTime()+"교시 "));
				this.add(new Label(sbj.getSchedule()[1].getDay()+"요일"+sbj.getSchedule()[1].getTime()+"교시 "));
				this.add(new Label(sbj.getPreRequisite()));
				this.add(new Label(sbj.getProf()));
				this.add(new Label(sbj.getClassroom()));
			}
			this.setVisible(true);
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent we) {
					setVisible(false);
					dispose();
				}
			});
		}
	}
	class MyTimeTable extends Frame{
		Label lb[][] = new Label[9][8];
		MyTimeTable(Subject[][] timeTable){
			super("내 시간표");
			this.setLayout(new GridLayout(9, 8));
			this.setBounds(200, 200, 900, 700);
			this.add(new Label());
			this.add(new Label("월", Label.CENTER));this.add(new Label("화", Label.CENTER));this.add(new Label("수", Label.CENTER));this.add(new Label("목", Label.CENTER));this.add(new Label("금", Label.CENTER));this.add(new Label("토", Label.CENTER));this.add(new Label("일", Label.CENTER));
		
			for(int i=1; i<9; i++) {
				this.add(new Label(i+"교시"));
				for(int j=1; j<8; j++) {
					if(timeTable[i][j] == null) {
						lb[i][j] = new Label(" ", Label.CENTER);
						this.add(lb[i][j]);
					}
					else {
						lb[i][j] = new Label( timeTable[i][j].getName(), Label.CENTER );
						this.add(lb[i][j]);
					}
				}
			}
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent we) {
					setVisible(false);
					dispose();
				}
			});
			this.setVisible(true);
		}
	}
	
	class ViewAllPastSubjectClass extends Frame{
		ViewAllPastSubjectClass(ArrayList<Subject> pastCourse) {
			
			super("과거 수강내역 열람");
			this.setBounds(200, 200, 500, 500);
			this.setLayout(new GridLayout(11,4));
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent we) {
					ViewAllPastSubjectClass.this.setVisible(false);
					ViewAllPastSubjectClass.this.dispose();
				}
			});
			
			this.add(new Label("과목 코드"+"          "+"과목명"));
			this.add(new Label("과목 코드"+"          "+"과목명"));
			Iterator<Subject> itr = pastCourse.iterator();
			while(itr.hasNext()) {
				Subject sbj = itr.next();
				this.add(new Label(sbj.getCode()+"          " + sbj.getName()));
			}
			this.setVisible(true);	
			
		}
	}
	class Logout {
		
		Logout(ArrayList<Subject> myCourse){
			Button yes = new Button("예"); Button no = new Button("아니요");
			Frame confirm = new Frame();
			confirm.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent we) {
					confirm.dispose();
					confirm.setVisible(false);
				}
			});
			confirm.setBounds(200, 200, 500, 200);
			confirm.setLayout(new FlowLayout());
			Panel p1 = new Panel();
			Panel p2 = new Panel();
			Label l1;
			if(saveCheck==false) {
				l1 = new Label("수강 신청 내역이 저장되지 않았습니다. 그래도 로그아웃 하시겠습니까?");
			}else {
				l1 = new Label("로그아웃 하시겠습니까?");
			}
			p1.add(l1);
			p2.add(yes);p2.add(no);
			yes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					if(saveCheck) {
						Iterator<Subject> itr = myCourse.iterator();
						Frame f = new Frame();
						f.addWindowListener(new WindowAdapter() {
							public void windowClosing(WindowEvent we) {
								System.exit(0);
							}
						});
						f.setTitle("수강 신청 내역");
						int resid= 8;
						itr = myCourse.iterator();
						f.setBounds(200, 200, 1000, 1000);
						f.setLayout(new GridLayout(9, 7));
						f.add(new Label("과목 코드"));f.add(new Label("과목명"));f.add(new Label("수업 시간(1)"));f.add(new Label("수업 시간(2)"));f.add(new Label("선수 과목"));f.add(new Label("교수진"));f.add(new Label("강의실"));
						while(itr.hasNext()) {
							resid--;
							Subject sbj = itr.next();
							f.add(new Label(sbj.getCode()));
							f.add(new Label(sbj.getName()));
							f.add(new Label(sbj.getSchedule()[0].getDay()+" "+sbj.getSchedule()[0].getTime()));
							f.add(new Label(sbj.getSchedule()[1].getDay()+" "+sbj.getSchedule()[1].getTime()));
							f.add(new Label(sbj.getPreRequisite()));
							f.add(new Label(sbj.getProf()));
							f.add(new Label(sbj.getClassroom()));
						}
						for(int i=0; i< resid * 7; i++) {
							f.add(new Label(" "));
						}
						f.setVisible(true);
						saveCheck = true;
					}else {
					System.exit(0);
					}
					
				}
			});
			no.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					confirm.dispose();
					confirm.setVisible(false);
				}
			});
			confirm.add(p1);confirm.add(p2);
			confirm.setVisible(true);
		}
	}
	class Save{
		Save(ArrayList<Subject> myCourse){
			int total= 0;
			Iterator<Subject> itr = myCourse.iterator();
			while(itr.hasNext()) {
				Subject sbj = itr.next();
				total+= sbj.getHakjum();
			}
			Frame f = new Frame();
			
			
			f.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent we) {
					f.dispose();
					f.setVisible(false);
				}
			});
			if(total > 24) {
				f.setBounds(200, 200, 500, 200);
				f.setLayout(new FlowLayout());
				f.add( new Label("24학점 이상은 수강신청 할 수 없습니다."));
			}else if( total < 9 ) {
				f.setBounds(200, 200, 500, 200);
				f.setLayout(new FlowLayout());
				f.add(new Label("9학점 미만은 수강신청 할 수 없습니다."));
			}else {
				f.setTitle("수강 신청 내역");
				f.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent we) {
						f.dispose();
						f.setVisible(false);
					}
				});
				File file = new File("수강신청내역.txt");
				int resid= 8;
				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter(file));
					
					itr = myCourse.iterator();
					f.setBounds(200, 200, 1000, 1000);
					f.setLayout(new GridLayout(9, 7));
					f.add(new Label("과목 코드"));f.add(new Label("과목명"));f.add(new Label("수업 시간(1)"));f.add(new Label("수업 시간(2)"));f.add(new Label("선수 과목"));f.add(new Label("교수진"));f.add(new Label("강의실"));
					while(itr.hasNext()) {
						resid--;
						Subject sbj = itr.next();
						f.add(new Label(sbj.getCode()));
						f.add(new Label(sbj.getName()));
						f.add(new Label(sbj.getSchedule()[0].getDay()+" "+sbj.getSchedule()[0].getTime()));
						f.add(new Label(sbj.getSchedule()[1].getDay()+" "+sbj.getSchedule()[1].getTime()));
						f.add(new Label(sbj.getPreRequisite()));
						f.add(new Label(sbj.getProf()));
						f.add(new Label(sbj.getClassroom()));
						String str = sbj.getCode() + "  " + sbj.getName() + "  " + sbj.getSchedule()[0].getDay()+"요일 "+sbj.getSchedule()[0].getTime()+"교시  " +sbj.getSchedule()[1].getDay()+"요일 "+sbj.getSchedule()[1].getTime()+"교시  "+ "  " +sbj.getPreRequisite() + "  " + sbj.getProf() + "  " + sbj.getClassroom();
						bw.write(str);
						bw.newLine();
					}
					bw.close();
				}catch(IOException e) {
					System.out.println("IOException occured at SubjectRegister line 590");
				}
				for(int i=0; i< resid * 7; i++) {
					f.add(new Label(" "));
				}
				saveCheck = true;
			}
			f.setVisible(true);
		}
	}
}
