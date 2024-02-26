package com.realssoft.rcompiler.logic.language;

public enum Operator
{

    RAM("[+\\-*]"),
    JOA("[+]"),
    EDU("[+\\-*/%]");

    public final String expression;

    Operator(String expression)
    {
        this.expression = expression;
    }

}
