# groovysap
This library is a wrapper for the SAP Java Connector 3.X. You can access an SAP system in a groovy way.

### Installation

1. Download the SAP Java Connector (JCo3) and extract `sapjco3.jar` 

2. Update the file `TEST.jcoDestination` with your credentials and your SAP system data.

3. Run the examples


### Example

The ABAP coding 

```
 DATA: ls_destination_from TYPE bapisfldst,
       lt_return           TYPE TABLE OF bapiret2,
       ls_return           TYPE bapiret2,
       lt_flight           TYPE TABLE OF bapisfldat,
       ls_flight           TYPE bapisfldat.`

       ls_destination_from-city = 'FRANKFURT'.
 
       CALL FUNCTION 'BAPI_FLIGHT_GETLIST'
            EXPORTING
                airline          = 'LH'
                destination_from = ls_destination_from
                max_rows         = 10
            TABLES
                flight_list      = lt_flight
                return           = lt_return.

        LOOP AT lt_return INTO ls_return WHERE type = 'E'.
            WRITE:  / , ls_return-message(50).
            EXIT.
        ENDLOOP.

        IF sy-subrc <> 0.
            LOOP AT lt_flight INTO ls_flight.
                NEW-LINE.
                WRITE :  ls_flight-airline, ls_flight-cityfrom, ls_flight-cityto, ls_flight-flightdate .
            ENDLOOP.
        ENDIF.
```

 is written in Groovy using as 
    
 ````
		// get a context #1
		GContext context = new GContext("TEST")

		// get a function #2
		GRfcFunction function = new GRfcFunction("BAPI_FLIGHT_GETLIST", context)
		
		// set your import parameters #3
		function.exporting {
			airline = 'LH'
			destination_from.city = 'FRANKFURT'
		}
		
		// the actual call #4
		function() 
		
		// have a look at the results #5
		function.tables {
			
			if (  Return.find{ it.Type == 'E' } ) {
				println Return.message
			} else {
				flight_list.each {  
					println "$it.cityfrom - $it.cityto - $it.price"
				}
			}
		}	
		// done!
	}
```

 