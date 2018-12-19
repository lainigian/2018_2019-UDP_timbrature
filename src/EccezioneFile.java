
/**
 * Eccezione usata quando si accede con operazioni in lettura su un file aperto in scrittura oppure
 * con operazioni in scrittura su un file aperto in lettura
 * @author User
 *
 */
public class EccezioneFile extends Exception 
{
	
	private String messaggio;
	public EccezioneFile (String messaggio)
	{
		this.messaggio=messaggio;
	}
	
	public String toString()
	{
		return messaggio;
	}

	
}
