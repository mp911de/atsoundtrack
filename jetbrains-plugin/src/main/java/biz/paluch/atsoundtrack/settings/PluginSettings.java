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

import biz.paluch.atsoundtrack.AtSoundtrackElement;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * @author Mark Paluch
 */
@State(name = "AtSoundtrackSettings", storages = {
        @Storage("$APP_CONFIG$/atsoundtrack.xml")})
public class PluginSettings extends AtSoundtrackSettings
        implements PersistentStateComponent<AtSoundtrackSettings> {

    public static AtSoundtrackSettings getInstance() {
        return ApplicationManager.getApplication().getComponent(PluginSettings.class);
    }

    public PluginSettings(long sleepMs, boolean iTunes, boolean spotify, boolean preferScriptEngine, Map<AtSoundtrackElement, Parentheses> parentheses, String content) {
        super(sleepMs, iTunes, spotify, preferScriptEngine, parentheses, content);
    }

    public PluginSettings() {

        getParentheses().put(AtSoundtrackElement.TITLE, Parentheses.NONE);
        getParentheses().put(AtSoundtrackElement.STREAM_TITLE, Parentheses.NONE);
        getParentheses().put(AtSoundtrackElement.ARTIST, Parentheses.NONE);
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
