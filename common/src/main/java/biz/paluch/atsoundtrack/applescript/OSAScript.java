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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

import biz.paluch.atsoundtrack.logging.InternalLogger;
import biz.paluch.atsoundtrack.logging.InternalLoggerFactory;

/**
 * @author Mark Paluch
 * @soundtrack Tranceformation Rewired by Diverted 116 (May 2015) - Ciacomix, Thomas Coastline
 */
public class OSAScript {

    public final static boolean IS_MAC;

    private final static InternalLogger log = InternalLoggerFactory.getLogger(OSAScript.class);
    private final static int EOF = -1;
    private final static boolean available;
    private final static String OS_NAME = System.getProperty("os.name").toLowerCase(Locale.US);

    static {

        IS_MAC = OS_NAME.indexOf("mac os x") > -1;

        boolean check = false;
        if (IS_MAC) {
            String result = eval("true");
            if ("true".equals(result)) {
                check = true;
            }
        }

        available = check;
    }

    private OSAScript() {

    }

    /**
     * 
     * @return true if available
     * 
     */
    public static boolean isAvailable() {
        return available;
    }

    /**
     * Execute AppleScript using {@literal osascript} No exceptions are thrown by this method.
     * 
     * @param code the code to evaluate
     * @return script result.
     */
    public static String eval(String code) {
        Runtime runtime = Runtime.getRuntime();
        String[] args = { "osascript", "-e", code };

        try {
            Process process = runtime.exec(args);
            process.waitFor();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            InputStream is = process.getInputStream();
            copyLarge(is, baos, new byte[4096]);
            return baos.toString().trim();

        } catch (IOException e) {
            log.warn(e.getMessage(), e);
            return null;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    private static long copyLarge(InputStream input, OutputStream output, byte[] buffer) throws IOException {

        long count = 0;
        int n;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
}
