package com.realssoft.rcompiler.logic.service;

import com.realssoft.Main;
import com.realssoft.rcompiler.logic.language.RegularExpression;
import com.realssoft.rcompiler.logic.language.TriploValue;
import com.realssoft.rcompiler.logic.mapper.Mapper;
import com.realssoft.rcompiler.logic.model.IndexModel;
import com.realssoft.rcompiler.logic.model.TriploModel;
import com.realssoft.rcompiler.logic.utils.VariableUtils;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TriploServices
{

    private final StringBuilder assigned;
    private final List<String> lexemeList;
    private final List<String> operatorsList;
    private final List<IndexModel> sectionList;

    private boolean isCycle = false;
    private int indexWhile = 0;
    private int indexOr = 1;
    private int indexAnd = 1;

    public TriploServices()
    {
        assigned = new StringBuilder();
        lexemeList = new ArrayList<>();
        operatorsList = new ArrayList<>();
        sectionList = new ArrayList<>();
    }

    private void reset()
    {
        assigned.setLength(0);
        lexemeList.clear();
        operatorsList.clear();
    }

    private void addRow(String objectData, String sourceData, String operator)
    {
        Main.TRIPLO_MAP.put(
                Main.TRIPLO_MAP.size() + 1,
                Mapper.mapToTriploModel(
                        objectData,
                        sourceData,
                        operator
                ));
    }

    private void addRow(String objectData, String sourceData,
                        String operator, IndexModel indexModel)
    {
        Main.TRIPLO_MAP.put(
                Main.TRIPLO_MAP.size() + 1,
                Mapper.mapToTriploModel(
                        objectData,
                        sourceData,
                        operator,
                        indexModel
                ));
    }

    private void addJMP()
    {
        Main.TRIPLO_MAP.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getIndexModel().getIndexWhile() == indexWhile)
                .findFirst()
                .ifPresent(entry -> Main.TRIPLO_MAP.put(
                        Main.TRIPLO_MAP.size() + 1,
                        Mapper.mapToTriploModel(
                                "",
                                entry.getKey(),
                                TriploValue.JMP.getValue()
                        )));
        indexOr = 1;
        indexAnd = 1;
    }

    private void addOperationArithmeticAndAssigment()
    {
        operatorsList.add(0, TriploValue.ASSIGNMENT.getValue());
        for (int index = 0; index < lexemeList.size(); index++)
        {
            addRow(
                    TriploValue.T1.getValue(),
                    lexemeList.get(index),
                    operatorsList.get(index)
            );
        }
        addRow(
                assigned.toString(),
                TriploValue.T1.getValue(),
                TriploValue.ASSIGNMENT.getValue()
        );
    }

    private void validateOperatorLogic(String operator) {
        if (operator == null) return;
        if (operator.equals("&&"))
        {
            indexAnd++;
            return;
        }
        indexAnd = 1;
        indexOr++;
    }

    private void identifyOperator(String lexeme, String operator, IndexModel indexModel)
    {
        if (operator == null) return;
        if (operator.matches(RegularExpression.O_RELATIONAL.value))
        {
            sectionList.add(indexModel);
            addRow(
                    TriploValue.T1.getValue(),
                    lexeme,
                    operator,
                    indexModel
            );
            addRow(
                    TriploValue.TR1.getValue(),
                    TriploValue.TRUE.getValue(),
                    null,
                    indexModel
            );
            addRow(
                    TriploValue.T1.getValue(),
                    TriploValue.FALSE.getValue(),
                    null,
                    indexModel
            );
            return;
        }

        if (operator.matches(RegularExpression.O_LOGIC.value))
        {
            validateOperatorLogic(operator);
            indexModel.setIndexAnd(indexAnd);
            indexModel.setIndexOr(indexOr);
            addRow(
                    TriploValue.T1.getValue(),
                    lexeme,
                    TriploValue.ASSIGNMENT.getValue(),
                    indexModel
            );
            return;
        }
        addRow(
                TriploValue.T1.getValue(),
                lexeme,
                TriploValue.ASSIGNMENT.getValue(),
                indexModel
        );
    }

    private void startOperationWhile()
    {
        operatorsList.add(0, TriploValue.ASSIGNMENT.getValue());
        for (int index = 0; index < lexemeList.size(); index++)
        {
            String operator = operatorsList.get(index);
            String lexeme = lexemeList.get(index);
            IndexModel indexModel = Mapper.mapToIndexModel(indexWhile, indexOr, indexAnd);
            identifyOperator(lexeme, operator, indexModel);
        }
        isCycle = false;
    }

    private void changeSections()
    {
        AtomicInteger currentSection = new AtomicInteger(1);
        sectionList.forEach(section ->
        {
            Main.TRIPLO_MAP.entrySet()
                    .stream()
                    .filter(entry -> {
                        TriploModel triploModel = entry.getValue();
                        IndexModel indexModel = triploModel.getIndexModel();
                        return indexModel.getIndexWhile() == section.getIndexWhile()
                                && indexModel.getIndexOr() == section.getIndexOr()
                                && indexModel.getIndexAnd() == section.getIndexAnd()
                                && triploModel.getSourceData().equals(TriploValue.TRUE.getValue());
                    })
                    .findFirst()
                    .ifPresent(entry -> {
                        int index = entry.getKey() + 2;
                        entry.getValue().setOperator(String.valueOf(index));
                    });

            Main.TRIPLO_MAP.values()
                    .stream()
                    .filter(triploModel -> {
                        IndexModel indexModel = triploModel.getIndexModel();
                        return indexModel.getIndexWhile() == section.getIndexWhile()
                                && indexModel.getIndexOr() == section.getIndexOr()
                                && indexModel.getIndexAnd() == section.getIndexAnd()
                                && triploModel.getSourceData().equals(TriploValue.FALSE.getValue());
                    })
                    .findFirst()
                    .ifPresent(triploModel -> {
                        int index = Main.TRIPLO_MAP.size() + 1;
                        triploModel.setOperator(String.valueOf(index));
                    });

            if (section.getIndexOr() != currentSection.get())
            {
                Main.TRIPLO_MAP.values()
                        .stream()
                        .filter(triploModel -> {
                            IndexModel indexModel = triploModel.getIndexModel();
                            return indexModel.getIndexWhile() == section.getIndexWhile()
                                    && indexModel.getIndexOr() == currentSection.get()
                                    && triploModel.getSourceData().equals(TriploValue.TRUE.getValue());
                        })
                        .reduce((first, last) -> last)
                        .ifPresent(triploModel ->
                                Main.TRIPLO_MAP.entrySet()
                                        .stream()
                                        .filter(entry -> entry.getValue()
                                                .getIndexModel()
                                                .getIndexWhile() == section.getIndexWhile())
                                        .reduce((first, last) -> last)
                                        .ifPresent(entry -> {
                                            int index = entry.getKey() + 1;
                                            triploModel.setOperator(String.valueOf(index));
                                        })
                        );

                Main.TRIPLO_MAP.entrySet()
                        .stream()
                        .filter(entry -> {
                            IndexModel indexModel = entry.getValue().getIndexModel();
                            return indexModel.getIndexWhile() == section.getIndexWhile()
                                    && indexModel.getIndexOr() == section.getIndexOr();
                        })
                        .findFirst()
                        .ifPresent(entry ->{
                            int index = entry.getKey();
                            Main.TRIPLO_MAP.values()
                                    .stream()
                                    .filter(triploModel -> {
                                        IndexModel indexModel = triploModel.getIndexModel();
                                        return indexModel.getIndexWhile() == section.getIndexWhile()
                                                && indexModel.getIndexOr() == currentSection.get()
                                                && triploModel.getSourceData().equals(TriploValue.FALSE.getValue());
                                    })
                                    .forEachOrdered(triploModel -> triploModel.setOperator(String.valueOf(index)));
                        });
                currentSection.set(section.getIndexOr());
            }
        });
    }

    private void endOperationWhile()
    {
        addJMP();
        changeSections();
        sectionList.clear();
    }

    private void analyze(@NotNull String lexeme, int column, int lastColumn)
    {
        if (column == lastColumn && lexeme.matches("[;}{]"))
        {
            if (VariableUtils.isVariableAssignment(lexemeList)
                    || VariableUtils.isVariableArithmetic(lexemeList, operatorsList, assigned))
            {
                addOperationArithmeticAndAssigment();
            }

            if (isCycle)
            {
                startOperationWhile();
            }

            if (lexeme.matches("}"))
            {
                endOperationWhile();
            }
            reset();
            return;
        }

        if (isCycle)
        {
            if (lexeme.matches(RegularExpression.ID.value)
                    || lexeme.matches(RegularExpression.NUMS_INTEGERS.value)
                    || lexeme.matches(RegularExpression.NUMS_FLOAT.value)
                    || lexeme.matches(RegularExpression.STRING.value))
            {
                lexemeList.add(lexeme);
                return;
            }

            if (lexeme.matches(RegularExpression.O_RELATIONAL.value)
                    || lexeme.matches(RegularExpression.O_LOGIC.value))
            {
                operatorsList.add(lexeme);
                return;
            }

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
            lexemeList.add(lexeme);
            return;
        }

        if (lexeme.matches(RegularExpression.NUMS_FLOAT.value))
        {
            lexemeList.add(lexeme);
            return;
        }

        if (lexeme.matches(RegularExpression.STRING.value))
        {
            lexemeList.add(lexeme);
            return;
        }

        if (lexeme.matches(RegularExpression.TEAM_7.value))
        {
            isCycle = true;
            indexWhile++;
        }
    }

    public void verify(@NotNull String @NotNull [] input, int currentLine, int lastLine)
    {
        int lastColumn = input.length - 1;
        for (int column = 0; column <= lastColumn; column++)
        {
            analyze(
                    input[column],
                    column,
                    lastColumn
            );
        }

        if (currentLine == lastLine)
        {
            indexWhile = 0;
        }
    }

}
