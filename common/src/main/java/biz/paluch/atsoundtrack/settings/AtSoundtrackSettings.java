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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Settings for AtSoundtrack
 *
 * @author Mark Paluch
 */
public class AtSoundtrackSettings {

    private long sleepMs = 1000L;
    private boolean iTunes = true;
    private boolean spotify = true;
    private boolean preferScriptEngine = false;
    private Map<AtSoundtrackElement, Parentheses> parentheses = new HashMap<>();
    private String content = "${title} - ${artist}";

    public AtSoundtrackSettings(long sleepMs, boolean iTunes, boolean spotify, boolean preferScriptEngine, Map<AtSoundtrackElement, Parentheses> parentheses, String content) {
        this.sleepMs = sleepMs;
        this.iTunes = iTunes;
        this.spotify = spotify;
        this.preferScriptEngine = preferScriptEngine;
        this.parentheses = parentheses;
        this.content = content;
    }

    public AtSoundtrackSettings() {
    }

    public AtSoundtrackSettings clone() {
        return new AtSoundtrackSettings(this.sleepMs, this.iTunes, this.spotify, this.preferScriptEngine,
                new HashMap<>(this.parentheses), this.content);
    }

    public void apply(AtSoundtrackSettings atSoundtrackSettings) {

        this.content = atSoundtrackSettings.content;
        this.iTunes = atSoundtrackSettings.iTunes;
        this.spotify = atSoundtrackSettings.spotify;
        this.parentheses = new HashMap<>(atSoundtrackSettings.parentheses);
    }

    public long getSleepMs() {
        return this.sleepMs;
    }

    public boolean isITunes() {
        return this.iTunes;
    }

    public boolean isSpotify() {
        return this.spotify;
    }

    public boolean isPreferScriptEngine() {
        return this.preferScriptEngine;
    }

    public Map<AtSoundtrackElement, Parentheses> getParentheses() {
        return this.parentheses;
    }

    public String getContent() {
        return this.content;
    }

    public void setSleepMs(long sleepMs) {
        this.sleepMs = sleepMs;
    }

    public void setITunes(boolean iTunes) {
        this.iTunes = iTunes;
    }

    public void setSpotify(boolean spotify) {
        this.spotify = spotify;
    }

    public void setPreferScriptEngine(boolean preferScriptEngine) {
        this.preferScriptEngine = preferScriptEngine;
    }

    public void setParentheses(Map<AtSoundtrackElement, Parentheses> parentheses) {
        this.parentheses = parentheses;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AtSoundtrackSettings)) {
            return false;
        }
        AtSoundtrackSettings that = (AtSoundtrackSettings) o;
        return this.sleepMs == that.sleepMs &&
                this.iTunes == that.iTunes &&
                this.spotify == that.spotify &&
                this.preferScriptEngine == that.preferScriptEngine &&
                Objects.equals(this.parentheses, that.parentheses) &&
                Objects.equals(this.content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(this.sleepMs, this.iTunes, this.spotify, this.preferScriptEngine, this.parentheses, this.content);
    }

    public String toString() {
        return "AtSoundtrackSettings(sleepMs=" + this.getSleepMs() + ", iTunes=" + this
                .isITunes() + ", spotify=" + this
                .isSpotify() + ", preferScriptEngine=" + this
                .isPreferScriptEngine() + ", parentheses=" + this
                .getParentheses() + ", content=" + this.getContent() + ")";
    }
}
