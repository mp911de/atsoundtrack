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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Settings for AtSoundtrack
 * 
 * @author Mark Paluch
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AtSoundtrackSettings {

    private long sleepMs = 1000L;
    private boolean iTunes = true;
    private boolean spotify = true;
    private boolean preferScriptEngine = false;
    private Map<AtSoundtrackElement, Parentheses> parentheses = new HashMap<AtSoundtrackElement, Parentheses>();
    private String content = "${title} - ${artist}";

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
}
