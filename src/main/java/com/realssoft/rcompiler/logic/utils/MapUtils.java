package com.realssoft.rcompiler.logic.utils;

import com.realssoft.rcompiler.logic.model.LineModel;
import com.realssoft.rcompiler.logic.model.TokenErrorModel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import java.util.stream.Collectors;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class MapUtils
{

    public static String getRowsError(@NotNull TokenErrorModel tokenErrorModel)
    {
        return tokenErrorModel.getLineMap()
                .values()
                .stream()
                .map(LineModel::getRow)
                .collect(Collectors.toList())
                .toString();
    }

}
