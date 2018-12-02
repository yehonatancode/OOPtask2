import java.io.File;
import java.util.Scanner;
import File_format.Csv2kml;
import GIS.NewGISProj;

public class MultiCSV {



	public String s = new String();

			public NewGISProj run() throws Exception {
		NewGISProj project = new NewGISProj();		
		System.out.println("Enter the directory path");
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		String s = scan.nextLine();
		File fileNew = new File(s);
		File [] list = fileNew.listFiles();
		
		
		if (!fileNew.isDirectory()) {
			throw new Exception("Error in file directory");
		}
		else {
		String check = ".csv";
		for (int i = 0; i < list.length; i++) {
			if (list[i].getName().contains(check)) {
				Csv2kml n = new Csv2kml(list[i].getAbsolutePath(), list[i].getName()+".kml"); 
				project.add(n.run());
			}
		}
		System.out.println("Convert finished");
		return project;
		}
	}


}