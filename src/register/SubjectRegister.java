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
		return check;	//true�� ��ȯ�ϸ� ���� ������ ���̴�!
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
		
		else return false;	//true �� ��ȯ�Ǹ� ���� ��û ���� !
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
		return check; // true�� ��ȯ�Ǹ� ���ſ� ������ ������ �ִ� ��
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
		return check; // true�� ��ȯ�Ǹ� ���� ������ ������ �ִ� ����
	}
}

class Delete_Subject extends Frame{
	Button btn[] = new Button[7];
	Label name[] = new Label[7];
	Label code[] = new Label[7];
	Label time1[] = new Label[7];
	Label time2[] = new Label[7];
	
	Delete_Subject(ArrayList<Subject> myCourse, Subject[][] timeTable){
		super("������û ����");
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
			Label temp = new Label("���� ��û ������ �����ϴ�");
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
					time1[i] = new Label( myCourse.get(i).getSchedule()[0].getDay() +"  " +  myCourse.get(i).getSchedule()[0].getTime() + " ����");
					time2[i]= new Label( myCourse.get(i).getSchedule()[1].getDay() +"  " +  myCourse.get(i).getSchedule()[1].getTime() + " ����");
					btn[i] = new Button("����");
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
							notify.add(new Label("���� ��û�� ��ҵǾ����ϴ�."));
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
	Button btn_code = new Button("����");
	TextField tf_name = new TextField(15);
	Button btn_name = new Button("����");
	Register_Subject(ArrayList<Subject> allCourse, ArrayList<Subject> pastCourse, ArrayList<Subject> myCourse, Subject[][] timeTable){
		super("2018�г⵵ 1�б� ������û");
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
		m_subject = new Menu("����");
		m_change = new Menu("����");
		m_account = new Menu("����");
		menubar.add(m_subject);
		menubar.add(m_change);
		menubar.add(m_account);
		mi_allSubject = new MenuItem("���� ������ ����");
		mi_mySubject = new MenuItem("�� �ð�ǥ");
		mi_myPastCourse = new MenuItem("���� ���� ���");
		mi_logout = new MenuItem("�α׾ƿ�");
		mi_save = new MenuItem("����");
		mi_changeSubject = new MenuItem("������û ����");
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
		Label lb_name = new Label("��������� ������û");
		p_name.add(lb_name);
		p_name.add(tf_name);
		p_name.add(btn_name);
		
		Panel p_code = new Panel();
		Label lb_code = new Label("���� �ڵ�� ������û");
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
						lb.setText(subject + " �� ���ſ� ������ �����̹Ƿ�, ���� ��û �� �� �����ϴ�.");
						result.add(lb);
					}
					else if(SubjectRegister.CheckAllCourse(subject, allCourse)== false) {
						lb.setText(subject + " ��/�� �̹� �б⿡ �������� ���� �����̹Ƿ�, ���� ��û �� �� �����ϴ�.");
						result.add(lb);
					}
					else if(subject.isEmpty()) {
						lb.setText(subject + "�� ���ڿ��� �ԷµǾ����ϴ�");
						result.add(lb);
					}
					else if(SubjectRegister.TimeTableCheck(subject, allCourse, timeTable) == false) {
						lb.setText(subject + " ��/�� �ٸ� ����� �ð��� ��ġ�Ƿ�, ���� ��û �� �� �����ϴ�.");
						result.add(lb);
					}
					else if(SubjectRegister.PrerequisiteCheck(subject, allCourse, pastCourse)==false) {
						lb.setText(subject + "  ��/�� ���� ������ ������û���� �ʾ�, ���� ��û �� �� �����ϴ�.");
						result.add(lb);
					}
					else { //���� ��û ������ ���
						Iterator<Subject> itr = allCourse.iterator();
						while(itr.hasNext()) {
							Subject inp = itr.next();
							if(inp.getName().equals(subject) || inp.getCode().equals(subject) ) {
								timeTable[inp.getSchedule()[0].getTime()][inp.getSchedule()[0].getDayInt()] = inp;
								timeTable[inp.getSchedule()[1].getTime()][inp.getSchedule()[1].getDayInt()] = inp;
								myCourse.add(inp);
								lb.setText(timeTable[inp.getSchedule()[1].getTime()][inp.getSchedule()[1].getDayInt()].getName() + " ������  ������û �Ͽ����ϴ�.");
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
				Frame resultFrame = new Frame("������û ���");
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
							
							resultLabel[cnt] = new Label(subject + " �� ���ſ� ������ �����̹Ƿ�, ���� ��û �� �� �����ϴ�.", Label.LEFT);
							cnt++;
						}
						else if(SubjectRegister.CheckAllCourse(subject, allCourse)== false) {
							resultLabel[cnt] = new Label(subject + " ��/�� �̹� �б⿡ �������� ���� �����̹Ƿ�, ���� ��û �� �� �����ϴ�.", Label.LEFT);
							cnt++;
						}
						else if(subject.isEmpty()) {
							resultLabel[cnt] = new Label("�� ���ڿ��� �ԷµǾ����ϴ�", Label.LEFT);
							cnt++;
						}
						else if(SubjectRegister.TimeTableCheck(subject, allCourse, timeTable) == false) {
							resultLabel[cnt] = new Label(subject + " ��/�� �ٸ� ����� �ð��� ��ġ�Ƿ�, ���� ��û �� �� �����ϴ�.", Label.LEFT);
							cnt++;
						}
						else if(SubjectRegister.PrerequisiteCheck(subject, allCourse, pastCourse)==false) {
							resultLabel[cnt] = new Label(subject + " ��/�� ���� ������ ������û���� �ʾ�, ���� ��û �� �� �����ϴ�.", Label.LEFT);
							cnt++;
						}
						else { //���� ��û ������ ���
							Iterator<Subject> itr = allCourse.iterator();
							while(itr.hasNext()) {
								Subject inp = itr.next();
								if(inp.getName().equals(subject) || inp.getCode().equals(subject) ) {
									timeTable[inp.getSchedule()[0].getTime()][inp.getSchedule()[0].getDayInt()] = inp;
									timeTable[inp.getSchedule()[1].getTime()][inp.getSchedule()[1].getDayInt()] = inp;
									myCourse.add(inp);
									resultLabel[cnt] = new Label(timeTable[inp.getSchedule()[1].getTime()][inp.getSchedule()[1].getDayInt()].getName() + " ������  ������û �Ͽ����ϴ�.", Label.LEFT);
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
			super("���� ������ ����");
			this.setBounds(200, 200, 1000, 800);
			this.setLayout(new GridLayout(12, 7));
			this.add(new Label("���� �ڵ�"));
			this.add(new Label("�����"));
			this.add(new Label("���� �ð� (1)"));
			this.add(new Label("�ð� �ð� (2)"));
			this.add(new Label("���� ����"));
			this.add(new Label("������"));
			this.add(new Label("���ǽ�"));
			Iterator<Subject> itr = allCourse.iterator();
			while(itr.hasNext()) {
				Subject sbj = itr.next();
				this.add(new Label(sbj.getCode()));
				this.add(new Label(sbj.getName()));
				this.add(new Label(sbj.getSchedule()[0].getDay()+"����"+sbj.getSchedule()[0].getTime()+"���� "));
				this.add(new Label(sbj.getSchedule()[1].getDay()+"����"+sbj.getSchedule()[1].getTime()+"���� "));
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
			super("�� �ð�ǥ");
			this.setLayout(new GridLayout(9, 8));
			this.setBounds(200, 200, 900, 700);
			this.add(new Label());
			this.add(new Label("��", Label.CENTER));this.add(new Label("ȭ", Label.CENTER));this.add(new Label("��", Label.CENTER));this.add(new Label("��", Label.CENTER));this.add(new Label("��", Label.CENTER));this.add(new Label("��", Label.CENTER));this.add(new Label("��", Label.CENTER));
		
			for(int i=1; i<9; i++) {
				this.add(new Label(i+"����"));
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
			
			super("���� �������� ����");
			this.setBounds(200, 200, 500, 500);
			this.setLayout(new GridLayout(11,4));
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent we) {
					ViewAllPastSubjectClass.this.setVisible(false);
					ViewAllPastSubjectClass.this.dispose();
				}
			});
			
			this.add(new Label("���� �ڵ�"+"          "+"�����"));
			this.add(new Label("���� �ڵ�"+"          "+"�����"));
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
			Button yes = new Button("��"); Button no = new Button("�ƴϿ�");
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
				l1 = new Label("���� ��û ������ ������� �ʾҽ��ϴ�. �׷��� �α׾ƿ� �Ͻðڽ��ϱ�?");
			}else {
				l1 = new Label("�α׾ƿ� �Ͻðڽ��ϱ�?");
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
						f.setTitle("���� ��û ����");
						int resid= 8;
						itr = myCourse.iterator();
						f.setBounds(200, 200, 1000, 1000);
						f.setLayout(new GridLayout(9, 7));
						f.add(new Label("���� �ڵ�"));f.add(new Label("�����"));f.add(new Label("���� �ð�(1)"));f.add(new Label("���� �ð�(2)"));f.add(new Label("���� ����"));f.add(new Label("������"));f.add(new Label("���ǽ�"));
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
				f.add( new Label("24���� �̻��� ������û �� �� �����ϴ�."));
			}else if( total < 9 ) {
				f.setBounds(200, 200, 500, 200);
				f.setLayout(new FlowLayout());
				f.add(new Label("9���� �̸��� ������û �� �� �����ϴ�."));
			}else {
				f.setTitle("���� ��û ����");
				f.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent we) {
						f.dispose();
						f.setVisible(false);
					}
				});
				File file = new File("������û����.txt");
				int resid= 8;
				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter(file));
					
					itr = myCourse.iterator();
					f.setBounds(200, 200, 1000, 1000);
					f.setLayout(new GridLayout(9, 7));
					f.add(new Label("���� �ڵ�"));f.add(new Label("�����"));f.add(new Label("���� �ð�(1)"));f.add(new Label("���� �ð�(2)"));f.add(new Label("���� ����"));f.add(new Label("������"));f.add(new Label("���ǽ�"));
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
						String str = sbj.getCode() + "  " + sbj.getName() + "  " + sbj.getSchedule()[0].getDay()+"���� "+sbj.getSchedule()[0].getTime()+"����  " +sbj.getSchedule()[1].getDay()+"���� "+sbj.getSchedule()[1].getTime()+"����  "+ "  " +sbj.getPreRequisite() + "  " + sbj.getProf() + "  " + sbj.getClassroom();
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
