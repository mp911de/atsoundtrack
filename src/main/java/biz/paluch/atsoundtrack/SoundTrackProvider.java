package biz.paluch.atsoundtrack;

import java.util.Map;

import biz.paluch.atsoundtrack.settings.AtSoundtrackSettings;

/**
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 * @since 13.05.15 11:32
 */
public interface SoundTrackProvider {

	boolean isApplicable(AtSoundtrackSettings atSoundtrackSettings);

	Map<AtSoundtrackElement, String> getSoundtrack();
}
