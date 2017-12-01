package bloomfilter;

import java.util.Scanner;

public class Menu {
	private static Scanner kb = new Scanner(System.in);
	private static int number = 0;
	
	private static String menu = "[1] - Adicionar novo Utilizador\n"
			+ "[2] - Verificar se Utilizador existe\n"
			+ "[3] - Remover Utilizador\n"
			+ "[4] - Associar Livro a um Utilizador\n"
			+ "[5] - Remover Livro a um Utilizador\n"
			+ "[6] - Associar múltiplos Livros a um Utilizador\n"
			+ "[7] - Adicionar/Verificar existência do livro na Base de dados\n"
			+ "[8] - Imprimir lista de Utilizadores\n"
			+ "[9] - Imprimir lista de livros de um Utilizador\n"
			+ "[10] - Similaridade entre 2 Utilizadores\n"
			+ "[11] - Utilizadores semelhantes\n"
			+ "[12] - Dados informativos da Biblioteca\n"
			+ "[0] - sair\n";
	
	public static int intInput(String inputmessage) {
		System.out.print(inputmessage + " ");
		number = kb.nextInt();
		kb.nextLine();
		return number;
	}
	
	public static String stringInput(String inputmessage) {
		System.out.print(inputmessage + " ");
		return kb.nextLine();
	}
	
	public static void printmenu() {
		System.out.print(menu);
	}
}
