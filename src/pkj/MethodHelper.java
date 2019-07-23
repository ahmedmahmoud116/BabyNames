package pkj;
import java.io.File;
import org.apache.commons.csv.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MethodHelper {
	public MethodHelper() {
		
	}
	public ArrayList<String> readFile(int year) throws IOException{
		
		
		String fileName = "yob" + year  + ".csv";
		File f = new File("C:\\Users\\lenovo\\Documents\\Downloads\\Compressed\\Java1\\Week4\\Babynames\\us_babynames_by_year\\" + fileName);
		
		ArrayList<String> al = new ArrayList<String>();
		FileReader fr = new FileReader(f);
		CSVParser cs = new CSVParser(fr,CSVFormat.DEFAULT);
		for(CSVRecord cr:cs) {
			for(int i = 0; i<3 ;i++) {
				al.add(cr.get(i));
			}
		}
		cs.close();
		return al;
	}
	public void  totalBirths() throws IOException {
		ArrayList<String> al = readFile(1900);
		int countM = 0;
		int countF = 0;
		
		for(int i = 0; i<al.size() ;i = i + 3) {
			if(al.get(i+1).equalsIgnoreCase("M"))
				countM++;
			else
				countF++;
			System.out.println(al.get(i) + " " + al.get(i+1) + " " + al.get(i+2));
		}
		System.out.println("Number of girls is: " + countF);
		System.out.println("Number of boys is: " + countM);
	}
	
	public void testBirth() throws IOException {
		totalBirths();
	}
	
	public int getRank(int year,String name,String gender) throws IOException{
			
		ArrayList<String> al = readFile(year);
		int countM = 0;
		int countF = 0;
		for(int i = 0; i<al.size();i = i+3) {
			if(al.get(i+1).equalsIgnoreCase("M")) {
				countM++;
				if(al.get(i).equalsIgnoreCase(name) && al.get(i+1).equalsIgnoreCase(gender)) {
					return countM;
				}
			}
			if(al.get(i+1).equalsIgnoreCase("F")) {
				countF++;
				if(al.get(i).equalsIgnoreCase(name) && al.get(i+1).equalsIgnoreCase(gender)) {
					return countF;
				}
			}
		}
		
		return -1;
	}
	
	public void testGetRank() throws IOException {
		System.out.println("the rank of " + "Frank" +" is " + getRank(1971, "Frank", "M"));
	}
	
	public String getName(int year,int rank,String gender) throws IOException{
		
		ArrayList<String> al = readFile(year);
		
		int countM = 0;
		int countF = 0;
		for(int i = 0; i<al.size();i = i+3) {
			if(al.get(i+1).equalsIgnoreCase("M")) {
				countM++;
				if( countM == rank && al.get(i+1).equalsIgnoreCase(gender)) {
					return al.get(i);
				}
			}
			if(al.get(i+1).equalsIgnoreCase("F")) {
				countF++;
				if( countF == rank && al.get(i+1).equalsIgnoreCase(gender)) {
					return al.get(i);
				}
			}
		}
		
		return "NO NAME";
	}
	
	public void testGetName() throws IOException {
		System.out.println("the name of rank " + "450" +" is " + getName(1982, 450, "M"));
	}
	
	public void whatIsNameInYear(String name,int year,int newyear,String gender) throws IOException {
		int rank  = getRank(year,name,gender);
		String newname = getName(newyear, rank, gender);
		System.out.println(name + " born in " + year + " would be " + newname + " if she was born in " + newyear);
	}
	
	public void testWhatIsName() throws IOException {
		whatIsNameInYear("Susan", 1972, 2014, "F");
	}
	
	public int yearOfHighestRank(String name,String gender) throws NumberFormatException, IOException {
		
		int min = 99999;
		int highestyear = -1;
		DirectoryResource dr = new DirectoryResource();
		for(File f: dr.selectedFiles()) {
			String fileName = f.getName();
			fileName = fileName.substring(3,7);
			int rank = getRank(Integer.parseInt(fileName), name, gender);
			if(min > rank && rank != -1)
			{
				min = rank;
				highestyear = Integer.parseInt(fileName);
			}
		}
		return highestyear;
	}
	
	public void testHighestYear() throws NumberFormatException, IOException {
		System.out.println("the highest rank year of name " + "Genevieve" + " is " + yearOfHighestRank("Genevieve", "F"));
	}
	
	public double getAverageRank(String name,String gender) throws NumberFormatException, IOException {
		
		int countF = 0;
		double sum = 0;
		DirectoryResource dr = new DirectoryResource();
		for(File f: dr.selectedFiles()) {
			countF++;
			String fileName = f.getName();
			fileName = fileName.substring(3,7);
			int rank = getRank(Integer.parseInt(fileName), name, gender);
			if(rank != -1)
			sum += rank;
		}
		if(sum == 0)
			return -1;
		
		return (sum/countF);
	}
	
	public void testgetAverageRank() throws NumberFormatException, IOException {
		System.out.printf("The average rank of " + "Susan" + " is " + "%.3f",getAverageRank("Susan", "F"));
	}
	
	public int getTotalBirthsRankedHigher(int year,String name,String gender) throws IOException {
		ArrayList<String> al = readFile(year);
		
		int sumprevR = 0;
		int rank = getRank(year, name, gender);
		if(rank == -1) {
			return -1;
		}
		for(int i = 0; i<al.size();i = i+3) {
			if(al.get(i+1).equalsIgnoreCase(gender))
			{	
			if(rank == 1)
				return sumprevR;
			sumprevR += Integer.parseInt(al.get(i+2));
			rank--;
			}
		}
		return sumprevR;
	}
	
	public void testgetTotalBirthsRankedHigher() throws IOException {
		System.out.println("The births of ranked higher than " + "Emily" + " is " + getTotalBirthsRankedHigher(1990, "Emily", "F"));
	}
}

