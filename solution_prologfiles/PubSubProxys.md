# IPsec Encryption

## Description
The PubSubProxys solution can be used to change the messaging between components from HTTP-based communication 
to messaging-based communication without customizing the components themselves. This is done by implementing 
the publish-sucribe pattern. The solution is independent of the specific message broker and protocol used.

## Related Patterns

* [Application Component Proxy](../pattern_prologfiles/ApplicationComponentProxy.md)

## Selection Criteria

```prolog
ipsec(Component_1, Component_2) :-
   host(Component_1, Host_1, vm),
   host(Component_2, Host_2, vm).
```

## Concrete Solution Implementation

[PubSubProxys](https://github.com/eclipse/winery)

