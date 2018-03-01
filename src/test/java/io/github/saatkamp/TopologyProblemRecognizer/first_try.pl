%% A example topology and one problem expressed as prolog


/** <examples>
?- likes(sam,dahl).
?- likes(sam,chop_suey).
?- likes(sam,pizza).
?- likes(sam,chips).
?- likes(sam,curry).
*/
unencrypted_public_communication(Component_1, Component_2) :-
	property(Relation_ID, data, sensitive),
	relation_of_type(Relation_ID, connectsTo),
	relation(Component_1, Component_2, Relation_ID),
	components_in_different_locations(Component_1, Component_2).
	
inbound_communication_restricted_location(Component_1, Component_2) :-
	relation_of_type(Relation_ID, connectsTo),
	relation(Component_1, Component_2, Relation_ID),
	components_in_different_locations(Component_1, Component_2),
	targetLabel(Component_2, Location_2),
	property(Location_2, inboundCommunication, restricted).
	
components_in_different_locations(Component_1, Component_2) :-
	targetLabel(Component_1, Location_1),
	targetLabel(Component_2, Location_2),
	Location_1\=Location_2.