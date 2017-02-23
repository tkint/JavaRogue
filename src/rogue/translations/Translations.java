/* 
 * Everyone is authorized to read, copy and share the code below
 * if the authors are credited
 */
package rogue.translations;

import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * @author Thomas
 */
public interface Translations {

    final ResourceBundle CREDITS = ResourceBundle.getBundle("rogue.translations.credits.credits");
    final ResourceBundle WORDS = ResourceBundle.getBundle("rogue.translations.words.words");

    public static String[] getTranslations(ResourceBundle resourceBundle, String keyPrefix) {
        String[] result;

        // Placement des clefs dans l'ordre établi
        List<String> list = Collections.list(resourceBundle.getKeys());
        Collections.sort(list);

        result = new String[list.size()];
        
        for (int i = 0; i < list.size(); i++) {
            result[i] = resourceBundle.getString((String) list.get(i));
        }

        return result;
    }
}
