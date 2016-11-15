package com.realdolmen.rair.domain.jsf.statistics;

import com.realdolmen.rair.data.dao.BookingDao;
import com.realdolmen.rair.domain.entities.Booking;
import com.realdolmen.rair.domain.entities.BookingStatus;
import org.primefaces.model.chart.PieChartModel;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Named("bookingStatistics")
@RequestScoped
public class BookingStatisticsBean implements Serializable {

    private PieChartModel completeAndPendingBookings;

    @Inject
    private BookingDao bookingDao;

    @PostConstruct
    @Transactional
    private void init() {
        initPendingCompleteChart();
    }

    private void initPendingCompleteChart() {
        completeAndPendingBookings = new PieChartModel();

        for (BookingStatus status : BookingStatus.values()) {
            long size = bookingDao.em().createNamedQuery("Booking.countByStatus", Long.class).setParameter("status", status).getSingleResult();
            completeAndPendingBookings.set(status.name(), size);
        }

        completeAndPendingBookings.setTitle("Bookings by status");
        completeAndPendingBookings.setLegendPosition("w");
    }

    public PieChartModel getCompleteAndPendingBookings() {
        return completeAndPendingBookings;
    }

    public void setCompleteAndPendingBookings(PieChartModel completeAndPendingBookings) {
        this.completeAndPendingBookings = completeAndPendingBookings;
    }
}
