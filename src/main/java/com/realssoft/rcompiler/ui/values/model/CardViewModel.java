package com.realssoft.rcompiler.ui.values.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.awt.Image;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardViewModel
{

    private Image backgroundImage;
    private String title;
    private String description;
    private int cornerRadius;

}
