var totalArea;
var movedImg;
var moveInterval;
var sendClicksInterval;
var clickPerSecond;
var secondToSend;
var team;
var player;

addEventListener('load', inicio, false);


function inicio() {
//initialize poolers
    team = urlParam("team");
    player = urlParam("nickname");
    clickPerSecond=0;
    sendClicksInterval = setInterval(sendClicks, 500);
    moveInterval = setInterval(checkWinning, 250);
    updateCountersInterval = setInterval(retrieveValues, 500);


//other stuff
    secondToSend = 0;
    document.getElementById("pressButton").addEventListener('click', RegisterClick, false);
    movedImg = $("#toolbar img");
    positionImages();
    totalArea = $("#toolbar").width() - movedImg.width();
};

function positionImages() {
    //console.log($(#toolbar).width()/2);
    var windowWidth = $("#toolbar").width();
    var leftPos = windowWidth / 2 - movedImg.width() / 2;
    movedImg.css("left", leftPos);
};


function moveLeft(offset) {
    if (movedImg) {
        var position = parseInt(movedImg.css("left"));
        if (position - offset < 0) {
            offset = position;
        }
        movedImg.animate({"left": "-=" + offset}, "fast");
    }
}

function moveRight(offset) {
    if (movedImg) {

        var position = parseInt(movedImg.css("left"));
        if (position + offset > totalArea) {
            offset = totalArea - position;
        }

        movedImg.animate({"left": "+=" + offset}, "fast");
    }
}


function checkWinning() {
    var bearsPoints = parseInt($("#bears").text());
    var bullsPoints = parseInt($("#bulls").text());
    if (bearsPoints > bullsPoints) {
        moveLeft(bearsPoints - bullsPoints);
    } else if (bearsPoints < bullsPoints) {
        moveRight(bullsPoints - bearsPoints);
    }
}

function RegisterClick() {
    clickPerSecond = clickPerSecond + 1;
}

function callbackSendClicks(data) {
    clickPerSecond = 0
    secondToSend = secondToSend + 1;
}

function sendClicks() {
    if (team == "blue") {
        $.ajax({ type: 'GET', url: sendClicksUrlBlue + "/" + player + "/" + secondToSend + "/" + clickPerSecond, dataType: "json", crossDomain: true, cache: false,
            success: function (data) {
                console.log(data);
                callbackSendClicks(data);
            },
            error: function (xhr, testStatus, error) {
                console.log(error)
            }   });
    } else if (team == "red") {
        $.ajax({ type: 'GET', url: sendClicksUrlRed + "/" + player + "/" + secondToSend + "/" + clickPerSecond, dataType: "json", crossDomain: true, cache: false,
            success: function (data) {
                callbackSendClicks(data);
            },
            error: function (xhr, testStatus, error) {
                console.log(error)
            }   });
    }
}

function retrieveValues() {
    $.ajax({ type: 'GET', url: retrieveCountersUrl, dataType: "json", crossDomain: true, cache: false,
        success: function (data) {
            console.log(data);
            callbackRetrieveCounters(data);
        },
        error: function (xhr, testStatus, error) {
            console.log(error)
        }   });

}

function callbackRetrieveCounters(data) {
    document.getElementById("bulls").innerHTML = data.redTeamCounter + " clicks";
    document.getElementById("bears").innerHTML = data.blueTeamCounter + " clicks";
    if(data.hasGameEnded == true){
        clearInterval(sendClicksInterval);
        clearInterval(moveInterval);
        clearInterval(updateCountersInterval);
        if(data.redTeamCounter < data.blueTeamCounter){
            window.location=winnerPage+"?winner=bears&player=" + player +"&playerTeam="+team;
        }   else{
            window.location=winnerPage+"?winner=bulls&player=" + player +"&playerTeam="+team;
        }
    }

}
