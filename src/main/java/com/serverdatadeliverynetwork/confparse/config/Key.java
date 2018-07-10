/* 
 * Copyright (C) 2018 Aayush Atharva
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.serverdatadeliverynetwork.confparse.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Aayush Atharva
 */
public class Key {

    /**
     * The KeyName of the key.
     */
    private String KeyName;

    /**
     * The value of the key.
     */
    private List<Value> KeyValues = new ArrayList<>();

    /**
     * The current index for the next method.
     */
    private int index = -1;

    /**
     * Creates a new key with the given KeyName.
     *
     * @param keyName The KeyName of the key.
     */
    public Key(String keyName) {
        this.KeyName = keyName;
    }

    /**
     * Adds a value to this key.
     *
     * @param value The value.
     */
    public void addValue(Value value) {
        KeyValues.add(value);
    }
  
    /**
     * Returns the KeyName of the key.
     *
     * @return The KeyName of the key.
     */
    public String getName() {
        return KeyName;
    }

    /**
     * Returns the value at the given index.
     *
     * @param index The index of the value.
     * @return The value at the index.
     */
    public Value getValue(int index) {
        return KeyValues.get(index);
    }

    /**
     * Returns whether this key has KeyValues.
     *
     * @return True whether this key has KeyValues.
     */
    public boolean hasValues() {
        return !KeyValues.isEmpty();
    }

    /**
     * Returns the next value from this key. If the maximum index is reached, it
     * will start at 0 (the first value) again.
     *
     * @return The next value of this key from the current index.
     */
    public Value next() {
        index++;

        if (index == KeyValues.size()) {
            index = 0;
        }

        return KeyValues.get(index);
    }

    /**
     * Gets the next value and returns it as a string.
     *
     * @return The value as a string.
     */
    public String nextString() {
        return next().asString();
    }

    /**
     * Gets the next value and returns it as an integer.
     *
     * @return The value as an integer.
     */
    public int nextInt() {
        return next().asInt();
    }

    /**
     * Gets the next value and returns it as a long.
     *
     * @return The value as a long.
     */
    public long nextLong() {
        return next().asLong();
    }

    /**
     * Gets the next value and returns it as a double.
     *
     * @return The value as a double.
     */
    public double nextDouble() {
        return next().asDouble();
    }

    /**
     * Gets the next value and returns it as a float.
     *
     * @return The value as a float.
     */
    public float nextFloat() {
        return next().asFloat();
    }

    /**
     * Gets the next value and returns it as a boolean.
     *
     * @return The value as a boolean.
     */
    public boolean nextBoolean() {
        return next().asBoolean();
    }

    /**
     * Returns the KeyValues as an unmodifiable list.
     *
     * @return The KeyValues as an unmodifiable list.
     */
    public List<Value> getValues() {
        return Collections.unmodifiableList(KeyValues);
    }
}
