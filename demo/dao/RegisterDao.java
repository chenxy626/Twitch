//完成与数据库进行交互的操作, dao --> data access object
package com.example.demo.dao;

import com.example.demo.entity.db.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository  //是一个component，表示这个class是个跟DB interact的class
public class RegisterDao {
    @Autowired
    private SessionFactory sessionFactory;

    public boolean register(User user){
        Session session = null;
        try{
            session = sessionFactory.openSession();
            session.beginTransaction(); //使用transaction保证操作的atomic（原子性）--> nothing or all
            session.save(user); //对DB的具体操作
            session.getTransaction().commit(); // 写log的操作
        } catch (IllegalStateException ex) { //
            ex.printStackTrace();
            session.getTransaction().rollback(); // rollback, 回到数据库操作之前的状态, 删除在DB中的操作
            return false;
        } finally {
            if(session != null){ //需要释放
                session.close(); // 关闭session
            }
        }
        return true;
    }

}
