$(function(){
	$("#myform").validate({
		debug : true ,
		submithandler : function(form){
			form.submit();	//tjiao
		},
		errorPlacement : function (error,element){
			$("#"  + $(element).attr("id").replace(".","\\.")+"Msg").append(error);
		},
		highlight : function(element,errorClass){
			$(element).fadeOut(1,function(){
				$(element).fadeIn(1,function(){
					$("#"+$(element).attr("id").replace(".","\\."))
				})
			})
		},
		rules : {
			"mid" : {
				required : true 
			},
			"password" : {
				required : true 
			},
			"code" : {
				required : true ,
				remote : {
					url : "checkRandAndCode.action",	//
					type : "post" ,
					dataType : "html".
					data : {
						code : function(){
							return $("#code").val();
						}
					},
					dataFilter : function(data,type){
						if(data.trim() == "true"){
							return true ;
						}else{
							return false; 
						}
					}
				}
			}
		}
	})
})