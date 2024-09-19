
public class Lab2 {
	public static void main(String[]args) {

	String[] Short_names = 
		{ "A","R", "N", "D", "C", "Q", "E", 
		"G",  "H", "I", "L", "K", "M", "F", 
		"P", "S", "T", "W", "Y", "V" };
	
	String[] FULL_NAMES = 
		{
		"alanine","arginine", "asparagine", 
		"aspartic acid", "cysteine",
		"glutamine",  "glutamic acid",
		"glycine" ,"histidine","isoleucine",
		"leucine",  "lysine", "methionine", 
		"phenylalanine", "proline", 
		"serine","threonine","tryptophan", 
		"tyrosine", "valine"};
	
	long start = System.currentTimeMillis();
    long end = 30000;
	
	int x = 0;
	String aa = "";
	while(x<30) {
		long currentTime = System.currentTimeMillis();
        if (currentTime - start >= end) {
            System.out.println("Time's up!");
            break;}
		int randomIndex = (int)(Math.random() * 20);
		aa += FULL_NAMES[randomIndex];
		System.out.println(aa);
		aa = "";
		String a = Short_names[randomIndex];
		String aString = System.console().readLine().toUpperCase();
		if (aString.equals(a)) {
			System.out.println("Correct!");
			}
		else {
			System.out.println("You bring shame upon your entire bloodline.");
			break;
		}
		x++;
	}
}}