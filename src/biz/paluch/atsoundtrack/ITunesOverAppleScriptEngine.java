package biz.paluch.atsoundtrack;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;

import org.apache.commons.exec.OS;

import com.intellij.openapi.diagnostic.Logger;

/**
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 * @since 13.05.15 11:32
 */
public class ITunesOverAppleScriptEngine extends AbstractITunesAppleScriptProvider {

    private static Logger logger = Logger.getInstance(ITunesOverAppleScriptEngine.class);
    private ScriptEngine scriptEngine;
    private Class<ScriptEngineFactory> factoryClass;

    public ITunesOverAppleScriptEngine() {

        if (OS.isFamilyMac()) {

            try {
                factoryClass = (Class) Class.forName("apple.applescript.AppleScriptEngineFactory");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isApplicable() {
        return factoryClass != null;
    }

    private ScriptEngine getScriptEngine() {

        if (scriptEngine == null) {
            scriptEngine = getAppleScriptEngine();
        }
        return scriptEngine;
    }

    private ScriptEngine getAppleScriptEngine() {

        if (factoryClass != null) {
            if (scriptEngine == null) {
                try {
                    scriptEngine = factoryClass.newInstance().getScriptEngine();
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
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
