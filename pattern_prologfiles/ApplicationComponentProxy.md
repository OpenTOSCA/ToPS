# Application Component Proxy

## Problem
Direct access is restricted to the accessed component

## Problem Rule

```prolog
direct_access_to_restricted_environment(Component_1, Component_2) :-
	direct_communication(Component_1, Component_2), 
	components_in_different_locations(Component_1, Component_2),
	component_in_restricted_environment(Component_2).
```

## Solution Description
The interface of a restricted application component is
duplicated to form a proxy component. Synchronous
and asynchronous communications with the proxy
component is initiated and maintained from the restricted
environment [...]
