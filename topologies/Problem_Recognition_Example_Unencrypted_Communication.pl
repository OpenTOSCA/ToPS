
component(java7).
component(php-5-module).
component(php-5-webapplication).
component(tomcat_7).
component(apache-2_4).
component(ubuntu-14_04-vm).
component(ubuntu-14_04-vm_2).
component(openstack-liberty-12).
component(vsphere5).
component_of_type(java7, java7).
component_of_type(php-5-module, php-5-module).
component_of_type(php-5-webapplication, php-5-webapplication).
component_of_type(tomcat_7, tomcat_7).
component_of_type(apache-2_4, apache-2.4).
component_of_type(ubuntu-14_04-vm, ubuntu-14.04-vm).
component_of_type(ubuntu-14_04-vm_2, ubuntu-14.04-vm).
component_of_type(openstack-liberty-12, openstack-liberty-12).
component_of_type(vsphere5, vsphere5).
relation(php-5-webapplication, java7, con_43).
relation(php-5-webapplication, php-5-module, con_55).
relation(php-5-module, apache-2_4, con_57).
relation(apache-2_4, ubuntu-14_04-vm, con_69).
relation(java7, tomcat_7, con_93).
relation(tomcat_7, ubuntu-14_04-vm_2, con_105).
relation(ubuntu-14_04-vm, openstack-liberty-12, con_101).
relation(ubuntu-14_04-vm_2, vsphere5, con_113).
relation_of_type(con_43, connectsto).
relation_of_type(con_55, hostedon).
relation_of_type(con_57, hostedon).
relation_of_type(con_69, hostedon).
relation_of_type(con_93, hostedon).
relation_of_type(con_105, hostedon).
relation_of_type(con_101, hostedon).
relation_of_type(con_113, hostedon).
property(con_43, sensitivedata, true
                ).
property(openstack-liberty-12, location, 5).
property(vsphere5, location, 3).
hosting_stack([java7, tomcat_7, ubuntu-14_04-vm_2, vsphere5]).
hosting_stack([php-5-webapplication, php-5-module, apache-2_4, ubuntu-14_04-vm, openstack-liberty-12]).
