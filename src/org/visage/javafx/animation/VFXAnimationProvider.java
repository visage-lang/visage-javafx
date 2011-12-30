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

import org.visage.animation.*;

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
            return new Interpolator() {
                public float interpolate(float f) {
                    return (float) javafx.animation.Interpolator.EASE_BOTH.interpolate(0d, 1d, f);
                }
            };
        }

        public Interpolator getEasingInstance(final float acceleration, final float deceleration) {
            return new Interpolator() {
                javafx.animation.Interpolator easing;
                {
                    // approximation based off the fact that we will always be passed in factors of .2 for acceleration and deceleration
                    if (acceleration >= .1) {
                        if (deceleration >= .1) {
                            easing = javafx.animation.Interpolator.EASE_BOTH;
                        } else {
                            easing = javafx.animation.Interpolator.EASE_IN;
                        }
                    } else {
                        if (deceleration >= .1) {
                            easing = javafx.animation.Interpolator.EASE_OUT;
                        } else {
                            easing = javafx.animation.Interpolator.EASE_BOTH;
                        }
                    }
                }
                public float interpolate(float f) {
                    return (float) easing.interpolate(0d, 1d, f);
                }
            };
        }

        public Interpolator getLinearInstance() {
            return new Interpolator() {
                public float interpolate(float f) {
                    return f;
                }
            };
        }

        public Interpolator getSplineInstance(final float x1, final float y1, final float x2, final float y2) {
            return new Interpolator() {
                javafx.animation.Interpolator spline = javafx.animation.Interpolator.SPLINE(x1, y1, x2, y2);
                public float interpolate(float f) {
                    return (float) spline.interpolate(0d, 1d, f);
                }
            };
        }
        
    }
}
