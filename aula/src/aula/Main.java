package aula;

public class Main {
	
	public static void main(String[]args) {
		
		/*
		int idade = 29;
		System.out.println("minha idade é " + idade);
		idade = 45;
		System.out.println("minha idade é " + idade);
		idade = 35;
		System.out.println("minha idade é " + idade);	
		*/
		
		//	Tipos de variáveis
		/*
		int numero = 2400000;
		String frase = "Frase utilizando String";
		boolean var = true;
		char var2 = 'a';
		double var3 = 24.9;
		
		var3 = 20.9;
		
		System.out.println(numero);
		System.out.println(frase);
		System.out.println(var);
		System.out.println(var2);
		System.out.println(var3);
		*/
		
		//	Constantes
		/*
		final int vida_maxima = 100;
		
		System.out.println(vida_maxima);
		*/
		
		//	Arrays - Normal e Multi-Dimensional.
		/*
		String[] nome = new String[5];
		nome[0] = "Guilherme.";
		nome[1] = "Andre.";
		nome[2] = "João.";
		
		System.out.println("Array normal com nome: " + nome[1]);
		
		String[][] var2 = new String[5][5];
		var2[0][0] = "Array multi-dimensional.";
		
		System.out.println(var2[0][0]);
		*/
		
		//	Condições
		/*
		int vida = 100;		
		if( vida == 100 ) {
			vida+=100;
			System.out.println("Minha vida é igual a 100!");
		} else {
			System.out.println("Minha vida não é igual a 100!");
		}
		
		String nome = "Andre!";		
		if ( nome == "Andre" ) {
			System.out.println("Nome igual a " + nome);
		} else if (nome == "João" ) {
			
		} else {
			
		}
		
		if (nome != "Joao") {
			System.out.println("O nome é diferente de João, ele se chama " + nome);
		}
		
		boolean var = true;
		if (var)// como se estivesse perguntando se é verdade, somente com uma linha sem o colchete.
			System.out.println("Verdade");
		*/
		
		//	Switch e case
		/*
		int life = 30;
		
		switch(life)
		{
			case 90:
				// Execute alguma ação aqui!
				System.out.println("Quero que meu personagem corra mais rápido!");
			break;
			case 30:
				System.out.println("Minha vida é igual a 30!");
			break;
			default:
				System.out.println("Nenhuma condição bateu! Eu tenho 100 de vida!");
		}
		*/
		
		//	Operações avançadas e operações matemáticas
		/*
		int vida_1 = 100;
		int vida_2 = 100;
		int vida_3 = 200;
		int vida_final = vida_1 + vida_2 + vida_3;
		int vida_final2 = ((vida_1 + vida_2) / 2) * 2;
		
		System.out.println(vida_1 + vida_2 + vida_3);
		System.out.println(vida_final);
		System.out.println(vida_final2);		
		
		String nome_jogador = "Andre";
		int skill = 100;		
		if ((nome_jogador == "Andre" && skill == 100) && skill <= 90) {
			System.out.println("Verdade");
		} else {
			System.out.println("Entramos no else!");
		}
		
		String nome_jogador = "Andre";
		int skill = 100;	
		if ((nome_jogador == "Andre" || nome_jogador == "Joao") && skill >= 100) {
			System.out.println("Comece o jogo!");
		}
		*/
		
		//	Loopings
		
		int contador = 0;		
		while (contador <= 10) {
			System.out.println("Olá, agora o contador está em: " + contador + " usando while!");
			contador++;
		}
		
		for ( int i = 0; i <= 10; i++) {
			System.out.println("Olá, contando até: " + i + " usando for!");
		}
		int cont = 0;
		do {
			System.out.println("Olá, contando até: " + cont + " usando do-while");
			cont+=1;
		} while (cont < 3);
	}	
}