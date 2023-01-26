parser grammar ArchParserGrammar;

options { tokenVocab=ArchLexerGrammar; }

archfile    :   prolog? architecture SEA_WS* EOF ;

prolog      :   XMLDeclOpen attribute* SPECIAL_CLOSE SEA_WS+ ;

architecture :	'<' Name attribute* '>' chardata? ((component | element) chardata?)*?  '<' '/' Name '>' ;

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