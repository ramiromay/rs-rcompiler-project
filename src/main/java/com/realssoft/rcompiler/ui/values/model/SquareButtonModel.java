package com.realssoft.rcompiler.ui.values.model;

import com.realssoft.materialdesign.MaterialDesignIcon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.awt.Color;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SquareButtonModel
{

    private MaterialDesignIcon icon;
    private Color iconColor;
    private Color backgroundColor;
    private boolean isDynamicColor;

}
