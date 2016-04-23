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

import java.io.File;

import biz.paluch.atsoundtrack.AtSoundtrackElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import biz.paluch.atsoundtrack.Messages;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.XmlSerializerUtil;

/**
 * @author Mark Paluch
 */
@State(name = "AtSoundtrackSettings", storages = {
        @Storage(id = "AtSoundtrackSettings", file = "$APP_CONFIG$/atsoundtrack.xml") })

public class PluginSettings extends AtSoundtrackSettings
        implements ApplicationComponent, PersistentStateComponent<AtSoundtrackSettings>, ExportableApplicationComponent {

    public static AtSoundtrackSettings getInstance() {
        return ApplicationManager.getApplication().getComponent(PluginSettings.class);
    }

    @Override
    public void initComponent() {

        if (getParentheses().isEmpty()) {
            getParentheses().put(AtSoundtrackElement.ARTIST, Parentheses.NONE);
            getParentheses().put(AtSoundtrackElement.ARTIST, Parentheses.NONE);
        }
    }

    @Override
    public void disposeComponent() {

    }

    @NotNull
    @Override
    public File[] getExportFiles() {
        return new File[0];
    }

    @NotNull
    @Override
    public String getPresentableName() {
        return Messages.message("plugin.name");
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "AtSoundtrackSettings";
    }

    @Nullable
    @Override
    public AtSoundtrackSettings getState() {
        return this;
    }

    @Override
    public void loadState(AtSoundtrackSettings atSoundtrackSettings) {
        XmlSerializerUtil.copyBean(atSoundtrackSettings, this);
    }
}
