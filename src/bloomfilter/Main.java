package bloomfilter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

	private static BloomFilter biblioteca = new BloomFilter(15000);
	public static void ficheiroUsers (String nomeFicheiro) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(nomeFicheiro), "UTF-8"));
		String line;
		while (br.ready()) {
			line = br.readLine();
			biblioteca.adduserfromfile(line);
		}
		br.close();
	}
	
	public static void ficheiroLivros (String nomeFicheiro) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(nomeFicheiro), "UTF-8"));
		String line;
		
		while (br.ready()) {
			line = br.readLine();
			biblioteca.addbookslists(line);
		}
		br.close();
	}

	
	public static void main(String[] args) throws IOException {
		ficheiroUsers("pessoas.text");
		
		ficheiroLivros("livros.txt");
		
		biblioteca.generatelists(biblioteca.getLivros().size());
		
		System.out.println("--------------BIBLIOTECA VIRTUAL--------------");
		System.out.println("DOWNLOAD DA BASE DE DADOS CONCLUIDA");
		System.out.printf("%d UTILIZADORES adicionados\n%d LIVROS adicionados\n", biblioteca.getSize(), biblioteca.getLivros().size());
		boolean keepgoing = true;
		
		while (keepgoing) {
			System.out.println("----------------------------------------------");
			System.out.println("--------------------MENU----------------------");
			Menu.printmenu();
			int verificacao=Menu.intInput("=>");
			while (verificacao<0 || verificacao>12) {
				System.out.println("Número inválido. Insira de novo");
				verificacao=Menu.intInput("=>");
			}
			keepgoing = operation(verificacao);
		}
	}
	
	public static boolean operation(int n) {
		
		switch (n) {
		case 1:
			biblioteca.adduser(Menu.stringInput("Nome Completo:"));
			return true;
		case 2:
			biblioteca.contains(Menu.stringInput("Nome do Utilizador: "));
			return true;
		case 3:
			int a=Menu.intInput("Remover utilizador por:\n1) ID\n2) Nome \n=> ");
			if (a == 1 || a == 2) return caso3(a);
			return true;
		case 4:
			int b=Menu.intInput("Encontrar Utilizador por:\n1) ID\n2) Nome \n=> ");
			if (b == 1 || b==2)	return caso4(b);
			return true;
		case 5:
			int d=Menu.intInput("Encontrar Utilizador por:\n1) ID\n2) Nome \n=> ");
			if (d == 1 || d==2) return caso5(d);
			return true;
		case 6:
			int nbooks = Menu.intInput("Quantos livros pretende adicionar?: ");
			int c=Menu.intInput("Encontrar Utilizador por:\n1) ID\n2) Nome \n=> ");
			if (c == 1 || c == 2) return caso6(c, nbooks);
			return true;
		case 7:
			biblioteca.isMember(Menu.stringInput("Nome do livro: "));
			return true;
		case 8:
			biblioteca.listadeutilizadores(0);
			return true;
		case 9:
			int e=Menu.intInput("Encontrar Utilizador por:\n1) ID\n2) Nome \n=> ");
			if (e == 1 || e == 2) return caso9(e);
			return true;
		case 10:
			System.out.println("Similaridade entre utilizadores");
			int f =Menu.intInput("Encontrar Utilizador por:\n1) ID\n2) Nome \n=> ");
			if (f == 1 || f == 2) return caso10(f);
			else {
				System.out.println("Opção Inválida. Voltando ao menu principal!");
				return true;
			}
		case 11:
			System.out.println("Utilizadores Semelhantes:");
			int g =Menu.intInput("Encontrar Utilizador por:\n1) ID\n2) Nome \n=> ");
			return caso11(g);
			
		case 12:
			double percentageDouble=biblioteca.rateFalsePositive();
			System.out.printf("Probabilidade de falsos positivos: %.8f \n", percentageDouble);
			System.out.printf("%d livro(s) existente(s) na Biblioteca\n%d Utilizadores atualmente registados na Biblioteca\n", biblioteca.getLivros().size(), biblioteca.getListautilizadores().size());
			return true;
		case 0:
			return false;
		default:
			return false;
				
		}
	}

	private static boolean caso3(int a) {
		if (a == 1) {
			int user = Menu.intInput("ID :");
			if (biblioteca.validateID(user) == 1) {
				biblioteca.removeuser(user);
				return true;
			}
			else {
				System.out.println("ID invalido\nVoltando ao menu principal");
				return true;
			}
		}
		else{		
			int result = biblioteca.getUserID(Menu.stringInput("Nome do Utilizador: "));
			if (result == -1) {
				return true;
			}
			else {
				if (biblioteca.validateID(result) == 1) {
					biblioteca.removeuser(result);
					return true;
				}
				else {
					System.out.println("ID inválido\nVoltando ao menu principal");
					return true;
				}
			}
		}
	}
	
	private static boolean caso4(int b) {
	
		if (b==1) {
			System.out.println("Pronto para Adicionar livro");
			int user = Menu.intInput("ID :");
			if (biblioteca.validateID(user) == 1){
				biblioteca.addbook(user, Menu.stringInput("Nome do Livro: "));
				return true;
			}
			else {
				System.out.println("ID invalido\nVoltando ao menu principal");
				return true;
			}
		}
		else {		
			int result = biblioteca.getUserID(Menu.stringInput("Nome do Utilizador: "));
			if (result == -1) {
				return true;
			}
			else {
				if (biblioteca.validateID(result) == 1) {
					biblioteca.addbook(result, Menu.stringInput("Nome do Livro: "));
					return true;
				}
				else {
					System.out.println("ID inválido\nVoltando ao menu principal");
					return true;
				}
			}
		}
	}
	
	private static boolean caso5(int d) {
		
		if (d==1) {
			int user = Menu.intInput("ID :");
			if (biblioteca.validateID(user) == 1) {
				biblioteca.removebook(user, Menu.stringInput("Nome do Livro: "));
				return true;
			}
			else {
				System.out.println("ID inválido\nVoltando ao menu principal");
				return true;
			}
		}
		else {		
			int result = biblioteca.getUserID(Menu.stringInput("Nome do Utilizador: "));
			if (result == -1) {
				return true;
			}
			else {
				if (biblioteca.validateID(result) == 1) {
					biblioteca.removebook(result, Menu.stringInput("Nome do Livro: "));
					return true;
				}
				else {
					System.out.println("ID inválido\nVoltando ao menu principal");
					return true;
				}
			}
		}
	}
	
	private static boolean caso6(int c, int nbooks) {
		int id=0;
		int result2=0;
		if (c == 1) {
			id = Menu.intInput("ID: ");
			if(biblioteca.validateID(id) == 0) {
				System.out.println("ID inválido\nVoltando ao menu principal");
				return true;
			}
		}
		else {
			result2 = biblioteca.getUserID(Menu.stringInput("Nome do Utilizador: "));
			if(biblioteca.validateID(result2) == 0) {
				System.out.println("ID inválido\nVoltando ao menu principal");
				return true;
			}
		}
		for (int i=0; i < nbooks;i++) {
			if (c == 1) {
				System.out.printf("Pronto para Adicionar livro numero %d\n", i+1);
				biblioteca.addbook(id, Menu.stringInput("Nome do Livro: "));
			}
			else if(c == 2){
				System.out.printf("Pronto para Adicionar livro numero %d\n", i+1);
				if (result2 == -1) {
					return true;
				}
				else {
					biblioteca.addbook(result2, Menu.stringInput("Nome do Livro: "));
				}
			}
		}
		return true;
	}

	private static boolean caso9(int e) {
		if(e == 1) {
			int user = Menu.intInput("ID :");
			if (biblioteca.validateID(user) == 1) {
				biblioteca.listadelivros(user);
				return true;
			}
			else {
				System.out.println("ID inválido\nVoltando ao menu principal");
				return true;
			}
		}
		else{		
			int result = biblioteca.getUserID(Menu.stringInput("Nome do Utilizador: "));
			if (result == -1) {
				return true;
			}
			else {
				if (biblioteca.validateID(result) == 1) {
					biblioteca.listadelivros(result);
					return true;
				}
				else {
					System.out.println("ID inválido\nVoltando ao menu principal");
					return true;
				}
			}	
		}
	}

	private static boolean caso10(int f) {
		int result1=0;
		int result2=0;
		if (f == 1) {
			result1 = Menu.intInput("ID do Utilizador 1: ");
			if (biblioteca.validateID(result1) == 0) {
				System.out.println("ID inválido");
				return true;
			}
			result2 = Menu.intInput("ID do Utilizador 2: ");
			if (biblioteca.validateID(result2) == 0) {
				System.out.println("ID inválido");
				return true;
			}
		}
		else{
			result1 = biblioteca.getUserID(Menu.stringInput("Nome do Utilizador 1: "));
			if (biblioteca.validateID(result2) == 0) {
				System.out.println("ID inválido");
				return true;
			}
			result2 = biblioteca.getUserID(Menu.stringInput("Nome do Utilizador 2: "));
			if (biblioteca.validateID(result2) == 0) {
				System.out.println("ID inválido");
				return true;
			}
		}
		System.out.printf("Similaridade: %.2f\n", MinHash.jaccard(biblioteca.getlistalivros(result1), biblioteca.getlistalivros(result2)));
		return true;
	}
	
	private static boolean caso11(int g) {
		int k=0;
		int contador=0;
		if (g == 1) {
			k = Menu.intInput("ID: ");
		}
		else if (g == 2) {
			k = biblioteca.getUserID(Menu.stringInput("Nome do Utilizador: "));
		}
		
		if(biblioteca.validateID(k) != 1) {
			System.out.println("ID inválido");
			return true;
		}
		if(biblioteca.getListaLivrosSize(k)==0) {
			System.out.println("O Utilizador não tem nehum livro associado");
			return true;
		}

		ArrayList<Long> temp = new ArrayList<Long>();
		temp = MinHash.suggested(k, biblioteca.getUser().get((long) k), biblioteca.getUser(), biblioteca.getListautilizadores().size());

		for (Long i: temp) {
			System.out.printf("%dº\tID:  %-6d - %s\n", contador + 1, i , biblioteca.getCodigo().get(i));
			contador++;
		}
		return true;
	}
}
