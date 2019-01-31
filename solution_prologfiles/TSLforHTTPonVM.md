# TLS for HTTP on VM

## Description
The TLS for HTTP on VM solution inserts proxies for the communication between the two communicating components.
The proxies enable a secure communication.

## Related Patterns

* [Secure Channel](../pattern_prologfiles/SecureChannel.md)

## Selection Criteria

```prolog
tlsonvm(Component_1, Component_2) :-
   host(Component_1, Host_1, ubuntud4e5f614a1b2c304d4e5f6vm),
   host(Component_2, Host_2, ubuntud4e5f614a1b2c304d4e5f6vm),
   relation_of_type(Relation_ID, httpconnectsto),
   relation(Component_1, Component_2, Relation_ID).
```

## Concrete Solution Implementation

[secureVmProxy](https://github.com/eclipse/winery)
