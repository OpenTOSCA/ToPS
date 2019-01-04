ipsec(Component_1, Component_2) :-
   host_is_vm(Component_1),
   host_is_vm(Component_2).

proxies(Component_1, vm, Component_2, container) :-
    host_is_vm(Component_1),
    host_is_container(Component_2).

proxies(Component_1, vm, Component_2, vm) :-
    host_is_vm(Component_1),
    host_is_vm(Component_2).

proxies(Component_1, container, Component_2, vm) :-
    host_is_container(Component_1),
    host_is_vm(Component_2).
