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

import com.sun.visage.animation.AnimationProvider;
import com.sun.visage.animation.Clip;
import com.sun.visage.animation.ClipFactory;
import com.sun.visage.animation.Interpolator;
import com.sun.visage.animation.InterpolatorFactory;
import com.sun.visage.animation.TimingTarget;

/**
 * @author Stephen Chin <steveonjava@gmail.com>
 */
public class VFXAnimationProvider implements AnimationProvider {
    private VFXClipFactory vFXClipFactory = new VFXClipFactory();
    private VFXInterpolatorFactory vFXInterpolatorFactory = new VFXInterpolatorFactory();

    public ClipFactory getClipFactory() {
        return vFXClipFactory;
    }

    public InterpolatorFactory getInterpolatorFactory() {
        return vFXInterpolatorFactory;
    }

    public boolean hasActiveAnimation() {
        return false;
    }
    
    public static class VFXClipFactory implements ClipFactory {
        public Clip create(long duration, TimingTarget target) {
            return new VFXClip(duration, target);
        }
    }
    
    public static class VFXInterpolatorFactory implements InterpolatorFactory {

        public Interpolator getDiscreteInstance() {
            return new Interpolator() {
                public float interpolate(float f) {
                    if (f == 1) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            };
        }

        public Interpolator getEasingInstance() {
            return null;
//            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Interpolator getEasingInstance(float acceleration, float deceleration) {
            return null;
//            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Interpolator getLinearInstance() {
            return new Interpolator() {
                public float interpolate(float f) {
                    return f;
                }
            };
        }

        public Interpolator getSplineInstance(float x1, float y1, float x2, float y2) {
            return null;
//            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    }
}
