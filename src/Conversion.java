import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;



public class Conversion {

	//Method to process the input CSV file and iterate through it by each row
	static void processInputFile (String inputFilePath) 
	{


		try{
			// Reading the CSV file using buffered reader
			File inputF = new File(inputFilePath);
			InputStream inputFS = new FileInputStream(inputF);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));

			// Skipping the first row of the csv file using .skip (1)
			// Iterating through every line --br.lines()-- 
			//Calling mapToItem for each row in CSV file
			br.lines().skip(1).map(mapToItem).collect(Collectors.toList());

			//closing the bufferedReader object after 
			br.close();

			//writing out all the remaining elements (which have not sessioned out) to the file
			for (ActiveID id : activeList) 
			{
				System.out.println(id.ip + "," + id.firstAccess +","+ id.lastAccess +","+id.activeDuration +","+id.noOfDocAccessed);			
			}

		} 
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
		}

	}

	//creating a List to store all the active IPs (whose session has not expired) 
	static List <ActiveID> activeList = new CopyOnWriteArrayList<ActiveID>();

	//Function to access each row and converting it to desired format
	static Function<String, AccessInfo> mapToItem = (line) -> 
	{
		//Storing the value of session life in sessionFile
		String sessionFile = "input/inactivity_period.txt";
		BufferedReader br;
		int sessionLife = 0;

		try 
		{	//Reading the value of sessionFile from inactivity_period.txt
			File inputF = new File(sessionFile);
			InputStream inputFS;
			inputFS = new FileInputStream(inputF);
			br = new BufferedReader(new InputStreamReader(inputFS));
			//Converting sessionFile value into integer
			sessionLife = Integer.parseInt(br.readLine());  
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println(e.getMessage());
		} 
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
		}

		//CSV ha comma seperated lines. hence splitting the data with every comma and storing them in array
		String[] p = line.split(",");

		//creating an AccessInfo type object and storing the value of each attribute in corresponding variable
		AccessInfo item = new AccessInfo();

		//Storing first column IP address in variable ip of class AccessItem
		item.ip = p[0];

		// Using SimpleDateFormat to convert values of second and third column into date and time 
		SimpleDateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat readFormat1 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		SimpleDateFormat writeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		//combining p[1] and p[2] into one DateTime variable date
		Date date = null;
		String dt = null;
		String b = null;
		try 
		{	
			b = p[1] + " " + p[2];
			date = readFormat.parse(b);
			System.out.println(date);
			if (!b.equals(readFormat.format(date))) 
			{
				System.out.println("inside if");
				date = null;
			}
		} 
		catch (ParseException e) 
		{
			System.out.println(e.getMessage());
		}
		if (date == null) {
			// Invalid date format
			try {
				date = readFormat1.parse(b);
				//Converting the DateTime variable to desired output format
				dt = writeFormat.format(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		} else {
			// Valid date format
			//Converting the DateTime variable to desired output format
			dt = writeFormat.format(date);
		}



		//storing other csv cell values into corresponding variable
		item.accessTime = dt;
		item.cik = p[4];
		item.accession = p[5];
		item.extension = p[6];

		// flag variable to check if IP already exists in the active ID list
		int count = 0;

		// Adding the IP entry to the list if the list is empty
		if(activeList.isEmpty() == true)
		{
			ActiveID newActiveId = new ActiveID();
			newActiveId.setIp(item.ip);
			newActiveId.setFirstAccess(item.accessTime);
			newActiveId.setLastAccess(item.accessTime);
			newActiveId.setActiveDuration(1);;
			newActiveId.setNoOfDocAccessed(1);
			activeList.add(newActiveId);
		}

		// executed if the list is not empty
		else 
		{	
			// comparing current element with all the element in the activeList
			for (ActiveID id : activeList) 
			{	
				//calculating inActive duration : current time - last access time for IP element
				Date d3 = null;
				Date d4 = null;

				//converting into date to perform calculation
				try 
				{
					d3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((item.accessTime));
					d4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((id.lastAccess));
					id.inActiveDuration = (d3.getTime() - d4.getTime())/1000 % 60;
				} 
				catch (ParseException e) 
				{
					System.out.println(e.getMessage());
				}


				//checking if the InActive duration of an IP element is greater than sessionLife
				if(id.inActiveDuration  > sessionLife)
				{
					//printing the IP element to the output 
					System.out.println(id.ip + "," + id.firstAccess +","+ id.lastAccess +","+id.activeDuration +","+id.noOfDocAccessed);	

					//Removing the IP element from the list
					activeList.remove(id)  ;//remove the object
				}	

				// if current IP already exists in the ActiveList. i.e. current Id is already active
				else if (id.getIp().equals(item.ip))
				{	
					//updating the last access of current ID to current time
					id.lastAccess = item.accessTime;
					// increasing the number of document accessed by 1
					id.noOfDocAccessed += 1;

					//calculating the new active time for IP element
					Date d1 = null;
					Date d2 = null;

					try 
					{
						d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((id.firstAccess));
						d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((id.lastAccess));
					} 
					catch (ParseException e) 
					{
						e.printStackTrace();
					}


					id.activeDuration = (d2.getTime() - d1.getTime())/1000 % 60;
					id.activeDuration += 1;

					// changing the flag value to 1.
					count += 1;
				}

			}

			//if the current element for not present in the list already or was removed due to session time out
			//adding  to the ActiveList
			if(count == 0)
			{
				ActiveID newActiveId = new ActiveID();
				newActiveId.setIp(item.ip);
				newActiveId.setFirstAccess(item.accessTime);
				newActiveId.setLastAccess(item.accessTime);
				newActiveId.setActiveDuration(1);
				newActiveId.setNoOfDocAccessed(1);

				activeList.add(newActiveId);
			}

		}

		return item;

	};

	public static boolean isValidFormat(String format, String value) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(value);
			if (!value.equals(sdf.format(date))) {
				date = null;
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return date != null;
	}


	//main method
	public static void main(String[] args) {

		LocalDateTime s = LocalDateTime.now();

		//CSV input file path
		String filePath = "input/outlog3.csv";

		//creating a printout object to print output to a txt file output.txt
		PrintStream out;
		try 
		{
			out = new PrintStream(new FileOutputStream("output/output.txt"),true);
			System.setOut(out);
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println(e.getMessage());
		}

		//calling function to process the csv file
		processInputFile(filePath);

	}


}