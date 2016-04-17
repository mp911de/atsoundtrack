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
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import biz.paluch.atsoundtrack.AtSoundtrackElement;
import biz.paluch.atsoundtrack.Messages;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.XmlSerializerUtil;

/**
 * Settings for AtSoundtrack
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 */
@State(name = "AtSoundtrackSettings", storages = {
        @Storage(id = "AtSoundtrackSettings", file = "$APP_CONFIG$/atsoundtrack.xml") })
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AtSoundtrackSettings
        implements ApplicationComponent, PersistentStateComponent<AtSoundtrackSettings>, ExportableApplicationComponent {

    private long sleepMs = 1000L;
    private boolean iTunes = true;
    private boolean spotify = true;
    private boolean preferScriptEngine = true;
    private Map<AtSoundtrackElement, Parentheses> parentheses = new HashMap<AtSoundtrackElement, Parentheses>();
    private String content = "${title} - ${artist}";

    public static AtSoundtrackSettings getInstance() {
        return ApplicationManager.getApplication().getComponent(AtSoundtrackSettings.class);
    }

    public AtSoundtrackSettings clone() {
        return new AtSoundtrackSettings(sleepMs, iTunes, spotify, preferScriptEngine,
                new HashMap<AtSoundtrackElement, Parentheses>(parentheses), content);
    }

    @Override
    public void initComponent() {

        if (parentheses.isEmpty()) {
            parentheses.put(AtSoundtrackElement.ARTIST, Parentheses.NONE);
            parentheses.put(AtSoundtrackElement.ARTIST, Parentheses.NONE);
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

    public void apply(AtSoundtrackSettings atSoundtrackSettings) {

        this.content = atSoundtrackSettings.content;
        this.iTunes = atSoundtrackSettings.iTunes;
        this.spotify = atSoundtrackSettings.spotify;
        this.parentheses = new HashMap<AtSoundtrackElement, Parentheses>(atSoundtrackSettings.parentheses);
    }
}
