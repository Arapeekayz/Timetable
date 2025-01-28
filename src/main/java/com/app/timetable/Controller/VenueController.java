package com.app.timetable.Controller;

import com.app.timetable.Helpers.StringConverter;
import com.app.timetable.Entity.Venue;
import com.app.timetable.Service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/venues")
public class VenueController {

    @Autowired
    private VenueService venueService;

    @GetMapping("")
    public String getAllVenues(Model model) {
        var list = venueService.getAllVenues();
        model.addAttribute("venues", list);
        return "venues.html";

    }

    @PostMapping("/add_venue")
    public RedirectView createVenue( @ModelAttribute Venue venue) {
        Venue newVenue = new Venue(StringConverter.toCamelCase(venue.getName()), StringConverter.toCamelCase(venue.getCode()));
        venueService.createVenue(newVenue);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/venues");
        return redirectView;

    }

    @PostMapping("/update/{id}")
    public RedirectView updateVenue(@PathVariable String id, @ModelAttribute Venue venue) {
        Venue newVenue = new Venue(StringConverter.toCamelCase(venue.getName()), StringConverter.toCamelCase(venue.getCode()));
        venueService.updateVenue(Long.parseLong(id),newVenue);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/venues");
        return redirectView;
    }

    @GetMapping("/delete/{id}")
    public RedirectView deleteVenue(@PathVariable Long id) {
        venueService.deleteVenue(id);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/venues");
        return redirectView;
    }


}
