package com.titizz.simulation.toyspring.simple;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * dom4j读取并解析xml
 */
public class Dom4JTest2
{
    int len = 0;
    int stlen =0;
    @Test
    public void maintest() throws Exception
    {
        String location = Dom4JTest2.class.getClassLoader().getResource("toy-spring-ioc.xml").getFile();
//        String location = Dom4JTest2.class.getClassLoader().getResource("testBean.xml").getFile();

        SAXReader saxReader = new SAXReader();

        Document document = saxReader.read(new File(location));

        // 获取根元素
        Element root = document.getRootElement();

        System.out.println("Root: " + root.getName());

        // 获取所有子元素
        List<Element> childList = root.elements("bean");
        System.out.println("total child count: " + childList.size());


        getNodes(root);




    }
    public void myPrintln(String s){
        StringBuffer space= new StringBuffer();
        for(int i= 0;i<this.len;i++)
        {
            space.append("  ");//这里是空格
        }
        System.out.println(space+s);

    }
    public  void getNodes(Element node) {
        myPrintln("level:"+String.valueOf(len));
        //当前节点的名称、文本内容和属性
        myPrintln("当前节点名称：" + node.getName());//当前节点名称
        myPrintln("当前节点的内容：" + node.getTextTrim());//当前节点名称
        Element  pNode = node.getParent();
//        List<Attribute> listAttr = node.attributes();//当前节点的所有属性的list
//        for (Attribute attr : listAttr) {//遍历当前节点的所有属性
//            String name = attr.getName();//属性名称
//            String value = attr.getValue();//属性的值
//
//            myPrintln("属性名称：" + name +" "+ "属性值：" + value);
//        }


        List<Element> childList = node.elements();
        int childsize = childList.size();
        if (childsize>0){
            myPrintln("total child count: " + childList.size());
        }
        this.len=this.len+1;
        for (Element e :childList){
            this.getNodes(e);
        }
        this.len = this.len-1;
    }

}