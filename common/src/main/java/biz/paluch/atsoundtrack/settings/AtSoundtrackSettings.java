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

import java.util.HashMap;
import java.util.Map;

import biz.paluch.atsoundtrack.AtSoundtrackElement;

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
    private Map<AtSoundtrackElement, Parentheses> parentheses = new HashMap<AtSoundtrackElement, Parentheses>();
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
        return new AtSoundtrackSettings(sleepMs, iTunes, spotify, preferScriptEngine,
                new HashMap<AtSoundtrackElement, Parentheses>(parentheses), content);
    }

    public void apply(AtSoundtrackSettings atSoundtrackSettings) {

        this.content = atSoundtrackSettings.content;
        this.iTunes = atSoundtrackSettings.iTunes;
        this.spotify = atSoundtrackSettings.spotify;
        this.parentheses = new HashMap<AtSoundtrackElement, Parentheses>(atSoundtrackSettings.parentheses);
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

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AtSoundtrackSettings)) {
            return false;
        }
        final AtSoundtrackSettings other = (AtSoundtrackSettings) o;
        if (!other.canEqual((Object) this)) {
            return false;
        }
        if (this.getSleepMs() != other.getSleepMs()) {
            return false;
        }
        if (this.isITunes() != other.isITunes()) {
            return false;
        }
        if (this.isSpotify() != other.isSpotify()) {
            return false;
        }
        if (this.isPreferScriptEngine() != other.isPreferScriptEngine()) {
            return false;
        }
        final Object this$parentheses = this.getParentheses();
        final Object other$parentheses = other.getParentheses();
        if (this$parentheses == null ? other$parentheses != null : !this$parentheses
                .equals(other$parentheses)) {
            return false;
        }
        final Object this$content = this.getContent();
        final Object other$content = other.getContent();
        if (this$content == null ? other$content != null : !this$content
                .equals(other$content)) {
            return false;
        }
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AtSoundtrackSettings;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $sleepMs = this.getSleepMs();
        result = result * PRIME + (int) ($sleepMs >>> 32 ^ $sleepMs);
        result = result * PRIME + (this.isITunes() ? 79 : 97);
        result = result * PRIME + (this.isSpotify() ? 79 : 97);
        result = result * PRIME + (this.isPreferScriptEngine() ? 79 : 97);
        final Object $parentheses = this.getParentheses();
        result = result * PRIME + ($parentheses == null ? 43 : $parentheses.hashCode());
        final Object $content = this.getContent();
        result = result * PRIME + ($content == null ? 43 : $content.hashCode());
        return result;
    }

    public String toString() {
        return "AtSoundtrackSettings(sleepMs=" + this.getSleepMs() + ", iTunes=" + this
                .isITunes() + ", spotify=" + this
                .isSpotify() + ", preferScriptEngine=" + this
                .isPreferScriptEngine() + ", parentheses=" + this
                .getParentheses() + ", content=" + this.getContent() + ")";
    }
}
