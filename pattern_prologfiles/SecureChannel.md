# Secure Channel

## Problem
Insecure Communication

## Problem Rule

```prolog
insecure_public_communication(Component_1, Component_2) :-
	property(Relation_ID, sensitivedata, true),
	relation(Relation_ID, Component_1, Component_2),
	relation_types(RT),
	member(Relation_ID, RT),
	member(connectsto, RT),
	components_in_different_locations(Component_1, Component_2),
	not(property(Relation_ID, encrypted, true)).
```

## Solution Description
Create secure channels for sensitive data that obscure
the data in transit. Exchange information between
client and server to allow them to set up encrypted
communication between themselves. [...]

## Reference
M. Schumacher, E. Fernandez-Buglioni, D. Hybertson, F. Buschmann, P. Sommerlad. *Security Patterns: Integrating Security and Systems Engineering*. John Wiley & Sons, Inc., 2006.
