import org.dom4j.Element;
import org.dom4j.Node;

import java.util.Iterator;
import java.util.List;

class TypeTransition {

    private static StringBuilder typeBuilder = new StringBuilder();
    private static StringBuilder typeImports = new StringBuilder("imports ");


    static StringBuilder getTypeImports(Element root){
        //set complexTypeName to imports list
        List<Node> list = root.selectNodes("//types//complexType");
        for (Node aList : list){
            Element element = (Element) aList;
            String complexTypeName = element.attributeValue("name");
            typeImports.append(complexTypeName).append(",");
        }
        //set messageName to imports list
        List<Node> list1 =  root.selectNodes("//message");
        for (Node bList : list1){
            Element element = (Element)bList;
            String messageName = element.attributeValue("name");
            typeImports.append(messageName).append(",");
        }
        //delete the last ,
        typeImports.deleteCharAt(typeImports.length()-1).append("\n").append("ops:").append("\n");
        return  typeImports;
    }

    static StringBuilder getComplexType(Element root){
        List<Node> list = root.selectNodes("//types//complexType");

        for (Node aList : list) {

            Element element = (Element) aList;

            // get the record type
            String complexTypeName = element.attributeValue("name");
            typeBuilder.append("type ").append(complexTypeName).append("=record").append("\n");

            //get the normal type
            Iterator<Node> sequenceIterator = element.nodeIterator();
            while (sequenceIterator.hasNext()) {
                Node sequenceNode = sequenceIterator.next();
                if (sequenceNode instanceof Element) {
                    Element sequenceElement = (Element) sequenceNode;
                    Iterator<Node> eleIterator = sequenceElement.nodeIterator();
                    while (eleIterator.hasNext()) {
                        Node eleNode = eleIterator.next();
                        if (eleNode instanceof Element) {
                            Element ele = (Element) eleNode;
                            String normalName = ele.attributeValue("name");
                            String normalType = ele.attributeValue("type");
                            String afterNormalType = TransitionTools.spiltPrefix(normalType);
                            typeBuilder.append(normalName).append(": ").append(afterNormalType).append(";").append("\n");
                        }
                    }
                }
            }
        }


        typeBuilder.append("end").append("\n");
    return typeBuilder;
    }


}
