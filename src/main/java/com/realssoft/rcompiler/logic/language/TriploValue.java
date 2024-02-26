package com.realssoft.rcompiler.logic.language;

import lombok.Getter;

@Getter
public enum TriploValue
{

    JMP("JMP"),
    ASSIGNMENT("="),
    T1("T1"),
    TR1("TR1"),
    TRUE("TRUE"),
    FALSE("FALSE");

    private final String value;

    TriploValue(String value)
    {
        this.value = value;
    }

}
