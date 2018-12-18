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
package com.aayushatharva.confparse.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Aayush Atharva
 */
public class Header {

    /**
     * The HeaderName of the header.
     */
    private String HeaderName;

    /**
     * The keys of the header.
     */
    private Map<String, Key> HeaderKeys = new HashMap<>();

    /**
     * Creates a new header with the given HeaderName.
     *
     * @param headerName The HeaderName of the header.
     */
    public Header(String headerName) {
        this.HeaderName = headerName;
    }

    /**
     * Adds a key to this header.
     *
     * @param key The key.
     */
    public void addKey(Key key) {
        HeaderKeys.put(key.getName(), key);
    }

    /**
     * Returns whether the given key exists.
     *
     * @param key The key to check.
     * @return True or false whether the given check exists or not.
     */
    public boolean hasKey(String key) {
        return HeaderKeys.containsKey(key);
    }

    /**
     * Returns the key with the given HeaderName.
     *
     * @param key The HeaderName of the key.
     * @return The key object.
     */
    public Key getKey(String key) {
        return HeaderKeys.get(key);
    }


    /**
     * Returns the HeaderName of the key.
     *
     * @return The HeaderName of the key.
     */
    public String getName() {
        return HeaderName;
    }

    /**
     * Returns the keys as an unmodifiable list.
     *
     * @return The keys as an unmodifiable list.
     */
    public List<Key> getKeys() {
        return Collections.unmodifiableList(new ArrayList<>(HeaderKeys.values()));
    }
    
    public List<Key> getKeysList() {

        for (Key k : HeaderKeys.values()) {
            System.out.println(k.getName());
        }
   
        return new ArrayList<>(HeaderKeys.values());
    }
    
   
}
