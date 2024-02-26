package com.realssoft.rcompiler.ui.values;

import javax.swing.*;
import javax.swing.border.*;

public interface ConfigureComponent
{

    default void configureProperties(){}
    default void configureFrameEvent(JFrame frame){}
    default void configureComponents(){}
    default void configureButtonsEvent(){}
    default void configureMouseEvents(){}
    default void configureLayout(){}
    default void configureBorder() {}

}
