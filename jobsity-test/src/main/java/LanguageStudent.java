import java.util.ArrayList;
import java.util.Collection;

public class LanguageStudent {

    private Collection<String> languages = new ArrayList<>();

    public Collection<String> getLanguages() {
        return languages;
    }

    public void addLanguage(String language) {
        languages.add(language);
    }

}
