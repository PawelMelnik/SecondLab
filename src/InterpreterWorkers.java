import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InterpreterWorkers extends Interpreter{
    protected static final String COLON = ":";
    public static final Map<String, Enum> workers_lexemeMap;
    public enum WorkersTypes {WORKER_FIRST, WORKER_LAST, WORKER}
    static
    {
        workers_lexemeMap = new HashMap<>();
        workers_lexemeMap.put("WORKER_FIRST", WorkersTypes.WORKER_FIRST);
        workers_lexemeMap.put("WORKER_LAST", WorkersTypes.WORKER_LAST);
        workers_lexemeMap.put("WORKER", WorkersTypes.WORKER);
    }

    public static void Interpreted(String config, Map<String, WorkerProperties> resultMap) throws IOException
    {
        MyReader reader = new MyReader(config);
        String ParamString;

        while ((ParamString = reader.ReadLine()) != null)
        {
            if ((ParamString = ParamString.replaceAll("\\s", "")).isEmpty())
            {
                continue;
            }
            String[] ParamPair = ParamString.split(COLON);
            if (!IsTripletCorrect(ParamPair))
            {
                throw new IOException();
            }
            WorkerProperties properties = new WorkerProperties(ParamPair[2], (WorkersTypes)workers_lexemeMap.get(ParamPair[0]));/////&&&&&
            resultMap.put(ParamPair[1], properties);
        }

        if (!resultMap.containsKey(WorkersTypes.WORKER_FIRST))/////////
        {
            Log.report("Input file is not found");
            throw new FileNotFoundException();
        }

        if (!resultMap.containsKey(WorkersTypes.WORKER_LAST))///////////
        {
            Log.report("Workers file is not found");
            throw new FileNotFoundException();
        }

        reader.CloseStream();
    }

    public static boolean isLexeme(String lexeme)
    {
        for (String key : workers_lexemeMap.keySet())
        {
            if (lexeme.equals(key))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean IsTripletCorrect(String[] ParamPair)
    {
        if (ParamPair.length != 3|| ParamPair[0].isEmpty() || ParamPair[1].isEmpty()|| ParamPair[2].isEmpty())
        {
            Log.report("Invalid syntax in config file");
            return false;
        }
        if (!isLexeme(ParamPair[0]))
        {
            Log.report("Unknown lexeme " + ParamPair[0]);
            return false;
        }
        return true;
    }
}
