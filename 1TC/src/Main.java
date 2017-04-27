import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Main {
	Automat afd;
	String stare;
	BufferedReader br;
	PrintWriter pw;
	ArrayList<String> types;
	ArrayList<String> values;
	ArrayList<Token> tokens;
	int i;
	int poz;
	public Main() throws IOException{
		afd = new Automat();
		stare = afd.getStareinitaiala();
		br = new BufferedReader(new FileReader("test_c2.txt"));
		pw = new PrintWriter("tokens.txt", "UTF-8");
		types = new ArrayList<>();
		values = new ArrayList<>();
		tokens = new ArrayList<>();
		i=0;
		poz=0;
	}
	public static void main(String[] args) throws IOException {
		Main m = new Main();
		Token t = m.gettoken();
		if(t!=null)
			m.tokens.add(t);
		while(m.i!=-1)
		{
			t = m.gettoken();
			if(t!=null)
				m.tokens.add(t);
			if(m.types.indexOf("Error")!=-1)
				break;
		}
		for(int j = 0; j<m.tokens.size();j++){
				m.pw.println("Token");
				m.pw.println("|-- tip => "+m.types.get(m.tokens.get(j).gettip()));
				m.pw.println("+-- valoare => "+m.values.get(m.tokens.get(j).getval()));
				m.pw.println("-----------------------------");
		}
		m.br.close();
		m.pw.close();
	}
	public Token gettoken() throws IOException{
		Token x = new Token();
		stare = afd.getStareinitaiala();
		String value = "";
		boolean go = true;
		if(poz==0)
		{
			i=br.read();
			if(i==-1)
			{
				types.add("Succes");
				values.add("Succes");
				x.settip(types.indexOf("Succes"));
				x.setval(values.indexOf("Succes"));
				return x;
			}
		}
		do {
			poz++;
			go = true;
			if((afd.getTranzitie(stare,(char)i)!=null))
			{
				stare = afd.getTranzitie(stare,(char)i);
				value = value + (char)i;
			}
			else
			{
				if(afd.finala(stare))
				{
					if(!((stare.equals("ignora")||stare.equals("precompilare"))))
					{
						if(types.contains(stare)==false)
							types.add(stare);
						if(values.contains(value)==false)
							values.add(value);
						x.settip(types.indexOf(stare));
						x.setval(values.indexOf(value));
						return x;
					}
					else
					{
						go = false;
						stare = afd.getStareinitaiala();
						value="";
					}
				}
				else
				{
					types.add("Error");
					if(values.contains(value)==false)
						values.add(value);
					x.settip(types.indexOf("Error"));
					x.setval(values.indexOf(value));
					return x;
				}
			}
			if(go)
			{
				i=br.read();
				if(i==-1)
					break;
			}
		} while(i!=-1);
		System.out.println(stare);
		if(afd.finala(stare))
		{
			if(!((stare.equals("ignora")||stare.equals("precompilare"))))
			{
				if(types.contains(stare)==false)
					types.add(stare);
				if(values.contains(value)==false)
					values.add(value);
				x.settip(types.indexOf(stare));
				x.setval(values.indexOf(value));
			}
			else
			{
				types.add("Succes");
				values.add("Succes");
				x.settip(types.indexOf("Succes"));
				x.setval(values.indexOf("Succes"));
			}
		}
		else
		{
			types.add("Error");
			if(values.contains(value)==false)
				values.add(value);
			x.settip(types.indexOf("Error"));
			x.setval(values.indexOf(value));
		}
		return x;
	}
/*	public Token gettoken() throws IOException{
		Token x = new Token();
		stare = afd.getStareinitaiala();
		String value = "";
		if(poz!=0)
		{
			if(i!=-1)
				if(afd.getTranzitie(stare, (char)i)==null)
				{
					if(!afd.finala(stare))
					{
						types.add("Error");
	//					values.add(value);
						if((values.indexOf(value))!=-1)
							x.setval(values.indexOf(value));
						else
						{
							values.add(value);
							x.setval(values.indexOf(value));
						}
						x.settip(types.indexOf("Error"));
	//					x.setval(values.indexOf(value));
						return x;
					}
					else
					{
						if(!((stare.equals("ignora")||stare.equals("precompilare"))))
						{
							if(types.indexOf(stare)!=-1)
							types.add(stare);
							x.settip(types.indexOf(stare));
							//					values.add(value);
							if((values.indexOf(value))!=-1)
								x.setval(values.indexOf(value));
							else
							{
								values.add(value);
								x.setval(values.indexOf(value));
							}
	//						x.settip(types.indexOf("Error"));
		//					x.setval(values.indexOf(value));
							return x;
						}
					}
				}
				else
				{
					stare = afd.getTranzitie(stare, (char)i);
					value = value+(char)i;
				}
			else
			{
				if(afd.finala(stare))
				{
					if(!((stare.equals("ignora")||stare.equals("precompilare"))))
					{
						if(types.indexOf(stare)!=-1)
							x.settip(types.indexOf(stare));
						else
						{
							types.add(stare);
							x.settip(types.indexOf(stare));
						}
						if((values.indexOf(value))!=-1)
							x.setval(values.indexOf(value));
						else
						{
							values.add(value);
							x.setval(values.indexOf(value));
						}
						return x;
//						System.out.println(poz+" : "+value+" "+ stare);
					}
				}
				else
				{
					types.add("Error");
					if((values.indexOf(value))!=-1)
						x.setval(values.indexOf(value));
					else
					{
						values.add(value);
						x.setval(values.indexOf(value));
					}
					//values.add(value);
					x.settip(types.indexOf("Error"));
					//x.setval(values.indexOf(value));
					return x;
				}
			}
		}
		while((i=br.read())!=-1)
		{
			poz++;
			if((afd.getTranzitie(stare,(char)i)!=null))
			{
				//System.out.println(poz);
				stare = afd.getTranzitie(stare,(char)i);
				value = value + (char)i;
			}
			else
			{
				if(afd.finala(stare))
				{
					if(!((stare.equals("ignora")||stare.equals("precompilare"))))
					{
						if(types.indexOf(stare)!=-1)
							x.settip(types.indexOf(stare));
						else
						{
							types.add(stare);
							x.settip(types.indexOf(stare));
						}
						if((values.indexOf(value))!=-1)
							x.setval(values.indexOf(value));
						else
						{
							values.add(value);
							x.setval(values.indexOf(value));
						}
//						return x;
//						System.out.println(poz+" : "+value+" "+ stare);
					}
				}
				else
				{
					types.add("Error");
					if((values.indexOf(value))!=-1)
						x.setval(values.indexOf(value));
					else
					{
						values.add(value);
						x.setval(values.indexOf(value));
					}
					//values.add(value);
					x.settip(types.indexOf("Error"));
					//x.setval(values.indexOf(value));
//					return x;
				}
//				System.out.println(poz+" "+stare+" "+value);
				if(!((stare.equals("ignora")||stare.equals("precompilare"))))
					{
//					System.out.println(poz+" "+stare+" "+value);
					return x;
					}
				else
				{
					stare = afd.getStareinitaiala();
					value = "";
					if(i!=-1)
						if(afd.getTranzitie(stare, (char)i)==null)
						{
							if(!afd.finala(stare))
							{
								types.add("Error");
			//					values.add(value);
								if((values.indexOf(value))!=-1)
									x.setval(values.indexOf(value));
								else
								{
									values.add(value);
									x.setval(values.indexOf(value));
								}
								x.settip(types.indexOf("Error"));
			//					x.setval(values.indexOf(value));
								return x;
							}
							else
							{
								if(!((stare.equals("ignora")||stare.equals("precompilare"))))
								{
									if(types.indexOf(stare)!=-1)
									types.add(stare);
									x.settip(types.indexOf(stare));
									//					values.add(value);
									if((values.indexOf(value))!=-1)
										x.setval(values.indexOf(value));
									else
									{
										values.add(value);
										x.setval(values.indexOf(value));
									}
			//						x.settip(types.indexOf("Error"));
				//					x.setval(values.indexOf(value));
									return x;
								}
							}
						}
						else
						{
							stare = afd.getTranzitie(stare, (char)i);
							value = value+(char)i;
						}
					else
					{
						if(afd.finala(stare))
						{
							if(!((stare.equals("ignora")||stare.equals("precompilare"))))
							{
								if(types.indexOf(stare)!=-1)
									x.settip(types.indexOf(stare));
								else
								{
									types.add(stare);
									x.settip(types.indexOf(stare));
								}
								if((values.indexOf(value))!=-1)
									x.setval(values.indexOf(value));
								else
								{
									values.add(value);
									x.setval(values.indexOf(value));
								}
								return x;
//								System.out.println(poz+" : "+value+" "+ stare);
							}
						}
						else
						{
							types.add("Error");
							if((values.indexOf(value))!=-1)
								x.setval(values.indexOf(value));
							else
							{
								values.add(value);
								x.setval(values.indexOf(value));
							}
							//values.add(value);
							x.settip(types.indexOf("Error"));
							//x.setval(values.indexOf(value));
							return x;
						}
					}
				}
			}
		}
		if(i==-1)
		{
			if(afd.finala(stare))
			{
				if(!((stare.equals("ignora")||stare.equals("precompilare"))))
				{
					if(types.indexOf(stare)!=-1)
						x.settip(types.indexOf(stare));
					else
					{
						types.add(stare);
						x.settip(types.indexOf(stare));
					}
					if((values.indexOf(value))!=-1)
						x.setval(values.indexOf(value));
					else
					{
						values.add(value);
						x.setval(values.indexOf(value));
					}
//					return x;
//					System.out.println(poz+" : "+value+" "+ stare);
				}
			}
			else
			{
				types.add("Error");
				if((values.indexOf(value))!=-1)
					x.setval(values.indexOf(value));
				else
				{
					values.add(value);
					x.setval(values.indexOf(value));
				}
				//values.add(value);
				x.settip(types.indexOf("Error"));
				//x.setval(values.indexOf(value));
				return x;
			}
		}
		return null;
	}
*/
}
