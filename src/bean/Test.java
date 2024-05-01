package bean;

import javax.security.auth.Subject;

public class Test {

	/**
	 * 学生番号:Student
	 */
	private Student student;

	/**
	 * 科目:Subject
	 */
	private Subject subject;

	/**
	 * クラス番号:String
	 */
	private String classNum;

	/**
	 * 学校コード:School
	 */
	private School school;

	/**
	 * 学生番号:int
	 */
	private int no;

	/**
	 * スコア:int
	 */
	private int point;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public String getClassNum() {
		return classNum;
	}

	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public void setSubject(bean.Subject subject2) {
		// TODO 自動生成されたメソッド・スタブ

	}



}
