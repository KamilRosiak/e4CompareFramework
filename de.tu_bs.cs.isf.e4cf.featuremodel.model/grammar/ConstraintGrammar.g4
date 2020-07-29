grammar ConstraintGrammar;

formula
    : equals_formula
    ;

equals_formula 
    : implies_formula (EQUALS implies_formula)*
    ;

implies_formula 
    : or_formula (IMPLIES or_formula)*
    ;

or_formula 
    : and_formula (OR and_formula)*
    ;

and_formula
    :  primitive (AND primitive)*
    ;
primitive 
	: negation? literal 
	| negation? (LEFT_BRACKET formula RIGHT_BRACKET)    
	;

negation 
	: NEGATION
	;
	
literal
	:  IDENTIFER
	;

LEFT_BRACKET
	: '('
	;
	
RIGHT_BRACKET
	: ')'
	;
	
NEGATION
	: 'NOT'
	;

AND
	: 'AND'
	;
	
OR
	: 'OR'
	;
	
IMPLIES
	: 'IMPLIES'
	;
	
EQUALS
	: 'EQUALS'
	;
	
IDENTIFER
	: ALPHANUMERIC+
	;
	
fragment ALPHANUMERIC // alphabetical letters
	: [A-Z] | [a-z] | [0-9] | '_'
	;
	
WHITESPACE  : (' ' | '\t' | '\r' | '\n')+ {skip();} ;
	