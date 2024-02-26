package com.realssoft.rcompiler.logic.utils;

import com.realssoft.Main;
import com.realssoft.rcompiler.logic.language.Token;
import com.realssoft.rcompiler.logic.model.TokenErrorModel;
import com.realssoft.rcompiler.logic.model.TokenModel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class TokenUtils
{

    public static @NotNull String generateTokenErrorUnique(Token token)
    {
        int tokenCounter = 1;
        for (TokenErrorModel tokenErrorModel : Main.TOKEN_ERROR_MAP.values())
        {
            String tokenNomenclature = tokenErrorModel.getTokenModel()
                    .getToken()
                    .split("\\d")[0];
            if (tokenNomenclature.equals(token.value))
            {
                tokenCounter ++;
            }
        }
        return token.value + tokenCounter;
    }

    public static @NotNull String generateTokenUnique(Token token)
    {
        if(token == Token.O_ASSIGMENT || token == Token.TEAM_7)
        {
            return token.value;
        }

        int tokenCounter = 1;
        for (TokenModel tokenModel : Main.TOKEN_MAP.values())
        {
            String tokenNomenclature =  tokenModel.getToken()
                    .split("\\d")[0];
            if (tokenNomenclature.equals(token.value))
            {
                tokenCounter ++;
            }
        }

        return token.value + tokenCounter;
    }

}
