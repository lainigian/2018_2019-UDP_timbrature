
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.time.LocalDate;

public class UDPserver extends Thread
{

	private DatagramSocket socket;
	private String filePresenze="presenze.txt";
	
	public UDPserver (int port) throws SocketException
	{
		socket=new DatagramSocket(port);
		socket.setSoTimeout(1000);
		
	}
	
	public void run()
	{
		byte[] bufferRequest= new byte[5];
	//	byte[] bufferAnswer= new byte[5];
		DatagramPacket request=new DatagramPacket(bufferRequest, bufferRequest.length);
		DatagramPacket answer;
		String messaggioRicevuto;
		String messaggioRisposta;
		String scritturaFile;
		boolean giaPresente;
		
		while (!interrupted())
		{
			try 
			{
				socket.receive(request);
				messaggioRicevuto= new String(request.getData(), "ISO-8859-1");
				
				try 
				{
					giaPresente=cercaInFile(messaggioRicevuto);
					if (giaPresente)
						messaggioRisposta=scriviFile("UOK"+messaggioRicevuto);
					else
						messaggioRisposta=scriviFile("EOK"+messaggioRicevuto);
				} 
				catch (EccezioneFile | IOException e) 
				{
					messaggioRisposta="ERROR";
				}
				//messaggioRisposta="EOK"+messaggioRicevuto;
				
				
				answer=new DatagramPacket(messaggioRisposta.getBytes("ISO-8859-1"), messaggioRisposta.length(), request.getAddress(), request.getPort());
				socket.send(answer);		
			} 
			catch (SocketTimeoutException e) 
			{
				System.err.println("Timeout");
			}
			catch (IOException e) 
			{
				
				e.printStackTrace();
			}
		}
		closeSocket();
		
	}
	
	public void closeSocket()
	{
		socket.close();
	}
	
	private String scriviFile(String presenzaAssenza) throws IOException, EccezioneFile, FileNotFoundException
	{
		TextFile file= new TextFile(filePresenze, 'w');
		file.toFile(presenzaAssenza+";"+LocalDate.now().toString());
		file.closeFile();
		return presenzaAssenza;
	}
	
	private boolean cercaInFile(String matricola) throws IOException, EccezioneFile
	{
		String recordLetto;
		TextFile file= new TextFile(filePresenze, 'r');
		String[] elementiRecord;
		
			try 
			{
				while(true)
				{
					recordLetto=file.fromFile();
					elementiRecord=recordLetto.split(";");
					if (elementiRecord[0].compareTo("EOK"+matricola)==0 && elementiRecord[1].compareTo(LocalDate.now().toString())==0)
					{
						file.closeFile();
						return true;
					}
					
				} 
			}
			catch (EccezioneTextFileEOF e) 
			{
				return false;
			}
		
		
	}
	public static void main(String[] args)
	{
		ConsoleInput tastiera= new ConsoleInput();
		try 
		{
			UDPserver echoServer= new UDPserver(2000);
			echoServer.start();
			tastiera.readLine();
			echoServer.interrupt();
			
		} 
		catch (SocketException e) 
		{
			System.err.println("Impossibile istanziare il socket");
		} 
		catch (IOException e) 
		{
			System.out.println("Errore generico di I/O dalla tastiera");
		}

	}

}
