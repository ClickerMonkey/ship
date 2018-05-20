package ship.pacise09;

import java.util.Scanner;

public class program5 {
 static boolean map[][] = new boolean[20][30];
 static int y, x, dir;
 
 public static void main(String[] args) {
  Scanner sc = new Scanner(System.in);
  y = sc.nextInt();
  x = sc.nextInt();
  dir = sc.nextInt();
  
  if (y < 0 || x < 0 || y >= 20 || x >= 30) {
   printMap();
   return;
  }
  
  while (readMove(sc));
  printMap();
 }

 static void printMap() {
  for (int yy = 0; yy < 20; yy++) {
   for (int xx = 0; xx < 30; xx++)
    System.out.print(map[yy][xx] ? "*" : " ");
   System.out.println();
  } //yy
 } //printMap
 
 static boolean readMove(Scanner sc) {
  int m = sc.nextInt();
  int d = sc.nextInt();
  for (int i = 0; i < m; i++)
   if (!move() || map[y][x]) {
    dir = d;
    return false;
   }
  dir = d;
  return true;
 } //readMove

 static boolean move() {
  map[y][x] = true;
  switch (dir) {
   case 0:
    return (--y >= 0);
   case 1:
    return (++x < 30);
   case 2:
    return (--x >= 0);
   case 3:
    return (++y < 20);
  } //switch
  throw new ArrayIndexOutOfBoundsException();
 } //move
}
