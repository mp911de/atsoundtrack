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

import org.jetbrains.annotations.NotNull;

import biz.paluch.atsoundtrack.AtSoundtrackComponent;
import biz.paluch.atsoundtrack.Renderer;
import biz.paluch.atsoundtrack.settings.AtSoundtrackSettings;
import biz.paluch.atsoundtrack.settings.PluginSettings;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.AutoCompletionPolicy;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiDocCommentBase;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlToken;
import com.intellij.util.ProcessingContext;

/**
 * Completion provider for the various languages. Avoid static dependencies to language plugins so this plugin can be used
 * across the different Jetbrains products.
 * 
 * @author Mark Paluch
 */
public class AtSoundtrackCompletionProvider extends CompletionProvider<CompletionParameters> {

    @Override
    protected void addCompletions(@NotNull CompletionParameters completionParameters, ProcessingContext processingContext,
            @NotNull CompletionResultSet completionResultSet) {

        AtSoundtrackSettings settings = PluginSettings.getInstance();
        String name = Renderer.render(AtSoundtrackComponent.getSoundtrack(), settings);

        PsiElement origPosition = completionParameters.getOriginalPosition();

        if (origPosition == null || name.trim().isEmpty() || origPosition.getLanguage() == null) {
            return;
        }

        AtSoundtrackContribution contribution = configureContribution(origPosition);
        if (!contribution.shouldContribute()) {
            return;
        }

        String prefix = origPosition.getText();
        String completionString = String.format("soundtrack %s", name);
        String element = contribution.getPrefixChar() + completionString;
        String displayName = element;

        boolean foundPrefix = false;
        if (prefix != null && prefix.length() > 0) {

            int index = prefix.lastIndexOf(contribution.getPrefixChar());
            if (index != -1) {
                foundPrefix = true;
            }
            if (foundPrefix) {
                element = completionString;
            }
        }

        if (foundPrefix && !contribution.isDisplayPrefixCharIfPrefixFound()) {
            displayName = completionString;
        }

        if (!contribution.isPrefixRequired() || (contribution.isPrefixRequired() && foundPrefix)) {
            LookupElementBuilder builder = LookupElementBuilder.create(element).withPresentableText(displayName)
                    .withCaseSensitivity(true);
            completionResultSet.addElement(builder.withAutoCompletionPolicy(AutoCompletionPolicy.GIVE_CHANCE_TO_OVERWRITE));
        }
    }

    private AtSoundtrackContribution configureContribution(PsiElement origPosition) {

        AtSoundtrackContribution contribution = new AtSoundtrackContribution();
        String origPositionString = origPosition.toString();
        String contextString = "";
        String languageId = origPosition.getLanguage().getID();

        if (origPosition.getContext() != null) {
            contextString = origPosition.getContext().toString();
        }

        if (origPosition instanceof PsiComment || origPosition.getContext() instanceof PsiDocCommentBase
                || origPosition.getClass().getName().contains("PsiDocToken")) {
            contribution.enableContribution();
        }

        // XML <!-- ... -->
        if (languageId.equalsIgnoreCase("XML") && origPosition instanceof XmlToken) {
            if (origPositionString.contains("XML_COMMENT_CHARACTERS")) {
                contribution.enableContribution();
            }
        }

        if (languageId.equalsIgnoreCase("CSS")) {
            contribution.prefixRequired();
        }

        if (languageId.equalsIgnoreCase("Python")) {

            if (origPositionString.contains("Py:DOCSTRING")) {
                contribution.setPrefixChar(':');
                contribution.enableContribution();
            }
        }

        if (languageId.equalsIgnoreCase("Scala")) {

            if (origPositionString.contains("DOC_COMMENT_DATA")) {
                contribution.enableContribution();
            }
            contribution.prefixRequired();
            contribution.setDisplayPrefixCharIfPrefixFound(false);
        }

        if (languageId.equalsIgnoreCase("JavaScript")) {
            contribution.setDisplayPrefixCharIfPrefixFound(false);
        }

        if (languageId.equalsIgnoreCase("CoffeeScript")) {
            contribution.prefixRequired();
        }

        if (languageId.equalsIgnoreCase("ruby")) {

            if (origPositionString.contains("block comment") || origPositionString.equalsIgnoreCase("line comment")) {
                contribution.enableContribution();
                contribution.prefixRequired();
            }
        }

        if (languageId.equalsIgnoreCase("PHP")) {

            if (origPositionString.contains("C style comment") || origPositionString.contains("line comment")
                    || origPositionString.contains("DOC_TEXT") || origPositionString.contains("DOC_TAG_NAME")) {

                if (origPositionString.contains("DOC_TAG_NAME") || origPositionString.contains("DOC_TEXT")) {
                    contribution.setDisplayPrefixCharIfPrefixFound(false);
                }

                contribution.enableContribution();
            }
        }

        if (languageId.equalsIgnoreCase("Kotlin")) {

            if (contextString.contains("KDoc") || origPositionString.contains("KDOC_TEXT")) {
                contribution.enableContribution();
            }

            if (origPositionString.contains("EOL_COMMENT")) {
                contribution.prefixRequired();
            }
        }

        // SQL
        if (languageId.equalsIgnoreCase("SQL") && origPositionString.contains("SQL_LINE_COMMENT")) {
            contribution.prefixRequired();
        }

        if (origPositionString.contains("END_OF_LINE_COMMENT") || origPositionString.contains("C_STYLE_COMMENT")
                || origPositionString.contains("XML_COMMENT_CHARACTERS")) {
            contribution.prefixRequired();
        }

        return contribution;
    }
}
