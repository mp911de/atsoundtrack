package biz.paluch.atsoundtrack;

import static com.intellij.openapi.util.text.StringUtil.*;

/**
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 * @soundtrack Menno de Jong - Cloudcast 032 (May 2015)
 */
public abstract class AbstractITunesAppleScriptProvider implements SoundTrackProvider {

    public String getName() {

        String isRunning = eval("tell application \"System Events\" to (name of processes) contains \"iTunes\"");

        if ("false".equals("" + isRunning) || "0".equals("" + isRunning)) {
            return null;
        }

        String playerState = "" + eval("tell application \"iTunes\" to get player state as string");
        if (!"playing".equals(playerState)) {
            return null;
        }

        String streamTitle = "" + eval("tell application \"iTunes\" to get current stream title");
        if (!streamTitle.contains("NSAppleEventDescriptor") && !streamTitle.contains("''msng''")
                && !streamTitle.equals("missing value")) {
            return streamTitle;
        }

        String trackName = ""
                + eval("tell application \"iTunes\"\n" + "\tif exists name of current track then\n"
                        + "\t\tget name of current track\n" + "\tend if\n" + "end tell");

        String artistName = ""
                + eval("tell application \"iTunes\"\n" + "\tif exists artist of current track then\n"
                        + "\t\tget artist of current track\n" + "\tend if\n" + "end tell");

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

    protected abstract String eval(String code);
}
