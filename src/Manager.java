import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

public class Manager {
    private Map<Enum, String> configMap;
    private Map<String, Executor> conveyorMap;
    private Executor first_worker;
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

    private void CreateConveyorMap(Map<String, WorkerProperties> workersMap, Map<String, String> workers_relationsMap, Map<String, Map<Enum, String>> work_configsMap)
    {

        for (String key : workersMap.keySet())
        {
            Executor Variable = new RLECoder();
            String input = null, output = null;
            InterpreterWorkers.WorkersTypes type = workersMap.get(key).type_of_worker;
            if (type == InterpreterWorkers.WorkersTypes.WORKER_FIRST)
            {
                input = configMap.get(InterpreterConfig.Params.INPUT_FILE);
                Variable.SetInput(input);
                first_worker = Variable;
            }
            else if (type == InterpreterWorkers.WorkersTypes.WORKER_LAST)
            {
                output = configMap.get(InterpreterConfig.Params.OUTPUT_FILE);
                Variable.SetOutput(output);
            }
            int code_mode = Integer.parseInt(work_configsMap.get(key).get(InterpreterWorkersConfigs.Params.CODE_MODE));
            Variable.SetCodeMode(code_mode);
            conveyorMap.put(key, Variable);
        }
        for (String key : workers_relationsMap.keySet())
        {
            Executor Worker = conveyorMap.get(key);
            Executor Consumer = conveyorMap.get(workers_relationsMap.get(key));
            Worker.SetConsumer(Consumer);
        }
    }

    public void Run()
    {
        while ()
        {
            first_worker.Run();
        }
    }
}
