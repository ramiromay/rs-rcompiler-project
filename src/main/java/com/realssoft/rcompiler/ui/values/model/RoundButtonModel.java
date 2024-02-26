package com.realssoft.rcompiler.ui.values.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.swing.Icon;
import java.awt.Color;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoundButtonModel
{

    private Icon icon;
    private String text;
    private Color textColor;
    private Color backgroundColor;
    private int cornerRadius;

}
