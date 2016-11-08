package com.realdolmen.rair;

import javax.inject.Inject;
import javax.inject.Named;

@Named (value = "register")
public class RegisterBean {

    //region CONSTANTS -

    //endregion

    //region Private Member Variables -

    private String selectedSex;
    private Enum<Sex> sexEnum;

    //endregion	 

    //region Private Properties - 

    //endregion

    //region Private Methods - 

    //endregion	

    //region Constructors - 

    //endregion

    //region Public Properties -

    public String getSelectedSex() {
        return selectedSex;
    }

    public void setSelectedSex(String selectedSex) {
        this.selectedSex = selectedSex;
    }

    public Sex[] getSexEnum() {
        return Sex.values();
    }

    //endregion

    //region Public Methods - 



    //endregion

}
