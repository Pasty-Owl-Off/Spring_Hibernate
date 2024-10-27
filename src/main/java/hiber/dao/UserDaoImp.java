package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserByCar(int series, String model) {
      return sessionFactory.getCurrentSession().createQuery("from Car where series = " + series, Car.class)
              .setMaxResults(1)
              .uniqueResult().getModel().equals(model) ?
      sessionFactory.getCurrentSession().createQuery("from User where car_series = " + series, User.class)
              .setMaxResults(1)
              .uniqueResult() :
              null;
   }
}
