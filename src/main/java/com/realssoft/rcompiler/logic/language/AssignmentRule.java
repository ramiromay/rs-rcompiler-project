package com.realssoft.rcompiler.logic.language;

import lombok.Getter;

@Getter
public enum AssignmentRule
{

    RAM(DataType.RAM),
    EDU(DataType.EDU),
    JOA(DataType.JOA);

    private final DataType rule;

    AssignmentRule(DataType rule)
    {
        this.rule = rule;
    }

}
