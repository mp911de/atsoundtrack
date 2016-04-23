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

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import lombok.Setter;
import biz.paluch.atsoundtrack.itunes.ITunesOverAppleScriptEngine;
import biz.paluch.atsoundtrack.itunes.ITunesOverAppleScriptOSAScript;
import biz.paluch.atsoundtrack.logging.InternalLogger;
import biz.paluch.atsoundtrack.logging.InternalLoggerFactory;
import biz.paluch.atsoundtrack.settings.AtSoundtrackSettings;
import biz.paluch.atsoundtrack.spotify.SpotifyOverAppleScriptEngine;
import biz.paluch.atsoundtrack.spotify.SpotifyOverAppleScriptOSAScript;

/**
 * @author Mark Paluch
 * @since 13.05.15 10:53
 */
public class AtSoundtrack {

    private final static InternalLogger log = InternalLoggerFactory.getLogger(AtSoundtrack.class);
    private static Thread atSoundtrackThread;
    private static AtSoundtrackThread runnable;
    private @Setter AtSoundtrackSettings atSoundtrackSettings;

    public static Map<AtSoundtrackElement, String> getSoundtrack() {
        if (runnable != null) {
            return runnable.getSoundtrack();
        }
        return Collections.emptyMap();
    }

    public AtSoundtrack() {
    }

    public void initComponent() {

        log.debug("Create AtSoundtrackThread");
        runnable = new AtSoundtrackThread(atSoundtrackSettings);
        atSoundtrackThread = new Thread(runnable, "AtSoundtrackThread");
        atSoundtrackThread.setDaemon(true);
        atSoundtrackThread.start();
    }

    public void disposeComponent() {
        runnable.stop();
    }

    public String getComponentName() {
        return "AtSoundtrack";
    }

    private static class AtSoundtrackThread implements Runnable {

        private final AtSoundtrackSettings atSoundtrackSettings;
        private AtomicReference<Map<AtSoundtrackElement, String>> soundtrack = new AtomicReference<Map<AtSoundtrackElement, String>>(
                new HashMap<AtSoundtrackElement, String>());
        private AtomicBoolean atomicBoolean = new AtomicBoolean(true);

        public AtSoundtrackThread(AtSoundtrackSettings atSoundtrackSettings) {
            this.atSoundtrackSettings = atSoundtrackSettings;
        }

        @Override
        public void run() {

            List<SoundTrackProvider> providers = getProviders();

            while (atomicBoolean.get()) {

                boolean found = false;
                try {
                    for (SoundTrackProvider provider : providers) {
                        if (provider.isApplicable(atSoundtrackSettings)) {
                            soundtrack.set(provider.getSoundtrack());
                            found = true;
                            break;
                        }
                    }

                } catch (RuntimeException e) {
                    log.warn(e.getMessage(), e);
                }

                if (!found) {
                    soundtrack.set(new HashMap<AtSoundtrackElement, String>());
                }

                try {
                    Thread.sleep(atSoundtrackSettings.getSleepMs());
                } catch (InterruptedException e) {
                    return;
                }
            }
        }

        private List<SoundTrackProvider> getProviders() {

            if (atSoundtrackSettings.isPreferScriptEngine()) {
                return Arrays.asList(new ITunesOverAppleScriptEngine(), new ITunesOverAppleScriptOSAScript(),
                        new SpotifyOverAppleScriptEngine(), new SpotifyOverAppleScriptOSAScript());

            }

            return Arrays.asList(new ITunesOverAppleScriptOSAScript(), new SpotifyOverAppleScriptOSAScript());
        }

        public Map<AtSoundtrackElement, String> getSoundtrack() {
            return soundtrack.get();
        }

        public void stop() {
            atomicBoolean.set(false);
        }
    }
}
