package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Student;
import bean.Test;
import dao.StudentDao;
import dao.TestDao;

public class TestRegistAction {

    /**
     * リクエストからテスト情報を登録するメインの実行メソッド。
     */
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // リクエストデータからTestオブジェクトを作成
        Test test = setRequestData(req);

        // データベースアクセスオブジェクトを生成
        TestDao testDao = new TestDao();

        // テスト情報をデータベースに保存
        @SuppressWarnings("unchecked")
		boolean isSaved = testDao.save((List<Test>) test);

        // 結果に基づいてユーザーにフィードバックを提供
        if (isSaved) {
            res.getWriter().write("Test registration successful!");
        } else {
            res.getWriter().write("Failed to register test.");
        }
    }

    /**
     * HTTPリクエストからTestオブジェクトを抽出し、設定します。
     */
    public Test setRequestData(HttpServletRequest req) throws Exception {
        Test test = new Test();
        Test subject = new Subject()

        // Student IDを取得し、Studentオブジェクトを取得する
        String studentId = req.getParameter("studentNo");
        StudentDao studentDao = new StudentDao();
        Student student = studentDao.get(studentId);

        if (student == null) {
            throw new Exception("Student not found with ID: " + studentId);
        }

        test.setStudent(student);

        // その他のテスト情報を設定
        test.setSubject(req.getParameter("subjectCd"));
        test.setSchool(req.getParameter("schoolCd"));
        test.setPoint(Integer.parseInt(req.getParameter("point")));
        test.setClassNum(req.getParameter("classNum"));

        return test;
    }
}
