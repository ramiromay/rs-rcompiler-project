package com.realssoft.rcompiler.logic.validator;

import org.jetbrains.annotations.NotNull;
import java.util.List;

public interface ArithmeticValidator
{

    void valid(
            int currentLine,
            String assign,
            @NotNull List<String> lexemeList,
            @NotNull List<String> operationsList
    );

}
