# Secure Channel

## Problem
Insecure Communication Channel

## Problem Rule

```prolog
insecure_public_communication(Component_1, Component_2) :-
	property(Relation_ID, sensitivedata, true),
	relation_of_type(Relation_ID, httpconnectsto),
	relation(Component_1, Component_2, Relation_ID),
	components_in_different_locations(Component_1, Component_2),
	not(property(Relation_ID, security, true)).
```

## Solution Description
Create secure channels for sensitive data that obscure
the data in transit. Exchange information between
client and server to allow them to set up encrypted
communication between themselves. [...]
