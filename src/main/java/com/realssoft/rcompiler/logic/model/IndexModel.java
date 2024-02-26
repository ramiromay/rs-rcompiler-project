package com.realssoft.rcompiler.logic.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class IndexModel
{

    private int indexWhile;
    private int indexOr;
    private int indexAnd;

    @Override
    public String toString() {
        return "IndexModel{" +
                "indexWhile=" + indexWhile +
                ", indexOr=" + indexOr +
                ", indexAnd=" + indexAnd +
                '}';
    }
}
