package hellosemantiweb;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author davenpattle
 */

public class UserResumeInstitute {
	
	private String Institute;
	
	private int StartYr;
	
	private int EndYr;

	public UserResumeInstitute(String institute,int start,int end){
		this.Institute = institute;
		this.StartYr = start;
		this.EndYr = end;
	}
        
        

	public String getInstitute() {
		return Institute;
	}

	public void setInstitute(String institute) {
		Institute = institute;
	}

	public int getStartYr() {
		return StartYr;
	}

	public void setStartYr(int startYr) {
		StartYr = startYr;
	}

	public int getEndYr() {
		return EndYr;
	}

	public void setEndYr(int endYr) {
		EndYr = endYr;
	}
}
