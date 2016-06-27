function loginClick(){
	var loginId = jQuery('#loginId').val();
	var loginPasswd = jQuery('#loginPasswd').val();
	var domain = jQuery('#domain').val();
	
	if(loginId == '' || loginPasswd == ''){
		jQuery('#loginError').html('用户名/密码为空...');
		return;
	}
	
	jQuery.ajax({
		url : domain + '/auth/auth/do',
		type : 'POST',
		dataType : 'json',
		data : {'loginId':loginId,'passwd':loginPasswd},
		error: function(error){
	       alert('登陆处理出现错误...请按F5刷新页面...');
	    },
	    success: function(data){
	    	 if(data.code == 1){
	    		 jQuery('#loginError').html('用户名/密码为空...');
	 			return;
	    	 }else if(data.code == 2){
	    		jQuery('#loginError').html('用户不存在/用户名密码不正确...');
	 			return;
	    	 }else if(data.code == -1){
	    		 window.location.href = domain + '';
	    	 }
	    }
	});
	
	jQuery('#loginButton').submit();
}

//回车事件
function change(evt) {
	document.onkeydown = function(event) {  
        var target, code, tag;  
        if (!event) {  
            event = window.event; //针对ie浏览器  
            target = event.srcElement;  
            code = event.keyCode;  
            if (code == 13) {  
            	loginClick();
                tag = target.tagName;  
                if (tag == "TEXTAREA") { return true; }  
                else { return false; }  
            }  
        }  
        else {  
            target = event.target; //针对遵循w3c标准的浏览器，如Firefox  
            code = event.keyCode;  
            if (code == 13) {  
            	loginClick();
                tag = target.tagName;  
                if (tag == "INPUT") { return false; }  
                else { return true; }   
            }  
        }  
    };  
}