host_is_vm(Component) :-
   hosting_stack(S),
   member(Component, S),
   member(H, S),
   component_of_type(H,ubuntud4e5f614a1b2c304d4e5f6vm).

host_is_container(Component) :-
   hosting_stack(S),
   member(Component, S),
   member(H, S),
   component_of_type(H, container).
