# TLS for HTTP on VM

## Description
The TLS for HTTP on VM solution inserts proxies for the communication between the two communicating components.
The proxies enable a secure communication.

## Related Patterns

* [Secure Channel](../pattern_prologfiles/SecureChannel.md)

## Selection Criteria

```prolog
tlsonvm(Component_1, Component_2) :-
   host(Component_1, Host_1, vm),
   host(Component_2, Host_2, vm),
   relation(Relation_ID, Component_1, Component_2),
   relation_types(RT),
   member(Relation_ID, RT),
   member(httpconnectsto, RT).
```

## Concrete Solution Implementation

[secureVmProxy](https://github.com/eclipse/winery)
