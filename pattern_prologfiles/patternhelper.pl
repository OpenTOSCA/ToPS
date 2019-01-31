components_in_different_locations(Component_1, Component_2) :-
	hosting_stack(S1),
	hosting_stack(S2),
	property(H1, location, Location_1),
	property(H2, location, Location_2),
	member(Component_1, S1),
	member(H1, S1),
	member(Component_2, S2),
	member(H2, S2),
	Location_1 \= Location_2.

component_in_restricted_environment(Component_1) :-
    property(P, inboundcommunication, false),
    hosting_stack(S),
    member(Component_1, S),
    member(P,S).

hybrid_environment(Component_1, Component_2) :-
    components_in_different_locations(Component_1, Component_2),
    component_in_restricted_environment(Component_1),
    not(component_in_restricted_environment(Component_2)).

direct_communication(Component_1, Component_2) :-
    relation_of_type(Relation_ID, httpconnectsto),
    relation(Component_1, Component_2, Relation_ID),
    not(component_of_type(Component_2, channelg7h8i9w1d4e5f6wip1)).

messaging_communication(Channel, Component_1, Component_2) :-
    component_of_type(Channel, channelg7h8i9w1d4e5f6wip1),
    relation(Component_1, Channel, Relation1),
    relation(Component_2, Channel, Relation2),
    relation_of_type(Relation1, httpconnectsto),
    relation_of_type(Relation2, httpconnectsto).
