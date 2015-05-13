package biz.paluch.atsoundtrack;

import java.util.Properties;

import com.intellij.ide.fileTemplates.DefaultTemplatePropertiesProvider;
import com.intellij.psi.PsiDirectory;

/**
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 * @since 13.05.15 10:01
 */
public class PropertyProvider implements DefaultTemplatePropertiesProvider {

    @Override
    public void fillProperties(PsiDirectory psiDirectory, Properties properties) {
        String name = AtSoundtrack.getName();
        if (name != null) {
            properties.put("soundtrack", name);
        } else {
            properties.put("soundtrack", "");
        }
    }

}
