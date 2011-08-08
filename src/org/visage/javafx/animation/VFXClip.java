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

import com.sun.visage.animation.Clip;
import com.sun.visage.animation.Interpolator;
import com.sun.visage.animation.TimingTarget;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Stephen Chin <steveonjava@gmail.com>
 */
public class VFXClip implements Clip {
    private final long duration;
    private final TimingTarget target;
    private Interpolator interpolator;
    private int resolution;
    private boolean running;
    Timer timer = new Timer();

    VFXClip(long duration, TimingTarget target) {
        this.duration = duration;
        this.target = target;
    }

    public boolean isRunning() {
        return running;
    }

    public void pause() {
        running = false;
        target.pause();
    }

    public void resume() {
        running = true;
        target.resume();
    }

    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    long startTime;
    public void start() {
        running = true;
        target.begin();
        startTime = System.currentTimeMillis();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                target.timingEvent(0, System.currentTimeMillis() - startTime);
            }
        }, 0, 1000 / 60);
    }

    public void stop() {
        running = false;
        timer.cancel();
        target.end();
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }
    
}
