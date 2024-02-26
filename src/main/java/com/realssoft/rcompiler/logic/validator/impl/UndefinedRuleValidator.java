package com.realssoft.rcompiler.logic.validator.impl;

import com.realssoft.Main;
import com.realssoft.rcompiler.logic.constant.MessageConstant;
import com.realssoft.rcompiler.logic.exception.GlobalExceptionHandler;
import com.realssoft.rcompiler.logic.language.DataType;
import com.realssoft.rcompiler.logic.language.Token;
import com.realssoft.rcompiler.logic.validator.UndefinedValidator;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class UndefinedRuleValidator implements UndefinedValidator
{

    @Override
    public boolean valid(int currentLine, String assign, @NotNull List<String> lexemeList)
    {
        AtomicBoolean isAllDeclared = new AtomicBoolean(true);
        Main.TOKEN_MAP.values()
                .stream()
                .filter(tm -> tm.getLexeme().contentEquals(assign))
                .findFirst()
                .ifPresent(tokenModel ->
                {
                    if (tokenModel.getDatatype() == null)
                    {
                        GlobalExceptionHandler.saveException(
                                currentLine,
                                tokenModel.getLexeme(),
                                DataType.NULL,
                                MessageConstant.UNDEFINED_VARIABLE,
                                Token.ERROR_SEMANTIC_UNDEFINED
                        );
                        isAllDeclared.set(false);
                    }
                });

        lexemeList.forEach(lexeme -> Main.TOKEN_MAP.values()
                .stream()
                .filter(tm -> tm.getLexeme().contentEquals(lexeme))
                .findFirst()
                .ifPresent(tokenModel -> {
                    if (tokenModel.getDatatype() == null)
                    {
                        GlobalExceptionHandler.saveException(
                                currentLine,
                                tokenModel.getLexeme(),
                                DataType.NULL,
                                MessageConstant.UNDEFINED_VARIABLE,
                                Token.ERROR_SEMANTIC_UNDEFINED
                        );
                        isAllDeclared.set(false);
                    }
                })
        );
        return isAllDeclared.get();
    }
}
