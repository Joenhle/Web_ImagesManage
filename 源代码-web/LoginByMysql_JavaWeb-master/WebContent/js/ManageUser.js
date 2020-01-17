
var titles = ['用户名', '邮箱', '图片数量', '权限', '注册日期']
function isEmailAvailable(emailInput) {
	   var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	   if (!myreg.test(emailInput)) {
		   alert('邮箱格式错误');
	       return false;
	   }
	   else {
	       return true;
	   }
	}
$(function () {
    var table = $('#books').DataTable({
        data: data,
        "pagingType": "full_numbers",
        "bSort": true,
        "language": {
            "sProcessing": "处理中...",
            "sLengthMenu": "显示 _MENU_ 项结果",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页"
            },
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        },
        "columnDefs": [{
            "searchable": false,
            "orderable": true,
            "targets": 0
        }],
        "order": [[1, 'asc']]
    });
    table.on('order.dt search.dt', function() {
        table.column(0, {
            search: 'applied',
            order: 'applied'
        }).nodes().each(function(cell, i) {
            cell.innerHTML = i + 1;
        });
    }).draw();
    $('#books tbody').on('click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    });
    $("#cancelAdd").on('click', function() {
        console.log('cancelAdd');
        $("#addBookModal").find('input').val('')
    })
    $("#addBooksInfo").on('click', function() {
        console.log('addBooksInfo');
        if (data.length) {
            if ($("#addBookModal").find('input').val() !== '') {
            	var fin_flag=1;
            	var addtitles = ['用户名', '密码', '图片数量', '权限', '邮箱']
                var bookName = $("#bookName").val()
                var bookAuthor = $("#bookAuthor").val()
                var bookPrice = $("#bookPrice").val()
                var bookPublish = $("#bookPublish").val()
                var bookISBN = $("#bookISBN").val()
                var addBookInfos = [].concat(bookName, bookAuthor, bookPrice, bookPublish, bookISBN);
                for (var i = 0; i < addBookInfos.length; i++) {
                    if (addBookInfos[i] === '') {
                        alert(addtitles[i] + '内容不能为空');
                        fin_flag=0;
                    }
                    else if(i===3){
                    	if( (bookPublish!=="管理员")&&(bookPublish!=="普通用户") ){
                    	    alert('权限只能为管理员或普通用户' );
                    	    fin_flag=0;
                    	}
                    }
                    
                }
                if(fin_flag && isEmailAvailable(bookISBN) ){
                    table.row.add(['', bookName, bookAuthor, bookPrice, bookPublish, bookISBN]).draw();
                    var add_userform = document.getElementById('add_userform');
                    add_userform.submit();              //提交添加用户表单
                    //data.push(['', bookName, bookAuthor, bookPrice, bookPublish, bookISBN]); 
                }
                $("#addBookModal").find('input').val('')
            }
        } else {
            alert('请输入内容')
        }
    })
    $("#addBooksInfo").click();
    $("#btn_add").click(function(){
        console.log('add');
        $("#addBook").modal()
    });
    $('#btn_edit').click(function () {
        console.log('edit');
        if (table.rows('.selected').data().length) {
            $("#editBookInfo").modal()
            var rowData = table.rows('.selected').data()[0];
            var inputs = $("#editBookModal").find('input');
            document.getElementById("editBookName").innerHTML=rowData[1];
            for (var i = 0; i < inputs.length-1; i++) {
                $(inputs[i]).val(rowData[i + 2])
            }
        } else {
            alert('请选择项目');
        }
    });
    $("#saveEdit").click(function() {
    	var fin_flag=1;
    	var rowData = table.rows('.selected').data()[0];
        var editBookName = rowData[1];
        var editBookAuthor = $("#editBookAuthor").val()
        var editBookPrice = $("#editBookPrice").val()
        var editBookPublish = $("#editBookPublish").val()
        var editBookISBN = $("#editBookISBN").val()
        
        var newRowData = [].concat(editBookName, editBookAuthor, editBookPrice, editBookPublish, editBookISBN);
        var tds = Array.prototype.slice.call($('.selected td'))
        for (var i = 0; i < newRowData.length; i++) {
            if (newRowData[i] !== '') {
                tds[i + 1].innerHTML = newRowData[i];
                if(i===3){
                	if( (newRowData[i]!=="管理员")&&(newRowData[i]!=="普通用户") ){
                	    alert('权限只能为管理员或普通用户' );
                	    fin_flag=0;
                	}
                }
            } else {
            	if(i<newRowData.length-1){
                   alert(titles[i] + '内容不能为空');
                   fin_flag=0;
            	}
            }
        }
        if(fin_flag && isEmailAvailable(editBookAuthor) ){
            //alert('要修改的用户：'+table.rows('.selected').data()[0] );  //测试
            var change_userform = document.getElementById('change_userform');
            var node = document.createElement("input");
            node.setAttribute("type", "hidden");
            node.setAttribute("name", "username");
            node.setAttribute("value", editBookName);
            //alert();
            change_userform.appendChild(node);
            change_userform.submit();
            
            //data.filter(table.rows('.selected').data()[0]);  //从数组里删除
            //data.push(['', editBookName, editBookAuthor, editBookPrice, editBookPublish, editBookISBN]);  //修改后的数据添加到数组里
            //table.row('.selected').remove().draw(false);
            //table.row.add(['', editBookName, editBookAuthor, editBookPrice, editBookPublish, editBookISBN]).draw();
        }
    })
    $("#cancelEdit").click(function() {
        console.log('cancelAdd');
        $("#editBookModal").find('input').val('')
    })
    $('#btn_delete').click(function () {
        if (table.rows('.selected').data().length) {
            $("#deleteBook").modal()
        } else {
            alert('请选择项目');
        }
    });
    $('#delete').click(function () {
    	//alert('已经删除：'+table.rows('.selected').data()[0] );  //测试
        var delete_userform = document.getElementById('delete_userform');
        var node = document.createElement("input");
        node.setAttribute("type", "hidden");
        node.setAttribute("name", "deleted_user");
        node.setAttribute("value", table.rows('.selected').data()[0]);
        delete_userform.appendChild(node);
        delete_userform.submit();
        table.row('.selected').remove().draw(false);
    });
})