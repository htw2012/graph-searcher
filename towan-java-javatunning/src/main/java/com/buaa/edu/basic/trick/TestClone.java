package com.buaa.edu.basic.trick;

import java.util.Vector;

import org.junit.Test;

public class TestClone {

	public class Student implements Cloneable{
		private int id;
		private String name;
		private Vector courses;
		public Student(){
			try {
				Thread.sleep(1000);
				System.out.println("Student Construnctor called");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		public Vector getCourses() {
			return courses;
		}
		public void setCourses(Vector courses) {
			this.courses = courses;
		}
		public Student newInstance(){
			try {
				return (Student) this.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected Object clone()throws CloneNotSupportedException{
			Student stu=(Student)super.clone();
			Vector v=stu.getCourses();
			Vector v1=new Vector();
			for(Object o:v){
				v1.add(o);
			}
			stu.setCourses(v1);
			return stu;
		}	
	}
	@Test
	public void test() {
		Student stu1=new Student();
		Vector cs=new Vector();
		cs.add("Java");
		stu1.setId(1);
		stu1.setName("XiaoMing");
		stu1.setCourses(cs);
		
		Student stu2=stu1.newInstance();
		stu2.setId(2);
		stu2.setName("XiaoDong");
		stu2.getCourses().add("C#");
		
		System.out.println("stu1'name:"+stu1.getName());
		System.out.println("stu2'name:"+stu2.getName());
		System.out.println(stu1.getCourses()==stu2.getCourses());
	}

}
