package biz.paluch.atsoundtrack;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.jetbrains.annotations.NotNull;

import biz.paluch.atsoundtrack.itunes.ITunesOverAppleScriptEngine;
import biz.paluch.atsoundtrack.itunes.ITunesOverAppleScriptOSAScript;
import biz.paluch.atsoundtrack.settings.AtSoundtrackSettings;
import biz.paluch.atsoundtrack.spotify.SpotifyOverAppleScriptEngine;
import biz.paluch.atsoundtrack.spotify.SpotifyOverAppleScriptOSAScript;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.diagnostic.Logger;

/**
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 * @since 13.05.15 10:53
 */
public class AtSoundtrack implements ApplicationComponent {

    private static Thread atSoundtrackThread;
    private static AtSoundtrackThread runnable;
    private AtSoundtrackSettings atSoundtrackSettings;

    public static Map<AtSoundtrackElement, String> getSoundtrack() {
        if (runnable != null) {
            return runnable.getSoundtrack();
        }
        return Collections.emptyMap();
    }

    public AtSoundtrack() {
    }

    public void initComponent() {

        atSoundtrackSettings = AtSoundtrackSettings.getInstance();
        runnable = new AtSoundtrackThread(atSoundtrackSettings);
        atSoundtrackThread = new Thread(runnable, "AtSoundtrackThread");
        atSoundtrackThread.setDaemon(true);
        atSoundtrackThread.start();
    }

    public void disposeComponent() {
        runnable.stop();
    }

    @NotNull
    public String getComponentName() {
        return "AtSoundtrack";
    }

    private static class AtSoundtrackThread implements Runnable {

        private static Logger logger = Logger.getInstance(AtSoundtrackThread.class);
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
                    logger.warn(e);
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
