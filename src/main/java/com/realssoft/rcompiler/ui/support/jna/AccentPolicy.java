package com.realssoft.rcompiler.ui.support.jna;

import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;

public class AccentPolicy extends Structure implements Structure.ByReference
{

    public static int AccentState;
    public int GradientColor;

    @Override
    protected List<String> getFieldOrder()
    {
        return Arrays.asList(
                "AccentState",
                "AccentFlags",
                "GradientColor",
                "AnimationId");
    }

}
