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
        shouldContribute = true;
    }

    public boolean shouldContribute() {
        return shouldContribute;
    }

    public void prefixRequired() {
        prefixRequired = true;
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

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AtSoundtrackContribution)) {
            return false;
        }
        final AtSoundtrackContribution other = (AtSoundtrackContribution) o;
        if (!other.canEqual((Object) this)) {
            return false;
        }
        if (this.isPrefixRequired() != other.isPrefixRequired()) {
            return false;
        }
        if (this.isShouldContribute() != other.isShouldContribute()) {
            return false;
        }
        if (this.isDisplayPrefixCharIfPrefixFound() != other
                .isDisplayPrefixCharIfPrefixFound()) {
            return false;
        }
        if (this.getPrefixChar() != other.getPrefixChar()) {
            return false;
        }
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AtSoundtrackContribution;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + (this.isPrefixRequired() ? 79 : 97);
        result = result * PRIME + (this.isShouldContribute() ? 79 : 97);
        result = result * PRIME + (this.isDisplayPrefixCharIfPrefixFound() ? 79 : 97);
        result = result * PRIME + this.getPrefixChar();
        return result;
    }

    public String toString() {
        return "AtSoundtrackContribution(prefixRequired=" + this
                .isPrefixRequired() + ", shouldContribute=" + this
                .isShouldContribute() + ", displayPrefixCharIfPrefixFound=" + this
                .isDisplayPrefixCharIfPrefixFound() + ", prefixChar=" + this
                .getPrefixChar() + ")";
    }
}
