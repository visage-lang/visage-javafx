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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.visage.runtime.RuntimeProvider;
import org.visage.runtime.TypeInfo;
import org.visage.runtime.VisageExit;
import org.visage.runtime.sequence.Sequences;

/**
 * @author Stephen Chin <steveonjava@gmail.com>
 */
public class VFXRuntimeProvider extends Application implements RuntimeProvider {
    private static Method entryPoint;
    private static Object result;
    private static Stage primaryStage;
    private static Boolean gotStage;
    
    public static Stage getPrimaryStageOnce() {
        if (gotStage) return null;
        gotStage = true;
        return primaryStage;
    }

    public boolean usesRuntimeLibrary(Class type) {
        return true;
    }

    public Object run(Method entryPoint, String... args) throws Throwable {
        this.entryPoint = entryPoint;
        launch(VFXRuntimeProvider.class, args);
        return result;
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            this.primaryStage = stage;
            gotStage = false;
            result = entryPoint.invoke(null, Sequences.make(TypeInfo.String, getParameters().getRaw()));
            primaryStage.show();
        } catch (InvocationTargetException ite) {
            Throwable cause = ite.getCause();
            // todo - check what really gets thrown...
            if (!(cause instanceof VisageExit)) { // explicit exit
                if (cause instanceof Exception) {
                    throw (Exception) cause;
                } else {
                    throw new Exception(cause);
                }
            }
        }
    }
    
    public void deferAction(Runnable r) {
        Platform.runLater(r);
    }

    public void exit() {
        Platform.exit();
    }
}
