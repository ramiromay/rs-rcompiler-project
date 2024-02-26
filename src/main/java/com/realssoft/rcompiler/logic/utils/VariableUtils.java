package com.realssoft.rcompiler.logic.utils;

import com.realssoft.rcompiler.logic.language.RegularExpression;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class VariableUtils
{

    public static  boolean isVariableAssignment(@NotNull List<String> lexemeList)
    {
        return lexemeList.size() == 1;
    }

    public static boolean isVariableArithmetic(@NotNull List<String> lexemeList,
                                               @NotNull List<String> operatorsList,
                                               StringBuilder assigned)
    {
        return lexemeList.size() >= 2
                && operatorsList.size() == lexemeList.size() - 1
                && assigned.length() != 0;
    }

    public static boolean isNumber(@NotNull String lexemeNext)
    {
        return (!lexemeNext.matches("[;,\\])]") &&
                !lexemeNext.matches(RegularExpression.O_ARITHMETIC.value) &&
                !lexemeNext.matches(RegularExpression.O_LOGIC.value) &&
                !lexemeNext.matches(RegularExpression.O_ASSIGMENT.value) &&
                !lexemeNext.matches(RegularExpression.O_RELATIONAL.value));
    }

}
