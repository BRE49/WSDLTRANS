package kent.service;

import kent.model.JsonResult;
import kent.model.SuccessResult;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class TransitionServiceOnWeb {

    //公理生成部分的变量列表
    private List<String> varList;
    //数据名称及其复杂类型映射
    private Map<String,String> dataAndType;
    //存储端口类型名
    private String portTypeName = "";

    //输入一个String类型的xml文件
    public JsonResult trans(String inputWsdl) throws UnsupportedEncodingException,DocumentException{
            StringBuilder text = new StringBuilder();
            SAXReader saxReader = new SAXReader();
            //将String类型转换为InputStream类型
            InputStream wsdlStream = new ByteArrayInputStream(inputWsdl.getBytes("UTF-8"));
            Document document = saxReader.read(wsdlStream);
            Element root = document.getRootElement();

            //初始化全局变量
            varList = new ArrayList<>();
            dataAndType = new LinkedHashMap<>();
            text.append(getComplexType(root));
            text.append(getSpecMain(root,document));
            text.append(getAxioms());
            //System.out.println(text);
            SuccessResult result = new SuccessResult();
            result.setData(text.toString());
            return result.setInfo("转换成功");
    }

    /**
     *
     * tools 工具部分
     */

    //去掉前缀 如 xs：string
    private String spiltPrefix(String a){
        if("null".equals(a) || a == null) return "";
        if(a.contains(":")){
            String[] b = a.split(":");
            return b[1];
        }else{
            return a;
        }
    }

    //imports列表防止重复添加
    private boolean containsString(StringBuilder s,String a){
        return Arrays.asList(s.toString().split(":")[1].substring(1).split(","))
                .contains(a);
    }


    /**
     * 类型转换部分
     */
    //复合类型转换
    private StringBuilder getComplexType(Element root){
        StringBuilder typeBuilder = new StringBuilder();
        List<?> list = root.selectNodes("//types//complexType");
        if(list.size() == 0){
            root.addNamespace("xs","http://www.w3.org/2001/XMLSchema");
            list = root.selectNodes("//wsdl:types//xs:complexType");
        }
        list.forEach(l->{
            Element element = (Element) l;
            // get the record type
            String complexTypeName = element.attributeValue("name");
            typeBuilder.append("type ").append(complexTypeName).append("=record").append("\n");
            //get the normal type
            Iterator<?> sequenceIterator = element.nodeIterator();
            sequenceIterator.forEachRemaining(s->{
                if (s instanceof Element) {
                    Element sequenceElement = (Element) s;
                    Iterator<?> eleIterator = sequenceElement.nodeIterator();
                    eleIterator.forEachRemaining(ele->{
                        if(ele instanceof Element){
                            Element elem = (Element) ele;
                            String normalName = elem.attributeValue("name");
                            String normalType = elem.attributeValue("type");
                            String afterNormalType = spiltPrefix(normalType);
                            typeBuilder.append(normalName).append(": ").append(afterNormalType).append(";").append("\n");
                        }
                    });
                }
            });
        });
        typeBuilder.append("end").append("\n").append("\n");
        return typeBuilder;
    }


    /**
     * 签名主体部分
     */

    private StringBuilder getSpecMain(Element root,Document doc){
        // service 名 转换为 Spec 名
        StringBuilder specMain = new StringBuilder("Spec ");
        String specName = root.element("service").attributeValue("name");
        specMain.append(specName).append("\n");

        //类型转换构建imports列表
        //set complexTypeName to imports list
        specMain.append("imports: ");
        List<?> list = root.selectNodes("//types//complexType");
        //前缀补充
        if(list.size() == 0){
            root.addNamespace("xs","http://www.w3.org/2001/XMLSchema");
            list = root.selectNodes("//wsdl:types//xs:complexType");

        }

        varList.add("message");
        list.forEach(l->{
            int index = 0;
            Element element = (Element) l;
            Iterator<?> sequenceIt = element.nodeIterator();
            sequenceIt.forEachRemaining(sequenceNode -> {
                if(sequenceNode instanceof Element){
                    Element sequenceEle = (Element)sequenceNode;
                    Iterator<?> xsIt = sequenceEle.nodeIterator();
                    xsIt.forEachRemaining(xsNode ->{
                        if(xsNode instanceof Element){
                            Element xsEle = (Element)xsNode;
                            String basicTypeName = xsEle.attributeValue("type");
                            basicTypeName = spiltPrefix(basicTypeName);
                            if(!containsString(specMain,basicTypeName))
                                specMain.append(basicTypeName).append(",");
                        }
                    });
                }
            });
            String complexTypeName = element.attributeValue("name");
            if(!containsString(specMain,complexTypeName))
                specMain.append(complexTypeName).append(",");
            //复杂类型添加到变量列表中
            varList.add(++index,complexTypeName);
        });
        //set messageName to imports list
        List<?> list1 =  root.selectNodes("//message");
        if(list1.size() == 0) list1 = root.selectNodes("//wsdl:message");
        list1.forEach(l->{
            Element element = (Element) l;
            String messageName = element.attributeValue("name");
            //拿到import列表 防止重复添加
            if(!containsString(specMain,messageName))
            specMain.append(messageName).append(",");
        });
        //delete the last ,
        specMain.deleteCharAt(specMain.length()-1).append("\n").append("ops:").append("\n");

        //transformer 和 observer 的转换与构建
        StringBuilder trans = new StringBuilder("transformer:").append("\n");
        StringBuilder ob = new StringBuilder("observer:").append("\n");
        //operation tag
        StringBuilder inS = new StringBuilder();
        StringBuilder outS = new StringBuilder();

            // 提取第一层的所有节点 只有标签节点definition
            Iterator it = doc.nodeIterator();
            while (it.hasNext()){
                //转换成标签节点 element
                Element def = (Element) it.next();
                //提取definition的子节点，不包括孙
                Iterator typeIterator = def.nodeIterator();
                while (typeIterator.hasNext()){
                    Node typeNode = (Node) typeIterator.next();
                    //找出type标签节点
                    if(typeNode instanceof Element){
                        String typeName = typeNode.getName();
                        if(Objects.equals(typeName,"types")){
                            Element typeElement = (Element)typeNode;
                            //提取types的子节点 只有标签节点schema
                            Iterator eleIt = typeElement.nodeIterator();
                            while (eleIt.hasNext()){
                                Node schemaNode = (Node) eleIt.next();
                                if(schemaNode instanceof Element){
                                    Element schemaElement = (Element)schemaNode;
                                    //提取schema标签节点下的子节点
                                    Iterator schIt = schemaElement.nodeIterator();
                                    while (schIt.hasNext()){
                                        Node eleNode = (Node) schIt.next();
                                        //找到element节点
                                        if(eleNode instanceof Element){
                                            Element elementTag = (Element)eleNode;
                                            String tagName = elementTag.getName();
                                            if(tagName.equals("element")){
                                                String eleNameAttr = elementTag.attributeValue("name");
                                                String eleTypeAttr = elementTag.attributeValue("type");

                                                //remove prefix
                                                eleNameAttr = this.spiltPrefix(eleNameAttr);
                                                eleTypeAttr = this.spiltPrefix(eleTypeAttr);
                                                dataAndType.put(eleNameAttr,eleTypeAttr);
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
                        }else if(typeName.equals("portType")){
                            Element portTypeElement = (Element)typeNode;
                            //将端口类型名称添加到变量列表的头部
                            portTypeName = portTypeElement.attributeValue("name");
                            varList.set(0,portTypeName);
                            //提取portType标签下的下一级节点，只有operation
                            Iterator portTypeIt = portTypeElement.nodeIterator();
                            while (portTypeIt.hasNext()){
                                Node operationNode = (Node) portTypeIt.next();
                                if(operationNode instanceof Element){
                                    Element operationElement = (Element)operationNode;
                                    //提取操作名 getMaxMinTemps
                                    /*String operationName = operationElement.attributeValue("name");
                                    ob.append(operationName).append(": ");*/
                                    Iterator<?> inOutIt = operationElement.nodeIterator();
                                    while (inOutIt.hasNext()){
                                        Node inOutNode  =  (Node) inOutIt.next();
                                        if(inOutNode instanceof Element){
                                            Element inOutElement = (Element) inOutNode;
                                            String inOutName = inOutElement.getName();
                                            //operation 元素构成不同
                                            String messageLabel = inOutElement.attributeValue("messageLabel");
                                            if("".equals(messageLabel) || Objects.equals(messageLabel,null)){
                                                messageLabel = inOutElement.attributeValue("message");
                                            }
                                            String elementAttr = inOutElement.attributeValue("element");
                                            if("".equals(elementAttr) || Objects.equals(elementAttr,null)){
                                                elementAttr = inOutElement.attributeValue("name");
                                            }
                                            elementAttr = spiltPrefix(elementAttr);

                                            if(messageLabel.equals("In") || inOutName.equals("input")){
                                                inS.append(elementAttr).append(",");
                                            }else if(messageLabel.equals("Out") || inOutName.equals("output")){
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
            if(inS.length() > 1) {
                inS.deleteCharAt(inS.length() - 1);
            }
            inS.append(" -> ");
            if(outS.length() > 1) {
                outS.deleteCharAt(outS.length() - 1);
            }
            ob.append("\n").append(inS).append(outS).append("\n");

        specMain.append(trans).append(ob);


        return specMain;
    }

    /**
     * 公理构造生成部分
     */

    private  StringBuilder getAxioms(){
        //公理构造StringBuilder
        StringBuilder axioms = new StringBuilder();
        //变量列表转StringBuilder
        StringBuilder varString = new StringBuilder();
        //公理主体部分
        StringBuilder axiomsMain = new StringBuilder();
        axioms.append("\n").append("axioms: ").append("\n");
        //处理变量列表中的元素，取一半长度的字符串

        varString.append("var ");
        // get var list
        varList.forEach(v->{
            String temp;
            temp = v.substring(0,v.length()/2+1);
            temp = temp+": "+v+"; ";
            varString.append(temp);
        });

        //生成公理说明主体部分
        String subPortTypeName = portTypeName.substring(0,portTypeName.length()/2+1);
        //{PlaceAndDate=pdrec, MaxMinTemp=mmtre, InDataFault=errmes} dataAndType Map序列
        Set<String> complexTypeSet = dataAndType.keySet();
        Iterator<String> keySetIt = complexTypeSet.iterator();
        //进行双重循环，围绕每一个key值进行迭代，构造公理
        keySetIt.forEachRemaining(k->{
            Iterator<String> it = complexTypeSet.iterator();
            it.forEachRemaining(i -> {
                String subP = subPortTypeName + ".set_ " + k + " (" + this.spiltPrefix(dataAndType.get(k)) + ").get_ ";
                axiomsMain.append(subP);
                if (i.equals(k)) {
                    String kvN = k + " = " + this.spiltPrefix(dataAndType.get(k)) + "\n";
                    axiomsMain.append(kvN);
                } else {
                    String tempK = i + " = " + subPortTypeName + ".get_ " + i + "\n";
                    axiomsMain.append(tempK);
                }
            });
        });
        axioms.append(varString).append("\n");
        axioms.append(axiomsMain);
        axioms.append("end-spec");
        return axioms;
    }
}
