package com.realssoft.rcompiler.logic.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TriploModel
{

    private String objectData;
    private String sourceData;
    private String operator;
    private IndexModel indexModel;

}
