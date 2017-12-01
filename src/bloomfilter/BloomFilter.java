package bloomfilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class BloomFilter {
	
	private static Random r = new Random();
	private int[] setUsers;	//List BloomFilter
	private ArrayList<String> listautilizadores = new ArrayList<String>(); //ArrayList of Users (WITHOUT False Positives)
	private ArrayList<String> livros = new ArrayList<String>();	//ArrayList of Users
	private HashMap<Long, String> codigo = new HashMap<Long, String>(); //HashMap ID:Users Name
	private HashMap<Long, ArrayList<String> > user = new HashMap<Long, ArrayList<String> >(); //HashMap ID:Associated Users' Book List
    private int setSize, size;
    
 
    // Constructor
    public BloomFilter(int capacity)
    {
        setSize = capacity;
        setUsers = new int[setSize];
        size = 0;
    }

    //----------------------------ADD---------------------------------------------------------
    public void addtoMaps(String n)
    {
    	long o = HashFunction.hasFunction1(n, setSize);
    	ArrayList<String> vazio = new ArrayList<String>();
    	listautilizadores.add(n);
    	codigo.put((long)o, n);
    	user.put((long)o, vazio);
    }
    
    //Adds to bloom filter
    public void add(String obj)
    {
    	long o = HashFunction.hasFunction1(obj, setSize);
    	long p = HashFunction.hasFunction2(obj, setSize);
    	long l = HashFunction.hasFunction3(obj, setSize);
    	long h = HashFunction.hasFunction4(obj, setSize);
    	setUsers[(int)o]=1;
    	setUsers[(int)p]=1;
    	setUsers[(int)l]=1;
    	setUsers[(int)h]=1;
        size++;
    }
    
    
    //Adds without printing to an ArrayList and HashMaps
  	public void adduserfromfile(String n)
  	{
  		addtoMaps(n);
  		add(n);
  	}
  	
  	//Adds to an ArrayList and HashMaps
  	public void adduser(String n)
  	{
  		long o = HashFunction.hasFunction1(n, setSize);
  		if (contains2(n)) {
  			System.out.println("O Utilizador existe");
  			return;
  		}
  		addtoMaps(n);
  		add(n);
  		System.out.printf("Utilizador %s com o id %d foi adiconado com sucesso\n", n, o);
  	}
    
	//adds a book to an User
	public void addbook(int u, String nbook)
	{
		ArrayList<String> temp2 = new ArrayList<>();
		//Checks if book is already associated with the user
		for (String xv : user.get((long) u)) {
			if (nbook.equalsIgnoreCase(xv)) {
				System.out.println("O livro já está adicionado ao utilizador\n");
				return;
			}
		}
		temp2 = user.get((long) u);
		temp2.add(nbook);
		//Checks if book is in the Data Base and if not adds it
		int exists = 0;
		for (String l : livros) {
			if(l.equalsIgnoreCase(nbook)) {
				exists = 1;
			}
		}
		if(exists == 0) {
			livros.add(nbook);
			System.out.printf("O livro %s foi adicionado com sucesso à Biblioteca\n", nbook);
		}
		user.replace((long) u,temp2);
		System.out.printf("O livro %s foi adiconado ao user %s com sucesso\n", nbook, codigo.get((long) u));
	}
	
	//Adds Book to a List of all books
	public void addbookslists (String livro) {
		livros.add(livro);
	}
	
	//-------------------------REMOVE---------------------------------------------------------
  	
	//Removes from Bloom
    public void remove()
    {
    	makeEmpty();
    	for(String lo: listautilizadores) {
    		add(lo);
    	}
    }
    
    //Removes User from ArrayList and from the HashMaps
  	public void removeuser(int b)
  	{
  		for (long id : codigo.keySet()) {
  			if (id == (long) b) {
  				String olduser = codigo.get(id);
  				ArrayList<String> temp = new ArrayList<>();
  				for (String utilizadores : listautilizadores) {
  					if (!utilizadores.equalsIgnoreCase(olduser)) {
  						temp.add(utilizadores);
  					}
  				}listautilizadores = temp;
  				codigo.remove(id);
  				user.remove(id);
  				System.out.printf("Utilizador %s com o código %d foi removido com sucesso\n", olduser, id);
  				break;
  			}
  		}
  		remove();
  	}
  	
  	//Removes a Book from the User
  	public void removebook(int c, String rname)
  	{
  		for (long users : codigo.keySet()) {
  			if (users == (long) c) {
  				ArrayList<String> lista = user.get(users);
  				for (String livros : lista) {
  					//Checks if book is associated with the user
  					if (livros.equalsIgnoreCase(rname)) {
  						lista.remove(livros);
  						user.replace(users, lista);
  						System.out.printf("O livro %s foi removido com sucesso no utilizador %s com o ID: %d\n", livros.toLowerCase(), codigo.get(users), users);
  						return;
  					}
  					else {
  						System.out.printf("O livro %s não se encontra atualmente associado ao Utilizador\n Voltando ao Menu\n", rname);
  						return;
  					}
  				}
  			}
  		}
  		System.out.println("Utilizador não encontrado");
  	}
    
  	//---------------------------------------Bloom Tools-------------------------------------------------
  	
  	//Makes Bloom Empty (Used only when a User is removed)
    public void makeEmpty()
    {
        setUsers = new int[setSize];
        size = 0;
    }
    
	//----------------------------------CONTAINS--------------------------------------------------------
    
    //Check if User doesn't belong or might belong to the list of Users(Bloom) and returns to or false
    public boolean contains2(String obj) 
    {
    	long o = HashFunction.hasFunction1(obj, setSize);
    	long p = HashFunction.hasFunction2(obj, setSize);
    	long l = HashFunction.hasFunction3(obj, setSize);
    	long h = HashFunction.hasFunction4(obj, setSize);
  		return (setUsers[(int)o]==1 && setUsers[(int)p]==1 && setUsers[(int)l]==1 && setUsers[(int)h]==1);
    }
    
    //Check if User doesn't belong or might belong to the list of Users(Bloom) and prints it
    public void contains(String obj) 
    {
    	long o = HashFunction.hasFunction1(obj, setSize);
    	long p = HashFunction.hasFunction2(obj, setSize);
    	long l = HashFunction.hasFunction3(obj, setSize);
    	long h = HashFunction.hasFunction4(obj, setSize);
  		if(setUsers[(int)o]==1 && setUsers[(int)p]==1 && setUsers[(int)l]==1 && setUsers[(int)h]==1) {
  			System.out.printf("O Utilizador %s pode existir\n", obj);
  			return;
  		}
        System.out.printf("O Utilizador %s não existe\n", obj);
    }
    
    //Checks if Book belongs to the list of Books and if not it asks the user if it wants to add it
  	public void isMember(String nome) {
  		int h = 1;
  		for (String l : livros) {
  			if (l.equalsIgnoreCase(nome)) {
  				h=0;
  				System.out.printf("O livro %s existe na Biblioteca\n", nome);
  				return;
  			}
  		}
  		if (h==1) {
  			String y_n = Menu.stringInput("O livro "+nome+" não existe na Biblioteca\nDeseja adicionÃ¡-lo?(Y/N): ");
  			if (y_n.equalsIgnoreCase("y")) {
  				livros.add(nome);
  				System.out.printf("O livro %s foi adicionado\n", nome);
  			}
  			else if (y_n.equalsIgnoreCase("n")) {
  				System.out.printf("Voltanto ao Menu Principal\n");
  			}
  			else {
  				System.out.printf("Opção não válida\n");
  				return;
  			}
  		}
  	}
    
	//------------------------------------MAIN TOOLS------------------------------------
	
	//Get an User ID by the full or only a part of the users name
	public int getUserID(String n)
	{
		int sresult=0;
		for (long username : codigo.keySet()) {
			if (codigo.get(username).toLowerCase().contains(n.toLowerCase())) {
				System.out.printf("%d: %s com o ID %d\n", sresult+1, codigo.get(username), username);
				sresult++;
			}
		}
		System.out.printf("%d resultado(s) encontrado(s)\n", sresult);
		int number = Menu.intInput("Insira o ID do utilizador pretendido (-1 para voltar ao Menu): ");
		return number;
	}
	
	//Check if ID exists
	public int validateID(int v)
	{
		int accert = 0;
		for (long username : codigo.keySet()) {
			if (username == (long) v) {
				accert=1;
				break;
			}
		}
		return accert;
	}
	
	//Generates a random number (between 1 and 10) of Books (chosen randomly) to add to an User 
	public void generatelists (int nlivros) {
		for (long utilizadores : user.keySet()) {
			int nbookstoadd = r.nextInt(9)+1;
			ArrayList<String> temp = new ArrayList<String>();
			for(int i=0; i<nbookstoadd; i++) {
				int book = r.nextInt(nlivros-1)+1;
				String l = livros.get(book);
				int fg = 0;
				//Confirms that the book is not already in the Users List of associated Books
				for (String xv : user.get(utilizadores)) {
					if (xv.equalsIgnoreCase(l)) {
						fg=1;
					}
				}
				if(fg==1) {
					i--;
					continue;
				}
				temp.add(l);
				user.replace(utilizadores, temp);
			}
			
		}
		
	}
	
	//-----------------------------------------PRINTS-----------------------------------------
	
	//Prints lists of Users
	public void listadeutilizadores(int s) {
		int o = s;
		int m = 0;
		if (o == 0) {
			System.out.println("----------------------------------------------");
			System.out.println("|                UTILIZADORES                |");
			System.out.printf("| Nº Total de Utilizadores:%4d              |\n", listautilizadores.size());
			System.out.println("|                                            |");
			System.out.println("| ID    : NOME                               |");
		}
		for (long ids : codigo.keySet()) {
			if (o == 0 && m <=25) {
				System.out.printf("| %-6d: %-25s          |\n", ids, codigo.get(ids));
			}
			else if (o == 1 && m > 25) {
				System.out.printf("| %-6d: %-25s          |\n", ids, codigo.get(ids));
			}
			m++;
		}
		if (o==0) {
			String n = Menu.stringInput("Deseja ver a lista a completa de Utilizadores? (Y/N): ");
			if (n.equalsIgnoreCase("y")) {
				listadeutilizadores(1);
			}
		}
	}

	//Prints List of Books associated with an User
	public void listadelivros(int x) {
		System.out.println("----------------------------------------------");
		System.out.printf("|      %-32s      |\n", codigo.get((long) x));
		System.out.printf("| Nº Total de Livros: %2d                     |\n", user.get((long) x).size());
		System.out.println("|                                            |");
		for (String li : user.get((long) x)) {
			System.out.printf("| *%-35s       |\n", li);
		}
	}
	
	//Prints False Positive Rate
	public double rateFalsePositive() {
		double t = setSize;
		double d = 4*getSize(); //number of hashes*number of elements added
		double density0s= Math.exp(-d/t);
		double density1s= 1-density0s;
		double FalsePositive= Math.pow(density1s,4);
		return FalsePositive;
	}
	
	//------------------------------------------Gets----------------------------------
	
	//Gets List of Associated Books of an User
	public ArrayList<String> getlistalivros(int x) {
		return user.get((long) x);
	}
	
	//Gets Size of Bloom Filter
    public int getSize()
    {
        return size;
    }
	
    //Gets HashMap with key:ID and value:Name of the User
	public HashMap<Long, String> getCodigo() {
		return codigo;
	}

	//Gets HashMap with key:ID and value:ArraysList of the associated Books
	public HashMap<Long, ArrayList<String>> getUser() {
		return user;
	}

	//Gets Size of an User List of Associated Books
	public int getListaLivrosSize(int x) {
		return user.get((long) x).size();
	}

	//Gets List of Users
	public ArrayList<String> getListautilizadores() {
		return listautilizadores;
	}

	//Gets List of Books
	public ArrayList<String> getLivros() {
		return livros;
	}
}
