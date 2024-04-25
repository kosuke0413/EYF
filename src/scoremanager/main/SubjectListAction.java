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

public class SubjectListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");//ログインユーザー

		String nameStr="";// 科目名
		String cdStr="";
		List<Subject> subjects = null;// 科目リスト
		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		SubjectDao sDao = new SubjectDao();//学生Dao
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		Map<String, String> errors = new HashMap<>();// エラーメッセージ



		//DBからデータ取得 3
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> list = cNumDao.filter(teacher.getSchool());

		nameStr = req.getParameter("f1");
		cdStr = req.getParameter("f2");
		subjects = sDao.filter(teacher.getSchool());
//		if (cdStr==null) {
			// 指定なしの場合
			// 全学生情報を取得
//			subjects = sDao.filter(teacher.getSchool());
	//	}

		req.setAttribute("f2", cdStr);
		// リクエストにクラス番号をセット
		req.setAttribute("f1", nameStr);

		// リクエストに科目リストをセット
		req.setAttribute("subjects", subjects);


		req.getRequestDispatcher("subject_list.jsp").forward(req, res);
	}

}




