package com.realssoft.rcompiler.ui.values.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.awt.Color;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventColorModel
{

    private Color enteredColor;
    private Color exitedColor;

}
