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

package biz.paluch.atsoundtrack.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;

/**
 * @author Mark Paluch
 * @since 13.05.15 11:49
 */
public class AtSoundtrackContributor extends CompletionContributor {

    public AtSoundtrackContributor() {

         PsiElementPattern.Capture<PsiElement> pattern =
                PlatformPatterns.psiElement();


        extend(CompletionType.BASIC, pattern, new AtSoundtrackCompletionProvider());
    }

    public void fillCompletionVariants(CompletionParameters parameters, CompletionResultSet result) {
        /*AtSoundtrackSettings settings = AtSoundtrackSettings.getInstance();

        String prefix = null;
        if (parameters != null && parameters.getOriginalPosition() != null) {
            prefix = parameters.getOriginalPosition().getText();
        }

        if (prefix != null && !prefix.contains("soundtrack")) {
            String name = Renderer.render(AtSoundtrack.getSoundtrack(), settings);
            if (!isEmpty(name)) {

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
                        result.addElement(LookupElementBuilder.create("@soundtrack " + name));
                    } else {
                        result.addElement(LookupElementBuilder.create("soundtrack " + name));
                    }
                }
            }
        } */

        super.fillCompletionVariants(parameters, result);
    }


}
