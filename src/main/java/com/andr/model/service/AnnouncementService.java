package com.andr.model.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.andr.helper.serialize.*;
import com.andr.model.City;
import com.andr.model.User;
import com.andr.model.annoucement.Announcement;
import com.andr.model.annoucement.AnnouncementType;
import com.andr.model.car.*;
import com.andr.model.store.HbmStore;
import com.andr.model.store.Store;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.function.Function;

public class AnnouncementService {
    private static final Logger LOG = LoggerFactory.getLogger(AnnouncementService.class.getName());
    private final Store store = HbmStore.instOf();
    private final Map<String, Function<HttpServletRequest, Optional<String>>> dispatch =
            new HashMap<>();
    private final Gson gson = new GsonBuilder()
            .setDateFormat("dd-MM-yyyy HH:mm:ss")
            .registerTypeAdapter(City.class, new CitySerializer())
            .registerTypeAdapter(CarModel.class, new CarModelSerializer())
            .registerTypeAdapter(CarBodyType.class, new CarBodyTypesSerializer())
            .registerTypeAdapter(CarEngineType.class, new CarEngineTypesSerializer())
            .registerTypeAdapter(CarPhoto.class, new CarPhotoSerializer())
            .registerTypeAdapter(
                    CarTransmissionBoxType.class, new CarTransmissionBoxTypeSerializer()
            )
            .registerTypeAdapter(Car.class, new CarSerializer())
            .registerTypeAdapter(User.class, new UserSerializer())
            .registerTypeAdapter(AnnouncementType.class, new AnnouncementTypeSerializer())
            .create();

    private AnnouncementService() {
        this.load("save", save());
        this.load("update", update());
        this.load("get-form-fields", getFormFields());
        this.load("get-user-announcement", getUserAnnouncement());
        this.load("get-all-announcement", getAllAnnouncement());
    }

    public static AnnouncementService getInstance() {
        return AnnouncementService.Holder.INSTANCE;
    }

    private static final class Holder {
        private static final AnnouncementService INSTANCE = new AnnouncementService();
    }

    public void load(String action, Function<HttpServletRequest, Optional<String>> handle) {
        this.dispatch.put(action, handle);
    }

    private Function<HttpServletRequest, Optional<String>> save() {
        return request -> {
            Optional<String> announcement = Optional.empty();
            try {
                Announcement announcementFromForm = gson.fromJson(
                        request.getReader(), Announcement.class
                );
                HttpSession session = request.getSession();
                User currentUser = (User) session.getAttribute("user");
                if (announcementFromForm.getUser().getId() != currentUser.getId()) {
                    throw new IllegalStateException("Illegal user's data from the form");
                }
                store.save(announcementFromForm);
                announcement = Optional.of(gson.toJson(announcementFromForm));
            } catch (Exception e) {
                LOG.error("Exception to save the announcement", e);
            }
            return announcement;
        };
    }

    private Function<HttpServletRequest, Optional<String>> update() {
        return request -> {
            Optional<String> announcement = Optional.empty();
            try {
                JsonObject jsonFromForm = gson.fromJson(request.getReader(), JsonObject.class);
                Announcement announcementFromDb = store.findAnnouncementById(
                        jsonFromForm.get("announcementId").getAsInt()
                );
                HttpSession session = request.getSession();
                User currentUser = (User) session.getAttribute("user");
                if (announcementFromDb.getUser().getId() != currentUser.getId()) {
                    throw new IllegalStateException("Illegal update, it's your announcement!");
                }
                if (jsonFromForm.has("isSold")) {
                    announcementFromDb.setSold(jsonFromForm.get("isSold").getAsBoolean());
                }
                store.update(announcementFromDb);
                announcement = Optional.of(gson.toJson(announcementFromDb));
            } catch (Exception e) {
                LOG.error("Exception to update the announcement", e);
            }
            return announcement;
        };
    }

    private Function<HttpServletRequest, Optional<String>> getFormFields() {
        return request -> {
            Optional<String> rsl = Optional.empty();
            try {
                Map<String, Object> response = new LinkedHashMap<>();
                Map<String, Object> fieldsData = new LinkedHashMap<>();

                List<City> cities = store.findAllCites();
                fieldsData.put("cities", cities);

                List<CarModel> carModels = store.findAllCarModel();
                fieldsData.put("carModels", carModels);

                List<CarBodyType> carBodyTypes = store.findAllCarBodyType();
                fieldsData.put("carBodyTypes", carBodyTypes);

                List<CarEngineType> carEngineTypes = store.findAllCarEngineType();
                fieldsData.put("carEngineTypes", carEngineTypes);

                List<CarTransmissionBoxType> carTransmissionBoxTypes =
                        store.findAllCarTransmissionBoxType();
                fieldsData.put("carTransmissionBoxTypes", carTransmissionBoxTypes);

                List<AnnouncementType> announcementTypes = store.findAllAnnouncementType();
                fieldsData.put("announcementType", announcementTypes.get(0));

                response.put("fields", fieldsData);

                HttpSession session = request.getSession();
                User currentUser = (User) session.getAttribute("user");
                response.put("user", currentUser);

                rsl = Optional.of(gson.toJson(response));
            } catch (Exception e) {
                LOG.error("Exception getting announcement data", e);
            }
            return rsl;
        };
    }

    private Function<HttpServletRequest, Optional<String>> getUserAnnouncement() {
        return request -> {
            Optional<String> rsl = Optional.empty();
            try {
                String userId = request.getParameter("id");
                List<Announcement> announcements =
                        store.findAnnouncementByUserId(Integer.parseInt(userId));
                rsl = Optional.of(gson.toJson(announcements));
            } catch (Exception e) {
                LOG.error("Exception getting announcement data", e);
            }
            return rsl;
        };
    }

    private Function<HttpServletRequest, Optional<String>> getAllAnnouncement() {
        return request -> {
            Optional<String> rsl = Optional.empty();
            try {
                List<Announcement> announcements = store.findAllAnnouncement();
                rsl = Optional.of(gson.toJson(announcements));
            } catch (Exception e) {
                LOG.error("Exception getting announcement data", e);
            }
            return rsl;
        };
    }

    public Optional<String> execute(HttpServletRequest request) {
        String action = request.getParameter("action");
        if (!dispatch.containsKey(action)) {
            throw new IllegalArgumentException("This action is illegal! ");
        }
        return dispatch.get(action).apply(request);
    }
}