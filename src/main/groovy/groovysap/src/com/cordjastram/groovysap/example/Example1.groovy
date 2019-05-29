
package com.cordjastram.groovysap.example

/**
*   Copyright 2010 by Cord Jastram
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*
*/
//this.class.classLoader.rootLoader.addURL(new URL("/home/marc/sap"))
//this.class.classLoader.rootLoader.URLs.each{ println it }
import com.cordjastram.groovysap.*
import com.sap.conn.jco.*
import com.sap.conn.jco.ext.*

class Example1 {
	
	static void main(String[] args) {
		rfcReadTable()
		bapiGetFlightList()
		materialReadPlants()
	}
	
	
	static void materialReadPlants() {
		GContext context = new GContext("TEST")
		
		//GRfcFunction function = new GRfcFunction("MATERIAL_READ_PLANTS",context)
                //GRfcFunction function = new GRfcFunction("SDOK_PROFILE_READ",context)
                GRfcFunction function = new GRfcFunction("SDOK_QUE_GET_TRACE_LEVEL",context)
		if ( function != null) {

			function.exporting { matnr$MATNR = '19' }

			function()

			function.tables {

				plants.each {
					println "Intern: $it.matnr Extern: ${it.matnr$MATNR} Werk: $it.werks"
				}
			}
		}
}
	
	static void bapiGetFlightList () {
		
		GContext context = new GContext("TEST")

		GRfcFunction function = new GRfcFunction("BAPI_SFLIGHT_GETLIST", context)
		
		function.exporting {
			airline = 'LH'
			destination_from.city = 'FRANKFURT'
		}
		
		function() 
		
		function.tables {
			
			if (  Return.find{ it.Type == 'E' } ) {
				println Return.message
			} else {
				flight_list.each {  
					println "$it.cityfrom - $it.cityto - $it.price"
				}
			}
		}
	}
	
	
	static void rfcReadTable() {
		
		GContext context = new GContext("TEST") 
		
		GRfcFunction function = new GRfcFunction("RFC_READ_TABLE", context)
		
		function.exporting {
			rowcount = 30
			query_table = 'SFLIGHT'
		}
		
		function()
		
		function.tables {
			data.each { println it.wa }
		}
	}
}
