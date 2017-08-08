import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import java.util.Iterator;

class SpecTransition {

    private static StringBuilder specMain = new StringBuilder("Spec ");

    // spec main body
    static StringBuilder getSpecMain(Element root){
        SpecTransition specTransition = new SpecTransition();
        String specName = specTransition.getSpecName(root);
        specMain.append(specName).append("\n");
        return specMain;
    }

    // put service name to spec name
    private String getSpecName(Element rootElement){
        Element serviceTag = rootElement.element("service");
        return serviceTag.attributeValue("name");
    }


    /*static StringBuilder getTransformer(Document document) {
        StringBuilder trans = new StringBuilder("transformer:").append("\n");
        StringBuilder ob = new StringBuilder("observer:").append("\n");
        try{
            // 提取第一层的所有节点 只有标签节点definition
            Iterator<Node> it = document.nodeIterator();
            while (it.hasNext()){
                //转换成标签节点 element
                Element def = (Element) it.next();
                //提取definition的子节点，不包括孙
                Iterator<Node> typeIterator = def.nodeIterator();
                while (typeIterator.hasNext()){
                    Node typeNode = typeIterator.next();
                    //找出type标签节点
                    if(typeNode instanceof Element){
                        String typeName = typeNode.getName();
                        if(typeName.equals("types")){
                            Element typeElement = (Element)typeNode;
                            //提取types的子节点 只有标签节点schema
                            Iterator<Node> eleIt = typeElement.nodeIterator();
                            while (eleIt.hasNext()){
                                Node schemaNode = eleIt.next();
                                if(schemaNode instanceof Element){
                                    Element schemaElement = (Element)schemaNode;
                                    //提取schema标签节点下的子节点
                                    Iterator<Node> schIt = schemaElement.nodeIterator();
                                    while (schIt.hasNext()){
                                        Node eleNode = schIt.next();
                                        //找到element节点
                                        if(eleNode instanceof Element){
                                            Element elementTag = (Element)eleNode;
                                            String tagName = elementTag.getName();
                                            if(tagName.equals("element")){
                                                String eleNameAttr = elementTag.attributeValue("name");
                                                String eleTypeAttr = elementTag.attributeValue("type");
                                                trans.append("set_ ").append(eleNameAttr)
                                                        .append(": ").append(eleTypeAttr)
                                                        .append(" ->VOID").append("\n");
                                                ob.append("get_ ").append(eleNameAttr)
                                                        .append(": VOID -> ").append(eleTypeAttr).append("\n");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }

        return trans.append(ob);
    }*/

    static StringBuilder getOperationInfo(Document document){
        StringBuilder opInfo = new StringBuilder();
        StringBuilder inS = new StringBuilder();
        StringBuilder outS = new StringBuilder();
        try{
            Iterator<Node> firstLevel = document.nodeIterator();
            while (firstLevel.hasNext()){
                Node defNode = firstLevel.next();
                if(defNode instanceof Element){
                    Element defElement = (Element)defNode;
                    Iterator<Node> secondLevel = defElement.nodeIterator();
                    while (secondLevel.hasNext()) {
                        Node portTypeNode = secondLevel.next();
                        if(portTypeNode instanceof Element){
                            Element portTypeElement = (Element)portTypeNode;
                            if(portTypeElement.getName().equals("portType")){
                                Iterator<Node> thirdLevel = portTypeElement.nodeIterator();
                                while (thirdLevel.hasNext()){
                                    Node operationNode = thirdLevel.next();
                                    if(operationNode instanceof Element){
                                        Element operationElement = (Element)operationNode;
                                        if(operationElement.getName().equals("operation")){
                                            opInfo.append(operationElement.getName()).append(": ");
                                            Iterator<Node> forthLevel = operationElement.nodeIterator();
                                            while (forthLevel.hasNext()){
                                                Node inOutNode = forthLevel.next();
                                                if(inOutNode instanceof Element){
                                                    Element inOutElement = (Element)inOutNode;
                                                    String messageLabel = inOutElement.attributeValue("messageLabel");
                                                    String elementAttr = inOutElement.attributeValue("element");
                                                    elementAttr = TransitionTools.spiltPrefix(elementAttr);
                                                    if(messageLabel.equals("In")){
                                                        inS.append(elementAttr).append(",");
                                                    }else if(messageLabel.equals("Out")){
                                                        outS.append(elementAttr).append(",");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
        inS.deleteCharAt(inS.length()-1);
        inS.append(" -> ");
        outS.deleteCharAt(outS.length()-1);
        opInfo.append(inS).append(outS).append("\n");
        return  opInfo;
    }


}
