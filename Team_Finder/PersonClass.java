package teammaker;

import java.util.ArrayList;

public class PersonClass {
	String name;
	int[] scores;
	Boolean perferability;
	int goals;
	double[] calculatedScores; 

	public double[] calculatedScore(int[] score){
	       double[] result = new double[5];
	       double frontEnd = 0.0001, backEnd = 0.0001, database = 0.0001, hardware = 0.0001;
	       ArrayList<Integer> flist = new ArrayList<Integer>(5);
	       ArrayList<Integer> blist = new ArrayList<Integer>(7);
	       ArrayList<Integer> dlist = new ArrayList<Integer>(6);
	       ArrayList<Integer> hlist = new ArrayList<Integer>(2);
	       for (int i = 0; i < 20; i++){
	           if (score[i] != 0 & i < 5) {
	               frontEnd += 1;
	               flist.add(score[i]);
	           }
	           else if (score[i] != 0 & 5 <= i & i < 12) {
	               backEnd++;
	               blist.add(score[i]);
	           }
	           else if (score[i] != 0 & 12 <= i & i < 18) {
	               database++;
	               dlist.add(score[i]);
	           }
	           else if (score[i] != 0 & 18 <= i & i < 20) {
	               hardware++;
	               hlist.add(score[i]);
	           }
	       }
	       int sum1 = flist.stream().mapToInt(Integer::intValue).sum();
	       result [0] = sum1 / frontEnd * (0.75 + frontEnd * 0.05);
	       int sum2 = blist.stream().mapToInt(Integer::intValue).sum();
	       result [1] = sum2 / backEnd * (0.75 + backEnd * 0.05);
	       int sum3 = dlist.stream().mapToInt(Integer::intValue).sum();
	       result [2] = sum3 / database * (0.75 + database * 0.05);
	       int sum4 = hlist.stream().mapToInt(Integer::intValue).sum();
	       result [3] = sum4 / hardware * (0.8 + hardware * 0.1);
	       return result;
	}

	public PersonClass(String n, int[] s, Boolean p, int g){
		name = n;
		scores = s;
		calculatedScores = calculatedScore(s);
		perferability = p;
		goals = g;
	}
	public String getName() {
		return name;
	}
	public int[] getScores() {
		return scores;
	}
	public double[] getCalculatedScores() {
		return calculatedScores;
	}
	public Boolean getPerferability() {
		return perferability;
	}
	public int getGoals() {
		return goals;
	}
}
