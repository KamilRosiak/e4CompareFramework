parser grammar UMLParser;

options { tokenVocab=UMLLexer; }

document    :   prolog? root SEA_WS* EOF ;

prolog      :   XMLDeclOpen attribute* SPECIAL_CLOSE SEA_WS+ ;

root :	'<' Name attribute* '>' chardata? ((component | element) chardata?)*?  '<' '/' Name '>' ;

component	:	'<' Name attribute* '>' content '<' '/' Name '>' ;

content     :   chardata?
                ((element | component | reference | CDATA | PI | COMMENT) chardata?)* ;

element     :  '<' Name attribute* '/>' ;

reference   :   EntityRef | CharRef ;

attribute   :   Name '=' VALUE ;

/** ``All text that is not markup constitutes the character data of
 *  the document.''
 */
chardata    :   TEXT | SEA_WS ;

misc        :   COMMENT | PI | SEA_WS ;