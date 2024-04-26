package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao{
	private String baseSql;

	private List<TestListSubject>  postFilter(ResultSet rSet){
		//戻り値用のリスト
		List<TestListSubject> list = new ArrayList<>();
		try{
			while(rSet.next()) {
				//学生インスタンスを初期化
				TestListSubject testlistsubject = new TestListSubject();
				//学生インスタンスに検索結果をセット
				testlistsubject.setEntYear(rSet. getInt("ent_year"));
				testlistsubject.setStudentNo (rSet. getString("subject_cd"));
				testlistsubject.setStudentName (rSet. getString("student_name"));
				testlistsubject.setClassNum(rSet. getString("class_num"));

				//リストに追加
				list.add(testlistsubject);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return list;

	}

	public List<TestListSubject>  Filter(int entYear,String classNum,Subject subject,School school) throws Exception{
		//リストを初期化
	    List<TestListSubject> list = new ArrayList<>();
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
		    statement = connection. prepareStatement (baseSql);
		    //プリペアードステートメントに学校コードをバインド
		    statement. setInt(1, entYear);
		    statement. setString(2, classNum);
		    statement. setString(3,subject.getCd() );
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
