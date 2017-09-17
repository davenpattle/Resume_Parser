/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hellosemantiweb;

/**
 *
 * @author davenpattle
 */

public class UserResumeCompany {
	private String Name;
    private int workExp;
    private int StartYr;
    private int EndYr;
    private int EndMn;
    private int StartMn;
    private String duration;
    private boolean Internship;
    
    
    public UserResumeCompany(){}
    
    public UserResumeCompany(String name,int sy,int ey,int sm,int em,Boolean internship){
        this.Name = name;
        this.StartYr = sy;
        this.EndYr = ey;
        this.StartMn = sm;
        this.EndMn = em;
        this.workExp = (EndYr - StartYr)*12 + EndMn - StartMn + 1;
        this.setInternship(internship);
    }
    
    public UserResumeCompany(String name,String dur,Boolean internship){
        this.Name = name;
        this.setDuration(dur);
        this.setInternship(internship);
    }
    
    public void DisplayCompany(){
        System.out.println("Name:" + this.Name + " Work Experience: " + this.workExp);
    }
    
    public String GetName(){
    	return this.Name;
    }

    
    public int GetEndYr(){
    	return this.EndYr;
    }

	public boolean isInternship() {
		return Internship;
	}

	public void setInternship(boolean internship) {
		Internship = internship;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
    
    
}

