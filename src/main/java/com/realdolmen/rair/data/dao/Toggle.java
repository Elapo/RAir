package com.realdolmen.rair.data.dao;

public interface Toggle {
    void activate();
    boolean isActive();

    void deactivate();
    void setActive(boolean flag);
}
