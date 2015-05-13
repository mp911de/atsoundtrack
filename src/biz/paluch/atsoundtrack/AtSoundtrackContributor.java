package biz.paluch.atsoundtrack;

import org.jetbrains.annotations.NotNull;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElement;

/**
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 * @since 13.05.15 11:49
 */
public class AtSoundtrackContributor extends CompletionContributor {

    @Override
    public void fillCompletionVariants(CompletionParameters parameters, CompletionResultSet result) {


        if (AtSoundtrack.getName() != null) {
            result.addElement(new LookupElement() {
                @NotNull
                @Override
                public String getLookupString() {
                    return "@soundtrack " + AtSoundtrack.getName();
                }
            });
        }
        super.fillCompletionVariants(parameters, result);
    }
}
