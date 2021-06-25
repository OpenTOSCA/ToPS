ipsec(Component_1, Component_2) :-
   host(Component_1, Host_1, vm),
   host(Component_2, Host_2, vm).

tlsonvm(Component_1, Component_2) :-
   host(Component_1, Host_1, vm),
   host(Component_2, Host_2, vm),
   relation(Relation_ID, Component_1, Component_2),
   relation_types(RT),
   member(Relation_ID, RT),
   member(httpconnectsto, RT).
