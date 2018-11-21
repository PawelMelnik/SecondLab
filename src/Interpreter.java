import java.util.Map;

public abstract class Interpreter {
    protected static final String COLON = ":";

    public static boolean isLexeme(String lexeme, Map<String, Enum> map)
    {
        for (String key : map.keySet())
        {
            if (lexeme.equals(key))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean IsPairCorrect(String[] ParamPair, Map<String, Enum> lexemeMap)
    {
        if (ParamPair.length != 2|| ParamPair[0].isEmpty() || ParamPair[1].isEmpty())
        {
            Log.report("Invalid syntax in config file");
            return false;
        }
        if (!isLexeme(ParamPair[0], lexemeMap))
        {
            Log.report("Unknown lexeme " + ParamPair[0]);
            return false;
        }
        return true;
    }
}
