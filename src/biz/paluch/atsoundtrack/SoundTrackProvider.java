package biz.paluch.atsoundtrack;

/**
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 * @since 13.05.15 11:32
 */
public interface SoundTrackProvider {

	boolean isApplicable();

	String getName();
}
