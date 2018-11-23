import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

public class Manager {
    private Map<Enum, String> configMap;
    Manager(String config) throws IOException
    {
        configMap = new EnumMap(InterpreterConfig.Params.class);
        InterpreterConfig.Interpreted(config, configMap);
        Map<String, WorkerProperties> workersMap = new HashMap<>();
        Map<String, String> workers_relationsMap = new HashMap<>();
        Map<String, Map<Enum, String>> work_configsMap = new HashMap<>();/*EnumMap(InterpreterWorkersConfigs.Params.class)*/
        InterpreterWorkers.Interpreted(configMap.get(InterpreterConfig.Params.WORKERS_FILE), workersMap);
        InterpreterRelations.Interpreted(configMap.get(InterpreterConfig.Params.RELATIONS_FILE), workers_relationsMap);
        InterpreterWorkersConfigs.Interpreted(workersMap, work_configsMap);

        conveyorMap = new HashMap<>();
        CreateConveyorMap(workersMap, workers_relationsMap, work_configsMap);
    }
    private Map<String, Executor> conveyorMap;
    private void CreateConveyorMap(Map<String, WorkerProperties> workersMap, Map<String, String> workers_relationsMap, Map<String, Map<Enum, String>> work_configsMap)
    {
        for (String key : workersMap.keySet())
        {

        }
    }
}
