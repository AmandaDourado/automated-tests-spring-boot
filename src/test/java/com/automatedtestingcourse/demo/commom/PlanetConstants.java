package com.automatedtestingcourse.demo.commom;

import com.automatedtestingcourse.demo.domain.Planet;

public class PlanetConstants {

    public static final Planet PLANET = new Planet("name","climate","terrain");
    public static final Planet INVALID_PLANET = new Planet("","","");
}
