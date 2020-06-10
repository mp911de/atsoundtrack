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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import biz.paluch.atsoundtrack.AtSoundtrackElement;
import biz.paluch.atsoundtrack.SoundTrackProvider;

/**
 * @author Mark Paluch
 * @soundtrack Menno de Jong - Cloudcast 032 (May 2015)
 */
public abstract class AbstractITunesAppleScriptProvider implements SoundTrackProvider {

    private final String applicationName;

    protected AbstractITunesAppleScriptProvider(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    public Map<AtSoundtrackElement, String> getSoundtrack() {

        if (!isRunning()) {
            return Collections.emptyMap();
        }

        Map<AtSoundtrackElement, String> names = new HashMap<AtSoundtrackElement, String>();

        String streamTitle = "" + eval("tell application \"" + applicationName + "\" to get current stream title");
        if (!streamTitle.contains("NSAppleEventDescriptor") && !streamTitle.contains("''msng''")
                && !streamTitle.equals("missing value")) {

            names.put(AtSoundtrackElement.STREAM_TITLE, streamTitle);
        }

        String title = "" + eval("tell application \"" + applicationName + "\"\n" + "\tif exists name of current track then\n"
                + "\t\tget name of current track\n" + "\tend if\n" + "end tell");

        String artist = "" + eval("tell application \"" + applicationName + "\"\n" + "\tif exists artist of current track then\n"
                + "\t\tget artist of current track\n" + "\tend if\n" + "end tell");

        if ("null".equals(title)) {
            title = null;
        }

        if ("null".equals(artist)) {
            artist = null;
        }

        if (title != null && !title.trim().equals("")) {
            names.put(AtSoundtrackElement.TITLE, title);

        }

        if (artist != null && !artist.trim().equals("")) {
            names.put(AtSoundtrackElement.ARTIST, artist);

        }

        return names;
    }

    protected boolean isRunning() {
        String isRunning = eval("tell application \"System Events\" to (name of processes) contains \"" + applicationName + "\"");

        if ("false".equals("" + isRunning) || "0".equals("" + isRunning)) {
            return false;
        }

        String playerState = "" + eval("tell application \"" + applicationName + "\" to get player state as string");
        if (!"playing".equals(playerState)) {
            return false;
        }
        return true;
    }

    protected abstract String eval(String code);
}
