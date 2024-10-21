import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FastaSequence {
	private String header;
	private String seq;
	
	public FastaSequence(String header,String seq) {
		this.header = header;
		this.seq = seq;
	}
	
	public static List<FastaSequence> readFastaFile(String filepath) throws Exception
	{List<FastaSequence> fastaList = new ArrayList<>();
    BufferedReader reader = new BufferedReader(new FileReader(filepath));
    String line;
    StringBuilder str = new StringBuilder();
    String header = null;
    String seq = "";
    while ((line = reader.readLine()) != null) {
        //System.out.println(line);
        if(line.startsWith(">")) {
        	//System.out.println("Meep");
        	if(header == null) {
        		header = line;
        		//System.out.println(line);
        		
        	}
        	else if(header != null) {
        		//System.out.println(header);
        		fastaList.add(new FastaSequence(header, seq));
        		//System.out.println(seq);
        		header = line;
        		seq = "";
        	}
 
        	}
        else {
        	seq +=line;
        	//System.out.println(seq);
        	//break;
        }
      
        
    }
    //System.out.println(header);
    //System.out.println(seq);
    fastaList.add(new FastaSequence(header, seq));
	return fastaList;}
	
	public String getHeader()
	{return header;}

	// returns the Dna sequence of this FastaSequence
	public String getSequence() 
	{return seq;}
	
	// returns the number of G’s and C’s divided by the length of this sequence
	public float getGCRatio()
	{
		int gc = 0;
		for(int i = 0;i<seq.length();i++) {
			if(seq.charAt(i) == 'G' || seq.charAt(i) == 'C') {
				gc++;
			}
		}
		return (float) gc / seq.length();}

	
	public static void main(String[] args) throws Exception
	{
	     List<FastaSequence> fastaList = 
		FastaSequence.readFastaFile(
			"/Users/ansh/git/Labs/src/test.fasta");

	     for( FastaSequence fs : fastaList)
	     {
	         System.out.println(fs.getHeader());
	         System.out.println(fs.getSequence());
	         System.out.println(fs.getGCRatio());
	      }

	     //File myFile = new File("/Users/ansh/git/Labs/src/test.fasta");

	     //writeTableSummary( fastaList,  myFile);
	}



}
