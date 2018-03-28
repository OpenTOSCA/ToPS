# Application Component Proxy

## Problem
Direct access is restricted to the accessed component

## Problem Rule

```prolog
direct_access_to_restricted_environment(Component_1, Component_2) :-
	relation_of_type(Relation_ID, connectsto),
	relation(Component_1, Component_2, Relation_ID),
	components_in_different_locations(Component_1, Component_2),
	property(H, inboundcommunication, false),
	hosting_stack(S),
	member(Component_2, S),
	member(H, S).
```

## Solution Description
The interface of a restricted application component is
duplicated to form a proxy component. Synchronous
and asynchronous communications with the proxy
component is initiated and maintained from the restricted
environment [...]
