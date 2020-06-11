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

import biz.paluch.atsoundtrack.AtSoundtrackComponent;
import biz.paluch.atsoundtrack.AtSoundtrackElement;
import com.intellij.openapi.options.Configurable;
import com.intellij.ui.DocumentAdapter;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;

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
	private JPanel panel;

    private final AtSoundtrackSettings initialSettings;
    private final AtSoundtrackSettings workingset;
    private boolean muteEvents = false;

    public AtSoundtrackConfigurationForm(AtSoundtrackSettings initialSettings) {
		this.initialSettings = initialSettings;
		this.workingset = initialSettings.clone();

		this.panel.addAncestorListener(new AncestorListener() {
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

		this.content.getDocument().addDocumentListener(new DocumentAdapter() {
			@Override
			protected void textChanged(DocumentEvent e) {
				if (AtSoundtrackConfigurationForm.this.muteEvents) {
					return;
				}
				AtSoundtrackConfigurationForm.this.workingset
						.setContent(AtSoundtrackConfigurationForm.this.content.getText());
				updateExample();
			}
		});

		ChangeListener changeListener = e -> {
			if (AtSoundtrackConfigurationForm.this.muteEvents) {
				return;
			}
			toWorkingset();
			updateExample();
		};

		this.iTunesCheckBox.addChangeListener(changeListener);
		this.spotifyCheckBox.addChangeListener(changeListener);

		this.titleBracketsRadioButton.addChangeListener(changeListener);
		this.titleCurlyRadioButton.addChangeListener(changeListener);
		this.titleNoneRadioButton.addChangeListener(changeListener);
		this.titleRoundRadioButton.addChangeListener(changeListener);

		this.artistBracketsRadioButton.addChangeListener(changeListener);
		this.artistCurlyRadioButton.addChangeListener(changeListener);
		this.artistNoneRadioButton.addChangeListener(changeListener);
		this.artistRoundRadioButton.addChangeListener(changeListener);
		initFromSettings();
	}

    private void updateExample() {

		String renderedSoundtrack = biz.paluch.atsoundtrack.Renderer.render(AtSoundtrackComponent.getSoundtrack(), this.workingset);

        if (!renderedSoundtrack.trim().isEmpty()) {
			this.example.setText(String.format("@soundtrack %s", renderedSoundtrack));
		} else {
			this.example.setText("Cannot provide @soundtrack");
		}

    }

    private void updateComponents() {
		setData(this.workingset);
    }

    private void setData(AtSoundtrackSettings data) {

        try {
			this.muteEvents = true;
			setParentheses(data.getParentheses().get(AtSoundtrackElement.ARTIST), this.artistNoneRadioButton, this.artistRoundRadioButton,
					this.artistCurlyRadioButton, this.artistBracketsRadioButton);
			setParentheses(data.getParentheses().get(AtSoundtrackElement.STREAM_TITLE), this.titleNoneRadioButton,
					this.titleRoundRadioButton, this.titleCurlyRadioButton, this.titleBracketsRadioButton);

			this.content.setText(data.getContent());
			this.iTunesCheckBox.setSelected(data.isITunes());
			this.spotifyCheckBox.setSelected(data.isSpotify());
		} finally {
			this.muteEvents = false;
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
		this.workingset.apply(this.initialSettings);
		setData(this.workingset);
		updateExample();
	}

    public void toWorkingset() {

		Parentheses titleParentheses = toParentheses(this.titleNoneRadioButton, this.titleRoundRadioButton, this.titleCurlyRadioButton,
				this.titleBracketsRadioButton);
		this.workingset.getParentheses().put(AtSoundtrackElement.TITLE, titleParentheses);

		Parentheses artistParentheses = toParentheses(this.artistNoneRadioButton, this.artistRoundRadioButton, this.artistCurlyRadioButton,
				this.artistBracketsRadioButton);
		this.workingset.getParentheses().put(AtSoundtrackElement.ARTIST, artistParentheses);

		this.workingset.setContent(this.content.getText());
		this.workingset.setSpotify(this.spotifyCheckBox.isSelected());
		this.workingset.setITunes(this.iTunesCheckBox.isSelected());

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
		return !this.workingset.equals(this.initialSettings);
	}

	public void apply() {
		this.initialSettings.apply(this.workingset);
	}

	public JPanel getPanel() {
		return this.panel;
	}
}
