package biz.paluch.atsoundtrack.applescript;

import biz.paluch.atsoundtrack.logging.InternalLoggerFactory;

import javax.script.ScriptException;

/**
 * AppleScript script engines.
 *
 * @author Mark Paluch
 */
public enum AppleScriptEvaluators implements ScriptEvaluator {

    ScriptEngine {

        private final javax.script.ScriptEngine scriptEngine;
        {

            javax.script.ScriptEngine engine;
            try {
                engine = AppleScriptEngine.createScriptEngine();
            } catch (Exception e) {
                engine = null;
            }

            this.scriptEngine = engine;
        }

        @Override
        public boolean isAvailable() {
            return this.scriptEngine != null && AppleScriptEngine.isAvailable();
        }

        @Override
        public String evaluate(String code) {
            try {
                Object o = this.scriptEngine.eval(code);
                if (o == null) {
                    return null;
                }
                return o.toString();
            } catch (ScriptException e) {
                InternalLoggerFactory
                        .getLogger(AppleScriptEvaluators.class).warn(e.getMessage(), e);
            }
            return null;
        }
    }, OSAScript {
        @Override
        public boolean isAvailable() {
            return biz.paluch.atsoundtrack.applescript.OSAScript.isAvailable();
        }

        @Override
        public String evaluate(String code) {
            return biz.paluch.atsoundtrack.applescript.OSAScript.eval(code);
        }
    };

    public abstract boolean isAvailable();
}
