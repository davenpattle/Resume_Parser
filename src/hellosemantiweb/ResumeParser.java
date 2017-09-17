/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hellosemantiweb;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 *
 * @author davenpattle
 */

class DownloadResume{
    String fileUrl;
    String fileData;
    
    DownloadResume(int index){
        fileUrl = "resumes/" + index;
        fileData = ImportFileData(fileUrl);
    }
    
    public String ImportFileData(String url){
		String File = url;
		
		if(File==null)
			return null;
		
		File file = new File(File);
		String extension = "";
		DataInputStream f;
		try {
			f = new DataInputStream(new FileInputStream(file));
			int magic = f.readInt();
			
			if(magic==0x25504446){
				extension = "pdf";
			}
			else if(magic==0x504B0304){
				extension = "docx";
			}
			else{
				extension = "doc";
			}
			
			f.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		
		if(extension.equals("pdf")){
			PDFParser parser;
			String parsedText = null;;
			PDFTextStripper pdfStripper = null;
			PDDocument pdDoc = null;
			COSDocument cosDoc = null;
			
			if (!file.isFile()) {
				System.err.println("File " + File + " does not exist.");
				return null;
			}
			try {
				parser = new PDFParser(new RandomAccessFile(file,"r"));
			} catch (IOException e) {
				System.err.println("Unable to open PDF Parser. " + e.getMessage());
				return null;
			}
			try {
				parser.parse();
				cosDoc = parser.getDocument();
				pdfStripper = new PDFTextStripper();
				pdDoc = new PDDocument(cosDoc);
				pdfStripper.setStartPage(1);
				pdfStripper.setEndPage(5);
				parsedText = pdfStripper.getText(pdDoc);
			} catch (Exception e) {
				System.err.println("An exception occured in parsing the PDF Document."+ e.getMessage());
				return null;
			} finally {
				try {
					if (cosDoc != null)
						cosDoc.close();
					if (pdDoc != null)
						pdDoc.close();
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
			return parsedText;	
		}
		else if(extension.equals("doc")){
	        try
	        {
	        	FileInputStream fis = new FileInputStream(file);
	            HWPFDocument document = new HWPFDocument(fis);
	            WordExtractor extractor = new WordExtractor(document);
	            String fileData = extractor.getText();
	            return fileData;
	        }
	        catch (Exception exep)
	        {
	            exep.printStackTrace();
	            return null;
	        }
		}
		else if(extension.equals("docx")){
			try
	        {
				FileInputStream fis = new FileInputStream(file);
				XWPFDocument document = new XWPFDocument(fis);
				XWPFWordExtractor extractor = new XWPFWordExtractor(document);
				String fileData = extractor.getText();
				return fileData;
	        }
			catch(Exception exep){
				exep.printStackTrace();
				return null;
			}
		}
		return null;
	}
    
}

class Parser{
    DownloadResume resume;

    public static ArrayList<String> PositionList;
    public static ArrayList<String> SkillList;
    public static ArrayList<String> DegreeList;
    public static ArrayList<String> CompanyList;
    public static ArrayList<String> InstituteList;
    public static ArrayList<String> MajorList;      
    
    Parser() throws IOException{
        InstituteList = new ArrayList<>();
	CompanyList = new ArrayList<>();
	DegreeList = new ArrayList<>();
	SkillList = new ArrayList<>();
	PositionList = new ArrayList<>();
	MajorList = new ArrayList<>();
        
        ImportInstituteList();
        ImportCompanyList();
        ImportDegreeList();
        ImportMajorList();
        ImportSkillList();
        ImportPositionList();
    }
    
    public void DownloadFile(int index){
        resume = new DownloadResume(index);
    }
    
    // Import Institute List for User Resume parsing
         public static void  ImportInstituteList() throws IOException{
        	 FileReader in = new FileReader("lists/institutes_arv.csv");
             BufferedReader buf_in = new BufferedReader(in);
             String str;
             while((str=buf_in.readLine())!=null){
                 InstituteList.add(str);
             }
             buf_in.close();
             in.close();
         }
		
         
      // Import Company List for User Resume parsing
         public static void  ImportCompanyList() throws IOException{
        	 FileReader in = new FileReader("lists/companies_arv.csv");
             BufferedReader buf_in = new BufferedReader(in);
             String str;
             while((str=buf_in.readLine())!=null){
                 CompanyList.add(str);
             }
             buf_in.close();
             in.close();
         }
         
      // Import Degree List for User Resume parsing
         public static void  ImportDegreeList() throws IOException{
        	 FileReader in = new FileReader("lists/degrees.csv");
             BufferedReader buf_in = new BufferedReader(in);
             String str;
             while((str=buf_in.readLine())!=null){
                 DegreeList.add(str);
             }
             buf_in.close();
             in.close();
         }
         
      // Import Skill List for User Resume parsing
         public static void  ImportSkillList() throws IOException{
        	 FileReader in = new FileReader("lists/skills.csv");
             BufferedReader buf_in = new BufferedReader(in);
             String str;
             while((str=buf_in.readLine())!=null){
                 SkillList.add(str);
                 //System.out.println(str);
             }
             buf_in.close();
             in.close();
         }
         
      // Import Position List for User Resume parsing
         public static void  ImportPositionList() throws IOException{
        	 FileReader in = new FileReader("lists/positions.csv");
             BufferedReader buf_in = new BufferedReader(in);
             String str;
             while((str=buf_in.readLine())!=null){
                 PositionList.add(str);
                 //System.out.println(str);
             }
             buf_in.close();
             in.close();
         }
         
      // Import Position List for User Resume parsing
         public static void  ImportMajorList() throws IOException{
        	 FileReader in = new FileReader("lists/majors.csv");
             BufferedReader buf_in = new BufferedReader(in);
             String str;
             while((str=buf_in.readLine())!=null){
                 MajorList.add(str);
                 //System.out.println(str);
             }
             buf_in.close();
             in.close();
         }
    
    public String GetSkillRegex(int index){
		String str = SkillList.get(index);
		String[] words = str.split("\\$");
        String regex = "";
        for(int i=0;i<words.length;i++){
            String[] partlist = words[i].split(" ");
            if(partlist.length!=1){
                    regex += "(?i)";
                }
            else{
                    regex += "(?-i)";
            }
            
            for(int j=0;j<partlist.length;j++){
            	regex += "\\b";
            	String temp="";
                for(int k=0;k<partlist[j].length();k++){
                    temp+= partlist[j].charAt(k) + "[ \\t]?";
                }
                partlist[j] = temp;
            	partlist[j] = partlist[j].replace("(", "[" + "(" + "]?");
                partlist[j] = partlist[j].replace(")", "[" + ")" + "]?");
                partlist[j] = partlist[j].replace(",","(,)?");
                partlist[j] = partlist[j].replace(".","(\\.)?");
                partlist[j] = partlist[j].replaceAll("(-|–|-)","(-|–|-)?");
                partlist[j] = partlist[j].replaceAll("&","[&]?");
                partlist[j] = partlist[j].replaceAll("\\Q+\\E", "[+]?");
                
                if(j!=partlist.length-1){
                    regex += partlist[j] + "([(\\.)\\t,\\s]|(-|–|-))*?";
                }
                else{
                    regex += partlist[j];
                }
            }
            
            regex += "\\b";
            
            if(i!=words.length-1)
                regex += "|";
        }
        
        //System.out.println(regex);
        return regex;
	}
    
    public String GetMajorRegex(int index){
		String str = MajorList.get(index);
		String[] words = str.split("\\$");
        String regex = "";
        for(int i=0;i<words.length;i++){
            String[] partlist = words[i].split(" ");
            regex += "(?i)";
            
            for(int j=0;j<partlist.length;j++){
            	regex += "\\b";
            	String temp="";
                for(int k=0;k<partlist[j].length();k++){
                    temp+= partlist[j].charAt(k) + "[ \\t]?";
                }
                partlist[j] = temp;
            	partlist[j] = partlist[j].replace("(", "[" + "(" + "]?");
                partlist[j] = partlist[j].replace(")", "[" + ")" + "]?");
                partlist[j] = partlist[j].replace(",","(,)?");
                partlist[j] = partlist[j].replace(".","(\\.)?");
                partlist[j] = partlist[j].replaceAll("(-|–|-)","(-|–|-)?");
                partlist[j] = partlist[j].replaceAll("&","[&]?");
                partlist[j] = partlist[j].replaceAll("\\Q+\\E", "[+]?");
                
                if(j!=partlist.length-1){
                    regex += partlist[j] + "([(\\.)\\t,\\s]|(-|–|-))*?";
                }
                else{
                    regex += partlist[j];
                }
            }
            
            regex += "\\b";
            
            if(i!=words.length-1)
                regex += "|";
        }
        
        //System.out.println(regex);
        return regex;
	}
    
    public String GetPositionRegex(int index){
		String str = PositionList.get(index);
		String[] words = str.split("\\$");
        String regex = "";
        for(int i=0;i<words.length;i++){
            String[] partlist = words[i].split(" ");
            regex += "(?i)";
            
            for(int j=0;j<partlist.length;j++){
            	regex += "\\b";
            	String temp="";
                for(int k=0;k<partlist[j].length();k++){
                    temp+= partlist[j].charAt(k) + "[ \\t]?";
                }
                partlist[j] = temp;
            	partlist[j] = partlist[j].replace("(", "[" + "(" + "]?");
                partlist[j] = partlist[j].replace(")", "[" + ")" + "]?");
                partlist[j] = partlist[j].replace(",","(,)?");
                partlist[j] = partlist[j].replace(".","(\\.)?");
                partlist[j] = partlist[j].replaceAll("(-|–|-)","(-|–|-)?");
                partlist[j] = partlist[j].replaceAll("&","[&]?");
                partlist[j] = partlist[j].replaceAll("\\Q+\\E", "[+]?");
                
                if(j!=partlist.length-1){
                    regex += partlist[j] + "([(\\.)\\t,\\s]|(-|–|-))*?";
                }
                else{
                    regex += partlist[j];
                }
            }
            
            regex += "\\b";
            
            if(i!=words.length-1)
                regex += "|";
        }
        
        //System.out.println(regex);
        return regex;
	}
	
	public String GetInstituteRegex(int index){
		String str = InstituteList.get(index);
		String[] words = str.split("\\$");
        String regex = "";
        for(int i=0;i<words.length;i++){
            String[] partlist = words[i].split(" ");
            regex += "(?i)";
            
            for(int j=0;j<partlist.length;j++){
            	regex += "\\b";
            	String temp="";
                for(int k=0;k<partlist[j].length();k++){
                    temp+= partlist[j].charAt(k) + "[ \\t]?";
                }
                partlist[j] = temp;
            	partlist[j] = partlist[j].replace("(", "[" + "(" + "]?");
                partlist[j] = partlist[j].replace(")", "[" + ")" + "]?");
                partlist[j] = partlist[j].replace(",","(,)?");
                partlist[j] = partlist[j].replace(".","(\\.)?");
                partlist[j] = partlist[j].replaceAll("(-|–|-)","(-|–|-)?");
                partlist[j] = partlist[j].replaceAll("&","[&]?");
                partlist[j] = partlist[j].replaceAll("\\Q+\\E", "[+]?");
                
                if(j!=partlist.length-1){
                    regex += partlist[j] + "([(\\.)\\t,\\s]|(-|–|-))*?";
                }
                else{
                    regex += partlist[j];
                }
            }
            
            regex += "\\b";
            
            if(i!=words.length-1)
                regex += "|";
        }
        
        //System.out.println(regex);
        return regex;
    }
	
	public String GetDegreeRegex(int index){
		String str = DegreeList.get(index);
		String[] words = str.split("\\$");
        String regex = "";
        for(int i=0;i<words.length;i++){
            String[] partlist = words[i].split(" ");
            regex += "(?i)";
            
            for(int j=0;j<partlist.length;j++){
            	regex += "\\b";
            	String temp="";
                for(int k=0;k<partlist[j].length();k++){
                    temp+= partlist[j].charAt(k) + "[ \\t]?";
                }
                partlist[j] = temp;
            	partlist[j] = partlist[j].replace("(", "[" + "(" + "]?");
                partlist[j] = partlist[j].replace(")", "[" + ")" + "]?");
                partlist[j] = partlist[j].replace(",","(,)?");
                partlist[j] = partlist[j].replace(".","(\\.)?");
                partlist[j] = partlist[j].replaceAll("(-|–|-)","(-|–|-)?");
                partlist[j] = partlist[j].replaceAll("&","[&]?");
                partlist[j] = partlist[j].replaceAll("\\Q+\\E", "[+]?");
                
                if(j!=partlist.length-1){
                    regex += partlist[j] + "([(\\.)\\t,\\s]|(-|–|-))*?";
                }
                else{
                    regex += partlist[j];
                }
            }
            
            regex += "\\b";
            
            if(i!=words.length-1)
                regex += "|";
        }
        
        return regex;
	}
	
	public String GetCompanyRegex(int index){
		String str = CompanyList.get(index);
		String[] words = str.split("\\$");
        String regex = "";
        for(int i=0;i<words.length;i++){
            String[] partlist = words[i].split(" ");
            regex += "(?i)";
            int flag=0;
            int breakpoint = 2;
            if(partlist.length<=3){
            	breakpoint = 1;
            }
            for(int j=0;j<partlist.length;j++){
            	regex += "\\b";
            	String temp="";
                for(int k=0;k<partlist[j].length();k++){
                    temp+= partlist[j].charAt(k) + "[ \\t]?";
                }
                partlist[j] = temp;
            	partlist[j] = partlist[j].replace("(", "[" + "(" + "]?");
                partlist[j] = partlist[j].replace(")", "[" + ")" + "]?");
                partlist[j] = partlist[j].replace(",","(,)?");
                partlist[j] = partlist[j].replace(".","(\\.)?");
                partlist[j] = partlist[j].replaceAll("(-|–|-)","(-|–|-)?");
                partlist[j] = partlist[j].replaceAll("&","[&]?");
                partlist[j] = partlist[j].replaceAll("\\Q+\\E", "[+]?");
                
                if(flag > breakpoint){
                    regex += "(" + partlist[j] + ")?";
                }
                else if(j!=partlist.length-1){
                    if((j!=0) &&(partlist[j].replaceAll("\\Q[ \\t]?\\E","").matches("(?i).*\\bThe\\b.*")==true || partlist[j].replaceAll("\\Q[ \\t]?\\E","").matches("(?i).*\\bdr\\.\\b.*")==true || partlist[j].replaceAll("\\Q[ \\t]?\\E","").matches("(?i).*\\bpvt.*")==true || partlist[j].replaceAll("\\Q[ \\t]?\\E","").matches("(?i).*\\bltd.*")==true || partlist[j].replaceAll("\\Q[ \\t]?\\E","").matches("(?i).*\\binc\\b.*")==true  || partlist[j].replaceAll("\\Q[ \\t]?\\E","").matches("(?i).*\\bnew\\b.*")==true || partlist[j].replaceAll("\\Q[ \\t]?\\E","").matches("(?i).*\\bgroup\\b.*")==true || partlist[j].replaceAll("\\Q[ \\t]?\\E","").matches("(?i).*\\blimited\\b.*")==true || partlist[j].replaceAll("\\Q[ \\t]?\\E","").matches("(?i).*\\bof\\b.*")==true || partlist[j].replaceAll("\\Q[ \\t]?\\E","").matches("(?i).*\\bbank\\b.*")==true || partlist[j].replaceAll("\\Q[ \\t]?\\E","").matches("(?i).*\\bfor\\b.*")==true || partlist[j].replaceAll("\\Q[ \\t]?\\E","").matches("(?i).*\\bcorp(oration)?\\b.*")==true)){
                        regex += "(" + partlist[j] +")?"+ "([(\\.)\\t,\\s]|(-|–|-))*?";
                    }
                    else if(partlist[j].contains(".") || partlist[j].contains("-") || partlist[j].contains("&")){
                        regex += partlist[j] + "([(\\.)\\t,\\s]|(-|–|-))*?";
                    }
                    else if(partlist[j].replaceAll("\\Q[ \\t]?\\E","").matches(".*\\band\\b.*")){
                        regex += "(" + partlist[j] + "|[&]" +")?" + "([(\\.)\\t,\\s]|(-|–|-))*?";
                    }
                    else if(partlist[j].replaceAll("\\Q[ \\t]?\\E","").matches(".*\\b[A-Za-z0-9]\\b.*")){
                        regex += partlist[j] + "([(\\.)\\t,\\s]|(-|–|-))*?";
                    }
                    else{
                        regex += partlist[j] + "([(\\.)\\t,\\s]|(-|–|-))*?";
                        flag++;
                    }
                }
                else{
                    if((j!=0) && (partlist[j].replaceAll("\\Q[ \\t]?\\E","").matches("(?i).*\\bThe\\b.*")==true || partlist[j].replaceAll("\\Q[ \\t]?\\E","").matches("(?i).*\\bdr\\.\\b.*")==true || partlist[j].replaceAll("\\Q[ \\t]?\\E","").matches("(?i).*\\bpvt.*")==true || partlist[j].replaceAll("\\Q[ \\t]?\\E","").matches("(?i).*\\bltd.*")==true || partlist[j].replaceAll("\\Q[ \\t]?\\E","").matches("(?i).*\\binc\\b.*")==true  || partlist[j].replaceAll("\\Q[ \\t]?\\E","").matches("(?i).*\\bnew\\b.*")==true || partlist[j].replaceAll("\\Q[ \\t]?\\E","").matches("(?i).*\\bgroup\\b.*")==true || partlist[j].replaceAll("\\Q[ \\t]?\\E","").matches("(?i).*\\blimited\\b.*")==true || partlist[j].replaceAll("\\Q[ \\t]?\\E","").matches("(?i).*\\bof\\b.*")==true || partlist[j].replaceAll("\\Q[ \\t]?\\E","").matches("(?i).*\\bbank\\b.*")==true || partlist[j].replaceAll("\\Q[ \\t]?\\E","").matches("(?i).*\\bfor\\b.*")==true || partlist[j].replaceAll("\\Q[ \\t]?\\E","").matches("(?i).*\\bcorp(oration)?\\b.*")==true)){
                        regex += "(" + partlist[j] +")?";
                    }
                    else{
                        regex += partlist[j];
                    } 
                }
            }
            
            regex += "\\b";
            
            
            if(i!=words.length-1)
                regex += "|";
        }
        
        //System.out.println(regex);
        return regex;
    }
    
    public UserResumeData FindUserInstituteData(UserResumeData res_data){
		int size = InstituteList.size();
		 
		
		for(int i=0;i<size;i++){
			Pattern p = Pattern.compile(GetInstituteRegex(i));
			String file = res_data.GetFileContent();
	        file = file.replaceAll("\\Q[\\E","");
	        file = file.replaceAll("\\Q]\\E","");
	        file = file.replaceAll("\\Q(\\E","");
	        file = file.replaceAll("\\Q)\\E","");
	        Matcher m = p.matcher(file);
			
			int count = 0;
			//System.out.println(p.pattern());
            if(m.find()) {
                count++;
            }
            if(count>0){
                    //System.out.println(p.pattern());
                    String str = p.pattern();
                    str = str.replaceAll("\\Q(?i)\\E", "");
                    str = str.replaceAll("\\Q(?-i)\\E", "");
                    str = str.replaceAll("\\Q[ \\t]?\\E", "");
                    str = str.replaceAll("\\Q\\b\\E", "");
                    str = str.replaceAll("\\Q[+]?\\E", "+");
                    str = str.replaceAll("\\Q[&]?\\E", "&");
                    str = str.replaceAll("\\Q(,)?\\E", ",");
                    str = str.replaceAll("\\Q(\\.)?\\E", ".");
                    str = str.replaceAll("\\Q([(\\.)\\t,\\s]|(-|–|-))*?\\E", " ");
                    str = str.replaceAll("\\Q(-|–|-)?\\E", "-");
                    str = str.replaceAll("\\Q[(]?\\E", "");
                    str = str.replaceAll("\\Q[)]?\\E", "");
                    String[] list = str.split("\\|");
                    String College_name = list[0];
                    
                    String regex = "(([0-9][ \\t]?[0-9]([ \\t]?[0-9][ \\t]?[0-9])?|[0-9][ \\t]?[0-9])(.*)?(([0-9][ \\t]?[0-9]([ \\t]?[0-9][ \\t]?[0-9])?|[0-9][ \\t]?[0-9])))|([0-9][ \\t]?[0-9]([ \\t]?[0-9][ \\t]?[0-9])?|[0-9][ \\t]?[0-9])";
                    regex = "(((" + regex +  ")((.*?))?" + "(" + GetInstituteRegex(i) + "))|((" + GetInstituteRegex(i) + ")" + "((.*?)(\\n)?(.*?))?("+ regex +")))" ;
                    //System.out.println(1);
                    Pattern e_p = Pattern.compile(regex);
                    Matcher e_m = e_p.matcher(file);
                    if(e_m.find()){
                        //System.out.println(e_m.group());
                        String match = e_m.group();
                        int c=0;
                        String pattern = null;
                        String date=null,col=null;
                        for(int k=0;k<InstituteList.size();k++){
                            Pattern id_p = Pattern.compile(GetInstituteRegex(k));
                            Matcher id_m = id_p.matcher(match);
                            if(id_m.find()){
                                c++;
                                pattern = p.pattern();
                            }
                        }
                       //System.out.println(2); 
                        if(c==1){
                            String patstr = pattern;
                            patstr = patstr.replaceAll("\\Q(?i)\\E", "");
                            patstr = patstr.replaceAll("\\Q(?-i)\\E", "");
                            patstr = patstr.replaceAll("\\Q[ \\t]?\\E", "");
                            patstr = patstr.replaceAll("\\Q\\b\\E", "");
                            patstr = patstr.replaceAll("\\Q[+]?\\E", "+");
                            patstr = patstr.replaceAll("\\Q[&]?\\E", "&");
                            patstr = patstr.replaceAll("\\Q(,)?\\E", ",");
                            patstr = patstr.replaceAll("\\Q(\\.)?\\E", ".");
                            patstr = patstr.replaceAll("\\Q([(\\.)\\t,\\s]|(-|–|-))*?\\E", " ");
                            patstr = patstr.replaceAll("\\Q(-|–|-)?\\E", "-");
                            patstr = patstr.replaceAll("\\Q[(]?\\E", "");
                            patstr = patstr.replaceAll("\\Q[)]?\\E", "");
                            String[] liststr = str.split("\\|");
                            col = liststr[0];
                            String regexpat = "\\b(([0-9][ \\t]?[0-9]([ \\t]?[0-9][ \\t]?[0-9])?|[0-9][ \\t]?[0-9])(.*)?(([0-9][ \\t]?[0-9]([ \\t]?[0-9][ \\t]?[0-9])?|[0-9][ \\t]?[0-9])))|([0-9][ \\t]?[0-9]([ \\t]?[0-9][ \\t]?[0-9])?|[0-9][ \\t]?[0-9])\\b";
                            Pattern d_p = Pattern.compile(regex);
                            Matcher d_m = d_p.matcher(match);
                            if(d_m.find()){
                                date = d_m.group().replaceAll("(([A-Za-z])|([0-9][ \\t]?[0-9][ \\t]?[0-9][ \\t]?[0-9][ \\t]?[0-9][ \\t]?[0-9]?[ \\t]?[0-9]?[ \\t]?[0-9]?[ \\t]?[0-9]?[ \\t]?[0-9]?))", "");
                            }
                            
                            d_p = Pattern.compile("[0-9][ \\t]?[0-9][ \\t]?([0-9][ \\t]?[0-9])?");
                            d_m = d_p.matcher(date);
                            String start=null,end=null;
                            int cc = 0;
                            while(cc<2 && d_m.find()){
                                cc++;
                                //System.out.println(d_m.group());
                                if(cc==1)
                                    start = d_m.group();
                                else
                                    end = d_m.group();
                            }
                            
                            if(start!=null)
                            start = start.replaceAll("[ \\t]", "");
                            if(end!=null)
                            end = end.replaceAll("[ \\t]","");
                            int s=0,e=0;
                            if(start!=null){
                                s = Integer.parseInt(start);
                                if(s<100){
                                    s+=2000;
                                }
                            }
                            if(end!=null){
                                e = Integer.parseInt(end);
                                if(e<100){
                                    e+=2000;
                                }
                            }
                            //System.out.println(3);
                            res_data.AddInstitute(new UserResumeInstitute(College_name,s,e));
                        }
                        //System.out.println(4);
                    }
                    //System.out.println(5);
                    res_data.AddInstituteReference(College_name);
                    //System.out.println(6);
                    res_data = FindUserInstituteDegree(GetInstituteRegex(i),res_data);
                    //System.out.println(7);
                }
		}
		return res_data;
	}    
        
    public UserResumeData FindUserInstituteDegree(String pattern,UserResumeData res_data){
		int size = DegreeList.size();
		
		for(int i=0;i<size;i++){
			
			String Degree  = GetDegreeRegex(i);
			Pattern p = Pattern.compile("(((" +Degree + ")" + "((.*?)(\\n)?(.*?))?" + "("+ pattern+ "))" + "|" + "(("+ pattern+ ")" + "((.*?)(\\n)?(.*?))?" + "(" +Degree + ")))");
			String file = res_data.GetFileContent();
	        file = file.replaceAll("\\Q[\\E","");
	        file = file.replaceAll("\\Q]\\E","");
	        file = file.replaceAll("\\Q(\\E","");
	        file = file.replaceAll("\\Q)\\E","");
	        Matcher m = p.matcher(file);
	        //System.out.println("1");
            if(m.find()){
            	String match = null;
                String deg;
                String col;
                int count=0;
                //System.out.println("2");
                for(int j=0;j<size;j++){
                    Degree = GetDegreeRegex(j);
                    Pattern d_p = Pattern.compile(Degree);
                    Matcher d_m = d_p.matcher(m.group()); // get a matcher object
                    
                    if(d_m.find()){
                        count++;
                        String str = Degree;
                        str = str.replaceAll("\\Q(?i)\\E", "");
                        str = str.replaceAll("\\Q(?-i)\\E", "");
                        str = str.replaceAll("\\Q[ \\t]?\\E", "");
                        str = str.replaceAll("\\Q\\b\\E", "");
                        str = str.replaceAll("\\Q[+]?\\E", "+");
                        str = str.replaceAll("\\Q[&]?\\E", "&");
                        str = str.replaceAll("\\Q(,)?\\E", ",");
                        str = str.replaceAll("\\Q(\\.)?\\E", ".");
                        str = str.replaceAll("\\Q([(\\.)\\t,\\s]|(-|–|-))*?\\E", " ");
                        str = str.replaceAll("\\Q(-|–|-)?\\E", "-");
                        str = str.replaceAll("\\Q[(]?\\E", "");
                        str = str.replaceAll("\\Q[)]?\\E", "");
                        String[] list = str.split("\\|");
                        match = list[0];
                    }
                }
                //System.out.println("3");
                if(count==1){
                    int ccount=0;
                    deg = match;
                    for(int j=0;j<InstituteList.size();j++){
                        String College = GetInstituteRegex(j);
                        Pattern d_p = Pattern.compile(College);
                        Matcher d_m = d_p.matcher(m.group());
                        
                        if(d_m.find()){
                         ccount++;
                         String str = College;
                         str = str.replaceAll("\\Q(?i)\\E", "");
                         str = str.replaceAll("\\Q(?-i)\\E", "");
                         str = str.replaceAll("\\Q[ \\t]?\\E", "");
                         str = str.replaceAll("\\Q\\b\\E", "");
                         str = str.replaceAll("\\Q[+]?\\E", "+");
                         str = str.replaceAll("\\Q[&]?\\E", "&");
                         str = str.replaceAll("\\Q(,)?\\E", ",");
                         str = str.replaceAll("\\Q(\\.)?\\E", ".");
                         str = str.replaceAll("\\Q([(\\.)\\t,\\s]|(-|–|-))*?\\E", " ");
                         str = str.replaceAll("\\Q(-|–|-)?\\E", "-");
                         str = str.replaceAll("\\Q[(]?\\E", "");
                         str = str.replaceAll("\\Q[)]?\\E", "");
                         String[] list = str.split("\\|");
                         match = list[0];
                        }
                    }
                    //System.out.println("4");
                   if(ccount==1){
                       col = match;
                       int flag = 0;
                       for(int j=0;j<res_data.GetColDegList().size();j++){
                           if(res_data.GetColDegCollege(j).equals(col)){
                               flag=1;
                           }
                       }
                       if(flag==0){
                         res_data.AddColDeg(col, deg);
                         //System.out.println(col + "   " + deg);
                       }
                   }
                   //System.out.println("5");
                }
                //System.out.println("6");
            }
            //System.out.println("7");
            //System.out.println("Degree");
		}
		return res_data;
	}    
        
    public UserResumeData FindCompanyWorkExperience(String company,UserResumeData res_data){
		String company_name = company;
		String regex = "(" + company + ")" + "((.*?)(\\n)?(.*?))?(?i)\\b((j[ \\t]?a[ \\t]?n([uary ]{4,8})?|f[ \\t]?e[ \\t]?b([ruary ]{5,9})?|m[ \\t]?a[ \\t]?r([ch ]{2,4})?|a[ \\t]?p[ \\t]?r([il ]{2,4})?|m[ \\t]?a[ \\t]?y|j[ \\t]?u[ \\t]?n([e ]{1,2})?|j[ \\t]?u[ \\t]?l([y ]{1,2})?|a[ \\t]?u[ \\t]?g([ust ]{3,6})?|s[ \\t]?e[ \\t]?p([tembr ]{6,12})?|o[ \\t]?c[ \\t]?t([ober ]{4,8})?|(n[ \\t]?o[ \\t]?v|d[ \\t]?e[ \\t]?c)([embr ]{5,10})?)([^A-Za-z0-9]*?)([0-9][ \\t]?[0-9]([ \\t]?[0-9][ \\t]?[0-9])?)([^A-Za-z0-9]*?)(j[ \\t]?a[ \\t]?n([uary ]{4,8})?|f[ \\t]?e[ \\t]?b([ruary ]{5,9})?|m[ \\t]?a[ \\t]?r([ch ]{2,4})?|a[ \\t]?p[ \\t]?r([il ]{2,4})?|m[ \\t]?a[ \\t]?y|j[ \\t]?u[ \\t]?n([e ]{1,2})?|j[ \\t]?u[ \\t]?l([y ]{1,2})?|a[ \\t]?u[ \\t]?g([ust ]{3,6})?|s[ \\t]?e[ \\t]?p([tembr ]{6,12})?|o[ \\t]?c[ \\t]?t([ober ]{4,8})?|(n[ \\t]?o[ \\t]?v|d[ \\t]?e[ \\t]?c)([embr ]{5,10})?)[^A-Za-z0-9]*?([0-9][ \\t]?[0-9]([ \\t]?[0-9][ \\t]?[0-9])?)|(j[ \\t]?a[ \\t]?n([uary ]{4,8})?|f[ \\t]?e[ \\t]?b([ruary ]{5,9})?|m[ \\t]?a[ \\t]?r([ch ]{2,4})?|a[ \\t]?p[ \\t]?r([il ]{2,4})?|m[ \\t]?a[ \\t]?y|j[ \\t]?u[ \\t]?n([e ]{1,2})?|j[ \\t]?u[ \\t]?l([y ]{1,2})?|a[ \\t]?u[ \\t]?g([ust ]{3,6})?|s[ \\t]?e[ \\t]?p([tembr ]{6,12})?|o[ \\t]?c[ \\t]?t([ober ]{4,8})?|(n[ \\t]?o[ \\t]?v|d[ \\t]?e[ \\t]?c)([embr ]{5,10})?)[^A-Za-z0-9]*?([0-9][ \\t]?[0-9]([ \\t]?[0-9][ \\t]?[0-9])?)[^A-Za-z0-9]*?(c[ \\t]?u[ \\t]?r[ \\t]?r[ \\t]?e[ \\t]?n[ \\t]?t[ \\t]?(l[ \\t]?y)|p[ \\t]?r[ \\t]?e[ \\t]?s[ \\t]?e[ \\t]?n[ \\t]?t|o[ \\t]?n[ \\t]?w[ \\t]?a[ \\t]?r[ \\t]?d[ \\t]?(s)?|t[ \\t]?i[ \\t]?l[ \\t]?l[ \\t]*d[ \\t]?a[ \\t]?t[ \\t]?e|t[ \\t]?o[ \\t]*d[ \\t]?a[ \\t]?t[ \\t]?e|t[ \\t]?i[ \\t]?l[ \\t]?l[ \\t]*n[ \\t]?o[ \\t]?w)|(?i)(s[ \\t]?i[ \\t]?n[ \\t]?c[ \\t]?e|t[ \\t]?i[ \\t]?l[ \\t]?l)[^A-Za-z0-9]*?(j[ \\t]?a[ \\t]?n([uary ]{4,8})?|f[ \\t]?e[ \\t]?b([ruary ]{5,9})?|m[ \\t]?a[ \\t]?r([ch ]{2,4})?|a[ \\t]?p[ \\t]?r([il ]{2,4})?|m[ \\t]?a[ \\t]?y|j[ \\t]?u[ \\t]?n([e ]{1,2})?|j[ \\t]?u[ \\t]?l([y ]{1,2})?|a[ \\t]?u[ \\t]?g([ust ]{3,6})?|s[ \\t]?e[ \\t]?p([tembr ]{6,12})?|o[ \\t]?c[ \\t]?t([ober ]{4,8})?|(n[ \\t]?o[ \\t]?v|d[ \\t]?e[ \\t]?c)([embr ]{5,10})?)[^A-Za-z0-9]*?([0-9][ \\t]?[0-9]([ \\t]?[0-9][ \\t]?[0-9])?)|(j[ \\t]?a[ \\t]?n([uary ]{4,8})?|f[ \\t]?e[ \\t]?b([ruary ]{5,9})?|m[ \\t]?a[ \\t]?r([ch ]{2,4})?|a[ \\t]?p[ \\t]?r([il ]{2,4})?|m[ \\t]?a[ \\t]?y|j[ \\t]?u[ \\t]?n([e ]{1,2})?|j[ \\t]?u[ \\t]?l([y ]{1,2})?|a[ \\t]?u[ \\t]?g([ust ]{3,6})?|s[ \\t]?e[ \\t]?p([tembr ]{6,12})?|o[ \\t]?c[ \\t]?t([ober ]{4,8})?|(n[ \\t]?o[ \\t]?v|d[ \\t]?e[ \\t]?c)([embr ]{5,10})?)[^A-Za-z0-9]*?(j[ \\t]?a[ \\t]?n([uary ]{4,8})?|f[ \\t]?e[ \\t]?b([ruary ]{5,9})?|m[ \\t]?a[ \\t]?r([ch ]{2,4})?|a[ \\t]?p[ \\t]?r([il ]{2,4})?|m[ \\t]?a[ \\t]?y|j[ \\t]?u[ \\t]?n([e ]{1,2})?|j[ \\t]?u[ \\t]?l([y ]{1,2})?|a[ \\t]?u[ \\t]?g([ust ]{3,6})?|s[ \\t]?e[ \\t]?p([tembr ]{6,12})?|o[ \\t]?c[ \\t]?t([ober ]{4,8})?|(n[ \\t]?o[ \\t]?v|d[ \\t]?e[ \\t]?c)([embr ]{5,10})?)[^A-Za-z0-9]*?([0-9][ \\t]?[0-9]([ \\t]?[0-9][ \\t]?[0-9])?)|([0-9][ \\t]?[0-9][ \\t]?[0-9][ \\t]?[0-9])[^A-Za-z0-9]*?([0-9][ \\t]?[0-9][ \\t]?[0-9][ \\t]?[0-9])|([0-9][ \\t]?[0-9]?[ \\t]?[0-9]?)[^A-Za-z0-9]*?(m[ \\t]?o[ \\t]?n[ \\t]?t[ \\t]?h[ \\t]?(s)?|y[ \\t]?e[ \\t]?a[ \\t]?r[ \\t]?(s)?)|(j[ \\t]?a[ \\t]?n([uary ]{4,8})?|f[ \\t]?e[ \\t]?b([ruary ]{5,9})?|m[ \\t]?a[ \\t]?r([ch ]{2,4})?|a[ \\t]?p[ \\t]?r([il ]{2,4})?|m[ \\t]?a[ \\t]?y|j[ \\t]?u[ \\t]?n([e ]{1,2})?|j[ \\t]?u[ \\t]?l([y ]{1,2})?|a[ \\t]?u[ \\t]?g([ust ]{3,6})?|s[ \\t]?e[ \\t]?p([tembr ]{6,12})?|o[ \\t]?c[ \\t]?t([ober ]{4,8})?|(n[ \\t]?o[ \\t]?v|d[ \\t]?e[ \\t]?c)([embr ]{5,10})?)[^A-Za-z0-9]*?([0-9][ \\t]?[0-9][ \\t]?([0-9][ \\t]?[0-9])?))\\b";
		
        Pattern p = Pattern.compile(regex);
        
        String file = res_data.GetFileContent(); 
        file = file.replaceAll("\\Q[\\E","");
        file = file.replaceAll("\\Q]\\E","");
        file = file.replaceAll("\\Q(\\E","");
        file = file.replaceAll("\\Q)\\E","");
        Matcher m = p.matcher(file);

        
       
        while(m.find()) {
        	
        	Pattern w_p = Pattern.compile("(?i)\\b((j[ \\t]?a[ \\t]?n([uary ]{4,8})?|f[ \\t]?e[ \\t]?b([ruary ]{5,9})?|m[ \\t]?a[ \\t]?r([ch ]{2,4})?|a[ \\t]?p[ \\t]?r([il ]{2,4})?|m[ \\t]?a[ \\t]?y|j[ \\t]?u[ \\t]?n([e ]{1,2})?|j[ \\t]?u[ \\t]?l([y ]{1,2})?|a[ \\t]?u[ \\t]?g([ust ]{3,6})?|s[ \\t]?e[ \\t]?p([tembr ]{6,12})?|o[ \\t]?c[ \\t]?t([ober ]{4,8})?|(n[ \\t]?o[ \\t]?v|d[ \\t]?e[ \\t]?c)([embr ]{5,10})?)([^A-Za-z0-9]*?)([0-9][ \\t]?[0-9]([ \\t]?[0-9][ \\t]?[0-9])?)([^A-Za-z0-9]*?)(j[ \\t]?a[ \\t]?n([uary ]{4,8})?|f[ \\t]?e[ \\t]?b([ruary ]{5,9})?|m[ \\t]?a[ \\t]?r([ch ]{2,4})?|a[ \\t]?p[ \\t]?r([il ]{2,4})?|m[ \\t]?a[ \\t]?y|j[ \\t]?u[ \\t]?n([e ]{1,2})?|j[ \\t]?u[ \\t]?l([y ]{1,2})?|a[ \\t]?u[ \\t]?g([ust ]{3,6})?|s[ \\t]?e[ \\t]?p([tembr ]{6,12})?|o[ \\t]?c[ \\t]?t([ober ]{4,8})?|(n[ \\t]?o[ \\t]?v|d[ \\t]?e[ \\t]?c)([embr ]{5,10})?)[^A-Za-z0-9]*?([0-9][ \\t]?[0-9]([ \\t]?[0-9][ \\t]?[0-9])?)|(j[ \\t]?a[ \\t]?n([uary ]{4,8})?|f[ \\t]?e[ \\t]?b([ruary ]{5,9})?|m[ \\t]?a[ \\t]?r([ch ]{2,4})?|a[ \\t]?p[ \\t]?r([il ]{2,4})?|m[ \\t]?a[ \\t]?y|j[ \\t]?u[ \\t]?n([e ]{1,2})?|j[ \\t]?u[ \\t]?l([y ]{1,2})?|a[ \\t]?u[ \\t]?g([ust ]{3,6})?|s[ \\t]?e[ \\t]?p([tembr ]{6,12})?|o[ \\t]?c[ \\t]?t([ober ]{4,8})?|(n[ \\t]?o[ \\t]?v|d[ \\t]?e[ \\t]?c)([embr ]{5,10})?)[^A-Za-z0-9]*?([0-9][ \\t]?[0-9]([ \\t]?[0-9][ \\t]?[0-9])?)[^A-Za-z0-9]*?(c[ \\t]?u[ \\t]?r[ \\t]?r[ \\t]?e[ \\t]?n[ \\t]?t[ \\t]?(l[ \\t]?y)|p[ \\t]?r[ \\t]?e[ \\t]?s[ \\t]?e[ \\t]?n[ \\t]?t|o[ \\t]?n[ \\t]?w[ \\t]?a[ \\t]?r[ \\t]?d[ \\t]?(s)?|t[ \\t]?i[ \\t]?l[ \\t]?l[ \\t]*d[ \\t]?a[ \\t]?t[ \\t]?e|t[ \\t]?o[ \\t]*d[ \\t]?a[ \\t]?t[ \\t]?e|t[ \\t]?i[ \\t]?l[ \\t]?l[ \\t]*n[ \\t]?o[ \\t]?w)|(?i)(s[ \\t]?i[ \\t]?n[ \\t]?c[ \\t]?e|t[ \\t]?i[ \\t]?l[ \\t]?l)[^A-Za-z0-9]*?(j[ \\t]?a[ \\t]?n([uary ]{4,8})?|f[ \\t]?e[ \\t]?b([ruary ]{5,9})?|m[ \\t]?a[ \\t]?r([ch ]{2,4})?|a[ \\t]?p[ \\t]?r([il ]{2,4})?|m[ \\t]?a[ \\t]?y|j[ \\t]?u[ \\t]?n([e ]{1,2})?|j[ \\t]?u[ \\t]?l([y ]{1,2})?|a[ \\t]?u[ \\t]?g([ust ]{3,6})?|s[ \\t]?e[ \\t]?p([tembr ]{6,12})?|o[ \\t]?c[ \\t]?t([ober ]{4,8})?|(n[ \\t]?o[ \\t]?v|d[ \\t]?e[ \\t]?c)([embr ]{5,10})?)[^A-Za-z0-9]*?([0-9][ \\t]?[0-9]([ \\t]?[0-9][ \\t]?[0-9])?)|(j[ \\t]?a[ \\t]?n([uary ]{4,8})?|f[ \\t]?e[ \\t]?b([ruary ]{5,9})?|m[ \\t]?a[ \\t]?r([ch ]{2,4})?|a[ \\t]?p[ \\t]?r([il ]{2,4})?|m[ \\t]?a[ \\t]?y|j[ \\t]?u[ \\t]?n([e ]{1,2})?|j[ \\t]?u[ \\t]?l([y ]{1,2})?|a[ \\t]?u[ \\t]?g([ust ]{3,6})?|s[ \\t]?e[ \\t]?p([tembr ]{6,12})?|o[ \\t]?c[ \\t]?t([ober ]{4,8})?|(n[ \\t]?o[ \\t]?v|d[ \\t]?e[ \\t]?c)([embr ]{5,10})?)[^A-Za-z0-9]*?(j[ \\t]?a[ \\t]?n([uary ]{4,8})?|f[ \\t]?e[ \\t]?b([ruary ]{5,9})?|m[ \\t]?a[ \\t]?r([ch ]{2,4})?|a[ \\t]?p[ \\t]?r([il ]{2,4})?|m[ \\t]?a[ \\t]?y|j[ \\t]?u[ \\t]?n([e ]{1,2})?|j[ \\t]?u[ \\t]?l([y ]{1,2})?|a[ \\t]?u[ \\t]?g([ust ]{3,6})?|s[ \\t]?e[ \\t]?p([tembr ]{6,12})?|o[ \\t]?c[ \\t]?t([ober ]{4,8})?|(n[ \\t]?o[ \\t]?v|d[ \\t]?e[ \\t]?c)([embr ]{5,10})?)[^A-Za-z0-9]*?([0-9][ \\t]?[0-9]([ \\t]?[0-9][ \\t]?[0-9])?)|([0-9][ \\t]?[0-9][ \\t]?[0-9][ \\t]?[0-9])[^A-Za-z0-9]*?([0-9][ \\t]?[0-9][ \\t]?[0-9][ \\t]?[0-9])|([0-9][ \\t]?[0-9]?[ \\t]?[0-9]?)[^A-Za-z0-9]*?(m[ \\t]?o[ \\t]?n[ \\t]?t[ \\t]?h[ \\t]?(s)?|y[ \\t]?e[ \\t]?a[ \\t]?r[ \\t]?(s)?)|(j[ \\t]?a[ \\t]?n([uary ]{4,8})?|f[ \\t]?e[ \\t]?b([ruary ]{5,9})?|m[ \\t]?a[ \\t]?r([ch ]{2,4})?|a[ \\t]?p[ \\t]?r([il ]{2,4})?|m[ \\t]?a[ \\t]?y|j[ \\t]?u[ \\t]?n([e ]{1,2})?|j[ \\t]?u[ \\t]?l([y ]{1,2})?|a[ \\t]?u[ \\t]?g([ust ]{3,6})?|s[ \\t]?e[ \\t]?p([tembr ]{6,12})?|o[ \\t]?c[ \\t]?t([ober ]{4,8})?|(n[ \\t]?o[ \\t]?v|d[ \\t]?e[ \\t]?c)([embr ]{5,10})?)[^A-Za-z0-9]*?([0-9][ \\t]?[0-9][ \\t]?([0-9][ \\t]?[0-9])?))\\b");
        	Matcher w_m = w_p.matcher(m.group());            
            if(w_m.find()){

                String match = w_m.group();

                String StartMn = "";
                String StartYr = "";
                String EndMn = "";
                String EndYr = "";
                int sm,em,sy,ey;
                String yrmnth=null;
                if(match.matches(".*([0-9]{5,12}).*")){
                    StartMn = "Wrong Hit";
                }
                else if(match.matches(".*(?i)(t[ \\t]?i[ \\t]?l[ \\t]?l)[ \\t]*(d[ \\t]?a[ \\t]?t[ \\t]?e).*") || match.matches(".*(?i)(t[ \\t]?o)[ \\t]*(d[ \\t]?a[ \\t]?t[ \\t]?e).*") || match.matches(".*(?i)(p[ \\t]?r[ \\t]?e[ \\t]?s[ \\t]?e[ \\t]?n[ \\t]?t).*") || match.matches(".*(?i)(o[ \\t]?n[ \\t]?w[ \\t]?a[ \\t]?r[ \\t]?d[ \\t]?(s)?).*") || match.matches(".*(?i)(s[ \\t]?i[ \\t]?n[ \\t]?c[ \\t]?e).*") || match.matches(".*(?i)(c[ \\t]?u[ \\t]?r[ \\t]?r[ \\t]?e[ \\t]?n[ \\t]?t(ly)).*")){
                    Pattern month = Pattern.compile("(?i)(j[ \\t]?a[ \\t]?n([uary ]{4,8})?|f[ \\t]?e[ \\t]?b([ruary ]{5,9})?|m[ \\t]?a[ \\t]?r([ch ]{2,4})?|a[ \\t]?p[ \\t]?r([il ]{2,4})?|m[ \\t]?a[ \\t]?y|j[ \\t]?u[ \\t]?n([e ]{1,2})?|j[ \\t]?u[ \\t]?l([y ]{1,2})?|a[ \\t]?u[ \\t]?g([ust ]{3,6})?|s[ \\t]?e[ \\t]?p([tembr ]{6,12})?|o[ \\t]?c[ \\t]?t([ober ]{4,8})?|(n[ \\t]?o[ \\t]?v|d[ \\t]?e[ \\t]?c)([embr ]{5,10})?)");
                    Matcher m_matcher = month.matcher(match);
                    
                    if(m_matcher.find()){
                         StartMn = m_matcher.group().replaceAll("[ \\t]", "");
                    }
                    
                    
                    Pattern year = Pattern.compile("([0-9][ \\t]?[0-9][ \\t]?([0-9][ \\t]?[0-9])?)");
                    Matcher y_matcher = year.matcher(match);
                    
                    if(y_matcher.find()){
                         StartYr = y_matcher.group().replaceAll("[ \\t]", "");
                    }
                    
                    EndMn = "Current";
                    EndYr = "Current";
                   
                }
                else if(match.matches(".*(?i)(t[ \\t]?i[ \\t]?l[ \\t]?l).*")){                    
                    Pattern month = Pattern.compile("(?i)(j[ \\t]?a[ \\t]?n([uary ]{4,8})?|f[ \\t]?e[ \\t]?b([ruary ]{5,9})?|m[ \\t]?a[ \\t]?r([ch ]{2,4})?|a[ \\t]?p[ \\t]?r([il ]{2,4})?|m[ \\t]?a[ \\t]?y|j[ \\t]?u[ \\t]?n([e ]{1,2})?|j[ \\t]?u[ \\t]?l([y ]{1,2})?|a[ \\t]?u[ \\t]?g([ust ]{3,6})?|s[ \\t]?e[ \\t]?p([tembr ]{6,12})?|o[ \\t]?c[ \\t]?t([ober ]{4,8})?|(n[ \\t]?o[ \\t]?v|d[ \\t]?e[ \\t]?c)([embr ]{5,10})?)");
                    Matcher m_matcher = month.matcher(match);
                    
                    if(m_matcher.find()){
                         EndMn = m_matcher.group().replaceAll("[ \\t]", "");
                    }
                    
                    Pattern year = Pattern.compile("([0-9][ \\t]?[0-9][ \\t]?([0-9][ \\t]?[0-9])?)");
                    Matcher y_matcher = year.matcher(match);
                    
                    if(y_matcher.find()){
                         EndYr = y_matcher.group().replaceAll("[ \\t]", "");
                    }
                    
                    StartMn = "Current";
                    StartYr = "Current";
                    
                }
                else if(match.matches(".*(?i)(y[ \\t]?e[ \\t]?a[ \\t]?r[ \\t]?(s)?).*") || match.matches(".*(?i)(m[ \\t]?o[ \\t]?n[ \\t]?t[ \\t]?h[ \\t]?(s)?).*")){
                    Pattern year = Pattern.compile("([0-9][ \\t]?[0-9]?[ \\t]?[0-9]?)");
                    Matcher y_matcher = year.matcher(match);
                    
                    if(y_matcher.find()){
                        yrmnth = y_matcher.group().replaceAll("[ \\t]", "");
                    }
                    
                    Pattern month = Pattern.compile("(?i)\\b(m[ \\t]?o[ \\t]?n[ \\t]?t[ \\t]?h[ \\t]?(s)?|y[ \\t]?e[ \\t]?a[ \\t]?r[ \\t]?(s)?)\\b");
                    Matcher m_matcher = month.matcher(match);
                    
                    if(m_matcher.find()){
                        if(m_matcher.group().replaceAll("[ \\t]", "").matches("month(s)?")){
                        	yrmnth += " months";
                        }
                        else{
                        	yrmnth += " years";
                        }
                    }
                }
                else{
                    Pattern month = Pattern.compile("(?i)\\b(j[ \\t]?a[ \\t]?n[ \\t]?(u[ \\t]?a[ \\t]?r[ \\t]?y)?|f[ \\t]?e[ \\t]?b[ \\t]?(r[ \\t]?u[ \\t]?a[ \\t]?r[ \\t]?y)?|m[ \\t]?a[ \\t]?r[ \\t]?(c[ \\t]?h)?|a[ \\t]?p[ \\t]?r[ \\t]?(i[ \\t]?l)?|m[ \\t]?a[ \\t]?y|j[ \\t]?u[ \\t]?n[ \\t]?(e)?|j[ \\t]?u[ \\t]?l[ \\t]?(y)?|a[ \\t]?u[ \\t]?g[ \\t]?(u[ \\t]?s[ \\t]?t)?|s[ \\t]?e[ \\t]?p[ \\t]?(t[ \\t]?e[ \\t]?m[ \\t]?b[ \\t]?e[ \\t]?r)?|o[ \\t]?c[ \\t]?t[ \\t]?(o[ \\t]?b[ \\t]?e[ \\t]?r)?|n[ \\t]?o[ \\t]?v[ \\t]?(e[ \\t]?m[ \\t]?b[ \\t]?e[ \\t]?r)?|d[ \\t]?e[ \\t]?c[ \\t]?(e[ \\t]?m[ \\t]?b[ \\t]?e[ \\t]?r)?)\\b");
                    Matcher m_matcher = month.matcher(match);
                    
                    if(m_matcher.find()){
                         StartMn = m_matcher.group().replaceAll("[ \\t]", "");
                    }else{
                        StartMn = "Current";
                    }
                    
                    if(m_matcher.find()){
                        EndMn = m_matcher.group().replaceAll("[ \\t]", "");
                    }else{
                        EndMn = "Current";
                    }
                    
                    Pattern year = Pattern.compile("([0-9][ \\t]?[0-9][ \\t]?([0-9][ \\t]?[0-9])?)");
                    Matcher y_matcher = year.matcher(match);
                    
                    if(y_matcher.find()){
                         StartYr = y_matcher.group().replaceAll("[ \\t]", "");
                    }
                    
                    if(y_matcher.find()){
                        EndYr = y_matcher.group().replaceAll("[ \\t]", "");
                    }
                    else if(EndMn=="Current"){
                        EndYr = "Current";
                    }
                    else{
                        EndYr = StartYr;
                    }
                }                
                
                if(!StartMn.equals("Wrong Hit")){
                String str = company_name;
                str = str.replaceAll("\\Q(?i)\\E", "");
                str = str.replaceAll("\\Q(?-i)\\E", "");
                str = str.replaceAll("\\Q[ \\t]?\\E", "");
                str = str.replaceAll("\\Q\\b\\E", "");
                str = str.replaceAll("\\Q[+]?\\E", "+");
                str = str.replaceAll("\\Q[&]?\\E", "&");
                str = str.replaceAll("\\Q(,)?\\E", ",");
                str = str.replaceAll("\\Q(\\.)?\\E", ".");
                str = str.replaceAll("\\Q([(\\.)\\t,\\s]|(-|–|-))*?\\E", " ");
                str = str.replaceAll("\\Q(-|–|-)?\\E", "-");
                str = str.replaceAll("\\Q[(]?\\E", "");
                str = str.replaceAll("\\Q[)]?\\E", "");
                String[] list = str.split("\\|");
                String internship_regex = "(?i)\\b("+ "i[ \\t]?n[ \\t]?t[ \\t]?e[ \\t]?r[ \\t]?n[ \\t]?s[ \\t]?h[ \\t]?i[ \\t]?p[ \\t]?s|i[ \\t]?n[ \\t]?t[ \\t]?e[ \\t]?r[ \\t]?n[ \\t]?s[ \\t]?h[ \\t]?i[ \\t]?p|i[ \\t]?n[ \\t]?t[ \\t]?e[ \\t]?r[ \\t]?n" + ")\\b" + "((.*?)(\\n)?(.*?))?" + "(" + company_name + ")" + "|" + "(?i)(" + company_name + ")" + "((.*?)(\\n)?(.*?))?" + "(?i)\\b("+ "i[ \\t]?n[ \\t]?t[ \\t]?e[ \\t]?r[ \\t]?n[ \\t]?s[ \\t]?h[ \\t]?i[ \\t]?p[ \\t]?s|i[ \\t]?n[ \\t]?t[ \\t]?e[ \\t]?r[ \\t]?n[ \\t]?s[ \\t]?h[ \\t]?i[ \\t]?p|i[ \\t]?n[ \\t]?t[ \\t]?e[ \\t]?r[ \\t]?n" + ")\\b";
                Pattern i_p = Pattern.compile(internship_regex);
                Matcher i_m = i_p.matcher(res_data.GetFileContent());
                
                if(i_m.find()){
                	if(yrmnth==null)
                		res_data.AddCompany(new UserResumeCompany(list[0],StartMn + "/" +  StartYr + "-" + EndMn + "/" + EndYr,true));
                	else
                		res_data.AddCompany(new UserResumeCompany(list[0],yrmnth,true));
                }
                else{
                	if(yrmnth==null)
                		res_data.AddCompany(new UserResumeCompany(list[0],StartMn + "/" +  StartYr + "-" + EndMn + "/" + EndYr,false));
                	else
                		res_data.AddCompany(new UserResumeCompany(list[0],yrmnth,false));
                }  
                }
        
            }   
        
        }
        
        return res_data;
    }    
        
    public UserResumeData FindUserCompanyData(UserResumeData res_data){
		int size = CompanyList.size();
		 
		
		for(int i=0;i<size;i++){
			Pattern p = Pattern.compile(GetCompanyRegex(i));
			String file = res_data.GetFileContent();
	        file = file.replaceAll("\\Q[\\E","");
	        file = file.replaceAll("\\Q]\\E","");
	        file = file.replaceAll("\\Q(\\E","");
	        file = file.replaceAll("\\Q)\\E","");
	        Matcher m = p.matcher(file);
			
			int count = 0;
			//System.out.println(p.pattern());
            if(m.find()) {
                count++;
            }           
            if(count>0){
            	String str = p.pattern();
            	str = str.replaceAll("\\Q(?i)\\E", "");
                str = str.replaceAll("\\Q(?-i)\\E", "");
                str = str.replaceAll("\\Q[ \\t]?\\E", "");
                str = str.replaceAll("\\Q\\b\\E", "");
                str = str.replaceAll("\\Q[+]?\\E", "+");
                str = str.replaceAll("\\Q[&]?\\E", "&");
                str = str.replaceAll("\\Q(,)?\\E", ",");
                str = str.replaceAll("\\Q(\\.)?\\E", ".");
                str = str.replaceAll("\\Q([(\\.)\\t,\\s]|(-|–|-))*?\\E", " ");
                str = str.replaceAll("\\Q(-|–|-)?\\E", "-");
                str = str.replaceAll("\\Q[(]?\\E", "");
                str = str.replaceAll("\\Q[)]?\\E", "");
                String[] list = str.split("\\|");
                res_data.AddCompanyReference(list[0]);
                //System.out.println(GetCompanyRegex(i));
            	res_data = FindCompanyWorkExperience(GetCompanyRegex(i),res_data);
            }
         
		}
		return res_data;
	}    
        
    public UserResumeData FindUserEmailId(UserResumeData res_data){
		String pattern = "\\b[A-Za-z0-9._%+-–-]+@[A-Za-z0-9.-–-]+\\.[A-Za-z]{2,6}\\b";
        Pattern p = Pattern.compile(pattern);
        String file = res_data.GetFileContent();
        file = file.replaceAll("\\Q[\\E","");
        file = file.replaceAll("\\Q]\\E","");
        file = file.replaceAll("\\Q(\\E","");
        file = file.replaceAll("\\Q)\\E","");
        Matcher m = p.matcher(file);
        
        if(m.find()){
        	res_data.SetEmailId(m.group());
        }
        else{
        	res_data.SetEmailId("Not Found");
        }
        
        return res_data;
	}    
        
    public UserResumeData FindUserPhoneNo(UserResumeData res_data){
		String pattern = "[\\+[0-9][0-9]]?[ ,-–-\\t]?[0-9][ ,-–-\\t]?[0-9][ ,-–-\\t]?[0-9][ ,-–-\\t]?[0-9][ ,-–-\\t]?[0-9][ ,-–-\\t]?[0-9][ ,-–-\\t]?[0-9][ ,-–-\\t]?[0-9][ ,-–-\\t]?[0-9][ ,-–-\\t]?[0-9]";
        Pattern p = Pattern.compile(pattern);
        String file = res_data.GetFileContent();
        file = file.replaceAll("\\Q[\\E","");
        file = file.replaceAll("\\Q]\\E","");
        file = file.replaceAll("\\Q(\\E","");
        file = file.replaceAll("\\Q)\\E","");
        Matcher m = p.matcher(file);
        
        if(m.find()){
        	res_data.SetPhoneNo(m.group());
        }
        else{
        	res_data.SetPhoneNo("Not Found");
        }
        
        return res_data;
	}    
        
    public UserResumeData FindUserDegree(UserResumeData res_data){
		int size = DegreeList.size();
		
		for(int i=0;i<size;i++){
			Pattern p = Pattern.compile(GetDegreeRegex(i));
			String file = res_data.GetFileContent();
	        file = file.replaceAll("\\Q[\\E","");
	        file = file.replaceAll("\\Q]\\E","");
	        file = file.replaceAll("\\Q(\\E","");
	        file = file.replaceAll("\\Q)\\E","");
	        Matcher m = p.matcher(file);
			
			int count = 0;
			//System.out.println(p.pattern());
            if(m.find()) {
                count++;
            }           
            if(count>0){
            	String str = p.pattern();
            	str = str.replaceAll("\\Q(?i)\\E", "");
                str = str.replaceAll("\\Q(?-i)\\E", "");
                str = str.replaceAll("\\Q[ \\t]?\\E", "");
                str = str.replaceAll("\\Q\\b\\E", "");
                str = str.replaceAll("\\Q[+]?\\E", "+");
                str = str.replaceAll("\\Q[&]?\\E", "&");
                str = str.replaceAll("\\Q(,)?\\E", ",");
                str = str.replaceAll("\\Q(\\.)?\\E", ".");
                str = str.replaceAll("\\Q([(\\.)\\t,\\s]|(-|–|-))*?\\E", " ");
                str = str.replaceAll("\\Q(-|–|-)?\\E", "-");
                str = str.replaceAll("\\Q[(]?\\E", "");
                str = str.replaceAll("\\Q[)]?\\E", "");
                String[] list = str.split("\\|");
                res_data.AddDegree(list[0]);
                //System.out.println(list[0]);
                
                for(int j=0;j<MajorList.size();j++){
                    String Major = GetMajorRegex(j);
                    String regex = "(((" + Major + ")" + "((.*?)(\\n)?(.*?))?" + "(" + GetDegreeRegex(i) + "))" + "|" + "((" + GetDegreeRegex(i) + ")" + "((.*?))?" + "("+ Major + ")))";
                    
                    Pattern m_p = Pattern.compile(regex);
                    Matcher m_m = m_p.matcher(res_data.GetFileContent());
                    
                    //System.out.println("Start: "+ m_p.pattern() + "\n");
                    
                    if(m_m.find()){
                    	String match = m_m.group();
                    	String deg = null, mjr = null;
                    	int c=0;
                    	//System.out.println("degree");
                    	for(int k=0;k<DegreeList.size();k++){
                            String Degree = GetDegreeRegex(k);
                            Pattern d_p = Pattern.compile(Degree);
                            Matcher d_m = d_p.matcher(match);
                            
                            if(d_m.find()){
                                c++;
                                String degstr = Degree;
                                degstr = degstr.replaceAll("\\Q(?i)\\E", "");
                                degstr = degstr.replaceAll("\\Q(?-i)\\E", "");
                                degstr = degstr.replaceAll("\\Q[ \\t]?\\E", "");
                                degstr = degstr.replaceAll("\\Q\\b\\E", "");
                                degstr = degstr.replaceAll("\\Q[+]?\\E", "+");
                                degstr = degstr.replaceAll("\\Q[&]?\\E", "&");
                                degstr = degstr.replaceAll("\\Q(,)?\\E", ",");
                                degstr = degstr.replaceAll("\\Q(\\.)?\\E", ".");
                                degstr = degstr.replaceAll("\\Q([(\\.)\\t,\\s]|(-|–|-))*?\\E", " ");
                                degstr = degstr.replaceAll("\\Q(-|–|-)?\\E", "-");
                                degstr = degstr.replaceAll("\\Q[(]?\\E", "");
                                degstr = degstr.replaceAll("\\Q[)]?\\E", "");
                                String[] deglist = degstr.split("\\|");
                                deg = deglist[0];
                            }
                        }
                        
                        if(c==1){
                        Pattern d_p = Pattern.compile(Major);
                        Matcher d_m = d_p.matcher(match);
                        
                        if(d_m.find()){
                            String mjrstr = Major;
                            mjrstr = mjrstr.replaceAll("\\Q(?i)\\E", "");
                            mjrstr = mjrstr.replaceAll("\\Q(?-i)\\E", "");
                            mjrstr = mjrstr.replaceAll("\\Q[ \\t]?\\E", "");
                            mjrstr = mjrstr.replaceAll("\\Q\\b\\E", "");
                            mjrstr = mjrstr.replaceAll("\\Q[+]?\\E", "+");
                            mjrstr = mjrstr.replaceAll("\\Q[&]?\\E", "&");
                            mjrstr = mjrstr.replaceAll("\\Q(,)?\\E", ",");
                            mjrstr = mjrstr.replaceAll("\\Q(\\.)?\\E", ".");
                            mjrstr = mjrstr.replaceAll("\\Q([(\\.)\\t,\\s]|(-|–|-))*?\\E", " ");
                            mjrstr = mjrstr.replaceAll("\\Q(-|–|-)?\\E", "-");
                            mjrstr = mjrstr.replaceAll("\\Q[(]?\\E", "");
                            mjrstr = mjrstr.replaceAll("\\Q[)]?\\E", "");
                            String[] mjrlist = mjrstr.split("\\|");
                            mjr = mjrlist[0]; 
                        }
                        }
                    
                    if(mjr!=null)
                    	res_data.AddMajor(deg, mjr);
                    }
                }
            }
		}
		return res_data;
	}    
        
    public UserResumeData FindUserSkill(UserResumeData res_data){
		int size = SkillList.size();
		//System.out.println("Finding Skills");
		for(int i=0;i<size;i++){
			Pattern p = Pattern.compile(GetSkillRegex(i));
			String file = res_data.GetFileContent();
	        file = file.replaceAll("\\Q[\\E","");
	        file = file.replaceAll("\\Q]\\E","");
	        file = file.replaceAll("\\Q(\\E","");
	        file = file.replaceAll("\\Q)\\E","");
	        Matcher m = p.matcher(file);

			//System.out.println(p.pattern());
	        //System.out.println(p.pattern());
			if(m.find()){
				String str = p.pattern();
				str = str.replaceAll("\\Q(?i)\\E", "");
                str = str.replaceAll("\\Q(?-i)\\E", "");
                str = str.replaceAll("\\Q[ \\t]?\\E", "");
                str = str.replaceAll("\\Q\\b\\E", "");
                str = str.replaceAll("\\Q[+]?\\E", "+");
                str = str.replaceAll("\\Q[&]?\\E", "&");
                str = str.replaceAll("\\Q(,)?\\E", ",");
                str = str.replaceAll("\\Q(\\.)?\\E", ".");
                str = str.replaceAll("\\Q([(\\.)\\t,\\s]|(-|–|-))*?\\E", " ");
                str = str.replaceAll("\\Q(-|–|-)?\\E", "-");
                str = str.replaceAll("\\Q[(]?\\E", "");
                str = str.replaceAll("\\Q[)]?\\E", "");
                String[] list = str.split("\\|");
                res_data.AddSkill(list[0]);
            }           
		}
		return res_data;
	}    
    
    public UserResumeData FindUserPosition(UserResumeData res_data){
		int size = PositionList.size();
		//System.out.println("Finding Skills");
		for(int i=0;i<size;i++){
			Pattern p = Pattern.compile(GetPositionRegex(i));
			String file = res_data.GetFileContent();
	        file = file.replaceAll("\\Q[\\E","");
	        file = file.replaceAll("\\Q]\\E","");
	        file = file.replaceAll("\\Q(\\E","");
	        file = file.replaceAll("\\Q)\\E","");
	        Matcher m = p.matcher(file);

			//System.out.println(p.pattern());
	        //System.out.println(p.pattern());
			if(m.find()){
				String str = p.pattern();
				str = str.replaceAll("\\Q(?i)\\E", "");
                str = str.replaceAll("\\Q(?-i)\\E", "");
                str = str.replaceAll("\\Q[ \\t]?\\E", "");
                str = str.replaceAll("\\Q\\b\\E", "");
                str = str.replaceAll("\\Q[+]?\\E", "+");
                str = str.replaceAll("\\Q[&]?\\E", "&");
                str = str.replaceAll("\\Q(,)?\\E", ",");
                str = str.replaceAll("\\Q(\\.)?\\E", ".");
                str = str.replaceAll("\\Q([(\\.)\\t,\\s]|(-|–|-))*?\\E", " ");
                str = str.replaceAll("\\Q(-|–|-)?\\E", "-");
                str = str.replaceAll("\\Q[(]?\\E", "");
                str = str.replaceAll("\\Q[)]?\\E", "");
                String[] list = str.split("\\|");
                res_data.AddPosition(list[0]);
            }           
		}
		return res_data;
	}
    
    public UserResumeData GetResumeContent() throws IOException{
		
		if(resume.fileData==null)
			return null;
		
		UserResumeData res_data = new UserResumeData();
                
                res_data.SetFileContent(resume.fileData);
                System.out.println("Filecontent Set");
		
                res_data = FindUserInstituteData(res_data);
                System.out.println("Institutes Set");
		
                res_data = FindUserCompanyData(res_data);
                System.out.println("Companies Set");
		
                res_data = FindUserEmailId(res_data);
		System.out.println("Email Set");
		
                res_data = FindUserPhoneNo(res_data);
		System.out.println("Phone No Set");
		
                res_data = FindUserDegree(res_data);
		System.out.println("Degree Set");
		
                res_data = FindUserSkill(res_data);
		System.out.println("Skill Set");
		
                res_data = FindUserPosition(res_data);
		System.out.println("Position Set");
                
		return res_data;
	}
}

public class ResumeParser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Parser parser = new Parser();
        for(int j=2;j<=2;j++){
            System.out.println();
            System.out.println(j);
            parser.DownloadFile(j);
            UserResumeData resume = parser.GetResumeContent();
            System.out.println("Phone Number");
            System.out.println(resume.GetPhoneNo());

            System.out.println("Email Id");
            System.out.println(resume.GetEmailId());

            System.out.println("Company and Experience");
            for(int i=0;i<resume.GetCompanyList().size();i++){
                   resume.GetCompanyList().get(i).DisplayCompany();
               }

            System.out.println("Institute and Degree");
            for(int i=0;i<resume.GetColDegList().size();i++){
                System.out.println(resume.GetColDegList().get(i).GetCollege());
                System.out.println(resume.GetColDegList().get(i).GetDegree());
            }

            System.out.println("Degree And Major");
            for(int i=0;i<resume.getMajorList().size();i++){
                System.out.println(resume.getMajorList().get(i).GetDegree());
                System.out.println(resume.getMajorList().get(i).GetMajor());
            }
            
            System.out.println("Position");
            for(int i=0;i<resume.getPositionList().size();i++){
                System.out.println(resume.getPositionList().get(i));
            }
            
            System.out.println("Skill");
            for(int i=0;i<resume.getSkillList().size();i++){
                System.out.println(resume.getSkillList().get(i));
            }
            System.out.println();
        }    
    }
    
}
