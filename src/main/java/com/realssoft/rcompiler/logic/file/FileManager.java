package com.realssoft.rcompiler.logic.file;

import com.realssoft.rcompiler.ui.support.notification.Notifications;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Setter
@Getter
public class FileManager extends FileFilter
{

    private static final FileManager INSTANCE = new FileManager();
    private static final String EXTENSION = ".jrs";
    private static final String DESCRIPTION = "Archivos de RS (*.jrs)";

    private Path absolutePath;
    private final StringBuilder fileName;
    private final StringBuilder content;
    private final Notifications notification;

    private FileManager()
    {
        notification = Notifications.getInstance();
        fileName = new StringBuilder();
        content = new StringBuilder();
    }

    public static FileManager getInstance()
    {
        return INSTANCE;
    }

    private void read()
    {
        try (BufferedReader reader = Files.newBufferedReader(absolutePath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null)
            {
                content.append(line).append("\n");
            }
            notification.show(
                    Notifications.Type.INFO,
                    Notifications.Location.BOTTOM_RIGHT,
                    "Cargando contenido del archivo " + fileName
            );
        } catch (IOException ex)
        {
            notification.show(
                    Notifications.Type.ERROR,
                    Notifications.Location.BOTTOM_RIGHT,
                    "Error al leer el archivo"
            );
        }
    }

    private void write(String content, @NotNull File file)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
            Notifications.getInstance().show(
                    Notifications.Type.SUCCESS,
                    Notifications.Location.BOTTOM_RIGHT,
                    "Se guardo el archivo " + fileName + " correctamente"
            );
        } catch (IOException e) {
            Notifications.getInstance().show(
                    Notifications.Type.ERROR,
                    Notifications.Location.BOTTOM_RIGHT,
                    "Error al guardar el archivo."
            );
        }
    }


    public String readFile()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccione un archivo");
        fileChooser.setFileFilter(this);
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile != null)
            {
                content.setLength(0);
                fileName.setLength(0);
                fileName.append(selectedFile.getName());
                absolutePath = Paths.get(selectedFile.getAbsolutePath());
                read();
            }
        }
        return content.toString();
    }


    public void writeFile(String content) {

        if (absolutePath == null)
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar Archivo");
            fileChooser.setFileFilter(this);
            int returnValue = fileChooser.showSaveDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if (selectedFile != null)
                {
                    fileName.setLength(0);
                    fileName.append(selectedFile.getName());
                    absolutePath = Paths.get(selectedFile.getAbsolutePath());
                    write(content, selectedFile);
                }
            }
            return;
        }

        File selectedFile = new File(absolutePath.toString());
        write(content, selectedFile);
    }

    @Override
    public boolean accept(@NotNull File file)
    {
        return file.getName().toLowerCase().endsWith(EXTENSION) || file.isDirectory();
    }

    @Override
    public String getDescription()
    {
        return DESCRIPTION;
    }

}
