import java.io.FileWriter;
import java.io.IOException;

public class Log {
    private static FileWriter logWriter;
    public static String lineSeparator = System.getProperty("line.separator");

    private  static void init()
    {
        try
        {
            logWriter = new FileWriter("log.log");
        }
        catch (IOException e)
        {
            System.out.println("Can't create log file");
            e.printStackTrace();
        }
        report("Program booted");
    }

    public static void report(String report)
    {
        if (logWriter == null)
        {
            init();
        }
        try
        {
            logWriter.write(report);
            logWriter.write(lineSeparator);
            logWriter.flush();
        }
        catch (IOException e)
        {
            System.out.println("Can't write to log file");
            e.printStackTrace();
        }
    }

    public static void close()
    {
        try
        {
            report("Program end");
            logWriter.flush();
            logWriter.close();
        }
        catch (IOException e)
        {
            System.out.println("Can't close log file");
            e.printStackTrace();
        }
    }
}
