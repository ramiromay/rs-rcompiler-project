package com.realssoft.rcompiler.ui.component;

import com.realssoft.Main;
import com.realssoft.rcompiler.logic.file.FileManager;
import com.realssoft.rcompiler.logic.service.CodeOptimizationService;
import com.realssoft.rcompiler.logic.service.LexicalService;
import com.realssoft.rcompiler.logic.service.SemanticService;
import com.realssoft.rcompiler.logic.service.SyntaxService;
import com.realssoft.rcompiler.logic.service.TriploServices;
import com.realssoft.rcompiler.ui.support.notification.Notifications;
import com.realssoft.rcompiler.ui.values.ColorRS;
import com.realssoft.rcompiler.ui.values.observers.IEventObserver;
import com.realssoft.rcompiler.ui.values.ConfigureComponent;
import com.realssoft.rcompiler.ui.values.FontRS;
import lombok.Setter;

import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class CodeSpace extends JTextPane
        implements ConfigureComponent, IEventObserver<Integer>
{

    private static final CodeSpace INSTANCE = new CodeSpace();
    private StyledDocument doc;
    private Style textStyle;
    private LexicalService lexicalService;
    private SyntaxService syntaxService;
    private SemanticService semanticService;
    private TriploServices  triploServices;
    private CodeOptimizationService codeOptimizationService;
    private FileManager fileManager;
    private Notifications notifications;

    @Setter
    private JTextPane paneOptimize;

    public CodeSpace()
    {
        super();
        configureProperties();
        configureMouseEvents();
        configureComponents();
        configureBorder();
    }

    public static CodeSpace getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void configureProperties()
    {
        this.setBackground(ColorRS.WHITE);
        this.setFont(FontRS.changeFont(FontRS.SEGOE_UI_REGULAR, Font.PLAIN, 20));
    }

    @Override
    public void configureComponents()
    {
        fileManager = FileManager.getInstance();
        notifications = Notifications.getInstance();
        lexicalService = new LexicalService();
        syntaxService = new SyntaxService();
        semanticService = new SemanticService();
        triploServices = new TriploServices();
        codeOptimizationService = new CodeOptimizationService();
    }

    @Override
    public void configureBorder()
    {
        this.setBorder(new EmptyBorder(0, 2, 0, 0));
    }

    @Override
    public boolean getScrollableTracksViewportWidth()
    {
        return getUI().getPreferredSize(this).width
                <= getParent().getSize().width;
    }

    @Override
    public void update(Integer generic)
    {
        if (generic == 1)
        {
            Main.TOKEN_MAP.clear();
            Main.TOKEN_ERROR_MAP.clear();
            Main.TRIPLO_MAP.clear();


            //processText2(this.getText(), true);
            String[] l = this.getText().replace("\r", "").split("\n");
            codeOptimizationService.separateLines(l);
            String newCode = codeOptimizationService.optimizeCode();
            paneOptimize.setText(newCode);
            String[] lines = newCode.replace("\r", "").split("\n");

            for (int line = 0; line < lines.length; line++)
            {
                String [] lexemeList = lines[line].trim().split("\\s+");
                lexicalService.verify(lexemeList, line + 1);
                syntaxService.verify(lexemeList, line + 1);
                semanticService.verify(lexemeList, line + 1);
                triploServices.verify(lexemeList,line, lines.length - 1);
            }

        }

        if(generic == 2)
        {
            fileManager.setAbsolutePath(null);
            fileManager.getFileName().setLength(0);
            fileManager.getContent().setLength(0);
            CompletableFuture.runAsync(() ->
            {
                try
                {
                    notifications.show(
                            Notifications.Type.WARNING,
                            Notifications.Location.BOTTOM_RIGHT,
                            "Borrando contenido");
                    Thread.sleep(600);
                    this.setText("");
                    notifications.show(
                            Notifications.Type.WARNING,
                            Notifications.Location.BOTTOM_RIGHT,
                            "Preparando archivo en blanco"
                    );
                    Thread.sleep(600);
                    notifications.show(
                            Notifications.Type.SUCCESS,
                            Notifications.Location.BOTTOM_RIGHT,
                            "Listo"
                    );
                    NavBar.getInstance().repaint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        if (generic == 3)
        {
            String content = fileManager.readFile();
            if (!content.isEmpty())
            {
                notifications.show(
                        Notifications.Type.SUCCESS,
                        Notifications.Location.BOTTOM_RIGHT,
                        "Listo"
                );
                this.setText(content);
                NavBar.getInstance().repaint();
            }
        }

        if (generic == 4)
        {
            fileManager.writeFile(this.getText());
        }

    }

    private String TIPO = "^(RAM|EDU|JOA|7while7|\\{|\\})$";
    private String TIPO2 = "^(RAM|EDU|JOA|\\{|\\})$";
    HashMap<String, String> hashMap;


    public static void crearArchivo(String contenido) {
        // Nombre del archivo
        String nombreArchivo = "codigoOriginal.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            // Escribir el contenido en el archivo
            writer.write(contenido);

            System.out.println("Archivo creado exitosamente: " + nombreArchivo);
        } catch (IOException e) {
            // Manejar la excepción en caso de error
            e.printStackTrace();
        }
    }

    private void processText2(String text, boolean proceso) {
        String[] lines = text.split("\n");
        StringBuilder combinedLines = new StringBuilder();
        StringBuilder combinedLines2 = new StringBuilder();
        boolean shouldCombine = false;
        //LIMPIA ES HASHMAP
        hashMap = new HashMap<>();


        int cont = 0;

        for (String line : lines) {
            cont = 0;
            String[] lexemes = line.trim().split("\\s+");

            if (lexemes.length > 0 && lexemes[0].matches(TIPO)) {
                if (shouldCombine) {
                    combinedLines.append("\n").append(line);
                } else {
                    combinedLines.append(line);
                }
                shouldCombine = true;
            }else if (lexemes.length > 0 && !lexemes[0].matches(TIPO)) {
                if (cont == 0) {
                    combinedLines.append("\n").append(line.split(lexemes[0] + "=")[0]);
                    combinedLines2.append(line.replace(lexemes[0] + " = ", ""));
                    //MAPEA LAS VARIABLES EJEMPLO: hashMap.put("realT * realT1 ;", "realE =");
                    hashMap.put(combinedLines2.toString(), lexemes[0] + "=");
                    cont++;
                    combinedLines2.setLength(0);
                }
            }
        }
        //SI PROCESO ES FALSO ES PORQUE YA ES SU SEGUNDA MAPEADA O M�S MAPEADAS
        if(proceso){
            processText3(combinedLines.toString());
        }else{
            setText(combinedLines.toString());
            //LLAMAR AL PROCESO 3 YA QUE ESTAN LIBRES
            processText3(combinedLines.toString());
        }
    }

    //YA CAMBIA LAS LINEAS QUE NO SE USAN PARA NADA EN TODO EL PROGRAMA
    private void processText3(String text){
        String[] lines = text.split("\n");
        StringBuilder combinedLines = new StringBuilder();
        boolean seUsa= false;
        boolean shouldCombine = false;
        String[] lexemesEspecificos = null;
        int contGlobal=0;
        //PARA MAPEAR LAS VARIABLES QUE ESTAN EN EL WHILE ()
        for (String line : lines) {
            String[] lexemes = line.trim().split("\\s+");
            if (lexemes.length > 0 && !lexemes[0].matches(TIPO2)) {
                if(lexemes[0].equals("7while7")){
                    int tamano = lexemes.length;
                    if(tamano==10||tamano==11){
                        lexemesEspecificos= new String[]{lexemes[2], lexemes[4], lexemes[6], lexemes[8]};
                    }else if(tamano==6||tamano==7){
                        lexemesEspecificos = new String[]{lexemes[2], lexemes[4]};
                    }
                }
            }
        }
        //CHECAR QUE VARIABLES SE USAN
        for (int i = 0; i< lines.length; i++) {
            String line = lines[i];
            String[] lexemes = line.trim().split("\\s+");
            //VARIABLES DEL WHILE
            if (lexemes.length > 0 && !lexemes[0].matches(TIPO2)) {
                if(lexemes[0].equals("7while7")){
                    seUsa = true;
                }else{
                    seUsa = contain(lexemes[0]);
                }
            }else{
                seUsa=true;
            }
            //A�ADE UNA LINEA combinedLines SI SE UTILIZA ESE RESULTADO DE VARIABLE EN TODO EL PROGRAMADA
            if(seUsa){
                if (shouldCombine) {
                    combinedLines.append("\n").append(line);
                } else {
                    combinedLines.append(line);
                }
                shouldCombine = true;
                seUsa=false;
            }else{
                //CHECAR QUE VARIABLES SE USAN EN EL WHILE
                if(lexemesEspecificos != null) {
                    for (String clave : lexemesEspecificos) {
                        if (clave.contains(lexemes[0])) {
                            combinedLines.append("\n").append(line);
                            seUsa = true;
                        }
                    }
                    //AUMENTA EL CONTGLOBAL PARA VOLVER A MAPEAR
                    if (!seUsa) {
                        contGlobal++;
                    }
                }

            }
        }
        //SI NO SE USA ESA VARIABLE ENTRARA AL PROCESO 2 QUE MAPEA TODA LA ENTRADA DE NUEVO PERO ESTA VEZ NO LO PONE EN TEXTAREA
        if(contGlobal!=0){
            processText2(combinedLines.toString(), true);
        }else{
            setText(combinedLines.toString());
        }
    }

    //VERIFICA SI SE USA EL LEXEMA EN EL COTENIDO DEL PROGRAMA
    private boolean contain(String lexeme){
        if(lexeme.contains("real")){
            for (String clave : hashMap.keySet()) {
                if (clave.contains(lexeme)) {
                    return true;
                }
            }
        }
        return false;
    }

}
