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
    relation(Relation_ID, Component_1, Component_2),
    relation_types(RT),
    member(Relation_ID, RT),
    member(connectsto, RT),
    component_types(CT),
    member(Component_2, CT),
    not(member(channel, CT)).


messaging_communication(Channel, Component_1, Component_2) :-
    component_types(CT),
    member(Channel, CT),
    member(channel, CT),
    relation(Relation1, Component_1, Channel),
    relation(Relation2, Component_2, Channel),
    relation_types(RT1),
    member(Relation1, RT1),
    member(connectsto, RT1),
    relation_types(RT2),
    member(Relation1, RT2),
    member(connectsto, RT2).
