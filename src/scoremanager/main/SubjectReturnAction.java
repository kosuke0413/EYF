//削除した科目一覧画面

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

public class SubjectReturnAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");//ログインユーザー

		String nameStr="";// 科目名
		String cdStr="";  //科目コード
		String isAttendStr="";//入力された在学フラグ
		boolean isAttend = true;// 在学フラグ
		List<Subject> subjects = null;// 科目リスト→JSPにあげるやつ
		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		SubjectDao sDao = new SubjectDao();//科目bDao
		ClassNumDao cNumDao = new ClassNumDao();// クラスコードDaoを初期化
		Map<String, String> errors = new HashMap<>();// エラーメッセージ

		//DBからデータ取得 3
		// ログインユーザーの学校コードをもとに学校ごとの科目の一覧を取得
		List<String> list = cNumDao.filter(teacher.getSchool());

		nameStr = req.getParameter("f1");
		cdStr = req.getParameter("f2");
		isAttendStr = req.getParameter("f3");
		//ISATTENDがTRUEのものだけ表示する
		if (isAttendStr != null) {
			// 在学フラグを立てる
			isAttend = true;
		}
		subjects = sDao.filter_return(teacher.getSchool(),isAttend);  //学校別の科目


		req.setAttribute("f2", cdStr);
		// リクエストに科目番号をセット
		req.setAttribute("f1", nameStr);

		// リクエストに科目リストをセット
		req.setAttribute("subjects", subjects);


		req.getRequestDispatcher("subject_return.jsp").forward(req, res);

		session.removeAttribute("subjectdelet");
	}

}





