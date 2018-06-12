# Message Mover

## Problem
distributed messaging because of restricted environment which contains the messaging middleware

## Problem Rule

```prolog
distributed_messaging(Component_1, Component_2) :-
    messaging_communication(Channel, Component_1, Component_2),
    components_in_different_locations(Component_1, Component_2),
    hybrid_environment(Component_1, Component_2).
```

## Solution Description
A message mover is used to integrate message queues hosted in different environments [...].
