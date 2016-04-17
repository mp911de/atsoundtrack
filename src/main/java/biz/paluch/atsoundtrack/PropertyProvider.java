package biz.paluch.atsoundtrack;

import java.util.Properties;

import biz.paluch.atsoundtrack.settings.AtSoundtrackSettings;

import com.intellij.ide.fileTemplates.DefaultTemplatePropertiesProvider;
import com.intellij.psi.PsiDirectory;

/**
 * Property provider for file templates.
 * 
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 * @since 13.05.15 10:01
 */
public class PropertyProvider implements DefaultTemplatePropertiesProvider {

    private AtSoundtrackSettings settings = AtSoundtrackSettings.getInstance();

    @Override
    public void fillProperties(PsiDirectory psiDirectory, Properties properties) {

        String name = Renderer.render(AtSoundtrack.getSoundtrack(), settings);
        if (!name.trim().isEmpty()) {
            properties.put("soundtrack", name);
        } else {
            properties.put("soundtrack", "");
        }
    }
}
