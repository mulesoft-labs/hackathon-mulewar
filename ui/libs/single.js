// JScript source code
$(document).ready(function() {
//global variables
var form = $("#form1");
var name = $("#txtname"); //textbox u are going to validate
var nameInfo = $("#infotext"); //to display error message
//first validation on form submit
form.submit(function() {
// validation begin before submit
if (validateName()) {
return true;
} else { return false; }
});
//declare name validation function
function validateName() {
//validation for empty
if (name.val() == "") {
name.addClass("error");
nameInfo.text("Names cannot be empty!");
nameInfo.addClass("error");
return false;
} else {
name.removeClass("error");
nameInfo.text("*");
nameInfo.removeClass("error");
}
//if it's NOT valid
if (name.val().length < 4) {
name.addClass("error");
nameInfo.text("Names must have at least 4 letters!");
nameInfo.addClass("error");
return false;
}
//if it's valid
else {
name.removeClass("error");
nameInfo.text("*");
nameInfo.removeClass("error");
return true;
}
// validation only for characters no numbers
//var filter = /^[a-zA-Z]*$/;
//if (filter.test(name.val())) {
//name.removeClass("error");
//nameInfo.text("*");
//nameInfo.removeClass("error");
//return true;
//}
//else {
//name.addClass("error");
//nameInfo.text("Names cannot have numbers!");
//nameInfo.addClass("error");
//return false;
//}
}
});

