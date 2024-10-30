import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
    String header = null;
    String seq = "";
    while ((line = reader.readLine()) != null) {
        //System.out.println(line);
        if(line.startsWith(">")) {
        	//System.out.println("Meep");
        	if(header == null) {
        		header = line.substring(2);
        		//System.out.println(line);
        		
        	}
        	else if(header != null) {
        		//System.out.println(header);
        		fastaList.add(new FastaSequence(header, seq));
        		//System.out.println(seq);
        		header = line.substring(2);
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
    reader.close();
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
	public int numA()
	{
		int a = 0;
		for(int i = 0;i<seq.length();i++) {
			if(seq.charAt(i) == 'A'){
				a++;
			}
		}
		return a;}
	public int numG()
	{
		int a = 0;
		for(int i = 0;i<seq.length();i++) {
			if(seq.charAt(i) == 'G'){
				a++;
			}
		}
		return a;}
	public int numC()
	{
		int a = 0;
		for(int i = 0;i<seq.length();i++) {
			if(seq.charAt(i) == 'C'){
				a++;
			}
		}
		return a;}
	public int numT()
	{
		int a = 0;
		for(int i = 0;i<seq.length();i++) {
			if(seq.charAt(i) == 'T'){
				a++;
			}
		}
		return a;}

	
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

	     File myFile = new File("/Users/ansh/git/Labs/src/test.txt");

	     writeTableSummary( fastaList,  myFile);
	}

	private static void writeTableSummary(List<FastaSequence> fastaList, File myFile) {
		try {
			BufferedWriter f_writer
			= new BufferedWriter(new FileWriter(myFile));
			f_writer.write("sequenceID\tnumA\tnumC\tnumG\tnumT\tsequence\n");
			for( FastaSequence fs : fastaList)
		     {
				String[] words = fs.getHeader().split(" ");
				f_writer.write(words[0] + "\t" + fs.numA() + "\t" + fs.numC()+ "\t" + fs.numG()+ "\t" + fs.numT() + "\t" + fs.getSequence() + "\n");
		      }
			f_writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}



}
