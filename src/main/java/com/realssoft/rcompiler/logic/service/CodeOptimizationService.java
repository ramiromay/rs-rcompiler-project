package com.realssoft.rcompiler.logic.service;

import com.realssoft.rcompiler.logic.language.RegularExpression;
import com.realssoft.rcompiler.logic.language.Revision;
import com.realssoft.rcompiler.logic.model.OptimizeModel;
import com.realssoft.rcompiler.logic.model.RevisionModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CodeOptimizationService
{

    private static final String START_LOOP_WHILE = "\\{";
    private static final String END_LOOP_WHILE = "}";
    private static final String FRAGMENT_START_WHILE = "startWhile";
    private static final String FRAGMENT_END_WHILE = "endWhile";

    private int startWhile = 0;
    private int endWhile = 0;
    private int startWhileIndex = 0;
    private int endWhileIndex = 0;
    private int position = 0;
    private final List<String> lines;
    private final List<OptimizeModel> optimizeModels;
    private final Set<Integer> lineRemove;
    private final Set<RevisionModel> identifiers;


    public CodeOptimizationService()
    {
        lines = new ArrayList<>();
        optimizeModels = new ArrayList<>();
        lineRemove = new HashSet<>();
        identifiers = new HashSet<>();

    }

    private void separateIdentifiers(String fragment)
    {
        String [] lexemes = fragment.split("\\s+");
        for (String lexeme : lexemes)
        {
            System.out.println("----- lexeme: " + lexeme);
            if (lexeme.trim().matches(RegularExpression.ID.value))
            {
                identifiers.add(new RevisionModel(lexeme, Revision.UNCHECKED));
            }
            System.out.println("----- lexeme add: " + identifiers.toString());
        }
    }

    public void separateLines(String [] lines)
    {
        optimizeModels.clear();
        this.lines.clear();
        int length = lines.length;
        for (int i = 0; i < length; i++)
        {
            this.lines.add(lines[i]);
            String[] fragments = lines[i].trim().split("=");
            String firstFragment =  fragments[0].trim();
            String team =  firstFragment.split("\\s+")[0];
            if (fragments.length == 2 && firstFragment.matches(RegularExpression.ID.value))
            {
                optimizeModels.add(new OptimizeModel(i, firstFragment, fragments[1].trim()));
            }
            else if (firstFragment.matches(START_LOOP_WHILE))
            {
                startWhile = i;
                startWhileIndex = optimizeModels.size();
                optimizeModels.add(new OptimizeModel(startWhile, FRAGMENT_START_WHILE, FRAGMENT_START_WHILE));
            }
            else if (firstFragment.matches(END_LOOP_WHILE))
            {
                endWhile = i;
                endWhileIndex = optimizeModels.size();
                optimizeModels.add(new OptimizeModel(endWhile, FRAGMENT_END_WHILE, FRAGMENT_END_WHILE));
            }
            else if (team.matches(RegularExpression.TEAM_7.value))
            {
                for (String fragment : fragments)
                {
                    System.out.println("----- fragment: " + fragment);
                    separateIdentifiers(fragment);
                }
            }
        }
    }

    private boolean contains(String searchIn, String search)
    {
        String[] fragments = searchIn.split("\\s+");
        for (String fragment : fragments)
        {
            if (fragment.equals(search))
            {
                return true;
            }
        }
        return false;
    }

    private int getArrayIndex(int lineIndex)
    {
        for (int index = 0; index < optimizeModels.size(); index++)
        {
            if (lineIndex == optimizeModels.get(index).getLineIndex())
            {
                return index;
            }
        }
        return -1;
    }

    private void checkIfAlterations(OptimizeModel model, int limit)
    {
        for (int index = 0; index < limit; index++)
        {
            OptimizeModel optimizeModel = optimizeModels.get(index);
            if (contains(optimizeModel.getSecondFragment(), model.getFirstFragment()))
            {
                return;
            }
        }
        lineRemove.add(model.getLineIndex());
    }

    private void checkIfUsed(OptimizeModel model)
    {
        int arrayIndex = getArrayIndex(model.getLineIndex());
        int arraySize = optimizeModels.size();

        for (int index = arrayIndex + 1; index < arraySize; index++)
        {
            OptimizeModel optimizeModel = optimizeModels.get(index);
            System.out.println("Donde se busca: " + optimizeModel.getSecondFragment() + "   Buscar: " + model.getFirstFragment());
            if (contains(optimizeModel.getSecondFragment(), model.getFirstFragment()))
            {
                System.out.println("------ Lo busco");
                return;
            }
        }
        lineRemove.add(model.getLineIndex());
        for (int index = 0; index < arrayIndex; index++)
        {
            OptimizeModel optimizeModel = optimizeModels.get(index);
            if (optimizeModel.getLineIndex() > startWhile
                    && optimizeModel.getLineIndex() < endWhile)
            {
                continue;
            }
            checkIfAlterations(optimizeModel, arrayIndex);
        }
    }

    private boolean checkIfExist(String fragment)
    {
        for (RevisionModel identifier : identifiers)
        {
            if (identifier.getFirstFragment().equals(fragment))
            {
                return true;
            }
        }
        return false;
    }

    private void addIdentifierUsed(String secondFragment)
    {
        String[] fragments = secondFragment.split("\\s+");
        for (String fragment : fragments)
        {
            if (fragment.matches(RegularExpression.ID.value) && (!checkIfExist(fragment)))
            {
                identifiers.add(new RevisionModel(fragment, Revision.UNCHECKED));
            }
        }
    }

    private void checkIfUsedInLoopWhile(OptimizeModel model)
    {
        for (RevisionModel identifier : identifiers)
        {

            System.out.println(model.toString());
            if (contains(model.getFirstFragment(), identifier.getFirstFragment())
                    && identifier.getRevision() == Revision.UNCHECKED)
            {
                addIdentifierUsed(model.getSecondFragment());
                position = startWhileIndex;
                identifier.setRevision(Revision.CHECKED);
                return;
            }
        }

    }

    private void addLineRemove(OptimizeModel model)
    {
        System.out.println(identifiers.toString());
        for (RevisionModel identifier : identifiers)
        {
            if (identifier.getFirstFragment().equals(model.getFirstFragment()))
            {
                return;
            }
        }
        lineRemove.add(model.getLineIndex());
        System.out.println("Linea a eliminar: " + (model.getLineIndex() + 1));
    }

    public String optimizeCode()
    {
        for (position = 0; position < optimizeModels.size(); position++)
        {
            OptimizeModel optimizeModel = optimizeModels.get(position);
            if (optimizeModel.getLineIndex() >= startWhile
                    && optimizeModel.getLineIndex() < endWhile)
            {
              checkIfUsedInLoopWhile(optimizeModel);
            }
            else if (optimizeModel.getLineIndex() == endWhile)
            {
                System.out.println("startWhile: " + startWhile + "   endWhile: " + endWhile);
                for (int index = startWhileIndex + 1; index < endWhileIndex; index ++)
                {


                    OptimizeModel model = optimizeModels.get(index);
                    System.out.println(model.toString());
                    if (model.getFirstFragment().equals(FRAGMENT_END_WHILE))
                    {
                        break;
                    }
                    addLineRemove(model);
                }
            }
            else
            {
                checkIfUsed(optimizeModel);
            }
        }

        int remove = 0;
        for (int lineIndex : lineRemove)
        {
            System.out.println(lineIndex + 1 - remove);
            lines.remove(lineIndex - remove);
            remove ++;
        }
        lineRemove.clear();

        separateLines(lines.toArray(new String[0]));
        for (position = 0; position < optimizeModels.size(); position++)
        {
            OptimizeModel optimizeModel = optimizeModels.get(position);
            if (optimizeModel.getLineIndex() >= startWhile
                    && optimizeModel.getLineIndex() < endWhile)
            {
                checkIfUsedInLoopWhile(optimizeModel);
            }
            else if (optimizeModel.getLineIndex() == endWhile)
            {
                System.out.println("startWhile: " + startWhile + "   endWhile: " + endWhile);
                for (int index = startWhileIndex + 1; index < endWhileIndex; index ++)
                {


                    OptimizeModel model = optimizeModels.get(index);
                    System.out.println(model.toString());
                    if (model.getFirstFragment().equals(FRAGMENT_END_WHILE))
                    {
                        break;
                    }
                    addLineRemove(model);
                }
            }
            else
            {
                checkIfUsed(optimizeModel);
            }
        }

        remove = 0;
        for (int lineIndex : lineRemove)
        {
            System.out.println( (lineIndex + 1 - remove));
            lines.remove(lineIndex - remove);
            remove ++;
        }
        lineRemove.clear();
        identifiers.clear();
        StringBuilder newCode = new StringBuilder();
        for (String line : lines)
        {
            newCode.append(line).append("\n");
        }
        return newCode.toString();
    }

}
