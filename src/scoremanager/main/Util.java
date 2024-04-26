package scoremanager.main;

import javax.servlet.http.HttpServletRequest;

import bean.Teacher;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Util {

    // セッションから現在のTeacherオブジェクトを取得する（存在しない場合は新しく作成する）
    public static Teacher getUser(HttpServletRequest request) {
        Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
        if (teacher == null) {
            teacher = new Teacher();
            request.getSession().setAttribute("teacher", teacher);
        }
        return teacher;
    }

    // TeacherオブジェクトのclassNumSetプロパティを更新する
    public static void setClassNumSet(HttpServletRequest request) {
        Teacher teacher = getUser(request);
        String[] classNums = request.getParameterValues("classNumSet");
        if (classNums != null) {
            Set<Integer> classNumSet = new HashSet<>();
            for (String num : classNums) {
                classNumSet.add(Integer.parseInt(num));
            }
            teacher.setClassNumSet(classNumSet);
        }
    }

    // TeacherオブジェクトのentYearSetプロパティを更新する
    public static void setEntYearSet(HttpServletRequest request) {
        Teacher teacher = getUser(request);
        String[] entYears = request.getParameterValues("entYearSet");
        if (entYears != null) {
            Set<Integer> entYearSet = new HashSet<>();
            for (String year : entYears) {
                entYearSet.add(Integer.parseInt(year));
            }
            teacher.setEntYearSet(entYearSet);
        }
    }

    // Teacherオブジェクトのsubjectsプロパティを更新する
    public static void setSubjects(HttpServletRequest request) {
        Teacher teacher = getUser(request);
        String[] subjects = request.getParameterValues("subjects");
        if (subjects != null) {
            Set<String> subjectSet = new HashSet<>(Arrays.asList(subjects));
            teacher.setSubjects(subjectSet);
        }
    }

    // TeacherオブジェクトのnumSetプロパティを更新する
    public static void setNumSet(HttpServletRequest request) {
        Teacher teacher = getUser(request);
        String[] nums = request.getParameterValues("numSet");
        if (nums != null) {
            Set<Integer> numSet = new HashSet<>();
            for (String num : nums) {
                numSet.add(Integer.parseInt(num));
            }
            teacher.setClassNumSet(numSet);
        }
    }
}
