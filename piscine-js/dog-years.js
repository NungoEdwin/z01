function dogYears(planet,dogyears){
    let earthyears,dogYears;
    if (planet=='earth'){
        earthyears= dogyears/31557600;
        dogYears=earthyears*7*1;

    }else if (planet=='mercury'){
       earthyears= dogyears/31557600;
       dogYears=earthyears*7*(1/0.2408467);
    }else if (planet=='venus'){
        earthyears= dogyears/31557600;
        dogYears=earthyears*7* (1/0.61519726);
    }else if (planet=='mars'){
        earthyears= dogyears/31557600;
        dogYears=earthyears*7*(1/1.8808158);
    }else if (planet=='jupiter'){
        earthyears= dogyears/31557600;
        dogYears=earthyears*7*(1/11.862615);
    }else if (planet=='saturn'){
        earthyears= dogyears/31557600;
        dogYears=earthyears*7*(1/29.447498);
    }else if (planet=='uranus'){
        earthyears= dogyears/31557600;
        dogYears=earthyears*7*(1/84.016846);
    }else if (planet=='neptune'){
        earthyears= dogyears/31557600;
        dogYears=earthyears*7*(1/164.79132);
    }
    return dogYears.toFixed(2)*1
    //*1 is to force type to number which weirdly is string by default js yawa!!
}
console.log(dogYears('mercury', 2134835688))