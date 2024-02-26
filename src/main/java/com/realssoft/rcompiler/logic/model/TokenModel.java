package com.realssoft.rcompiler.logic.model;

import com.realssoft.rcompiler.logic.language.DataType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenModel
{

    private String lexeme;
    private String token;
    private DataType datatype;

}
