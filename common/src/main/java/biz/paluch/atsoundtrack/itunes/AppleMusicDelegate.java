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
import biz.paluch.atsoundtrack.applescript.ScriptEvaluator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mark Paluch
 * @soundtrack RFLKTD - To The Milky Way and Beyond (Original Mix)
 * @since 20.07.2020
 */
class AppleMusicDelegate {

    private final String applicationName;
    private final ScriptEvaluator evaluator;

    public AppleMusicDelegate(String applicationName, ScriptEvaluator evaluator) {
        this.applicationName = applicationName;
        this.evaluator = evaluator;
    }

    public Map<AtSoundtrackElement, String> getSoundtrack() {

        if (!isRunning()) {
            return Collections.emptyMap();
        }

        Map<AtSoundtrackElement, String> names = new HashMap<>();

        String streamTitle = "" + evaluate("tell application \"" + this.applicationName + "\" to get current stream title");
        if (!streamTitle.contains("NSAppleEventDescriptor") && !streamTitle.contains("''msng''")
                && !streamTitle.equals("missing value")) {

            names.put(AtSoundtrackElement.STREAM_TITLE, streamTitle);
        }

        String title = "" + evaluate("tell application \"" + this.applicationName + "\"\n" + "\tif exists name of current track then\n"
                + "\t\tget name of current track\n" + "\tend if\n" + "end tell");

        String artist = "" + evaluate("tell application \"" + this.applicationName + "\"\n" + "\tif exists artist of current track then\n"
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

        if (!this.evaluator.isAvailable()) {
            return false;
        }

        String isRunning = evaluate("tell application \"System Events\" to (name of processes) contains \"" + this.applicationName + "\"");

        if ("false".equals("" + isRunning) || "0".equals("" + isRunning)) {
            return false;
        }

        String playerState = "" + evaluate("tell application \"" + this.applicationName + "\" to get player state as string");
        if (!"playing".equals(playerState)) {
            return false;
        }
        return true;
    }

    private String evaluate(String script) {
        return this.evaluator.evaluate(script);
    }

}
