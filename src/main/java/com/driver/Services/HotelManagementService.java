package com.driver.Services;

import com.driver.Repository.HotelManagementRepository;
import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelManagementService {

    @Autowired
    private HotelManagementRepository repository;

    public String addHotel(Hotel hotel)
    {
         if(hotel == null)
         {
             return "FAILURE";
         }
         else if(hotel.getHotelName() == null)
         {
             return "FAILURE";
         }
         else
         {
             return repository.addHotel(hotel.getHotelName(),hotel,hotel.getFacilities());
         }
    }
    public Integer addUser(User user)
    {
        return repository.addUser(user.getaadharCardNo(),user);
    }
    public String getHotelWithMostFacilities()
    {
        return repository.getHotelWithMostFacilities();
    }
    public int bookARoom(Booking obj)
    {
        String id = obj.getBookingId();
        return repository.bookARoom(id,obj);
    }
    public int getBookings(int aadharCard)
    {
        return repository.getBookings(aadharCard);
    }
    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName)
    {
        return repository.updateFacilities(newFacilities,hotelName);
    }
}
