package csv;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
//import java.io.Writer;
import java.util.ArrayList;

import java.util.HashSet;

import java.util.Set;

import com.crud.dao.FetchGlerskalinn;

public class StackReader {

	private static BufferedReader cSVFileReader;
	// private static BufferedWriter cSVFileWriter;

	// ArrayList<DTOPontunVasar> allarpontunar = new
	// ArrayList<DTOPontunVasar>();
	public Set<DTOPontunVasar> allarPontunarRecksOnly = new HashSet<DTOPontunVasar>();
	// HASHtable hashmap key-value pairs
	Set<DTOPontunVasar> allarPonturnarVasarOnly = new HashSet<DTOPontunVasar>();
	ArrayList<String> vasaArray = new ArrayList<String>();
	private BufferedWriter out = null;
	private FileOutputStream fos = null;

	public StackReader() { // throws IOExcepn tion
		String pontunarN = null;
		String vasaN = null;

		DTOPontunVasar temp = null;
		try {
			cSVFileReader = new BufferedReader(new FileReader("VasaSkra.DAT"));
			String dataRow = cSVFileReader.readLine();
			// ArrayList <DTOPontunVasar> allarpontunar = new
			// ArrayList<DTOPontunVasar>();

			while (dataRow != null) {
				String[] dataArray = dataRow.split(",");
				for (String item : dataArray) {
					// TO DO make some error handling ( which comes from CSV)
					if (item.length() > 4 && item.length() < 7) {
						pontunarN = item;
					} else if (item.length() > 3 && item.length() < 5) {
						vasaN = item;

					}else {}
					/*
					 * temp = new DTOPontunVasar(pontunarN, vasaN);
					 * allarpontunar.add(temp);
					 */
					// System.out.print(item + "\t");
				}
				  // TO DO also check the length here 
				 if ( Integer.parseInt(vasaN) <= 2999 ){
				temp = new DTOPontunVasar(pontunarN, vasaN);
				allarPontunarRecksOnly.add(temp);
				System.out.println(); // Print the data line.
				 }else if (Integer.parseInt(vasaN)>=3000){
					 
					 temp= new DTOPontunVasar(pontunarN, vasaN);
					 allarPonturnarVasarOnly.add(temp);
				 }
				 
				 
				 
				dataRow = cSVFileReader.readLine();

				// get rid of a double entries here

			}
		} catch (IOException e) {
			// throw new IOException (e.getMessage());
		} finally {
			try {

				cSVFileReader.close();

			} catch (IOException e) {
				e.printStackTrace();

			}

		}

		try {
			// Writing back a file without double entries

			fos = new FileOutputStream("VasaSkra.dat");
			out = new BufferedWriter(new OutputStreamWriter(fos));
			for (DTOPontunVasar pairToWrite : allarPonturnarVasarOnly) {

				String coma = ",";
				out.write(pairToWrite.getPontunarN());
		
				
				out.write(coma);
				out.write(pairToWrite.getVasaN());
				out.write(coma);
				out.write("\n");
				
 // here we checking an Set with our Rekkar
				for (DTOPontunVasar r :allarPontunarRecksOnly){
					System.out.println("set of allarPontunarRecksOnly="+r.getPontunarN()+" ," + r.getVasaN());
					
					
					
				}
			}

			// Flush the BUFFER !!!

			// finally do close
		} catch (IOException e) {
			System.out.println(e);
		} finally {
			try {
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
		}
		

	}
	
	// TO DO delete this metod 
	public void DeleteOldRecksInSQL (){
		       for (DTOPontunVasar val :allarPontunarRecksOnly){
		    	   
		    	   if (Integer.parseInt(val.getVasaN()) <3000  ){
		    		   
		    		   allarPonturnarVasarOnly.add(val);
		    	   }
		    	   
		    	   
		    	   
		       }
		       
		       if (allarPonturnarVasarOnly.size()>0){
		       for (DTOPontunVasar p :allarPonturnarVasarOnly){
		                   FetchGlerskalinn fg = new FetchGlerskalinn();
		                   fg.delete(Integer.parseInt(p.getVasaN()));
		    	   
		       }
		       }
                     		
		
	};

	public String SearchVasan(String enteredPontun) {

		String vasaN = "";
		String tempVasaN = "";
		for (DTOPontunVasar pairValue : allarPontunarRecksOnly) {

			if (enteredPontun.equalsIgnoreCase(pairValue.getPontunarN())) {

				tempVasaN = pairValue.getVasaN();

				vasaArray.add(tempVasaN);

			}

		}

		// remove, set is an overkill now
		Set<String> set = new HashSet<String>(vasaArray);

		System.out.print("Remove duplicate result: ");

		String[] result = new String[set.size()];
		set.toArray(result);
		for (String s : result) {
			String semicol = ";";
			System.out.print(s + ", ");
			// adding a "rekka msg to output for values bigger then 1000
			// Ispan marking rekkar from 1 to 2999 . available figures are
			// >=3000
			if (Integer.parseInt(s) <= 3000) {
				vasaN = vasaN.concat(" Rekka#" + s + semicol);

			} else {
				vasaN = vasaN.concat(" inni-" + s.substring(1) + semicol);
			}
		}

		return vasaN;
	}

	public void DeletePontun(String pontunToDelete) {

		try {
			// FileOutputStream fos = null;

			fos = new FileOutputStream("VasaSkra.dat");
			out = new BufferedWriter(new OutputStreamWriter(fos));
			for (DTOPontunVasar pairToWrite : allarPontunarRecksOnly) {
				if (pontunToDelete.equalsIgnoreCase(pairToWrite.getPontunarN())) {

				} else {

					String coma = ",";
					out.write(pairToWrite.getPontunarN());
					out.write(coma);
					out.write(pairToWrite.getVasaN());
					out.write(coma);
					out.write("\n");
				}

			}

			// Flush the BUFFER !!!

			// finally do close
		} catch (IOException e) {
			System.out.println(e);
		} finally {
			try {
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
		}
	}
}
