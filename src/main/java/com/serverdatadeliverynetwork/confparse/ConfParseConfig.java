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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.serverdatadeliverynetwork.confparse.config.Header;
import com.serverdatadeliverynetwork.confparse.config.Key;
import com.serverdatadeliverynetwork.confparse.config.Value;
import com.serverdatadeliverynetwork.confparse.exceptions.ConfParseEmptyConfigException;
import com.serverdatadeliverynetwork.confparse.exceptions.ConfParseException;
import com.serverdatadeliverynetwork.confparse.exceptions.ConfParseInvalidConfigException;
import com.serverdatadeliverynetwork.confparse.exceptions.ConfParseLoadConfigException;

/**
 *
 * @author Aayush Atharva
 */
public class ConfParseConfig {

    /**
     * The config file line by line as a list.
     */
    private List<String> Config = new ArrayList<>();

    /**
     * All headers from the config.
     */
    private Map<String, Header> headers = new HashMap<>();

    /**
     * Creates a new ConfParse config from the given file.
     *
     * @param file The config file.
     * @throws ConfParseException If something went wrong.
     */
    protected ConfParseConfig(File file) throws ConfParseException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String Line;
            while ((Line = bufferedReader.readLine()) != null) {
                // Check If Line Is Not Empty Or Line Is Not Comment
                if (!Line.isEmpty() && !Line.startsWith("#")) {
                    Config.add(Line.trim());
                }
            }
        } catch (IOException e) {
            throw new ConfParseLoadConfigException("Could not load config file '" + file.getName() + "'");   // Throw File Not Found ConfParseException
        }

        // Check If Config Is Empty Or Not
        if (Config.isEmpty()) {
            throw new ConfParseEmptyConfigException("Config file " + file.getName() + " is empty");     // Throw ConfParseEmptyConfigException
        }

        parse(); // Start Parsing File
    }

    /**
     * Creates a new ConfParse config from the given file URL.
     *
     * @param URL URL of ConfParse config file
     * @throws ConfParseException If Something went wrong
     */
    protected ConfParseConfig(URL URL) throws ConfParseException {

        BufferedReader reader = null;
        URLConnection connection;

        try {
            connection = URL.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/58.0.1271.95 Safari/537.11");
            connection.connect();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String Line = null;
            while ((Line = reader.readLine()) != null) {
                if (!Line.isEmpty() && !Line.startsWith("#")) {
                    Config.add(Line.trim());
                }
            }

            reader.close();

        } catch (IOException ex) {
            throw new ConfParseLoadConfigException("Could not load config file data from '" + URL.toString() + "'");   // Throw File Not Found ConfParseException
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                }
            }
        }

        // Check If Config Is Empty Or Not
        if (Config.isEmpty()) {
            throw new ConfParseEmptyConfigException("Config file data at " + URL.toString() + " is empty");     // Throw ConfParseEmptyConfigException
        }

        parse(); // Start Parsing File
    }

    /**
     * Creates a new ConfParse config from the given file and builder instance.
     *
     * @param file The file.
     * @param ConfParseBuilder The builder instance.
     * @throws ConfParseException If something went wrong.
     */
    protected ConfParseConfig(File file, ConfParse.ConfParser ConfParseBuilder) throws ConfParseException {

        this(file);

        // Check if there are default values
        if (ConfParseBuilder.getHeaders().isEmpty()) {
            return;
        }

        // Sets possible default values
        for (Header builderHeader : ConfParseBuilder.getHeaders()) {
            Header header = headers.get(builderHeader.getName());
            if (header == null) {
                headers.put(builderHeader.getName(), builderHeader);
            } else {
                for (Key builderKey : builderHeader.getKeys()) {
                    if (!header.hasKey(builderKey.getName())) {
                        header.addKey(builderKey);
                    } else {
                        Key key = header.getKey(builderKey.getName());
                        if (!key.hasValues()) {
                            builderKey.getValues().forEach(key::addValue);
                        }
                    }
                }
            }
        }

    }

    /**
     * Creates a new ConfParse config from the given URL and builder instance.
     *
     * @param url The File URL
     * @param ConfParseBuilder The builder instance.
     * @throws ConfParseException If something went wrong.
     */
    protected ConfParseConfig(URL url, ConfParse.ConfParser ConfParseBuilder) throws ConfParseException {

        this(url);

        // Check if there are default values
        if (ConfParseBuilder.getHeaders().isEmpty()) {
            return;
        }

        // Sets possible default values
        for (Header builderHeader : ConfParseBuilder.getHeaders()) {
            Header header = headers.get(builderHeader.getName());
            if (header == null) {
                headers.put(builderHeader.getName(), builderHeader);
            } else {
                for (Key builderKey : builderHeader.getKeys()) {
                    if (!header.hasKey(builderKey.getName())) {
                        header.addKey(builderKey);
                    } else {
                        Key key = header.getKey(builderKey.getName());
                        if (!key.hasValues()) {
                            builderKey.getValues().forEach(key::addValue);
                        }
                    }
                }
            }
        }

    }

    /**
     * Parses the config lines.
     *
     * @throws ConfParseException If something went wrong during the parsing.
     */
    private void parse() throws ConfParseException {
        Header currentHeader = null;

        for (int i = 0; i < Config.size(); i++) {
            String line = Config.get(i);

            if (line.endsWith(":")) {

                if (currentHeader != null) {
                    headers.put(currentHeader.getName(), currentHeader);
                }

                currentHeader = new Header(line.substring(0, line.length() - 1));
            } else {
                String[] info = line.split(" ");
                if (info.length > 0) {
                    String key = info[0];
                    String[] options = new String[info.length - 1];
                    System.arraycopy(info, 1, options, 0, options.length);

                    Key keyData = new Key(key);
                    for (String option : options) {
                        keyData.addValue(new Value(option));
                    }

                    if (currentHeader != null) {
                        currentHeader.addKey(keyData);
                    } else {
                        throw new ConfParseInvalidConfigException("at least one header at the top is needed");
                    }
                }
            }

            // Add the last header
            if (i == Config.size() - 1) {
                if (currentHeader != null) {
                    headers.put(currentHeader.getName(), currentHeader);
                }
            }
        }
    }

    /**
     * Returns whether the given header exists or not.
     *
     * @param header The header to check.
     * @return True or false whether the given header exists or not.
     */
    public boolean hasHeader(String header) {
        return headers.containsKey(header);
    }

    /**
     * Returns whether the given header and key exists or not.
     *
     * @param header The header to check.
     * @param key The key to check.
     * @return True or false whether the given header and key exists or not.
     */
    public boolean hasHeaderAndKey(String header, String key) {
        return headers.containsKey(header) && headers.get(header).hasKey(key);
    }

    /**
     * Returns the header if it exists otherwise it returns null.
     *
     * @param header The header name.
     * @return The header object instance.
     */
    public Header getHeader(String header) {
        return headers.get(header);
    }
}
