package com.driver.controllers;


import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HotelManagementRepository {

    HashMap<String, Hotel>  hotels = new HashMap<String, Hotel>();
    HashMap<Integer, User> users = new HashMap<Integer, User>();

    HashMap<String, List<Facility>> hotelsWithFacilities = new HashMap<>();

    HashMap<String, Booking> bookings = new HashMap<>();
    public String addHotel(String hotelName,Hotel hotel,List<Facility>list)
    {
        if(hotels.containsKey(hotelName))
        {
            return "FAILURE";
        }
        else
        {
            hotels.put(hotelName,hotel);
            hotelsWithFacilities.put(hotelName,list);
            return "SUCCESS";
        }
    }

    public Integer addUser(int aadharCardNo,User user)
    {
         if(!users.containsKey(aadharCardNo))
         {
             users.put(aadharCardNo,user);
             return aadharCardNo;
         }
        return null;
    }

    public String getHotelWithMostFacilities()
    {
        int count=0;
        String ans="";
        for(Map.Entry<String,List<Facility>> it: hotelsWithFacilities.entrySet())
        {
             int size = it.getValue().size();
             if(size > count)
             {
                 count=size;
                 ans = it.getKey();
             }
             else if(size == count)
             {
                 ans = helper(ans,it.getKey());
             }
        }
        return ans;
    }

    public String helper(String a,String b)
    {
        for(int i=0; i<a.length() && i<b.length(); i++)
        {
            int aVal = 97+(int)(a.charAt(i));
            int bVal = 97+(int)(b.charAt(i));

            if(aVal < bVal)
            {
               return a;
            }
            else if(aVal == bVal)
            {
                continue;
            }
            else{
                return b;
            }
        }
        return "";
    }


    public int bookARoom(String id,Booking obj)
    {
        bookings.put(id,obj);
        int roomsNeeded = obj.getNoOfRooms();
        String hotelName = obj.getHotelName();
        Hotel hotel = hotels.get(hotelName);
        int totalRooms = hotel.getAvailableRooms();
        if(roomsNeeded > totalRooms)
        {
            return -1;
        }
        else
        {
            int pricePerNgt = hotel.getPricePerNight();
            return roomsNeeded*pricePerNgt;
        }
    }
    public int getBookings(int aadharCard)
    {
        int count=0;
        for(Booking it:bookings.values())
        {

            if(it.getBookingAadharCard()==(aadharCard))
            {
                count++;
            }
        }
        return count;
    }

    public Hotel updateFacilities(List<Facility> newFacilities,String hotelName)
    {
        Hotel ans = null;
        for(String it:hotelsWithFacilities.keySet())
        {
            if(it.equals(hotelName))
            {
                List<Facility>oldFacilities = hotelsWithFacilities.get(it);
                if(oldFacilities.equals(newFacilities))
                {
                    ans=null;
                }
                else
                {
                    hotelsWithFacilities.put(it,newFacilities);
                    Hotel obj = hotels.get(it);
                    obj.setFacilities(newFacilities);
                    hotels.put(it,obj);
                    ans=obj;
                }
            }
        }
        return ans;
    }
}
