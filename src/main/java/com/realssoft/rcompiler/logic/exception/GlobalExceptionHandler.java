package com.realssoft.rcompiler.logic.exception;

import com.realssoft.Main;
import com.realssoft.rcompiler.logic.language.DataType;
import com.realssoft.rcompiler.logic.language.Token;
import com.realssoft.rcompiler.logic.mapper.Mapper;
import com.realssoft.rcompiler.logic.model.LineModel;
import com.realssoft.rcompiler.logic.model.TokenErrorModel;
import com.realssoft.rcompiler.logic.model.TokenModel;
import lombok.NoArgsConstructor;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class GlobalExceptionHandler
{

    private static boolean updateTokenErrorModel(int currentLine, String lexeme, DataType dataType)
    {
        AtomicBoolean isExist = new AtomicBoolean(false);
        Main.TOKEN_ERROR_MAP.values()
                .stream()
                .filter(tem -> {
                    TokenModel tokenModel = tem.getTokenModel();

                    if (dataType == DataType.NULL)
                    {
                        return tokenModel.getToken().equals(lexeme);
                    }
                    return tokenModel.getToken().equals(lexeme) &&
                            tokenModel.getDatatype().equals(dataType);
                })
                .findFirst()
                .ifPresent(tokenErrorModel -> {
                    if (dataType != DataType.NULL)
                    {
                        Map<Integer, LineModel> map = tokenErrorModel.getLineMap();
                        boolean isExistInMap = map.values()
                                .stream()
                                .anyMatch(lineModel -> lineModel.getRow() == currentLine);

                        if (!isExistInMap)
                        {
                            map.put(map.size() + 1, Mapper.mapToLineModel(currentLine));
                            Main.TOKEN_ERROR_MAP.put(
                                    tokenErrorModel.getTokenModel().getToken(),
                                    tokenErrorModel
                            );
                            isExist.set(true);
                        }
                    }
                });
        return isExist.get();
    }

    private static void saveTokenErrorModel(int currentLine, String lexeme, DataType dataType,
                                            String description, Token token)
    {
        TokenErrorModel tokenErrorModel = Mapper.mapToTokenErrorModel(lexeme, description, token);
        TokenModel tokenModel = tokenErrorModel.getTokenModel();
        if (dataType != DataType.NULL) tokenModel.setDatatype(dataType);
        tokenErrorModel.getLineMap().put(1, Mapper.mapToLineModel(currentLine));
        Main.TOKEN_ERROR_MAP.put(tokenModel.getToken(), tokenErrorModel);
    }

    public static void saveException(int currentLine, String lexeme, DataType dataType,
                                     String description, Token token)
    {
        if (updateTokenErrorModel(currentLine, lexeme, dataType)) return;
        saveTokenErrorModel(
                currentLine,
                lexeme,
                dataType,
                description,
                token
        );
    }

}
