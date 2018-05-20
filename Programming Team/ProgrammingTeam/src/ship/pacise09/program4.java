package ship.pacise09;

import java.util.Scanner;
import java.util.regex.Pattern;

public class program4 {
 public static void main(String[] args) {
  Scanner sc = new Scanner(System.in);
  Pattern p1 = Pattern.compile("[aeiouyAEIOUY]");

  int n = sc.nextInt();
  sc.nextLine();
  Pattern[] bad = new Pattern[n];

  for (int i = 0; i < n; i++) {
   String r = ".*" + p1.matcher(sc.nextLine()).replaceAll("[aeiouyAEIOUY]?") + ".*";
   bad[i] = Pattern.compile(r);
  }

  while (sc.hasNext()) {
   String s = sc.nextLine();
   boolean isBad = false;
   for (int i = 0; i < n && !isBad; i++)
    if (bad[i].matcher(s).matches())
     isBad = true;
   System.out.println(s + (isBad ? " denied" : " approved"));
  } //more input
 } //main
}
