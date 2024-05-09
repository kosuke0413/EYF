package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

    // SQLクエリのベース部分
    private static final String BASE_SQL = "SELECT t.student_no, t.subject_cd, t.school_cd, t.no, t.point, t.class_num, " +
                                           "s.name AS student_name, sub.name AS subject_name, sch.name AS school_name " +
                                           "FROM test t " +
                                           "JOIN student s ON t.student_no = s.no " +
                                           "JOIN subject sub ON t.subject_cd = sub.cd AND t.school_cd = sub.school_cd " +
                                           "JOIN school sch ON t.school_cd = sch.cd WHERE ";

    /**
     * 指定された学生、科目、学校、テスト番号でテスト情報を取得します。
     * @throws Exception
     */
    public Test get(Student student, Subject subject, School school, int no) throws Exception {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(BASE_SQL +
                "t.student_no = ? AND t.subject_cd = ? AND t.school_cd = ? AND t.no = ?")) {
            statement.setString(1, student.getNo());
            statement.setString(2, subject.getCd());
            statement.setString(3, school.getCd());
            statement.setInt(4, no);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapToTest(rs);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve test.", e);
        }
        return null;
    }

    /**
     * ResultSetからTestオブジェクトリストを生成します。
     */
    private List<Test> postFilter(ResultSet rs) throws SQLException {
        List<Test> tests = new ArrayList<>();
        while (rs.next()) {
            tests.add(mapToTest(rs));
        }
        return tests;
    }

    /**
     * ResultSetからTestオブジェクトをマッピングします。
     */
    private Test mapToTest(ResultSet rs) throws SQLException {
        Test test = new Test();
        test.setNo(rs.getInt("no"));
        test.setPoint(rs.getInt("point"));
        test.setClassNum(rs.getString("class_num"));
        test.setStudent(new Student());
        test.setSubject(new Subject());
        test.setSchool(new School());
        return test;
    }

    /**
     * 複数の条件でテスト情報をフィルタリングしてリストとして取得します。
     * @throws Exception
     */
    public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
        List<Test> tests;
        String sql = BASE_SQL + "s.ent_year = ? AND t.class_num = ? AND t.subject_cd = ? AND t.test_no = ? AND t.school_cd = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entYear);
            statement.setString(2, classNum);
            statement.setString(3, subject.getCd());
            statement.setInt(4, num);
            statement.setString(5, school.getCd());

            try (ResultSet rs = statement.executeQuery()) {
                tests = postFilter(rs);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to filter tests", e);
        }
        return tests;
    }

    /**
     * テストのリストをデータベースに保存します。
     * @throws Exception
     */
    public boolean save(List<Test> tests) throws Exception {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (Test test : tests) {
                    save(test, connection); // 個別のsaveメソッドを使用
                }
                connection.commit();
                return true;
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException("Failed to save tests", e);
            }
        }
    }

    private void save(Test test, Connection connection) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/**
     * テストのリストをデータベースから削除します。
	 * @throws Exception
     */
    public boolean delete(List<Test> tests) throws Exception {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (Test test : tests) {
                    delete(test, connection); // 個別のdeleteメソッドを使用
                }
                connection.commit();
                return true;
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException("Failed to delete tests", e);
            }
        }
    }

	private void delete(Test test, Connection connection) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
