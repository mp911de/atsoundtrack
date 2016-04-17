package biz.paluch.atsoundtrack.itunes;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import biz.paluch.atsoundtrack.applescript.AppleScriptEngine;

import biz.paluch.atsoundtrack.settings.AtSoundtrackSettings;
import com.intellij.openapi.diagnostic.Logger;

/**
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 * @since 13.05.15 11:32
 */
public class ITunesOverAppleScriptEngine extends AbstractITunesAppleScriptProvider {

    private static Logger logger = Logger.getInstance(ITunesOverAppleScriptEngine.class);
    private ScriptEngine scriptEngine;

    public ITunesOverAppleScriptEngine() {

    }

    @Override
    public boolean isApplicable(AtSoundtrackSettings atSoundtrackSettings) {
        return atSoundtrackSettings.isITunes() && AppleScriptEngine.isAvailable() && isRunning();
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
