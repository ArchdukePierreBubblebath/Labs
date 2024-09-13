
public class HelloWorld {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int aaacount = 0;
		int i = 0;
		char[] nucleotides = {'A', 'T', 'G', 'C'};
		while (i < 1000) {
		String dna = "";
		int x = 0;
		
		while(x < 3) { 
		int randomIndex = (int)(Math.random() * 4);
		dna += nucleotides[randomIndex];
		x +=1;
		
		}
		if (dna.equals("AAA")){
			aaacount +=1;
		}
		System.out.println(dna);
		i+=1;

		}
		
		System.out.println(aaacount);
		int j = 0;
		aaacount = 0;
		while(j<1001) {
		String newDNA = "";
		int y = 0;
		while(y<3) {
		int random = (int)(Math.random() * 100);
		if (random >-1 && random < 12) {
			newDNA += "A";
		
		}
		else if(random > 11 && random < 50) {
			newDNA +="C";
		}
	
		else if(random > 49 && random < 61) {
			newDNA +="T";
			
		}
		else if(random > 60 && random < 101) {
			newDNA +="G";
	
		//System.out.println("meep");
		
		
		}
		y +=1;
		
		
		
	}
		System.out.println(newDNA);
		if (newDNA.equals("AAA")){
			aaacount +=1;
		}
		j += 1;
	}
		System.out.println(aaacount);}}



