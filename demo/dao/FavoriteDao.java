package com.example.demo.dao;

import com.example.demo.entity.db.Item;
import com.example.demo.entity.db.ItemType;
import com.example.demo.entity.db.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;

@Repository
public class FavoriteDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void setFavoriteItem(String userId, Item item){
        Session session = null;
        try{
            session = sessionFactory.openSession();
            User user = session.get(User.class, userId);
            user.getItemSet().add(item);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception ex){
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if(session != null) session.close();
        }
    }

    public void unsetFavoriteItem(String userId, String itemId){
        Session session = null;
        try{
            session = sessionFactory.openSession();
            User user = session.get(User.class, userId);
            Item item = session.get(Item.class, itemId);
            user.getItemSet().remove(item);
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception ex){
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if(session != null){
                session.close();
            }
        }
    }

    public Set<Item> getFavoriteItem(String userId){
        Session session = null;
        try{
            session = sessionFactory.openSession();
            return session.get(User.class, userId).getItemSet();
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            if(session != null) session.close();
        }
        return new HashSet<>();
    }

    // Get favorite item ids for the given user
    public Set<String> getFavoriteItemIds(String userId) {
        Set<String> itemIds = new HashSet<>();
        Session session = sessionFactory.openSession();
        try {
            //Method 1
            //session = sessionFactory.openSession();
            //User user = session.get(User.class, userId);
            //if(user == null) return itemIds;
            //Set<Item> items = user.getItemSet();

            //Method 2
            Set<Item> items = session.get(User.class, userId).getItemSet();
            for(Item item : items) {
                itemIds.add(item.getId());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if(session != null) session.close();
        }
        return itemIds;
    }

    // Get favorite items for the given user.
    // The returned map includes three entries like
    // {"Video": [item1, item2, item3], "Stream": [item4, item5, item6], "Clip": [item7, item8, ...]}
    public Map<String, List<String>> getFavoriteGameIds(Set<String> favoriteItemIds) {
        Map<String, List<String>> itemMap = new HashMap<>();
        for (ItemType type : ItemType.values()) {
            itemMap.put(type.toString(), new ArrayList<>());
        }

        try (Session session = sessionFactory.openSession()) {
            for(String itemId : favoriteItemIds) {
                //先归类
                Item item = session.get(Item.class, itemId);
                //再放入商品集合中
                itemMap.get(item.getType().toString()).add(item.getGameId());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return itemMap;
    }


}
