

import java.io.*;


public class TextFile 
{

	private char mode;
	private BufferedReader reader;
	private BufferedWriter writer;
	
	/**
	 * Costruttore
	 * @param filename pathname del file da aprire
	 * @param mode modalità di apertura del file R per sola lettura, W per sola scrittura
	 * @throws IOException nel caso non sia possibile accedere al file
	 */
	public TextFile(String fileName, char mode) throws IOException
	{
		this.mode='R';
		if (mode=='W'|| mode=='w')
		{
			this.mode='W';
			writer=new BufferedWriter(new FileWriter(fileName,true));
		}
		else
			reader=new BufferedReader(new FileReader(fileName));
		
	}
	
	/**
	 * Scrive una stringa sul file di testo
	 * @param line stringa da scrivere
	 * @throws IOException nel caso non sia possibile accedere al file
	 * @throws EccezioneFile se si tenta di scrivere su un file aperto in lettura
	 */
	public void toFile(String line) throws EccezioneFile, IOException
	{
		
		if (mode=='R')
			throw new EccezioneFile("File aperto in lettura");
		
		writer.write(line);
		writer.newLine();
	}
	
	/**
	 * Legge una riga di testo dal file.
	 * @return riga letta
	 * @throws IOException nel caso non sia possibile accedere al file
	 * @throws FileException se il file è aperto in scrittura
	 * @throws EccezioneTextFileEOF se è stata raggiunta la fine del file
	 */
	public String fromFile() throws EccezioneFile, IOException, EccezioneTextFileEOF
	{
		String line;
		
		if (mode=='W')
			throw new EccezioneFile("File aperto in scrittura");
		
		line=reader.readLine();
		
		if (line==null)
			throw new EccezioneTextFileEOF();
		
		return line;
	}
	
	/**
	 * Chiude il file aperto.
	 * Da invocare al termine delle operazioni di scrittura (dopo toFile)
	 * e di lettura (dopo fromFile)
	 * @throws IOException nel caso non sia possibile accedere al file
	 */
	public void closeFile() throws IOException
	{
		if (mode=='R')
			reader.close();
		else
			writer.close();
	}
	
}
