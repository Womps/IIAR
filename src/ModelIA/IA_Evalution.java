package ModelIA;

public class IA_Evalution 
{
	private static IA_Evalution instanceIaEval = null; 
	private IA_Evalution()
	{
		
	}
	
	public IA_Evalution getInstance()
	{
		if(instanceIaEval == null)
			instanceIaEval = new IA_Evalution();
			
		return instanceIaEval;
	}
	
	public int evalution()
	{
		return 0;
	}
}
