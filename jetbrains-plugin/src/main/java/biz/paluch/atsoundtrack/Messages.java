/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package biz.paluch.atsoundtrack;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.ResourceBundle;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.PropertyKey;

import com.intellij.CommonBundle;

/**
 * @author Mark Paluch
 */
public class Messages {

    @NonNls
    private static final String BUNDLE_NAME = "biz.paluch.atsoundtrack.messages";

    private static Reference<ResourceBundle> bundle;

    private Messages() {
    }

    private static ResourceBundle getBundle() {
        ResourceBundle bundle = null;
        if (Messages.bundle != null) {
            bundle = Messages.bundle.get();
        }
        if (bundle == null) {
            bundle = ResourceBundle.getBundle(BUNDLE_NAME);
            Messages.bundle = new SoftReference<ResourceBundle>(bundle);
        }
        return bundle;
    }

    public static String message(@PropertyKey(resourceBundle = BUNDLE_NAME) String key, Object... params) {
        return CommonBundle.message(getBundle(), key, params);
    }

}