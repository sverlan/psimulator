package parser;

%%

%{
  StringBuffer string = new StringBuffer();

  private Parser parser;

  public Yylex(java.io.Reader r, Parser parser) {
    this(r);
    this.parser = parser;
  }
%}


%byaccj

nb=[0-9]+
id=[a-zA-Z]+[a-zA-Z0-9'_]*

%%

\#.*$ {}

{nb} {parser.yylval = new Pval(Integer.parseInt(yytext())); return parser.NB;}

Configuration|CONFIGURATION {return parser.CNAME;} 

Rules|RULES {return parser.RNAME;}

{id} { parser.yylval = new Pval(yytext()); return parser.ID;}

->  {return parser.TO;}

[ ] | \t {}
\r\n| \r | \n  {parser.line_no++;}
. {return yycharat(0);}

