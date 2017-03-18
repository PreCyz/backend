# backend
Example of spring (java config only), jpa, apache-cxf with (jaxrs and jaxws) in one place.

This is an example of application that gathering in one place frameworks like: Apache CXF, Spring, JPA. 
All config (where I could) is done "java config" way. The idea behind this app is basic backend application, 
which on one end has connection to database and on the other end has WS and REST connection from world. 

Basic architecture is:

	 ___          ___        ___                      ___
	| W |        | F |      | S |       ___          | D |
	| O | [WS]   | A |      | E |      | D |  [JPA]  | A |
	| R | <==>   | C | <==> | R | <==> | A |   <==>  | T |
	| L | [REST] | A |      | V |      | O |  [JDBC] | A |
	| D |        | D |      | I |       ---          | B |
	 ---         | E |      | C |                    | A |
	              ---       | E |                    | S |
	                        | S |                    | E |
	                         ---                     | S |
	        ------------------------------------      ---
	        |              SPRING              |
	        ------------------------------------
					 
There is facade which is entry point from the world. Facade contains services which class DAO. The DAO tier hits 
the database and returns data.
