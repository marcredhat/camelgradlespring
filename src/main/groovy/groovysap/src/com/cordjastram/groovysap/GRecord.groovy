package com.cordjastram.groovysap

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

import com.sap.conn.jco.JCoRecord

import static com.sap.conn.jco.JCoMetaData.*

class GRecord  {
	
	/**
	 *  the separator between field name and conversion exit name
	 *  e.g.  material_number$MATNR 
	 */
	static final String SEPARATOR = '$'
	
	/**
	 *  the wrapped JCoRecord 
	 */
	public final JCoRecord record

	
	/**
	 * @constructor 
	 * @param record
	 */
	GRecord(JCoRecord record) {
		this(record, null)
	}
	
	/**
	 * @constructor
	 * @param record
	 * @param context
	 */
	GRecord(JCoRecord record, GContext context) {
		this.record = record
		this.context = context
	} 
	
	@Override
	void setProperty(String name, Object object ) {
		
		String uppercaseName = name.toUpperCase()
		
		int position = uppercaseName.indexOf(SEPARATOR)
		
		if ( position > -1 && context.destination != null) {
			String conversionName = uppercaseName.substring(position+1)
			uppercaseName = uppercaseName.substring(0,position )
			object =  context.conversionExitInput(conversionName, object.toString() )
		}
		this.record.setValue(uppercaseName, object)
	}
	
	@Override
	Object getProperty(String name) {
		
		String uppercaseName = name.toUpperCase()
		Object value = null
		int position = uppercaseName.indexOf(SEPARATOR)
		
		if ( position > -1 && context.destination != null) {
			String conversionName = uppercaseName.substring(position+1)
			uppercaseName = uppercaseName.substring(0,position )
			value = context.conversionExitOutput(conversionName, this.getValue( uppercaseName )  )
		} 
		else {
			value = this.getValue( uppercaseName )
		}
		return value
	}
	

	private Object getValue(String name)   {
		
		String upperCaseName = name.toUpperCase()
		
		switch ( record.getMetaData().getType(upperCaseName) ) {
			
			case TYPE_TABLE:
				GTable table = map.get(upperCaseName)
			
				if (table == null) {
					table = new GTable( record.getTable(upperCaseName), this.context )	
					map.put(upperCaseName, table)
				}
			    return table
			
			case TYPE_STRUCTURE:
				GRecord newRecord = map.get(upperCaseName)
			
				if (newRecord == null) {
					newRecord = new GRecord( record.getStructure(upperCaseName), this.context )
					map.put(upperCaseName, newRecord)
				}
				return newRecord
			
			default:
				return record.getValue(upperCaseName)
		}
	}	
	
	/**
	 *  the GContext used for the conversion exti
	 */
	private final GContext context
	
	/**
	 *  cache for GTable and GRecord objects in order to avoid extensive
	 *  creation of Objects
	 */
	private final Map map = new HashMap()
}

