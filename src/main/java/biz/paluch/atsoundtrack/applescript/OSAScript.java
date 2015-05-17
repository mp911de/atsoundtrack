package biz.paluch.atsoundtrack.applescript;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.exec.OS;

import com.intellij.openapi.diagnostic.Logger;

/**
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 * @soundtrack Tranceformation Rewired by Diverted 116 (May 2015) - Ciacomix, Thomas Coastline
 */
public class OSAScript {
    private static Logger logger = Logger.getInstance(OSAScript.class);
    private static final int EOF = -1;
    private static final boolean available;

    static {
        boolean check = false;
        if (OS.isFamilyMac()) {
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
            logger.warn(e);
            return null;
        } catch (InterruptedException e) {
            logger.warn(e);
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
