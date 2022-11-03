import java.util.ArrayList;
import java.util.Collection;

public class LanguageTeacher extends LanguageStudent {

    public boolean teach(LanguageStudent student, String language) {
        if (getLanguages().contains(language)) {
            student.addLanguage(language);
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
      LanguageTeacher teacher = new LanguageTeacher();
      teacher.addLanguage("English2");

      LanguageStudent student = new LanguageStudent();
      teacher.teach(student, "English");

      for(String language : student.getLanguages())
          System.out.println(language);
    }

}
