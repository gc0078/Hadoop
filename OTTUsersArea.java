import java.util.HashMap;
import java.util.Map;

public class OTTUsersArea
{
  static Map<String,String> areaMap = new HashMap<String,String>();   
	 
  OTTUsersArea()
  {    
    areaMap.put("231","Texas(231)");
    areaMap.put("409","Michigan(409)");
    areaMap.put("314","Missouri(314)");
    areaMap.put("000","Pennsylvania");
    areaMap.put("00", "Houston");
    areaMap.put("0", "Newyork");
  }

   public String getAreaName(String areaName)
   {	  
	   return areaMap.get(areaName).toString();      
   }  
}
