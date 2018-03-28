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
