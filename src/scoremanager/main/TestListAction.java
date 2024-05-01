package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import tool.Action;

public class TestListAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");//ログインユーザー

		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		int year = todaysDate.getYear();// 現在の年を取得
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		SubjectDao subDao = new SubjectDao();   // 科目Daoを初期化

		//DBからデータ取得 3
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> cNumlist = cNumDao.filter(teacher.getSchool());
		//ログインユーザーの学校コードをもとに科目の一覧を取得
		List<Subject> sublist = subDao.filter(teacher.getSchool());

		// リストを初期化
		List<Integer> entYearSet = new ArrayList<>();
		// 10年前から1年後まで年をリストに追加
		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}

		//DBへデータ保存 5
		//なし
		//レスポンス値をセット 6
		// リクエストにデータをセット
		req.setAttribute("ent_year_set", entYearSet); //リクエストに10年前から1年後の入学年度をセット
		req.setAttribute("class_num_set", cNumlist);  //ログインしてる先生の学校のクラス番号をセット
		req.setAttribute("subject_list_set", sublist);//ログインしてる先生の学校の科目名をセット

		//JSPへフォワード 7
		req.getRequestDispatcher("test_list.jsp").forward(req, res);
	}

}
