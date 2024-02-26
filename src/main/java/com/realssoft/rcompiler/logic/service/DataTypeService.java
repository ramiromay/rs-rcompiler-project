package com.realssoft.rcompiler.logic.service;

import com.realssoft.rcompiler.logic.language.DataType;
import com.realssoft.rcompiler.logic.model.TokenModel;
import com.realssoft.rcompiler.logic.utils.SearchUtils;
import org.jetbrains.annotations.NotNull;

public class DataTypeService
{

    private DataType datatype;

    public DataTypeService()
    {
        setNull();
    }

    public boolean isNull()
    {
        return datatype == DataType.NULL;
    }

    public void setNull()
    {
        datatype = DataType.NULL;
    }

    public void changeDataType(@NotNull String lexeme)
    {
        if (lexeme.equals("RAM"))
        {
            datatype = DataType.RAM;
            return;
        }
        if (lexeme.equals("EDU"))
        {
            datatype = DataType.EDU;
            return;
        }

        if (lexeme.equals("JOA"))
        {
            datatype = DataType.JOA;
        }
    }

    public void assignDataType(@NotNull String lexeme, DataType datatype)
    {
        TokenModel tokenModel = SearchUtils.findTokenModelByLexeme(lexeme);
        if (tokenModel == null) return;
        tokenModel.setDatatype(datatype);
    }

    public void assignDataTypeToRam(@NotNull String lexeme)
    {
        assignDataType(lexeme, DataType.RAM);
    }

    public void assignDataTypeToEdu(@NotNull String lexeme)
    {
        assignDataType(lexeme, DataType.EDU);
    }

    public void assignDataTypeToJoa(@NotNull String lexeme)
    {
        assignDataType(lexeme, DataType.JOA);
    }

    public void assignDataTypeToVariable(@NotNull String lexeme)
    {
        assignDataType(lexeme, datatype);
    }

}
