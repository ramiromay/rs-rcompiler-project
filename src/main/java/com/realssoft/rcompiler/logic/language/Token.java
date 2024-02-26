package com.realssoft.rcompiler.logic.language;

public enum Token
{

    SEPARATORS("SPR"),
    O_ARITHMETIC("OA"),
    O_RELATIONAL("OR"),
    O_ASSIGMENT("AS"),
    O_LOGIC("OL"),
    ID("ID"),
    NUMS_INTEGERS("NE"),
    NUMS_FLOAT("ND"),
    STRING("STR"),
    TYPE_DATA("TD"),
    TEAM_7("IR"),
    LEXICAL_ERROR("FEL"),
    ERROR_SYNTAX("FES"),
    ERROR_SEMANTIC_INCOMPATIBLE("FESI"),
    ERROR_SEMANTIC_UNDEFINED("FESU");

    public final String value;

    Token(String value)
    {
        this.value = value;
    }

}
