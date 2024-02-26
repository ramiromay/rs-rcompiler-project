package com.realssoft.rcompiler.ui.config;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import java.awt.Point;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

@Getter
@Setter
public class ConfigProperties extends Properties
{

    private static final ConfigProperties INSTANCE = new ConfigProperties();
    private int systemLocationY;
    private int systemLocationX;
    private int systemSizeHeight;
    private int systemSizeWidth;
    private boolean isSystemMoved;
    private boolean isSystemBootConfiguration;
    private boolean isSystemStateMaximized;

    private ConfigProperties()
    {
        super();
        load();
    }

    public static ConfigProperties getInstance()
    {
        return INSTANCE;
    }

    public void load()
    {
        try
        {
            super.load(new FileReader("application.properties"));
            systemLocationX = Integer
                    .parseInt(super.getProperty("system.location.x"));
            systemLocationY = Integer
                    .parseInt(super.getProperty("system.location.y"));
            systemSizeWidth = Integer
                    .parseInt(super.getProperty("system.size.width"));
            systemSizeHeight = Integer
                    .parseInt(super.getProperty("system.size.height"));
            isSystemMoved = Boolean
                    .parseBoolean(super.getProperty("system.moved"));
            isSystemStateMaximized = Boolean
                    .parseBoolean(super.getProperty("system.initial.state.maximized"));
            isSystemBootConfiguration = Boolean
                    .parseBoolean(super.getProperty("system.boot.configuration"));
            super.store(new FileWriter("application.properties"),
                    "Copyright (C) 2022 RealsSoft.");
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void close()
    {
        try
        {
            super.load(new FileReader("application.properties"));
            super.setProperty("system.location.x", String.valueOf(systemLocationX));
            super.setProperty("system.location.y", String.valueOf(systemLocationY));
            super.setProperty("system.size.width", String.valueOf(systemSizeWidth));
            super.setProperty("system.size.height", String.valueOf(systemSizeHeight));
            super.setProperty("system.moved", String.valueOf(isSystemMoved));
            super.setProperty("system.initial.state.maximized", String.valueOf(isSystemStateMaximized));
            super.setProperty("system.boot.configuration", String.valueOf(isSystemBootConfiguration));
            super.store(new FileWriter("application.properties"),
                    "Copyright (C) 2023 RealsSoft.");
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void validateMovement()
    {
        if(!isSystemMoved)
        {
            isSystemMoved = true;
        }
    }

    public void setSystemLocation(@NotNull Point point)
    {
        systemLocationX = point.x;
        systemLocationY = point.y;
    }

    public void setSystemLocation(int x, int y)
    {
        systemLocationX = x;
        systemLocationY = y;
    }

    public void setSystemSize(int systemSizeWidth, int systemSizeHeight)
    {
        this.systemSizeWidth = systemSizeWidth;
        this.systemSizeHeight = systemSizeHeight;
    }

}
