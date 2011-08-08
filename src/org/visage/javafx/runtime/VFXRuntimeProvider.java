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
package org.visage.javafx.runtime;

import com.sun.visage.runtime.FXExit;
import com.sun.visage.runtime.RuntimeProvider;
import com.sun.visage.runtime.TypeInfo;
import com.sun.visage.runtime.sequence.Sequences;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javafx.application.Application;
import javafx.application.Platform;
import visage.lang.FX;

/**
 * @author Stephen Chin <steveonjava@gmail.com>
 */
public class VFXRuntimeProvider implements RuntimeProvider {

    public boolean usesRuntimeLibrary(Class type) {
        return true;
    }

    public Object run(Method entryPoint, String... args) throws Throwable {
        try {
            return entryPoint.invoke(null, Sequences.make(TypeInfo.String, args));
        } catch (InvocationTargetException ite) {
            Throwable cause = ite.getCause();
            // todo - check what really gets thrown...
            if (cause instanceof FXExit) { // explicit exit
                return null;
            }
            throw cause;
        }
    }

    public void deferAction(Runnable r) {
        Platform.runLater(r);
    }

    public void exit() {
        Platform.exit();
    }
    
}
