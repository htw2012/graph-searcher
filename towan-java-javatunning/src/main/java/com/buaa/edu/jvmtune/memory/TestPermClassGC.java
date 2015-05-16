package com.buaa.edu.jvmtune.memory;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import org.junit.Test;

/**
 * -XX:PermSize=2M -XX:MaxPermSize=4M -XX:+PrintGCDetails
 * 
 * 
 */
public class TestPermClassGC {
	static MyClassLoader cl = new MyClassLoader();

	@SuppressWarnings("unused")
    @Test
	public void testOneClassLoad()throws CannotCompileException, InstantiationException,
		IllegalAccessException, NotFoundException{
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
		    //定义类名
			CtClass c = ClassPool.getDefault().makeClass("Geym" + i);
			c.setSuperclass(ClassPool.getDefault().get("com.buaa.edu.jvmtune.memory.JavaBeanObject"));
			//新建类
			Class<?> clz = c.toClass();
			JavaBeanObject v=(JavaBeanObject)clz.newInstance();
		}
	}
	
	@SuppressWarnings({"rawtypes", "unused"})
    @Test  //不抛出OOME
	public void testNewClassLoad() throws CannotCompileException, InstantiationException,
			IllegalAccessException, NotFoundException {
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			CtClass c = ClassPool.getDefault().makeClass("Geym" + i);
			c.setSuperclass(ClassPool.getDefault().get("com.buaa.edu.jvmtune.memory.JavaBeanObject"));
			Class clz = c.toClass(cl, null);
			JavaBeanObject v=(JavaBeanObject)clz.newInstance();
			if(i%10==0)
				cl = new MyClassLoader();
		}
	}
}