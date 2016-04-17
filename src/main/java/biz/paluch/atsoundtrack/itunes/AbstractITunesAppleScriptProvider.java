package biz.paluch.atsoundtrack.itunes;

import static com.intellij.openapi.util.text.StringUtil.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import biz.paluch.atsoundtrack.AtSoundtrackElement;
import biz.paluch.atsoundtrack.SoundTrackProvider;

/**
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 * @soundtrack Menno de Jong - Cloudcast 032 (May 2015)
 */
public abstract class AbstractITunesAppleScriptProvider implements SoundTrackProvider {


    @Override
    public Map<AtSoundtrackElement, String> getSoundtrack() {
        
        if (!isRunning()) {
            return Collections.emptyMap();
        }

        Map<AtSoundtrackElement, String> names = new HashMap<AtSoundtrackElement, String>();
        
        String streamTitle = "" + eval("tell application \"iTunes\" to get current stream title");
        if (!streamTitle.contains("NSAppleEventDescriptor") && !streamTitle.contains("''msng''")
                && !streamTitle.equals("missing value")) {

            names.put(AtSoundtrackElement.STREAM_TITLE, streamTitle);
        }

        String title = ""
                + eval("tell application \"iTunes\"\n" + "\tif exists name of current track then\n"
                        + "\t\tget name of current track\n" + "\tend if\n" + "end tell");

        String artist = ""
                + eval("tell application \"iTunes\"\n" + "\tif exists artist of current track then\n"
                        + "\t\tget artist of current track\n" + "\tend if\n" + "end tell");

        if ("null".equals(title)) {
            title = null;
        }

        if ("null".equals(artist)) {
            artist = null;
        }

        if (!isEmpty(title)) {
            names.put(AtSoundtrackElement.TITLE, title);

        }
        if (!isEmpty(artist)) {
            names.put(AtSoundtrackElement.ARTIST, artist);

        }

        return names;
    }

    protected boolean isRunning() {
        String isRunning = eval("tell application \"System Events\" to (name of processes) contains \"iTunes\"");

        if ("false".equals("" + isRunning) || "0".equals("" + isRunning)) {
            return false;
        }

        String playerState = "" + eval("tell application \"iTunes\" to get player state as string");
        if (!"playing".equals(playerState)) {
            return false;
        }
        return true;
    }

    protected abstract String eval(String code);
}
