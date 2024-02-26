package com.realssoft.rcompiler.logic.file;

import com.realssoft.Main;
import com.realssoft.rcompiler.logic.language.RegularExpression;
import com.realssoft.rcompiler.logic.model.ReplacementDimensionModel;
import com.realssoft.rcompiler.ui.component.CodeSpace;
import com.realssoft.rcompiler.ui.values.PathRS;
import org.jetbrains.annotations.NotNull;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenerateTokenFile
{

    private final StringBuilder code;
    private final StringBuilder newLine;
    private final List<String> lines;
    private final List<String> lexemes;
    private final ReplacementDimensionModel dimensionModel;

    public GenerateTokenFile()
    {
        code = new StringBuilder();
        newLine = new StringBuilder();
        lines = new ArrayList<>();
        lexemes = new ArrayList<>();
        dimensionModel = new ReplacementDimensionModel();
    }

    private void assignReplacementDimension(int lineIndex)
    {
        dimensionModel.setStart(0);
        for (int i = 0; i < lineIndex; i ++)
        {
            dimensionModel.setStart(
                    dimensionModel.getStart()
                            + lines.get(i).length() + 1
            );
        }
        dimensionModel.setEnd(
                dimensionModel.getStart()
                        + lines.get(lineIndex).length()
        );
    }

    private void validateColumn(int columIndex,
                                int columCompareTo,
                                String errorToken,
                                String lexeme)
    {
        if(columCompareTo == columIndex)
        {
            newLine.append(errorToken).append(" ");
            return;
        }
        newLine.append(lexeme).append(" ");
    }

    private void replacerImproved(@NotNull String regex,
                                  @NotNull String replacement)
    {
        code.replace(
                0,
                code.length(),
                code.toString().replaceAll(
                        regex,
                        replacement
                )
        );
    }

    private void rebuildLineErrors(String errorToken,
                                   int lineIndex,
                                   int colum)
    {
        Collections.addAll(
                lexemes,
                lines.get(lineIndex).split("\\s+")
        );

        for(int index = 0; index < lexemes.size(); index ++)
            validateColumn(index, colum, errorToken, lexemes.get(index));

        assignReplacementDimension(lineIndex);
        code.replace(
                dimensionModel.getStart(),
                dimensionModel.getEnd(),
                newLine.toString().trim()
        );
        lines.set(
                lineIndex,
                newLine.toString().trim()
        );
        newLine.setLength(0);
        lexemes.clear();
    }

    private void rebuildLine(@NotNull String lexeme,
                             @NotNull String token)
    {
        if(lexeme.matches(RegularExpression.ID.value) ||
                lexeme.matches(RegularExpression.O_ARITHMETIC.value))
        {
            replacerImproved(
                    "\\b" + lexeme + "\\b",
                    token
            );
            return;
        }
        replacerImproved(lexeme, token);
    }

    private void initializeData()
    {
        code.setLength(0);
        code.append(CodeSpace.getInstance().getText());
        Collections.addAll(
                lines,
                code.toString().split("\\n")
        );
    }

    private @NotNull String replaceLexemeForToken()
    {
        initializeData();
        Main.TOKEN_ERROR_MAP.forEach((keyPrimary, entry) ->
                entry.getLineMap().forEach((keySecondary, lineModel) ->
                        rebuildLineErrors(
                                keyPrimary,
                                lineModel.getRow() - 1,
                                lineModel.getColum()
                        ))
        );
        Main.TOKEN_MAP.forEach((key, value) ->
                rebuildLine(value.getLexeme(), key)
        );
        lines.clear();
        return code.toString();
    }

    public void start()
    {
        try(FileWriter fileWriter = new FileWriter(PathRS.TOKEN_FILE))
        {
            fileWriter.write(replaceLexemeForToken());
        }
        catch (IOException e)
        {
            Logger.getLogger(GenerateTokenFile.class.getName())
                    .log(Level.SEVERE, "Error con el archivo", e);
        }
    }

}
