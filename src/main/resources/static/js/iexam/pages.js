$(function() {
	let active_paper = 1; 
	console.log(active_paper);
	let all_page = 20;
	console.log(all_page);
	let keyPage=0;

	function change(active_paper) {
		if(active_paper == 1) {
			$('.pages-next').removeAttr('disabled');
			$('.pages-last').removeAttr('disabled');
			$('.pages-first').attr("disabled","disabled");
			$('.pages-prev').attr("disabled","disabled");
			console.log(active_paper+"，已为第一页");
		} else if(active_paper == all_page) {
			$('.pages-next').attr("disabled","disabled");
			$('.pages-last').attr("disabled","disabled");
			$('.pages-first').removeAttr('disabled');
			$('.pages-prev').removeAttr('disabled');
			console.log(active_paper+"，已为最后一页");
		} else {
			$('.pages-next').removeAttr('disabled');
			$('.pages-last').removeAttr('disabled');
			$('.pages-first').removeAttr('disabled');
			$('.pages-prev').removeAttr('disabled');
			console.log("当前页："+active_paper);
		}
	}

	//点击至首页
	$('.pages-first').click(function() {
		active_paper = 1;
		keyPage=active_paper;
		change(active_paper);
		$(".pages-active").val("第"+active_paper+"页/共"+all_page+"页");
	});

	//点击至尾页
	$('.pages-last').click(function() {
		active_paper = all_page;
		keyPage=active_paper;
		change(active_paper);
		$(".pages-active").val("第"+active_paper+"页/共"+all_page+"页");
	});

	//下一页
	$(".pages-next").click(function() {
		if(active_paper == all_page) {
			console.log("当前页："+active_paper+"，无法继续点击");
			return false; //如果大于总页数就禁用下一页
		} else {
			$(".pages-active").text(++active_paper); //点击下一页的时候当前页数的值就加1
			keyPage=active_paper;
			change(active_paper);
			$(".pages-active").val("第"+active_paper+"页/共"+all_page+"页");
		}
	});

	//上一页
	$(".pages-prev").click(function() {
		if(active_paper == 1) {
			console.log("当前页："+active_paper+"，无法继续点击");
			return false;
		} else {
			$(".pages-active").text(--active_paper);
			keyPage=active_paper;
			change(active_paper);
			$(".pages-active").val("第"+active_paper+"页/共"+all_page+"页");
		}
	});

	$(".pages-active").focus(function(event) {
		$(this).val(active_paper).select();
	});

	$(".pages-active").focusout(function(event){
		/* Act on the event */
		if (keyPage>all_page|keyPage<0|isNaN(keyPage)) {
			$(this).val("第"+active_paper+"页/共"+all_page+"页")
		}else{
			active_paper=keyPage;
			change(active_paper);
			$(this).val("第"+active_paper+"页/共"+all_page+"页")
		}
	});
	$(".pages-active").keyup(function(event) {
		/* Act on the event */
		keyPage=$(this).val();
	});
});