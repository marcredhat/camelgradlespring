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

import java.util.Map 
import groovy.util.BuilderSupport

import com.sap.conn.jco.JCoException
import com.sap.conn.jco.JCoFunction
import com.sap.conn.jco.JCoDestination
 
 
class GRfcFunction extends BuilderSupport {
	/**
	 * the wrapped JCoFunction
	 */
	public final JCoFunction function

	public final functionExists
	
	void call( ) throws JCoException {
		this.function.execute (this.destination)
	}
	
	GRfcFunction(String functionName, GContext context) {
		assert context != null
		assert functionName != null 
		assert functionName.length() > 0
		
		this.destination = context.destination
    	this.function = destination.getRepository().getFunction (functionName)
		if ( function != null) {
 			functionExists = true
			this.tableParameter = new GRecord( this.function.getTableParameterList(), context )
			this.importingParameter = new GRecord( this.function.getImportParameterList(), context )
			this.exportingParameter = new GRecord( this.function.getExportParameterList(), context )
		} else {
			throw new InstantiationError("Function " + functionName + " does not exist in the target system")
		}

	}
	
	@Override
	Object getProperty(String name)   {
		assert name != null
		assert ! "".equals( name )
		
		return this.getCurrent().getProperty(name)
	}
	
	@Override
	void setProperty(String name, Object value) {
		assert name != null
		assert ! "".equals( name )
		assert value != null
		
		this.getCurrent().setProperty(name,value)
	}  
	
	@Override
	protected Object createNode(Object name) {
		 
		String upperCaseName = name.toString().toUpperCase()
		Object obj
		switch (upperCaseName ) {
			case 'EXPORTING':
				obj = this.importingParameter
				break
			case 'IMPORTING':
				obj = this.exportingParameter
				break
			case 'TABLES':
				obj = this.tableParameter
				break
			default:
				throw new RuntimeException("Invalid node name: " + name)
		}
		return obj
	}
	
	/**
	 * This method should never be called
	 */
	@Override
	protected Object createNode(Object name, Object value) {
		assert false : 'Should not be called'
		return null
	}
	
	/**
	 * This method should never be called
	 */
	@Override
	protected Object createNode(Object name, Map attributes) {
		assert false : 'Should not be called'
		return null
	}
	
	/**
	* This method should never be called
	*/
	@Override
	protected Object createNode(Object name, Map attributes, Object value) {
		assert false : 'Should not be called'
		return null
	}
	
	/**
	* This method should never be called
	*/
	@Override
	protected void setParent(Object parent, Object child) {
		assert false : 'Should not be called'
	}
	
	private final GRecord  tableParameter
	private final GRecord  importingParameter
	private final GRecord  exportingParameter
	private final JCoDestination destination 
}
