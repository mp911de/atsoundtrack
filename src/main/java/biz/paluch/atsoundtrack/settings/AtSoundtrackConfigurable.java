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

package biz.paluch.atsoundtrack.settings;

import javax.swing.*;

import biz.paluch.atsoundtrack.Messages;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AtSoundtrackConfigurable implements SearchableConfigurable {

    @Nullable
    private AtSoundtrackConfigurationForm form;

    @Override
    @Nls
    public String getDisplayName() {
        return Messages.message("plugin.name");
    }

    @Override
    @Nullable
    @NonNls
    public String getHelpTopic() {
        return "AtSoundtrack";
    }

    @Override
    @NotNull
    public JComponent createComponent() {
        if (form == null) {
            form = new AtSoundtrackConfigurationForm(AtSoundtrackSettings.getInstance());
        }
        return form.getPanel();
    }

    @Override
    public boolean isModified() {
        return form != null && (form.isModified());
    }

    @Override
    public void apply() {
        if (form != null) {
            form.apply();
        }
    }

    @Override
    public void reset() {
        if (form != null) {
            form.initFromSettings();
        }
    }

    @Override
    public void disposeUIResources() {
        form = null;
    }

    @NotNull
    @Override
    public String getId() {
        return getDisplayName();
    }

    @Nullable
    @Override
    public Runnable enableSearch(String s) {
        return null;
    }
}