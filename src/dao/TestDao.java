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

    private static final String baseSql = "SELECT * FROM tests WHERE ";

    // getメソッド: 特定の学生、科目、学校、テスト番号を指定してテスト情報を取得
    public Test get(Student student, Subject subject, School school, int no) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        Test test = null;

        try {
            statement = connection.prepareStatement(baseSql + "student_no=? AND subject_id=? AND school_id=? AND test_no=?");
            statement.setString(1, student.getNo());
            statement.setString(2, subject.getCd());
            statement.setString(3, school.getCd());
            statement.setInt(4, no);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                test = new Test();
                test.setStudent(student);
                test.setSubject(subject);
                test.setSchool(school);
                test.setNo(rs.getInt("test_no"));
                test.setPoint(rs.getInt("point"));
                test.setClassNum(rs.getString("class_num"));
            }
        } catch (SQLException e) {
            throw new Exception("Failed to get test", e);
        } finally {
            close(statement);
            close(connection);
        }
        return test;
    }

    // filterメソッド: 特定の条件でテストをフィルタリングしてリストとして取得
    public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
        List<Test> tests = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        StudentDao studentDao = new StudentDao();

        try {
            String sql = baseSql + "ent_year=? AND class_num=? AND subject_id=? AND test_no=? AND school_id=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, entYear);
            statement.setString(2, classNum);
            statement.setString(3, subject.getCd());
            statement.setInt(4, num);
            statement.setString(5, school.getCd());

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Test test = new Test();
                Student student = studentDao.get(rs.getString("student_no"));
                test.setNo(rs.getInt("test_no"));
                test.setPoint(rs.getInt("point"));
                test.setStudent(student);
                test.setSubject(subject);
                test.setSchool(school);
                test.setClassNum(classNum);
                tests.add(test);
            }
        } catch (SQLException e) {
            throw new Exception("Failed to filter tests", e);
        } finally {
            close(statement);
            close(connection);
        }
        return tests;
    }

    // saveメソッド: テストインスタンスをデータベースに保存する（新規登録または更新）
    public boolean save(Test test, Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            if (test.getNo() == 0) {  // 新規登録の場合
                statement = connection.prepareStatement("INSERT INTO tests (student_no, subject_id, school_id, test_no, point, class_num) VALUES (?, ?, ?, ?, ?, ?)");
                statement.setString(1, test.getStudent().getNo());
                statement.setString(2, test.getSubject().getCd());
                statement.setString(3, test.getSchool().getCd());
                statement.setInt(4, test.getNo());
                statement.setInt(5, test.getPoint());
                statement.setString(6, test.getClassNum());
            } else {  // 更新の場合
                statement = connection.prepareStatement("UPDATE tests SET point = ?, class_num = ? WHERE test_no = ?");
                statement.setInt(1, test.getPoint());
                statement.setString(2, test.getClassNum());
                statement.setInt(3, test.getNo());
            }
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new Exception("Failed to save test", e);
        } finally {
            close(statement);
        }
    }

    // deleteメソッド: テストインスタンスをデータベースから削除
    public boolean delete(Test test, Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("DELETE FROM tests WHERE test_no = ?");
            statement.setInt(1, test.getNo());
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new Exception("Failed to delete test", e);
        } finally {
            close(statement);
        }
    }

    // リソースを安全にクローズするヘルパーメソッド
    private void close(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                // エラーログ出力または例外処理
            }
        }
    }
}
