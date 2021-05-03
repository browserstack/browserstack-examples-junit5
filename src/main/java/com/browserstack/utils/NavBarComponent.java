package com.browserstack.utils;

import com.browserstack.pages.*;

public abstract class NavBarComponent {
    public abstract HomePage clickOnHome();
    public abstract Offers clickOnOffers();
    public abstract Orders  clickOnOrders();
    public abstract Favourites clickOnFavourites();
    public abstract Login clickOnSignIn();

}
