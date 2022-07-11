package com.andr.model.store;

import com.andr.model.City;
import com.andr.model.User;
import com.andr.model.annoucement.Announcement;
import com.andr.model.annoucement.AnnouncementType;
import com.andr.model.car.*;

import java.util.List;

public interface Store {
    User findUserByLogin(String login);

    void save(User user);

    void delete(User user);

    List<City> findAllCites();

    List<CarModel> findAllCarModel();

    List<CarBodyType> findAllCarBodyType();

    List<CarEngineType> findAllCarEngineType();

    List<CarTransmissionBoxType> findAllCarTransmissionBoxType();

    List<AnnouncementType> findAllAnnouncementType();

    Announcement findAnnouncementById(int id);

    List<Announcement> findAnnouncementByUserId(int userId);

    List<Announcement> findAllAnnouncement();

    void save(Announcement announcement);

    void update(Announcement announcement);

    void saveCarPhoto(CarPhoto carPhoto, int announcementId);
}