# Message Mover

## Problem
distributed messaging because of restricted environment which contains the messaging middleware

## Problem Rule

```prolog
distributed_messaging(Component, Channel) :-
    component_of_type(Channel, channel),
    direct_access_to_restricted_environment(Component, Channel).
```

## Solution Description
A message mover is used to integrate message queues hosted in different environments [...].
