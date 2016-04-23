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

package biz.paluch.atsoundtrack.logging;

/**
 * Logger abstraction for various logger frameworks.
 *
 * @author Mark Paluch
 */
public abstract class InternalLoggerFactory {

    private static final InternalLoggerFactory internalLoggerFactory;

    static {

        InternalLoggerFactory loggerFactory = new NoOpLoggerFactory();
        try {
            loggerFactory = (InternalLoggerFactory) Class.forName("biz.paluch.atsoundtrack.IntelliJLoggerFactory")
                    .newInstance();
        } catch (Exception e) {
        }

        internalLoggerFactory = loggerFactory;
    }

    /**
     * Creates a new  {@link InternalLogger} for the given {@link Class}.
     *
     * @param theClass
     * @return
     */
    public static InternalLogger getLogger(Class<?> theClass) {
        return internalLoggerFactory.getLoggerInstance(theClass);
    }

    protected abstract InternalLogger getLoggerInstance(Class<?> theClass);

    public static class NoOpLoggerFactory extends InternalLoggerFactory {

        @Override
        protected InternalLogger getLoggerInstance(Class<?> theClass) {
            return NoOpLogger.INSTANCE;
        }
    }

    public static class NoOpLogger implements InternalLogger {
        public final static NoOpLogger INSTANCE = new NoOpLogger();

        private NoOpLogger() {
        }

        @Override
        public void info(String message) {

        }

        @Override
        public void debug(String message) {

        }

        @Override
        public void warn(String message) {

        }

        @Override
        public void warn(String message, Throwable throwable) {

        }

        @Override
        public void error(String message) {

        }

        @Override
        public void error(String message, Throwable throwable) {

        }
    }

}
