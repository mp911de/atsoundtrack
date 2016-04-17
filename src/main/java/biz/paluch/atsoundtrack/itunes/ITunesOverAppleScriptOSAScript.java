package biz.paluch.atsoundtrack.itunes;

import biz.paluch.atsoundtrack.applescript.OSAScript;
import biz.paluch.atsoundtrack.settings.AtSoundtrackSettings;

/**
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 * @since 13.05.15 11:32
 */
public class ITunesOverAppleScriptOSAScript extends AbstractITunesAppleScriptProvider {

    public ITunesOverAppleScriptOSAScript() {

    }

    @Override
    public boolean isApplicable(AtSoundtrackSettings atSoundtrackSettings) {
        return atSoundtrackSettings.isITunes() && OSAScript.isAvailable() && isRunning();
    }

    protected String eval(String code) {
        return OSAScript.eval(code);
    }

}
