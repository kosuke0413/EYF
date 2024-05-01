package scoremanager.main;

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
public class SubjectDeleteExecuteAction extends Action {
public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
	//ローカル変数の宣言 1
	SubjectDao sDao = new SubjectDao();// 科目Dao
	HttpSession session = req.getSession();//セッション
	Teacher teacher = (Teacher)session.getAttribute("user");// ログインユーザーを取得
	ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
	Map<String, String> errors = new HashMap<>();//エラーメッセージ

	//リクエストパラメータ―の取得 2
	String cd = req.getParameter("cd");
	//String name = req.getParameter("name");

	//DBからデータ取得 3
	Subject subject = sDao.get(cd);// 科目コードから科目インスタンスを取得
	@SuppressWarnings("unchecked")
	List<Subject> subjectdelet=(List<Subject>)session.getAttribute("subjectdelet");

	for (Subject i : subjectdelet) {
			if (((Subject) i).getCd()==cd) {
				subjectdelet.remove(i);
				break;
			}
		}

	req.getRequestDispatcher("subject_delete_done.jsp").forward(req, res);
	}
}
