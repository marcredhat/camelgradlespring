//package com.codergists.somescripts
//import HelloWorldService

import com.cordjastram.groovysap.example.Example1
class HelloWorldServiceImpl implements HelloWorldService {

	String name
        //def test = new Example1()
        //def rfcTable = test.rfcReadTable()

   String sayHello()
   {
		"Bla Bla Hello $name. Welcome to Scripting in Groovy."
		def test = new Example1()
                def rfcTable = test.rfcReadTable()
		//println "Marc - Printing RFC table  ${rfcTable}"
                //test.bapiGetFlightList()
		//test.materialReadPlants() 
		"Over and out - Marc - Bye now"
   }

} 
