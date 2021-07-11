# Generating Clones and Evaluating Clone Detection Tools

## Rough Plan
- [X] Super Simple Base Example
- [X] Transform into Generic Data Structure
- <Entry Point for Project 1>
- [X] Take a look at how to modify data structure (semantic)
- [X] How to create clones? -> Paper Taxonomy
- [X] Log Modifications to Trees -> OG Location, Clone Location (File:Line), Operations (Taxonomy)
- [X] How to save Tree
- [ ] Determine nodes to clone
- [X] Look into Taxonomy (What can we do just on nodes? What attributes do we need for the rest of the taxonomy?)
- [ ] Call random clone operations
- [X] Folder selection support
- [X] UI Settings (granularity, type weights, number of mutations, ...)
- [ ] Crossover operations
- [X] Implement Multipass
- [ ] Implement Granularity
- [X] Implement Type Weights
- [ ] Optional: Taxonomy soundness
- [X] Optional: output every clone
- [X] Determine Exchange Data Format [4Weeks] (FileSet<GenericDataStructures>, ..?)
  - Metadata as Log?
- <Entry Point for Project 2>
- [X] Read outputs from project 1
- [ ] Clone detection: Implement metrics (recall & precision)
- [ ] Variability Mining
- [ ] Taxonomy Mining
- [ ] Add output UI (view?)
- [ ] Add output file format
- <Framework Issues>
- [X] Standardized Node Types
- [X] Parser Structure (If-ElseIf, )
- [X] Introduce Expressions (Decorate Subexpressions of a Node as Label)
- [ ] Extract ExpressionOperators into Enum
- [X] Multi-Variable Initilization broken
- [X] Fix instant parsing when right clicking
- [X] Do-While has duplicate attribute "condition" but with different key "value"

===== 16.06.2021
Seeds als Base-File
Repository of Cloneable Code
Empty nodes are great targets for duplication.
Nodes with Attributes are great candidates for cloning but respect their containment.
Xtend ok.


===== 23.06.2021
AttributeTypes (Keys): Welche gibt es auf generischer Ebene?
Ist die Tree Structure zu generisch? Wie kriegen wir konkrete sachen auf die wir arbeiten können?
Brauchen wir constraints? Allgemein/Domänenabhängig?
Nehmen wir den Java Parser wie er ist oder müssen wir ihn gegebenenfalls ändern?
Warum nehmen wir den Java Parser um Tree-Structures zu erzeugen? Das ist völlig biased. Ohne Grammatik für den Tree sind diese analysen nur pseudo-generisch.
-> Function Parameter sind Nodes aber Expressions sind Argument-Strings.
Wie wird entschieden was als Nodes und was als Attribute Values dargestellt wird?

Attribute haben nur einen Value (für uns)
Was ist Node was ist Attribut -> erstmal undefiniert. Alles was in java leaf ist in tree attribut.
Wir stellen passende constraints an die Datenstruktur passend zum Javaparser (später Ungereimtheiten in lessons learnt)
Möglicherweise Config für Generator (Welche Nodes dürfen angefasst werden? Sprachausnahmen? Grammatik. Baumtyp)

===== 30.06.2021
Annahme: Initiale ground truth is clone free -> else overfitting?
Clone Granularity Discussion
 - Änderungen an Granularity Level source node selection ein (Statement => Statements, Methods => Signatures, Classes => Packages & Imports?)
 
===== 07.07.2021
GitHub JavaParser gibt momentan JavaAST als generische Datenstruktur vor. Manuelles, rekursives parsen der AST 
könnte da eine Abstraktionsebene draufsetzen, kostet uns aber ca. eine Woche. 
Wollen wir etwas anderes als AST in unserer GenericDatastructure darstellen?
- Definitiv auf StandardizedNodeType bleiben, access zu JavaNodeType deutet auf probleme im StandardNodeType hin
Wollen wir potentiell verschiedene AST vergleichen (C <> Java)?

===== 14.07.2021
- Compare Engine Reorders Nodes -> TaxonomyExample D
- Discuss Programflows

