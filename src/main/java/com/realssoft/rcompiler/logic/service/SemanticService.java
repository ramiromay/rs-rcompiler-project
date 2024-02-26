package com.realssoft.rcompiler.logic.service;

import com.realssoft.rcompiler.logic.language.RegularExpression;
import com.realssoft.rcompiler.logic.utils.VariableUtils;
import com.realssoft.rcompiler.logic.validator.UndefinedValidator;
import com.realssoft.rcompiler.logic.validator.impl.ArithmeticRulesValidatorImpl;
import com.realssoft.rcompiler.logic.validator.impl.AssignmentRuleValidatorImpl;
import com.realssoft.rcompiler.logic.validator.impl.UndefinedRuleValidator;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class SemanticService
{

    private final DataTypeService dataTypeService;
    private final ArithmeticRulesValidatorImpl arithmeticRulesValidator;
    private final AssignmentRuleValidatorImpl assignmentRuleValidator;
    private final UndefinedValidator undefinedValidator;
    private final StringBuilder assigned;
    private final List<String> lexemeList;
    private final List<String> operatorsList;

    public SemanticService()
    {
        dataTypeService = new DataTypeService();
        arithmeticRulesValidator = new ArithmeticRulesValidatorImpl();
        assignmentRuleValidator = new AssignmentRuleValidatorImpl();
        undefinedValidator = new UndefinedRuleValidator();
        assigned = new StringBuilder();
        lexemeList = new ArrayList<>();
        operatorsList = new ArrayList<>();
    }

    private void reset()
    {
        dataTypeService.setNull();
        assigned.setLength(0);
        lexemeList.clear();
        operatorsList.clear();
    }

    private void assessResults(int currentLine)
    {
        if (!dataTypeService.isNull()) return;
        if (!undefinedValidator.valid(currentLine, assigned.toString(), lexemeList)) return;
        if (VariableUtils.isVariableAssignment(lexemeList))
        {
            assignmentRuleValidator.valid(
                    currentLine,
                    assigned.toString(),
                    lexemeList.get(0)
            );
            return;
        }

        if (VariableUtils.isVariableArithmetic(lexemeList, operatorsList, assigned))
        {
            arithmeticRulesValidator.valid(
                    currentLine,
                    assigned.toString(),
                    lexemeList,
                    operatorsList
            );
        }
    }

    private void analyze(@NotNull String lexeme , int currentLine, int column, int lastColumn)
    {
        if (column == lastColumn)
        {
            if (lexeme.matches(";"))
                assessResults(currentLine);
            reset();
            return;
        }

        if (lexeme.matches(RegularExpression.DATATYPE.value))
        {
            dataTypeService.changeDataType(lexeme);
            return;
        }

        if (!dataTypeService.isNull()
                && lexeme.matches(RegularExpression.ID.value))
        {
            dataTypeService.assignDataTypeToVariable(lexeme);
            return;
        }

        if (column == 0 &&
                lexeme.matches(RegularExpression.ID.value))
        {
            assigned.append(lexeme);
            return;
        }

        if (assigned.length() != 0 &&
                lexeme.matches(RegularExpression.O_ARITHMETIC.value))
        {
            operatorsList.add(lexeme);
            return;
        }

        if (assigned.length() != 0 &&
                lexeme.matches(RegularExpression.ID.value))
        {
            lexemeList.add(lexeme);
            return;
        }

        if (lexeme.matches(RegularExpression.NUMS_INTEGERS.value))
        {
            dataTypeService.assignDataTypeToRam(lexeme);
            lexemeList.add(lexeme);
            return;
        }

        if (lexeme.matches(RegularExpression.NUMS_FLOAT.value))
        {
            dataTypeService.assignDataTypeToEdu(lexeme);
            lexemeList.add(lexeme);
            return;
        }

        if (lexeme.matches(RegularExpression.STRING.value))
        {
            dataTypeService.assignDataTypeToJoa(lexeme);
            lexemeList.add(lexeme);
        }
    }

    public void verify(@NotNull String @NotNull [] input, int currentLine)
    {
        int lastColumn = input.length - 1;
        for (int column = 0; column <= lastColumn; column++)
        {
            analyze(
                    input[column],
                    currentLine,
                    column,
                    lastColumn
            );
        }
    }

}
