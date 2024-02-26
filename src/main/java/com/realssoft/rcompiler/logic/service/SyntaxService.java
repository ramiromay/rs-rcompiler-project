package com.realssoft.rcompiler.logic.service;

import com.realssoft.rcompiler.logic.constant.MessageConstant;
import com.realssoft.rcompiler.logic.exception.GlobalExceptionHandler;
import com.realssoft.rcompiler.logic.language.DataType;
import com.realssoft.rcompiler.logic.language.RegularExpression;
import com.realssoft.rcompiler.logic.language.Token;
import com.realssoft.rcompiler.logic.utils.VariableUtils;
import org.jetbrains.annotations.NotNull;

public class SyntaxService
{

    private final StringBuilder lexeme;
    private final StringBuilder lexemeNext;

    public SyntaxService()
    {
        lexeme = new StringBuilder();
        lexemeNext = new StringBuilder();
    }

    public void analyze(@NotNull String lexemeNext, @NotNull String lexeme, int currentLine)
    {
        if(lexeme.isEmpty()) return;
        if(lexeme.matches(RegularExpression.SEPARATORS.value))
        {
            if(lexeme.matches("[,]") && (!lexemeNext.matches("\\w+")))
            {
                GlobalExceptionHandler.saveException(
                        currentLine,
                        lexeme,
                        DataType.NULL,
                        MessageConstant.SYNTAX_ERROR,
                        Token.ERROR_SYNTAX
                );
            }
            return;
        }
        if(lexeme.matches(RegularExpression.O_ARITHMETIC.value))
        {
            if(!lexemeNext.matches("(-?)\\d+|(-?)\\d+\\.\\d+|\\w+|\"[^\"]*\""))
            {
                GlobalExceptionHandler.saveException(
                        currentLine,
                        lexeme,
                        DataType.NULL,
                        MessageConstant.SYNTAX_ERROR,
                        Token.ERROR_SYNTAX
                );
            }
            return;
        }
        if(lexeme.matches(RegularExpression.O_RELATIONAL.value))
        {
            if(!lexemeNext.matches("(-?)\\d+|(-?)\\d+\\.\\d+|\\w+|\"[^\"]*\""))
            {
                GlobalExceptionHandler.saveException(
                        currentLine,
                        lexeme,
                        DataType.NULL,
                        MessageConstant.SYNTAX_ERROR,
                        Token.ERROR_SYNTAX
                );
            }
            return;
        }
        if(lexeme.matches(RegularExpression.O_ASSIGMENT.value))
        {
            if(!lexemeNext.matches("(-?)\\d+|(-?)\\d+\\.\\d+|\\w+|\"[^\"]*\""))
            {
                if(lexemeNext.matches("(;,)"))
                {
                    GlobalExceptionHandler.saveException(
                            currentLine,
                            lexeme,
                            DataType.NULL,
                            MessageConstant.SYNTAX_ERROR,
                            Token.ERROR_SYNTAX
                    );
                    return;
                }
                GlobalExceptionHandler.saveException(
                        currentLine,
                        lexemeNext,
                        DataType.NULL,
                        MessageConstant.SYNTAX_ERROR,
                        Token.ERROR_SYNTAX
                );
            }
            return;
        }
        if(lexeme.matches(RegularExpression.O_LOGIC.value))
        {
            if(!lexemeNext.matches("(-?)\\d+|(-?)\\d+\\.\\d+|\\w+"))
            {
                GlobalExceptionHandler.saveException(
                        currentLine,
                        lexeme,
                        DataType.NULL,
                        MessageConstant.SYNTAX_ERROR,
                        Token.ERROR_SYNTAX
                );
            }
            return;
        }
        if(lexeme.matches(RegularExpression.ID.value))
        {
            if(!lexemeNext.matches("([=,;+)\\-*/%])")
                    && !lexemeNext.matches(RegularExpression.O_LOGIC.value)
                    && !lexemeNext.matches(RegularExpression.O_RELATIONAL.value))
            {
                GlobalExceptionHandler.saveException(
                        currentLine,
                        lexeme,
                        DataType.NULL,
                        MessageConstant.SYNTAX_ERROR,
                        Token.ERROR_SYNTAX
                );
            }
            return;
        }
        if(lexeme.matches(RegularExpression.NUMS_INTEGERS.value))
        {
            if(VariableUtils.isNumber(lexemeNext))
            {
                GlobalExceptionHandler.saveException(
                        currentLine,
                        lexeme,
                        DataType.NULL,
                        MessageConstant.SYNTAX_ERROR,
                        Token.ERROR_SYNTAX
                );
            }
            return;
        }
        if(lexeme.matches(RegularExpression.NUMS_FLOAT.value))
        {
            if(VariableUtils.isNumber(lexemeNext))
            {
                GlobalExceptionHandler.saveException(
                        currentLine,
                        lexeme,
                        DataType.NULL,
                        MessageConstant.SYNTAX_ERROR,
                        Token.ERROR_SYNTAX
                );
            }
            return;
        }
        if (lexeme.matches(RegularExpression.STRING.value))
        {
            if (!lexemeNext.matches("([;+\\-*/%])"))
            {
                GlobalExceptionHandler.saveException(
                        currentLine,
                        lexeme,
                        DataType.NULL,
                        MessageConstant.SYNTAX_ERROR,
                        Token.ERROR_SYNTAX
                );
            }
            return;
        }
        if(lexeme.matches(RegularExpression.DATATYPE.value))
        {
            if(!lexemeNext.matches("\\w+"))
            {
                GlobalExceptionHandler.saveException(
                        currentLine,
                        lexeme,
                        DataType.NULL,
                        MessageConstant.SYNTAX_ERROR,
                        Token.ERROR_SYNTAX
                );
            }
            return;
        }
        if(lexeme.matches(RegularExpression.TEAM_7.value)
                && (!lexemeNext.matches("[(]")))
        {
            GlobalExceptionHandler.saveException(
                    currentLine,
                    lexeme,
                    DataType.NULL,
                    MessageConstant.SYNTAX_ERROR,
                    Token.ERROR_SYNTAX
            );
        }
    }

    public void verify(@NotNull String @NotNull [] input, int currentLine)
    {
        for (int column = 0; column < input.length; column++)
        {
            lexeme.append(input[column]);
            if(column != input.length - 1)
                lexemeNext.append(input[column + 1]);
            analyze(
                    lexemeNext.toString(),
                    lexeme.toString(),
                    currentLine
            );
            if(column != input.length - 1)
                lexemeNext.setLength(0);
            lexeme.setLength(0);
        }
    }

}
