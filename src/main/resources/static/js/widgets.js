$(function(){

    function initTiles(){
        $(".live-tile").css('height', function(){
            return $(this).data('height')
        }).liveTile();

        $(document).one('pjax:beforeReplace', function(){
            $('.live-tile').liveTile("destroy", true).each(function(){
                var data = $(this).data("LiveTile");
                if (typeof (data) === "undefined")
                    return;
                clearTimeout(data.eventTimeout);
                clearTimeout(data.flCompleteTimeout);
                clearTimeout(data.completeTimeout);
            });
        });
    }

    function initWeather(){
        var icons = new Skycons({"color": Sing.colors['white']});
        icons.set("clear-day", "clear-day");
        icons.play();

        icons = new Skycons({"color": Sing.colors['white']});
        icons.set("partly-cloudy-day", "partly-cloudy-day");
        icons.play();

        icons = new Skycons({"color": Sing.colors['white']});
        icons.set("rain", "rain");
        icons.play();

        icons = new Skycons({"color": Sing.lighten(Sing.colors['brand-warning'], .1)});
        icons.set("clear-day-3", "clear-day");
        icons.play();

        icons = new Skycons({"color": Sing.colors['white']});
        icons.set("partly-cloudy-day-3", "partly-cloudy-day");
        icons.play();

        icons = new Skycons({"color": Sing.colors['white']});
        icons.set("clear-day-1", "clear-day");
        icons.play();

        icons = new Skycons({"color": Sing.colors['brand-success']});
        icons.set("partly-cloudy-day-1", "partly-cloudy-day");
        icons.play();

        icons = new Skycons({"color": Sing.colors['gray']});
        icons.set("clear-day-2", "clear-day");
        icons.play();

        icons = new Skycons({"color": Sing.colors['gray-light']});
        icons.set("wind-1", "wind");
        icons.play();

        icons = new Skycons({"color": Sing.colors['gray-light']});
        icons.set("rain-1", "rain");
        icons.play();
    }

    function pageLoad(){
        $('.widget').widgster();
        initTiles();
        initWeather();
    }

    pageLoad();
    SingApp.onPageLoad(pageLoad);

});