import java.util.HashMap;
import java.util.Map;

public class OTTUsersCircle 
{
  static Map<String,String> cname = new HashMap<String,String>();
  String cId,state;
  
  OTTUsersCircle(String cId)
  {
   this.cId=cId;		 
  }
	 
  OTTUsersCircle()
  {
    this.cId = new String();	 
  }

   public String getcName()
   {	  
      cname.put("0001","AndhraPradesh");
      cname.put("0002","TamilNadu");
      cname.put("0003","Delhi");
      cname.put("0004","UPE");
      cname.put("0005","Guraj");
      cname.put("0006","Kerala");
      cname.put("0007","Karnataka");
      cname.put("0008","Kolkatta");
      cname.put("0009","MUMBAI");
      cname.put("0010","Rajastan");
      cname.put("0011","Punjab");
      cname.put("0012","Haryana");
      cname.put("0013","UPW");
      cname.put("0014","Maharstra&Goa");
      cname.put("0015","Chhattisgarh");
      cname.put("0016","Nagaland");
      cname.put("0017","Orissa");
      cname.put("0018","Assam");
      cname.put("0019","ArunachalPradesh");
      cname.put("0020","MadhyaPradesh");
      cname.put("0021","Bihar");
      cname.put("0022","HimachalPradesh");
      cname.put("0023","Jammu&Kashmir");
      cname.put("0024","Meghalaya");
      cname.put("0025","Mizoram");
      cname.put("0026","Manipur");
      cname.put("0027","Jharkhand");
      cname.put("0028","Telangana");     
      state = cname.get(cId);      
     
      return state;
   }  
}
