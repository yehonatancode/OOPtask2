package Algorithms;
	import java.io.File;
	import java.util.Scanner;

	import File_format.Csv2kml;

	public class MultiCSV {



		public String s = new String();

				public void run() throws Exception {
			System.out.println("Enter the directory path");
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
					Csv2kml c2k = new Csv2kml(list[i].getAbsolutePath(), list[i].getName()+".kml"); 
					c2k.run();
				}
			}
			System.out.println("Convert finished");
			}
		}


	}

