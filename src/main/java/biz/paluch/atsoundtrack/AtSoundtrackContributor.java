package biz.paluch.atsoundtrack;

import static com.intellij.openapi.util.text.StringUtil.*;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiDocCommentBase;
import com.intellij.psi.xml.XmlComment;
import com.intellij.psi.xml.XmlToken;

/**
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 * @since 13.05.15 11:49
 */
public class AtSoundtrackContributor extends CompletionContributor {

    public void fillCompletionVariants(CompletionParameters parameters, CompletionResultSet result) {

        String prefix = null;
        if(parameters != null && parameters.getOriginalPosition() != null){
            prefix = parameters.getOriginalPosition().getText();
        }

        if (prefix != null && !prefix.contains("soundtrack")) {

            if (!isEmpty(AtSoundtrack.getName())) {

                boolean qualified = false;
                if (parameters.getOriginalPosition() instanceof PsiComment
                        || parameters.getOriginalPosition().getContext() instanceof PsiDocCommentBase
                        || parameters.getOriginalPosition().getClass().getName().contains("PsiDocToken")) {

                    qualified = true;
                }
                if (parameters.getOriginalPosition() instanceof XmlToken) {
                    XmlToken xmlToken = (XmlToken) parameters.getOriginalPosition();

                    if (xmlToken instanceof XmlComment || xmlToken.getContext() instanceof XmlComment) {
                        qualified = true;
                    }
                }

                if (qualified) {
                    if (!prefix.contains("@")) {
                        result.addElement(LookupElementBuilder.create("@soundtrack " + AtSoundtrack.getName()));
                    } else {
                        result.addElement(LookupElementBuilder.create("soundtrack " + AtSoundtrack.getName()));
                    }
                }
            }
        }

        super.fillCompletionVariants(parameters, result);
    }
}
