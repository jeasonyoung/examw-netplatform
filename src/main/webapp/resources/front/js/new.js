$(document).ready(function(){
    $('.sidelist').mousemove(function(){							  
	$(this).find('.kch-class').show();
	$(this).find('h3').addClass('hover');
    $(this).find(".arrow").hide();
	});
	$('.sidelist').mouseleave(function(){
	$(this).find('.kch-class').hide();
	$(this).find(".arrow").show();
	$(this).find('h3').removeClass('hover');
	});
	$(".kch-nva").mousemove(function(){
	$("#sidebar2").show();								 
	});
	$(".kch-nva").mouseleave(function(){
	$("#sidebar2").hide();								 
	});
	$("#sidebar2").children().mousemove(function(){
	$("#sidebar2").show();	
	})
});

var sidebar2Hover;
$(function(){
$('#sidebar2').mouseleave(function(){
hide_sidebar2();
});
$('#sidebar2').mouseenter(function() { sidebar2Hover = true; });
});
function hide_sidebar2() {
var timeout = function() {
if (!sidebar2Hover) $('#sidebar2').hide();
};
sidebar2Hover = false;
setTimeout(timeout, 10);
}
