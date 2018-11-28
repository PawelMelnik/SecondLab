import java.io.*;

public class RLECoder implements Executor {

    private String input = null;
    private String output = null;
    Executor Consumer;
    public byte[] block = null;
    int CODE_MODE;

    public void SetInput (String input)
    {
        this.input = input;
    }
    public void SetOutput (String output)
    {
        this.output = output;
    }

    public void SetConsumer(Executor ex)
    {
        this.Consumer = ex;
    }
    public void Run()
    {
        try {
            if (this.CODE_MODE == 0)
            {
                RunEncode();
            }
            else
            {
                RunDecode();
            }
        }
        catch (IOException e)
        {
            Log.report("Can't run");//////////
        }
    }
    public void Put(Object obj)
    {
        this.block = (byte [])obj;
    }

    private void RunEncode() throws IOException
    {
        if (input != null && block != null)
        {
            Log.report("Incorrect first worker");
            throw new IOException();
        }

        if (input != null)
        {
            ReadToArray();
        }

        int block_index = 0;
        int out_arr_index = 0;
        byte count = 1;
        byte i = block[block_index];
        byte[] out_array = new byte[8];

        while (i != -1 && block_index < 4)
        {
            byte j = block[block_index + 1];

            while (j == i && block_index < 4)
            {
                count++;
                block_index++;
                j = block[block_index];
            }


            out_array[out_arr_index] = count;
            out_arr_index++;
            out_array[out_arr_index] = i;
            out_arr_index++;
            i = j;
            count = 1;

        }
    }


    private static void RunDecode(String inputFile, String outputFile, int min_length) throws IOException
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

    private void ReadToArray() throws IOException
    {
        byte[] array = new byte[4];
        try (FileInputStream readerInput = new FileInputStream(input))
        {

            byte i = 0;
            int k = 0;

            while (i != -1 && k < 4)
            {
                i = (byte)readerInput.read();
                array[k] = i;
                k++;
            }
        }
        catch (IOException e)
        {
            Log.report("Can't encode file");
            throw new IOException();
        }
        block = array;
    }

    /*DataInputStream ChooseInput() throws IOException
    {
        DataInputStream inp;
        if(input == null)
            inp = new DataInputStream(new ByteArrayInputStream(this.block));
        else
            inp = new DataInputStream(new FileInputStream(input));

        return inp;
    }

    DataOutputStream ChooseOutput() throws IOException
    {
        DataOutputStream out;
        if(input == null)
            out = new DataOutputStream(new ByteArrayOutputStream());
        else
            out = new DataOutputStream(new FileOutputStream(output));

        return out;
    }*/

    /*private void RunEncode() throws IOException
    {
        DataInputStream readerInput = null;
        DataOutputStream writerOutput = null;
        try
        {
            readerInput = ChooseInput();
            writerOutput = ChooseOutput();
        }
        catch(IOException ex){

            Log.report("Can't decode file");
            throw new IOException();
        }

        try
        {
            byte i = readerInput.readByte();
            int k = 1;

            while (i != -1)
            {
                byte j = readerInput.readByte();

                while (j == i)
                {
                    k++;
                    j = readerInput.readByte();
                }

                writerOutput.writeByte((byte)k);
                writerOutput.flush();
                writerOutput.writeByte(i);
                writerOutput.flush();

                i = j;
                k = 1;
            }
        }

        catch (IOException e)
        {

        }

        if(output == null)
        {
            Consumer.Put(writerOutput.toString());
        }
    }


    private void RunDecode() throws IOException
    {
        DataInputStream readerInput = null;
        DataOutputStream writerOutput = null;
        try
        {
            readerInput = ChooseInput();
            writerOutput = ChooseOutput();
        }
        catch(IOException ex){

            Log.report("Can't decode file");
            throw new IOException();
        }


        byte k = 0;
        try {
            while((k = readerInput.readByte()) != -1)
            {
                byte j = readerInput.readByte();
                while( k > 0)
                {
                    writerOutput.writeByte((char)j);
                    writerOutput.flush();
                    k--;
                }
            }
        }
        catch (IOException e)
        {

        }
        readerInput.close();
        writerOutput.close();

    }*/

}
