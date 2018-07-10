# ConfParse
ConfParse is a config format, parser and API for Java

#Config Data Building
```Java
-----------------------
Build Config Data From String Data
-------------------------------------------------------------------------------------
        String Data = "Server:\n"
                + "\n"
                + "	TotalServer 15";

        ConfParseConfig confParse = ConfParse.fromData(Data).BuildFromData();
-------------------------------------------------------------------------------------

-----------------------
Build Config Data From Web Data

        ConfParseConfig confParse = ConfParse.fromURL(new URL("http://localhost/Data.txt")).buildFromURL();
-------------------------------------------------------------------------------------

-----------------------
Build Config Data From File

        ConfParseConfig confParse = ConfParse.fromFile(new File("D://Data.txt")).BuildFromFile();
-------------------------------------------------------------------------------------

-----------------------
Build Config Data From Path

	    ConfParseConfig confParse = ConfParse.fromFilePath(new File("D://Data.txt").toPath()).BuildFromFile();
		
-------------------------------------------------------------------------------------

-----------------------
Build Config Data From FileName

	    ConfParseConfig confParse = ConfParse.fromFileName("D://Data.txt").BuildFromFile();
		
-------------------------------------------------------------------------------------

-----------------------
Build Config Data From URI

	    ConfParseConfig confParse = ConfParse.fromFileName(new URI("file:///D:/Data.txt")).BuildFromFile();
		
-------------------------------------------------------------------------------------
```

# ConfParse Config File Format
```Java
# Comment Supported
# Server Database
Server:

	TotalServer 15

AsiaServers:

	MumbaiServers 10
	SingaporeServers 5

ServersInformation:

	AsiaServerA MumbaiServerA 100.100.100.100 udp:53
	AsiaServerB MumbaiServerB 100.100.100.101 udp:11211
	AsiaServerC SingaporeServerA 200.200.200.200 tcp:80
	AsiaServerD SingaporeServerB 200.200.200.201 tcp:443
```
# Example
## Simple Parsing
Code: 
```Java
package com.serverdatadeliverynetwork.confparse;

import com.serverdatadeliverynetwork.confparse.config.Header;
import com.serverdatadeliverynetwork.confparse.config.Key;
import com.serverdatadeliverynetwork.confparse.exceptions.ConfParseException;
import java.net.URL;

/**
 *
 * @author Aayush Atharva
 */
public class Main {

    public static void main(String[] args) throws MalformedURLException {

        try {

            ConfParseConfig confParse;

            // Create a config from a config URL
            {
                confParse = ConfParse.fromURL(new URL("http://localhost/Data.txt")).buildFromURL();
            }

            

            // Check If The Header 'Server' And The Key 'TotalServer' Is Available
            // True = Header and Key Available, False = Header Or Key Not Available
            if (confParse.hasHeaderAndKey("Server", "TotalServer")) {
                Key TotalServer = confParse.getHeader("Server").getKey("TotalServer"); // Fetch All Data From Key 'TotalServers' In Header 'Server'

                // Check If TotalServer Key Has Some Value Or Not
                // True = Key Available, False = Key Not Available
                if (TotalServer.hasValues()) {
                    System.out.println("Total Server: " + TotalServer.getValue(0).asInt());
                }

            }

            // Check If The Header 'AsiaServers' Exists Or Not
            // True = Header Available, False = Header Not Available
            if (confParse.hasHeader("AsiaServers")) {
                Header AsiaServers = confParse.getHeader("AsiaServers"); // Fetch All Data From Key 'TotalServers' In Header 'Server'

                Key Mumbai = AsiaServers.getKey("MumbaiServers");               // Fetch All Data From Key 'Mumbai' In Header 'AsiaServers'
                if (Mumbai.hasValues()) {
                    System.out.println("Total Mumbai Servers: " + Mumbai.getValue(0));
                }

                Key Singapore = AsiaServers.getKey("SingaporeServers");  // Fetch All Data From Key 'SingaporeServers' In Header 'AsiaServers'
                if (Singapore.hasValues()) {
                    System.out.println("Total Singapore Servers: " + Singapore.getValue(0));
                }

            }

            // Check If The Header 'ServersInformation' And Initial Key 'AsiaServerA Exists Or Not
            // True = Header and Initial Key Available, False = Header Or Initial Key Not Available
            if (confParse.hasHeaderAndKey("ServersInformation", "AsiaServerA")) {

                Key Server = confParse.getHeader("ServersInformation").getKey("AsiaServerA"); // Fetch Data From Key 'ServersInformation' In Header 'AsiaServerA'

                // Check If The Key Has Values
                // True = Key Available, False = Key Not Available
                if (Server.hasValues()) {
                    String ServerID = Server.getValue(0).asString();
                    String ServerIP = Server.getValue(1).asString();
                    String ServerProtocolAndPort = Server.getValue(2).asString();

                    System.out.println("Server ID: " + ServerID);
                    System.out.println("Server IP: " + ServerIP);
                    System.out.println("Server Protocol And Port: " + ServerProtocolAndPort);
                }
            }

        } catch (ConfParseException e) {
            e.printStackTrace();
        }

    }
}

```
## Defaults
ConfParse Config File: 
```Java
# Comment Supported
# Server Database
Server:
	AsiaServerA MumbaiServerA 100.100.100.100 udp:53
```
Code: 
```Java
package com.serverdatadeliverynetwork.confparse;

import com.serverdatadeliverynetwork.confparse.config.Header;
import com.serverdatadeliverynetwork.confparse.config.Key;
import com.serverdatadeliverynetwork.confparse.config.Value;
import com.serverdatadeliverynetwork.confparse.exceptions.ConfParseException;


/**
 *
 * @author Aayush Atharva
 */
public class Main {

    public static void main(String[] args) throws ConfParseException {
        try {
            // Create the cope and add the default header
            ConfParseConfig confParse = ConfParse.from("D://Data.ConfParse")
                    .def(new Header("Server"), new Key("AsiaServerA"), new Value("MumbaiServerA"), new Value("100.100.100.108"), new Value("udp:53"))
                    .build();

            // No need to check if the header or key exists
            // because we have the default values
            Key bindKey = confParse.getHeader("Server").getKey("AsiaServerA");

            String ServerID = bindKey.getValue(0).asString();
            String ServerIP = bindKey.getValue(1).asString();
          
            System.out.println("ServerID: " + ServerID);
            System.out.println("ServerIP: " + ServerIP);
        } catch (ConfParseException e) {
            e.printStackTrace();
        }
    }
}

```

