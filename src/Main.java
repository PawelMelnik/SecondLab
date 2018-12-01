import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 1)
        {
            Manager manager = new Manager(args[0]);
            manager.Run();
        }
        else
        {
            Log.report("Wrong file format");
        }
        Log.close();
        System.exit(0);
    }
}
