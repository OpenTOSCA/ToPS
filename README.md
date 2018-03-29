# Topology-ProDec - A Pattern-based Problem Detection Framework for TOSCA Topologies

## Setup

1. Install SWI Prolog at your system - [SWI Download](http://www.swi-prolog.org/download/stable)
2. Add SWI to the environment variable PATH of your system (e.g. C:\Program Files\swipl\bin)
3. Make sure that a Winery instance is running under port 8080 - [Winery Setup Information](https://github.com/OpenTOSCA/winery)
4. Checkout the topology-prodec repository and run the application
5. Use a REST-Client to call the Topology-ProDec. A call must look like this:
`http://[IP address]:[port]/recognize?wineryURL=[encoded Winery URL for the specific service template]`
Example:
`http://localhost:8090/recognize?wineryURL=http%3A%2F%2Flocalhost%3A8080%2Fwinery%2F&serviceTemplateNS=http%3A%2F%2Fwww.example.org%2Ftosca%2Fservicetemplates&serviceTemplateID=Problem_Recognition_Example_Unencrypted_Communication`
6. As a result a JSON with the problem, the pattern, and the components for which the problem has been found are returned.

## Haftungsausschluss

Dies ist ein Forschungsprototyp.
Die Haftung für entgangenen Gewinn, Produktionsausfall, Betriebsunterbrechung, entgangene Nutzungen, Verlust von Daten und Informationen, Finanzierungsaufwendungen sowie sonstige Vermögens- und Folgeschäden ist, außer in Fällen von grober Fahrlässigkeit, Vorsatz und Personenschäden, ausgeschlossen.

## Disclaimer of Warranty

Unless required by applicable law or agreed to in writing, Licensor provides the Work (and each Contributor provides its Contributions) on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied, including, without limitation, any warranties or conditions of TITLE, NON-INFRINGEMENT, MERCHANTABILITY, or FITNESS FOR A PARTICULAR PURPOSE.
You are solely responsible for determining the appropriateness of using or redistributing the Work and assume any risks associated with Your exercise of permissions under this License.
