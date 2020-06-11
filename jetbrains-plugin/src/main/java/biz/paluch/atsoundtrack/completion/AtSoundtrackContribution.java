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

import java.util.Objects;

/**
 * Contribution configuration.
 *
 * @author Mark Paluch
 */
public class AtSoundtrackContribution {

    private boolean prefixRequired;
    private boolean shouldContribute = false;
    private boolean displayPrefixCharIfPrefixFound = true;
    private char prefixChar = '@';

    public AtSoundtrackContribution() {
    }

    public void enableContribution() {
        this.shouldContribute = true;
    }

    public boolean shouldContribute() {
        return this.shouldContribute;
    }

    public void prefixRequired() {
        this.prefixRequired = true;
    }

    public boolean isPrefixRequired() {
        return this.prefixRequired;
    }

    public boolean isShouldContribute() {
        return this.shouldContribute;
    }

    public boolean isDisplayPrefixCharIfPrefixFound() {
        return this.displayPrefixCharIfPrefixFound;
    }

    public char getPrefixChar() {
        return this.prefixChar;
    }

    public void setPrefixRequired(boolean prefixRequired) {
        this.prefixRequired = prefixRequired;
    }

    public void setShouldContribute(boolean shouldContribute) {
        this.shouldContribute = shouldContribute;
    }

    public void setDisplayPrefixCharIfPrefixFound(boolean displayPrefixCharIfPrefixFound) {
        this.displayPrefixCharIfPrefixFound = displayPrefixCharIfPrefixFound;
    }

    public void setPrefixChar(char prefixChar) {
        this.prefixChar = prefixChar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AtSoundtrackContribution)) {
            return false;
        }
        AtSoundtrackContribution that = (AtSoundtrackContribution) o;
        return this.prefixRequired == that.prefixRequired &&
                this.shouldContribute == that.shouldContribute &&
                this.displayPrefixCharIfPrefixFound == that.displayPrefixCharIfPrefixFound &&
                this.prefixChar == that.prefixChar;
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(this.prefixRequired, this.shouldContribute, this.displayPrefixCharIfPrefixFound, this.prefixChar);
    }


    public String toString() {
        return "AtSoundtrackContribution(prefixRequired=" + this
                .isPrefixRequired() + ", shouldContribute=" + this
                .isShouldContribute() + ", displayPrefixCharIfPrefixFound=" + this
                .isDisplayPrefixCharIfPrefixFound() + ", prefixChar=" + this
                .getPrefixChar() + ")";
    }
}
