package com.realssoft.rcompiler.ui.values;

public class StringRS
{

    /**
     * TEXT FOR THE MENU
     */
    public static final String NAME_COMPANY = "RealsSoft";
    public static final String MENU_BUTTON_HOME = "Inicio";
    public static final String MENU_BUTTON_ABOUT = "Acerca";

    /**
     * TEXT FOR THE HOME ACTIVITY
     */
    public static final String RC_COMPILER = "RCompiler";
    public static final String RCC_DESCRIPTION = "<html>Accede al compilador <br>favorito del lenguaje RS</html>";
    public static final String RC_TEST = "Tutorial";
    public static final String RCT_DESCRIPTION = "<html>Conoce el lenguaje RS, <br>el lenguaje de los reales</html>";
    public static final String ALPHABETIC = "Alfabeto";
    public static final String RULES = "Tabla de reglas o sintaxis";
    public static final String WELCOME = "Bienvenido al compilador RCompiler";
    public static final String RS = "Lenguaje RS";

    /**
     * TEXT FOR THE ALPHABETIC ACTIVITY
     */
    public static final String ALPHABETIC_DESCRIPTION = "Caracteres admitidos por el lenguaje RS";
    public static final String [] ALPHABETIC_COLUMN_TABLE = {
            "Carácter",
            "ASCII"
    };

    /**
     * TEXT FOR THE RULES ACTIVITY
     */
    public static final String RULE_DESCRIPTION = "Reglas y sintaxis admitida por el lenguaje RS";
    public static final String [] RULES_COLUMN_TABLE = {
            "Instrucción",
            "Regla o sintaxis",
            "Lenguaje regular",
            "Expresión regular"};
    public static final String [] RULES_SEPARATORS = {
            "Separadores",
            "Los símbolos permitidos de los separadores pueden ser los siguientes.",
            "{ ;,,,.,∶,(,),[ ,],{,} }",
            "; U ,U .U ∶ U ( U ) U [ U ] U { U }"
    };
    public static final String [] RULES_O_ARITHMETIC = {
            "Operaciones aritméticas",
            "Los símbolos permitidos de las operaciones aritméticas pueden ser las siguientes.",
            "{+,−,*,/,%}",
            "+ U – U * U / U %"
    };
    public static final String [] RULES_O_RELATIONAL = {
            "Operaciones relacionales",
            "Los símbolos permitidos de las operaciones relaciones son las siguientes.",
            "{<,<=,>,> =,==,!=}",
            "< U <= U > U > = U == U !="
    };
    public static final String [] RULES_O_ASSIGMENT = {
            "Operador de asignación",
            "Los símbolos permitidos para asignación son los siguientes.",
            "{=}",
            "="
    };
    public static final String [] RULES_O_LOGIC = {
            "Operadores lógicos o booleanos",
            "Los símbolos permitidos para las operaciones lógica o booleanas son las siguientes.",
            "{&&,||}",
            "&& U ||"
    };
    public static final String [] RULES_ID = {
            "Identificadores",
            "Inicia con la palabra real seguidamente de una letra mayúscula, puede incluir guion bajo o cualquier secuencia de número.",
            "{realNum, realNum3, realNum_3,… }]",
            "real(A-Z)+(a−z U _ U 0−9)*"
    };
    public static final String [] RULES_NUMS_INTEGERS = {
            "Números enteros",
            "Los números enteros pueden ser positivos o negativos e inician y finalizan con el número 7.",
            "{707, 787, −717, 77087, −7807…}",
            "(ϵU−)7(0-9)+7"
    };
    public static final String [] RULES_NUMS_FLOAT = {
            "Números con puntos decimales",
            "Los números pueden ser positivos o negativos y la parte entera inicia y finaliza con el número 7.",
            "{707.15,787.11, −717.65,77087.54823, −7807.157…}",
            "(ϵU−)7(0−9)+7.(0−9)+"
    };
    public static final String [] RULES_TYPE_DATA = {
            "Tipos de datos",
            "Los tipos de datos pueden ser RAM, EDU y JOA",
            "{RAM,EDU,JOA}",
            "RAM U EDU U JOA"
    };
    public static final String [] RULES_TEAM_7 = {
            "Equipo 7",
            "Cada palabra de este lenguaje regular deberá iniciar y terminar con el número 7.",
            "{7while7}",
            "7while7"
    };

}
