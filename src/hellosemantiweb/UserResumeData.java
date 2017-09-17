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
import java.util.ArrayList;

public class UserResumeData {
	private Long userId;
	private String Name;
	private String ImageURL;
	private String ResumeURL;
	private String FileContent;
	private String EmailId;
	private String PhoneNo;
	private ArrayList<UserResumeInstitute> InstituteList;
	private ArrayList<String> InstituteReferenceList;
	private ArrayList<String> CompanyReferenceList;
	private ArrayList<UserResumeCompany> CompanyList;
    private ArrayList<Experience> ExperienceList;
    private ArrayList<String> DegreeList;
    private ArrayList<ColDeg> InstitutewithDegree;
    private ArrayList<String> SkillList;
    private ArrayList<String> PositionList;
    private ArrayList<DegreeMajor> MajorList;
	
	public UserResumeData(){
		InstituteList = new ArrayList<>();
		InstituteReferenceList = new ArrayList<>();
		CompanyList = new ArrayList<>();
		CompanyReferenceList = new ArrayList<>();
		ExperienceList = new ArrayList<>();
		DegreeList = new ArrayList<>();
		InstitutewithDegree = new ArrayList<>();
		SkillList = new ArrayList<>();
		PositionList = new ArrayList<>();
		MajorList = new ArrayList<>();
	}
	
	public Long GetUserId(){
		return this.userId;
	}
	
	public void SetUserId(Long userId){
		this.userId = userId;
	}
	
	public String GetFileContent(){
		return this.FileContent;
	}
	
	public void SetFileContent(String FileContent){
		this.FileContent = FileContent;
	}
	
	public String GetEmailId(){
		return this.EmailId;
	}
	
	public void SetEmailId(String EmailId){
		this.EmailId = EmailId;
	}
	
	public String GetPhoneNo(){
		return this.PhoneNo;
	}
	
	public void SetPhoneNo(String PhoneNo){
		this.PhoneNo = PhoneNo;
	}
	
	public ArrayList<String> GetInstituteReferenceList(){
		return this.InstituteReferenceList;
	}
	
	public ArrayList<UserResumeInstitute> GetInstituteList(){
		return this.InstituteList;
	}
	
	public void SetInstituteList(ArrayList<UserResumeInstitute> InstituteList){
		this.InstituteList = InstituteList;
	}
	
	public ArrayList<UserResumeCompany> GetCompanyList(){
		return this.CompanyList;
	}

	public ArrayList<Experience> GetExperienceList(){
		return this.ExperienceList;
	}
	
	public ArrayList<String> GetDegreeList(){
		return this.DegreeList;
	}
	
	public ArrayList<ColDeg> GetColDegList(){
		return this.InstitutewithDegree;
	}
	
	public ArrayList<String> GetCompanyReferenceList(){
		return this.CompanyReferenceList;
	}
	
	public void SetCompanyList(ArrayList<UserResumeCompany> CompanyList){
		this.CompanyList = CompanyList;
	}
	
	public void AddInstitute(UserResumeInstitute Institute){
		this.InstituteList.add(Institute);
	}
	
	public void AddSkill(String Skill){
		this.SkillList.add(Skill);
	}
	
	public void AddPosition(String Position){
		this.PositionList.add(Position);
	}
	
	public void AddMajor(String Degree,String Major){
		this.MajorList.add(new DegreeMajor(Degree,Major));
	}
	
	public void AddInstituteReference(String Institute){
		this.InstituteReferenceList.add(Institute);
	}
	
	public void AddCompany(UserResumeCompany company){
		this.CompanyList.add(company);
	}
	
	public void AddCompanyReference(String company){
		this.CompanyReferenceList.add(company);
	}
	
	public void AddExperience(int StartMn,int StartYr,int EndMn,int EndYr){
    	this.ExperienceList.add(new Experience(StartMn,StartYr,EndMn,EndYr));
    }
	
