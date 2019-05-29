
Setup
=====
A file called SAPConnectionInfo.jcoDestination needs to be present at the root of the folder from which we are starting the app

Example of SAPConnectionInfo.jcoDestination
jco.client.lang=en
jco.client.client=001
jco.client.passwd<SAP password>
jco.client.user=<SAP username>
jco.client.sysnr=00
jco.client.ashost=<IP address of SAP instance>
#Pool properites from the JCo v3
#Maximum number of active connections that
#can be created for a destination simultaneously
jco.destination.peak_limit=10
#Maximum number of idle connections kept open by the destination.
#A value of 0 has the effect that there is no connection pooling.
jco.destination.pool_capacity=10
#Time in ms after that the connections hold by the destination can be closed
jco.destination.expiration_time=60000
#Period in ms after that the destination checks the released connections for expiration
jco.destination.expiration_check_period=60000
#Max time in ms to wait for a connection, if the max allowed number
#of connections is allocated by the application
jco.destination.max_get_client_time=60000


SAP connector jar files
=======================
Test SAP connector JDOC jar files: 
[marc@rhel8ga ~]$ java -jar /home/marc/sap/sapjco3.jar -stdout | more
Expected result should be similar to:
--------------------------------------------------------------------------------------
|                                 SAP Java Connector                                 |
|                Copyright (c) 2000-2018 SAP SE. All rights reserved.                |
|                                Version Information                                 |
--------------------------------------------------------------------------------------
Java Runtime:
 Operating System:    Linux 4.18.0-80.el8.x86_64 for amd64
 Java VM:             1.8.0_212 Oracle Corporation
 Default charset:     UTF-8
Versions:
 JCo API:             3.0.19 (2018-12-03)
 JCo middleware:      JavaRfc 2.2.23
 JCo library:         721.1120
Library Paths:
 JCo archive:         /home/marc/sap/sapjco3.jar
 JCo library:         /home/marc/sap/libsapjco3.so
--------------------------------------------------------------------------------------
|                                      Manifest                                      |
--------------------------------------------------------------------------------------
Manifest-Version: 1.0
Ant-Version: Apache Ant 1.6.4
Implementation-Title: com.sap.conn.jco
Implementation-Version: 20181209 2228 [3.0.19 (2018-12-03)]
Specification-Vendor: SAP SE, Walldorf
Specification-Title: SAP Java Connector v3
Implementation-Vendor-Id: com.sap
Created-By: 5.1.028 (SAP AG)
Specification-Version: 3.0.19
Implementation-Vendor: SAP SE, Walldorf
Main-Class: com.sap.conn.jco.rt.About

=======
Test SAP connector IDOC jar files:
[marc@rhel8ga ~]$ java -jar /home/marc/sap/sapidoc3.jar -stdout | more
--------------------------------------------------------------------------------------
|                            SAP Java IDoc Class Library                             |
|                Copyright (c) 2000-2018 SAP SE. All rights reserved.                |
|                                Version Information                                 |
--------------------------------------------------------------------------------------
Java Runtime:
 Operating System:    Linux 4.18.0-80.el8.x86_64 for amd64
 Java VM:             1.8.0_212 Oracle Corporation
 Default charset:     UTF-8
Versions:
 IDoc API:            3.0.13 (2018-10-01)
 JCo API:             3.0.19 (2018-12-03)
Library Paths:
 IDoc archive:        /home/marc/sap/sapidoc3.jar
 JCo archive:         /home/marc/sap/sapjco3.jar
--------------------------------------------------------------------------------------
|                                      Manifest                                      |
--------------------------------------------------------------------------------------
Manifest-Version: 1.0
Ant-Version: Apache Ant 1.6.5
Implementation-Version: 30.000.20181001214016.0000
Changelist: 439196
Perforce-Server: perforce3301.wdf.sap.corp:3301
Implementation-Vendor-Id: sap.com
Edition: JavaIDoc_30_REL
Class-Path: sapjco3.jar
Created-By: 8.1.021 25.51-b12 (SAP AG)
Main-Class: com.sap.conn.idoc.jco.rt.About

====

In build.gradle, find "compile fileTree(dir: '/home/marc/sap', include: '*.jar')" and
replace /home/marc/sap with the folder where you have the SAP connector files (https://support.sap.com/en/product/connectors/jco.html)


Running the app
===============
git clone https://github.com/marcredhat/camelgradlespring.git
cd camelgradlespring/
create the SAPConnectionInfo.jcoDestination file (example above)
./gradlew bootRun


Tests
=====
curl http://localhost:8080/camel/SAPLegacyFlights
curl http://localhost:8080/camel/test


Use Gradle to generate an uberjar
=================================
git clone https://github.com/marcredhat/camelgradlespring.git
cd camelgradlespring/
./gradlew bootJar 

The uberjar is created under build/libs

Copy the SAP Connector .jar and .so files to build/libs (where Gradle generated the uberjar):

Copy the jcoDestination file to build/libs

The end results should look like:
[marc@rhel8ga libs]$ pwd
/home/marc/gradlecamelsap/camelsapdemo/build/libs
[marc@rhel8ga libs]$ ls
camelsapdemo-0.0.1-SNAPSHOT.jar  libsapjco3.so  SAPConnectionInfo.jcoDestination  sapidoc3.jar  sapjco3.jar

You can now run the uberjar:
java  -jar camelsapdemo-0.0.1-SNAPSHOT.jar

==========================================
SAP instances used for testing:
SAP NetWeaver AS ABAP and SAP BW 7.5 SP01 on SAP HANA SP10 [Developer Edition] 
from cal.sap.com



==========================================
My .bashrc is:
[marc@rhel8ga libs]$ more /home/marc/.bashrc
# .bashrc

# Source global definitions
if [ -f /etc/bashrc ]; then
	. /etc/bashrc
fi

# User specific environment
PATH="$HOME/.local/bin:$HOME/bin:$PATH:/home/marc/gradle-5.4.1/bin"
export PATH
export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.212.b04-1.el8_0.x86_64/jre
export CLASSPATH=/home/marc/sap:/home/marc/sap/groovy-2.5.7/lib

# User specific aliases and functions

#THIS MUST BE AT THE END OF THE FILE FOR SDKMAN TO WORK!!!
export SDKMAN_DIR="/home/marc/.sdkman"
[[ -s "/home/marc/.sdkman/bin/sdkman-init.sh" ]] && source "/home/marc/.sdkman/bin/sdkman-init.sh"
