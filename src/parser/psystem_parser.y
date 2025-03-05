%{
  import java.io.*;
  import datastructure.*;
  import system.*;
import datastructure.Cell;
import datastructure.Configuration;
import datastructure.multiset.Multiset;
import datastructure.multiset.Symbol;
import datastructure.rule.Rule;
import datastructure.rule.RuleSet;
import java.util.ArrayList;

%}

%token <i> NB
%token TO CNAME RNAME
%token <s> ID

%type <en> MElement 
%type <ms> Multiset 

%type <cl> Cell

%type <conf> Conf NEConf

%type <ps> Start

%type <rs> RuleList

%type <rule> Rule

%type <sconf> SetConfList SetConf

%%

Start: CNAME Conf RNAME RuleList {$$=new PSystem($2,$4);}


RuleList: Rule {$$=new RuleSet();$$.add($1);}
| RuleList Rule {$$=$1; $$.add($2);}
;

Rule: Conf TO Conf ';' SetConf ';' SetConf {$$=new Rule($1,$3,$5,$7);}

SetConf: '{' '}'   {$$=new ArrayList<Configuration>();}
|'{' SetConfList '}'   {$$=$2;}
;

SetConfList:  NEConf  {$$=new ArrayList<Configuration>();$$.add($1);}
| SetConfList ',' NEConf   {$$=$1;$1.add($3);}
;


Conf:         {$$=new Configuration();}
| Conf Cell {$$=$1;$$.add($2);}
;

NEConf:  Cell       {$$=new Configuration();$$.add($1);}
| NEConf Cell {$$=$1;$$.add($2);}
;


Cell: '(' NB ','  Multiset ')' {$$=new Cell($2,$4);}
;

Multiset:      {$$=new Multiset<Symbol>();}
| Multiset MElement {$$=$1;$$.add($2.s,$2.val);}
;

MElement: ID {$$=new Pair(table.get($1),1);}
| '[' ID ',' NB ']' {$$=new Pair(table.get($2),$4);}
; 

%%

  /* a reference to the lexer object */
  private Yylex lexer;
  private SymbolTable table;

  public int line_no = 1;

  /* interface to the lexer */
  private int yylex () {
    int yyl_return = -1;
    try {
      yyl_return = lexer.yylex();
    }
    catch (IOException e) {
      System.err.println("IO error :"+e);
    }
    return yyl_return;
  }

  /* error reporting */
  public void yyerror (String error) {
    System.err.println ("Error: " + error + " on line " + line_no);
  }

  /* lexer is created in the constructor */
  public Parser(Reader r) {
    table = new SymbolTable();
    lexer = new Yylex(r, this);
  }

  public Pval getValue(){
    return yyval;
  }
