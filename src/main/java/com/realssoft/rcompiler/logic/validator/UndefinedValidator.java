package com.realssoft.rcompiler.logic.validator;

import org.jetbrains.annotations.NotNull;
import java.util.List;

public interface UndefinedValidator
{

    boolean valid(
            int currentLine,
            String assign,
            @NotNull List<String> lexemeList
    );

}
