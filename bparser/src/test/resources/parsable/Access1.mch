/* Animating add for a while leads to a bus error of SICStus Prolog !!! */

MACHINE           Access

SETS              USER; PRINTER; OPTION; PERMISSION = {ok, noaccess}

CONSTANTS         options

PROPERTIES        options : PRINTER <-> OPTION & dom(options) = PRINTER & ran(options) = OPTION

VARIABLES         access

INVARIANT         access : USER <-> PRINTER

INITIALISATION    access := {}

OPERATIONS

  add(uu, pp) =
  PRE uu : USER & pp : PRINTER 
  THEN access := access \/ {uu |-> pp}
  END;

  block(uu,pp) =
  PRE uu : USER & pp : PRINTER 
  THEN access := access - {uu |-> pp}
  END;

  ban(uu) =
  PRE uu : USER
  THEN access := {uu} <<| access
  END;

  unify(u1, u2) =
  PRE u1 : USER & u2 : USER 
  THEN access := access \/ {u1}*access[{u2}] 
                        \/ {u2}*access[{u1}]
  END;

ans <-- optionquery(uu,oo) =
  PRE uu : USER & oo : OPTION
  THEN IF uu |-> oo : (access;options)
       THEN ans := ok
       ELSE ans := noaccess
       END
  END;

nn <-- printnumquery(pp) =
  PRE pp : PRINTER
  THEN nn := card(access |> {pp})
  END

END




