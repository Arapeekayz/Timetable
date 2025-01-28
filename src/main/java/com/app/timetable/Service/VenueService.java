package com.app.timetable.Service;

import com.app.timetable.Entity.Invigilator;
import com.app.timetable.Entity.Venue;
import com.app.timetable.Interface.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService {

    @Autowired
    private VenueRepository venueRepository;

    public List<Venue> getAllVenues() {
        return (List<Venue>) venueRepository.findAll();
    }

    public Venue getVenueById(Long id) {

        return venueRepository.findById(id).orElse(null);
    }

    public Venue createVenue(Venue venue) {
        return venueRepository.save(venue);
    }

    public Venue updateVenue(Long id, Venue venue) {
        Venue existingVenue = venueRepository.findById(id).orElse(null);
        if (existingVenue != null) {
            existingVenue.setName(venue.getName());
            existingVenue.setCode(venue.getCode());
            return venueRepository.save(existingVenue);
        } else {
            return null;
        }
    }

    public void deleteVenue(Long id) {
        venueRepository.deleteById(id);
    }
}

