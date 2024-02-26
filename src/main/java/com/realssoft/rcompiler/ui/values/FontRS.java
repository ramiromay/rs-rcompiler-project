package com.realssoft.rcompiler.ui.values;

import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FontRS
{

    public static final int SEGOE_UI_REGULAR = 0;
    public static final int SEGOE_UI_BOLD = 1;

    public FontRS() {}

    /** For Font Style -> Font.PLAIN = 0 , Font.BOLD = 1 , Font.ITALIC = 2 */
    public static Font changeFont(int fontOption,int fonStyle, float fontSize)
    {
        Font font;
        InputStream inputStream = null;
        try
        {
            switch (fontOption)
            {
                case SEGOE_UI_REGULAR:
                    inputStream = new BufferedInputStream(
                            Files.newInputStream(Paths.get(PathRS.SEGOE_UI_REGULAR_FONT)));
                    break;
                case SEGOE_UI_BOLD:
                    inputStream = new BufferedInputStream(
                            Files.newInputStream(Paths.get(PathRS.SEGOE_UI_BOLD_FONT)));
                    break;
            }
            assert inputStream != null;
            font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            font = new Font("Arial", Font.BOLD, 14);
        }
        return font.deriveFont(fonStyle, fontSize);
    }

}
