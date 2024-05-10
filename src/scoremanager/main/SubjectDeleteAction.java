
package scoremanager.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		SubjectDao sDao = new SubjectDao();//科目Dao
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");// ログインユーザーを取得
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		Map<String, String> errors = new HashMap<>();//エラーメッセージ
		School school = teacher.getSchool();

		//リクエストパラメータ―の取得 2
		String cd = req.getParameter("cd");//科目コード

		//DBからデータ取得 3

//		Subject subject = sDao.get(cd,school);//科目コードから科目インスタンスを取得

		Subject subject = sDao.get(cd,teacher.getSchool());//科目コードから科目インスタンスを取得

		List<String> list = cNumDao.filter(teacher.getSchool());//ログインユーザーの学校コードをもとに科目の一覧を取得


		//ビジネスロジック 4
		//DBへデータ保存 5
		//レスポンス値をセット 6
		//条件で手順4~6の内容が分岐
		req.setAttribute("class_num_set", list);
		if (subject != null) {// 科目が存在していた場合
			req.setAttribute("cd", subject.getCd());


		} else {// 科目が存在していなかった場合
			errors.put("cd", "科目が存在していません");
			req.setAttribute("errors", errors);
		}
		//JSPへフォワード 7
		req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
	}
}
