package subject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class SubjectInput extends Subject {
	public static void Input(ArrayList<Subject> allCourse) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("TotalSubject.txt"));
			int num = Integer.parseInt( br.readLine() );
			
			for(int i=0; i< num; i++) {
				String inp = br.readLine();
				StringTokenizer tk = new StringTokenizer(inp);
				int hakjum = Integer.parseInt(tk.nextToken());
				String code = tk.nextToken();
				String name = tk.nextToken();
				String day1 = tk.nextToken();
				int day1Int = ConvertDay(day1);
				int time1 =Integer.parseInt(tk.nextToken());
				String day2 = tk.nextToken();
				int day2Int = ConvertDay(day2);
				int time2 = Integer.parseInt(tk.nextToken());
				String preRequisite = tk.nextToken();
				Pair[] course = new Pair[2];
				course[0] = new Pair(day1, day1Int, time1);
				course[1] = new Pair(day2, day2Int, time2);
				String prof = tk.nextToken();
				String classroom = tk.nextToken();
				if(preRequisite.equals("x")) {
					allCourse.add(new Subject(hakjum, code, name, course, prof, classroom));
				}
				else
					allCourse.add(new Subject(hakjum, code, name, course, preRequisite, prof, classroom));
				
			} //���� �Է¹ޱ�
			br.close();
			
		}	catch(FileNotFoundException e) {
			System.out.println("TotalSubject.txt ������ �������� �ʽ��ϴ�.");
		}	catch(IOException e) {
			System.out.println("TotalSubject.txt �� �д� �������� ���� �߻�");
		}
	}
	public static void PastSubjectInput(ArrayList<Subject> PastCourse) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("PastSubject.txt"));
			int num = Integer.parseInt(br.readLine() );
			for(int i=0; i< num; i++) {
				String temp = br.readLine();
				StringTokenizer tk = new StringTokenizer(temp);
				String code = tk.nextToken();
				String name = tk.nextToken();		
				Subject inp = new Subject(code, name);
				PastCourse.add(inp);
			}		
			br.close();
		}catch(FileNotFoundException e) {
			System.out.println("PastSubject.txt ������ �������� �ʽ��ϴ�.");
		} catch(IOException e) {
			System.out.println("PastSubject.txt �� �д� �������� ���� �߻�");
		}
	}
	private static int ConvertDay(String day) {
		if(day.equals("��"))	return 1;
		if(day.equals("ȭ"))	return 2;
		if(day.equals("��"))	return 3;
		if(day.equals("��"))	return 4;
		if(day.equals("��"))	return 5;
		if(day.equals("��"))	return 6;
		if(day.equals("��"))	return 7;
		else {
			System.out.println("Error Occured in Class SubjectInput.ConverDay");
			return -1;
		}
	}
}