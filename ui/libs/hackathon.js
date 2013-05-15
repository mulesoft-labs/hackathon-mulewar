//var gameStartUrl = "http://172.16.20.207:8085/mulewar/startedStatus";
var gameStartUrl = "http://mulewar.corp.mulesoft.com/mulewar/startedStatus";
var sendClicksUrlRed = "http://mulewar.corp.mulesoft.com/mulewar/tugRed"
var sendClicksUrlBlue = "http://mulewar.corp.mulesoft.com/mulewar/tugBlue"
var retrieveCountersUrl = "http://mulewar.corp.mulesoft.com/mulewar/stats/team/all"
var winnerPage = "winners.html"


function urlParam(name) {
    var results = new RegExp('[\\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results)
        return results[1];
    else
        return 0;
    //return results[1] || 0;
}

function getTeam() {
    var team = decodeURIComponent(urlParam('team'));
    if (team == "Join+Jedi's+team") {
        team = "blue";
    }
    else {
        if (team == "Join+Sith's+team") {
            team = "red";
        }
        else {
            alert('Could not set team!');
        }
    }
    return team;
}


//function to open url a new window/tab
function load_url(url) {
    var load = window.open(url);
}
// -->

//function to show the countdown in seconds until the web page is unfrozen (active) again
function do_countdown(duration) {
    //10 seconds fix
    start_num = duration;
    var countdown_output = document.getElementById('countdown_div');

    if (start_num > 0) {
        countdown_output.innerHTML = format_as_time(start_num);
        var t = setTimeout("update_clock(\"countdown_div\", " + start_num + ")", 1000);
    }

    return false;
}

//helper function to update the timer on the web page this is frozen
function update_clock(countdown_div, new_value) {
    var countdown_output = document.getElementById(countdown_div);
    var new_value = new_value - 1;

    if (new_value > 0) {
        new_formatted_value = format_as_time(new_value);
        countdown_output.innerHTML = new_formatted_value;

        var t = setTimeout("update_clock(\"countdown_div\", " + new_value + ")", 1000);
    } else {
        //finish!
        countdown_output.innerHTML = "";
        $('#countdown_box').hide();

        //unlock UI
        $.uiUnlock();

        //perform anything here after the web page is unfrozen
    }
}

//helper function to calculate the time (seconds) remaining as minutes and seconds
function format_as_time(seconds) {
    var minutes = parseInt(seconds / 60);
    var seconds = seconds - (minutes * 60);

    if (minutes < 10) {
        minutes = "0" + minutes;
    }

    if (seconds < 10) {
        seconds = "0" + seconds;
    }

    var return_var = minutes + ':' + seconds;

    return return_var;
}


//main function to load the new website and start the countdown
function view_blog_countdown(duration) {
    $('#countdown_box').show(); //countdown
    $.uiLock('');
    //alert(duration);
    //performs countdown then unlocks
    do_countdown(duration);
}

//funciton to initialise a click event for the webpage buttons
$(document).ready(function () {
    $('#lock').click(function () {

        //show content
        $('#countdown_box').show(); //countdown

        //lock interface
        $.uiLock('');

        //start the countdown (unlocks interface at end)
        do_countdown();

    });

    //Initial settings
    $('#countdown_box').hide();

});

