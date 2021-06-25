# IPsec Encryption

## Description
The Ipsec encrytion solution can be used to secure the communication between two components.
This can only be used if the communicating components are natively hosted on virtual machines.
Using this mechanism the communication is encrypted on level 3 of the ISO/OSI-Model.

## Related Patterns

* [Secure Channel](../pattern_prologfiles/SecureChannel.md)

## Selection Criteria

```prolog
ipsec(Component_1, Component_2) :-
   host(Component_1, Host_1, vm),
   host(Component_2, Host_2, vm).
```

## Concrete Solution Implementation

[IPsec](https://github.com/eclipse/winery)


