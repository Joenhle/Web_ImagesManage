// JavaScript Document
$('select.dropdown').each(function() {

    var dropdown = $('<div />').addClass('dropdown selectDropdown');

    $(this).wrap(dropdown);

    var label = $('<span />').text($(this).attr('placeholder')).insertAfter($(this));
    var list = $('<ul />');

    $(this).find('option').each(function() {
        list.append($('<li />').append($('<a />').text($(this).text())));
    });

    list.insertAfter($(this));

    if($(this).find('option:selected').length) {
        label.text($(this).find('option:selected').text());
        list.find('li:contains(' + $(this).find('option:selected').text() + ')').addClass('active');
        $(this).parent().addClass('filled');
    }

});

$(document).on('click touch', '.selectDropdown ul li a', function(e) {
    e.preventDefault();
    var dropdown = $(this).parent().parent().parent();
    var active = $(this).parent().hasClass('active');
    var label = active ? dropdown.find('select').attr('placeholder') : $(this).text();

    dropdown.find('option').prop('selected', false);
    dropdown.find('ul li').removeClass('active');

    dropdown.toggleClass('filled', !active);
    dropdown.children('span').text(label);

    if(!active) {
        dropdown.find('option:contains(' + $(this).text() + ')').prop('selected', true);
        $(this).parent().addClass('active');
    }

    dropdown.removeClass('open');
    
    if($(this).text()=="遥感图像" || $(this).text()=="卫星样图" || $(this).text()=="环境监测" || $(this).text()=="类型未知"){
        if($(this).text()=="类型未知"){
        	document.getElementById("hidden_image_category").value="未知"
        }else{
        	document.getElementById("hidden_image_category").value=$(this).text()
        }
    	
    }else if($(this).text()=="SVIA" || $(this).text()=="SVIB" || $(this).text()=="SVIC" || $(this).text()=="SVID" || $(this).text()=="卫星未知"){
    	if($(this).text()=="卫星未知"){
    	   document.getElementById("hidden_satellite").value="未知"
    	}else{
    		document.getElementById("hidden_satellite").value=$(this).text()
    	}       
    }
});

$('.dropdown > span').on('click touch', function(e) {
    var self = $(this).parent();
    self.toggleClass('open');
});

$(document).on('click touch', function(e) {
    var dropdown = $('.dropdown');
    if(dropdown !== e.target && !dropdown.has(e.target).length) {
        dropdown.removeClass('open');
    }
});

// light
$('.switch input').on('change', function(e) {
    $('.dropdown, body').toggleClass('light', $(this).is(':checked'));
});