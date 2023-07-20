package programa_leitura;

import java.util.Random;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		/*
		String nome, idade, peso, cidade, estado, pais;
		System.out.println("Por favor digite o seu nome:");
		nome = in.nextLine();
		System.out.println("Por favor digite a sua idade:");
		idade = in.nextLine();
		System.out.println("Por favor digite o seu peso:");
		peso = in.nextLine();
		System.out.println("Por favor digite a sua cidade:");
		cidade = in.nextLine();
		if (cidade.equals("Diadema") {
			System.out.println("Somos de Diadema!");
		} else {
			System.out.println("Somos de cidades diferentes!");
		}
		System.out.println("Por favor digite o seu estado:");
		estado = in.nextLine();
		System.out.println("Por favor digite o seu país:");
		pais = in.nextLine();
		//	Gerar resultado final
		System.out.println("-------");
		System.out.println("Aqui está o resultado do teste:");
		System.out.println("Nome: " + nome);
		System.out.println("Idade: " + idade + "anos");
		System.out.println("Peso: " + peso + "kg");
		System.out.println("Cidade: " + cidade);
		System.out.println("Estado: " + estado);
		System.out.println("País: " + pais);
		*/
		Scanner in = new Scanner(System.in);
		String nome;
		Random rand = new Random();
		System.out.println("Seja bem-vindo ao jogo! Insira seu nome");
		nome = in.nextLine();
		System.out.println("Seja muito bem-vindo(a) " + nome);
		System.out.println("Você deseja avançar para qual direção?(w,s,a,d)");
		String comando = in.nextLine();
		if (comando.equals("w")) {
			System.out.println("Você está indo para frente!");
			System.out.println("Um inimigo surgiu, o que deseja fazer? (a = atacar, c = correr)");
			comando = in.nextLine();
			if (comando.equals("a")) {
				if (rand.nextInt(100) < 75) {
					System.out.println("Você ganhou o jogo!");
				} else {
					System.out.println("Você perdeu o jogo!");					
				}
			} else {
				System.out.println("Você correu! O jogo terminou!");
			}
		} else if (comando.equals("s")) {
			System.out.println("Você está entrando em outro mapa...");
			/**/
		}
	}
}
