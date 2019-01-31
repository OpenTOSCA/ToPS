ipsec(Component_1, Component_2) :-
   host(Component_1, Host_1, ubuntud4e5f614a1b2c304d4e5f6vm),
   host(Component_2, Host_2, ubuntud4e5f614a1b2c304d4e5f6vm).

tlsonvm(Component_1, Component_2) :-
   host(Component_1, Host_1, ubuntud4e5f614a1b2c304d4e5f6vm),
   host(Component_2, Host_2, ubuntud4e5f614a1b2c304d4e5f6vm),
   relation_of_type(Relation_ID, connectsto),
   relation(Component_1, Component_2, Relation_ID).
