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

package biz.paluch.atsoundtrack;

import biz.paluch.atsoundtrack.logging.InternalLogger;
import biz.paluch.atsoundtrack.logging.InternalLoggerFactory;

import com.intellij.openapi.diagnostic.Logger;

/**
 * @author Mark Paluch
 */
public class IntelliJLoggerFactory extends InternalLoggerFactory {

    @Override
    protected InternalLogger getLoggerInstance(Class<?> theClass) {
        return new IntelliJLogger(Logger.getInstance(theClass));
    }

    private static class IntelliJLogger implements InternalLogger {

        private final Logger logger;

        public IntelliJLogger(Logger logger) {
            this.logger = logger;
        }

        @Override
        public void info(String message) {
            logger.info(message);
        }

        @Override
        public void debug(String message) {
            logger.debug(message);
        }

        @Override
        public void warn(String message) {
            logger.warn(message);
        }

        @Override
        public void warn(String message, Throwable throwable) {
            logger.warn(message, throwable);
        }

        @Override
        public void error(String message) {
            logger.error(message);
        }

        @Override
        public void error(String message, Throwable throwable) {
            logger.error(message, throwable);
        }
    }
}
