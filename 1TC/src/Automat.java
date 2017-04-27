import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Automat {
	String start;
	ArrayList<String> finale;
	ArrayList<Tranzitie> tranzitii;
	String stare_curenta;
	public Automat() throws IOException
	{
		this.finale = new ArrayList<>();
		this.tranzitii = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader("automat.txt"));
		String line = br.readLine();
		this.start = line;
		line = br.readLine();
		for(String w:line.split("\\s+"))
			this.finale.add(w);
		while((line = br.readLine())!=null)
		{
			String[] tranzitie = line.split("\\s+");
			Tranzitie tr = new Tranzitie();
			tr.stare_plecare = tranzitie[0];
			tr.stare_sosire = tranzitie[2];
			if (tranzitie[1].length() > 1) 
			{
                switch (tranzitie[1]) 
                {
                    case "\\u00A0" : { tr.caracter=(char)32; } break;
                    case "\\r" : { tr.caracter=(char)13; } break;
                    case "\\n" : { tr.caracter=(char)10; } break;
                    case "\\t" : { tr.caracter=(char)9; } break;
                    case "\\f" : { tr.caracter=(char)12; } break;
                    case "\\u000B" : { tr.caracter=(char)11; } break;
                    default : {} break;
                }
            }
			else
				tr.caracter = tranzitie[1].charAt(0);
			this.tranzitii.add(tr);
		}
		br.close();
		/*System.out.println(this.start);
		for(String x:this.finale)
			System.out.print(x+" ");
		System.out.println();
		for(Tranzitie x:this.tranzitii)
			System.out.println(x.stare_plecare+" "+x.caracter+" "+x.stare_sosire);*/
	}
	public String getTranzitie(String stare,char caracter)
	{
		String staref = null;
		for(Tranzitie x:this.tranzitii)
			if(x.stare_plecare.equals(stare)&&x.caracter==caracter)
			{
				staref = x.stare_sosire;
				break;
			}
		return staref;
	}
	public void setStareCurenta(String s)
	{
		this.stare_curenta = s;
	}
	public String getStareCurenta()
	{
		return this.stare_curenta;
	}
	public String getStareinitaiala()
	{
		return this.start;
	}
	public boolean finala(String stare)
	{
		if(finale.contains(stare))
			return true;
		return false;
	}
}
