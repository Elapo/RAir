package com.realdolmen.rair.domain.entities;

import com.realdolmen.rair.data.dao.Toggle;
import com.realdolmen.rair.domain.modifiers.PriceModifier;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Booking.findByFlight", query = "SELECT b FROM Booking b WHERE b.flight = :flight"),
        @NamedQuery(name="Booking.findByStatus", query = "SELECT b FROM Booking b WHERE b.status = :status")
})
public class Booking implements Toggle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date purchaseTime;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @ManyToOne
    private Flight flight;

    private boolean active;

    @ManyToMany
    private List<PriceModifier> priceModifiers;

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public Date getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Date purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    @Override
    public void activate() {
        active = true;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void deactivate() {
        active = false;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public List<PriceModifier> getPriceModifiers() {
        return priceModifiers;
    }

    public void setPriceModifiers(List<PriceModifier> priceModifiers) {
        this.priceModifiers = priceModifiers;
    }
}
