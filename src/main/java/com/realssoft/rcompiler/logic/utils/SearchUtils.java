package com.realssoft.rcompiler.logic.utils;

import com.realssoft.Main;
import com.realssoft.rcompiler.logic.model.TokenModel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class SearchUtils
{

    public static TokenModel findTokenModelByLexeme(String lexeme)
    {
        return Main.TOKEN_MAP.values()
                .stream()
                .filter(model -> model.getLexeme().equals(lexeme))
                .findFirst()
                .orElse(null);

    }

    public static @NotNull List<TokenModel> findTokenModelListByLexeme(@NotNull List<String> lexemeList)
    {
        List<TokenModel> listAssigned = new ArrayList<>();
        lexemeList.forEach(lexeme -> listAssigned.add(Main.TOKEN_MAP.values()
                .stream()
                .filter(model -> model.getLexeme().equals(lexeme))
                .findFirst()
                .orElse(null)));
        return listAssigned;
    }

}
