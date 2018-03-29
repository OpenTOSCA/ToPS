# Topology-ProDec - A Pattern-based Problem Detection Framework for TOSCA Topologies

![build status](https://circleci.com/gh/saatkamp/topology-prodec.svg?style=shield&circle-token=:circle-token)

The Topology-ProDec can be used to identify problems in TOSCA topology templates. Based on known architecture and design knowledge in form of patterns these problems are formalized as Prolog rules. Patterns can be stored as markdown files with a predefined structure. Based on these pattern files a Prolog file is generated.
A TOSCA topology template which shall be checked is transformed in aProlog file containing the topology elements as Prolog facts.
The topology facts are checked with the contained patterns.
A JSON file with the detected problems are returned.


## Project Structure

- All pattern markdown files are stored in [pattern-prologfiles](https://github.com/saatkamp/topology-prodec/tree/master/pattern_prologfiles). An example is the [Secure Channel](https://github.com/saatkamp/topology-prodec/blob/master/pattern_prologfiles/SecureChannel.md). This structure must also be adhered to for all other patterns.
- The generated file [patterns.pl](https://github.com/saatkamp/topology-prodec/blob/master/pattern_prologfiles/patterns.pl) contains all Prolog rules from the pattern files.
- The [helper.pl](https://github.com/saatkamp/topology-prodec/blob/master/pattern_prologfiles/helper.pl) encompasses additinal rules which ease the description of the pattern rules.
- The generated TOSCA topologies as Prolog facts are contained in the [topologies](https://github.com/saatkamp/topology-prodec/tree/master/topologies) folder.

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
