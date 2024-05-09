//新規登録画面２
package scoremanager.main;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション
		SubjectDao sDao = new SubjectDao();//学生Dao
		String cd = "";//科目コード
		String name = "";//科目名
		Subject subject = null;
		Map<String, String> errors = new HashMap<>();// エラーメッセージ
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		Teacher teacher = (Teacher) session.getAttribute("user");// ログインユーザーを取得
		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得


		//リクエストパラメータ―の取得 2
		cd = req.getParameter("cd");//学生番号
		name = req.getParameter("name");//氏名


		//DBからデータ取得 3
		subject = sDao.get(cd,teacher.getSchool());// 学生番号から学生インスタンスを取得
		List<String> list = cNumDao.filter(teacher.getSchool());// ログインユーザーの学校コードをもとにクラス番号の一覧を取得


		//ビジネスロジック 4
		//DBへデータ保存 5

		if (name==null) {// 科目名が選択されていない場合
			errors.put("name", "科目名を入力してください");
		}else{
			if (subject == null) {
				// 科目インスタンスを初期化
				subject = new Subject();
				// インスタンスに値をセット
				subject.setCd(cd);
				subject.setName(name);
				subject.setSchool(((Teacher)session.getAttribute("user")).getSchool());
				// 学生を保存
				sDao.save(subject);
			} else {			//入力された科目コードがDBに保存されていた場合
				errors.put("cd", "科目コードが重複しています");
			}
		}

		//エラーがあったかどうかで手順6~7の内容が分岐
		//レスポンス値をセット 6
		//JSPへフォワード 7
		req.setAttribute("f2", cd);
		// リクエストにクラス番号をセット
		req.setAttribute("f1", name);


		if(!errors.isEmpty()){
			// リクエスト属性をセット
			req.setAttribute("errors", errors);
			req.setAttribute("cd", cd);
			req.setAttribute("name", name);
			req.getRequestDispatcher("subject_create.jsp").forward(req, res);
			return;
		}

		//飛べ！
		req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
	}
}
