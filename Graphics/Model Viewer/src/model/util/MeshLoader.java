package model.util;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import model.Mesh;

public class MeshLoader {

	public Mesh loadFromFile(String filepath) throws IOException {
		
		Scanner input = new Scanner(new File(filepath));
		Mesh mesh = new Mesh(Color.white);
		
		int points = input.nextInt();
		while (--points >= 0) {
			mesh.addPoint(input.nextDouble(), input.nextDouble(), input.nextDouble());
		}
		
		int faces = input.nextInt();
		while (--faces >= 0) {
			int corners = input.nextInt();
			switch (corners) {
			case 3:
				mesh.addFace(input.nextInt(), input.nextInt(), input.nextInt(), Color.white);
				break;
			case 4:
				mesh.addQuad(input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), Color.white);
				break;
			}
		}
		
		return mesh;
	}
	
}
