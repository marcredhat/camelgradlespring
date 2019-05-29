
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
In build.gradle, find "compile fileTree(dir: '/home/marc/sap', include: '*.jar')" and
replace /home/marc/sap with the folder where you have the SAP connector files (https://support.sap.com/en/product/connectors/jco.html)


Running the app
===============
git clone https://github.com/marcredhat/camelgradlespring.git
cd camelgradlespring/
cp /home/marc/gradlecamelsap/camelsapdemo/SAPConnectionInfo.jcoDestination .


Tests
=====
curl http://localhost:8080/camel/SAPLegacyFlights
curl http://localhost:8080/camel/test
