package biz.paluch.atsoundtrack;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.jetbrains.annotations.NotNull;

import biz.paluch.atsoundtrack.itunes.ITunesOverAppleScriptEngine;
import biz.paluch.atsoundtrack.itunes.ITunesOverAppleScriptOSAScript;
import biz.paluch.atsoundtrack.spotify.SpotifyOverAppleScriptEngine;
import biz.paluch.atsoundtrack.spotify.SpotifyOverAppleScriptOSAScript;

import com.intellij.openapi.components.ApplicationComponent;

/**
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 * @since 13.05.15 10:53
 */
public class AtSoundtrack implements ApplicationComponent {

    private static Thread backgroundTrackGathering;
    private static BackgroundTrackGathering runnable;

    public static String getName() {
        if (runnable != null) {
            return runnable.getName();
        }
        return null;
    }

    public AtSoundtrack() {
    }

    public void initComponent() {
        runnable = new BackgroundTrackGathering();
        backgroundTrackGathering = new Thread(runnable, "backgroundTrackGathering");
        backgroundTrackGathering.setDaemon(true);
        backgroundTrackGathering.start();
    }

    public void disposeComponent() {
        runnable.stop();
    }

    @NotNull
    public String getComponentName() {
        return "AtSoundtrack";
    }

    private static class BackgroundTrackGathering implements Runnable {

        private AtomicReference<String> name = new AtomicReference<String>("");
        private AtomicBoolean atomicBoolean = new AtomicBoolean(true);

        @Override
        public void run() {

            List<SoundTrackProvider> providers = getProviders();

            while (atomicBoolean.get()) {

                try {
                    for (SoundTrackProvider provider : providers) {
                        if (provider.isApplicable()) {
                            name.set(provider.getName());
                            break;
                        }
                    }
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }

        private List<SoundTrackProvider> getProviders() {

            return Arrays.asList(new ITunesOverAppleScriptEngine(), new ITunesOverAppleScriptOSAScript(),
                    new SpotifyOverAppleScriptEngine(), new SpotifyOverAppleScriptOSAScript());
        }

        public String getName() {
            return name.get();
        }

        public void stop() {
            atomicBoolean.set(false);
        }
    }
}
