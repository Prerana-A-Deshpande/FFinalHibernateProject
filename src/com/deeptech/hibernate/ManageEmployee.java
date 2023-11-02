package com.deeptech.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ManageEmployee {
	private static SessionFactory sf;
	public static void main(String[] args) {
		try {
			sf=new Configuration().configure().addAnnotatedClass(Employee.class).buildSessionFactory();
			//this line is responsible to create table, fields, columns, etc
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		//creating objects
		ManageEmployee me=new ManageEmployee();
		Integer emp1=me.addEmployee("Preetham","A","Developer",35000);
		Integer emp2=me.addEmployee("Chandan","C","DataAanalyst",38000);
		Integer emp3=me.addEmployee("Madan","D","Developer",40000);
		Integer emp4=me.addEmployee("Priya","E","DataAanalyst",58000);
		Integer emp5=me.addEmployee("Chethan","M","Developer",88000);
		me.updateEmployee(emp1, 50000);
		me.listEmployee();
		me.deleteEmployee(emp2);
	}
	public void deleteEmployee(Integer emp) {
		Session s=sf.openSession();
		Transaction tx=null;
		try {
			tx=s.beginTransaction();
			Employee empdata=s.get(Employee.class, emp);
			s.delete(empdata);
			tx.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void updateEmployee(Integer e1, double sal) {
		Session s=sf.openSession();
		Transaction tx=null;
		try {
			tx=s.beginTransaction();
			Employee emp=s.get(Employee.class, e1);
			emp.setSalary(sal);
			s.update(emp);
			tx.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void listEmployee() {
		Session s=sf.openSession();
		Transaction tx=null;
		try {
			tx=s.beginTransaction();
			Query q=s.createQuery("from Employee");
			List<Employee> e=q.list();
			for(Employee emp:e) {
				System.out.println(emp.getFirstname()+"\t"+emp.getLastname()+"\t"+emp.getDesignation()+"\t"+emp.getSalary());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
public Integer addEmployee(String fname, String lname, String desig, double sal) {
	Session s=sf.openSession();
	Transaction tx=null;
	Integer employeeID=null;
	try {
		tx=s.beginTransaction();
		Employee emp=new Employee();
		emp.setFirstname(fname);
		emp.setLastname(lname);
		emp.setDesignation(desig);
		emp.setSalary(sal);
		employeeID=(Integer) s.save(emp);
		System.out.println("record saved");
		tx.commit();
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	finally {
		s.close();
		//sf.close();
	}
	return employeeID;
	
}
}
