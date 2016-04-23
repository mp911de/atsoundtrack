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

package biz.paluch.atsoundtrack.settings;

import javax.swing.*;
import javax.swing.event.*;

import biz.paluch.atsoundtrack.AtSoundtrackElement;
import lombok.Getter;
import biz.paluch.atsoundtrack.AtSoundtrackComponent;

import com.intellij.openapi.options.Configurable;
import com.intellij.ui.DocumentAdapter;

/**
 * @author Mark Paluch
 */
public class AtSoundtrackConfigurationForm implements Configurable.NoScroll {

    private JRadioButton titleNoneRadioButton;
    private JRadioButton titleRoundRadioButton;
    private JRadioButton titleCurlyRadioButton;
    private JRadioButton titleBracketsRadioButton;
    private JRadioButton artistNoneRadioButton;
    private JRadioButton artistRoundRadioButton;
    private JRadioButton artistCurlyRadioButton;
    private JRadioButton artistBracketsRadioButton;
    private JCheckBox iTunesCheckBox;
    private JCheckBox spotifyCheckBox;
    private JTextField content;
    private JLabel example;
    private @Getter JPanel panel;

    private final AtSoundtrackSettings initialSettings;
    private final AtSoundtrackSettings workingset;
    private boolean muteEvents = false;

    public AtSoundtrackConfigurationForm(AtSoundtrackSettings initialSettings) {
        this.initialSettings = initialSettings;
        this.workingset = initialSettings.clone();

        panel.addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                // Called when component becomes visible, to ensure that the
                // popups
                // are visible when the form is shown for the first time.
                updateComponents();
            }

            @Override
            public void ancestorRemoved(AncestorEvent event) {
            }

            @Override
            public void ancestorMoved(AncestorEvent event) {
            }
        });

        content.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(DocumentEvent e) {
                if (muteEvents) {
                    return;
                }
                workingset.setContent(content.getText());
                updateExample();
            }
        });

        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (muteEvents) {
                    return;
                }
                toWorkingset();
                updateExample();
            }
        };

        iTunesCheckBox.addChangeListener(changeListener);
        spotifyCheckBox.addChangeListener(changeListener);

        titleBracketsRadioButton.addChangeListener(changeListener);
        titleCurlyRadioButton.addChangeListener(changeListener);
        titleNoneRadioButton.addChangeListener(changeListener);
        titleRoundRadioButton.addChangeListener(changeListener);

        artistBracketsRadioButton.addChangeListener(changeListener);
        artistCurlyRadioButton.addChangeListener(changeListener);
        artistNoneRadioButton.addChangeListener(changeListener);
        artistRoundRadioButton.addChangeListener(changeListener);
        initFromSettings();
    }

    private void updateExample() {

        String renderedSoundtrack = biz.paluch.atsoundtrack.Renderer.render(AtSoundtrackComponent.getSoundtrack(), workingset);

        if (!renderedSoundtrack.trim().isEmpty()) {
            example.setText(String.format("@soundtrack %s", renderedSoundtrack));
        } else {
            example.setText("Cannot provide @soundtrack");
        }

    }

    private void updateComponents() {
        setData(workingset);
    }

    private void setData(AtSoundtrackSettings data) {

        try {
            muteEvents = true;
            setParentheses(data.getParentheses().get(AtSoundtrackElement.ARTIST), artistNoneRadioButton, artistRoundRadioButton,
                    artistCurlyRadioButton, artistBracketsRadioButton);
            setParentheses(data.getParentheses().get(AtSoundtrackElement.STREAM_TITLE), titleNoneRadioButton,
                    titleRoundRadioButton, titleCurlyRadioButton, titleBracketsRadioButton);

            content.setText(data.getContent());
            iTunesCheckBox.setSelected(data.isITunes());
            spotifyCheckBox.setSelected(data.isSpotify());
        } finally {
            muteEvents = false;
        }
    }

    private void setParentheses(Parentheses parentheses, JRadioButton none, JRadioButton round, JRadioButton curly,
            JRadioButton brackets) {

        if (parentheses == null) {
            parentheses = Parentheses.NONE;
        }

        switch (parentheses) {
            case NONE:
                none.getModel().setSelected(true);
                round.getModel().setSelected(false);
                curly.getModel().setSelected(false);
                brackets.getModel().setSelected(false);
                break;
            case ROUND:
                none.getModel().setSelected(false);
                round.getModel().setSelected(true);
                curly.getModel().setSelected(false);
                brackets.getModel().setSelected(false);
                break;
            case CURLY:
                none.getModel().setSelected(false);
                round.getModel().setSelected(false);
                curly.getModel().setSelected(true);
                brackets.getModel().setSelected(false);
                break;
            case BRACKET:
                none.getModel().setSelected(false);
                round.getModel().setSelected(false);
                curly.getModel().setSelected(false);
                brackets.getModel().setSelected(true);
                break;
        }

    }

    public void initFromSettings() {
        workingset.apply(initialSettings);
        setData(workingset);
        updateExample();
    }

    public void toWorkingset() {

        Parentheses titleParentheses = toParentheses(titleNoneRadioButton, titleRoundRadioButton, titleCurlyRadioButton,
                titleBracketsRadioButton);
        workingset.getParentheses().put(AtSoundtrackElement.TITLE, titleParentheses);

        Parentheses artistParentheses = toParentheses(artistNoneRadioButton, artistRoundRadioButton, artistCurlyRadioButton,
                artistBracketsRadioButton);
        workingset.getParentheses().put(AtSoundtrackElement.ARTIST, artistParentheses);

        workingset.setContent(this.content.getText());
        workingset.setSpotify(this.spotifyCheckBox.isSelected());
        workingset.setITunes(this.iTunesCheckBox.isSelected());

    }

    private Parentheses toParentheses(JRadioButton none, JRadioButton round, JRadioButton curly, JRadioButton brackets) {

        if (round.isSelected()) {
            return Parentheses.ROUND;
        }

        if (curly.isSelected()) {
            return Parentheses.CURLY;
        }

        if (brackets.isSelected()) {
            return Parentheses.BRACKET;
        }

        return Parentheses.NONE;
    }

    public boolean isModified() {
        return !workingset.equals(initialSettings);
    }

    public void apply() {
        initialSettings.apply(workingset);
    }
}
