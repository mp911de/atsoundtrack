package biz.paluch.atsoundtrack.applescript;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

import org.apache.commons.exec.OS;

import com.intellij.openapi.diagnostic.Logger;

/**
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 * @soundtrack Tranceformation Rewired by Diverted 116 (May 2015) - Ciacomix, Thomas Coastline
 */
public class AppleScriptEngine {
    private static Logger logger = Logger.getInstance(AppleScriptEngine.class);
    private static Class<ScriptEngineFactory> factoryClass;

    static {
        if (OS.isFamilyMac()) {
            try {
                factoryClass = (Class) Class.forName("apple.applescript.AppleScriptEngineFactory");
            } catch (ClassNotFoundException e) {
                logger.debug(e);
            }
        }
    }

    private AppleScriptEngine() {

    }

    /**
     * 
     * @return true if available
     * 
     */
    public static boolean isAvailable() {
        return factoryClass != null;
    }

    /**
     * Create a new script engine instance.
     * 
     * @return a new {@linkplain ScriptEngine}
     */
    public static ScriptEngine createScriptEngine() {

        try {
            return factoryClass.newInstance().getScriptEngine();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

    }
}
