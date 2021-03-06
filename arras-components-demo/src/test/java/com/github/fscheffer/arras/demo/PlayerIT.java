package com.github.fscheffer.arras.demo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.fscheffer.arras.test.ArrasTestCase;

public class PlayerIT extends ArrasTestCase {

    @BeforeMethod
    public void before() {
        open("/PlayerDemo");
    }

    @Test
    public void testVideoPlayer() {

        // for debugging, show the complete video player
        moveTo("#video");

        click("#video .vjs-big-play-button");

        // wait for the click
        waitUntil(classesPresent("#video > div", "vjs-playing"));

        // wait until the video is loaded
        waitUntil(classesNotPresent("#video > div", "vjs-waiting"));

        // pause the video and bring up the controls in case they auto-hide
        click("#video video");

        waitUntil(visible("#video .vjs-control-bar"));

        if (!isLive()) {

            waitUntil(containsText("#video .vjs-duration-display", "0:33"));
        }
    }

    @Test
    public void testAudioPlayer() {

        click("#audio .vjs-play-control");

        waitUntil(classesPresent("#audio > div", "vjs-playing"));

        if (!isLive()) {
            waitUntil(containsText("#audio .vjs-duration-display", "3:29"));
        }
    }

    protected boolean isLive() {
        return element(".vjs-live-display").isDisplayed();
    }
}
