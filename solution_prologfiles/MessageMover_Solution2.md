# Testing

## Description
The Ipsec encrytion solution can be used to secure the communication between two components.
This can only be used if the communicating components are natively hosted on virtual machines.
Using this mechanism the communication is encrypted on level 3 of the ISO/OSI-Model.

## Related Patterns

* [Application Component Proxy](../pattern_prologfiles/ApplicationComponentProxy.md)

## Selection Criteria

```prolog
test(Component_1, Component_2) :-
   host_is_vm(Component_1),
   host_is_vm(Component_2).
```

## Concrete Solution Implementation

[Winery Algorithm](https://github.com/eclipse/winery)
