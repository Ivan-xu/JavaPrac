package com.titizz.simulation.toyspring.simple;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class mySimpleIOC {
//    加载 xml 配置文件，遍历其中的标签
//    获取标签中的 id 和 class 属性，加载 class 属性对应的类，并创建 bean
//    遍历标签中的标签，获取属性值，并将属性值填充到 bean 中
//    将 bean 注册到 bean 容器中
    private  int len;
    private Map<String,Object>beanMap = new HashMap<>();

    public mySimpleIOC(String location)throws Exception{
        loadBeans(location);
    }
    private void loadBeans(String location) throws Exception {
        //加载XML配置文件

        SAXReader saxReader = new SAXReader();

        Document document = saxReader.read(new File(location));

        // 获取根元素
        Element root = document.getRootElement();

        System.out.println("Root: " + root.getName());

        getNodes(root);
    }

    private void registerBean(String id, Object bean) {
        beanMap.put(id, bean);
    }
    public Object getBean(String name) {
        Object bean = beanMap.get(name);
        if (bean == null) {
            throw new IllegalArgumentException("there is no bean with name " + name);
        }

        return bean;
    }

    public void myPrintln(String s){
        StringBuffer space= new StringBuffer();
        for(int i= 0;i<this.len;i++)
        {
            space.append("  ");//这里是空格
        }
        System.out.println(space+s);

    }

    public  void getNodes(Element node) throws  Exception{
        myPrintln("level:"+String.valueOf(len));
        String nodeName = node.getName();
        myPrintln("当前节点名称：" + node.getName());//当前节点名称
        if ((nodeName.equals("bean"))){
            String id = node.attributeValue("id");
            String className = node.attributeValue("class");
            Class beanClass = null;
            try {
                beanClass = Class.forName(className);
                Object bean = beanClass.newInstance();
                // 将 bean 注册到 bean 容器中
                //if innerbean not register
                registerBean(id, bean);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }

        }
        if (nodeName.equals("property")){
            Element pNode = node.getParent();
            //parent node type of bean and not null
            if (pNode!=null && pNode.getName().equals("bean")){
                String propName = node.attributeValue("name");
                String propValue = node.attributeValue("value");
                String propRef = node.attributeValue("ref");
                myPrintln("name: "+propName+" value: "+propValue+" ref:"+propRef);
                Object bean = getBean(pNode.attributeValue("id"));
                Field declaredField = bean.getClass().getDeclaredField(propName);
                declaredField.setAccessible(true);

                if (propValue != null && propValue.length() > 0) {
                    declaredField.set(bean, propValue);
                } else if (propRef !=null && propRef.length()>0){
                    declaredField.set(bean, getBean(propRef));

                }

            }
        }
        List<Element> childList = node.elements();
        this.len=this.len+1;
        for (Element e :childList){
            this.getNodes(e);
        }
        this.len = this.len-1;






    }




}









