package biz.paluch.atsoundtrack;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 * @since 13.05.15 11:32
 */
public class ITunesOverAppleScript implements SoundTrackProvider {

    private ScriptEngine scriptEngine;
    private ScriptEngineManager manager = new ScriptEngineManager();

    @Override
    public boolean isApplicable() {
        return getScriptEngine() != null;
    }

    private ScriptEngine getScriptEngine() {

        if (scriptEngine == null) {
            scriptEngine = getAppleScriptEngine();
        }
        return scriptEngine;
    }

    private ScriptEngine getAppleScriptEngine() {

        for (ScriptEngineFactory factory : manager.getEngineFactories()) {

            if (factory.getEngineName().contains("AppleScript")) {
                return factory.getScriptEngine();
            }
        }

        return null;
    }

    @Override
    public String getName() {

        ScriptEngine engine = getScriptEngine();
        if (engine == null) {
            throw new IllegalStateException("Cannot obtain Apple Script engine");
        }
        try {
            Object isRunning = engine.eval("tell application \"System Events\" to (name of processes) contains \"iTunes\"");

            if ("0".equals("" + isRunning)) {
                return null;
            }

            String playerState = "" + engine.eval("tell application \"iTunes\" to get player state as string");
            if (!"playing".equals(playerState)) {
                return null;
            }

            String streamTitle = "" + engine.eval("tell application \"iTunes\" to get current stream title");
            if (!streamTitle.contains("NSAppleEventDescriptor") && !streamTitle.contains("''msng''")) {
                return streamTitle;
            }

            String trackName = ""
                    + engine.eval("tell application \"iTunes\"\n" + "\tif exists name of current track then\n"
                            + "\t\tget name of current track\n" + "\tend if\n" + "end tell");

            String artistName = ""
                    + engine.eval("tell application \"iTunes\"\n" + "\tif exists artist of current track then\n"
                            + "\t\tget artist of current track\n" + "\tend if\n" + "end tell");

            if ("null".equals(trackName)) {
                trackName = null;
            }

            if ("null".equals(artistName)) {
                artistName = null;
            }

            if (trackName != null) {

                if (trackName != null && artistName != null) {
                    return artistName + " - " + trackName;
                } else {
                    return trackName;
                }
            }
        } catch (ScriptException e) {
            e.printStackTrace();
        }

        return null;
    }
}
