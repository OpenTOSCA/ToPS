# Integration Provider
## Problem
Integration of application components that reside in different environments which are restricted

## Problem Rule

```prolog
integration_of_restricted_environments(Component_1, Component_2) :-
    components_in_different_locations(Component_1, Component_2),
    component_in_restricted_environment(Component_1),
    component_in_restricted_environment(Component_2),
    ((messaging_communication(Channel, Component_1, Component_2),
    component_in_restricted_environment(Channel));
    direct_communication(Component_1, Component_2)).
```

## Solution Description
The distributed applications or their components communicate using integration components offered by a third party provider.
