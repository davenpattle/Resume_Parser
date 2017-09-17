package hellosemantiweb;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.apache.jena.enhanced.BuiltinPersonalities.model;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.rdf.model.Bag;
import org.apache.jena.rdf.model.Resource;
//import org.mindswap.pellet.Individual;

public class resume
{
    /***********************************/
    /* Constants                       */
    /***********************************/

    // where the ontology should be
    public static final String SOURCE_URL = "http://localhost/semanticweb/resume.owl";

    // where we've stashed it on disk for the time being
    protected static final String SOURCE_FILE = "/var/www/html/semanticweb/resume.owl";

    // the namespace of the ontology
    public static final String NS = SOURCE_URL + "#";

    /***********************************/
    /* External signature methods      */
    /***********************************/

    public static void main( String[] args ) throws IOException {
        
        Parser parser = new Parser();
        parser.DownloadFile(11); // j 
        UserResumeData resume_data = parser.GetResumeContent();
        //URL url;
        //url = new URL("Vedant patel");
        //System.out.println(url);
        //System.out.println(java.net.URLEncoder.encode("Vedant Patel", "UTF-8"));
        new resume().run(resume_data);
    }
    
    public String EncodeURL(String str) throws UnsupportedEncodingException{
        return java.net.URLEncoder.encode(str, "UTF-8");
    }
    
    public void run(UserResumeData resume_data){
        
            FileWriter out;
            OntModel m;
            m = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
            loadModel(m);
            URL url;
            
         try{  
             
            
            OntClass Person= (OntClass) m.getOntClass(NS+"Person");
            OntClass PersonInstituteId= (OntClass) m.getOntClass(NS+"PersonInstituteId");
            
// create individual ex:jack
            Individual person = m.createIndividual(NS+"vikramadtiya_shekhar", (OntClass)Person);
            ArrayList<Individual> person_institute = new ArrayList();
            for(int i=0;i<resume_data.GetColDegList().size();i++){
               
                person_institute.add(m.createIndividual(NS+"vikramadtiya_shekhar"+EncodeURL(resume_data.GetColDegList().get(i).GetCollege()), (OntClass)PersonInstituteId));
            }
            
            
// create some properties - probably better to use FOAF here really
            DatatypeProperty name = m.getDatatypeProperty( NS + "name" );
            DatatypeProperty email = m.getDatatypeProperty( NS + "email" );
            DatatypeProperty number = m.getDatatypeProperty( NS + "number" );
            DatatypeProperty positions = m.getDatatypeProperty( NS + "positions" );
            DatatypeProperty skills = m.getDatatypeProperty( NS + "skills" );
            DatatypeProperty company_name = m.getDatatypeProperty(NS + "company_name");
            DatatypeProperty institute_name = m.getDatatypeProperty(NS + "institute_name");
            DatatypeProperty institute_degree = m.getDatatypeProperty(NS + "institute_degree");
            ObjectProperty has_institute_id= m.getObjectProperty(NS+ "has_institute_id");
            
            
           
            person.addProperty(name,"Vikramaditya Shekhar");
            person.addProperty(email,resume_data.GetEmailId());
            person.addProperty(number,resume_data.GetPhoneNo());
            for(int i=0;i<resume_data.getSkillList().size();i++){
                person.addProperty(skills,resume_data.getSkillList().get(i));
                
            }
            for(int i=0;i<resume_data.getPositionList().size();i++){
                person.addProperty(positions,resume_data.getPositionList().get(i));
                
            }
            for(int i=0;i<resume_data.GetCompanyList().size();i++){
                person.addProperty(company_name,resume_data.GetCompanyList().get(i).GetName());
                
            }
            for(int i=0;i<resume_data.GetCompanyList().size();i++){
                person.addProperty(company_name,resume_data.GetCompanyList().get(i).GetName());
                
            }
            
            for(int i=0;i<resume_data.GetColDegList().size();i++){
                person.addProperty(has_institute_id,person_institute.get(i));
                person_institute.get(i).addProperty(institute_name,resume_data.GetColDegList().get(i).GetCollege());
                person_institute.get(i).addProperty(institute_degree,resume_data.GetColDegList().get(i).GetDegree());
                
            }
            
   
            
           
            
           // System.out.print(jack);
 
            
            
         /* Iterator itr = jack.listPropertyValues(name);
           while(itr.hasNext()){
               System.out.println(itr.next().toString());
           }*/
           out=new FileWriter("resume_piyush.ttl",true);
            m.write(out, "Turtle");
        } catch (IOException ex) {
            Logger.getLogger(resume.class.getName()).log(Level.SEVERE, null, ex);
        }
       /* 
        Model model = ModelFactory.createDefaultModel() ;
        model.read("resume.ttl") ;
        System.out.print(model);*/
       
    
    }
    
 

  
    protected void loadModel( OntModel m ) {
        FileManager.get().getLocationMapper().addAltEntry( SOURCE_URL, SOURCE_FILE );
        Model baseOntology = FileManager.get().loadModel( SOURCE_URL );
        m.addSubModel( baseOntology );
        
        m.setNsPrefix( "resume", NS );

        // for compactness, add a prefix declaration st: (for Sam Thomas)
    }

}