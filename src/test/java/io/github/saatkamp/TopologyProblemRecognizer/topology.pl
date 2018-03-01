component(phpapp).
component(javaapp).
component(webserver_1).
component(webserver_2).
component(java).
component(php_container).
component(mysql_db).
relation(phpapp, javaapp, httpconnection).
relation(javaapp, mysql_db, sqlconnection).
relation(javaapp, webserver_2, hostedOn_2).
relation(phpapp, webserver_1, hostedOn_1).
relation(mysql_db, mysql_dbms, hostedOn_3).
relation(javaapp, java, dependsOn_2).
relation(phpapp, php_container, dependsOn_1).
relation(java, webserver_2, hostedOn_21).
relation(php_container, webserver_1, hostedOn_11).
relation_of_type(httpconnection, connectsTo).
relation_of_type(sqlconnection, connectsTo).
relation_of_type(hostedOn_2, hostedOn).
relation_of_type(hostedOn_1, hostedOn).
relation_of_type(hostedOn_11, hostedOn).
relation_of_type(hostedOn_21, hostedOn).
relation_of_type(dependsOn_1, dependsOn).
relation_of_type(dependsOn_2, dependsOn).
relation_of_type(hostedOn_3, hostedOn).
targetLabel(phpapp, location_1).
targetLabel(javaapp, location_2).
targetLabel(mysql_db, location_1).
targetLabel(webserver_1, location_1).
targetLabel(webserver_2, location_2).
targetLabel(mysql_dbms, location_1).
targetLabel(java, location_2).
targetLabel(php_container, location_1).
property(httpconnection, data, sensitive).
property(sqlconnection, data, sensitive).
property(location_2, inboundCommunication, restricted).