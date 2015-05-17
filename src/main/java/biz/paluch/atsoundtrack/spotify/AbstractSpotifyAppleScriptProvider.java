package biz.paluch.atsoundtrack.spotify;

import static com.intellij.openapi.util.text.StringUtil.*;

import biz.paluch.atsoundtrack.SoundTrackProvider;

/**
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 * @soundtrack Tranceformation Rewired by Diverted 116 (May 2015) - Ciacomix, Thomas Coastline
 */
public abstract class AbstractSpotifyAppleScriptProvider implements SoundTrackProvider {

    public String getName() {

        if (!isRunning()) {
            return null;
        }

        String trackName = "" + eval("tell application \"Spotify\"\n" + "get name of current track\n" + "end tell");

        String artistName = "" + eval("tell application \"Spotify\"\n" + "get artist of current track\n" + "end tell");

        if ("null".equals(trackName)) {
            trackName = null;
        }

        if ("null".equals(artistName)) {
            artistName = null;
        }

        if (!isEmpty(trackName)) {

            if (!isEmpty(artistName)) {
                return artistName + " - " + trackName;
            } else {
                return trackName;
            }
        }

        return null;
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
