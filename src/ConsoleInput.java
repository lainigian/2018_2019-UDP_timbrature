
import java.io.*;

/**
 * 
 * @author User
 *
 */
public class ConsoleInput
{
	//Attributi
	private BufferedReader reader;

	
	//Costruttore
	public ConsoleInput()
	{
		reader=new BufferedReader(new InputStreamReader(System.in));	
	}
	
	public int readInt() throws NumberFormatException, IOException
	{
		return (Integer.parseInt(reader.readLine()));
	}
	
	public short readShort() throws NumberFormatException, IOException
	{
		return (Short.parseShort(reader.readLine()));
	}
	
	public long readLong() throws NumberFormatException, IOException
	{
		return (Long.parseLong(reader.readLine()));
	}
	
	public float readFloat() throws NumberFormatException, IOException
	{
		return (Float.parseFloat(reader.readLine()));
	}
	
	public double readDouble() throws NumberFormatException, IOException
	{
		return (Double.parseDouble(reader.readLine()));
	}
	
	public byte readByte() throws NumberFormatException, IOException
	{
		return (Byte.parseByte(reader.readLine()));
	}
	
	public boolean readBoolean() throws NumberFormatException, IOException
	{
		return (Boolean.parseBoolean(reader.readLine()));
	}
	
	public char readChar() throws NumberFormatException, IOException
	{
		return (reader.readLine().charAt(0));
	}
	
	public String readLine() throws IOException
	{
		return (reader.readLine());
	}
	
}
