package com.ascstudios.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AStar {
	
	public static double lastTime = System.currentTimeMillis();
	private static Comparator<Node> nodeSorter = new Comparator<Node>(){
		
		@Override		
		public int compare(Node n0, Node n1) {
			if(n1.fCost < n0.fCost) {
				return + 1;
			}
			if(n1.fCost > n0.fCost) {
				return -1;
			}
			return 0;
		}
	}; //	Permite comparar 2 objetos e organize no arraylist

	public static boolean clear() {
		if(System.currentTimeMillis() - lastTime >= 1000) {
			return true;
		}
		return false;
	}
	
	//	
	public static List<Node> findPath(World world, Vector2i start, Vector2i end){
		lastTime = System.currentTimeMillis();
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		
		Node current = new Node(start, null, 0, getDistance(start, end));
		openList.add(current);
		while(openList.size() > 0) {
			Collections.sort(openList, nodeSorter);
			current = openList.get(0);
			if(current.tile.equals(end)) {
				//	Chegamos no ponto final, basta retornar o valor
				List<Node> path = new ArrayList<Node>();
				while(current.parent != null) {
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			
			openList.remove(current);
			closedList.add(current);
			
			//	Verificar todas as posições do inimigo e saber se pode ir ou não.
			for(int i = 0; i < 9; i++) {
				if(i == 4) continue;
				int x = current.tile.x; //	Posição atual.
				int y = current.tile.y; //	Posição atual.
				int xi = (i%3) - 1; //	Posição que está verificando.
				int yi = (i/3) - 1; //	Posição que está verificando.
				Tile tile = World.tiles[x + xi + ((y + yi) * World.WIDTH)]; //	Verificando se o tile existe e depois se existe espaço livre.
				if(tile == null) continue;
				if(tile instanceof WallTile) continue;
				if(i == 0) {
				//	Para o jogador andar em todas as direções
					Tile test = World.tiles[x + xi + 1 + ((y + yi) * World.WIDTH)];
					Tile test2 = World.tiles[x + xi + ((y + yi + 1) * World.WIDTH)];
					if(test instanceof WallTile || test2 instanceof WallTile) continue;
				}
				else if(i == 2) {
					Tile test = World.tiles[x + xi - 1 + ((y + yi) * World.WIDTH)];
					Tile test2 = World.tiles[x + xi + ((y + yi + 1) * World.WIDTH)];
					if(test instanceof WallTile || test2 instanceof WallTile) continue;
				}
				else if(i == 6) {
					Tile test = World.tiles[x + xi + ((y + yi - 1) * World.WIDTH)];
					Tile test2 = World.tiles[x + xi + 1 + ((y + yi) * World.WIDTH)];
					if(test instanceof WallTile || test2 instanceof WallTile) continue;
				}
				else if(i == 8) {
					Tile test = World.tiles[x + xi + ((y + yi - 1) * World.WIDTH)];
					Tile test2 = World.tiles[x + xi - 1 + ((y + yi) * World.WIDTH)];
					if(test instanceof WallTile || test2 instanceof WallTile) continue;
				}
				//
				
				Vector2i a = new Vector2i(x + xi, y + yi);
				double gCost = current.gCost + getDistance(current.tile, a); //	Calcular o gCost, tempo para ir de um tile até outro, otimizando.
				double hCost = getDistance(a, end);
				
				Node node = new Node(a, current, gCost, hCost);				
				//	Otimizando o Node node e chamando o metodo para verificar se já existe na lista para n colocar repetido
				if(vecInList(closedList, a) && gCost >= current.gCost) continue;
				//	Se não estiver na lista vamos add.
				if(!vecInList(openList, a)) {
					openList.add(node);
				//	Se a posição gCost for menor a que está na lista, remove e add.
				}else if(gCost < current.gCost) {
					openList.remove(current);
					openList.add(node);
				}
				//
			}			
		}
		closedList.clear();
		return null;
	}
	
	private static boolean vecInList(List<Node> list, Vector2i vector) {
		for(int i = 0; i< list.size(); i++) {
			if(list.get(i).tile.equals(vector)) {
				return true;
			}
		}
		return false;
	}
	
	private static double getDistance(Vector2i tile, Vector2i goal) {
		double dx = tile.x - goal.x;
		double dy = tile.y - goal.y;
		
		return Math.sqrt((dx * dx) + (dy * dy));
	}
}
