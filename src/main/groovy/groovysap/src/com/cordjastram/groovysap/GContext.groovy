package com.cordjastram.groovysap

/**
 *   Copyright 2010 by Cord Jastram
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *	    http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

import com.sap.conn.jco.JCoContext
import com.sap.conn.jco.JCoDestination
import com.sap.conn.jco.JCoRecord
import com.sap.conn.jco.AbapException

import static com.sap.conn.jco.JCoMetaData.*
import com.sap.conn.jco.*

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

class GContext {
	 
	FieldConversionMap conversionExitMap = new FieldConversionMap()
	
	public final JCoDestination destination
	private final GRfcFunction rfcConversionExit
	private final GRfcFunction rfcCommit
	private final GRfcFunction rfcRollback
	
	private final String INPUT  = "_INPUT"
	private final String OUTPUT = "_OUTPUT"
	
	GContext(String destinationName) {
		
		assert destinationName != null 
		
		this.destination = JCoDestinationManager.getDestination(destinationName)
		
		this.rfcCommit =  new GRfcFunction("BAPI_TRANSACTION_COMMIT", this)
		this.rfcRollback = new GRfcFunction("BAPI_TRANSACTION_ROLLBACK", this)
		this.rfcConversionExit = new GRfcFunction("RSA0_CONVERSION_EXIT", this)

		conversionExitMap.init()
	}
	
	void rollback() {
		rfcRollback()
	}
	
	private String conversionExit(String name, String input, String direction) {
		
		FieldConversion fieldConversion = conversionExitMap.getConversion(name)
		String result = input
		
		if ( fieldConversion == null || rfcConversionExit == null ) {
			return input
		}
		
		rfcConversionExit.exporting {
			i_structure = fieldConversion.structure 
			i_fieldname = fieldConversion.field
			i_convexit  = fieldConversion.conversionExit
			i_direction = direction
			i_value     = input
		}	
		
		rfcConversionExit()
		
		rfcConversionExit.importing { result = e_value  }
		
		return result
	}
	
	String conversionExitInput(String exitName, String externalValue) {
		return conversionExit(exitName, externalValue, INPUT )
	}  
	
	String conversionExitOutput(String exitName, String internalValue) {
		return conversionExit(exitName, internalValue, OUTPUT )
	}
	
	private void commit(boolean commitAndWait) {
		
		rfcCommit.exporting {
			wait = commitAndWait ? 'X' : ' '
		}
		
		rfcCommit()
		
		rfcCommit.importing {
			if ( Return.type == 'E' ) {
				throw new AbapException( Return.message )
			}
		}
	}
	
	private void commitWorkAndWait() {
		commit(true)
	}
	
	private void commit() {
		commit(false)
	}
	
	/**
	 * 
	 * @param map
	 * @param cl the closure to cal
	 */
	void transaction(boolean workAndWait, Closure cl) {
		try { 
			JCoContext.begin(destination)	
			cl.call( )
			workAndWait ? commitWorkAndWait() : commit()
		} catch (Exception e) {
			rollback()
		}
		finally {
			JCoContext.end(destination)
		}
	}
	
	void transaction(Closure cl) {
		try {
			JCoContext.begin(destination)
			cl.call( )
			commit()
		} catch (Exception e) {
			rollback()
		}
		finally {
			JCoContext.end(destination)
		}
	}
}
