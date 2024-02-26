package com.realssoft.rcompiler.logic.model;

import com.realssoft.rcompiler.logic.language.Revision;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RevisionModel
{

    private String firstFragment;
    private Revision revision;

}
