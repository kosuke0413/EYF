package scoremanager.main;

import java.util.Map;

public class TestListSubject {
	private int entYear;
	private String studentNo;
	private String studentName;
	private String classNum;
	private Map<Integer,Integer> points;

	public TestListSubject(int entYear, String studentNo, String studentName, String classNum,
			Map<Integer, Integer> points) {
		this.entYear = entYear;
		this.studentNo = studentNo;
		this.studentName = studentName;
		this.classNum = classNum;
		this.points = points;
	}

	public int getEntYear() {
		return entYear;
	}

	public void setEntYear(int entYear) {
		this.entYear = entYear;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getClassNum() {
		return classNum;
	}

	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	public Map<Integer, Integer> getPoints() {
		return points;
	}

	public void setPoints(Map<Integer, Integer> points) {
		this.points = points;
	}

	public String getPoint(int key) {
		return point;
	}

	public void setPoint(int key,int value) {
		this.points = point;
	}




}
