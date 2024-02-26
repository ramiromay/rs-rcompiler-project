package com.realssoft.rcompiler.logic.validator.impl;

import com.realssoft.rcompiler.logic.constant.MessageConstant;
import com.realssoft.rcompiler.logic.exception.GlobalExceptionHandler;
import com.realssoft.rcompiler.logic.language.DataType;
import com.realssoft.rcompiler.logic.language.ArithmeticRule;
import com.realssoft.rcompiler.logic.language.Token;
import com.realssoft.rcompiler.logic.model.TokenModel;
import com.realssoft.rcompiler.logic.utils.SearchUtils;
import com.realssoft.rcompiler.logic.validator.ArithmeticValidator;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ArithmeticRulesValidatorImpl implements ArithmeticValidator
{

    private final Map<DataType, ArithmeticRule> arithmeticRules = new EnumMap<>(DataType.class);

    public ArithmeticRulesValidatorImpl()
    {
        arithmeticRules.put(DataType.RAM, ArithmeticRule.RAM_OPA_RAM);
        arithmeticRules.put(DataType.JOA, ArithmeticRule.JOA_OPA_JOA);
        arithmeticRules.put(DataType.EDU, ArithmeticRule.EDU_OPA_RAM);
    }

    @Override
    public void valid(int currentLine, String assign,
                      @NotNull List<String> lexemeList, @NotNull List<String> operationsList)
    {
        TokenModel assigned = SearchUtils.findTokenModelByLexeme(assign);
        List<TokenModel> assignedList = SearchUtils.findTokenModelListByLexeme(lexemeList);

        DataType assignedDataType = assigned.getDatatype();
        ArithmeticRule assignedArithmeticRule  = arithmeticRules.get(assignedDataType);
        String acceptedOperations = assignedArithmeticRule.getOperator().expression;
        List<DataType> acceptedDataTypes =  Arrays.asList(
                assignedArithmeticRule.getRuleOne(),
                assignedArithmeticRule.getRuleTwo()
        );

        assignedList.forEach(tokenModel -> {
            if (!acceptedDataTypes.contains(tokenModel.getDatatype()))
            {
                GlobalExceptionHandler.saveException(
                        currentLine,
                        tokenModel.getLexeme(),
                        assignedDataType,
                        String.format(MessageConstant.INCOMPATIBLE_TYPES, assignedDataType.name()),
                        Token.ERROR_SEMANTIC_INCOMPATIBLE
                );
            }
        });

        operationsList.forEach(operation -> {
            if (!operation.matches(acceptedOperations))
            {
                GlobalExceptionHandler.saveException(
                        currentLine,
                        operation,
                        assignedDataType,
                        String.format(MessageConstant.INCOMPATIBLE_TYPES, assignedDataType.name()),
                        Token.ERROR_SEMANTIC_INCOMPATIBLE
                );
            }
        });
    }

}
