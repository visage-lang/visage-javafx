/*
 * Copyright (c) 2010-2011, Visage Project
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name Visage nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.visage.javafx.animation;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.util.Duration;
import org.visage.animation.Clip;
import org.visage.animation.Interpolator;
import org.visage.animation.TimingTarget;

/**
 * @author Stephen Chin <steveonjava@gmail.com>
 */
public class VFXClip implements Clip {
    private final long duration;
    private final TimingTarget target;
    private int resolution;
    Timeline timeline;
    DoubleProperty value = new SimpleDoubleProperty() {
        @Override
        protected void invalidated() {
            target.timingEvent(value.floatValue(), (long) timeline.getCurrentTime().toMillis());
            super.invalidated();
        }
    };

    VFXClip(long duration, TimingTarget target) {
        this.duration = duration;
        this.target = target;
    }

    public boolean isRunning() {
        return timeline.getStatus() == Animation.Status.RUNNING;
    }

    public void pause() {
        timeline.pause();
        target.pause();
    }

    public void resume() {
        target.resume();
        timeline.play();
    }

    public void setInterpolator(Interpolator interpolator) {
        // right now we will always get a linear interpolator, so it is safe to ignore
    }

    long startTime;
    public void start() {
        if (timeline == null) {
            KeyFrame kf = new KeyFrame(duration == INDEFINITE ? Duration.millis(Double.MAX_VALUE) : Duration.millis(duration), new KeyValue(value, 1));
            if (resolution > 0) {
                double framerate = 1000d / resolution;
                timeline = new Timeline(framerate, kf);
            } else {
                timeline = new Timeline(kf);
            }
        }
        target.begin();
        timeline.playFromStart();
    }

    public void stop() {
        timeline.stop();
        target.end();
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }
}
