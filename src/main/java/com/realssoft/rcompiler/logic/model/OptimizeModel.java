package com.realssoft.rcompiler.logic.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OptimizeModel
{

    private int lineIndex;
    private String firstFragment;
    private String secondFragment;

    @Override
    public String toString() {
        return "OptimizeModel{" +
                "lineIndex=" + lineIndex +
                ", assignTo='" + firstFragment + '\'' +
                ", assignationTo='" + secondFragment + '\'' +
                '}';
    }
}
