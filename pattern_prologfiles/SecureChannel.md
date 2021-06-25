# Secure Channel

## Problem and Context
How do we ensure that data being passed across public or semi-public space is secure in transit?

The system delivers functionality and information to the clients across the public Internet through one or more Web servers. [...] The application must exchange data with the client. A percentage of this data will be sensitive in nature.

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
