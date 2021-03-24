package teammaker;
import teammaker.PersonClass;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class CompareClass {
	
	PersonClass user;
	ArrayList<PersonClass> hackerNetwork;
	ArrayList<PersonClass> matches;

	public CompareClass(PersonClass user, ArrayList<PersonClass> hackerNetwork) {
		this.user = user;
		this.hackerNetwork = hackerNetwork;
	}

	public void compare() {
		int[][] combined = new int[hackerNetwork.size()][2];

		int[] scores = new int[hackerNetwork.size()];
		String[] names = new String[hackerNetwork.size()];
		for (int count = 0; count <= hackerNetwork.size(); count++){
			int score = 0;
			for(int a = 0; a < 4; a++){
				for (int i = -1; hackerNetwork.get(count).getScores()[a] + i < hackerNetwork.get(count).getScores()[a] + 1; i += 0.1){
					if (hackerNetwork.get(count).getScores()[a] + i == this.user.getScores()[a]){
						score++;
					}
				}
			}
			if (user.getGoals() == hackerNetwork.get(count).getGoals()){
				score ++;
			}
			System.out.println(score);
			combined[count][0] = score;
			combined[count][1] = user.getName();
		}
		for (int count = 0; count < scores.length; count ++){
			for (int i = 0; i < scores[count]; i++){

			}
		}
	}

	public ArrayList<PersonClass> getMatches() {
		return matches;
	
	}

}
