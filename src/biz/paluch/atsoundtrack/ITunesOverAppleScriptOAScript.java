package biz.paluch.atsoundtrack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.exec.OS;

import com.intellij.openapi.diagnostic.Logger;

/**
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 * @since 13.05.15 11:32
 */
public class ITunesOverAppleScriptOAScript extends AbstractITunesAppleScriptProvider {
    private static Logger logger = Logger.getInstance(ITunesOverAppleScriptOAScript.class);

    private boolean applicable = false;
    private static final int EOF = -1;

    public ITunesOverAppleScriptOAScript() {

        if (OS.isFamilyMac()) {
            applicable = true;
        }
    }

    @Override
    public boolean isApplicable() {
        return applicable;
    }

    protected String eval(String code) {
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

    public static long copyLarge(InputStream input, OutputStream output, byte[] buffer) throws IOException {

        long count = 0;
        int n = 0;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
}
