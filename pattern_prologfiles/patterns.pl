direct_access_to_restricted_environment(Component_1, Component_2) :-
	direct_communication(Component_1, Component_2), 
	components_in_different_locations(Component_1, Component_2),
	component_in_restricted_environment(Component_2).

integration_of_restricted_environments(Component_1, Component_2) :-
    components_in_different_locations(Component_1, Component_2),
    component_in_restricted_environment(Component_1),
    component_in_restricted_environment(Component_2),
    ((messaging_communication(Channel, Component_1, Component_2),
    component_in_restricted_environment(Channel));
    direct_communication(Component_1, Component_2)).

distributed_messaging(Component_1, Component_2) :-
    messaging_communication(Channel, Component_1, Component_2),
    components_in_different_locations(Component_1, Component_2),
    hybrid_environment(Component_1, Component_2).

insecure_public_communication(Component_1, Component_2) :-
	property(Relation_ID, sensitivedata, true),
	relation(Relation_ID, Component_1, Component_2),
	relation_types(RT),
	member(Relation_ID, RT),
	member(connectsto, RT),
	components_in_different_locations(Component_1, Component_2),
	not(property(Relation_ID, encrypted, true)).
