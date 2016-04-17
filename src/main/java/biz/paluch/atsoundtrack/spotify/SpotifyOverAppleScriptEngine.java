package biz.paluch.atsoundtrack.spotify;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import biz.paluch.atsoundtrack.applescript.AppleScriptEngine;

import biz.paluch.atsoundtrack.settings.AtSoundtrackSettings;
import com.intellij.openapi.diagnostic.Logger;

/**
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 * @since 17.05.15 21:49
 */
public class SpotifyOverAppleScriptEngine extends AbstractSpotifyAppleScriptProvider {

    private static Logger logger = Logger.getInstance(SpotifyOverAppleScriptEngine.class);
    private ScriptEngine scriptEngine;

    public SpotifyOverAppleScriptEngine() {

    }

    @Override
    public boolean isApplicable(AtSoundtrackSettings atSoundtrackSettings) {
        return atSoundtrackSettings.isSpotify() && AppleScriptEngine.isAvailable() && isRunning();
    }

    private ScriptEngine getScriptEngine() {

        if (scriptEngine == null) {
            scriptEngine = AppleScriptEngine.createScriptEngine();
        }
        return scriptEngine;
    }

    @Override
    protected String eval(String code) {
        ScriptEngine engine = getScriptEngine();
        try {
            Object o = engine.eval(code);
            if (o == null) {
                return null;
            }
            return o.toString();
        } catch (ScriptException e) {
            logger.warn(e);
            return null;
        }
    }
}
