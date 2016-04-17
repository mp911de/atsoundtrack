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

package biz.paluch.atsoundtrack.completion;

import lombok.Data;

/**
 * Contribution configuration.
 * 
 * @author Mark Paluch
 */
@Data
public class AtSoundtrackContribution {

    private boolean prefixRequired;
    private boolean shouldContribute = false;
    private boolean displayPrefixCharIfPrefixFound = true;
    private char prefixChar = '@';

    public void enableContribution() {
        shouldContribute = true;
    }

    public boolean shouldContribute() {
        return shouldContribute;
    }

    public void prefixRequired() {
        prefixRequired = true;

    }
}
