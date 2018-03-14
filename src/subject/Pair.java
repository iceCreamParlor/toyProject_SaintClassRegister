package subject;

public class Pair{
	int time, dayInt;
	String day;
	Pair(){}
	Pair(String day, int dayInt, int time){this.day=day; this.time = time; this.dayInt= dayInt; }
	public int getTime() {
		return time;
	}
	public int getDayInt() {
		return dayInt;
	}
	public String getDay() {
		return day;
	}
}