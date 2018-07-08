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
package com.serverdatadeliverynetwork.confparse;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.serverdatadeliverynetwork.confparse.config.Header;
import com.serverdatadeliverynetwork.confparse.config.Key;
import com.serverdatadeliverynetwork.confparse.config.Value;
import com.serverdatadeliverynetwork.confparse.exceptions.ConfParseException;

/**
 *
 * @author Aayush Atharva
 */

public final class ConfParse {

    /**
     * Creates a new ConfParse config from the given file name or file path.
     *
     * @param FileName The file name or path.
     * @return New ConfParse Config
     * @throws ConfParseException If something went wrong during loading or parsing.
     */
    public static ConfParser from(String FileName) throws ConfParseException {
        return from(new File(FileName));
    }

    /**
     * Creates a new ConfParse config from the given path.
     *
     * @param FilePath The path.
     * @return A new ConfParse config.
     * @throws ConfParseException If something went wrong during loading or parsing.
     */
    public static ConfParser from(Path FilePath) throws ConfParseException {
        return from(FilePath.toFile());
    }

    /**
     * Creates a new ConfParse config from the given URI.
     *
     * Useful if you want to load a file in the resources.
     *
     * @param FileURI The URI.
     * @return A new ConfParse config.
     * @throws ConfParseException If something went wrong during loading or 
     * parsing.
     */
    public static ConfParser from(URI FileURI) throws ConfParseException {
        return from(new File(FileURI));
    }

    /**
     * Creates a new ConfParse config from the given file.
     *
     * @param file The file.
     * @return A new ConfParse config.
     * @throws ConfParseException If something went wrong during loading or parsing.
     */
    public static ConfParser from(File file) throws ConfParseException {
        return new ConfParser(file);
    }

     /**
     * Creates a new ConfParse config from the given file.
     *
     * @param URL The file URL.
     * @return A new ConfParse config.
     * @throws ConfParseException If something went wrong during loading or parsing.
     */
    public static ConfParser from(URL URL) throws ConfParseException {
        return new ConfParser(URL);
    }

    /**
     * A builder to add defaults.
     */
    public static class ConfParser {

        /**
         * The Config file.
         */
        private File File;

        /**
         * The Config File URL
         */
        private URL URL;

        /**
         * All default headers for the Config.
         */
        private Map<String, Header> Headers = new HashMap<>();

        /**
         * Creates a new builder for the given Config file.
         *
         * @param File The Config file.
         */
        public ConfParser(File File) {
            this.File = File;
        }

        /**
         * Creates a new builder for the given Config file URL.
         *
         * @param URL The config file URL.
         */
        public ConfParser(URL URL) {
            this.URL = URL;
        }

        /**
         * Adds a default header with the given key and the values.
         *
         * @param header The default header to add.
         * @param key The default key to add.
         * @param values The default values to add.
         * @return This ConfParse builder.
         */
        public ConfParser def(Header header, Key key, Value... values) {
            Header h = Headers.get(header.getName());
            
            if (h != null) {
                Key k = h.getKey(key.getName());
                if (k != null) {
                    for (Value value : values) {
                        k.addValue(value);
                    }
                } else {
                    
                    for (Value value : values) {
                        key.addValue(value);
                    }
                    
                    h.addKey(key);
                }
            } else {
                
                for (Value value : values) {
                    key.addValue(value);
                }
                
                header.addKey(key);

                Headers.put(header.getName(), header);
            }

            return this;
        }

        /**
         * Builds the ConfParse config from this builder based on File.
         *
         * @return The parsed ConfParse config.
         * @throws ConfParseException If something went wrong during the
         * parsing.
         */
        public ConfParseConfig build() throws ConfParseException {
            return new ConfParseConfig(File, this);
        }

        /**
         * Builds the ConfParse config from this builder based on URL.
         *
         * @return The parsed ConfParse config.
         * @throws ConfParseException If something went wrong during the
         * parsing.
         */
        public ConfParseConfig buildFromURL() throws ConfParseException {
            return new ConfParseConfig(URL, this);
        }

        /**
         * Returns an unmodifiable list of the default headers.
         *
         * @return The default headers as an unmodifiable list.
         */
        public List<Header> getHeaders() {
            return Collections.unmodifiableList(new ArrayList<>(Headers.values()));
        }
    }
}
