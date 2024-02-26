package com.realssoft.rcompiler.logic.language;

public enum RegularExpression
{

    SEPARATORS("[;,\\.:\\(\\)\\[\\]\\{\\}]"),
    O_ARITHMETIC("[+\\-*/%]"),
    O_RELATIONAL("(<=|>=|<|>|==|!=)"),
    O_ASSIGMENT("="),
    O_LOGIC("(&&|\\|\\|)"),
    ID("real[A-Z][a-z_\\d]*"),
    NUMS_INTEGERS("(-?)7\\d+7"),
    NUMS_FLOAT("(-?)7\\d+7.\\d+"),
    STRING("\"[^\"]*\""),
    DATATYPE("(RAM|EDU|JOA)"),
    TEAM_7("7while7");

    public final String value;

    RegularExpression(String value)
    {
        this.value = value;
    }

}
