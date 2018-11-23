import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InterpreterWorkers {
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

        if (!isAllWorkers(resultMap))
        {
            Log.report("Incorrect types of workers");
            throw new IOException();
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

    public static boolean isAllWorkers(Map<String, WorkerProperties> resultMap)
    {
        if (isContainsFirst(resultMap) && isContainsLast(resultMap))
        {
            return  true;
        }
        return false;
    }

    public static boolean isContainsFirst(Map<String, WorkerProperties> resultMap)
    {
        for (String worker_key: resultMap.keySet())
        {
            if(resultMap.get(worker_key).type_of_worker.equals("WORKER_FIRST"))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean isContainsLast(Map<String, WorkerProperties> resultMap)
    {
        for (String worker_key: resultMap.keySet())
        {
            if(resultMap.get(worker_key).type_of_worker.equals("WORKER_LAST"))
            {
                return true;
            }
        }
        return false;
    }
}
