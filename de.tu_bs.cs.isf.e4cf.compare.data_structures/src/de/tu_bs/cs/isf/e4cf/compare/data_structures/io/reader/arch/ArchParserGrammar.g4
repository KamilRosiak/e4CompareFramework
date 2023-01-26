parser grammar ArchParserGrammar;

options { tokenVocab=ArchLexerGrammar; }

archfile    :   prolog? architecture SEA_WS* EOF ;

prolog      :   XMLDeclOpen attribute* SPECIAL_CLOSE SEA_WS+ ;

architecture :	OPEN Name attribute* '>' chardata? ((component | element) chardata?)*  '<' '/' Name '>' ;

component	:	'<' ComponentName attribute* '>' content '<' '/' ComponentName '>' ;

content     :   chardata?
                ((element | reference | CDATA | PI | COMMENT) chardata?)* ;

element     :   '<' Name attribute* '>' content '<' '/' Name '>'
            |   '<' Name attribute* '/>'
            ;

reference   :   EntityRef | CharRef ;

attribute   :   Name '=' VALUE ;

/** ``All text that is not markup constitutes the character data of
 *  the document.''
 */
chardata    :   TEXT | SEA_WS ;

misc        :   COMMENT | PI | SEA_WS ;