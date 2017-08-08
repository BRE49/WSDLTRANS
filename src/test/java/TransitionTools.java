import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TransitionTools {
    public static String spiltPrefix(String a){
        if(a.contains(":")){
            String[] b = a.split(":");
            return b[1];
        }else{
            return a;
        }
    }

    /*public static String File2String(File file){
    /   try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String content;
            while ((content=bufferedReader.readLine())!=null){
                sb.append(content);
            }
           return sb.toString();
        }catch (Exception e){
            return null;
        }
    }

    public String WSDLR(){
        File wsdl = new File(this.getClass().getClassLoader().getResource("ws01.wsdl").getFile());
        return TransitionTools.File2String(wsdl);
    }
    */
}
