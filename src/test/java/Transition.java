import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class Transition {

    private StringBuilder text;

    public StringBuilder getText() {
        text = new StringBuilder();
        return text;
    }

    File wsdl = new File(this.getClass().getClassLoader().getResource("ws01.wsdl").getFile());


    @Test
    public void transition1() {
     /*   try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(wsdl);
            Element root = document.getRootElement();
            StringBuilder text1 = new StringBuilder();
            text1.append(TypeTransition.getComplexType(root));
            text1.append(SpecTransition.getSpecMain(root));
            text1.append(TypeTransition.getTypeImports(root));
            StringBuilder sb = new StringBuilder(SpecTransition.getTransformer(document));
            text1.append(sb);
            System.out.println(text1);
            text1.append(SpecTransition.getOperationInfo(document));
            System.out.println(text1);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }*/

    }
}
