package subject;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;

interface Registerable{
	String name="default", code="default";
	String getName();
	String getCode();
}

public class Subject extends Pair implements Registerable{
	int hakjum;
	String name= "          ", code;
	Pair[] time;
	String preRequisite;
	String prof, classroom;
	Subject(){}
	Subject(String code, String name){
		this.code = code; this.name = name;
	}
	Subject(int hakjum, String code, String name, Pair time[], String prof, String classroom){
		this.hakjum = hakjum; this.time= time;	this.name=name; this.code=code; this.prof = prof; this.classroom=classroom;
	}
	Subject(int hakjum, String code, String name, Pair time[], String preRequisite, String prof, String classroom){
		this.hakjum= hakjum; this.time= time;	this.name=name; this.code=code; this.preRequisite = preRequisite;this.prof = prof; this.classroom=classroom;
	}
	public String getClassroom() {
		return classroom;
	}
	public String getProf() {
		return prof;
	}
	public int getHakjum() {
		return hakjum;
	}
	public String getName() {
		return name;
	}
	public String getCode() {
		return code;
	}
	public Pair[] getSchedule() {
		return time;
	}
	public String getPreRequisite() {
		return preRequisite;
	}
	public boolean equals(Subject s) {
		if(s.name == name || s.code == code)	return true;
		else return false;
	}
	public String toString(Subject s) {
		return s.getName();
	}
}

class ViewAllPastSubjectClass extends Frame{
	ViewAllPastSubjectClass(ArrayList<Subject> pastCourse) {
		
		super("과거 수강내역 열람");
		this.setBounds(200, 200, 700, 500);
		this.setLayout(new GridLayout(11,2));
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				ViewAllPastSubjectClass.this.setVisible(false);
				ViewAllPastSubjectClass.this.dispose();
			}
		});
		
		this.add(new Label("과목 코드"+"          "+"과목명"));
		Iterator<Subject> itr = pastCourse.iterator();
		while(itr.hasNext()) {
			Subject sbj = itr.next();
			this.add(new Label(sbj.getCode()+"          " + sbj.getName()));
		}
		this.setVisible(true);	
		
	}
}