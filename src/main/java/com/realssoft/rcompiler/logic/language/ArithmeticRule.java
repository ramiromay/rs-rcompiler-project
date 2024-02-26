package com.realssoft.rcompiler.logic.language;

import lombok.Getter;

@Getter
public enum ArithmeticRule
{

    RAM_OPA_RAM(DataType.RAM, Operator.RAM, DataType.RAM),
    JOA_OPA_JOA(DataType.JOA, Operator.JOA, DataType.JOA),
    EDU_OPA_EDU(DataType.EDU, Operator.EDU, DataType.EDU),
    EDU_OPA_RAM(DataType.EDU, Operator.EDU, DataType.RAM);

    private final DataType ruleOne;
    private final Operator operator;
    private final DataType ruleTwo;

    ArithmeticRule(DataType ruleOne, Operator operator, DataType ruleTwo)
    {
        this.ruleOne = ruleOne;
        this.operator = operator;
        this.ruleTwo = ruleTwo;
    }

}
