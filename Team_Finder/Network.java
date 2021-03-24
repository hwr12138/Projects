package teammaker;
import teammaker.PersonClass;
import teammaker.CompareClass;
import java.util.ArrayList;

public class Network {
	
	ArrayList<PersonClass> hackerNetwork = new ArrayList<PersonClass>();
	
	public Network() {
		
		createNetwork();
		
		int[] scores = {0,2,2,1,0,4,3,0,0,0,0,3,0,0,0,0,0,0,0,0};
		PersonClass user = new PersonClass("Justin Bauer", scores, false, 2);
		
		CompareClass userComparison = new CompareClass(user, hackerNetwork);
		userComparison.compare();
		
		toText(user);
		
		for (int i = 0; i < userComparison.getMatches().size(); i++) {
			
		}
	}

	private void createNetwork() {
		// Add fake network
		PersonClass n = null;
		hackerNetwork.add(n);
		
	}
	private void toText(PersonClass person) {
		System.out.println("Name: " + person.getName());
		System.out.print("Team level: ");
		if (person.perferability) {
			System.out.println("Same level");
		}else {
			System.out.println("Any level");
		}
		System.out.print("Goal: ");
		if (person.getGoals() == 1) {
			System.out.println("Win");
		}else if (person.getGoals() == 2) {
			System.out.println("Learn");
		}else {
			System.out.println("Have fun");
		}
		System.out.print("Skills known: ");
		for (int i = 0; i < person.scores.length; i++) {
			if (person.scores[i] != 0) {
				if(i == 0) {
					System.out.print("React, ");
				}else if(i == 1) {
					System.out.print("CSS, ");
				}else if(i == 2) {
					System.out.print("HTML, ");
				}else if(i == 3) {
					System.out.print("Javascript, ");
				}else if(i == 4) {
					System.out.print("Swift, ");
				}else if(i == 5) {
					System.out.print("Java, ");
				}else if(i == 6) {
					System.out.print("C/C++, ");
				}else if(i == 7) {
					System.out.print("C#, ");
				}else if(i == 8) {
					System.out.print("Ruby, ");
				}else if(i == 9) {
					System.out.print("PHP, ");
				}else if(i == 10) {
					System.out.print(".NET, ");
				}else if(i == 11) {
					System.out.print("Python, ");
				}else if(i == 12) {
					System.out.print("SQL, ");
				}else if(i == 13) {
					System.out.print("MongoDB, ");
				}else if(i == 14) {
					System.out.print("Firebase, ");
				}else if(i == 15) {
					System.out.print("Google Cloud, ");
				}else if(i == 16) {
					System.out.print("Google Azure, ");
				}else if(i == 17) {
					System.out.print("Android Studio, ");
				}else if(i == 18) {
					System.out.print("Raspberry PI, ");
				}else if(i == 19) {
					System.out.print("Arduino, ");
				}else {
					System.out.print("Error");
				}
			}
		}
		System.out.println("");
		System.out.println("Ratings: ");
		System.out.println("	Front end: " + Math.round(person.getCalculatedScores()[0]*100.0)/100.0);
		System.out.println("	Back end: " + Math.round(person.getCalculatedScores()[1]*100.0)/100.0);
		System.out.println("	Database: " + Math.round(person.getCalculatedScores()[2]*100.0)/100.0);
		System.out.println("	Hardware: " + Math.round(person.getCalculatedScores()[3]*100.0)/100.0);
		System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
		System.out.println("");
	}
}