	public void AddDegree(String Degree){
		this.DegreeList.add(Degree);
    }
	
	public void AddColDeg(String College,String Degree){
		this.InstitutewithDegree.add(new ColDeg(College,Degree));
	}
	
	public String GetColDegDegree(int index){
		return this.InstitutewithDegree.get(index).GetDegree();
	}
	
	public String GetColDegCollege(int index){
		return this.InstitutewithDegree.get(index).GetCollege();
	}
	
	public int GetStartYr(int index){
    	return this.ExperienceList.get(index).GetStartYr();
    }
    
    public int GetEndYr(int index){
    	return this.ExperienceList.get(index).GetEndYr();
    }
    
    public int GetStartMn(int index){
    	return this.ExperienceList.get(index).GetStartMn();
    }
    
    public int GetEndMn(int index){
    	return this.ExperienceList.get(index).GetEndMn();
    }

	public ArrayList<String> getSkillList() {
		return SkillList;
	}

	public void setSkillList(ArrayList<String> skill) {
		SkillList = skill;
	}

	public ArrayList<String> getPositionList() {
		return PositionList;
	}

	public void setPositionList(ArrayList<String> positionList) {
		PositionList = positionList;
	}

	public ArrayList<DegreeMajor> getMajorList() {
		return MajorList;
	}

	public void setMajorList(ArrayList<DegreeMajor> majorList) {
		MajorList = majorList;
	}
	
	public String GetMajorDeg(int index){
		return this.MajorList.get(index).GetDegree();
	}
	
	public String GetMajorMaj(int index){
		return this.MajorList.get(index).GetMajor();
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getImageURL() {
		return ImageURL;
	}

	public void setImageURL(String imageURL) {
		ImageURL = imageURL;
	}

	public String getResumeURL() {
		return ResumeURL;
	}

	public void setResumeURL(String resumeURL) {
		ResumeURL = resumeURL;
	}
}

class Experience{
	private int StartYr;
    private int EndYr;
    private int EndMn;
    private int StartMn;
    
    Experience(int StartMn,int StartYr,int EndMn,int EndYr){
    	this.StartMn = StartMn;
    	this.EndMn = EndMn;
    	this.StartYr = StartYr;
    	this.EndYr = EndYr;
    }
    
    public int GetStartYr(){
    	return this.StartYr;
    }
    
    public void SetStartYr(int StartYr){
    	this.StartYr = StartYr;
    }
    
    public int GetEndYr(){
    	return this.EndYr;
    }
    
    public void SetEndYr(int EndYr){
    	this.EndYr = EndYr;
    }
    
    public int GetStartMn(){
    	return this.StartMn;
    }
    
    public void SetStartMn(int StartMn){
    	this.StartMn = StartMn;
    }
    
    public int GetEndMn(){
    	return this.EndMn;
    }
    
    public void SetEndMn(int EndMn){
    	this.EndMn = EndMn;
    }
}


class ColDeg{
	private String College;
	private String Degree;
	
	ColDeg(){}
	
	ColDeg(String College,String Degree){
		this.College = College;
		this.Degree = Degree;
	}
	
	public String GetCollege(){
		return this.College;
	}
	
	public void SetCollege(String College){
		this.College = College;
	}
	
	public String GetDegree(){
		return this.Degree;
	}
	
	public void SetDegree(String Degree){
		this.Degree = Degree;
	}
}

class DegreeMajor{
	private String Degree;
	private String Major;
	
	DegreeMajor(){}
	
	DegreeMajor(String Degree,String Major){
		this.Degree = Degree;
		this.Major = Major;
	}
	
	public String GetDegree(){
		return this.Degree;
	}
	
	public void SetDegree(String Degree){
		this.Degree = Degree;
	}
	
	public String GetMajor(){
		return this.Major;
	}
	
	public void SetMajor(String Major){
		this.Major = Major;
	}
}
