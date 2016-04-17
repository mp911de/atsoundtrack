package biz.paluch.atsoundtrack;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import biz.paluch.atsoundtrack.settings.AtSoundtrackSettings;
import biz.paluch.atsoundtrack.settings.Parentheses;

/**
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
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