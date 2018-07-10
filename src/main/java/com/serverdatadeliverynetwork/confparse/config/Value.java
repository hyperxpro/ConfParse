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

/**
 *
 * @author Aayush Atharva
 */
public class Value {

    /**
     * The value of the value.
     */
    private String ValueName;

    /**
     * Creates a new value with the given value.
     *
     * @param valueName The value.
     */
    public Value(String valueName) {
        this.ValueName = valueName;
    }

    /**
     * Returns the value as a string.
     *
     * @return The value as a string.
     */
    public String asString() {
        return ValueName;
    }
   
    /**
     * Returns the value as an int.
     *
     * @return The value as an int.
     */
    public int asInt() {
        return Integer.parseInt(ValueName);
    }

    /**
     * Returns the value as a long.
     *
     * @return The value as a long.
     */
    public long asLong() {
        return Long.parseLong(ValueName);
    }

    /**
     * Returns the value as a double.
     *
     * @return The value as a double.
     */
    public double asDouble() {
        return Double.parseDouble(ValueName);
    }

    /**
     * Returns the value as a float.
     *
     * @return The value as a float.
     */
    public float asFloat() {
        return Float.parseFloat(ValueName);
    }

    /**
     * Returns the value as a boolean.
     *
     * @return The value as a boolean.
     */
    public boolean asBoolean() {
        return Boolean.parseBoolean(ValueName);
    }

    @Override
    public String toString() {
        return ValueName;
    }
}
