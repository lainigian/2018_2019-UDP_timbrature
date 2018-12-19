
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class UDPclient 
{

	private DatagramSocket socket;
	
	public UDPclient() throws SocketException
	{
		socket= new DatagramSocket();
		socket.setSoTimeout(1000);
	}
	
	public void closeSocket()
	{
		socket.close();
	}
	
	public String sendAndReceive(String host, int port, String messaggio) throws UnsupportedEncodingException, UnknownHostException, IOException
	{
		byte[] bufferRequest=new byte[5];
		byte[] bufferAnswer=new byte[8];
		DatagramPacket request;
		DatagramPacket answer;
		InetAddress address=InetAddress.getByName(host);
		
		
		
		String rispostaServer = null;
		
		bufferRequest=messaggio.getBytes("ISO-8859-1");
		request=new DatagramPacket(bufferRequest, bufferRequest.length, address, port);
		answer=new DatagramPacket(bufferAnswer, bufferAnswer.length);
		socket.send(request);
		socket.receive(answer);
		if (answer.getAddress().getHostAddress().compareTo(host)==0 && answer.getPort()==port)
		{
			rispostaServer=new String(answer.getData(), "ISO-8859-1");
		}
		closeSocket();
		return rispostaServer;
	}
	
	public static void main(String[] args) 
	{
		String rispostaServer;
		String host="127.0.0.1";
		int port=2000;
		String messaggio="111;5";
		
		
		
		try 
		{
			UDPclient client=new UDPclient();
			rispostaServer=client.sendAndReceive(host, port, messaggio);
			System.out.println("Risposta dal server: "+rispostaServer);
		} 
		catch (SocketTimeoutException e)
		{
			System.err.println("Il server non risponde");
		}
		catch (SocketException e)
		{
			System.err.println("Impossibile istanziare il socket");
		} 
		catch (UnsupportedEncodingException e)
		{
			System.err.println("Charset non supportato");
		} 
		catch (UnknownHostException e) 
		{
			System.err.println("Host sconosciuto");
		} 
		catch (IOException e) 
		{
			System.err.println("Errore generico di I/O");
		}
		

	}

}
