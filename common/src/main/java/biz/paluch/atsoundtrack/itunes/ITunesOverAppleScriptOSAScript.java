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

import biz.paluch.atsoundtrack.applescript.OSAScript;
import biz.paluch.atsoundtrack.settings.AtSoundtrackSettings;

/**
 * @author Mark Paluch
 * @since 13.05.15 11:32
 */
public class ITunesOverAppleScriptOSAScript extends AbstractITunesAppleScriptProvider {

    public ITunesOverAppleScriptOSAScript() {

        super("iTunes");
    }

    @Override
    public boolean isApplicable(AtSoundtrackSettings atSoundtrackSettings) {
        return atSoundtrackSettings.isITunes() && OSAScript.isAvailable() && isRunning();
    }

    protected String eval(String code) {
        return OSAScript.eval(code);
    }

}
