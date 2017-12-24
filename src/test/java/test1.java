import kent.util.Const;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class test1 {

    @Test
    public void testLoader(){
        StringBuilder result = new StringBuilder("");
        ClassLoader cl = getClass().getClassLoader();
        File file  = new File(cl.getResource("ws01.wsdl").getFile());
        System.out.println(file.exists());
        File file1 = new File("ws01.wsdl");
        System.out.println(file1.exists());
    }

    @Test
    public void testDom(){
        File file1 = new File(getClass().getClassLoader().getResource("PhoneNumber.wsdl").getFile());
        System.out.println("WSDL文件是否存在？ "+file1.exists());

        try {
            //创建xml解析器对象
            SAXReader reader = new SAXReader();
            //读取xml文档，返回document对象，document代表整个xml文档
            Document doc = reader.read(file1);
            Element root = doc.getRootElement();
            String name = root.getName();
            //获取根节点
            System.out.println("根节点是: "+name);
            Iterator<Node> it = root.nodeIterator();
            while (it.hasNext()){
                Node node = it.next();
                System.out.println(node.getName());
            }
        }catch (DocumentException ex){
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    //获取子节点
    @Test
    public void testDom1(){
        File wsdl = new File(this.getClass().getClassLoader().getResource("ws01.wsdl").getFile());
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(wsdl);
            Element root = document.getRootElement();
            List<Node> list = root.selectNodes("//types//complexType");
            System.out.println(list.size());
            for (int i = 0;i<list.size();i++){
                Node node = list.get(i);
                System.out.println(node.getName());
                Element element = (Element)node;
                Iterator<Node> iterator = element.nodeIterator();
                while (iterator.hasNext()){
                    Node node1 = iterator.next();
                    if(node1 instanceof Element){
                     Element el1 = (Element)node1;
                        Iterator<Node> iterator1 = el1.nodeIterator();
                        while (iterator1.hasNext()){
                            Node node2 = iterator1.next();
                            if(node2 instanceof Element){
                                Element element1 = (Element)node2;
                                System.out.println(element1.attributeValue("name"));
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    @Test
    public void testList1(){
            StringBuilder sb = new StringBuilder("1");
            sb.append("\n");
            sb.append("2");
        System.out.println(sb);

        String a = "xs:123";
        if(a.contains(":")){
           String[] b =  a.split(":");
            System.out.println(b[1]);
        }
    }

    @Test
    public void testCollection(){
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.set(0,"3");
        System.out.println(list.get(0));
        String a = "123";
        System.out.println(a.length()/2+1);
    }

    @Test
    public void testStringMethod(){
        String a = "12345";
        System.out.println(a.substring(0,2));
        Map<String,String> testMap = new LinkedHashMap<>();
        testMap.put("key1","value1");
        testMap.put("key2","value2");
        testMap.put("key3","value3");
        Set keySet = testMap.keySet();
        Iterator k = keySet.iterator();
        for (int i=0;i<testMap.size();i++) {
            while (k.hasNext()) {
                String b = (String) k.next();
                System.out.println(b);
            }
        }
    }

    @Test
    public void testCal(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
        String month = simpleDateFormat.format(date);
        Integer m = Integer.parseInt(month);
        System.out.println(m);
        System.out.println(calendar.get(Calendar.MONTH)+1);
    }

    @Test
    public void testString(){
        String a = "imports: sayHello,";
        System.out.println(a.substring(9));
        System.out.println(Const.WEBPATH);
    }

}
