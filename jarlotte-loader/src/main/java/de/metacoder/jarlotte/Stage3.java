/* Copyright 2015 Felix Becker

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. */

package de.metacoder.jarlotte;

import java.io.File;

public class Stage3 {

    private Class<?> initializerClass;
    private Object initializer;

    public void run(File webappDir, String initializerClassName, ClassLoader jarlotteClassLoader) throws Exception {
        /*
        we currently don't know the initializer interface here - todo extract it to the jar classes
         */

        System.out.println("Loading and starting initializer " + initializerClassName);
        initializerClass = jarlotteClassLoader.loadClass(initializerClassName);
        initializer = initializerClass.newInstance();
        initializerClass.getDeclaredMethod("initialize", File.class).invoke(initializer, webappDir);
    }

    public void stop() {
        if(initializerClass != null) {
            try {
                initializerClass.getDeclaredMethod("stop").invoke(initializer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
