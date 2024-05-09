package bean;
import java.io.Serializable;

public class Subject implements Serializable{

	//SBUJECTの中身を設定
	private School School;
	private String cd;
	private String name;
	private boolean isAttend;

	//ゲッターとセッタ＝
	public School getSchool() {
		return School;
	}
	public void setSchool(School school) {
		School = school;
	}
	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isAttend() {
		return isAttend;
	}
	public void setAttend(boolean isAttend) {
		this.isAttend = isAttend;
	}



}