package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao{

	//ログインした学校別で値を取得するSQL文の元
	private String baseSql = "select * from subject where school_cd=? ";

//ゲット
	public Subject get(String cd)throws Exception{

		Subject subject=new Subject();
		Connection connection=getConnection();
		PreparedStatement statement=null;

		try{   //科目コード別に作成
			statement=connection.prepareStatement("select * from subject where cd=?");
			statement.setString(1, cd);

			//ResultSet=「取得した値（selectによる検索値の獲得）
			ResultSet rSet=statement.executeQuery();

			SchoolDao schoolDao=new SchoolDao();

			if (rSet.next()) {
				subject.setCd(rSet.getString("cd"));
				subject.setName(rSet.getString("name"));
				//科目フィールドには学校コードで検索した学校インスタンスをセット
				subject.setSchool(schoolDao.get(rSet.getString("school_cd")));
				subject.setAttend(rSet.getBoolean("is_attend"));

			} else {
				//リザルトセットが存在しない場合
				//科目インスタンスにnullをセット
				subject= null;
			} }catch (Exception e) {
				throw e;
			} finally {
				if(statement != null) {
					try {
						statement.close();
					} catch (SQLException sqle) {
						throw sqle;
					}
				}
			}
		return subject;
	}


	//filterを作るために作ったようわからん奴
	public List<Subject> postFilter(ResultSet rSet,School school) throws Exception {
		//戻り値用のリスト
		List<Subject> list = new ArrayList<>();
		try{
			while(rSet.next()) {
				//科目インスタンスを初期化
				Subject subject = new Subject();
				//科目インスタンスに検索結果をセット
				subject.setCd(rSet. getString("cd"));
				subject.setName (rSet. getString("name"));
				subject.setSchool (school);
				subject.setAttend(rSet. getBoolean("is_attend"));
				//リストに追加
				list.add(subject);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return list;
	}
	//フィルター（出力）
	public List<Subject> filter(School school,boolean isAttend)throws Exception {
		//リストを初期化
	    List<Subject> list = new ArrayList<>();
	    //コネクションを確立
	    Connection connection = getConnection();
	    //プリペアードステートメント
	    PreparedStatement statement = null;
	    //リザルトセット
	    ResultSet rSet = null;

	    String conditionIsAttend = "";
	    //在学フラグがtrueの場合
	    if (isAttend) {
	    conditionIsAttend = "and is_attend=true";
	}
	    try {
		    //プリペアードステートメントにSQL文をセット
		    statement = connection. prepareStatement (baseSql+ conditionIsAttend);

		    //プリペアードステートメントに学校コードをバインド
		    statement. setString(1, school. getCd ());
		    // プライベートステートメントを実行
		    rSet = statement.executeQuery ();
		    //帰ってきたResultSet型をStudent型に変えて結果をセットする
		    list = postFilter(rSet,school);
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




	//セーブ（更新＆作成用）
	public boolean save(Subject subject) throws Exception {
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//実行件数
		int count = 0;

		try{
			//データベースから科目を取得
			Subject old = get(subject.getCd());
			if (old == null) {
				//科目（CD)が存在しなかった場合
				//プリペアードステートメンにINSERT文をセット
				statement = connection.prepareStatement(
						"insert into subject (school_cd, cd, name,) values(?, ?, ?,) ");
				//プリペアードステートメントに値をバインド
				statement.setString(1, subject.getSchool().getCd());
				statement.setString(2,subject.getCd());
				statement.setString(3,subject.getName());
			} else {
				//学生が存在した場合
				//プリペアードステートメントにUPDATE文をセット
				statement = connection
						.prepareStatement("update subject set name=? where cd=?");
				//プリペアードステートメントに値をバインド
				statement.setString(2,subject.getCd());
				statement.setString(1,subject.getName());
			}

			//プリペアードステートメントを実行
			count = statement.executeUpdate();

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

		if (count > 0) {
			//実行件数が1以上ある場合
			return true;
		} else {
			//実行件数が0件の場合
			return false;
		}

}
	public boolean delete(Subject subject) throws Exception {

		//コネクションを確立
				Connection connection = getConnection();
				//プリペアードステートメント
				PreparedStatement statement = null;
				//実行できた件数
				int count = 0;

				try{   //科目コード別に作成
					//CDをもとにそのデータのすべてを取得
					Subject old = get(subject.getCd());

					//自分のログインしている学校の指定したCDのものを更新
					//trueをfalseにすることで消してはいないが表示しないことにする
					statement=connection.prepareStatement("update subject set is_attend=false where cd=? and school_cd=?");
					statement.setString(1, subject.getCd());
					statement. setString(2, subject.getSchool().getCd());
					count = statement.executeUpdate();
				}catch(Exception e){
					throw e;
				}finally{
					if(statement !=null){
						try{
							connection.close();
						}catch(SQLException sqle){
							throw sqle;
						}
					}
				}
				//実行結果が一つ以上なら成功！
				if(count > 0){
					return true;
				}else{
					return false;
				}




	}}
