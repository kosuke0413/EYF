
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

public class SubjectUpdateExecuteAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		SubjectDao sDao = new SubjectDao();// 科目Dao
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");// ログインユーザーを取得
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		Map<String, String> errors = new HashMap<>();//エラーメッセージ
		School school = teacher.getSchool();

		//リクエストパラメータ―の取得 2
		String cd = req.getParameter("cd");
		String name = req.getParameter("name");

		//DBからデータ取得 3
		Subject subject = sDao.get(cd,teacher.getSchool());// 科目コードから科目インスタンスを取得

		List<String> list = cNumDao.filter(teacher.getSchool());//ログインユーザーの学校コードをもとにクラス番号の一覧を取得

		//ビジネスロジック 4
		//DBへデータ保存 5
		//条件で4～5が分岐
		System.out.println("ExecuteのCD"+cd);
		if (subject != null) {
			// 科目が存在していた場合
			// インスタンスに値をセット
			subject.setCd(cd);
			subject.setName(name);

			sDao.save(subject);
		} else {
			errors.put("cd", "学生が存在していません");
		}

		//エラーがあったかどうかで手順6~7の内容が分岐
		//レスポンス値をセット 6
		//JSPへフォワード 7

		if(!errors.isEmpty()){//エラーがあった場合、更新画面へ戻る
			// リクエスト属性をセット
			req.setAttribute("errors", errors);
			req.setAttribute("cd", cd);
			req.setAttribute("name", name);
			req.getRequestDispatcher("subject_update.jsp").forward(req, res);
			return;
		}

		req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
	}
}
