/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package biz.paluch.atsoundtrack.applescript;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

/**
 * @author Mark Paluch
 * @soundtrack Tranceformation Rewired by Diverted 116 (May 2015) - Ciacomix, Thomas Coastline
 */
public class AppleScriptEngine {
    private static Class<ScriptEngineFactory> factoryClass;

    static {
        if (OSAScript.IS_MAC) {
            try {
                factoryClass = (Class) Class.forName("apple.applescript.AppleScriptEngineFactory");
            } catch (ClassNotFoundException e) {
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
