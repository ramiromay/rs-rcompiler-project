package com.realssoft.rcompiler.logic.service;

import com.realssoft.Main;
import com.realssoft.rcompiler.logic.constant.MessageConstant;
import com.realssoft.rcompiler.logic.exception.GlobalExceptionHandler;
import com.realssoft.rcompiler.logic.language.DataType;
import com.realssoft.rcompiler.logic.language.RegularExpression;
import com.realssoft.rcompiler.logic.language.Token;
import com.realssoft.rcompiler.logic.mapper.Mapper;
import com.realssoft.rcompiler.logic.model.TokenModel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor
public class LexicalService
{

    private boolean isExisted(String lexeme)
    {
        return Main.TOKEN_MAP.values()
                .stream()
                .anyMatch(tokenModel -> tokenModel.getLexeme().equals(lexeme));
    }

    public void saveLexeme(String lexeme, Token token)
    {
        if(!isExisted(lexeme))
        {
            TokenModel newLexeme = Mapper.mapToTokenModel(lexeme, token);
            Main.TOKEN_MAP.put(newLexeme.getToken(), newLexeme);
        }
    }

    public void analyze(@NotNull String lexeme, int currentLine)
    {
        if(lexeme.isEmpty()) return;
        if(lexeme.matches(RegularExpression.SEPARATORS.value))
        {
            saveLexeme(lexeme, Token.SEPARATORS);
            return;
        }

        if(lexeme.matches(RegularExpression.O_ARITHMETIC.value))
        {
            saveLexeme(lexeme, Token.O_ARITHMETIC);
            return;
        }

        if(lexeme.matches(RegularExpression.O_RELATIONAL.value))
        {
            saveLexeme(lexeme, Token.O_RELATIONAL);
            return;
        }

        if(lexeme.matches(RegularExpression.O_ASSIGMENT.value))
        {
            saveLexeme(lexeme, Token.O_ASSIGMENT);
            return;
        }

        if(lexeme.matches(RegularExpression.O_LOGIC.value))
        {
            saveLexeme(lexeme, Token.O_LOGIC);
            return;
        }

        if(lexeme.matches(RegularExpression.ID.value))
        {
            saveLexeme(lexeme, Token.ID);
            return;
        }

        if(lexeme.matches(RegularExpression.NUMS_INTEGERS.value))
        {
            saveLexeme(lexeme, Token.NUMS_INTEGERS);
            return;
        }

        if(lexeme.matches(RegularExpression.NUMS_FLOAT.value))
        {
            saveLexeme(lexeme, Token.NUMS_FLOAT);
            return;
        }

        if (lexeme.matches(RegularExpression.STRING.value))
        {
            saveLexeme(lexeme, Token.STRING);
            return;
        }

        if(lexeme.matches(RegularExpression.DATATYPE.value))
        {
            saveLexeme(lexeme, Token.TYPE_DATA);
            return;
        }
        if(lexeme.matches(RegularExpression.TEAM_7.value))
        {

            saveLexeme(lexeme, Token.TEAM_7);
            return;
        }

        GlobalExceptionHandler.saveException(
                currentLine,
                lexeme,
                DataType.NULL,
                MessageConstant.LEXICAL_ERROR,
                Token.LEXICAL_ERROR
        );
    }

    public void verify(@NotNull String @NotNull [] input, int currentLine)
    {
        for (String lexeme : input)
        {
            analyze(lexeme, currentLine);
        }
    }

}
