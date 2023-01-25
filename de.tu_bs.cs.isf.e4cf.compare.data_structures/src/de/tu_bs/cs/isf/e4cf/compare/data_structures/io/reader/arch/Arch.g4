grammar Arch;

Value: [A-Za-z0-9_- :|>./]+ ;
QuotedValue: '="' Value '"';

xmlEncoding: 'encoding' QuotedValue;
xmlVersion: 'version' QuotedValue;
xmlEnd: '/>';

xmiVersion: 'xmi:version' QuotedValue;
xmlnsXmi: 'xmlns:xmi' QuotedValue;
xmlnsArchitecture: 'xmlns:architecture' QuotedValue;

id: 'xmi:id' QuotedValue;
name: 'name' QuotedValue;
signal: 'name' QuotedValue;
ports: 'ports' QuotedValue;
incomings: 'incomings' QuotedValue;
outgoings: 'outgoings' QuotedValue;
comments: 'comments' QuotedValue;
target: 'target' QuotedValue;
source: 'source' QuotedValue;
elements: 'elements' QuotedValue;
body: 'body' QuotedValue;

xml: '<?xml ' ((xmlVersion | xmlEncoding)(' ')?)+ '?>';
