package biz.paluch.atsoundtrack.spotify;

import static com.intellij.openapi.util.text.StringUtil.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import biz.paluch.atsoundtrack.AtSoundtrackElement;
import biz.paluch.atsoundtrack.SoundTrackProvider;

/**
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 * @soundtrack Tranceformation Rewired by Diverted 116 (May 2015) - Ciacomix, Thomas Coastline
 */
public abstract class AbstractSpotifyAppleScriptProvider implements SoundTrackProvider {

    @Override
    public Map<AtSoundtrackElement, String> getSoundtrack() {

        if (!isRunning()) {
            return Collections.emptyMap();
        }

        Map<AtSoundtrackElement, String> names = new HashMap<AtSoundtrackElement, String>();
        String title = "" + eval("tell application \"Spotify\"\n" + "get name of current track\n" + "end tell");
        String artist = "" + eval("tell application \"Spotify\"\n" + "get artist of current track\n" + "end tell");

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
        String isRunning = eval("tell application \"System Events\" to (name of processes) contains \"Spotify\"");

        if ("false".equals("" + isRunning) || "0".equals("" + isRunning)) {
            return false;
        }

        String playerState = "" + eval("tell application \"Spotify\" to get player state as string");
        if (!"playing".equals(playerState)) {
            return false;
        }
        return true;
    }

    protected abstract String eval(String code);
}
