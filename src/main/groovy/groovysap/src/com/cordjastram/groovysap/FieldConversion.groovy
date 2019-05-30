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

class FieldConversion {
	
	final public String name
	final public String structure
	final public String field
	final public String conversionExit
	
	FieldConversion(Map map) {
		this.name = map["name"]
		this.structure = map["structure"]
		this.field = map["field"]
		this.conversionExit = map["conversionExit"]	
	}
	
}

class FieldConversionMap {
	
	FieldConversion getConversion(String name) {
		return map.get(name)	
	}
	
	private Map<String, FieldConversion> map = new HashMap<String, FieldConversion>()
	
	public FieldConversionMap loadFromXml() {
		def inp = new File("./conversion.xml")
		//InputStream inp = getClass().getResourceAsStream("/home/marc/sap/conversion.xml")
               //InputStream inp = getClass().getResourceAsStream("conversion.xml")
		new XmlSlurper().parse(inp).children().each {
			map.putAt(it.attributes()['name'], new FieldConversion( it.attributes() ))
		}
		return this
	}
	
	void init() {
		loadFromXml()
	}
}
