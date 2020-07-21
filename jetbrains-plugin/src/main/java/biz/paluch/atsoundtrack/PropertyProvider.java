/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package biz.paluch.atsoundtrack;

import biz.paluch.atsoundtrack.settings.AtSoundtrackSettings;
import biz.paluch.atsoundtrack.settings.PluginSettings;
import com.intellij.ide.fileTemplates.DefaultTemplatePropertiesProvider;
import com.intellij.psi.PsiDirectory;

import java.util.Properties;

/**
 * Property provider for file templates.
 *
 * @author Mark Paluch
 * @since 13.05.15 10:01
 */
public class PropertyProvider implements DefaultTemplatePropertiesProvider {

    private final AtSoundtrackSettings settings = PluginSettings.getInstance();

    @Override
    public void fillProperties(PsiDirectory psiDirectory, Properties properties) {

        String name = Renderer.render(AtSoundtrackComponent.getSoundtrack(), this.settings);
        if (!name.trim().isEmpty()) {
            properties.put("soundtrack", name);
        } else {
            properties.put("soundtrack", "");
        }
    }
}
