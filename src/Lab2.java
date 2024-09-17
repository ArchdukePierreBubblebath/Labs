
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
	
	int x = 0;
	String aa = "";
	while(x<30) {
		int randomIndex = (int)(Math.random() * 20);
		aa += FULL_NAMES[randomIndex];
		System.out.println(aa);
		aa = "";
		String a = Short_names[randomIndex];
		//System.out.println(a);
		String aString = System.console().readLine().toUpperCase();
		if (aString.equals(a)) {
			System.out.println("Correct!");
			}
		else {
			System.out.println("You suck!");
			break;
		}
		x++;
		//STILL NEED TO IMPLEMENT TIMER
	}
}}