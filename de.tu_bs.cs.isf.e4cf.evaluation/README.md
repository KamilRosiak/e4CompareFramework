# Generating Clones and Evaluating Clone Detection Tools

## Rough Plan
- [X] Super Simple Base Example
- [X] Transform into Generic Data Structure
- <Entry Point for Project 1>
- [X] Take a look at how to modify data structure (semantic)
- [X] How to create clones? -> Paper Taxonomy
- [ ] Log Modifications to Trees -> OG Location, Clone Location (File:Line), Operations (Taxonomy)
- [X] How to save Tree
- ...
- [ ] Determine Exchange Data Format [4Weeks] (FileSet<GenericDataStructures>, ..?)
  - Metadata as Log?
- <Entry Point for Project 2>
- ...



Tree Serializiation
- Broken as is
  - Migrate to Ecore -> Refactor instantiation everywhere but robustm out of the box, modification safe serializers
  - **Use Existing GSON Parser **
  - Reparse into the input format (create file copies) -> More extensible for different (non generic) clone detection tools  
