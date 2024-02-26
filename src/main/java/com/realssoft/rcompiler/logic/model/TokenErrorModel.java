package com.realssoft.rcompiler.logic.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashMap;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenErrorModel
{

    private TokenModel tokenModel;
    private final Map<Integer, LineModel> lineMap = new HashMap<>();
    private String description;

}
