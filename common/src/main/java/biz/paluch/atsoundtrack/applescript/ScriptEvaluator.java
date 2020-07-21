package biz.paluch.atsoundtrack.applescript;

/**
 * Interface representing a script evaluator. Evaluators may be available or unavailable.
 *
 * @author Mark Paluch
 * @soundtrack RFLKTD - To The Milky Way and Beyond (Original Mix)
 */
public interface ScriptEvaluator {

    /**
     * Evaluate script {@code code}.
     *
     * @param code
     * @return
     */
    String evaluate(String code);

    /**
     * @return {@code true} if the evaluator is available/enabled.
     */
    boolean isAvailable();

}
