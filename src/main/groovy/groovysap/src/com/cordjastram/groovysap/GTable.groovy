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

import com.sap.conn.jco.JCoTable 
import java.util.NoSuchElementException

 
/**
 * Simple wrapper for a JCoTable
 * @author cjastram
 *
 */
class GTable extends GRecord implements Iterator  {
 
	GTable( JCoTable table, GContext context) {
		super(table, context)
		this.table = table
		bFirstRow = true
	}
	
	GTable( JCoTable table) {
		super(table)
		this.table = table
		bFirstRow = true
	}
	
	/**
	 * 
	 */
	
	final Iterator iterator() {
		this.firstRow()
		return this	
	}
 
	/**
	 * 
	 */
	final Object next() {
		if( ! hasNext() )
			throw NoSuchElementException("no more rows in GTable")
			
		if ( bFirstRow ) {
			this.table.firstRow()
			bFirstRow = false
		} else {
			this.table.nextRow() 
		}
		assert( bFirstRow == false )
		return this
	}
	 
	/**
	 * Returns true if the iteration has more elements
	 */
	final boolean hasNext() {
		if ( this.table.isEmpty() ||  ( this.table.isLastRow() && ( !bFirstRow ) ) )
			return false 
		else
		    return true 
	}
	
	/**
	* removes the current row
	*/
   final void remove() {
	   this.table.deleteRow()
   }
	
	/**
	 * Moves the row pointer to the first row.
	 */
	final void firstRow() {
		this.table.firstRow() 
		bFirstRow = true
	}
	
	/**
	 * Returns the number of rows
	 * @return number of rows
	 */
	final int getNumRows() {
		return this.table.getNumRows()	
	}
	
	/**
	 * @return Returns the current row number.
	 */
	final int getRow() {
		return this.table.getRow()
	}
	
	/**
	 * creates a copy of the current table row
	 */
	final GRecord getRowRecord() {
		return new GRecord( JCo.createStructure(this.table,table.getRow() ) )
	}
	
	/**
	 * Clears the table, i.e. deletes all rows
	 */
	final void clear() {
		this.table.clear()
	}
	
	/**
	 * Appends a new row at the end of the table and moves the row pointer 
	 * such that it points to the newly appended row.
	 */
	final void appendRow() {
		this.table.appendRow()
	}
	
	/**
	 * Inserts a new empty row before the specified position  
	 * @param index - the index of the row before to insert the new row
	 */
	final void insertRow(int index) {
		this.table.insertRow (index)
	}
		
	/**
	 * moves the row pointer to the next row
	 */
	final boolean nextRow() {
		bFirstRow = false
		return this.table.nextRow()
	}
	
	/**
	 *@return true if the table is empty otherwise false
	 */
	final boolean isEmpty() {
		return this.table.isEmpty()
	}
	
	final private JCoTable table
	private boolean bFirstRow = true
}
