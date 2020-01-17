// item selection
$('li.image_li').click(function () {
  $(this).toggleClass('selected');
  if ($('li.image_li.selected').length == 0)
    $('.select').removeClass('selected');
  else
    $('.select').addClass('selected');
  counter();
});

// all item selection
$('.select').click(function () {
  if ($('li.image_li.selected').length == 0) {
    $('li.image_li').addClass('selected');
    $('.select').addClass('selected');
  }
  else {
    $('li.image_li').removeClass('selected');
    $('.select').removeClass('selected');
  }
  counter();
});

// number of selected items
function counter() {
  if ($('li.image_li.selected').length > 0)
    $('.send').addClass('selected');
  else
    $('.send').removeClass('selected');
  $('.send').attr('data-counter',$('li.image_li.selected').length);
}

/*要删除的图片src传到后端*/
function Send_to_servlet() {
  var images_list=document.getElementsByClassName("selected");
  var  form_submit = document.getElementById('form_submit');
  for (var i=2;i<images_list.length;++i) {
    var str = images_list[i].innerHTML;
    var imgSrc = str.substring(10, str.length - 2);


    var node = document.createElement("input");
    node.setAttribute("type", "hidden");
    node.setAttribute("name", "src")
    node.setAttribute("value", imgSrc);
    form_submit.appendChild(node);
  }
  form_submit.submit();
}