package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao{

	//Filterで入手したDBのデータをTestListStudentに変換して返す
	private List<TestListStudent>  postFilter(ResultSet rSet){
		//戻り値用のリスト
		List<TestListStudent> list = new ArrayList<>();
		try{
			while(rSet.next()) {
				//学生インスタンスを初期化
				TestListStudent testliststudent = new TestListStudent();
				//学生インスタンスに検索結果をセット
				testliststudent.setSubjectName(rSet. getString("name"));
				testliststudent.setSubjectCd (rSet. getString("subject_cd"));
				testliststudent.setNum (rSet. getInt("no"));
				testliststudent.setPoint(rSet. getInt("point"));

				//リストに追加
				list.add(testliststudent);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return list;

	}

	public List<TestListStudent>  Filter(Student student) throws Exception{
		//リストを初期化
	    List<TestListStudent> list = new ArrayList<>();
	    //コネクションを確立
	    Connection connection = getConnection();
	    //プリペアードステートメント
	    PreparedStatement statement = null;
	    //リザルトセット
	    ResultSet rSet = null;
	    //SQL文のソート
	    //String order = " order by no asc";

	    try {
		    //プリペアードステートメントにSQL文をセット
		    statement = connection. prepareStatement ("SELECT DISTINCT s.NAME,t.SUBJECT_CD,t.NO,t.POINT FROM SUBJECT s JOIN TEST t ON s.CD = t.SUBJECT_CD WHERE STUDENT_NO = ? order by t.no asc");
		    //プリペアードステートメントに学校コードをバインド
		    statement. setString(1, student. getNo ());
		    // プライベートステートメントを実行
		    rSet = statement.executeQuery ();
		    list = postFilter(rSet);
		} catch (Exception e) {
			throw e;
		} finally {
			//
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}

			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		//listを返す
		return list;
	}
}
