import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Executor;

public class RLECoder implements Executor {
    private FileInputStream input = null;
    private FileOutputStream output = null;
    Executor Consumer;
    public byte[] block;
    int CODE_MODE;

    public void SetInput (FileInputStream input)
    {
        this.input = input;
    }
    public void SetOutput (FileOutputStream output)
    {
        this.output = output;
    }

    public void SetConsumer(Executor ex)
    {
        this.Consumer = ex;
    }
    public void Run() throws IOException
    {
        if (this.CODE_MODE == 0)
        {
            RunEncode();
        }
        else
        {
            RunDecode();
        }
    }
    public void Put(Object obj)
    {
        this.block = (byte [])obj;
    }

    private static void RunEncode() throws IOException
    {
        try (FileInputStream readerInput = new FileInputStream(inputFile);
             FileOutputStream writerOutput = new FileOutputStream(outputFile))
        {

            byte i = (byte)readerInput.read();
            int k = 1;

            while (i != -1)
            {
                byte j = (byte)readerInput.read();

                while (j == i)
                {
                    k++;
                    j = (byte)readerInput.read();
                }

                writerOutput.write((byte)k);
                writerOutput.flush();
                writerOutput.write(i);
                writerOutput.flush();

                i = j;
                k = 1;
            }
        }

        catch (IOException e)
        {
            Log.report("Can't encode file");
            throw new IOException();
        }

    }


    private static void RunDecode() throws IOException
    {
        try (FileInputStream readerInput = new FileInputStream(inputFile);
             FileOutputStream writerOutput = new FileOutputStream(outputFile))
        {
            if(min_length <= 0) {
                throw new IOException("Wrong portion length. Expected: >=0. Found:" + min_length);
            }

            byte k = 0;
            while((k = (byte)readerInput.read()) != -1)
            {
                byte j = (byte)readerInput.read();
                while( k > 0)
                {
                    writerOutput.write((char)j);
                    writerOutput.flush();
                    k--;
                }
            }
        }

        catch (IOException e)
        {
            Log.report("Can't decode file");
            throw new IOException();
        }

    }

}
