package ship.pacise09;

import java.util.Scanner;
import java.util.regex.Pattern;

public class program2 {
 public static void main(String[] args) {
  Scanner sc = new Scanner(System.in);
  String s = sc.nextLine();
  Pattern p1 = Pattern.compile("\\bah+\\b");
  Pattern p2 = Pattern.compile("\\bum+\\b");
  Pattern p3 = Pattern.compile("\\bokay\\b");
  Pattern p4 = Pattern.compile("\\s+");
  
  s = p1.matcher(s).replaceAll(" ");
  s = p2.matcher(s).replaceAll(" ");
  s = p3.matcher(s).replaceAll(" ");
  s = p4.matcher(s).replaceAll(" ");
  
  while (s.length() > 64) {
   int i;
   for (i = 64; i >= 0; i--) {
    if (s.charAt(i) == ' ') {
     System.out.println(s.substring(0,i));
     s = s.substring(i+1);
     break;
    } //is space?
   } //find space
  } //while long
  System.out.println(s);
//   for (int j = 0; j < 66; j++) {
//    System.out.print(j % 10);
//   }
//   System.out.println();
 } //main
}
