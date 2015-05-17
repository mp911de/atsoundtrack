package biz.paluch.atsoundtrack.spotify;

import biz.paluch.atsoundtrack.applescript.OSAScript;

import com.intellij.openapi.diagnostic.Logger;

/**
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 * @since 17.05.15 21:49
 * @soundtrack Tranceformation Rewired by Diverted 116 (May 2015) - Ciacomix, Thomas Coastline
 */
public class SpotifyOverAppleScriptOSAScript extends AbstractSpotifyAppleScriptProvider {
    private static Logger logger = Logger.getInstance(SpotifyOverAppleScriptOSAScript.class);

    public SpotifyOverAppleScriptOSAScript() {

    }

    @Override
    public boolean isApplicable() {
        return OSAScript.isAvailable() && isRunning();
    }

    protected String eval(String code) {
        return OSAScript.eval(code);
    }

}
