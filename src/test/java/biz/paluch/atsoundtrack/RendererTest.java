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

package biz.paluch.atsoundtrack;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import biz.paluch.atsoundtrack.settings.AtSoundtrackSettings;
import biz.paluch.atsoundtrack.settings.Parentheses;

/**
 * @author Mark Paluch
 */
public class RendererTest {

    @Test
    public void shouldRenderStreamTitle() throws Exception {

        AtSoundtrackSettings settings = new AtSoundtrackSettings();

        Map<AtSoundtrackElement, String> input = new HashMap<AtSoundtrackElement, String>();
        input.put(AtSoundtrackElement.STREAM_TITLE, "stream");
        input.put(AtSoundtrackElement.ARTIST, "artist");
        input.put(AtSoundtrackElement.TITLE, "track");

        String result = Renderer.render(input, settings);

        assertThat(result).isEqualTo("stream");
    }

    @Test
    public void shouldRenderArtistTrack() throws Exception {

        AtSoundtrackSettings settings = new AtSoundtrackSettings();

        Map<AtSoundtrackElement, String> input = new HashMap<AtSoundtrackElement, String>();
        input.put(AtSoundtrackElement.ARTIST, "artist");
        input.put(AtSoundtrackElement.TITLE, "track");

        String result = Renderer.render(input, settings);

        assertThat(result).isEqualTo("track - artist");
    }

    @Test
    public void shouldRenderArtistTrackWithParentheses() throws Exception {

        AtSoundtrackSettings settings = new AtSoundtrackSettings();
        settings.getParentheses().put(AtSoundtrackElement.ARTIST, Parentheses.BRACKET);
        settings.getParentheses().put(AtSoundtrackElement.TITLE, Parentheses.CURLY);

        Map<AtSoundtrackElement, String> input = new HashMap<AtSoundtrackElement, String>();
        input.put(AtSoundtrackElement.ARTIST, "artist");
        input.put(AtSoundtrackElement.TITLE, "track");

        String result = Renderer.render(input, settings);

        assertThat(result).isEqualTo("{track} - [artist]");
    }

    @Test
    public void shouldRenderArtistTrackWithParentheses2() throws Exception {

        AtSoundtrackSettings settings = new AtSoundtrackSettings();
        settings.getParentheses().put(AtSoundtrackElement.ARTIST, Parentheses.ROUND);
        settings.getParentheses().put(AtSoundtrackElement.TITLE, Parentheses.TRIANGLE);

        Map<AtSoundtrackElement, String> input = new HashMap<AtSoundtrackElement, String>();
        input.put(AtSoundtrackElement.ARTIST, "artist");
        input.put(AtSoundtrackElement.TITLE, "track");

        String result = Renderer.render(input, settings);

        assertThat(result).isEqualTo("<track> - (artist)");
    }

    @Test
    public void shouldRenderArtistWithoutTrack() throws Exception {

        AtSoundtrackSettings settings = new AtSoundtrackSettings();
        settings.setContent("${title} ${artist}");
        settings.getParentheses().put(AtSoundtrackElement.ARTIST, Parentheses.TRIANGLE);
        settings.getParentheses().put(AtSoundtrackElement.TITLE, Parentheses.ROUND);

        Map<AtSoundtrackElement, String> input = new HashMap<AtSoundtrackElement, String>();
        input.put(AtSoundtrackElement.ARTIST, "artist");

        String result = Renderer.render(input, settings);

        assertThat(result).isEqualTo("<artist>");
    }

    @Test
    public void shouldRenderArtistWithSpecialChars() throws Exception {

        AtSoundtrackSettings settings = new AtSoundtrackSettings();
        settings.setContent("${artist}");
        settings.getParentheses().put(AtSoundtrackElement.ARTIST, Parentheses.TRIANGLE);
        settings.getParentheses().put(AtSoundtrackElement.TITLE, Parentheses.ROUND);

        Map<AtSoundtrackElement, String> input = new HashMap<AtSoundtrackElement, String>();
        input.put(AtSoundtrackElement.ARTIST, "art $1 \\.*;.-¢¢[]()");

        String result = Renderer.render(input, settings);

        assertThat(result).isEqualTo("<art $1 .*;.-¢¢[]()>");
    }
}