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

package biz.paluch.atsoundtrack.itunes;

import biz.paluch.atsoundtrack.AtSoundtrackElement;
import biz.paluch.atsoundtrack.SoundTrackProvider;
import biz.paluch.atsoundtrack.applescript.ScriptEvaluator;
import biz.paluch.atsoundtrack.settings.AtSoundtrackSettings;

import java.util.Map;

/**
 * Apple Music app since macOS Catalina.
 *
 * @author Mark Paluch
 * @since 21.07.2020
 */
public class AppleMusicOverAppleScript implements SoundTrackProvider {

    private final AppleMusicDelegate delegate;

    public AppleMusicOverAppleScript(ScriptEvaluator evaluator) {
        this.delegate = new AppleMusicDelegate("Music", evaluator);
    }

    @Override
    public boolean isApplicable(AtSoundtrackSettings atSoundtrackSettings) {
        return atSoundtrackSettings.isITunes() && this.delegate.isRunning();
    }

    @Override
    public Map<AtSoundtrackElement, String> getSoundtrack() {
        return this.delegate.getSoundtrack();
    }

}
