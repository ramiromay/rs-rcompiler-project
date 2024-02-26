package com.realssoft.rcompiler.logic.mapper;

import com.realssoft.rcompiler.logic.language.Token;
import com.realssoft.rcompiler.logic.model.IndexModel;
import com.realssoft.rcompiler.logic.model.LineModel;
import com.realssoft.rcompiler.logic.model.TokenErrorModel;
import com.realssoft.rcompiler.logic.model.TokenModel;
import com.realssoft.rcompiler.logic.model.TriploModel;
import com.realssoft.rcompiler.logic.utils.TokenUtils;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class Mapper
{

    public static TokenModel mapToTokenModel(String lexeme, Token token)
    {
        return TokenModel.builder()
                .token(TokenUtils.generateTokenUnique(token))
                .lexeme(lexeme)
                .build();
    }

    public static LineModel mapToLineModel(int currentLine)
    {
        return LineModel.builder()
                .row(currentLine)
                .build();
    }

    public static TokenErrorModel mapToTokenErrorModel(String lexeme, String description, @NotNull Token token)
    {
        return TokenErrorModel.builder()
                .tokenModel(
                        TokenModel.builder()
                                .token(TokenUtils.generateTokenErrorUnique(token))
                                .lexeme(lexeme)
                                .build()
                )
                .description(description)
                .build();
    }

    public static IndexModel  mapToIndexModel(int indexWhile, int indexOr, int indexAnd)
    {
        return IndexModel.builder()
                .indexWhile(indexWhile)
                .indexOr(indexOr)
                .indexAnd(indexAnd)
                .build();
    }

    public static TriploModel mapToTriploModel(String objectData, String sourceData,
                                               String operator, IndexModel indexModel)
    {
        return TriploModel.builder()
                .objectData(objectData)
                .sourceData(sourceData)
                .operator(operator)
                .indexModel(indexModel)
                .build();
    }

    public static TriploModel mapToTriploModel(Object objectData, Object sourceData, Object operator)
    {
        return TriploModel.builder()
                .objectData(String.valueOf(objectData))
                .sourceData(String.valueOf(sourceData))
                .operator(String.valueOf(operator))
                .indexModel(IndexModel.builder().build())
                .build();
    }

}
