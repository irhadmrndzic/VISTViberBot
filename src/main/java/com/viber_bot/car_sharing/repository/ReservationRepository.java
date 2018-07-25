package com.viber_bot.car_sharing.repository;

import com.viber_bot.car_sharing.model.Reservation;
import com.viber_bot.car_sharing.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    //public void addRoute(Route route);
    public List<Reservation> findAll();
    public void deleteById(int id);
    //public long findById(long id);
    public Reservation findById(int id);
    public  Reservation findByRoute(Route route);
}
