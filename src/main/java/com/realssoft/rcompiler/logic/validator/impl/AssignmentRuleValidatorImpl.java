package com.realssoft.rcompiler.logic.validator.impl;

import com.realssoft.rcompiler.logic.constant.MessageConstant;
import com.realssoft.rcompiler.logic.exception.GlobalExceptionHandler;
import com.realssoft.rcompiler.logic.language.AssignmentRule;
import com.realssoft.rcompiler.logic.language.DataType;
import com.realssoft.rcompiler.logic.language.Token;
import com.realssoft.rcompiler.logic.model.TokenModel;
import com.realssoft.rcompiler.logic.utils.SearchUtils;
import com.realssoft.rcompiler.logic.validator.AssignmentValidator;
import java.util.EnumMap;
import java.util.Map;

public class AssignmentRuleValidatorImpl implements AssignmentValidator
{

    private final Map<DataType, AssignmentRule> assignmentRule = new EnumMap<>(DataType.class);

    public AssignmentRuleValidatorImpl()
    {
        assignmentRule.put(DataType.RAM, AssignmentRule.RAM);
        assignmentRule.put(DataType.EDU, AssignmentRule.EDU);
        assignmentRule.put(DataType.JOA, AssignmentRule.JOA);
    }

    @Override
    public void valid(int currentLine, String assign, String assigment)
    {
        TokenModel assignedTokenModel = SearchUtils.findTokenModelByLexeme(assign);
        TokenModel assigmentTokenModel = SearchUtils.findTokenModelByLexeme(assigment);
        DataType dataType = assignedTokenModel.getDatatype();
        AssignmentRule dataTypeRule = assignmentRule.get(dataType);

        if (assigmentTokenModel.getDatatype() != dataTypeRule.getRule())
        {
            GlobalExceptionHandler.saveException(
                    currentLine,
                    assigment,
                    dataType,
                    String.format(MessageConstant.INCOMPATIBLE_TYPES, dataType.name()),
                    Token.ERROR_SEMANTIC_INCOMPATIBLE
            );
        }

    }
}
