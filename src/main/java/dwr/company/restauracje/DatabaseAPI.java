package dwr.company.restauracje;

import entity.*;
import org.json.simple.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
//@SuppressWarnings("unchecked")
public class DatabaseAPI {
    static EntityManagerFactory emf;
    static EntityManager em;
    String restaurant;
    public DatabaseAPI(){
        //org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.INFO);//OFF
        emf = Persistence.createEntityManagerFactory("default");
        em = emf.createEntityManager();
    }
    private JSONObject prepareJSON(List<Employee> list){
        JSONObject jo = new JSONObject();
        for(int i = 0; i<list.size(); i++){
            jo.put(Integer.toString(i),list.get(i).toJSON());
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
    public JSONObject getAllEmployeesFullInfo(String name){
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT emp FROM Employee emp where emp.name like ?1 or emp.lastname like ?1 ").setParameter(1, "%"+name + "%");
        List<Employee> list = query.getResultList();
        System.out.println(list.size());
        List<Logins> list1 = new ArrayList<>();
        for (Employee employee : list) {
            query = em.createQuery("select log from Logins log where log.id = ?1").setParameter(1, employee.getId());
            Logins log = (Logins) query.getSingleResult();
            em.merge(log);
            list1.add(log);
        }
        em.getTransaction().commit();
        JSONObject jo = new JSONObject();
        for(int i = 0; i<list.size(); i++){
            jo.put(Integer.toString(i),list1.get(i).toJSON());
        }
        return jo;
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

    public void updateEmployee(Logins e){
        em.getTransaction().begin();
        em.merge(e);
        em.getTransaction().commit();
    }
    public JSONObject getAllEmployeesFullInfo()
    {
        JSONObject jo = new JSONObject();
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT log FROM Logins log");
        List<Logins> list = query.getResultList();

        em.getTransaction().commit();
        for(int i = 0; i<list.size(); i++){
            jo.put(Integer.toString(i),list.get(i).toJSON());
        }
        return jo;
    }


    public JSONObject getAllRestaurants(){
        JSONObject jo = new JSONObject();
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT res FROM Restaurants res");
        List<Restaurants> list = query.getResultList();
        em.getTransaction().commit();
        for(int i = 0; i<list.size(); i++){
            jo.put(Integer.toString(i),list.get(i).toJSON());
        }
        return jo;
    }
    public Logins authorization(String userName, String password,String restaurant){
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT log FROM Logins log where log.login = ?1 and log.password = ?2 and log.restaurantname = ?3").setParameter(1, userName).setParameter(2,password).setParameter(3,restaurant);
        List<Logins> list = query.getResultList();
        em.getTransaction().commit();
        if(list.size()==1) {
            this.restaurant=list.get(0).getRestaurantname();
            return list.get(0);
        }

        Logins bad_login = new Logins();
        bad_login.setId(-1);
        return bad_login;
    }


    public JSONObject getProducts() {
        JSONObject jo = new JSONObject();
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT prod FROM Products prod where prod.restaurant=?1").setParameter(1,restaurant);
        return printProduct(jo, query);
    }

    public JSONObject getProducts(String name,String category) {
        JSONObject jo = new JSONObject();
        em.getTransaction().begin();
        Query query;
        if (category.equals(""))
        {
            query = em.createQuery("SELECT prod FROM Products prod where prod.name like ?1 and prod.restaurant=?2 ").setParameter(1,name+"%").setParameter(2,restaurant);
        }
        else
            query = em.createQuery("SELECT prod FROM Products prod where prod.name like ?1 and prod.category = ?2 and prod.restaurant=?3 ").setParameter(1,name+"%").setParameter(2,category).setParameter(3,restaurant);
        return printProduct(jo, query);
    }

    private JSONObject printProduct(JSONObject jo, Query query) {
        List<Products> list = query.getResultList();
        em.getTransaction().commit();
        for(int i = 0; i<list.size(); i++){
            jo.put(Integer.toString(i),list.get(i).toJSON());
        }
        return jo;
    }


    public JSONObject getCategories() {
        JSONObject jo = new JSONObject();
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT cat FROM Categories cat where cat.restaurant=?1").setParameter(1,restaurant);
        List<Categories> list = query.getResultList();
        em.getTransaction().commit();
        for(int i = 0; i<list.size(); i++){
            jo.put(Integer.toString(i),list.get(i).toJSON());
        }
        return jo;
        //
    }

    public JSONObject getStorage() {
        JSONObject jo = new JSONObject();
        em.getTransaction().begin();
        Query query = em.createQuery( "SELECT st FROM Storage st where st.restaurant = ?1 ").setParameter(1,restaurant);
        List<Storage> list = query.getResultList();
        em.getTransaction().commit();
        System.out.println(list.size());
        for(int i = 0; i<list.size(); i++){
            jo.put(Integer.toString(i),list.get(i).toJSON());
        }
        return jo;
    }
    public void insertStorageItem(Storage s) {
        System.out.println("id w db:"+s.getId());
        em.getTransaction().begin();
        em.merge(s);
        em.getTransaction().commit();

    }

    public void updateStorageAmount(Storage st) {
        em.getTransaction().begin();
        Storage s = em.find(Storage.class,st.getId());
        s.setAmount(st.getAmount());
        em.merge(s);
        em.getTransaction().commit();
    }

    public void deleteItem(Storage st) {
        em.getTransaction().begin();
        Storage s = em.find(Storage.class, st.getId());
        System.out.println(s);
        em.createNativeQuery("DELETE FROM COMPOSITIONS c WHERE c.iditem = ?1").setParameter(1,s.getId()).executeUpdate();
        em.getTransaction().commit();
        em.getTransaction().begin();
        em.remove(s);
        em.getTransaction().commit();
    }


    public void makeOrder(OrderContainer orderContainer){
        em.getTransaction().begin();
        List<Positions> lp = orderContainer.getPositions();
        System.out.println(orderContainer.getClient());
        em.merge(orderContainer.getClient());
        Orders newOrder = em.merge(orderContainer.getOrder());
        System.out.println(newOrder);
        for(Positions p : lp){
            p.setIdorder(newOrder.getId());
            //em.createNativeQuery( "INSERT INTO Positions (idorder,idproduct,amount) values ( ?1 , ?2 , ?3 ) ").setParameter(1,p.getIdorder()).setParameter(2,p.getIdproduct()).setParameter(3,p.getAmount()).executeUpdate();
            em.persist(p);
            System.out.println(p);
        }
        em.getTransaction().commit();
        makeOrderdeleteFromStorage(lp);
    }
    protected void makeOrderdeleteFromStorage(List<Positions> p){
        em.getTransaction().begin();
        Query query = em.createQuery( "SELECT st FROM Storage st where st.restaurant = ?1 ").setParameter(1,restaurant);
        List<Storage> list = query.getResultList();
        query = em.createQuery( "SELECT st FROM Compositions st ");
        List<Compositions> ls= query.getResultList();
        for(Positions pos : p){
            for(Compositions com : ls){
                if(com.getIdproduct()==pos.getIdproduct()){
                    for(Storage st : list){
                        if(st.getId()==com.getIditem()){
                            st.setAmount(st.getAmount()-com.getAmount()*pos.getAmount());
                            em.merge(st);
                            break;
                        }
                    }
                }
            }
        }
        em.getTransaction().commit();
    }
    protected void positionAddToStorage(List<Positions> p){
        em.getTransaction().begin();
        Query query = em.createQuery( "SELECT st FROM Storage st where st.restaurant = ?1 ").setParameter(1,restaurant);
        List<Storage> list = query.getResultList();
        query = em.createQuery( "SELECT st FROM Compositions st ");
        List<Compositions> ls= query.getResultList();
        for(Positions pos : p){
            for(Compositions com : ls){
                if(com.getIdproduct()==pos.getIdproduct()){
                    for(Storage st : list){
                        if(st.getId()==com.getIditem()){
                            st.setAmount(st.getAmount()+com.getAmount()*pos.getAmount());
                            em.merge(st);
                            break;
                        }
                    }
                }
            }
        }
        em.getTransaction().commit();
    }

    public JSONObject getOrders(String date) {
        JSONObject jo = new JSONObject();
        em.getTransaction().begin();
        Query query = em.createQuery( "SELECT st FROM Orders st where st.restaurant = ?1 and st.dates =?2 ").setParameter(1,restaurant).setParameter(2, Date.valueOf(date));
        List<Orders> list = query.getResultList();
        em.getTransaction().commit();
        for(int i = 0; i<list.size(); i++){
            jo.put(Integer.toString(i),list.get(i).toJSON());
        }
        return jo;
    }
    public JSONObject getPositions(int id){
        JSONObject jo = new JSONObject();
        em.getTransaction().begin();
            Query query = em.createQuery( "SELECT st FROM Positions st where st.idorder = ?1 ").setParameter(1,id);
        List<Positions> list = query.getResultList();
            query=em.createQuery( "SELECT st FROM Products st where st.restaurant = ?1 ").setParameter(1,restaurant);
        List<Products> listP = query.getResultList();
        em.getTransaction().commit();
        for (Positions positions : list) {
            for (Products products : listP) {
                if (positions.getIdproduct() == products.getId()) {
                    positions.setProductName(products.getName());
                    positions.setProductPrice(products.getPrice() * positions.getAmount());
                    break;
                }
            }
        }
        for(int i = 0; i<list.size(); i++){
            jo.put(Integer.toString(i),list.get(i).toJSONU());
        }
        return jo;
    }

    public void insertCategory(String name) {
        Categories cat = new Categories();
        cat.setId(name);
        cat.setRestaurant(restaurant);
        em.getTransaction().begin();
        em.merge(cat);
        em.getTransaction().commit();
    }

    public void makeProduct(Products p, List<Storage> ingList) {
        System.out.println(p);
        for(Storage s : ingList){
            System.out.println(s);
        }
        em.getTransaction().begin();
        Products newCreatedProduct = em.merge(p);
        int idOfNewCreatedProduct = newCreatedProduct.getId();
        for (Storage storage : ingList) {
            Compositions c = new Compositions(0, idOfNewCreatedProduct, storage.getId(), storage.getAmount());
            em.merge(c);
        }
        em.getTransaction().commit();

    }

    public void deletePositionFromOrder(Positions p) {
        List<Positions > pl = new ArrayList<>();
        pl.add(p);
        positionAddToStorage(pl);
        pl.clear();
        em.getTransaction().begin();
        Query query = em.createQuery( "SELECT st FROM Positions st where st.id = ?1 ").setParameter(1,p.getId());
        pl = query.getResultList();
        em.remove(pl.get(0));
        em.getTransaction().commit();
    }

    public void editPositionFromOrder(Positions params, Integer newValue) {
       deletePositionFromOrder(params);
       params.setAmount(newValue);
       List<Positions> pl = new ArrayList<>();
       pl.add(params);
       makeOrderdeleteFromStorage(pl);
       em.getTransaction().begin();
       em.merge(params);
       em.getTransaction().commit();
    }

    public void deleteOrder(Orders params) {
        System.out.println(params);
        System.out.println("git");
        em.getTransaction().begin();
        em.createNativeQuery("DELETE FROM POSITIONS p WHERE p.idorder = ?1").setParameter(1,params.getId()).executeUpdate();
        em.getTransaction().commit();
        em.getTransaction().begin();
        em.createNativeQuery("DELETE FROM ORDERS o WHERE o.id = ?1").setParameter(1,params.getId()).executeUpdate();
        em.getTransaction().commit();
    }
    public JSONObject getComposition(int id) {
        List<Compositions> l;
        em.getTransaction().begin();
        Query query = em.createQuery( "SELECT st FROM Compositions st where st.idproduct = ?1 ").setParameter(1,id);
        l= query.getResultList();
        em.getTransaction().commit();
        JSONObject jo = new JSONObject();
        for(int i = 0; i<l.size(); i++){
            jo.put(Integer.toString(i),l.get(i).toJSON());
        }
        return jo;
    }

    public void updateProduct(Products p1, List<Storage> ing) {
        em.getTransaction().begin();
        Products p2=em.find(Products.class,p1.getId());
        p2.setCategory(p1.getCategory());
        p2.setName(p1.getName());
        p2.setPrice(p1.getPrice());
        em.merge(p2);
        List<Compositions> l;
        Query query = em.createQuery("SELECT st FROM Compositions st where st.idproduct = ?1 ").setParameter(1,p1.getId());
        l= query.getResultList();
        for(Compositions c : l)
        {
            em.remove(c);
        }
        for (Storage storage : ing) {
            Compositions c = new Compositions(0, p1.getId(), storage.getId(), storage.getAmount());
            em.merge(c);
        }
        em.getTransaction().commit();
    }
    public void deleteProduct(int id){
        em.getTransaction().begin();
        Products p2=em.find(Products.class,id);
        List<Compositions> l;
        Query query = em.createQuery("SELECT st FROM Compositions st where st.idproduct = ?1 ").setParameter(1,id);
        l= query.getResultList();
        for(Compositions c : l)
            em.remove(c);
        em.remove(p2);
        em.getTransaction().commit();
    }

    public void insertRestaurant(String params) {
        Restaurants r = new Restaurants();
        r.setName(params);
        em.getTransaction().begin();
        em.merge(r);
        em.getTransaction().commit();
    }
    public void deleteRestaurant(String params){
        em.getTransaction().begin();
        Restaurants r = em.find(Restaurants.class,params);
        em.remove(r);

        Query query = em.createQuery("select c from Categories c where c.restaurant=?1").setParameter(1,params);
        List<Categories> cat = query.getResultList();
        for(Categories c : cat)
            em.remove(c);

        query=em.createQuery("select p from Products p where p.restaurant=?1").setParameter(1,params);
        List<Products> prod = query.getResultList();
        for(Products p : prod)
            em.remove(p);

        query=em.createQuery("select p from Storage p where p.restaurant=?1").setParameter(1,params);
        List<Storage> stor = query.getResultList();
        for(Storage p : stor)
            em.remove(p);

        query=em.createQuery("select p from Orders p where p.restaurant=?1").setParameter(1,params);
        List<Orders> o = query.getResultList();
        for(Orders p : o)
            em.remove(p);

        query=em.createQuery("select p from Logins p where p.restaurantname=?1").setParameter(1,params);
        List<Logins> log = query.getResultList();
        for(Logins p : log)
            em.remove(p);

        em.getTransaction().commit();


    }
    public void updateRestaurant(String newName,String oldName){
        em.getTransaction().begin();
        Restaurants r = em.find(Restaurants.class,oldName);
        em.remove(r);

        Query query = em.createQuery("select c from Categories c where c.restaurant=?1").setParameter(1,oldName);
        List<Categories> cat = query.getResultList();
        for(Categories c : cat){
            c.setRestaurant(newName);
            em.merge(c);
        }


        query=em.createQuery("select p from Products p where p.restaurant=?1").setParameter(1,oldName);
        List<Products> prod = query.getResultList();
        for(Products p : prod){
            p.setRestaurant(newName);
            em.merge(p);
        }

        query=em.createQuery("select p from Storage p where p.restaurant=?1").setParameter(1,oldName);
        List<Storage> stor = query.getResultList();
        for(Storage p : stor){
            p.setRestaurant(newName);
            em.merge(p);
        }

        query=em.createQuery("select p from Orders p where p.restaurant=?1").setParameter(1,oldName);
        List<Orders> o = query.getResultList();
        for(Orders p : o){
            p.setRestaurant(newName);
            em.merge(p);
        }

        query=em.createQuery("select p from Logins p where p.restaurantname=?1").setParameter(1,oldName);
        List<Logins> log = query.getResultList();
        for(Logins p : log){
            p.setRestaurantname(newName);
            em.merge(p);
        }

        em.getTransaction().commit();

    }
}








//        em.createNativeQuery("INSERT INTO employee (name, lastname) VALUES (?,?)")
//        .setParameter(1, e.getName())
//        .setParameter(2, e.getLastname())
//        .executeUpdate();