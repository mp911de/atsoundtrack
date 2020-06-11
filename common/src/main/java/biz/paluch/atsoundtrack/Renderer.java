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

import biz.paluch.atsoundtrack.settings.AtSoundtrackSettings;
import biz.paluch.atsoundtrack.settings.Parentheses;
import com.intellij.openapi.util.text.StringUtil;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * Soundtrack renderer.
 *
 * @author Mark Paluch
 */
public class Renderer {

    private final static String Q_ARTIST = Pattern.quote("${artist}");
    private final static String Q_TITLE = Pattern.quote("${title}");

    /**
     * Render soundtrack.
     *
     * @param map
     * @param settings
     * @return
     */
    public static String render(Map<AtSoundtrackElement, String> map, AtSoundtrackSettings settings) {

        if (map == null || map.isEmpty()) {
            return "";
        }

        if (map.containsKey(AtSoundtrackElement.STREAM_TITLE)) {
            return map.get(AtSoundtrackElement.STREAM_TITLE).trim();
        }

        try {
            String result;
            String artistContent = getContent(map, settings, AtSoundtrackElement.ARTIST);
            String trackContent = getContent(map, settings, AtSoundtrackElement.TITLE);

            result = settings.getContent();
            if (artistContent != null) {
                result = result.replaceAll(Q_ARTIST, artistContent.replaceAll("\\$", "\\\\\\$"));
            } else {
                result = result.replaceAll(Q_ARTIST, "");
            }

            if (trackContent != null) {
                result = result.replaceAll(Q_TITLE, trackContent.replaceAll("\\$", "\\\\\\$"));
            } else {
                result = result.replaceAll(Q_TITLE, "");
            }
            return result.trim();

        } catch (Exception e) {
            return "";
        }
    }

    private static String getContent(Map<AtSoundtrackElement, String> map, AtSoundtrackSettings settings,
            AtSoundtrackElement element) {

        if (!map.containsKey(element)) {
            return null;
        }
        String content = map.get(element).trim();

        if (StringUtil.isNotEmpty(content)) {
            if (settings.getParentheses().containsKey(element)) {
                Parentheses parentheses = settings.getParentheses().get(element);

                switch (parentheses) {
                    case BRACKET:
                        content = String.format("[%s]", content);
                        break;
                    case CURLY:
                        content = String.format("{%s}", content);
                        break;
                    case TRIANGLE:
                        content = String.format("<%s>", content);
                        break;
                    case ROUND:
                        content = String.format("(%s)", content);
                        break;

                }
            }
        }

        return content;
    }
}
