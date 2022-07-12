package dwr.company.restauracje;

import entity.Employee;
import entity.Logins;
import entity.Restaurants;
import org.json.simple.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Level;

public class DatabaseAPI {
    static EntityManagerFactory emf;
    static EntityManager em;
    public DatabaseAPI(){
        org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.INFO);//OFF
        emf = Persistence.createEntityManagerFactory("default");
        em = emf.createEntityManager();
    }
    private JSONObject prepareJSON(List<Employee> list){
        JSONObject jo = new JSONObject();
        for(Integer i=0; i<list.size();i++){
            jo.put(i.toString(),list.get(i).toJSON());
        }
        return jo;
    }
    // Employee
    public JSONObject getAllEmployee(){
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT emp FROM Employee emp");
        List<Employee> list = query.getResultList();
        em.getTransaction().commit();
        return (prepareJSON(list));
    }
    public void close(){
        em.close();
    }
    public JSONObject getEmployeeById(int id){
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT emp FROM Employee emp where emp.id = ?1").setParameter(1, id);
        List<Employee> list = query.getResultList();
        em.getTransaction().commit();
        return (prepareJSON(list));
    }
    public JSONObject getEmployeeByName(String name){
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT emp FROM Employee emp where emp.name like ?1").setParameter(1, name + "%");
        List<Employee> list = query.getResultList();
        em.getTransaction().commit();
        return (prepareJSON(list));
    }
    public void insertEmployee (Employee e){
        em.getTransaction().begin();
        em.merge(e);
        em.getTransaction().commit();
    }
    public void deleteEmployee (Integer id){
        em.getTransaction().begin();
        //deleteLogin(id);
        em.remove(em.find(Employee.class, id));
        em.getTransaction().commit();
    }

    public void updateEmployee(Employee e){
        em.getTransaction().begin();
        Employee emp = em.find(Employee.class,e.getId());
        emp.setId(e.getId());
        emp.setName(e.getName());
        emp.setLastname(e.getLastname());
        em.getTransaction().commit();
    }
    public JSONObject getAllEmployeesFullInfo()
    {
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT log FROM Logins log");
        List<Logins> list = query.getResultList();
        for (Logins result : list) {
            em.persist(result);
            System.out.println(result.getEmp().getName()+result.getEmp().getLastname()+result.getLogin()+result.getPassword());
        }
        // logins.
        em.getTransaction().commit();

        return new JSONObject();
    }

    public int getDatebaseIdByName(String name){
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT rest FROM Restaurants rest where rest.name like ?1").setParameter(1, name);
        List<Restaurants> list = query.getResultList();
        em.getTransaction().commit();
        if (list.size()==1)
            return list.get(0).getId();
        return 0;
    }
    public Logins authorization(String userName, String password,int id){
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT log FROM Logins log where log.login = ?1 and log.password = ?2 and log.idrestaurant = ?3").setParameter(1, userName).setParameter(2,password).setParameter(3,id);
        List<Logins> list = query.getResultList();
        em.getTransaction().commit();
        if(list.size()==1) return list.get(0);

        Logins bad_login = new Logins();
        bad_login.setId(-1);
        return bad_login;
    }

}








//        em.createNativeQuery("INSERT INTO employee (name, lastname) VALUES (?,?)")
//        .setParameter(1, e.getName())
//        .setParameter(2, e.getLastname())
//        .executeUpdate();